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
 * Barometre SotialTV : Payload to publish
 * @author o.chauvie
 */
public class CountTweetsPayloadPublishEntry implements PublishEntry {
	
	/**
	 * List of barometre data 
	 */
	private ArrayList<countTweetsDataPublishEntry> countTweetsDatas;


	/**
	 * Constructor with default values
	 */
	public CountTweetsPayloadPublishEntry() {
	}

	
	/**
	 * getter countTweetsDatas
	 * @return countTweetsDatas
	 */
	public ArrayList<countTweetsDataPublishEntry> getCountTweetsDatas() {
		if (countTweetsDatas == null) {
			countTweetsDatas = new ArrayList<countTweetsDataPublishEntry>();
		}
		return countTweetsDatas;
	}


	/**
	 * setter 
	 * @param barometreDatas the barometreDatas to set
	 */
	public void setBarometreDatas(ArrayList<countTweetsDataPublishEntry> tweetsDatas) {
		this.countTweetsDatas = tweetsDatas;
	}

	/**
	 * Add a barometreData to the list of barometreDatas
	 * @param barometreData the barometreData to add
	 */
	public void addBarometreData(countTweetsDataPublishEntry tweetsData) {
		if (tweetsData != null) {
			getCountTweetsDatas().add(tweetsData);			
		}
	}

	@Override
	public String entryToJsonFormat() {
		String divider = ",";
		
        String json = "[";
        
        for (int i=0; i<getCountTweetsDatas().size(); i++) {
        	countTweetsDataPublishEntry tweetsDataPublishEntry = getCountTweetsDatas().get(i);
        	if (i>0) {
        		json = json + divider;
        	}
        	json = json + tweetsDataPublishEntry.entryToJsonFormat();
        }
        
        json = json + "]";
    	return json;
	}


	@Override
	public String getEntryType() {
		return "CountTweetsDataPublishEntry";
	}	
}