/*
 * Copyright (c) Novedia Group 2012.
 *
 *    This file is part of Hubiquitus
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy
 *    of this software and associated documentation files (the "Software"), to deal
 *    in the Software without restriction, including without limitation the rights
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *    of the Software, and to permit persons to whom the Software is furnished to do so,
 *    subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies
 *    or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *    INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *    PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 *    FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 *    ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *    You should have received a copy of the MIT License along with Hubiquitus.
 *    If not, see <http://opensource.org/licenses/mit-license.php>.
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
