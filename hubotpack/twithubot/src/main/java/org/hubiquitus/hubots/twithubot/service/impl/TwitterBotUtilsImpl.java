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

package org.hubiquitus.hubots.twithubot.service.impl;

import java.util.Date;

import org.hubiquitus.hapi.model.impl.HeaderPublishEntry;
import org.hubiquitus.hapi.model.impl.LabelPublishEntry;
import org.hubiquitus.hapi.model.impl.LocationPublishEntry;
import org.hubiquitus.hapi.model.impl.MessagePublishEntry;

import twitter4j.Status;

public class TwitterBotUtilsImpl {


    public static MessagePublishEntry statusToAMPublishEntry(Status status) {
    	MessagePublishEntry messagePublishEntry = new MessagePublishEntry();

    	messagePublishEntry.generateMsgid();
    	messagePublishEntry.setPublisher("TwitterBot");
    	messagePublishEntry.setType("tweet");
    	messagePublishEntry.setAuthor(status.getUser().getScreenName());
    	messagePublishEntry.setPublished(status.getCreatedAt());
    	
    	if (status.getGeoLocation()!=null) {
	    	if(status.getGeoLocation().getLatitude() != 0 && status.getGeoLocation().getLongitude() != 0)
	    	{
		    	LocationPublishEntry locationPublishEntry = new LocationPublishEntry();
		    	locationPublishEntry.setLat(String.valueOf(status.getGeoLocation().getLatitude()));
		    	locationPublishEntry.setIng(String.valueOf(status.getGeoLocation().getLongitude()));
		    	messagePublishEntry.setLocation(locationPublishEntry);
	    	}
    	}
    	
    	messagePublishEntry.addHeader("source", status.getSource());
    	
    	LabelPublishEntry labelPublishEntry = new LabelPublishEntry();
    	labelPublishEntry.setLabel(status.getText());
    	messagePublishEntry.setPayload(labelPublishEntry);
    	
        return messagePublishEntry;
    }


}
