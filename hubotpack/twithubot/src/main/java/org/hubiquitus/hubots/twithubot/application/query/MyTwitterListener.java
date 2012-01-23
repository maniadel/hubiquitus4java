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

package org.hubiquitus.hubots.twithubot.application.query;

import org.hubiquitus.hubots.twithubot.application.TwitterBotInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class MyTwitterListener implements StatusListener {
	
	final Logger logger = LoggerFactory.getLogger(MyTwitterListener.class);
    private TwitterBotInterface twitterBot;


    public MyTwitterListener() {
        super();
    }

    public MyTwitterListener(TwitterBotInterface twitterBot) {
        super();
        this.twitterBot = twitterBot;
    }


    @Override
    public void onStatus(Status status) {
        System.out.println("User: " + status.getUser().getScreenName() + " tweeted: "
                + status.getText());

        twitterBot.publish(status);
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        logger.debug("Got a status deletion notice id:"
                + statusDeletionNotice.getStatusId());
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
    	logger.debug("Got track limitation notice:"
                + numberOfLimitedStatuses);
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
    	logger.debug("Got scrub_geo event userId:" + userId + " upToStatusId:"
                + upToStatusId);
    }

    @Override
    public void onException(Exception ex) {
        ex.printStackTrace();
    }

    
    // Getters and setters -------------------------------------------------
    public void setTwitterBot(TwitterBotInterface twitterBot) {
		this.twitterBot = twitterBot;
	}
}
