package org.hubiquitus.hubotsdk.adapters;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import org.hubiquitus.hapi.hStructures.HLocation;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.apache.log4j.Logger;
import org.hubiquitus.hubotsdk.AdapterInbox;
import org.hubiquitus.hubotsdk.adapters.HtwitterAdapter.HAuthorTweet;
import org.hubiquitus.hubotsdk.adapters.HtwitterAdapter.HTweet;


import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class HTwitterAdapterInbox extends AdapterInbox{

	private static Logger log = Logger.getLogger(HTwitterAdapterInbox.class);

	private String consumerKey ;
	private String consumerSecret;
	private String twitterAccessToken;
	private String twitterAccessTokenSecret;
	private String langFilter;
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
		if(params.get("lang") != null) 
			setLangFilter(params.get("lang"));

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

	public HTwitterAdapterInbox() {
		super();
	}

	/**
	 * Function for tweet Streaming
	 */
	private void stream() {
		/**
		 * Configuration for access to twitter account
		 */
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setUseSSL(false); 
		cb.setOAuthConsumerKey(consumerKey);
		cb.setOAuthConsumerSecret(consumerSecret);
		cb.setOAuthAccessToken(twitterAccessToken);
		cb.setOAuthAccessTokenSecret(twitterAccessTokenSecret);

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
				ex.printStackTrace();
			}
		};

		FilterQuery fq = new FilterQuery();
		fq.track(tags.split(","));

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
		HAuthorTweet hauthortweet = new HAuthorTweet();

		//Construct the location 
		HLocation location = new HLocation();
		if(tweet.getGeoLocation() != null ) {
			location.setLat(tweet.getGeoLocation().getLatitude());
			location.setLng(tweet.getGeoLocation().getLongitude());
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

		// Init The date
		tweet.getCreatedAt().getTime();
		Calendar createdAt = new GregorianCalendar();
		Calendar createdAtAuthor = new GregorianCalendar();
		createdAt.setTime(tweet.getCreatedAt());
		createdAtAuthor.setTime(tweet.getUser().getCreatedAt());
		message.setPublished(createdAt);	

		//Construct the Authortweet JSONObject

		hauthortweet.setStatus(tweet.getUser().getStatusesCount());
		hauthortweet.setFollowers(tweet.getUser().getFollowersCount());
		hauthortweet.setFriends(tweet.getUser().getFriendsCount());
		hauthortweet.setLocation(tweet.getUser().getLocation());
		hauthortweet.setDescription(tweet.getUser().getDescription());		
		hauthortweet.setProfileImg(tweet.getUser().getProfileImageURL().toString());
		hauthortweet.setUrl(tweet.getUser().getURL());
		hauthortweet.setCreatedAt(createdAtAuthor);
		hauthortweet.setLang(tweet.getUser().getLang());
		hauthortweet.setListeds(tweet.getUser().getListedCount());
		hauthortweet.setGeo(tweet.getUser().isGeoEnabled());
		hauthortweet.setVerified(tweet.getUser().isVerified());
		hauthortweet.setName(tweet.getUser().getName());
		hauthortweet.setScrName(tweet.getUser().getScreenName());

		//Construct the tweet JSONObject		
		htweet.setId(tweet.getId());
		htweet.setSource(tweet.getSource());
		htweet.setText(tweet.getText());
		htweet.setAuthortwt(hauthortweet.toJSON());

		message.setPayload(htweet);
		message.setType("hTweet");


		if (log.isDebugEnabled()) {
			log.debug("tweet("+tweet+") -> hMessage :"+message);
		}

		return message;
	}


	/* Getters & Setters */
	public String getConsumerKey() {
		return consumerKey;
	}


	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
		if(log.isDebugEnabled()){
			log.debug("Tags updated : " +tags);
		}
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

	public String getLangFilter() {
		return langFilter;
	}

	public void setLangFilter(String langFilter) {
		this.langFilter = langFilter;
	}

	@Override
	public String toString() {
		return "HTwitterAdapterInbox [consumerKey=" + consumerKey
				+ ", consumerSecret=" + consumerSecret
				+ ", twitterAccessToken=" + twitterAccessToken
				+ ", twitterAccessTokenSecret=" + twitterAccessTokenSecret
				+ ", langFilter=" + langFilter + ", tags=" + tags + "]";
	}



}
