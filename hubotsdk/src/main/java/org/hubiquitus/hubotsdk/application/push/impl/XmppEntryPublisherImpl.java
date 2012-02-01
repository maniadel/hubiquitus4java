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

package org.hubiquitus.hubotsdk.application.push.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.hubiquitus.hubotsdk.application.Bot;
import org.hubiquitus.hubotsdk.application.push.XmppEntryPublisher;
import org.hubiquitus.hubotsdk.exception.BotException;
import org.hubiquitus.hapi.model.ExceptionType;
import org.hubiquitus.hapi.model.PublishEntry;
import org.hubiquitus.hapi.model.impl.DataRequestEntry;
import org.hubiquitus.hapi.model.impl.UpdateDataRequestEntry;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

public class XmppEntryPublisherImpl extends Thread implements XmppEntryPublisher {

    Logger logger = LoggerFactory.getLogger(XmppEntryPublisherImpl.class);
	
    /*
      * Retrieved data queue
      */
    protected Queue<PublishEntry> queue;
    protected Semaphore queueContent;

    /*
      * Map node title and name
      */
    protected Map<String, String> nodeEntryAssociations;

    /*
      * XMPP Connection and interactions manager
      */
    protected SmackApiControllerImpl smackApiController;

    protected static SimpleDateFormat updateDateFormat = new SimpleDateFormat(
            "yyyyMMddhhmmss");
    protected static SimpleDateFormat publishDateFormat = new SimpleDateFormat(
            "yyyy/MM/dd hh:mm:ss");

    
    /*
     * Constructor
     */
   public XmppEntryPublisherImpl() {
	   this.queue = new LinkedBlockingQueue<PublishEntry>();
       this.queueContent = new Semaphore(0);

       this.nodeEntryAssociations = new HashMap<String, String>();
   }
    
    
    /*
      * Add data entry to queue
      */
    public void addToQueue(final PublishEntry entry, final String node) {
        this.queue.add(entry);
        this.nodeEntryAssociations.put(entry.getEntryType(), node);
        this.queueContent.release();
    }

    /*
      * Check if node with the given name already exists in server
      */
    public boolean checkExists(String node) throws XMPPException {
        return smackApiController.existsNode(node);
    }

    /*
      * Check if node with the given name already exists in server
      */
    public void createNode(String node, String title, List<String> allowedRosterGroups) throws BotException, XMPPException {
        smackApiController.createNode(node, title, allowedRosterGroups);
    }

    /*
      * Format publish date
      */
    public static String formatPublishDate(Date date) {
        String dat = "";
        try {
            if ((date != null) && (!date.equals("")))
                dat = publishDateFormat.format(date);
        } catch (Exception e) {
            dat = "";
        }
        return dat;
    }

    /*
      * Format update date
      */
    public static String formatUpdateDate(Date date) {
        String dat = "";
        try {
            if ((date != null) && (!date.equals("")))
                dat = updateDateFormat.format(date);
        } catch (Exception e) {
            dat = "";
        }
        return dat;
    }


    @Override
    public void run() {
        while ((isAlive()) && (!Bot.isStoppingPublisher())) {
            try {
                this.queueContent.tryAcquire(1, TimeUnit.SECONDS);
                if (smackApiController.isConnected()) {
                	PublishEntry entry = queue.poll();
                	if (entry != null) {
                		logger.info("Publishing [" + entry.entryToJsonFormat()
                				+ "]");
                		publish(entry,
                				nodeEntryAssociations.get(entry.getEntryType()));
                	}
                } else {
                    logger.error("Not connected.");
                    smackApiController.tryToReconnect();
                }
            } catch (XMPPException e) {
                logger.error(e.getMessage());
                smackApiController.getBotInterface().addException(e,
                        ExceptionType.PUBLISHER_CONNECTION_ERROR);
                Bot.setPublisherState(Bot.BotState.ERROR);
            } catch (InterruptedException e) {
            	logger.error(e.getMessage());
                smackApiController.getBotInterface().addException(e,
                        ExceptionType.PUBLISHER_CONNECTION_ERROR);
                Bot.setPublisherState(Bot.BotState.ERROR);
                smackApiController.getBotInterface().addException(e, ExceptionType.PUBLISHER_INTERRUPTED_ERROR);
            }
        }
    }

    
    @Override
    public void publishToNode(PublishEntry entry, String node) {
    	String jsonBegin = "{ \"alert\":";
    	String jsonEnd = "}";
    	String answer = entry.entryToJsonFormat();
		String jsonSequence = jsonBegin + answer + jsonEnd;
        try {
        	
        	// Publish the entry to node
            smackApiController.publishToNode(node, jsonSequence, node.concat(generateId()));
            
        } catch (Exception e) {
        	logger.error(e.getMessage());
            smackApiController.getBotInterface().addException(e, ExceptionType.PUBLISHER_PUBLISHING_ERROR);
        }
	
    }
    
    @Override
    public void publish(PublishEntry entry, String node) {
    	String jsonBegin = "{ \"alert\":";
    	String jsonEnd = "}";
    	String answer = entry.entryToJsonFormat();
		String jsonSequence = jsonBegin + answer + jsonEnd;
        try {
        	
        	// Publish the entry to node
            smackApiController.publishToNode(node, jsonSequence, node.concat(generateId()));
            
            // Send the entry to the hServer
            smackApiController.sendMessage(jsonSequence);
            
        } catch (Exception e) {
        	logger.error(e.getMessage());
            smackApiController.getBotInterface().addException(e, ExceptionType.PUBLISHER_PUBLISHING_ERROR);
        }
	
    }
    
    @Override
    public void sendMessage(PublishEntry entry) {
    	String jsonBegin = "{ \"alert\":";
    	String jsonEnd = "}";
    	String answer = entry.entryToJsonFormat();
    	String jsonSequence = jsonBegin + answer + jsonEnd;
        try {
        	smackApiController.sendMessage(jsonSequence);
        } catch (XMPPException xmppE) {
        	logger.error(xmppE.getMessage());
            smackApiController.getBotInterface().addException(xmppE, ExceptionType.PUBLISHER_PUBLISHING_ERROR);
        }
    }
    
    
    @Override
    public List<Item> getItemsFromNode(String nodeName, int maxItems) throws XMPPException {
          return smackApiController.getItemsFromNode(nodeName, maxItems);
    }
        

    /*
      * Generate unique ID
      */
    protected String generateId() {
        UUID id = UUID.randomUUID();
        return id.toString();
    }

    /*
      * Disconnect from XMPP Server
      */
    public void disconnect() {
        smackApiController.disconnect();
    }

    /*
      * Ping XMPP Server
      */
    public void ping() throws Exception {
        smackApiController.ping();
    }

    
    
    @Override
	public DataRequestEntry sendDbRequest(DataRequestEntry dataRequestEntry) {
    	DataRequestEntry response = null;
        try {
        	response = smackApiController.sendDbRequest(dataRequestEntry);
        } catch (XMPPException xmppE) {
        	xmppE.printStackTrace();
            smackApiController.getBotInterface().addException(xmppE, ExceptionType.PUBLISHER_SEND_REQUEST_ERROR);
        }
    	return response;
		
	}
    
	@Override
	public void sendUpdateDbRequest(UpdateDataRequestEntry updateDataRequestEntry) {
        try {
        	smackApiController.sendUpdateDbRequest(updateDataRequestEntry);
        } catch (XMPPException xmppE) {
            smackApiController.getBotInterface().addException(xmppE, ExceptionType.PUBLISHER_SEND_REQUEST_ERROR);
        }
	}


	@Override
	public void modifyAffiliation(String subscribedNodeName, String userAffiliation, String subscribedNodeAffiliationType) {
        try {
        	smackApiController.modifyAffiliation(subscribedNodeName, userAffiliation, subscribedNodeAffiliationType);
        } catch (XMPPException xmppE) {
            smackApiController.getBotInterface().addException(xmppE, ExceptionType.PUBLISHER_SEND_REQUEST_ERROR);
        }
	}
    
    
    // Getters and Setters ----------------------------------------------------------
	public SmackApiControllerImpl getSmackApiController() {
		return smackApiController;
	}
		
	// Spring injections
	@Required
	public void setSmackApiController(SmackApiControllerImpl smackApiController) {
		this.smackApiController = smackApiController;
	}
}