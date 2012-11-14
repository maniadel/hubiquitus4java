package org.hubiquitus.htwitterAPI4j.HTwitterClients;

import org.hubiquitus.twitter4j.stream.pub.HStatusUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterSendTweet {

	final static Logger log = LoggerFactory.getLogger(TwitterSendTweet.class);

	private static String consumerKey             = "rauHc8qj9em3BlIAaNaEg";
	private static String consumerSecret          = "VKdX0lVcjXcrQkMtooN4telfgl2CUi97URPWqZvGPE";
	private static String twitterAccessToken      = "345294967-ryEO8s3rt6EGQromIUWepak40Ayzq4DU7pbJXcCP";
	private static String twitterAccessTokenSecret= "8nJoIvWsxpK5Pv6DlrJfrsZju8v8Ct1yy7o54e0gnaA";	

	private static String status="Hello Hubiquitus 2 !!!";
	private static String screenName ="@testMANI1";

	private static String proxyHost="192.168.102.84";
	private static int    proxyPort= 3128;





	public static void main(String[] args) {

		status = screenName+" "+status;

		HStatusUpdate updateStatus = new HStatusUpdate(proxyHost, proxyPort, consumerKey, consumerSecret, twitterAccessToken, twitterAccessTokenSecret, status);
		int code = updateStatus.postTweet();

		log.info(" RECIVED CODE : "+code);


	}

}
