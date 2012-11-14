package org.hubiquitus.htwitterAPI4j.HTwitterClients;

import org.hubiquitus.twitter4j.stream.pub.HStatusUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterSendTweet {

	final static Logger log = LoggerFactory.getLogger(TwitterSendTweet.class);

	

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

	private static String status="Hello Hubiquitus 2 !!!";
	private static String screenName ="@testMANI1";

	public static void main(String[] args) {

		status = screenName+" "+status;

		HStatusUpdate updateStatus = new HStatusUpdate(proxyHost, proxyPort, consumerKey, consumerSecret, twitterAccessToken, twitterAccessTokenSecret, status);
		int code = updateStatus.postTweet();

		log.info(" RECIVED CODE : "+code);


	}

}
