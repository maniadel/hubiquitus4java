package org.hubiquitus.htwitterAPI4j.HTwitterClients;

import org.hubiquitus.twitter4j.stream.pub.HUserStream;
import org.hubiquitus.twitter4j.stream.pub.HUserStreamListner;
import org.hubiquitus.twitter4j.stream.pub.TweetToHMessage;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterUserStream extends HUserStream implements HUserStreamListner{

	final static Logger log = LoggerFactory.getLogger(TwitterStream.class);

	private static String consumerKey             = "rauHc8qj9em3BlIAaNaEg";
	private static String consumerSecret          = "VKdX0lVcjXcrQkMtooN4telfgl2CUi97URPWqZvGPE";
	private static String twitterAccessToken      = "345294967-ryEO8s3rt6EGQromIUWepak40Ayzq4DU7pbJXcCP";
	private static String twitterAccessTokenSecret= "8nJoIvWsxpK5Pv6DlrJfrsZju8v8Ct1yy7o54e0gnaA";	

	private static String proxyHost="192.168.102.84";
	private static int    proxyPort= 3128;

	// Stream parameters		
	private static String tags ="obama";	
	private static String delimited;
	private static String stallWarnings="true"; 
	private static String with;
	private static String replies;
	private static String locations;
	private static String count;

	private static TweetToHMessage tweetToHMessage =  new TweetToHMessage();
	
	public TwitterUserStream(String proxyHost, int proxyPort, String tags,
			String delimited, String stallWarnings, String with,
			String replies, String locations, String count, String consumerKey,
			String consumerSecret, String token, String tokenSecret) {
		super(proxyHost, proxyPort, tags, delimited, stallWarnings, with, replies,
				locations, count, consumerKey, consumerSecret, token, tokenSecret);		
	}


	@Override
	public void onStatus(JSONObject status) {
		
		
		log.info("------------------->  Recived Tweet :"+status.toString());
		log.info(" transforming Tweet to HMessage ...");
		try {
			log.info("----> Tweet under HMessage format  :"+tweetToHMessage.transformtweet(status));
		} catch (JSONException e) {
			log.error("Error in transformation Tweet to HMessage  :"+e);
		}
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
		TwitterUserStream stream = new TwitterUserStream(
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
