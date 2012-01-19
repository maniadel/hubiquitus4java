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

package org.hubiquitus.hubotsdk.application.query;

import java.util.ArrayList;
import java.util.List;

import org.hubiquitus.hubotsdk.application.BotInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BotDataFetcherBase extends Thread {

	Logger logger = LoggerFactory.getLogger(BotDataFetcherBase.class);

	/*
	 * Main Bot class
	 */
	protected BotInterface bot;

	/*
	 * List of already published entries
	 */
	protected List<String> publishedEntry;

	/*
	 * interval between each data retrieve action
	 */
	private int interval;

	/*
	 * Time past since last XMPP Server sent ping message
	 */
	private int timeLeft;

	/*
	 * Maximum left time between two XMPP Server ping messages (not to be disconnected)
	 */
	private int maxTimeBeforePing;

	/*
	 * True if Stopping fetcher procedure is initiated
	 */
	private boolean stoppingFetcher = false;

	/*
	 * False if fetcher thread is stopping/stopped
	 */
	protected boolean run;

	/*
	 * Constructor
	 * 	Standard situation
	 */
	public BotDataFetcherBase(final int interval, final BotInterface publisher) {
		this.interval = interval;
		this.bot = publisher;

		this.timeLeft = 0;
		this.maxTimeBeforePing = 250000;
		publishedEntry = new ArrayList<String>();
		run = true;
	}

	/*
	 * Constructor
	 * 	Use with proxy settings
	 */
	public BotDataFetcherBase(final int interval, final BotInterface publisher, final String proxyIP, final int proxyPort) {

		this.interval = interval;
		this.bot = publisher;

		this.timeLeft = 0;
		this.maxTimeBeforePing = 250000;
		publishedEntry = new ArrayList<String>();
		run = true;

		System.setProperty("proxySet", "true");
		System.setProperty("http.proxyHost", proxyIP);
		System.setProperty("http.proxyPort", "" + proxyPort);
	}

	/*
	 * Getters/Setters
	 */
	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public BotInterface getBot() {
		return bot;
	}

	public void setBot(BotInterface bot) {
		this.bot = bot;
	}

	public List<String> getPublishedEntry() {
		return publishedEntry;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public boolean isStoppingFetcher() {
		return stoppingFetcher;
	}

	public void setStoppingFetcher(boolean stoppingFetcher) {
		this.stoppingFetcher = stoppingFetcher;
	}

	/*
	 * Unpause Fetcher
	 */
	 public void unpause() {
		this.run = true;
	}

	/*
	 * Pause Fetcher
	 */
	 public void pause() {
		 this.run = false;
	 }

	 /*
	  * Increment past time since last ping.
	  * Sends ping message if necessary and resets timeLeft variable
	  */
	 public boolean incrementTimeLeft(int timeInterval) {
		 this.timeLeft += timeInterval;
		 if (this.timeLeft > maxTimeBeforePing) {
			 bot.ping();
			 resetTimeLeft();
			 return true;
		 }
		 return false;
	 }

	 /*
	  * Resets timeLeft variable
	  */
	 private void resetTimeLeft() {
		 this.timeLeft = 0;
	 }

}
