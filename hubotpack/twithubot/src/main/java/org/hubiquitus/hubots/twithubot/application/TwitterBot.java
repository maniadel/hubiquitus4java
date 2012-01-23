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

package org.hubiquitus.hubots.twithubot.application;

import java.util.ArrayList;
import java.util.List;

import org.hubiquitus.hubotsdk.application.Bot;
import org.hubiquitus.hubotsdk.exception.BotException;
import org.hubiquitus.hapi.model.ExceptionType;
import org.hubiquitus.hubots.twithubot.application.query.MyTwitterListener;
import org.hubiquitus.hubots.twithubot.service.impl.TwitterBotConfigurationSpring;
import org.hubiquitus.hubots.twithubot.service.impl.TwitterBotUtilsImpl;
import org.springframework.beans.factory.annotation.Required;

import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterBot extends Bot implements TwitterBotInterface {
	
	protected TwitterBotConfigurationSpring hubotConfig;
    
    /**
     * Constructor
     *
     */
    public TwitterBot() {
        super();
    }

    /*
     * Start Feed fetcher with general conf
     * 
     * Specific to Spring
     */
    public void startDataRetriever() throws BotException {

    	fetcherState = BotState.STARTED;
    	
    	if (this.getXmppEntryPublisher() == null) {
    		logger.error("Publisher not started yet. Not allowed to start fetcher.");
    		
            BotException botException = new BotException(
                    "Publisher not started yet. Not allowed to start fetcher.");
    		
    		fetcherState = BotState.ERROR;
            exceptionCollector.addException(botException, ExceptionType.FETCHER_LAUNCH_ERROR);
            
            throw botException;
        }

        startStreaming();
    }
    
    // Specific for Spring
    public void startStreaming() {
        logger.info("Starting Twitter Streaming with the following filter: " + hubotConfig.getListKeyWords());
    	
    	ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
        		.setUseSSL(false)
                .setOAuthConsumerKey(hubotConfig.getConsumerKey())
                .setOAuthConsumerSecret(hubotConfig.getConsumerSecret())
                .setOAuthAccessToken(hubotConfig.getAccessTokenKey())
                .setOAuthAccessTokenSecret(hubotConfig.getAccessTokenSecret());
        TwitterStreamFactory tsf = new TwitterStreamFactory(cb.build());
        TwitterStream twitterStream = tsf.getInstance();

        MyTwitterListener listener = new MyTwitterListener(this);
        twitterStream.addListener(listener);

        ArrayList<String> track = new ArrayList<String>();
        
        for (String keyword : hubotConfig.getListKeyWords()) {
        	track.add(keyword);
        }

        String[] trackArray = track.toArray(new String[track.size()]);
        twitterStream.filter(new FilterQuery(0, null, trackArray));
    }

    public void publish(Status status) {
        getXmppEntryPublisher().publish(TwitterBotUtilsImpl.statusToAMPublishEntry(status), node);
    }


    
    
    @Override
   	public String getNodeName() {
   		return this.hubotConfig.getNodeName();
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

    // Spring injections --------------------------------------------------------------
    @Required
	public void setHubotConfig(TwitterBotConfigurationSpring hubotConfig) {
		this.hubotConfig = hubotConfig;
	}

}
