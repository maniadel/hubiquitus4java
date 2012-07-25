package org.hubiquitus.hubotsdk.adapters;

import java.util.Map;

import org.apache.log4j.Logger;
import org.hubiquitus.hubotsdk.AdapterInbox;


import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class HTwitterAdapterInbox extends AdapterInbox{

	private static Logger logger = Logger.getLogger(HChannelAdapterOutbox.class);
	
	private String consumerKey ;
	private String consumerSecret;
	private String twitterAccessToken;
	private String twitterAccessTokenSecret;

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

	}

	@Override
	public void start() {
		stream();
	}

	@Override
	public void stop() {
		twitterStream.shutdown();
	}

	public HTwitterAdapterInbox() {
		super();
	}

	public void stream() {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setUseSSL(false); 
		cb.setOAuthConsumerKey(consumerKey);
		cb.setOAuthConsumerSecret(consumerSecret);
		cb.setOAuthAccessToken(twitterAccessToken);
		cb.setOAuthAccessTokenSecret(twitterAccessTokenSecret);
		twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		StatusListener listener = new StatusListener() {

			public void onStatus(Status status) {
				logger.info("@" + status.getUser().getScreenName() + " - " + status.getText());
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

}
