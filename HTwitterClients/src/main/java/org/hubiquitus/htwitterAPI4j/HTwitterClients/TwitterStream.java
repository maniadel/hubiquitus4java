package org.hubiquitus.htwitterAPI4j.HTwitterClients;

import org.hubiquitus.twitter4j.stream.pub.HStream;
import org.hubiquitus.twitter4j.stream.pub.HStreamListner;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TwitterStream extends HStream implements HStreamListner {

	public TwitterStream(String proxyHost, int proxyPort, String tags,
			String delimited, String stallWarnings, String with,
			String replies, String locations, String count, String consumerKey,
			String consumerSecret, String token, String tokenSecret) {
		super(proxyHost, proxyPort, tags, delimited, stallWarnings, with, replies,
				locations, count, consumerKey, consumerSecret, token, tokenSecret);		
	}



	final static Logger log = LoggerFactory.getLogger(TwitterStream.class);
	
	private static String consumerKey             = "yourConsumerKey";
	private static String consumerSecret          = "yourConsumerSecret";
	private static String twitterAccessToken      = "yourAccessToken";
	private static String twitterAccessTokenSecret= "yourAccessTokenSecret";	
	
	private static String tags="tag1,tag2,..";
	
	private static String proxyHost="0.0.0.0"; //Optional
	private static int    proxyPort= 0000;     //Optional
	
	private static String delimited="all";                         //Optional
	private static String stallWarnings="true";                    //Optional
	private static String with="followings";                       //Optional 
	private static String replies="all";                           //Optional
	private static String locations="-122.75,36.8,-121.75,37.8";   //Optional
	private static String count="130000";                          //Optional
	
	
	
	
	
	@Override
	public void onStatus(JSONObject status) {
		log.info("------------------->  Recived Tweet :"+status.toString());
		
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
		log.info("------------------->  Recived delete :"+scrubGeo.toString());
		
	}

	@Override
	public void onLimitNotices(JSONObject limit) {
		log.info("------------------->  Recived limit :"+limit.toString());
		
	}

	@Override
	public void onStatusWithheld(JSONObject statusWithheld) {
		log.info("------------------->  Recived limit :"+statusWithheld.toString());
		
	}

	@Override
	public void onUserWithheld(JSONObject userWithheld) {
		log.info("------------------->  Recived userWithheld :"+userWithheld.toString());
		
	}

	@Override
	public void onDisconnectMessages(JSONObject disconnect) {
		log.info("------------------->  Recived disconnect :"+disconnect.toString());
		
	}
	@Override
	public void onOtherMessage(JSONObject message) {
		log.info("------------------->  Recived message :"+message.toString());
		
	}

	
	
	public static void main(String[] args) {
		log.info("Start stream ...");
		TwitterStream stream = new TwitterStream(
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
}
