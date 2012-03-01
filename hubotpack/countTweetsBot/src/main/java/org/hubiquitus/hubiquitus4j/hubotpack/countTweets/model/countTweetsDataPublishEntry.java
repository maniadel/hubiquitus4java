/*
 * Copyright (c) Novedia Group 2011.
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

/**
 *
 */
package org.hubiquitus.hubiquitus4j.hubotpack.countTweets.model;


import java.util.ArrayList;

import org.hubiquitus.hapi.model.PublishEntry;

/**
 * Barometre SotialTV : Data to publish
 * @author o.chauvie
 */
public class countTweetsDataPublishEntry implements PublishEntry {
	
	/**
	 * TV channel 
	 */
	private String channel;
	
	/**
	 * Program title 
	 */
	private String title;
	
	/**
	 * Program tags
	 */
	private ArrayList<String> tags;
	
	/**
	 * Number of tweets 
	 */
	private int tweets;
	
//	/**
//	 * Number of tweets per minutes
//	 */
//	private long tweetspermin;
//	

	/**
	 * Constructor with default values
	 */
	public countTweetsDataPublishEntry() {
	}

	/**
	 * getter channel
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}
	
	/**
	 * setter channel
	 * @param msgid the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	/**
	 * getter title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * setter title
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	/**
	 * getter tweets
	 * @return the tweets
	 */
	public int getTweets() {
		return tweets;
	}
	
	/**
	 * setter tweets
	 * @param tweets the tweets to set
	 */
	public void setTweets(int tweets) {
		this.tweets = tweets;
	}
	
//	/**
//	 * getter tweetspermin
//	 * @return the tweetspermin
//	 */
//	public long getTweetspermin() {
//		return tweetspermin;
//	}
//	
//	/**
//	 * setter tweetspermin
//	 * @param tweetspermin the tweetspermin to set
//	 */
//	public void setTweetspermin(long tweetspermin) {
//		this.tweetspermin = tweetspermin;
//	}
	
	@Override
	public String entryToJsonFormat() {
		String divider = ",";
        String json = "";
    	
        json = "{ \"channel\" : \"" + getChannel() + "\"" + divider;
        json = json + " \"title\" : \"" + getTitle() + "\"" + divider;
        json = json + " \"tags\" : \"" + getTags() + "\"" + divider;
        json = json + " \"tweets\" : " + getTweets();
        //json = json + " \"tweetspermin\" : " + getTweetspermin();
        json = json + "}";
    	
    	return json;
	}

	@Override
	public String getEntryType() {
		return "BarometreDataPublishEntry";
	}	
}