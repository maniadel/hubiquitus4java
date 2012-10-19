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

package org.hubiquitus.TwitterBot;


import org.apache.log4j.Logger;
import org.hubiquitus.hapi.exceptions.MissingAttrException;
import org.hubiquitus.hapi.hStructures.HGeo;
import org.hubiquitus.hapi.hStructures.HLocation;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.Hubot;
import org.hubiquitus.hubotsdk.adapters.HtwitterAdapter.HTweet;

public class TwitterBot extends Hubot  {


	private static Logger log = Logger.getLogger(TwitterBot.class);
	
	public static void main( String[] args )throws Exception{
		TwitterBot bot  = new TwitterBot();
		bot.start();
		Thread.sleep(15000);
		HMessage hmessage = bot.createTweetMessage("sunchenliang@twitter.com");
		bot.send(hmessage);
	}

	@Override
	protected void inProcessMessage(HMessage messageIncoming) {
		log.info(messageIncoming.toString());
		
	}

	private HMessage createTweetMessage(String destination){
		HTweet tweet = new HTweet();
		try {
			tweet.setText("Helle, I'm testing my twitter bot. 7777 à Paris!");
		} catch (MissingAttrException e) {
			log.info("message: ", e);
		}
		try {
			HMessage m = this.buildMessage(destination, "hTweet", tweet, null);
			HGeo pos = new HGeo(2.240426,48.841178);
			HLocation loc = new HLocation();
			loc.setPos(pos);
			m.setLocation(loc);
			return m;
		} catch (MissingAttrException e) {
			log.info("message: ", e);
		}
		return null;
	}


}
