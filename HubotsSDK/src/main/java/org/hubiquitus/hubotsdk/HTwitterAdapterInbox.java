package org.hubiquitus.hubotsdk.adapters;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

<<<<<<< HEAD
import org.hubiquitus.hapi.hStructures.HLocation;
import org.hubiquitus.hapi.hStructures.HMessage;
=======
import org.apache.log4j.Logger;
>>>>>>> 0ef85ab68d054bc40b02ca37141c72a68e50c861
import org.hubiquitus.hubotsdk.AdapterInbox;
import org.hubiquitus.hubotsdk.adapters.HtwitterAdapter.HTweet;


import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
/**
 * 
 * @author Hubiquitus
 *
 */
public class HTwitterAdapterInbox extends AdapterInbox{

	private static Logger logger = Logger.getLogger(HChannelAdapterOutbox.class);
	
	private String consumerKey ;
	private String consumerSecret;
	private String twitterAccessToken;
	private String twitterAccessTokenSecret;
	private String host;
	private String langFilter;
	private int port;

	private String tags;

	protected TwitterStream twitterStream;

	@Override
	public void setProperties(Map<String, String> params) {
		if(params.get("consumerKey") != null) 
			setConsumerKey(params.get("consumerKey"));
		if(params.get("consumerSecret") != null) 
			setConsumerSecret(params.get("consumerSecret"));
		if(params.get("twitterAccessToken") != null) 
			setTwitterAccessToken(params.get("twitterAccessToken"));
		if(params.get("twitterAccessTokenSecret") != null) 
			setTwitterAccessTokenSecret(params.get("twitterAccessTokenSecret"));
		if(params.get("tags") != null) 
			setTags(params.get("tags"));
		if(params.get("host") != null) 
			setHost(params.get("host"));
		if(params.get("lang") != null) 
			setLangFilter(params.get("lang"));
		if(params != null && params.containsKey("port")) {
			this.port = Integer.parseInt(params.get("port"));

		}
	}

	/**
	 * Function to start Streaming
	 */
	public void start() {
		stream();
	}

	/**
	 * Function to stop Streaming
	 */
	public void stop() {
		twitterStream.shutdown();
	}

	public HTwitterAdapterInbox() {
		super();
	}

<<<<<<< HEAD
	/**
	 * Function for tweet Streaming
	 */
=======
>>>>>>> 0ef85ab68d054bc40b02ca37141c72a68e50c861
	public void stream() {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setUseSSL(false); 
		cb.setOAuthConsumerKey(consumerKey);
		cb.setOAuthConsumerSecret(consumerSecret);
		cb.setOAuthAccessToken(twitterAccessToken);
		cb.setOAuthAccessTokenSecret(twitterAccessTokenSecret);
		twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		StatusListener listener = new StatusListener() {

<<<<<<< HEAD
			public void onStatus(Status tweet) {
				String lang = tweet.getUser().getLang();
				if( lang != null && lang.equalsIgnoreCase(langFilter)) {
					HMessage message = transformtweet(tweet);
					put(message);
				}
=======
			public void onStatus(Status status) {
				logger.info("@" + status.getUser().getScreenName() + " - " + status.getText());
>>>>>>> 0ef85ab68d054bc40b02ca37141c72a68e50c861
			}
			
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			}

			public void onScrubGeo(long userId, long upToStatusId) {
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}
		};

		FilterQuery fq = new FilterQuery();
		fq.track(tags.split("#"));


		twitterStream.addListener(listener);
		twitterStream.filter(fq);  
	}
	
	/**
	 * Function for transforming Tweet to HMessage
	 * @param tweet
	 * @return
	 */
	private HMessage transformtweet(Status tweet){
		HMessage message = new HMessage();
		HTweet htweet = new HTweet();
		message.setAuthor(tweet.getUser().getName());

		if(tweet.getGeoLocation() != null ) {
			HLocation location = new HLocation();
			location.setLat(tweet.getGeoLocation().getLatitude());
			location.setLng(tweet.getGeoLocation().getLongitude());
			message.setLocation(location);
		}
		
		tweet.getCreatedAt().getTime();
		Calendar createdAt = new GregorianCalendar();
		createdAt.setTime(tweet.getCreatedAt());
		htweet.setCreatedAt(createdAt);
		htweet.setFriendsCount(tweet.getUser().getFriendsCount());
		htweet.setIdTweet(tweet.getId());
		htweet.setInReplyToScreenName(tweet.getInReplyToScreenName());
		htweet.setLocation(tweet.getUser().getLocation());
		htweet.setRetweetcount(tweet.getRetweetCount());
		htweet.setScreenName(tweet.getUser().getScreenName());
		htweet.setSource(tweet.getSource());
		htweet.setTweetText(tweet.getText());
		htweet.setLang(tweet.getUser().getLang());
		htweet.setStatus(tweet.getUser().getStatus());
		message.setPayload(htweet);
		
		return message;
	}
	

	/* Getters & Setters */
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getTwitterAccessToken() {
		return twitterAccessToken;
	}

	public void setTwitterAccessToken(String twitterAccessToken) {
		this.twitterAccessToken = twitterAccessToken;
	}

	public String getTwitterAccessTokenSecret() {
		return twitterAccessTokenSecret;
	}

	public void setTwitterAccessTokenSecret(String twitterAccessTokenSecret) {
		this.twitterAccessTokenSecret = twitterAccessTokenSecret;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	
	public String getLangFilter() {
		return langFilter;
	}

	public void setLangFilter(String langFilter) {
		this.langFilter = langFilter;
	}

}
