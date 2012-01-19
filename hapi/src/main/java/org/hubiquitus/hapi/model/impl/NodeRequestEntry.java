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

package org.hubiquitus.hapi.model.impl;


import java.util.UUID;

import org.hubiquitus.hapi.model.NodeQuery;
import org.hubiquitus.hapi.model.ObjectMapperInstanceDomainKey;
import org.hubiquitus.hapi.model.PublishEntry;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Type hDataRequest
 * @author o.chauvie
 */
public class NodeRequestEntry extends IQ implements PublishEntry  {

	private Logger logger = LoggerFactory.getLogger(NodeRequestEntry.class);
	
	public static final String PREFIX_NAMESPACE = "hubiquitus";
	public static final String NAMESPACE_SEPARATOR = ":";
	public static final String SUFFIX_NAMESPACE = "noderequestentry";
	public static final String NAMESPACE = PREFIX_NAMESPACE + NAMESPACE_SEPARATOR + SUFFIX_NAMESPACE;
	public static final String ENTRY_NAME = "NodeRequestEntry";
	public static final String QUERY_ROOT_ELEMENT = "query";
	public static final String QUERY_JSON_DATA_QUERY_ELEMENT = "json-data-query";
	
	private String entryType = ENTRY_NAME;

	/**
	 * Request Id
	 */
	private String requestId;
	
	private NodeQuery nodeQuery = new NodeQuery();
	
	/**
	 * Constructor
	 */
	public NodeRequestEntry() {
		super();
		requestId = UUID.randomUUID().toString();
		super.setPacketID(requestId);
		super.setType(IQ.Type.SET);

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
    
	@Override
	public String getEntryType() {
		return entryType;
	}

    /**
     * setter entryType
     * @param entryType the entryType to set
     */
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	@Override
	public String entryToJsonFormat() {
		String jsonDataQuery = null;
		try {
			jsonDataQuery = ObjectMapperInstanceDomainKey.PUBLISH_ENTRY.getMapper().writeValueAsString(this.getNodeQuery());
		} catch (Exception e) {
			// Do nothing
			logger.warn("Could not convert object to Json String", e);
		}
    	return jsonDataQuery;
	}

	@Override
	public String getChildElementXML() {

		StringBuffer result = new StringBuffer();
		result.append("<" + QUERY_ROOT_ELEMENT +" xmlns=\"" + NAMESPACE + "\">");
		result.append("<" + QUERY_JSON_DATA_QUERY_ELEMENT + ">");
		String jsonDataQuery = null;
		try {
			jsonDataQuery = ObjectMapperInstanceDomainKey.PUBLISH_ENTRY.getMapper().writeValueAsString(this.getNodeQuery());
		} catch (Exception e) {
			// Do nothing
			logger.warn("Could not convert object to Json String", e);
		}
		result.append(StringUtils.escapeForXML(jsonDataQuery));
		result.append("</" + QUERY_JSON_DATA_QUERY_ELEMENT + ">");
		result.append("</" + QUERY_ROOT_ELEMENT + ">");
		return result.toString();
	}

	/**
	 * @return the nodeQuery
	 */
	public NodeQuery getNodeQuery() {
		return nodeQuery;
	}
}
