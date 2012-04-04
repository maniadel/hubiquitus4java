/*
 * Copyright (c) Novedia Group 2012.
 *
 *     This file is part of Hubiquitus.
 *
 *     Hubiquitus is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Hubiquitus is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Hubiquitus.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.hubiquitus.hubots.feedbot.application.query;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.hubiquitus.hubotsdk.application.Bot;
import org.hubiquitus.hubotsdk.application.BotInterface;
import org.hubiquitus.hubotsdk.application.query.BotDataFetcherBase;
import org.hubiquitus.hapi.model.ExceptionType;
import org.hubiquitus.hubots.feedbot.service.impl.FeedBotUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.FetcherEvent;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.fetcher.FetcherListener;
import com.sun.syndication.fetcher.impl.DiskFeedInfoCache;
import com.sun.syndication.fetcher.impl.FeedFetcherCache;
import com.sun.syndication.fetcher.impl.HttpClientFeedFetcher;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.sun.syndication.io.FeedException;

public class ContinuousFeedFetcher extends BotDataFetcherBase implements FetcherListener {

	Logger logger = LoggerFactory.getLogger(ContinuousFeedFetcher.class);

	private URL feedUrl;

	private FeedFetcher feedFetcher;

	/*
	 * Constructor
	 * 	Standard situation
	 */
	public ContinuousFeedFetcher(final URL feedUrl, final int interval, final BotInterface publisher) {
		super(interval, publisher);

		this.feedUrl = feedUrl;
		FeedFetcherCache feedInfoCache = new DiskFeedInfoCache(System.getProperty("java.io.tmpdir"));
		feedFetcher = new HttpClientFeedFetcher(feedInfoCache);
		feedFetcher.addFetcherEventListener(this);
	}

	/*
	 * Constructor
	 * 	Use with proxy settings
	 */
	public ContinuousFeedFetcher(final String proxyIP, final int proxyPort, final URL feedUrl, final int interval, final BotInterface publisher) {
		super(interval, publisher, proxyIP, proxyPort);

		this.feedUrl = feedUrl;
		FeedFetcherCache feedInfoCache = new DiskFeedInfoCache(System.getProperty("java.io.tmpdir"));
		feedFetcher = new HttpURLFeedFetcher(feedInfoCache);
		feedFetcher.addFetcherEventListener(this);
	}

	@Override
	public void run() {
		logger.info("Continuous Feed Fetcher starting...");
		
		try {
			while ((isAlive()) && (!Bot.isStoppingFetcher())) {
				this.incrementTimeLeft(getInterval());
				if (run) {
					try {
						feedFetcher.retrieveFeed(feedUrl);
					} catch (IOException e) {
						logger.error(e.getMessage());
						bot.addException(e, ExceptionType.FETCHER_RETRIEVE_ERROR);
						Bot.setFetcherState(Bot.BotState.ERROR);
					} catch (FeedException e) {
						logger.error(e.getMessage());
						bot.addException(e, ExceptionType.FETCHER_RETRIEVE_ERROR);
						Bot.setFetcherState(Bot.BotState.ERROR);
					} catch (FetcherException e) {
						logger.error(e.getMessage());
						bot.addException(e, ExceptionType.FETCHER_RETRIEVE_ERROR);
						Bot.setFetcherState(Bot.BotState.ERROR);
					}
				}
				sleep(getInterval());
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			bot.addException(e, ExceptionType.FETCHER_INTERRUPTED_ERROR);
			Bot.setFetcherState(Bot.BotState.ERROR);
		}
	}

	/*
	 * Handle retrieved data event
	 * (non-Javadoc)
	 * @see com.sun.syndication.fetcher.FetcherListener#fetcherEvent(com.sun.syndication.fetcher.FetcherEvent)
	 */
	public void fetcherEvent(FetcherEvent event) {
		logger.info(event.getEventType());
		if (Bot.getFetcherState().equals(Bot.BotState.ERROR))
			Bot.setFetcherState(Bot.BotState.STARTED_ERRORS_RAISED);
		if (FetcherEvent.EVENT_TYPE_FEED_RETRIEVED.equals(event.getEventType())) {
			logger.info("FEED RETRIEVED  - Treatment...");

			for (SyndEntry entry : (List<SyndEntry>) event.getFeed().getEntries()) {
				String title = entry.getTitle();
				logger.info(title);
				if (title != null && !publishedEntry.contains(title)) {
					publishedEntry.add(title);
					bot.publish(FeedBotUtils.syndEntryToAMPublishEntry(entry));
					logger.info("Fetcher event: Publishing...");
				} else {
					logger.info("Title: " + title + " - Published entry contains title: " + publishedEntry.contains(title));
				}
			}
		}
	}


	/*
	 * Getters/Setters
	 */
	public URL getFeedUrl() {
		return feedUrl;
	}

	public void setFeedUrl(URL feedUrl) {
		this.feedUrl = feedUrl;
	}

}
