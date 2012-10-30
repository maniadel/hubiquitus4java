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


package org.hubiquitus.hubotsdk.adapters.HHttpAdapter;

import org.apache.commons.codec.binary.Base64;
import org.hubiquitus.hapi.exceptions.MissingAttrException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version v 0.5
 */
public class HHttpData extends JSONObject {
	
	final Logger logger = LoggerFactory.getLogger(HHttpData.class);
	
	
	public HHttpData() {
		super();
	}
	
	public HHttpData(JSONObject jsonObj) throws JSONException{
		super(jsonObj, JSONObject.getNames(jsonObj));
	}

	/* getters and setters */
	/**
	 * @return Post attachments sent with the query.
	 */
	public JSONObject getAttachments() {
		JSONObject attachments = null;
		try {
			attachments = this.getJSONObject("attachments");
		} catch (JSONException e) {
            // NOP
		}
		return attachments;
	}

	public void setAttachments(JSONObject attachments) {
			try {
                if(attachments == null || attachments.length()<=0)
				    this.remove("attachments");
                else
                    this.put("attachments", attachments);
			} catch (JSONException e) {
                logger.error("can not update attachments attribute: ", e);
			}
	}

	/**
	 * @return Body content as raw bytes encoded in Base64 for json.
	 */
    @SuppressWarnings("unused")
	public byte[] getRawBody() {
		String encodedRawBody;
		byte[] rawBody = null;
		try {
			encodedRawBody = this.getString("rawBody");
			if (encodedRawBody != null) {
				rawBody = Base64.decodeBase64(encodedRawBody);
			}
		} catch (JSONException e) {
            // NOP
		}
		return rawBody;
	}

	/**
	 * Set body content.
	 * @param rawBody the body content
	 */
	public void setRawBody(byte[] rawBody) {
		try {
			if(rawBody == null){
				this.remove("rawBody");
			}else{
				this.put("rawBody", Base64.encodeBase64String(rawBody));
			}
		} catch (JSONException e) {
            logger.error("can not update rawBody attribute: ", e);
		}
		
	}

	/**
	 * @return The method of the data. Possible values : get, post, put, delete
	 */
    @SuppressWarnings("unused")
	public String getMethod() {
		String method = null;
		try {
			method = this.getString("method");
		} catch (JSONException e) {
            logger.error("can not fetch method attribute: ", e);
        }
		return method;
	}

	/**
	 * Define the method of the data.
	 * @param method Possible values : get, post, put, delete
	 * @throws MissingAttrException 
	 */
	public void setMethod(String method) throws MissingAttrException {
		try {
			if(method == null || method.length()<=0){
				throw new MissingAttrException("method");
			}else{
				this.put("method", method);
			}
		} catch (JSONException e) {
            logger.error("can not update method attribute: ", e);
		}
	}

	/**
	 * @return Parameters applied to the URI. eg : “?a=2”
	 */
	public String getQueryArgs() {
		String queryArgs = null;
		try {
			queryArgs = this.getString("queryArgs");
		} catch (JSONException e) {
            // NOP
		}
		return queryArgs;
	}

	/**
	 * Set the parameters applied to the URI.
	 * @param queryArgs eg : “?a=2”
	 */
	public void setQueryArgs(String queryArgs) {
		try {
			if(queryArgs == null){
				this.remove("queryArgs");
			}else{
				this.put("queryArgs", queryArgs);
			}
		} catch (JSONException e) {
            logger.error("can not update queryArgs attribute: ", e);
		}
	}

	/**
	 * @return Path to the resource. eg : “/path” 
	 */
	public String getQueryPath() {
		String queryPath = null;
		try {
			queryPath = this.getString("queryPath");
		} catch (JSONException e) {
            logger.error("can not fetch queryPath attribute: ", e);
		}
		return queryPath;
	}

	/**
	 * Set path to the resource.
	 * @param queryPath eg : "/path"
	 */
	public void setQueryPath(String queryPath) {
		try {
			if(queryPath == null){
				this.remove("queryPath");
			}else{
				this.put("queryPath", queryPath);
			}
		} catch (JSONException e) {
            logger.error("can not update queryPath attribute: ", e);
		}
	}

	/**
	 * @return Host name used to do the query. eg : “localhost”
	 */
	public String getServerName() {
		String serverName = null;
		try {
			serverName = this.getString("serverName");
		} catch (JSONException e) {
            logger.error("can not fetch serverName attribute: ", e);
		}
		return serverName;
	}

	/**
	 * Set the host name used to do the query.
	 * @param serverName  eg : "localhost"
	 * @throws MissingAttrException 
	 */
	public void setServerName(String serverName) throws MissingAttrException {
		try {
			if(serverName == null || serverName.length()<=0){
				throw new MissingAttrException("serverName");
			}else{
				this.put("serverName", serverName);
			}
		} catch (JSONException e) {
            logger.error("can not update serverName attribute: ", e);
		}
	}

	/**
	 * @return Port used to do the query. eg : 8080
	 */
	public int getServerPort() {
		int serverPort = 0;
		try {
			serverPort = this.getInt("serverPort");
		} catch (JSONException e) {
            // NOP
		}
		return serverPort;
	}

	/**
	 * Set port used to do the query.
	 * @param serverPort eg : 8080
	 */
	public void setServerPort(int serverPort) {
		try {
			this.put("serverPort", serverPort);
		} catch (JSONException e) {
            logger.error("can not update serverPort attribute: ", e);
		}
	}
}
