# HTwitterAPI 1.1 For a Simple Client 

The HTwitterAPI 1.1 in this part, it used by a simple client independently of Hubiquitus. it provides the same function as in the case Hubiquitus.

## How to use HTwitterAPI 1.1 for the Simple Client? 

To use HTwitterAPI 1.1 for the simple client in case of simple stream, you need to import the packeges named : org.hubiquitus.twitter4j.stream.pub.HStream and import org.hubiquitus.twitter4j.stream.pub.HStreamListner.  

Otherwise you want to use the HTwitterAPI 1.1 for the  user stream you need to import the package named : import org.hubiquitus.twitter4j.stream.pub.HUserStream and  org.hubiquitus.twitter4j.stream.pub.HUserStreamListner. 

## Exemple HTwitter client 

### 1. Simple Stream

```java
package org.hubiquitus.hubiquitus4j_1_1.HTwitterSimplesClients;

import org.hubiquitus.twitter4j_1_1.stream.HStream;
import org.hubiquitus.twitter4j_1_1.stream.HStreamListner;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TwitterStream  implements HStreamListner {

	final static Logger log = LoggerFactory.getLogger(TwitterStream.class);
	
	private static String consumerKey             = "yourConsumerKey";
	private static String consumerSecret          = "yourConsumerSecret";
	private static String twitterAccessToken      = "yourAccessToken";
	private static String twitterAccessTokenSecret= "yourAccessTokenSecret";	
		
	private static String tags="tag1, tag2";

	private static String proxyHost=null; //Optional
	private static int    proxyPort= 0;     //Optional

	private static String delimited="all";                         //Optional
	private static String stallWarnings="true";                    //Optional
	private static String with="followings";                       //Optional 
	private static String replies="all";                           //Optional
	private static String locations ="-122.75,36.8,-121.75,37.8";  //Optional
	private static String count;                                   //Optional
	
	
	
	public static void main(String[] args) {
		log.info("Start stream ...");
		HStream stream = new HStream(proxyHost, proxyPort, tags,
                                             delimited, stallWarnings, with, 
                                             replies, locations, count, consumerKey,
                                             consumerSecret, twitterAccessToken, twitterAccessTokenSecret);
		stream.addListener(new TwitterStream());
		stream.start();
		
	}

	@Override
	public void onStatus(JSONObject status) {
		log.info("-  received Tweet :"+status.toString());
		
	}	

	@Override
	public void onStallWarning(JSONObject stallWarning) {
		log.info("-  received stallWarning :"+stallWarning.toString());
		
	}

	@Override
	public void onStatusDeletionNotices(JSONObject delete) {
		log.info("-  received delete :"+delete.toString());
		
	}

	@Override
	public void onLocationDeletionNotices(JSONObject scrubGeo) {
		log.info("-  received delete :"+scrubGeo.toString());
		
	}

	@Override
	public void onLimitNotices(JSONObject limit) {
		log.info("-  received limit :"+limit.toString());
		
	}

	@Override
	public void onStatusWithheld(JSONObject statusWithheld) {
		log.info("-  received limit :"+statusWithheld.toString());
		
	}

	@Override
	public void onUserWithheld(JSONObject userWithheld) {
		log.info("-  received userWithheld :"+userWithheld.toString());
		
	}

	@Override
	public void onDisconnectMessages(JSONObject disconnect) {
		log.info("-  received disconnect :"+disconnect.toString());
		
	}
	@Override
	public void onOtherMessage(JSONObject message) {
		log.info("-  received message :"+message.toString());
		
	}
}

```
### 2. User Stream

```java

package org.hubiquitus.hubiquitus4j_1_1.HTwitterSimplesClients;

import org.hubiquitus.twitter4j_1_1.stream.HUserStream;
import org.hubiquitus.twitter4j_1_1.stream.HUserStreamListner;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterUserStream  implements HUserStreamListner{

	final static Logger log = LoggerFactory.getLogger(TwitterStream.class);

	private static String consumerKey             = "yourConsumerKey";
	private static String consumerSecret          = "yourConsumerSecret";
	private static String twitterAccessToken      = "yourAccessToken";
	private static String twitterAccessTokenSecret= "yourAccessTokenSecret";	

	private static String delimited="all";                         //Optional
	private static String stallWarnings="true";                    //Optional
	private static String with="followings";                       //Optional 
	private static String replies="all";                           //Optional
	private static String locations="-122.75,36.8,-121.75,37.8";   //Optional
	private static String count;                                   //Optional

	private static String tags="tag1, tag2";

	private static String proxyHost= null;   //Optional
	private static int    proxyPort= 0;     //Optional





	public static void main(String[] args) {

		log.info("Start User stream ...");
		HUserStream userStream = new HUserStream(proxyHost, proxyPort, tags, 
							 delimited, stallWarnings, 
							 with, replies, locations, 
							 count, consumerKey, consumerSecret, 
							 twitterAccessToken, twitterAccessTokenSecret);

		userStream.addListener(new TwitterUserStream());
		userStream.start();

		log.info("User stream Started.");
	}

	@Override
	public void onStatus(JSONObject status) {		
		log.info("-  Recived Tweet :"+status.toString());
	}

	@Override
	public void onOtherMessage(JSONObject message) {
		log.info("-  Recived message :"+message.toString());		
	}

	@Override
	public void onStallWarning(JSONObject stallWarning) {
		log.info("-  Recived stallWarning :"+stallWarning.toString());		
	}

	@Override
	public void onStatusDeletionNotices(JSONObject delete) {
		log.info("-  Recived delete :"+delete.toString());
	}

	@Override
	public void onLocationDeletionNotices(JSONObject scrubGeo) {
		log.info("-  Recived scrubGeo :"+scrubGeo.toString());		
	}

	@Override
	public void onLimitNotices(JSONObject limit) {
		log.info("-  Recived limit :"+limit.toString());		
	}

	@Override
	public void onStatusWithheld(JSONObject statusWithheld) {
		log.info("-  Recived statusWithheld :"+statusWithheld.toString());
	}

	@Override
	public void onUserWithheld(JSONObject userWithheld) {
		log.info("-  Recived userWithheld :"+userWithheld.toString());
	}

	@Override
	public void onDisconnectMessages(JSONObject disconnect) {
		log.info("-  Recived disconnect :"+disconnect.toString());

	}
}
```

### 3. Post an tweet

Eventually you may use the HTwitterAPI 1.1 to post a tweets. For this, you need to import the library ie (org.hubiquitus.twitter4j_1_1.stream.HStatusUpdate).

 Look at this example of the beginning :

```java

package org.hubiquitus.hubiquitus4j_1_1.HTwitterSimplesClients;

import org.hubiquitus.twitter4j_1_1.stream.HStatusUpdate;
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
		HStatusUpdate updateStatus = new HStatusUpdate(proxyHost, proxyPort, consumerKey,
                                                               consumerSecret, twitterAccessToken,            
                                                               twitterAccessTokenSecret);
	        updateStatus.postTweet(status);

	}

}
```

The adequate pom.xml is :

```xml
<dependency>
	<groupId>org.hubiquitus.htwitterAPI4j_1.1</groupId>
	<artifactId>HtwitterAPI</artifactId>
	<version>1.0.0</version>
</dependency>
```