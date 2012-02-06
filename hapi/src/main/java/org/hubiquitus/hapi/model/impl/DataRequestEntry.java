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


import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.UUID;


import org.hubiquitus.hapi.model.PublishEntry;
import org.jivesoftware.smack.packet.IQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;




/**
 * Type hDataRequest
 * @author o.chauvie
 */
public class DataRequestEntry extends IQ implements PublishEntry  {

	Logger logger = LoggerFactory.getLogger(DataRequestEntry.class);
	
	public static final String NAMESPACE = "hubiquitus:datarequestentry";
	public static final String ENTRY_NAME = "DataRequestEntry";
	
	public static final String TYPE_FIND = "find";
	public static final String TYPE_GROUP = "group";
	public static final String TYPE_CMD = "cmd";
	public static final String TYPE_DISTINCT = "distinct";
	
	/**
	 * Constructor
	 */
	public DataRequestEntry() {
		super();
		requestId = UUID.randomUUID().toString();
		paramsRequest = new ArrayList<ParamRequest>();
		resultsList = new ArrayList<DataResultEntry>();
		requestType = TYPE_FIND;
		groupKeys = new ArrayList<String>();
	}

	/**
	 * Request Id
	 */
	private String requestId;
	
	/**
	 * Request type (find, count, group)
	 */
	private String requestType;
	
	/**
	 * Data base name
	 */
	private String dbName;
	
	/**
	 * Data base collection
	 */
	private String collectionName;
	
	/**
	 * Keys map to use for the request
	 */
    private List<ParamRequest> paramsRequest;
    
    /**
	 * Keys map to use for the request
	 */
    private String commandRequest;
    
    
    private List<String> groupKeys;
    
    private String reduce;
    
    private String initialKey;
    private int initialValue;
    
    /**
	 * Result list of the request (return by the hServer)
	 */
    private List<DataResultEntry> resultsList;

    /**
	 * The count is not the size of the result list.
	 * This is the the number of objects matching the query and it does not take limit/skip into consideration.
     */
    private Integer count;

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
	 * Getter requestType
	 * @return the requestType
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * Setter requestType
	 * @param requestType the requestType to set
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
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
	public List<ParamRequest> getParamsRequest() {
		return paramsRequest;
	}
	
	
	
	/**
	 * Getter commandRequest
	 * @return the commandRequest
	 */
	public String getCommandRequest() {
		return commandRequest;
	}

	/**
	 * Setter commandRequest
	 * @param commandRequest the commandRequest to set
	 */
	public void setCommandRequest(String commandRequest) {
		this.commandRequest = commandRequest;
	}

	/**
	 * Add a param in paramsRequest
	 * @param the paramRequest to add
	 */
	public void addParamsRequest(ParamRequest paramRequest) {
		paramsRequest.add(paramRequest);
	}

	/**
	 * setter requestKeys
	 * @param requestKeys the requestKeys to set
	 */
	public void setParamsRequest(List<ParamRequest> paramsRequest) {
		this.paramsRequest = paramsRequest;
	}

	
	
	/**
	 * Getter keys
	 * @return the keys
	 */
	public List<String> getGroupKeys() {
		return groupKeys;
	}

	/**
	 * Setter keys
	 * @param keys the keys to set
	 */
	public void setGroupKeys(List<String> groupKeys) {
		this.groupKeys = groupKeys;
	}
	
	/**
	 * Setter keys
	 * @param keys the keys to set
	 */
	public void addGroupKey(String groupKey) {
		this.groupKeys.add(groupKey);
	}

	/**
	 * Getter reduce
	 * @return the reduce
	 */
	public String getReduce() {
		return reduce;
	}

	/**
	 * Setter reduce
	 * @param reduce the reduce to set
	 */
	public void setReduce(String reduce) {
		this.reduce = reduce;
	}

	
	/** 
	 * getter resultsList
	 * @return resultsList
	 */
	
	public List<DataResultEntry> getResultsList() {
		return resultsList;
	}

	/**
	 * setter resultsList
	 * @param resultsList the resultsList to set
	 */
	public void setResultsList(List<DataResultEntry> resultsList) {
		this.resultsList = resultsList;
	}

	/**
	 * Add a result in resultsList
	 * @param the paramRequest to add
	 */
	public void addResults(DataResultEntry dataResultEntry) {
		resultsList.add(dataResultEntry);
	}
	
	
	
	/**
	 * Getter initialKey
	 * @return the initialKey
	 */
	public String getInitialKey() {
		return initialKey;
	}

	/**
	 * Setter initialKey
	 * @param initialKey the initialKey to set
	 */
	public void setInitialKey(String initialKey) {
		this.initialKey = initialKey;
	}

	/**
	 * Getter initialValue
	 * @return the initialValue
	 */
	public int getInitialValue() {
		return initialValue;
	}

	/**
	 * Setter initialValue
	 * @param initialValue the initialValue to set
	 */
	public void setInitialValue(int initialValue) {
		this.initialValue = initialValue;
	}

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * Create a copy of this object
	 * @return the copy
	 */
	public DataRequestEntry createCopy(){
		// TODO : to finish
		DataRequestEntry dre = new DataRequestEntry();
		dre.setCollectionName(this.getCollectionName());
		dre.setDbName(this.getDbName());
		for(int i=0; i<this.getParamsRequest().size(); i++){
			ParamRequest paramrequest = this.getParamsRequest().get(i).createCopy();
			dre.getParamsRequest().add(paramrequest);
		}
		return dre;
	}
	
	
	@Override
	public String getEntryType() {
		return ENTRY_NAME;
	}

	@Override
	public String entryToJsonFormat() {
		// TODO: to finish
		String divider = ",";
        String json = "{ \"requestId\" : \"" + getRequestId() + "\"" + divider;
        json = json +  " \"dbName\" : \"" + getDbName() + "\"" + divider;
        json = json + " \"collectionName\" : \"" + getCollectionName() + "\"" + divider;
        json = json + " \"commandRequest\" : \"" + getCommandRequest() + "\"" + divider;
        json = json + " \"requestKeys\" : \""; //[";
        json = json + "<params>";
        for (int i=0; i<paramsRequest.size(); i++){
        	json = json + paramsRequest.get(i).toXML();
        }
        json = json + "</params>\"";
        json = json + "}";
    	return json;
	}

	
	
	@Override
	public String getChildElementXML() {
		
		StringBuffer buf = new StringBuffer();
        buf.append("<query xmlns=\"" + NAMESPACE + "\">");
        buf.append("<dbname>").append(getDbName()).append("</dbname>");
        buf.append("<collectionName>").append(getCollectionName()).append("</collectionName>");
        
        buf.append("<requestType>").append(getRequestType()).append("</requestType>");
        
        if (commandRequest!=null && !"".equals(commandRequest)) {
        	buf.append("<commandRequest>").append(getCommandRequest()).append("</commandRequest>");
        }
        
        if (getParamsRequest().size()>0) {
	        buf.append("<requestKeys>");
	        buf.append("<params>");
	        for(int i=0; i<getParamsRequest().size(); i++){
	        	buf.append(getParamsRequest().get(i).toXML());
	        }
	        buf.append("</params>");
	        buf.append("</requestKeys>");
        }
        
        if (groupKeys.size()> 0) {
        	buf.append("<groupKeys>");
        	for (int i=0; i<groupKeys.size(); i++) {
        		String key = groupKeys.get(i);
        		buf.append("<groupKey>").append(key).append("</groupKey>");
        	}
        	buf.append("</groupKeys>");
        }
        
        if (reduce!=null && !"".equals(reduce)) {
        	buf.append("<reduce>").append(reduce).append("</reduce>");
        }
        
        if (initialKey!=null && !"".equals(initialKey)) {
        	buf.append("<initialKey>").append(initialKey).append("</initialKey>");
        }
        
        
        buf.append("<initialValue>").append(initialValue).append("</initialValue>");
        
        
      
        if(!resultsList.isEmpty()){
        	buf.append("<results>");
        	for(int i=0; i<resultsList.size(); i++){
        		DataResultEntry dataResultEntry = resultsList.get(i);
	        	buf.append("<" + DataResultEntry.ELEMENTNAME + ">");
	        	buf.append("<" + DataResultEntry.TYPE + ">").append(dataResultEntry.getType()).append("</" + DataResultEntry.TYPE + ">");
	        	buf.append("<" + DataResultEntry.AUTHOR + ">").append(dataResultEntry.getAuthor()).append("</" + DataResultEntry.AUTHOR + ">");
	        	buf.append("<" + DataResultEntry.PUBLISHER + ">").append(dataResultEntry.getPublisher()).append("</" + DataResultEntry.PUBLISHER + ">");
	        	buf.append("<" + DataResultEntry.PUBLISHEDDATE + ">").append(dataResultEntry.getPublishedDate()).append("</" + DataResultEntry.PUBLISHEDDATE + ">");
	        	buf.append("<" + DataResultEntry.PAYLOAD + ">").append(dataResultEntry.getPayload()).append("</" + DataResultEntry.PAYLOAD + ">");
	        	buf.append("</" + DataResultEntry.ELEMENTNAME + ">");
        	}
        	buf.append("</results>");
        }
      
        buf.append("</query>");
		
        return buf.toString();
	}
	
	/**
	 * Transform the iq response from hServer in list of {@link DataResultEntry} 
	 * @param xml the string xml of iq response
	 */
	public void addDataResultsEntry(String xml) {
		DocumentBuilder parser;
		try {
			parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = parser.parse(new InputSource(new StringReader(xml)));
			
			NodeList results = document.getElementsByTagName(DataResultEntry.ELEMENTNAME);
			for (int i=0; i<results.getLength(); i ++) {
				DataResultEntry dataResultEntry = new DataResultEntry();
				Node result = results.item(i);
				NodeList childs = result.getChildNodes();
				for (int j=0; j<childs.getLength() ; j++) {
					Node child = childs.item(j);
					String name = child.getNodeName();
					String value = child.getTextContent();
					
					if (DataResultEntry.TYPE.equals(name)) {
						dataResultEntry.setType(value);
					} else if (DataResultEntry.AUTHOR.equals(name)) {
						dataResultEntry.setAuthor(value);
					} else if (DataResultEntry.PUBLISHER.equals(name)) {
						dataResultEntry.setPublisher(value);
					} else if (DataResultEntry.PUBLISHEDDATE.equals(name)) {
						Date date = MessagePublishEntry.getFormatedPublishedDate(value);
						if (date==null) {
							logger.error("Can't parse the date: " + value + " in " + DataResultEntry.PUBLISHEDDATE);
						}
						dataResultEntry.setPublishedDate(date);
					}  else if (DataResultEntry.PAYLOAD.equals(name)) {
						dataResultEntry.setPayload(value);
					}  else if (DataResultEntry.HEADER.equals(name)) {
						dataResultEntry.setHeader(value);
					}  else if (DataResultEntry.LOCATION.equals(name)) {
						dataResultEntry.setLocation(value);
					}
				}
				this.addResults(dataResultEntry);
			}
			
		} catch (ParserConfigurationException e) {
			logger.error("Parser error for xml:" + xml + " - " + e.getMessage());
		} catch (SAXException e) {
			logger.error("Saxe error for xml:" + xml + " - " + e.getMessage());
		} catch (IOException e) {
			logger.error("IO error for xml:" + xml + " - " + e.getMessage());
		}
	}	

}

