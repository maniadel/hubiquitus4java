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

import java.util.Map;

import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.AdapterInbox;
import org.hubiquitus.twitter4j.stream.pub.HStream;
import org.hubiquitus.twitter4j.stream.pub.HStreamListner;
import org.hubiquitus.twitter4j.stream.pub.TweetToHMessage;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HTwitterAdapterInbox extends AdapterInbox implements HStreamListner
{
	final Logger log = LoggerFactory.getLogger(HTwitterAdapterInbox.class);

	private String consumerKey ;
	private String consumerSecret;
	private String twitterAccessToken;
	private String twitterAccessTokenSecret;
	private String langFilter;
	private String tags;
	private String proxyHost;
	private int    proxyPort;

	private HStream stream;
	private TweetToHMessage tweetToHMessage = new TweetToHMessage();
	/**
	 * The map where the JSONObject's properties are kept.
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	private Map map;

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
				if (properties.has("langFilter")) {
					this.langFilter = properties.getString("langFilter");
				}				
				if (properties.has("tags")) {
					this.tags = properties.getString("tags");
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

	/**
	 * Function to start Streaming
	 */
	@Override
	public void start() {
		log.info("Starting...");		
		stream = new HStream (
				proxyHost, 
				proxyPort, 
				tags, 
				consumerKey, 
				consumerSecret, 
				twitterAccessToken, 
				twitterAccessTokenSecret);
		stream.addListener(this);		
		stream.start();
		log.info("Started.");
	}

	@Override
	public void stop() {
		log.info("Stopping...");
		if (stream != null)
			stream.stop();
		log.info("Stopped.");	
	}

	/*************************************************************** */

	@Override
	public String toString() {
		return "HTwitterAdapterInbox [consumerKey=" + consumerKey
				+ ", consumerSecret="      + consumerSecret
				+ ", twitterAccessToken="  + twitterAccessToken
				+ ", twitterAccessTokenSecret=" + twitterAccessTokenSecret
				+ ", proxyHost="   + proxyHost
				+ ", proxyPort="   + proxyPort
				+ ", langFilter="  + langFilter + ", tags=" + tags + "]";
	}

	/*************************************************************** */

	public void onStatus(JSONObject status) {		
		log.info("received status: " + status);
		try {
			String lang = (status.getJSONObject("user")).getString("lang");
			log.debug("My Lang "+lang);
			if ((langFilter == null)|| (lang != null && lang.equalsIgnoreCase(langFilter))) {				
				HMessage message = tweetToHMessage.transformtweet(status);
				put(message);
			}

		} catch (JSONException e) {
			log.debug(" Error on get lang :",e);
			e.printStackTrace();
		}		
	}



	public void onStallWarning(JSONObject stallWarning) {
		log.warn("received stallWarning: " + stallWarning);
	}
	@Override
	public void onStatusDeletionNotices(JSONObject delete) {
		log.warn("received DeletionNotices : " + delete);		
	}	

	@Override
	public void onLocationDeletionNotices(JSONObject scrubGeo) {
		log.info("received location deletion notices message: " + scrubGeo);
	}
	@Override
	public void onLimitNotices(JSONObject limit) {
		log.info("received User limit notices message: " + limit);

	}

	@Override
	public void onStatusWithheld(JSONObject statusWithheld) {
		log.info("received User status Withheld message: " + statusWithheld);
	}

	@Override
	public void onUserWithheld(JSONObject userWithheld) {
		log.info("received User Withheld message: " + userWithheld);

	}

	@Override
	public void onDisconnectMessages(JSONObject disconnect) {
		log.info("received other discounnect message: " + disconnect);

	}
	public void onOtherMessage(JSONObject message) {
		log.info("received other message: " + message);
	}

}
