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
package org.hubiquitus.hubots.feedbot.service.impl;

import org.hubiquitus.hapi.model.impl.MessagePublishEntry;
import org.hubiquitus.hubots.feedbot.model.impl.FeedPublishEntry;

import com.sun.syndication.feed.synd.SyndEntry;



/**
 * @author a.benkirane
 *
 */
public class FeedBotUtils {

	private static FeedPublishEntry payload;
	
	public static MessagePublishEntry syndEntryToAMPublishEntry(SyndEntry entry) {
		MessagePublishEntry messagePublishEntry = new MessagePublishEntry();
		payload = new FeedPublishEntry(entry.getTitle() + " " + entry.getDescription().getValue());
        
        messagePublishEntry.generateMsgid();
    	messagePublishEntry.setPublisher("FeedBot");
    	messagePublishEntry.setType("RSS");
    	messagePublishEntry.setAuthor(entry.getAuthor());
    	messagePublishEntry.setPublished(entry.getPublishedDate());
    	
    	messagePublishEntry.setPayload(payload);
    	        	
        return messagePublishEntry;
    }
	
}
