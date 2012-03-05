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

package org.hubiquitus.hubiquitus4j.hubotpack.countTweets.service.impl;

import java.util.Date;

import org.hubiquitus.hapi.model.impl.ComplexPublishEntry;
import org.hubiquitus.hapi.model.impl.MessagePublishEntry;
import org.hubiquitus.hubiquitus4j.hubotpack.countTweets.model.countTweetsDataPublishEntry;
import org.hubiquitus.hubiquitus4j.hubotpack.countTweets.model.CountTweetsPayloadPublishEntry;
import org.hubiquitus.hubotsdk.service.impl.XmppBotConfiguration;



public class CountTweetsBotUtilsImpl {

	
	public static ComplexPublishEntry initComplexPublishEntry(XmppBotConfiguration xmppBotConfiguration, String nodeName) {
		ComplexPublishEntry complexPublishEntry = new ComplexPublishEntry();
		
		complexPublishEntry.generateConvid();
		complexPublishEntry.setTopic(nodeName);
		//complexPublishEntry.setPublishedDate(new Date());
		complexPublishEntry.setPublished(new Date());
		
		complexPublishEntry.setDomain(xmppBotConfiguration.getXmppHostAddress());
		complexPublishEntry.setHost(xmppBotConfiguration.getXmppHostAddress());
		complexPublishEntry.addParticipant(xmppBotConfiguration.getXmppUserName());
		
		MessagePublishEntry messagePublishEntry = new MessagePublishEntry();
		messagePublishEntry.generateMsgid();
		messagePublishEntry.setAuthor(xmppBotConfiguration.getXmppUserName());
		messagePublishEntry.setPublisher(nodeName);
		//messagePublishEntry.setPublishedDate(new Date());
		complexPublishEntry.setPublished(new Date());
		messagePublishEntry.setType(nodeName);
		messagePublishEntry.setPayload(new CountTweetsPayloadPublishEntry());
		
		complexPublishEntry.addMessage(messagePublishEntry);
		
		return complexPublishEntry;
	}

    public static countTweetsDataPublishEntry  lineToMessagePublishEntry(String line) {
      	countTweetsDataPublishEntry BarometreDataPublishEntry = new countTweetsDataPublishEntry();
        
        String[] datas = line.split("#");
        String channel = datas[0];
        String tweets = datas[1];
        String title = datas[2];
        String tweetspermin = datas[3];

        //System.out.println("line data = " + channel + "#" + tweets + "#" + title + "#" + tweetspermin);
        
        BarometreDataPublishEntry.setChannel(channel);
        BarometreDataPublishEntry.setTitle(title);
        BarometreDataPublishEntry.setTweets( Integer.valueOf(tweets));
        //BarometreDataPublishEntry.setTweetspermin(Integer.valueOf(tweetspermin));

        return BarometreDataPublishEntry;
    }


}
