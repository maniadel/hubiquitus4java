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

package org.hubiquitus.hapi.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import org.hubiquitus.hapi.model.impl.MessagePublishEntry;

public class UpdateDataQuery {

	/**
	 * Request Id
	 */
	private String requestId;
	
	/**
	 * Data base name
	 */
	private String dbName;
	
	/**
	 * Data base collection
	 */
	private String collectionName;
	
	/**
	 * Keys map to use for the request to find the document to be updated
	 */
    private Map<String, String> paramsRequest;
    
    private MessagePublishEntry data;

	/**
	 * Constructor
	 */
	public UpdateDataQuery() {
		super();
		requestId = UUID.randomUUID().toString();
		paramsRequest = new HashMap<String, String>();
	}

	/**
     * getter requestId
     * @return the requestId
     */
    public String getRequestId() {
		return requestId;
	}

    /**
     * setter requestId
     * @param requestId the requestId to set
     */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
    
    
    /**
     * getter dbName
     * @return the dbName
     */
    public String getDbName() {
		return dbName;
	}

    /**
     * setter dbName
     * @param dbName the dbName to set
     */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * getter collectionName
	 * @return the collectionName
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * setter collectionName
	 * @param collectionName the collectionName to set
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	/**
	 * getter requestKeys
	 * @return the requestKeys
	 */
	public Map<String, String> getParamsRequest() {
		return paramsRequest;
	}
	
	/**
	 * Add a param in paramsRequest
	 * @param the paramRequest to add
	 */
	public void addParamsRequest(String key, String value) {
		paramsRequest.put(key, value);
	}

	/**
	 * setter requestKeys
	 * @param requestKeys the requestKeys to set
	 */
	public void setParamsRequest(Map<String, String> paramsRequest) {
		this.paramsRequest = paramsRequest;
	}

	/**
	 * @return the data
	 */
	public MessagePublishEntry getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(MessagePublishEntry data) {
		this.data = data;
	}
}
