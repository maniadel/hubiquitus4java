/**
 * 
 */
package org.hubiquitus.hubiquitus4j.hubotpack.countTweets.service.impl;

import java.util.List;
import java.util.Map;

import org.hubiquitus.hubiquitus4j.hubotpack.countTweets.service.CountTweetsBotConfiguration;
import org.springframework.beans.factory.annotation.Required;

/**
 * Implementation of Barometre bot configuration
 * @author o.chauvie
 */
public class CountTweetsBotConfigurationSpring implements CountTweetsBotConfiguration {

	protected Map<String, Object> hubotConfigMap;

	
	public CountTweetsBotConfigurationSpring() {
		super();
	}
	
	@Override
	public String getTitle() {
		return (String)hubotConfigMap.get("title");
	}

	@Override
    public String getNodeName() {
        return (String)hubotConfigMap.get("nodename");
    }

    
    @Override
	public String getNodeNameToRead() {
    	return (String)hubotConfigMap.get("fetcher.nodename");
	}
    
    @Override
   	public String getNodeTPMToRead() {
       	return (String)hubotConfigMap.get("fetcher.tpmnodename");
   	}

	
	@SuppressWarnings("unchecked")
	public List<String> getListKeyWords() {
		return (List<String>)hubotConfigMap.get("fetcher.filter");
	}


	@SuppressWarnings("unchecked")
	public List<String> getAllowedRosterGroups() {
		return (List<String>)hubotConfigMap.get("publisher.allowedrostergroups");
	}


	// Get Configuration of the Bot in JSON format
	public String getMainConfJSON() {
		String toStr = "";
		toStr += "{ ";
		toStr += "\"Node name\" : \"" + this.getNodeName() + "\", ";
		toStr += "\"Title\" : \"" + this.getTitle() + "\", ";
		toStr += "\"Allowed Roster Groups\" : \"";

		for (String s : this.getAllowedRosterGroups()) {
			toStr += s + "; ";
		}

		toStr += "\", ";
		toStr += "\"Twitter node name \" : \"" + this.getNodeNameToRead() + "\" ";
		toStr += "}";
		return toStr;
	}
	
	
	// Spring injections ------------------------------------------------------------
	@Required
	public void setHubotConfigMap(Map<String, Object> hubotConfigMap) {
		this.hubotConfigMap = hubotConfigMap;
	}

	
}
