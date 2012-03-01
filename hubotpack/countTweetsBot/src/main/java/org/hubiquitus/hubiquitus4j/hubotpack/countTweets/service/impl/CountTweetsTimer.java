package org.hubiquitus.hubiquitus4j.hubotpack.countTweets.service.impl;

import java.util.Date;

import org.hubiquitus.hapi.model.ExceptionType;
import org.hubiquitus.hapi.model.impl.MessagePublishEntry;
import org.hubiquitus.hubiquitus4j.hubotpack.countTweets.application.CountTweetsBot;
import org.hubiquitus.hubiquitus4j.hubotpack.countTweets.model.CountTLimitedPayloadPublisEntry;
import org.hubiquitus.hubiquitus4j.hubotpack.countTweets.model.CountTweetsPayloadPublishEntry;
import org.hubiquitus.hubotsdk.application.Bot;
import org.hubiquitus.hubotsdk.application.BotInterface;
import org.hubiquitus.hubotsdk.application.push.impl.SmackApiControllerImpl;
import org.hubiquitus.hubotsdk.application.push.impl.XmppEntryPublisherImpl;
import org.hubiquitus.hubotsdk.application.query.BotDataFetcherBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountTweetsTimer extends BotDataFetcherBase{

	private Logger logger = LoggerFactory.getLogger(CountTweetsTimer.class);
	private CountTweetsBot countTweetsBot; 
	private int interval;
	
	public CountTweetsTimer(final int interval, final BotInterface countTweetsBot) {
		super(interval, countTweetsBot);
		this.countTweetsBot = (CountTweetsBot) countTweetsBot;
		this.interval = interval;
	}

	@Override
	public void run() {
		MessagePublishEntry messagePublishEntry = new MessagePublishEntry();
		logger.debug("Starting timer...");
		try {
			while ((isAlive()) && (!Bot.isStoppingFetcher())) {
				messagePublishEntry.generateMsgid();
				messagePublishEntry.setPublisher(countTweetsBot.getNodeName());
				messagePublishEntry.setPublished(new Date());
				
   					//CountTweetsPayloadPublishEntry countTweetsPayloadPublishEntry = (CountTweetsPayloadPublishEntry) messagePublishEntry.getPayload();
   					
   					XmppEntryPublisherImpl xmppEntryPublisherImpl = countTweetsBot.getXmppEntryPublisher();
   					SmackApiControllerImpl smackApiControllerImpl = xmppEntryPublisherImpl.getSmackApiController();
   								   					
   				}
				countTweetsBot.getNbMessages().size();
	
				CountTLimitedPayloadPublisEntry barometreLimitedPayloadPublisEntry = new CountTLimitedPayloadPublisEntry();
				messagePublishEntry.setPayload(barometreLimitedPayloadPublisEntry);
				countTweetsBot.getXmppEntryPublisher().publish(messagePublishEntry, countTweetsBot.getNodeTPMToRead());
				sleep(this.interval);
				
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			bot.addException(e, ExceptionType.FETCHER_INTERRUPTED_ERROR);
			Bot.setFetcherState(Bot.BotState.ERROR);
		} 	
	}
}
