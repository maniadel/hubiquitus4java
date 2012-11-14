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

package org.hubiquitus.twitterapi4j.adapters;

import org.hubiquitus.hapi.client.HMessageDelegate;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.AdapterOutbox;
import org.hubiquitus.hubotsdk.adapters.HtwitterAdapter.HTweet;
import org.hubiquitus.twitter4j.stream.pub.HStatusUpdate;
import org.hubiquitus.twitter4j.stream.pub.HStream;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





public class HTwitterAdapterOutbox extends AdapterOutbox{

	final Logger log = LoggerFactory.getLogger(HTwitterAdapterOutbox.class);

	private String consumerKey ;
	private String consumerSecret;
	private String twitterAccessToken;
	private String twitterAccessTokenSecret;
	private String proxyHost;
	private int proxyPort;
	private String status;
	
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
				statusUpdate.postTweet();
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
				twitterAccessTokenSecret,
				status
				);
		//statusUpdate.addListener(this);		
		//int code = statusUpdate.postTweet();
		//log.info("  RECIVED CODE FROM :"+code);
		log.info("  Twitter adapter outbox '"+actor+"' started.");
		

	}

	@Override
	public void stop() {
		//Not Supported 

	}	

}
