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
/**
 * 
 */
package org.hubiquitus.hubots.feedbot.service.impl;

import java.util.List;
import java.util.Map;

import org.hubiquitus.hubots.feedbot.service.FeedBotConfiguration;
import org.springframework.beans.factory.annotation.Required;

/**
 * @author a.benkirane
 *
 */
public class FeedBotConfigurationSpring implements FeedBotConfiguration {

	protected Map<String, Object> hubotConfigMap;


	public FeedBotConfigurationSpring() {
		super();
	}

	public String getTitle() {
		return (String)hubotConfigMap.get("title");
	}

	public String getNodeName() {
		return (String)hubotConfigMap.get("nodename");
	}

	public String getFeedLink() {
		return (String)hubotConfigMap.get("fetcher.feedlink");
	}

	public int getInterval() {
		return new Integer((String)hubotConfigMap.get("fetcher.interval")).intValue();
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
        toStr += "\"Feed Link\" : \"" + this.getFeedLink() + "\", ";
        toStr += "\"Interval\" : \"" + this.getInterval() + "\" ";
        toStr += "}";
        return toStr;
    }


	// Spring injections ------------------------------------------------------------
	@Required
	public void setHubotConfigMap(Map<String, Object> hubotConfigMap) {
		this.hubotConfigMap = hubotConfigMap;
	}
	
}
