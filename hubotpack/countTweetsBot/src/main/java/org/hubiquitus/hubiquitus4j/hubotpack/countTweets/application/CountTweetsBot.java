/*
 * Copyright (c) Novedia Group 2011.
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

package org.hubiquitus.hubiquitus4j.hubotpack.countTweets.application;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.hubiquitus.hapi.model.ExceptionType;
import org.hubiquitus.hapi.model.impl.ComplexPublishEntry;
import org.hubiquitus.hapi.utils.impl.CommonJSonParserImpl;
import org.hubiquitus.hubiquitus4j.hubotpack.countTweets.application.query.CountTweetsItemEventListener;
import org.hubiquitus.hubiquitus4j.hubotpack.countTweets.service.impl.CountTweetsBotConfigurationSpring;
import org.hubiquitus.hubiquitus4j.hubotpack.countTweets.service.impl.CountTweetsBotUtilsImpl;
import org.hubiquitus.hubiquitus4j.hubotpack.countTweets.service.impl.CountTweetsTimer;
import org.hubiquitus.hubotsdk.application.Bot;
import org.hubiquitus.hubotsdk.application.push.impl.SmackApiControllerImpl;
import org.hubiquitus.hubotsdk.application.push.impl.XmppEntryPublisherImpl;
import org.hubiquitus.hubotsdk.exception.BotException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.Item;
import org.springframework.beans.factory.annotation.Required;

public class CountTweetsBot extends Bot{
	
	/**
	 * Bot configuration
	 */
	protected CountTweetsBotConfigurationSpring hubotConfig;
	
	/**
	 * The payload of each message send to the publish node 
	 */
	private ComplexPublishEntry complexPublishEntry;
	
	private static Calendar instanceStarted;
	
	private CountTweetsTimer countTweetsTimer; 
	
	private static HashMap<String, Integer> nbTweets = new HashMap<String, Integer>();
	private List<Item>nbMessages ;
	
	/**
	 * JSON Parser
	 */
	protected CommonJSonParserImpl jSonParser;

	
	/**
     * Constructor
     *
     */
    public CountTweetsBot() {
        super();
    }

    
    /*
     * Start Feed fetcher with general conf
     * 
     * Specific to Spring
     */
    public void startDataRetriever() throws BotException {
    	
    	fetcherState = BotState.STARTED;
    	
    	XmppEntryPublisherImpl xmppEntryPublisherImpl = this.getXmppEntryPublisher();
    	if (this.getXmppEntryPublisher() == null) {
    		logger.error("Publisher not started yet. Not allowed to start fetcher.");
    	    BotException botException = new BotException(
                    "Publisher not started yet. Not allowed to start fetcher.");
    		fetcherState = BotState.ERROR;
            exceptionCollector.addException(botException, ExceptionType.FETCHER_LAUNCH_ERROR);
            throw botException;
        }
    
    	SmackApiControllerImpl smackApiControllerImpl = xmppEntryPublisherImpl.getSmackApiController();
    	CountTweetsItemEventListener countTweetsItemEventListener = new CountTweetsItemEventListener();
    	countTweetsItemEventListener.setCountTweetsBot(this);
    	
    	try {
    		// Subscribe any Bot 
			smackApiControllerImpl.subscribeNode(getTwitterNodeNameToRead(), countTweetsItemEventListener);
		} catch (XMPPException e) {
			throw new BotException("Data retriever error, can't suscribe node " + getTwitterNodeNameToRead() + " - " + e.getMessage());
		}
    	
    	//timer
    	countTweetsTimer = new CountTweetsTimer(60*1000, this);
    	countTweetsTimer.start();
    	try {
    		countTweetsTimer.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
    }
    
    /**
     * Get the twitter node name to read 
     * @return name of twitter node
     */
    public String getTwitterNodeNameToRead() {
    	return this.hubotConfig.getNodeNameToRead();
    }
    
    /**
     * getter complexPublishEntry
     * @return the complexPublishEntry
     */
    public ComplexPublishEntry getComplexPublishEntry() {
    	if (complexPublishEntry == null) {
    		XmppEntryPublisherImpl xmppEntryPublisherImpl = this.getXmppEntryPublisher();
    		SmackApiControllerImpl smackApiControllerImpl = xmppEntryPublisherImpl.getSmackApiController();
    		complexPublishEntry = CountTweetsBotUtilsImpl.initComplexPublishEntry(smackApiControllerImpl.getXmppBotConfiguration(), getNodeName()); 
    	}
  		return complexPublishEntry;
  	}

    /**
     * setter complexPublishEntry
     * @param complexPublishEntry the complexPublishEntry to set
     */
  	public void setComplexPublishEntry(
  			ComplexPublishEntry complexPublishEntry) {
  		this.complexPublishEntry = complexPublishEntry;
  	}
      
    @Override
   	public String getNodeName() {
   		return this.hubotConfig.getNodeName();
   	}
    
	public String getNodeTPMToRead() {
    	return this.hubotConfig.getNodeTPMToRead();
    }

   	@Override
   	public String getTitle() {
   		return this.hubotConfig.getTitle();
   	}

   	@Override
   	public List<String> getAllowedRosterGroups() {
   		return this.hubotConfig.getAllowedRosterGroups();
   	}

   	@Override
   	public String getMainConfJSON() {
   		return this.hubotConfig.getMainConfJSON();
   	}

    public static Calendar getInstanceStarted() {
		return instanceStarted;
	}


	public static void setInstanceStarted(Calendar instanceStarted) {
		CountTweetsBot.instanceStarted = instanceStarted;
	}

	public static HashMap<String, Integer> getNbTweets() {
		return nbTweets;
	}

	public static void setNbTweets(HashMap<String, Integer> nbTweets) {
		CountTweetsBot.nbTweets = nbTweets;
	}
	
	

	/**
	 * Getter jSonParser
	 * @return the jSonParser
	 */
	public CommonJSonParserImpl getJSonParser() {
		return jSonParser;
	}
	
	public List<Item> getNbMessages() {
		return nbMessages;
	}


	public void setNbMessages(List<Item> nbMessages) {
		this.nbMessages = nbMessages;
	}


	// Spring injections --------------------------------------------------------------
    @Required
	public void setHubotConfig(CountTweetsBotConfigurationSpring hubotConfig) {
		this.hubotConfig = hubotConfig;
	}
    
    @Required
	public void setJSonParser(CommonJSonParserImpl jSonParser) {
		this.jSonParser = jSonParser;
	}
}