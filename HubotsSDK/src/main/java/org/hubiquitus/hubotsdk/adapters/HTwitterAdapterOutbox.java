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
package org.hubiquitus.hubotsdk.adapters;

import org.hubiquitus.hapi.client.HMessageDelegate;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.AdapterOutbox;
import org.hubiquitus.hubotsdk.adapters.HtwitterAdapter.HTweet;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.GeoLocation;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class HTwitterAdapterOutbox extends AdapterOutbox {

	final Logger log = LoggerFactory.getLogger(HTwitterAdapterInbox.class);

	private String consumerKey ;
	private String consumerSecret;
	private String twitterAccessToken;
	private String twitterAccessTokenSecret;

	protected Twitter twitter;

	@Override
	public void setProperties(JSONObject properties) {
		if(properties != null){
			try {
				if (properties.has("consumerKey")) {
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
			} catch (Exception e) {
				log.debug("message: ", e);
			}
		}
		log.info("Properties Initialized : " + this);

	}
	
	@Override
	public void sendMessage(HMessage message, HMessageDelegate callback) {
		StatusUpdate tweet;
		tweet = transformTweet(message);
		sendToTwitter(tweet);
	}
	
	private StatusUpdate transformTweet(HMessage message) {
		if("htweet".equalsIgnoreCase(message.getType())){
			HTweet hTweet;
			try {
				hTweet = new HTweet(message.getPayloadAsJSONObject());
				String status = hTweet.getText();
				if(!actor.equals(message.getActor())){
					//If the hMessage doesn’t begin with the “@screenName”,
					//	the outbox adapter will add it automatically.
					if(!status.startsWith("@")){
						status = "@" + message.getActor().split("@")[0] + " " + status;
					}
				}
				StatusUpdate tweet = new StatusUpdate(status);
				// add the other infos for the tweet.
				if(message.getLocation() != null){
					//add the location info in the tweet.
					GeoLocation location = new GeoLocation(message.getLocation().getPos().getLat(), message.getLocation().getPos().getLng());
					tweet.setLocation(location);
				}
				//TODO if there are others infos to add.
				return tweet;
			} catch (JSONException e) {
				log.error("message: ",e);
			}
			
		}
		return null;
	}

	private void sendToTwitter(StatusUpdate tweet){
		 ConfigurationBuilder cb = new ConfigurationBuilder();
	        cb.setDebugEnabled(true).setUseSSL(true)
	          .setOAuthConsumerKey(consumerKey)
	          .setOAuthConsumerSecret(consumerSecret)
	          .setOAuthAccessToken(twitterAccessToken)
	          .setOAuthAccessTokenSecret(twitterAccessTokenSecret);
	        
	        TwitterFactory tf = new TwitterFactory(cb.build());
	        twitter = tf.getInstance();
			
	        try {
				twitter.updateStatus(tweet);
			} catch (Exception e) {
				log.error("message: ", e);
			}
	        
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
