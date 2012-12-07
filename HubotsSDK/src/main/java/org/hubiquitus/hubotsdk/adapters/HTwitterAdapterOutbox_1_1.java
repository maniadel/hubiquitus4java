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

package org.hubiquitus.hubotsdk.adapters;

import org.hubiquitus.hapi.client.HMessageDelegate;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.AdapterOutbox;
import org.hubiquitus.hubotsdk.adapters.HtwitterAdapter.HTweet;
import org.hubiquitus.twitter4j_1_1.stream.HStatusUpdate;


import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





public class HTwitterAdapterOutbox_1_1 extends AdapterOutbox{

	final Logger log = LoggerFactory.getLogger(HTwitterAdapterOutbox_1_1.class);

	private String consumerKey ;
	private String consumerSecret;
	private String twitterAccessToken;
	private String twitterAccessTokenSecret;
	private String proxyHost;
	private int proxyPort;
	
	
	HStatusUpdate statusUpdate;

	@Override
	public void setProperties(JSONObject properties) {
		if(properties != null){
			try{
				if(properties.has("consumerKey")){
					this.consumerKey = properties.getString("consumerKey");
				}
				if (properties.has("consumerSecret")) {
					this.consumerSecret = properties.getString("consumerSecret");
				}
				if (properties.has("twitterAccessToken")) {
					this.twitterAccessToken = properties.getString("twitterAccessToken");
				}
				if (properties.has("twitterAccessTokenSecret")) {
					this.twitterAccessTokenSecret = properties.getString("twitterAccessTokenSecret");
				}

				if (properties.has("proxyHost")) {
					this.proxyHost = properties.getString("proxyHost");
				}				
				if (properties.has("proxyPort")) {
					this.proxyPort = properties.getInt("proxyPort");
				}
			}catch(JSONException e){
				log.error("ERROR ON CONFIG FILE 'JSON NOT VALID', ERROR TYPE : ",e);
			}
		}
		log.info("Properties Initialized : " + this);		
	}



	@Override
	public void sendMessage(HMessage message, HMessageDelegate callback) {
		try {
			String status = transformTweet(message);
			if (status != null) {
				statusUpdate.postTweet(status);
			}
		} catch (Exception e) {
			log.error("message: ", e);
		}
	}

	public String transformTweet(HMessage message) {
		String result = null;
		if("htweet".equalsIgnoreCase(message.getType())){
			
			try {
				HTweet hTweet = new HTweet(message.getPayloadAsJSONObject());
				String status = hTweet.getText();
				String screenNameDest = "@" + message.getActor().split("@")[0];
				if ((!actor.equals(message.getActor())) && (!status.startsWith(screenNameDest))){
					//  in this case, the tweet must be send in public mode to the current actor
					//  If the hMessage doesn’t begin with the “@screenName”,
					//	the outbox adapter will add it automatically.
					status = screenNameDest + " " + status;
				}
				// check the limit;
				if (status.length() > 140) {
					status = status.substring(0, 136) + "...";
				}
				result = status;
			
			} catch (JSONException e) {
				log.error("Can not transform a hTweet in a status format: ",e);
			}
		}

		if (result == null) {
			log.warn("Strange, I receive an hMessage of type='"+message.getType()+"' instead of an 'hTweet'");
		}
		return result;
	}


	@Override
	public void start() {
		
		log.info("Twitter adapter outbox '"+actor+"' starting...");
		
		statusUpdate  = new HStatusUpdate(
				proxyHost, 
				proxyPort, 
				consumerKey, 
				consumerSecret, 
				twitterAccessToken, 
				twitterAccessTokenSecret
				);
		
		log.info("  Twitter adapter outbox '"+actor+"' started.");	

	}

	@Override
	public void stop() {
		//Not Supported 
	}	

}
