package org.hubiquitus.hubiquitus4j.hubotpack.countTweets.service;

import java.util.List;

/**
 * Interface for Barometre bot configuration
 * @author o.chauvie
 *
 */
public interface CountTweetsBotConfiguration {

	/**
	 * Get the bot's node name to read
	 * @return node name to read
	 */
	public String getNodeNameToRead();
	
	/**
	 * Get the bot's node name
	 * @return node name
	 */
	public String getNodeName();

	/**
	 * Get the bot's title
	 * @return title
	 */
    public String getTitle(); 

    /**
     * Get list of allowed roster groups
     * @return allowed Roster Groups
     */
    public List<String> getAllowedRosterGroups(); 

    /**
     * Get json configuration
     * @return the json configuration
     */
    public String getMainConfJSON();

    /**
     * Get the node name used to send messages
     * @return the node name
     */
	public String getNodeTPMToRead(); 
}
