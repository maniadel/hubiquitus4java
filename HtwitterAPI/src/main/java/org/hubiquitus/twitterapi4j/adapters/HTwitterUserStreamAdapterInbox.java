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

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.hubiquitus.hapi.exceptions.MissingAttrException;
import org.hubiquitus.hapi.hStructures.HGeo;
import org.hubiquitus.hapi.hStructures.HLocation;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.AdapterInbox;
import org.hubiquitus.hubotsdk.adapters.HtwitterAdapter.HTweet;
import org.hubiquitus.hubotsdk.adapters.HtwitterAdapter.HTweetAuthor;
import org.hubiquitus.twitter4j.stream.pub.HUserStream;
import org.hubiquitus.twitter4j.stream.pub.HUserStreamListner;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HTwitterUserStreamAdapterInbox extends AdapterInbox implements HUserStreamListner
{
	final Logger log = LoggerFactory.getLogger(HTwitterUserStreamAdapterInbox.class);

	private String consumerKey ;
	private String consumerSecret;
	private String twitterAccessToken;
	private String twitterAccessTokenSecret;
	private String proxyHost;
	private int proxyPort;
	
	private String langFilter;
	private String tags;	
	private String delimited;
	private String stallWarnings; 
	private String with;
	private String replies;
	private String locations;
	

	private HUserStream userStream;

	/**
	 * The map where the JSONObject's properties are kept.
	 */
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
				if (properties.has("proxyHost")) {
					this.proxyHost = properties.getString("proxyHost");
				}				
				if (properties.has("proxyPort")) {
					this.proxyPort = properties.getInt("proxyPort");
				}				
				if (properties.has("langFilter")) {
					this.langFilter = properties.getString("langFilter");
				}
				if (properties.has("tags")) {
					this.tags = properties.getString("tags");
				}
				if (properties.has("delimited")) {
					this.delimited = properties.getString("delimited");
				}
				if (properties.has("stallWarnings")) {
					this.stallWarnings = properties.getString("stallWarnings");
				}
				if (properties.has("with")) {
					this.with = properties.getString("with");
				}
				if (properties.has("replies")) {
					this.replies = properties.getString("replies");
				}
				if (properties.has("locations")) {
					this.locations = properties.getString("locations");
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
		 
		userStream = new HUserStream (
				proxyHost,  
				 proxyPort, 
		         tags,
		         delimited,
		         stallWarnings,
		         with, 
		         replies, 
		         locations,
		         consumerKey, 
		         consumerSecret, 
		         twitterAccessToken, 
		         twitterAccessTokenSecret);
		
		userStream.addListener(this);		
		userStream.start();
		log.info("Started.");  
	}

	@Override
	public void stop() {
		log.info("Stopping...");
		if (userStream != null)
			userStream.stop();
		log.info("Stopped.");	
	}

	public static java.util.Date getTwitterDate(String date) throws ParseException {

		final String TWITTER="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(TWITTER);
		sf.setLenient(true);
		return sf.parse(date);
	}

	/**
	 * Get an optional value associated with a key.
	 *
	 * @param key A key string.
	 * @return An object which is the value, or null if there is no value.
	 */
	public Object opt(String key) {
		return key == null ? null : this.map.get(key);
	}

	/**
	 * Determine if the value associated with the key is null or if there is
	 * no value.
	 *
	 * @param key A key string.
	 * @return true if there is no value associated with the key or if
	 *         the value is the JSONObject.NULL object.
	 */
	public boolean isNull(String key) {
		return JSONObject.NULL.equals(opt(key));
	}


	/**
	 * Function for transforming Tweet to HMessage
	 * @param tweet from Twitter API 
	 * @return HMessage of type hTweet
	 * @throws JSONException 
	 */
	private HMessage transformtweet(JSONObject tweet) throws JSONException{
		HMessage message = new HMessage();

		HTweet htweet = new HTweet();
		HTweetAuthor hauthortweet = new HTweetAuthor();

		//Construct the location 
		HLocation location = new HLocation();

		//Construct the Place
		if( !tweet.isNull("place") ){

			if("point".equalsIgnoreCase(  tweet.getJSONObject("place").getJSONObject("bounding_box").getString("type") )){				 
				HGeo geo = new HGeo(tweet.getJSONObject("place").getJSONObject("bounding_box").getJSONObject("coordinates")   ); 
				location.setPos(geo);
				message.setLocation(location);
			}	

			if(  !(tweet.getJSONObject("place").isNull("full_name"))     ){
				location.setAddr(tweet.getJSONObject("place").getString("full_name"));					
			}		
			if(!(tweet.getJSONObject("place").isNull("country_code") )){
				location.setCountryCode(tweet.getJSONObject("place").getString("country_code"));				
			}
			if((! ( tweet.getJSONObject("place").isNull("place_type"))) && ("city".equalsIgnoreCase(tweet.getJSONObject("place").getString("place_type")))){
				location.setCity( tweet.getJSONObject("place").getString("name") );					
			}
		}		

		// Init The date
		try {
			DateTime createdAt = new DateTime(getTwitterDate(tweet.getString("created_at")) );
			DateTime createdAtAuthor = new DateTime(getTwitterDate(tweet.getJSONObject("user").getString("created_at")));
			message.setPublished(new DateTime(createdAt));
			hauthortweet.setCreatedAt(createdAtAuthor);

		} catch (ParseException e2) {
			log.error("ERROR IN DATE PARSE :",e2);
		}

		//Construct the Authortweet JSONObject

		hauthortweet.setStatus      (tweet.getJSONObject("user").getInt   ("statuses_count"));		
		hauthortweet.setFollowers   (tweet.getJSONObject("user").getInt   ("followers_count"));
		hauthortweet.setFriends     (tweet.getJSONObject("user").getInt   ("friends_count"));
		hauthortweet.setLocation    (tweet.getJSONObject("user").getString("location"));
		hauthortweet.setDescription (tweet.getJSONObject("user").getString("description"));
		hauthortweet.setProfileImg  (tweet.getJSONObject("user").getString("profile_image_url_https"));

		if(! "null".equalsIgnoreCase(tweet.getJSONObject("user").getString("url"))){
			try {
				hauthortweet.setUrl( new URL(tweet.getJSONObject("user").getString("url"))  );
			} catch (MalformedURLException e1) {			
				log.debug("MAL FORMED URL  :"+e1);			
			}			
		}

		hauthortweet.setLang(   tweet.getJSONObject("user").getString("lang"));
		hauthortweet.setListeds(tweet.getJSONObject("user").getInt("listed_count"));
		hauthortweet.setGeo( tweet.getJSONObject("user").getBoolean("geo_enabled")); 
		hauthortweet.setVerified(tweet.getJSONObject("user").getBoolean("verified"));
		hauthortweet.setName(tweet.getJSONObject("user").getString("name")); 	


		//Construct the tweet JSONObject		
		htweet.setId(tweet.getLong("id"));
		try {
			htweet.setSource(tweet.getString("source"));
			htweet.setText(tweet.getString("text"));
			htweet.setAuthor(hauthortweet); //TODO see this 
		} catch (MissingAttrException e) {
			log.error("mssage: ", e);
		}

		message.setPayload(htweet);
		message.setType("hTweet");
		message.setAuthor(tweet.getJSONObject("user").getString("screen_name")+ "@twitter.com");		

		if (log.isDebugEnabled()) {
			log.debug("tweet("+tweet+") -> hMessage :"+message);
		}		
		return message;
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
				HMessage message = transformtweet(status);
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
