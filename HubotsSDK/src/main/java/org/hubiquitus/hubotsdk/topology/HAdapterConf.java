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

import org.hubiquitus.hapi.exceptions.MissingAttrException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HAdapterConf extends JSONObject {
	final Logger logger = LoggerFactory.getLogger(HAdapterConf.class);
	public HAdapterConf(){
		super();
	}
	
	public HAdapterConf(JSONObject jsonObj) throws JSONException{
		super(jsonObj, JSONObject.getNames(jsonObj));
	}
	
	/**
	 * @return The JID of the actor.
	 */
	public String getActor(){
		String actor;
		try {
			actor = this.getString("actor");
		} catch (Exception e) {
			actor = null;
		}
		return actor;
	}
	
	/**
	 * Set the JID of the actor. 
	 * @param actor
	 * @throws MissingAttrException 
	 */
	public void setActor(String actor) throws MissingAttrException{
		try {
			if(actor == null || actor.length()<=0){
				throw new MissingAttrException("actor");
			}
			this.put("actor", actor);
		} catch (JSONException e) {
			logger.error("Can not set actor attribute : ", e); 
		}
	}
	
	/**
	 * @return The type of adapter. It must be the class used to implement the adaptor. This value is ignore if the actor is a channel
	 */
	public String getType(){
		String type;
		try {
			type = this.getString("type");
		} catch (Exception e) {
			type = null;
		}
		return type;
	}
	
	/**
	 * Set the type of adapter. It must be the class used to implement the adaptor. This value is ignore if the actor is a channel
	 * @param type
	 */
	public void setType(String type){
		try {
			if(type == null){
				this.remove("type");
				return;
			}
			this.put("type", type);
		} catch (JSONException e) {
			logger.error("Can not set type attribute : ", e); 
		}
	}

	//depends on the type of adapter used
	
	public JSONObject getProperties(){
		JSONObject properties;
		try {
			properties = this.getJSONObject("properties");
		} catch (Exception e) {
			properties = null;
		}
		return properties;
	}
	
    public void setProperties(JSONObject properties){
    	try {
			if(properties == null){
				this.remove("properties");
			}else{
				this.put("properties", properties);
			}
		} catch (JSONException e) {
			logger.error("Can not set properties attribute : ", e); 
		}
    }
	
	
	
}
