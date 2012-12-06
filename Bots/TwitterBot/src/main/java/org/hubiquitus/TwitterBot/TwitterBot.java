
/*
 * Copyright (c) Novedia Group 2012.
 *
 *    This file is part of Hubiquitus
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy
 *    of this software and associated documentation files (the "Software"), to deal
 *    in the Software without restriction, including without limitation the rights
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *    of the Software, and to permit persons to whom the Software is furnished to do so,
 *    subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies
 *    or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *    INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *    PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 *    FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 *    ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *    You should have received a copy of the MIT License along with Hubiquitus.
 *    If not, see <http://opensource.org/licenses/mit-license.php>.
 */


package org.hubiquitus.TwitterBot;


import org.apache.log4j.Logger;
import org.hubiquitus.hapi.client.HClient;
import org.hubiquitus.hapi.exceptions.MissingAttrException;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.Hubot;
import org.hubiquitus.hubotsdk.adapters.HtwitterAdapter.HTweet;
import org.joda.time.DateTime;
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
		log.info("-------[twitterBot] Recived message :"+messageIncoming.toString());
		
		if (messageIncoming.getAuthor().startsWith(screenName)) {
			log.info(" THE AUTHOR IS :   "+messageIncoming.getAuthor().toString());
			
			send(createHelloTweet(screenName + "@twitter.com", "["+new DateTime()+"]  :"+"1.  Best wishes from Hubiquitus Team"));
			send(createHelloTweet(screenName + "@twitter.com", "@" + screenName + "["+new DateTime()+"]  :"+" 2.  Best wishes from Hubiquitus Team "));
			send(createHelloTweet("twitterOutbox@twitter.com", "["+new DateTime()+"]  :"+"3.  Best wishes from Hubiquitus Team") );
			send(createHelloTweet("twitterOutbox@twitter.com", "["+new DateTime()+"]  :"+"4.  Hello world ! v4 this message should be trunked 0123456789abcdefghijklmnopqrstuvwxyz 0123456789abcdefghijklmnopqrstuvwxyz 0123456789abcdefghijklmnopqrstuvwxyz 0123456789abcdefghijklmnopqrstuvwxyz"));
			
			send(createHelloTweet("u2@localhost", "5.  Hello ! v5, you must open a hClient with u2@localhost to get this message"));
            send(createHelloTweet("u1@localhost", "6.  Hello ! v6 should not be received... see Adapter Inbox behavior"));
		}else{
			try {
				String authorName = messageIncoming.getPayloadAsJSONObject().getJSONObject("author").getString("name");
				send(createHelloTweet(messageIncoming.getAuthor().toString() + "@twitter.com", "["+new DateTime()+"]    :"+"1.  Thank you "+ authorName +"!    Best wishes from Hubiquitus Team !! \n \n \n Sended from  "+"@"+screenName));
				
			} catch (JSONException e) {
				log.error(" Error ! can't get Author name ( "+e);
			}
			
		}		
	}
	
	private HMessage createHelloTweet(String actor, String status){
		HMessage msg = new HMessage();
		try {
            HTweet tweet = new HTweet();
			tweet.setText(status);
			msg = this.buildMessage(actor, "hTweet", tweet, null);
			
			return  msg;
		} catch (MissingAttrException e) {
			log.error("message: ", e);
		}
		return msg;
	}
	
}
