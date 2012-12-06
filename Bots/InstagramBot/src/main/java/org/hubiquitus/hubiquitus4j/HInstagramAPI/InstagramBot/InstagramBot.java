package org.hubiquitus.hubiquitus4j.HInstagramAPI.InstagramBot;

import org.apache.log4j.Logger;
import org.hubiquitus.hapi.client.HClient;
import org.hubiquitus.hapi.hStructures.HMessage;

import org.hubiquitus.hubotsdk.Hubot;

/**
 * 
 * This bot ask data to the instagram API using HInstagramTagsInbox Adapter
 * @author Adel MANI
 *
 */
public class InstagramBot extends Hubot
{

	private static Logger log = Logger.getLogger(InstagramBot.class);
	
	public static void main( String[] args )throws Exception{
		InstagramBot bot  = new InstagramBot();
		bot.start();	
	}

	@Override
	protected void init(HClient hClient) {
		super.init(hClient);
	}

	@Override
	protected void inProcessMessage(HMessage messageIncoming) {
		log.info("-[InstagramBot] Received message :"+messageIncoming.toString());
	}

}