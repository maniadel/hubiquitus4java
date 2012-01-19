/*
 * Copyright (c) Novedia Group 2012.
 *
 *     This file is part of Hubiquitus.
 *
 *     Hubiquitus is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Hubiquitus is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Hubiquitus.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.hubiquitus.hubotsdk.application;

import java.util.List;

import org.hubiquitus.hubotsdk.application.push.impl.SmackApiControllerImpl;
import org.hubiquitus.hubotsdk.application.push.impl.XmppEntryPublisherImpl;
import org.hubiquitus.hubotsdk.exception.BotException;
import org.hubiquitus.hubotsdk.iq.provider.DataRequestIQProvider;
import org.hubiquitus.hubotsdk.iq.provider.DataResultIQProvider;
import org.hubiquitus.hubotsdk.iq.provider.NodeRequestIQProvider;
import org.hubiquitus.hubotsdk.iq.provider.PayloadResultIQProvider;
import org.hubiquitus.hubotsdk.iq.provider.UpdateDataRequestIQProvider;
import org.hubiquitus.hapi.model.ExceptionType;
import org.hubiquitus.hapi.model.PublishEntry;
import org.hubiquitus.hapi.model.impl.DataRequestEntry;
import org.hubiquitus.hapi.model.impl.DataResultEntry;
import org.hubiquitus.hapi.model.impl.NodeRequestEntry;
import org.hubiquitus.hapi.model.impl.PayloadResultEntry;
import org.hubiquitus.hapi.model.impl.UpdateDataRequestEntry;
import org.hubiquitus.hubotsdk.service.ExceptionCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.PrivateDataManager;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

public abstract class Bot implements BotInterface {

    protected Logger logger = LoggerFactory.getLogger(Bot.class);
    
    /*
      * Current bot state enum.
      */
    public static enum BotState {
        STARTED,
        PAUSED,
        ERROR,
        STOPPED,
        STARTED_ERRORS_RAISED
    }

    /**
     * Pubsub node name to use for publishing
     */
    protected String node;

    /**
     * Publisher object
     */
    private XmppEntryPublisherImpl xmppEntryPublisher;
    
    /*
      * Exception collector. Used to collect all raised exceptions
      */
    protected ExceptionCollector exceptionCollector;

    /*
     * The path to the configuration files
     */
    private String xmlConfPath;
    
    /*
      * Booleans and tags to manage publisher and fetcher execution states
      */
    protected static boolean stoppingFetcher = false;
    protected static boolean stoppingPublisher = false;

    protected static BotState publisherState;
    protected static BotState fetcherState;
    
    /**
     * Constructor
     */
    public Bot() {
        super();
    }

    
    public void startPublisherAndDefineNode() {
    	try {
    		
    		// Add news private data providers for data base request on hServer 
    		DataRequestIQProvider customPrivateDataIQProvider = new DataRequestIQProvider();
    		PayloadResultIQProvider payloadResultIQProvider = new PayloadResultIQProvider();
    		DataResultIQProvider dataResultIQProvider = new DataResultIQProvider();
    		NodeRequestIQProvider nodeRequestIQProvider = new NodeRequestIQProvider();
    		UpdateDataRequestIQProvider updateDataRequestIQProvider = new UpdateDataRequestIQProvider();
    		ProviderManager.getInstance().addIQProvider("query",DataRequestEntry.NAMESPACE, customPrivateDataIQProvider );
    		PrivateDataManager.addPrivateDataProvider(PayloadResultEntry.ELEMENTNAME, PayloadResultEntry.NAMESPACE, payloadResultIQProvider);
    		PrivateDataManager.addPrivateDataProvider(DataResultEntry.ELEMENTNAME, DataResultEntry.NAMESPACE, dataResultIQProvider);
    		ProviderManager.getInstance().addIQProvider(NodeRequestEntry.QUERY_ROOT_ELEMENT, NodeRequestEntry.NAMESPACE, nodeRequestIQProvider);
    		ProviderManager.getInstance().addIQProvider(UpdateDataRequestEntry.QUERY_ROOT_ELEMENT, UpdateDataRequestEntry.NAMESPACE, updateDataRequestIQProvider);
//    		PrivateDataManager.addPrivateDataProvider(UpdateDataRequestEntry.RESPONSE_ROOT_ELEMENT, UpdateDataRequestEntry.NAMESPACE, updateDataRequestIQProvider);

    		// Start publisher
    		startPublisher(); 
    		
    		// Define publish node
    		defineNode(this.getNodeName(), this.getTitle(), this.getAllowedRosterGroups());
           
            publisherState = BotState.STARTED;
        } catch (XMPPException e) {
        	logger.error(e.getMessage());
            publisherState = BotState.ERROR;
            exceptionCollector.addException(e, ExceptionType.PUBLISHER_LAUNCH_ERROR);
        } catch (BotException e) {
        	logger.error(e.getMessage());
            publisherState = BotState.ERROR;
            exceptionCollector.addException(e, ExceptionType.PUBLISHER_LAUNCH_ERROR);
        }
        
    }

    /**
     * Create (if not exists) and configure pubsub node
     * @param nodeName
     * @param title
     * @param allowedRosterGroups
     */
    protected void defineNode(String nodeName, String title, List<String> allowedRosterGroups) {
        this.createNodeFromHServer(nodeName, title, allowedRosterGroups);
        this.node = nodeName;
    }

    protected void createNodeFromHServer(String nodeName, String title, List<String> allowedRosterGroups) {
        if ((title == null) || (title.equals(""))) {
            title = nodeName;
        }
        // Create new request 
		NodeRequestEntry nodeRequestEntry = new NodeRequestEntry();
		// Parameters for creating node
		nodeRequestEntry.getNodeQuery().setTitle(title);
		nodeRequestEntry.getNodeQuery().setName(nodeName);
		nodeRequestEntry.getNodeQuery().getAllowedRosterGroups().setRosterGroup(allowedRosterGroups);
		logger.debug("defineNode:" + nodeRequestEntry.entryToJsonFormat());
		
    	try {
    		XmppEntryPublisherImpl xmppEntryPublisherImpl = this.getXmppEntryPublisher();
        	SmackApiControllerImpl smackApiControllerImpl = xmppEntryPublisherImpl.getSmackApiController();
    		smackApiControllerImpl.sendNodeCreationRequest(nodeRequestEntry);
		} catch (Exception e) {
        	logger.error(e.getMessage());
            exceptionCollector.addException(e, ExceptionType.PUBLISHER_NODE_CREATION_ERROR);
		}
    }

    protected void startPublisher() throws XMPPException, BotException {
        logger.info("Starting publisher.");
        getXmppEntryPublisher().getSmackApiController().connect(getNodeName());
        getXmppEntryPublisher().start(); 
    }


    /*
      * Stop XMPP publisher.
      */
    public void stopXmppPublisher() {
        stoppingPublisher = true;
        getXmppEntryPublisher().disconnect();
        getXmppEntryPublisher().interrupt();
        publisherState = BotState.STOPPED;
    }


   
   /*
    * Publish a json entry content to node
    * (non-Javadoc)
    */
  public void publish(PublishEntry entry) {
      getXmppEntryPublisher().publish(entry, node);
  }
  
  @Override
  public void publishToNode(PublishEntry entry, String node) {
	  getXmppEntryPublisher().publishToNode(entry, node);
  }
  
  @Override
  public DataRequestEntry sendDbRequest(DataRequestEntry dataRequestEntry) {
	  return getXmppEntryPublisher().sendDbRequest(dataRequestEntry);
  }

  @Override
  public void subscribeNode(String node, ItemEventListener<Item> itemEventListener) throws BotException {
	  try {
		getXmppEntryPublisher().getSmackApiController().subscribeNode(node, itemEventListener);
	} catch (XMPPException e) {
		logger.error(e.getMessage(), e);
	}
  }
  
    
    /*
      * Add exception to exception collector
      * (non-Javadoc)
      * @see com.novedia.alertmama.feedbot.application.BotInterface#addException(java.lang.Exception, com.novedia.alertmama.feedbot.service.ExceptionCollector.ExceptionType)
      */
    public void addException(Exception e, ExceptionType publishingError) {
        exceptionCollector.addException(e, publishingError);
    }


    /*
      * Ping XMPP server (not to be automatically disconnected connected)
      * (non-Javadoc)
      * @see com.novedia.alertmama.feedbot.application.BotInterface#ping()
      */
    public void ping() {
        try {
            getXmppEntryPublisher().ping();
        } catch (Exception e) {
        	logger.error(e.getMessage());
            this.addException(e, ExceptionType.PUBLISHER_PING_ERROR);
            Bot.setPublisherState(Bot.BotState.ERROR);
        }
    }

    /*
      * Stop feed fetcher process.
      */
    public void stopDataRetriever() {
        stoppingFetcher = true;
        fetcherState = BotState.STOPPED;
    }

    /*
      * Stop bot
      * (non-Javadoc)
      * @see com.novedia.alertmama.feedbot.application.BotInterface#stop()
      */
    public void stop() {
    	stopDataRetriever();
        fetcherState = BotState.STOPPED;
        stopXmppPublisher();
        publisherState = BotState.STOPPED;
    }

    /*
      * Check if fetcher stop procedure is initiated
      */
    public static boolean isStoppingFetcher() {
        return stoppingFetcher;
    }

    /*
      * Check if publisher stop procedure is initiated
      */
    public static boolean isStoppingPublisher() {
        return stoppingPublisher;
    }

    /*
      * Getters/Setters
      */
    public static void setPublisherState(BotState state) {
        publisherState = state;
    }

    public static BotState getPublisherState() {
        return publisherState;
    }

    public static void setFetcherState(BotState state) {
        fetcherState = state;
    }

    public static BotState getFetcherState() {
        return fetcherState;
    }

    public XmppEntryPublisherImpl getXmppEntryPublisher() {
        return xmppEntryPublisher;
    }
    
        /*
      * Get all exceptions collected formatted in a single string
      */
    public String getExceptions() {
        return exceptionCollector.getExceptions();
    }
    
    /**
     * getter xmlConfPath
     * @return xmlConfPath the xmlConfPath
     */
    public String getXmlConfPath() {
		return xmlConfPath;
	}

    /**
     * setter xmlConfPath
     * @param xmlConfPath the xmlConfPath
     */
	public void setXmlConfPath(String xmlConfPath) {
		this.xmlConfPath = xmlConfPath;
	}
    
    
    // Spring injections ------------------------------------
    @Required
    public void setExceptionCollector(ExceptionCollector exceptionCollector) {
		this.exceptionCollector = exceptionCollector;
	}


	@Required
	public void setXmppEntryPublisher(XmppEntryPublisherImpl xmppEntryPublisher) {
		this.xmppEntryPublisher = xmppEntryPublisher;
	}
    
    
}