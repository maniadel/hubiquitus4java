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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hubiquitus.hapi.exception.EntryException;
import org.hubiquitus.hapi.model.PublishEntry;
import org.hubiquitus.hapi.utils.impl.DateUtils;

/**
 * hAPI complex type
 * @author o.chauvie
 */

public class ComplexPublishEntry implements PublishEntry {

	
	public static final String ENTRY_TYPE = "hComplex";
	public static final String CONVID = "convid";
	public static final String PUBLISHED = "published";               
	public static final String MESSAGES = "messages";  
	
	/**
	 * Provides a permanent, universally unique identifier 
	 * for the conversation in the form of an absolute IRI 
	 */
	private String convid;
	
	/**
	 * The description of the topic of the conversation in a comprehensible form 
	 */
    private String topic;
    
    /**
     * The criticity of the conversation 
     */
    private int criticity;
    
    /**
     * The geographical location to which the conversation refers.
     * If omitted, the conversation is considered unallocated 
     */
    private LocationPublishEntry location; 	

    /**
     * The date and time at which the conversation was first published
     */
    private Date published;
    
    /**
     * The date and time at which the previously published conversation has been modified
     */
    private Date updatedDate;
    
    /**
     * The domain to which the conversation belongs
     */
    private String domain;
    
    /**
     * The JID of the creator of the conversation
     */
    private String host;
    
    /**
     * The list of JIDs of the entities participating to this conversation
     */
    private List<String> participants;
    
    /**
     * The list of headers attached to this conversation. It is not mandatory to specify any header
     */
    private List<HeaderPublishEntry> headers;
    
    /**
     * The ordered list of messages exchanged between the participants
     */
    private List<MessagePublishEntry> messages;
	
	/**
	 * Constructor with default values
	 */
	public ComplexPublishEntry() {
		super();
		this.criticity = 1; 
	}

    
    /**
     * getter convid
     * @return the convid
     */
    public String getConvid() {
		return convid;
	}
    
    /**
     * setter convid
     * @param convid the convid to set
     */
	public void setConvid(String convid) {
		this.convid = convid;
	}
	
	/**
	 * Generate a new convid
	 */
	public void generateConvid() {
		this.convid = UUID.randomUUID().toString();
	}
	
	/**
     * getter topic
     * @return the topic
     */
	public String getTopic() {
		return topic;
	}
	
	/**
	 * setter topic
	 * @param topic the of topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
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
	 * @param criticity the of criticity to set
	 */
	public void setCriticity(int criticity) {
		this.criticity = criticity;
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
	 * @param location the of location to set
	 */
	public void setLocation(LocationPublishEntry location) {
		this.location = location;
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
	 * @param published the of published to set
	 */
	public void setPublished(Date published) {
		this.published = published;
	}
	
	/**
     * getter updatedDate
     * @return the updatedDate
     */
	public Date getUpdatedDate() {
		return updatedDate;
	}
	
	/**
	 * setter updatedDate
	 * @param updatedDate the of updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	/**
     * getter domain
     * @return the domain
     */
	public String getDomain() {
		return domain;
	}
	
	/**
	 * setter domain
	 * @param domain the of domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	/**
     * getter host
     * @return the host
     */
	public String getHost() {
		return host;
	}
	
	/**
	 * setter host
	 * @param host the of host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * getter participants
	 * @return the list of participants
	 */
	public List<String> getParticipants() {
		if (participants == null ) {
			participants = new ArrayList<String>();
		}
		return participants;
	}
	
	/**
	 * setter participants
	 * @param participants the list of participants to set
	 */
	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}
	
	/**
	 * Add a participant to the list of participants
	 * @param participant the participant to add
	 */
	public void addParticipant(String participant) {
		if (participant != null) {
			getParticipants().add(participant);			
		}
	}
	
	/**
	 * getter headers
	 * @return the list of headers
	 */
	public List<HeaderPublishEntry> getHeaders() {
		if (headers == null ) {
			headers = new ArrayList<HeaderPublishEntry>();
		}
		return headers;
	}
	
	/**
	 * setter headers
	 * @param headers the list of headers to set
	 */
	public void setHeaders(List<HeaderPublishEntry> headers) {
		this.headers = headers;
	}
	
	/**
	 * Add a header to the list of headers
	 * @param header the header to add
	 */
	public void addHeader(HeaderPublishEntry header) {
		if (header != null) {
			getHeaders().add(header);			
		}
	}
	
	/**
	 * getter messages
	 * @return the list of messages
	 */
	public List<MessagePublishEntry> getMessages() {
		if (messages == null ) {
			messages = new ArrayList<MessagePublishEntry>();
		}
		return messages;
	}
	
	
	/**
	 * setter messages
	 * @param messages the list of messages to set
	 */
	public void setMessages(List<MessagePublishEntry> messages) {
		this.messages = messages;
	}

	/**
	 * Add a message to the list of messages
	 * @param message the message to add
	 */
	public void addMessage(MessagePublishEntry message) {
		if (message != null) {
			getMessages().add(message);			
		}
	}

	@Override
	public String getEntryType() {
		return ENTRY_TYPE;
	}
	
	
	@Override
	public String entryToJsonFormat() {
		String divider = ",";
        String json = "";
        
        json = "{ \"" + CONVID + "\" : \"" + getConvid() + "\"" + divider;
        if (getTopic()!=null && !"".equals(getTopic())) {
        	json = json + " \"topic\" : \"" + getTopic() + "\"" + divider;
        }
        json = json + " \"criticity\" : " + getCriticity() +  divider;
        if (getLocation()!=null) {
        	json = json + " \"location\" : " + getLocation().entryToJsonFormat() + divider;
        }
        json = json + " \"" + PUBLISHED + "\" : \"" + DateUtils.formatDate(getPublished(), DateUtils.FORMAT_DATE_YYYYslMMslDD_HHMMSS) + "\"" + divider;
        if (getUpdatedDate()!=null ) {
        	json = json + " \"updated\" : \"" + DateUtils.formatDate(getUpdatedDate(), DateUtils.FORMAT_DATE_YYYYMMDDHHMMSS) + "\"" + divider;
        }
        json = json + " \"domain\" : \"" + getDomain() + "\"" + divider;
        json = json + " \"host\" : \"" + getHost() + "\"" + divider;
        
        // Participants
        json = json + " \"participants\" : ["; 
        List<String> participants = getParticipants();
        for (int i=0; i<participants.size() ; i++ ) {
        	if (i>0) {
        		json = json + divider;
        	}
        	String participant = participants.get(i);
        	json = json + "\"" + participant + "\" ";  
        }
        json = json + "]" + divider;

        //	Headers
        if (getHeaders().size()>0) {
	        json = json + " \"headers\" : [";
	        List<HeaderPublishEntry> headers = getHeaders();
	        for (int i=0; i<headers.size(); i++ ) {
	        	if (i>0) {
	        		json = json + divider;
	        	}
	        	HeaderPublishEntry header = headers.get(i);
	        	json = json + header.entryToJsonFormat();  
	        }
	        json = json + "]" + divider;
        }

        // Messages
        json = json + " \"" + MESSAGES + "\" : [";
        List<MessagePublishEntry> messages = getMessages();
        for (int i=0; i<messages.size(); i++ ) {
        	if (i>0) {
        		json = json + divider;
        	}
        	MessagePublishEntry message = messages.get(i);
        	json = json + message.entryToJsonFormat();  
        }
        json = json + "]";
    
        json = json + "}";
        		
    	return json;
	}    
	
	
	/**
	 * 
	 * @param body
	 * @return
	 * @throws JsonException
	 */
    public static ComplexPublishEntry JsonToMessageComplexPublishEntry(String body) throws EntryException {
    	ObjectMapper objectMapper = new ObjectMapper();
    	ComplexPublishEntry complexPublishEntry = null;
    	try {
    		complexPublishEntry = objectMapper.readValue(body, ComplexPublishEntry.class);	
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
    	return complexPublishEntry;
    }

    
    	
}