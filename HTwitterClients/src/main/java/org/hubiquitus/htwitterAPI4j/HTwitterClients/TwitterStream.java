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
	
	private static String consumerKey             = "rauHc8qj9em3BlIAaNaEg";
	private static String consumerSecret          = "VKdX0lVcjXcrQkMtooN4telfgl2CUi97URPWqZvGPE";
	private static String twitterAccessToken      = "345294967-ryEO8s3rt6EGQromIUWepak40Ayzq4DU7pbJXcCP";
	private static String twitterAccessTokenSecret= "8nJoIvWsxpK5Pv6DlrJfrsZju8v8Ct1yy7o54e0gnaA";	
	
	private static String tags="novedia";
	
	private static String proxyHost="192.168.102.84";
	private static int    proxyPort= 3128;
	
	private static String delimited="all";
	private static String stallWarnings; 
	private static String with;
	private static String replies;
	private static String locations;
	private static String count;
	
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
