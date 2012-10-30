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
import org.hubiquitus.hapi.client.HClient;
import org.hubiquitus.hapi.exceptions.MissingAttrException;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.Hubot;
import org.hubiquitus.hubotsdk.adapters.HtwitterAdapter.HTweet;
import org.json.JSONException;

public class TwitterBot extends Hubot  {


	private static Logger log = Logger.getLogger(TwitterBot.class);
	private String screenName;
	
	public static void main( String[] args )throws Exception{
		TwitterBot bot  = new TwitterBot();
		bot.start();
	}
	
	@Override
	protected void init(HClient hClient) {
		super.init(hClient);
		try {
			this.screenName = this.getProperties().getString("screenName");
		} catch (JSONException e) {
			log.error("can not get screenName : " + e);
		}
	}

	@Override
	protected void inProcessMessage(HMessage messageIncoming) {
		log.info(messageIncoming.toString());
		if (messageIncoming.getAuthor().startsWith(screenName)) {
            send(createHelloTweet(screenName + "@twitter.com", "Hello! v1"));
            send(createHelloTweet(screenName + "@twitter.com", "@" + screenName + " Hello! v2"));
            send(createHelloTweet("twitterOutbox@twitter.com", "Hello world ! v3"));
            send(createHelloTweet("twitterOutbox@twitter.com", "Hello world ! v4 this message should be trunked fjdkqlfjdlsqflsq fdjfsdjfklqs cdjsqklfdjsqlfsq fjdsqfjsdlfjsqkl jdlsqfdsjklf sdqolqjvdskqlf jsdqklfj dsljfsdklqf jsqlfjsqdljfdslqjf dsqlfsq jlfjsqlfjsqlkfjsql"));
            send(createHelloTweet("u2@localhost", "Hello ! v5, you must open a hClient with u2@localhost to get this message"));
            send(createHelloTweet("u1@localhost", "Hello ! v6 should not be received... see Adapter Inbox behavior"));
        }
	}
    
	
	private HMessage createHelloTweet(String actor, String status){
		try {
            HTweet tweet = new HTweet();
			tweet.setText(status);
            return this.buildMessage(actor, "hTweet", tweet, null);
		} catch (MissingAttrException e) {
			log.error("message: ", e);
		}
		return null;
	}


}
