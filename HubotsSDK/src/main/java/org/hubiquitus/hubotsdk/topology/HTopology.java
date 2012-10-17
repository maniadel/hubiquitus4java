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
package org.hubiquitus.hubotsdk.topology;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTopology extends HActor {
	final Logger logger = LoggerFactory.getLogger(HTopology.class);
	public HTopology(){
		super();
	}
	
	public HTopology(JSONObject jsonObj) throws JSONException{
		super(jsonObj);
	}
	
	public HTopology(String jsonString) throws JSONException{
		super(jsonString);
	}
	
	
	public JSONArray getAdapters(){
		JSONArray adapters;
		try {
			adapters = this.getJSONArray("adapters");
		} catch (Exception e) {
			adapters = null;
		}
		return adapters;
	}
	
	public void setAdapters(JSONArray adapters){
		try {
			if(adapters == null){
				this.remove("adapters");
			}else{
				this.put("adapters",adapters);
			}
		} catch (JSONException e) {
			logger.debug("Can not set the adapters attribute.", e);
		}
	}

    public JSONObject getProperties(){
        try {
            return getJSONObject("properties");
        } catch (Exception e) {
            return null;
        }
    }

    public void setProperties(JSONObject properties){
        try {
            if(properties == null){
                this.remove("properties");
            }else{
                this.put("properties",properties);
            }
        } catch (JSONException e) {
            logger.error("Can not set the properties attribute.", e);
            // TODO throw e
        }
    }
	
}
