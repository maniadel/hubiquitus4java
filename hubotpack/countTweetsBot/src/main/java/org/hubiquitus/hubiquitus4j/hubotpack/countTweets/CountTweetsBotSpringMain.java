package org.hubiquitus.hubiquitus4j.hubotpack.countTweets;

import java.util.Calendar;

import org.hubiquitus.hubiquitus4j.hubotpack.countTweets.application.CountTweetsBot;
import org.hubiquitus.hubotsdk.HubotMain;

public class CountTweetsBotSpringMain extends HubotMain {	
	
	/**
     * feedBot main. Requires launch configuration type and data
     *
     * @param args
     */
	public static void main(String[] args) {
		CountTweetsBot.setInstanceStarted(Calendar.getInstance());
		start(args);
	}
	
	
}
