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

	

	private static String proxyHost="0.0.0.0"; //Optional
	private static int    proxyPort= 0000;     //Optional

	private static String status=" Best wishes from Hubiquitus Team  !!!";
	private static String screenName ="@testMANI1";


	public static void main(String[] args) {

		status = screenName+" "+status;

		HStatusUpdate updateStatus = new HStatusUpdate(proxyHost, proxyPort, consumerKey, consumerSecret, twitterAccessToken, twitterAccessTokenSecret);
		int code = updateStatus.postTweet(status);
		if(code == 200){
			log.info(" Your tweet is posted with succes. Code : "+code);	
		}else{
			log.info(" We can't delivred your tweet, May be you are alerdy tweet this text. Code error  : "+code);
		}
		

	}

}
