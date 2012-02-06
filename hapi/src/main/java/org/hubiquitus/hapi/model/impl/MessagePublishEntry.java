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
package org.hubiquitus.hapi.model.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hubiquitus.hapi.exception.EntryException;
import org.hubiquitus.hapi.model.PublishEntry;
import org.hubiquitus.hapi.utils.impl.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Message type
 * @author o.chauvie
 */
public class MessagePublishEntry implements PublishEntry {

	public static final String ENTRY_TYPE = "hMessage";
	public static final String MSGID = "msgid";
	public static final String DBNAME = "dbName";
	public static final String HEADERS = "headers";
	public static final String LOCATION = "location";
	public static final String TYPE = "type";
	public static final String AUTHOR = "author";
	public static final String PUBLISHER = "publisher";
	public static final String PUBLISHED = "published";
	public static final String PAYLOAD = "payload";
	public static final String ISPERSISTENCE = "persistence";
	public static final String CRITICITY = "criticity";
	public static final String RELEVANCE = "relevance";
	
	 protected static Logger logger = LoggerFactory.getLogger(MessagePublishEntry.class);

	 private String entryType = ENTRY_TYPE;
	 
	/**
	 * Provides a permanent, universally unique identifier for the message 
	 */
	private String msgid;
	
	/**
	 * The type of the message payload 
	 */
	private String type;
	
	/**
	 * The criticity of the message 
	 */
	private int criticity;
	
	/**
	 * The period during which the event is considered as relevant
	 */
	private String relevance;
	
	/**
	 * The geographical location to which the message refer
	 */
	private LocationPublishEntry location;	
	
	/**
	 * The JID of the author 
	 */
	private String author;
	
	/**
	 * The JID of the client that effectively published the message
	 */
	private String publisher;
	
	/**
	 * The date and time at which the message has been published
	 */
	private Date published;
	
	/**
	 * The list of headers attached to this message
	 */
	private Map<String, String> headers;
	
	/**
	 * The list of JIDs of the participants that have received the message
	 */
	private ArrayList<String> receivedby;
	
	/**
	 * The list of JIDs of the participants that have read the message
	 */
	private ArrayList<String> readby;
	
	/**
	 * Message persistence in database
	 */
	private boolean persistence;
	
	/**
	 * Name of database for storage
	 */
	private String dbName;
	
	/**
	 * The content of the message. It can be plain text or more structured data (HTML, XML, JSON, etc.)
	 */
	private PublishEntry payload;

	/**
	 * Constructor with default values
	 */
	public MessagePublishEntry() {
		super();
		this.criticity = 1; 
		this.relevance = "always"; 
		this.type = "untyped";
		this.persistence = false;
	}

	/**
	 * getter msgid
	 * @return the msgid
	 */
	public String getMsgid() {
		return msgid;
	}
	
	/**
	 * setter msgid
	 * @param msgid the msgid to set
	 */
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	
	/**
	 * Generate a new Msgid
	 */
	public void generateMsgid() {
		this.msgid = UUID.randomUUID().toString();
	}
	
	/**
	 * getter type
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * setter type
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * getter criticity
	 * @return the criticity
	 */
	public int getCriticity() {
		return criticity;
	}
	
	/**
	 * setter criticity
	 * @param criticity the criticity to set
	 */
	public void setCriticity(int criticity) {
		this.criticity = criticity;
	}
	
	/**
	 * getter relevance
	 * @return the relevance
	 */
	public String getRelevance() {
		return relevance;
	}
	
	/**
	 * setter relevance
	 * @param relevance the relevance to set
	 */
	public void setRelevance(String relevance) {
		this.relevance = relevance;
	}
	
	/**
	 * getter location
	 * @return the location
	 */
	public LocationPublishEntry getLocation() {
		return location;
	}
	
	/**
	 * setter location
	 * @param location the location to set
	 */
	public void setLocation(LocationPublishEntry location) {
		this.location = location;
	}
	
	/**
	 * getter author
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * setter author
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * getter publisher
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}
	
	/**
	 * setter publisher
	 * @param publisher the publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	/**
	 * getter published
	 * @return the published
	 */
	public Date getPublished() {
		return published;
	}
	
	/**
	 * setter published
	 * @param published the published to set
	 */
	public void setPublished(Date published) {
		this.published = published;
	}
	
	/**
	 * getter published
	 * @return the published
	 */
	static public Date getFormatedPublishedDate(String dateSt) {
		Date date = null;
		try {
			date = DateUtils.parseDate(dateSt, DateUtils.FORMAT_DATE_YYYYslMMslDD_HHMMSS);
		} catch (ParseException e) {
			//logger.error("Can't parse the date: " + dateSt + " in format: " + DateUtils.FORMAT_DATE_YYYYslMMslDD_HHMMSS);
			logger.error(e.getMessage());
		}
		return date;
	}
	
	/**
	 * getter published in string formated
	 * @return the formated published
	 */
	public String getFormatedPublishedDateToString() {
		return DateUtils.formatDate(published, DateUtils.FORMAT_DATE_YYYYslMMslDD_HHMMSS);
	}
	
	/**
	 * setter publishedDate
	 * @param publishedDate the publishedDate to set
	 */
//	public void setPublishedDate(String publishedDate) {
//		Date date = null;
//		try {
//			date = DateUtils.parseDate(publishedDate, DateUtils.FORMAT_DATE_YYYYslMMslDD_HHMMSS);
//		} catch (ParseException e) {
//			logger.error("Can't parse the date: " + publishedDate + " in format: " + DateUtils.FORMAT_DATE_YYYYslMMslDD_HHMMSS);
//		}
//		this.publishedDate = date;
//	}
	
	/**
	 * getter headers
	 * @return the headers
	 */
	public Map<String, String> getHeaders() {
		if (headers == null ) {
			headers = new HashMap<String, String>(); 
		}
		return headers;
	}
	
	/**
	 * setter headers
	 * @param headers the headers to set
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	
	/**
	 * Add a header to the list of headers
	 * @param header the header to add
	 */
	public void addHeader(String key, String value) {
		if (key != null) {
			getHeaders().put(key, value);			
		}
	}
	
	/**
	 * getter receivedby
	 * @return the receivedby
	 */
	public List<String> getReceivedby() {
		if (receivedby == null) {
			receivedby = new ArrayList<String>(); 
		}
		return receivedby;
	}
	
	/**
	 * setter receivedby
	 * @param receivedby the receivedby to set
	 */
	public void setReceivedby(ArrayList<String> receivedby) {
		this.receivedby = receivedby;
	}
	
	/**
	 * Add a receivedby to the list of receivedby
	 * @param receivedby the receivedby to add
	 */
	public void addReceivedby(String receivedby) {
		if (receivedby != null) {
			getReceivedby().add(receivedby);			
		}
	}
	
	/**
	 * getter readby
	 * @return the readby
	 */
	public ArrayList<String> getReadby() {
		if (readby == null) {
			readby = new ArrayList<String>(); 
		}
		return readby;
	}
	
	/**
	 * setter readby
	 * @param readby the readby to set
	 */
	public void setReadby(ArrayList<String> readby) {
		this.readby = readby;
	}
	
	/**
	 * Add a readby to the list of readby
	 * @param readby the readby to add
	 */
	public void addReadby(String readby) {
		if (readby != null) {
			getReadby().add(readby);			
		}
	}

	/**
	 * getter persistence
	 * @return persistence
	 */
	public boolean isPersistence() {
		return persistence;
	}

	/**
	 * setter persistence
	 * @param persistence the persistence to set
	 */
	public void setPersistence(boolean persistence) {
		this.persistence = persistence;
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
	 * getter payload
	 * @return the payload
	 */
	public PublishEntry getPayload() {
		return payload;
	}
	
	/**
	 * setter payload
	 * @param paylod the payload to set
	 */
	public void setPayload(PublishEntry payload) {
		this.payload = payload;
	}

	@Override
	public String getEntryType() {
		return entryType;
	}
	
	
	@Override
	public String entryToJsonFormat() {
		String divider = ",";
        String json = "";
    	
        json = "{ \"" + MSGID + "\" : \"" + getMsgid() + "\"" + divider;
        if (getType()!=null && !"".equals(getType())) {
        	json = json + " \"" + TYPE + "\" : \"" + getType() + "\"" + divider;
        }
        json = json + " \"criticity\" : " + getCriticity() + divider;
        json = json + " \"relevance\" : \"" + getRelevance() + "\"" + divider;
        if (getLocation()!=null) {
        	json = json + " \"location\" : " + getLocation().entryToJsonFormat() + divider;
        }
        json = json + " \"" + AUTHOR + "\" : \"" + getAuthor() + "\"" + divider;
        if (getPublisher()!=null && !"".equals(getPublisher())) {
        	json = json + " \"" + PUBLISHER + "\" : \"" + getPublisher() + "\"" + divider;
        }
        json = json + " \"" + PUBLISHED + "\" : \"" +   DateUtils.formatDate(getPublished(), DateUtils.FORMAT_DATE_YYYYslMMslDD_HHMMSS) + "\"" + divider;
        json = json + " \"" + ISPERSISTENCE + "\" : \"" +   isPersistence() + "\"" + divider;
        
        if (getDbName()!=null && !"".equals(getDbName())) {
        	json = json + " \"" + DBNAME + "\" : \"" + getDbName()+ "\"" + divider;
        }
        
        // Headers
        Map<String, String> headers = getHeaders();
        if (headers != null && headers.size()>0) {
			json = json + " \"" + HEADERS + "\" : [";
	        boolean firstElement = true;
	        for (Iterator<String> iterator = headers.keySet().iterator(); iterator.hasNext();) {
				String key = iterator.next();
	        	if (firstElement) {
	        		json = json + divider;
	        		firstElement = false;
	        	}
	        	String header = headers.get(key);
	        	json = json + "\"" + header + " \" ";  
			}
	        json = json + "]" + divider;
        }
        
        // Received by
        if (getReceivedby().size()>0) {
	        json = json + " \"receivedby\" : [";
	        List<String> receivedbys = getReceivedby();
	        for (int i=0; i<receivedbys.size(); i++ ) {
	        	if (i>0) {
	        		json = json + divider;
	        	}
	        	String receivedby = receivedbys.get(i);
	        	json = json + "\"" + receivedby + " \" ";  
	        }
	        json = json + "]" + divider;
        }
        
        // Read by
        if (getReadby().size()>0) {
	        json = json + " \"readby\" : [";
	        ArrayList<String> readbys = getReadby();
	        for (int i=0; i<readbys.size(); i++ ) {
	        	if (i>0) {
	        		json = json + divider;
	        	}
	        	String readby = readbys.get(i);
	        	json = json + "\"" + readby + " \" ";  
	        }
	        json = json + "]" + divider;
        }

        // Payload content
        PublishEntry payload = getPayload();
        json = json + " \"" + PAYLOAD + "\" : " + payload.entryToJsonFormat() ;
        
        json = json + "}";
    	
    	return json;
	}
	
	 /**
     * convert object Json in object java
     * @param JSonObject
     * @return
     * @throws JsonException 
     */
    public static MessagePublishEntry JsonToMessagePublishEntry(String body) throws EntryException {
    	ObjectMapper objectMapper = new ObjectMapper();
    	MessagePublishEntry messagePublishEntry = null;
    	try {
    		messagePublishEntry = objectMapper.readValue(body, MessagePublishEntry.class);	
    	}
    	catch (JsonParseException jpE) {
    		throw new EntryException(jpE.getMessage());
    	}
    	catch (JsonMappingException jmE) {
    		throw new EntryException(jmE.getMessage());
    	}
    	catch (IOException ioE) {
    		throw new EntryException(ioE.getMessage());
    	}
    	return messagePublishEntry;
    }

	/**
	 * @param entryType the entryType to set
	 */
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}	
}