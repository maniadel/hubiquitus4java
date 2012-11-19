package org.hubiquitus.hubiquitus4j.FacebookBot;

import org.apache.log4j.Logger;
import org.hubiquitus.hapi.client.HClient;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.Hubot;


public class FacebookBot extends Hubot
{

	private static Logger log = Logger.getLogger(FacebookBot.class);
	
	public static void main( String[] args )throws Exception{
		FacebookBot bot  = new FacebookBot();
		bot.start();	
	}
	@Override
	protected void init(HClient hClient) {
		super.init(hClient);
	}

	@Override
	protected void inProcessMessage(HMessage messageIncoming) {
		log.info("-------[Facebook] Recived message :"+messageIncoming.toString());


	}





}
