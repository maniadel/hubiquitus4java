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
