package org.hubiquitus.hubiquitus4j.hubotpack.countTweets.model;

import java.util.ArrayList;

import org.hubiquitus.hapi.model.PublishEntry;

public class CountTLimitedPayloadPublisEntry implements PublishEntry {

	
	/**
	 * List of barometre data 
	 */
	private ArrayList<CountTLimitedPublishEntry> countTweetsDatas;
	
	/**
	 * getter barometreDatas
	 * @return barometreDatas
	 */
	public ArrayList<CountTLimitedPublishEntry> getCountTweetsDatas() {
		if (countTweetsDatas == null) {
			countTweetsDatas = new ArrayList<CountTLimitedPublishEntry>();
		}
		return countTweetsDatas;
	}


	/**
	 * setter 
	 * @param barometreDatas the barometreDatas to set
	 */
	public void setBarometreDatas(ArrayList<CountTLimitedPublishEntry> countTweetsDatas) {
		this.countTweetsDatas = countTweetsDatas;
	}

	/**
	 * Add a barometreData to the list of barometreDatas
	 * @param barometreData the barometreData to add
	 */
	public void addCountTweetsDatas(CountTLimitedPublishEntry countTweetsDatas) {
		if (countTweetsDatas != null) {
			getCountTweetsDatas().add(countTweetsDatas);			
		}
	}
	
	@Override
	public String getEntryType() {
		return "CountTLimitedPublishEntry";
	}

	@Override
	public String entryToJsonFormat() {
		String divider = ",";
		
        String json = "[";
        
        for (int i=0; i<getCountTweetsDatas().size(); i++) {
        	CountTLimitedPublishEntry countTLimitedPublishEntry = getCountTweetsDatas().get(i);
        	if (i>0) {
        		json = json + divider;
        	}
        	json = json + countTLimitedPublishEntry.entryToJsonFormat();
        }
        
        json = json + "]";
    	return json;
	}

}
