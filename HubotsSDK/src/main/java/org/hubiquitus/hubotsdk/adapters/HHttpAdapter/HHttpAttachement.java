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
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version v 0.5
 */
public class HHttpAttachement extends JSONObject {

	final Logger logger = LoggerFactory.getLogger(HHttpAttachement.class);
	
	
	public HHttpAttachement() {
		super();
	}
	
	public HHttpAttachement(JSONObject jsonObj) throws JSONException{
		super(jsonObj, JSONObject.getNames(jsonObj));
	}
	
	/* getters and setters */
	/**
	 * @return Name of the attachment.
	 */
	public String getName() {
		String name;
		try {
			name = this.getString("name");
		} catch (Exception e) {
			name = null;
			logger.warn("message: ", e);
		}
		return name;
	}
	
	/**
	 * Set the name of the attachment.
	 * @param name
	 */
	public void setName(String name) {
		try {
			if(name == null){
				logger.error("message: name in hHttpAttacheme is mandatory.");
				return;
			}else{
				this.put("name",name);
			}
		} catch (JSONException e) {
			logger.warn("message: ", e);
		}
	}

	/**
	 * @return Data raw bytes encoded in Base64 for JSONObject.
	 */
	public byte[] getData() {
		byte[] data;
		try {
			data = Base64.decodeBase64(this.getString("data"));
		} catch (Exception e) {
			data = null;
			logger.warn("message: ", e);
		}
		return data;
	}

	/**
	 * Set the data raw bytes.
	 * @param data
	 */
	public void setData(byte[] data) {
		try {
			if(data == null){
				logger.error("message: in hHttpAttacheme is mandatory.");
			}else{
				this.put("data", Base64.encodeBase64(data));
			}
		} catch (JSONException e) {
			logger.warn("message: ", e);
		}
	}

	/**
	 * @return Type of content. eg : application/Octet-stream
	 */
	public String getContentType() {
		String contentType;
		try {
			contentType = this.getString("contentType");
		} catch (Exception e) {
			contentType = null;
			logger.warn("message: ", e);
		}
		return contentType;
	}

	/**
	 * Set the type of content.
	 * @param contentType
	 */
	public void setContentType(String contentType) {
		try {
			if(contentType == null){
				logger.error("message: contentType in hHttpAttacheme is mandatory");
				return;
			}else{
				this.put("contentType", contentType);
			}
		} catch (JSONException e) {
			logger.warn("message: ", e);
		}
	}
	
}
