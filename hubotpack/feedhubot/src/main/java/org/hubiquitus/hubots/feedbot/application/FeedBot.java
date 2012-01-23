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

package org.hubiquitus.hubots.feedbot.application;

import java.net.URL;
import java.util.List;

import org.hubiquitus.hubotsdk.application.Bot;
import org.hubiquitus.hubotsdk.exception.BotException;
import org.hubiquitus.hapi.model.ExceptionType;
import org.hubiquitus.hubots.feedbot.application.query.ContinuousFeedFetcher;
import org.hubiquitus.hubots.feedbot.service.impl.FeedBotConfigurationSpring;
import org.hubiquitus.hubots.feedbot.service.impl.FeedBotUtils;
import org.springframework.beans.factory.annotation.Required;

import com.sun.syndication.feed.synd.SyndEntry;

public class FeedBot extends Bot implements FeedBotInterface {
	/*
	 * Feed fetcher. Retrieves all data from set feed URL and adds it to publication queue
	 */
	private ContinuousFeedFetcher feedFetcher;

	

	/*
	 * Init configuration. Contains all the information needed to launch bot
	 */
	private FeedBotConfigurationSpring hubotConfig;
	
	/**
	 * Constructor
	 *
	 * @param conf
	 */
	public FeedBot() {
		super();
	}
	

	

	public void startDataRetriever() {
		try {
			fetcherState = BotState.STARTED;
			if ((this.getXmppEntryPublisher().getSmackApiController().getXmppBotConfiguration().getUseProxy() != null) && this.getXmppEntryPublisher().getSmackApiController().getXmppBotConfiguration().getUseProxy().equals("true")) {
				startFeedFetcher(this.getXmppEntryPublisher().getSmackApiController().getXmppBotConfiguration().getProxyAddress(), this.getXmppEntryPublisher().getSmackApiController().getXmppBotConfiguration().getProxyPort(), this.hubotConfig.getFeedLink(), this.hubotConfig.getInterval());
			} else {
				startFeedFetcher(this.hubotConfig.getFeedLink(), this.hubotConfig.getInterval());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			fetcherState = BotState.ERROR;
			exceptionCollector.addException(e, ExceptionType.FETCHER_LAUNCH_ERROR);
		}
	}
	
	
	/*
	 * Start Feed fetcher
	 */
	private void startFeedFetcher(String feedURL, int interval)
			throws Exception {
		if (getXmppEntryPublisher() == null) {
    		logger.error("Publisher not started yet. Not allowed to start fetcher.");
    		
            throw new BotException(
                    "Publisher not started yet. Not allowed to start fetcher.");
        }

		URL feedUrl = new URL(feedURL);

		feedFetcher = new ContinuousFeedFetcher(feedUrl, interval, this);

		feedFetcher.start();
		feedFetcher.join();

	}

	/*
	 * Start Feed fetcher with proxy adress
	 */
	private void startFeedFetcher(String proxyAddress, int proxyPort, String feedURL,
			int interval) throws Exception {
		if (getXmppEntryPublisher() == null) {
    		logger.error("Publisher not started yet. Not allowed to start fetcher.");
    		
            throw new BotException(
                    "Publisher not started yet. Not allowed to start fetcher.");
        }

		URL feedUrl = new URL(feedURL);

		feedFetcher = new ContinuousFeedFetcher(proxyAddress,
				proxyPort, feedUrl, interval, this);

		feedFetcher.start();
		feedFetcher.join();

	}

	/*
	 * Publish a syndicate entry content to node (data will be formatted to be understood by alertmama mobile platform)
	 * (non-Javadoc)
	 * @see com.novedia.alertmama.feedbot.application.BotInterface#publish(com.sun.syndication.feed.synd.SyndEntry)
	 */
	public void publish(SyndEntry entry) {
		publish(FeedBotUtils.syndEntryToAMPublishEntry(entry));
	}

	
	@Override
	public String getNodeName() {
		return this.hubotConfig.getNodeName();
	}

	@Override
	public String getTitle() {
		return this.hubotConfig.getTitle();
	}

	@Override
	public List<String> getAllowedRosterGroups() {
		return this.hubotConfig.getAllowedRosterGroups();
	}

	@Override
	public String getMainConfJSON() {
		return this.hubotConfig.getMainConfJSON();
	}
	
	


	// Spring injections --------------------------------------------------------------
	@Required
	public void setHubotConfig(FeedBotConfigurationSpring hubotConfig) {
		this.hubotConfig = hubotConfig;
	}

	
}
