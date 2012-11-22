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

package org.hubiquitus.hubotsdk.adapters;

import org.hubiquitus.hapi.exceptions.MissingAttrException;
import org.hubiquitus.hapi.hStructures.HGeo;
import org.hubiquitus.hapi.hStructures.HLocation;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.AdapterInbox;
import org.hubiquitus.hubotsdk.adapters.HtwitterAdapter.HTweet;
import org.hubiquitus.hubotsdk.adapters.HtwitterAdapter.HTweetAuthor;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * the HTwitterAdapterInbox allow you to fetch some tweets from the
 * twitter streaming api
 */
public class HTwitterAdapterInbox extends AdapterInbox{

	final Logger log = LoggerFactory.getLogger(HTwitterAdapterInbox.class);

	private String consumerKey ;
	private String consumerSecret;
	private String twitterAccessToken;
	private String twitterAccessTokenSecret;
	private String langFilter;
	private String tags;
	private String content = "complete";

	protected TwitterStream twitterStream;

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
			if	(properties.has("content")){
				this.content = properties.getString("content");
			}
			}catch(Exception e){
				log.debug("message: ",e);
			}
		}
		log.info("Properties Initialized : " + this);

	}

	/**
	 * Function to start Streaming
	 */
	public void start() {
		log.info("Starting...");
		stream();
		log.info("Started.");
	}

	/**
	 * Function to stop Streaming
	 */
	public void stop() {
		log.info("Stopping...");
		twitterStream.shutdown();
		log.info("Stopped.");
	}

	/**
	 * Function for tweet Streaming
	 */
	private void stream() {
		/**
		 * Configuration for access to twitter account
		 */
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setUseSSL(true)
		  .setOAuthConsumerKey(consumerKey)
		  .setOAuthConsumerSecret(consumerSecret)
		  .setOAuthAccessToken(twitterAccessToken)
		  .setOAuthAccessTokenSecret(twitterAccessTokenSecret);

		//Instantiation of tweet stream
		twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		StatusListener listener = new StatusListener() {
			public void onStatus(Status tweet) {
				String lang = tweet.getUser().getLang();
				//Set the filter for the language
				if ((langFilter == null) || (lang != null && lang.equalsIgnoreCase(langFilter))) {
					HMessage message = transformtweet(tweet);
					put(message);
				}
			}

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}

			public void onScrubGeo(long userId, long upToStatusId) {}

			public void onException(Exception ex) {
				log.info("message: ", ex);
			}
		};

		FilterQuery fq = new FilterQuery();
		fq.track(tags.split(","));
		twitterStream.addListener(listener);
		twitterStream.filter(fq);  
	}

	/**
	 * Function for transforming Tweet to HMessage
	 * @param tweet from twitter4j
	 * @return HMessage of type hTweet
	 */
	private HMessage transformtweet(Status tweet){
		HMessage message = new HMessage();

		HTweet htweet = new HTweet();
		if(("complete").equalsIgnoreCase(content)){
			HTweetAuthor hauthortweet = new HTweetAuthor();
	
			//Construct the location 
			HLocation location = new HLocation();
			if(tweet.getGeoLocation() != null ) {
				HGeo geo = new HGeo(tweet.getGeoLocation().getLatitude(), tweet.getGeoLocation().getLongitude());
				location.setPos(geo);
				message.setLocation(location);
			}
	
			//Construct the Place
			if(tweet.getPlace()!= null){
				if(tweet.getPlace().getStreetAddress()!= null){
					location.setAddr(tweet.getPlace().getStreetAddress());
				}
				if(tweet.getPlace().getCountryCode()!= null){
					location.setCountryCode(tweet.getPlace().getCountryCode());
				}
				if((tweet.getPlace().getPlaceType()!= null) && ("city".equalsIgnoreCase(tweet.getPlace().getPlaceType()))){
					location.setCity(tweet.getPlace().getName());
				}
			}
	
			//Construct the Authortweet JSONObject
			hauthortweet.setStatus(tweet.getUser().getStatusesCount());
			hauthortweet.setFollowers(tweet.getUser().getFollowersCount());
			hauthortweet.setFriends(tweet.getUser().getFriendsCount());
			hauthortweet.setLocation(tweet.getUser().getLocation());
			hauthortweet.setDescription(tweet.getUser().getDescription());		
			hauthortweet.setProfileImg(tweet.getUser().getProfileImageURL().toString());
			hauthortweet.setUrl(tweet.getUser().getURL());
            hauthortweet.setCreatedAt(new DateTime(tweet.getUser().getCreatedAt()));
			hauthortweet.setLang(tweet.getUser().getLang());
			hauthortweet.setListeds(tweet.getUser().getListedCount());
			hauthortweet.setGeo(tweet.getUser().isGeoEnabled());
			hauthortweet.setVerified(tweet.getUser().isVerified());
			hauthortweet.setName(tweet.getUser().getName());

			//Construct the tweet JSONObject		
			htweet.setId(tweet.getId());
			try {
				htweet.setSource(tweet.getSource());
				htweet.setAuthor(hauthortweet);
			} catch (MissingAttrException e) {
				log.error("message: ", e);
			}
		
		}
        // now manage the minimal list of attributes to get from twitter
		try {
			htweet.setText(tweet.getText());
		} catch (MissingAttrException e) {
			log.error("message: ", e);
		}
		message.setPayload(htweet);
		message.setType("hTweet");
		message.setAuthor(tweet.getUser().getScreenName() + "@twitter.com");
        DateTime createdAt = new DateTime(tweet.getCreatedAt());
        message.setPublished(new DateTime(createdAt));

        if (log.isDebugEnabled()) {
			log.debug("tweet("+tweet+") -> hMessage :"+message);
		}

		return message;
	}


	/* Getters & Setters */

	/*public String getConsumerKey() {
		return consumerKey;
	}*/


	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	/*public String getTags() {
		return tags;
	}*/

	public void setTags(String tags) {
		this.tags = tags;
		if(log.isDebugEnabled()){
			log.debug("Tags updated : " +tags);
		}
	}


	/*
	public String getConsumerSecret() {
		return consumerSecret;
	}*/

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	/*public String getTwitterAccessToken() {
		return twitterAccessToken;
	}*/

	public void setTwitterAccessToken(String twitterAccessToken) {
		this.twitterAccessToken = twitterAccessToken;
	}

	/*public String getTwitterAccessTokenSecret() {
		return twitterAccessTokenSecret;
	}*/

	public void setTwitterAccessTokenSecret(String twitterAccessTokenSecret) {
		this.twitterAccessTokenSecret = twitterAccessTokenSecret;
	}

	/*public String getLangFilter() {
		return langFilter;
	}*/

	public void setLangFilter(String langFilter) {
		this.langFilter = langFilter;
	}
	
	public void setContent(String content){
		this.content = content;
	}

	@Override
	public String toString() {
		return "HTwitterAdapterInbox [consumerKey=" + consumerKey
				+ ", consumerSecret=" + consumerSecret
				+ ", twitterAccessToken=" + twitterAccessToken
				+ ", twitterAccessTokenSecret=" + twitterAccessTokenSecret
				+ ", langFilter=" + langFilter + ", tags=" + tags 
				+ ", content=" + content + "]";
	}



}
