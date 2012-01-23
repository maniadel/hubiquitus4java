package org.hubiquitus.hubots.myfirstbot.application.query;

import org.hubiquitus.hubots.myfirstbot.service.impl.MyFirstBotUtilsImpl;
import org.hubiquitus.hubotsdk.application.Bot;
import org.hubiquitus.hubotsdk.application.BotInterface;
import org.hubiquitus.hubotsdk.application.query.BotDataFetcherBase;
import org.hubiquitus.hapi.model.ExceptionType;
import org.hubiquitus.hapi.model.impl.MessagePublishEntry;


public class MyRetriever extends BotDataFetcherBase {

	private String arecuperer = "Hello";
	
	private String data;
	
	public MyRetriever(int interval, BotInterface publisher) {
		super(interval, publisher);
	}
	
	public void run() {
		try {
			while ((isAlive()) && (!Bot.isStoppingFetcher())) {
				this.incrementTimeLeft(getInterval());
				if (run) {
					//RetrieveData
					retrieve();
					sleep(getInterval());
				}
			}
		} catch (InterruptedException e) {
			bot.addException(e, ExceptionType.FETCHER_INTERRUPTED_ERROR);
			Bot.setFetcherState(Bot.BotState.ERROR);
		}
	}
	
	public void retrieve(){
		data = arecuperer;
		
		bot.publishToNode(MyFirstBotUtilsImpl.wordToMessagePublishEntry(data), bot.getNodeName());
		
	}
}
