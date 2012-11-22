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
import org.hubiquitus.hapi.hStructures.HCondition;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HActor extends JSONObject {
	final Logger logger = LoggerFactory.getLogger(HActor.class);

	public HActor() {
		super();
	};

	public HActor(JSONObject jsonObj) throws JSONException{
		super(jsonObj, JSONObject.getNames(jsonObj));
	}


	public HActor(String jsonString) throws JSONException {
		super(jsonString);
	}

	/* Getters & Setters */
	/**
	 * Return the type of the hActor. 
	 * It contains the name of the implementation used for the hActor. 
	 * It could be the name of a Node.JS file or the name of a Java class. 
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
	 * Set the type of the hActor.
	 * @param type
	 * @throws MissingAttrException 
	 */
	public void setType(String type) throws MissingAttrException{
		try {
			if(type == null || type.length()<=0){
				throw new MissingAttrException("type");
			}
			this.put("type", type);
		} catch (JSONException e) {
			logger.error("Can not set type attribute : ", e); 
		}
	}
	
	/**
	 * @return The JID of the actor. Used by the actor to establish a connection with the hserver.
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
	 * Set the JID of the actor. Used by the actor to establish a connection with the hserver.
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
	 * @return The password used by the actor to establish a connection with the hserver.
	 */
	public String getPwd(){
		String pwd;
		try {
			pwd = this.getString("pwd");
		} catch (Exception e) {
			pwd = null;
		}
		return pwd;
	}
	
	/**
	 * Set the password used by the actor to establish a connection with the hserver.
	 * @param pwd
	 */
	public void setPwd(String pwd){
		try {
			if(pwd == null){
				this.remove("pwd");
			}else{
				this.put("pwd", pwd);
			}
		} catch (JSONException e) {
			logger.error("Can not set pwd attribute : ", e); 
		}
	}
	
	/**
	 * @return The endpoint of the hserver, used to establish a connection with the hserver. This property is designed to be used by hubot implemented with the Java hubotSDK.
	 */
	public String getHserver(){
		String hserver;
		try {
			hserver = this.getString("hserver");
		} catch (Exception e) {
			hserver = null;
		}
		return hserver;
	}
	
	/**
	 * Set the endpoint of the hserver, used to establish a connection with the hserver. This property is designed to be used by hubot implemented with the Java hubotSDK.
	 * @param hserver
	 */
	public void setHserver(String hserver){
		try {
			if(hserver == null){
				this.remove("hserver");
			}else{
				this.put("hserver", hserver);
			}
		} catch (JSONException e) {
			logger.error("Can not set hserver attribute : ", e); 
		}
	}
	
	/**
	 * reserved for future usage
	 */
	public String getEndpoint(){
		String endpoint;
		try {
			endpoint = this.getString("endpoint");
		} catch (Exception e) {
			endpoint = null;
		}
		return endpoint;
	}
	
	/**
	 * reserved for future usage
	 * @param endpoint
	 */
	public void setEndpoint(String endpoint){
		try {
			if(endpoint == null){
				this.remove("endpoint");
			}else{
				this.put("endpoint", endpoint);
			}
		} catch (JSONException e) {
			logger.error("Can not set endpoint attribute : ", e); 
		}
	}
	
	/**
	 * @return The filter to set on the current session when the connection with the hserver is established. see setFilter for details
	 */
	public HCondition getFilter(){
		JSONObject jsonObj;
		HCondition filter = null;
		try {
			jsonObj = this.getJSONObject("filter");
			if (jsonObj != null && jsonObj.length() > 0) {
				filter = new HCondition(jsonObj);
			}
		} catch (Exception e) {
		}
		return filter;
	}
	
	/**
	 * Set the filter to set on the current session when the connection with the hserver is established. see setFilter for details
	 * @param filter
	 */
	public void setFilter(HCondition filter){
		try {
			if(filter == null){
				this.remove("filter");
			}else{
				this.put("filter", filter);
			}
		} catch (JSONException e) {
			logger.error("Can not set filter attribute : ", e); 
		}
	}
	
}
