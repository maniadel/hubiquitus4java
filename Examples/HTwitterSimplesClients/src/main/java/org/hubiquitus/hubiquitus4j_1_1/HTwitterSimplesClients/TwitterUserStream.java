/**
 Copyright (c) Novedia Group 2012.
 This file is part of Hubiquitus
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 of the Software, and to permit persons to whom the Software is furnished to do so,
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies
 or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 You should have received a copy of the MIT License along with Hubiquitus.
 If not, see <http://opensource.org/licenses/mit-license.php>. 
 
*/

package org.hubiquitus.hubiquitus4j_1_1.HTwitterSimplesClients;

import org.hubiquitus.twitter4j_1_1.stream.HUserStream;
import org.hubiquitus.twitter4j_1_1.stream.HUserStreamListner;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterUserStream extends HUserStream implements HUserStreamListner{

	final static Logger log = LoggerFactory.getLogger(TwitterStream.class);

	private static String consumerKey             = "yourConsumerKey";
	private static String consumerSecret          = "yourConsumerSecret";
	private static String twitterAccessToken      = "yourAccessToken";
	private static String twitterAccessTokenSecret= "yourAccessTokenSecret";	

	private static String tags="tag1,tag2";

	private static String proxyHost="0.0.0.0"; //Optional
	private static int    proxyPort= 0000;     //Optional

	private static String delimited="all";                         //Optional
	private static String stallWarnings="true";                    //Optional
	private static String with="followings";                       //Optional 
	private static String replies="all";                           //Optional
	private static String locations="-122.75,36.8,-121.75,37.8";   //Optional
	private static String count="130000";                          //Optional


	
	public TwitterUserStream(String proxyHost, int proxyPort, String tags,
			String delimited, String stallWarnings, String with,
			String replies, String locations, String count, String consumerKey,
			String consumerSecret, String token, String tokenSecret) {
		super(proxyHost, proxyPort, tags, delimited, stallWarnings, with, replies,
				locations, count, consumerKey, consumerSecret, token, tokenSecret);		
	}




	public static void main(String[] args) {

		log.info("Start stream ...");
		TwitterUserStream stream = new TwitterUserStream(
				proxyHost, 
				proxyPort, 
				tags,						
				delimited,
				stallWarnings, 
				with,
				replies, 
				locations, 
				count, 
				consumerKey,						
				consumerSecret, 
				twitterAccessToken, 
				twitterAccessTokenSecret);
		stream.addListener(stream);		
		stream.start();
		log.info("Started.");
	}

	@Override
	public void onStatus(JSONObject status) {		
		log.info("------------------->  Recived Tweet :"+status.toString());
	}

	@Override
	public void onOtherMessage(JSONObject message) {
		log.info("------------------->  Recived message :"+message.toString());		
	}

	@Override
	public void onStallWarning(JSONObject stallWarning) {
		log.info("------------------->  Recived stallWarning :"+stallWarning.toString());		
	}

	@Override
	public void onStatusDeletionNotices(JSONObject delete) {
		log.info("------------------->  Recived delete :"+delete.toString());
	}

	@Override
	public void onLocationDeletionNotices(JSONObject scrubGeo) {
		log.info("------------------->  Recived scrubGeo :"+scrubGeo.toString());		
	}

	@Override
	public void onLimitNotices(JSONObject limit) {
		log.info("------------------->  Recived limit :"+limit.toString());		
	}

	@Override
	public void onStatusWithheld(JSONObject statusWithheld) {
		log.info("------------------->  Recived statusWithheld :"+statusWithheld.toString());
	}

	@Override
	public void onUserWithheld(JSONObject userWithheld) {
		log.info("------------------->  Recived userWithheld :"+userWithheld.toString());
	}

	@Override
	public void onDisconnectMessages(JSONObject disconnect) {
		log.info("------------------->  Recived disconnect :"+disconnect.toString());
		
	}

}
