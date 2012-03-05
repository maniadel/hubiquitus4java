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
import java.util.Date;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hubiquitus.hapi.exception.EntryException;
import org.hubiquitus.hapi.utils.impl.DateUtils;
import org.jivesoftware.smackx.packet.PrivateData;

/**
 * Entry for data result from data base request
 * @author o.chauvie
 */
public class DataResultEntry implements PrivateData {

	public static final String NAMESPACE="hubiquitus:dataresultentry";
	public static final String ELEMENTNAME="result";
	
	public static final String ENTRY_TYPE = "hDataResult";
	public static final String MSGID = "msgid";
	public static final String CRITICITY = "criticity";
	public static final String TYPE = "type";
	public static final String AUTHOR = "author";
	public static final String PUBLISHER = "publisher";
	public static final String PUBLISHEDDATE = "published";
	public static final String PAYLOAD = "payload";
	public static final String HEADER = "headers";
	public static final String LOCATION = "location";
	
	/**
	 * The type of the message payload 
	 */
	private String type;
	
	/**
	 * The message Id 
	 */
	private String msgId;
	
	/**
	 * The criticity of the message 
	 */
	private int criticity;
	
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
	private Date publishedDate;
	
	/**
	 * The content of the message. It can be plain text or more structured data (HTML, XML, JSON, etc.)
	 */
	private String payload;
	
	/**
	 * The header of the message. It can be plain text or more structured data (HTML, XML, JSON, etc.)
	 */
	private String header;
	

	/**
	 * The location of the message. It can be plain text or more structured data (HTML, XML, JSON, etc.)
	 */
	private String location;

	/**
	 * Constructor with default values
	 */
	public DataResultEntry() {
		super();
	}

	
	
	/**
	 * Getter msgId
	 * @return the msgId
	 */
	public String getMsgId() {
		return msgId;
	}



	/**
	 * Setter msgId
	 * @param msgId the msgId to set
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}



	/**
	 * Getter criticity
	 * @return the criticity
	 */
	public int getCriticity() {
		return criticity;
	}



	/**
	 * Setter criticity
	 * @param criticity the criticity to set
	 */
	public void setCriticity(int criticity) {
		this.criticity = criticity;
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
	 * getter publishedDate
	 * @return the publishedDate
	 */
	public Date getPublishedDate() {
		return publishedDate;
	}
	
	/**
	 * setter publishedDate
	 * @param publishedDate the publishedDate to set
	 */
	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}
	
	
	/**
	 * getter payload
	 * @return the payload
	 */
	public String getPayload() {
		return payload;
	}
	
	/**
	 * setter payload
	 * @param paylod the payload to set
	 */
	public void setPayload(String payload) {
		this.payload = payload;
	}

	
	
	/*
	@Override
	public String getEntryType() {
		return ENTRY_TYPE;
	}
	
	*/
	
	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString(){
		String object = "\ntype : " + type;
		object = object + "\nmsgid : " + msgId;
		object = object + "\ncriticity : " + criticity;
		object = object + "\nauthor : " + author;
		object = object + "\npublisher : " + publisher;
		object = object + "\npublished date : " + publishedDate.toString();
		object = object + "\npayload : " + payload + "\n";
		object = object + "\nheader : " + header + "\n";
		object = object + "\nlocation : " + location + "\n";
		return object;
	}
	
		
	 /**
     * Convert object Json in object java
     * @param JSonObject
     * @return
     * @throws JsonException 
     */
    public static DataResultEntry JsonToMessagePublishEntry(String body) throws EntryException {
    	ObjectMapper objectMapper = new ObjectMapper();
    	DataResultEntry messagePublishEntry = null;
    	try {
    		messagePublishEntry = objectMapper.readValue(body, DataResultEntry.class);	
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

	@Override
	public String getElementName() {
		return DataResultEntry.ELEMENTNAME;
	}

	@Override
	public String getNamespace() {
		return DataResultEntry.NAMESPACE;
	}

	@Override
	public String toXML() {
		StringBuffer buf = new StringBuffer();
		buf.append("<" + ELEMENTNAME + " xlmns=\"" + getNamespace() + "\" >");
			buf.append("<" + MSGID + ">").append(getMsgId()).append("</" + MSGID + ">");
			buf.append("<" + CRITICITY + ">").append(getCriticity()).append("</" + CRITICITY + ">");
			buf.append("<" + TYPE + ">").append(getType()).append("</" + TYPE + ">");
	    	buf.append("<" + AUTHOR + ">").append(getAuthor()).append("</" + AUTHOR + ">");
	    	buf.append("<" + PUBLISHER + ">").append(getPublisher()).append("</" + PUBLISHER + ">");
	    	String dateSt = DateUtils.formatDate(getPublishedDate(), DateUtils.FORMAT_DATE_YYYYslMMslDD_HHMMSS);
	    	buf.append("<" + PUBLISHEDDATE + ">").append(dateSt).append("</" + PUBLISHEDDATE + ">");
	    	buf.append("<" + PAYLOAD + ">").append(getPayload()).append("</" + PAYLOAD + ">");
	    	buf.append("<" + HEADER + ">").append(getHeader()).append("</" + HEADER + ">");
	    	buf.append("<" + LOCATION + ">").append(getLocation()).append("</" + LOCATION + ">");
	    buf.append("</" + ELEMENTNAME + ">");
        return buf.toString();
	}	
	
	
}