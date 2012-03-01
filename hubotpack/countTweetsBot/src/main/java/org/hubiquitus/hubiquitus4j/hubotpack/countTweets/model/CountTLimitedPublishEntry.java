/**
 * 
 */
package org.hubiquitus.hubiquitus4j.hubotpack.countTweets.model;

import org.hubiquitus.hapi.model.PublishEntry;

/**
 * @author l.chong-wing
 *
 */
public class CountTLimitedPublishEntry implements PublishEntry {

	/**
	 * TV channel 
	 */
	private String channel;
	
	/**
	 * Number of tweets per minutes
	 */
	private long tweetspermin;
	
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
	 * getter tweetspermin
	 * @return the tweetspermin
	 */
	public long getTweetspermin() {
		return tweetspermin;
	}
	
	/**
	 * setter tweetspermin
	 * @param tweetspermin the tweetspermin to set
	 */
	public void setTweetspermin(long tweetspermin) {
		this.tweetspermin = tweetspermin;
	}
	
	
	/* (non-Javadoc)
	 * @see org.hubiquitus.hubots.api.model.PublishEntry#getEntryType()
	 */
	@Override
	public String getEntryType() {
		return "CountTLimitedPublishEntry";
	}

	/* (non-Javadoc)
	 * @see org.hubiquitus.hubots.api.model.PublishEntry#entryToJsonFormat()
	 */
	@Override
	public String entryToJsonFormat() {
		String divider = ",";
        String json = "";
    	
        json = "{ \"channel\" : \"" + getChannel() + "\"" + divider;
        json = json + " \"tweetspermin\" : " + getTweetspermin();
        json = json + "}";
    	
    	return json;
	}

}
