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


package org.hubiquitus.hapi.hStructures;

import java.util.Calendar;
import java.util.Map;

import org.hubiquitus.hapi.util.DateISO8601;

/**
 * @version 0.4
 * hAPI Command. For more info, see Hubiquitus reference
 */

public class HMessage extends HStructure {
	
	public HMessage() {
		super();
	}
	
	public String getHType() {
		return "hmessage";
	}
	
	/**
	 * Mandatory. Filled by the hApi.
	 * @return message id. NULL if undefined
	 */
	public String getMsgid() {
		return (String) this.get("msgid", String.class);
	}

	public void setMsgid(String msgid) {
		this.put("msgid", msgid);
	}

	/**
	 * Mandatory
	 * @return channel id. NULL if undefined 
	 */
	public String getChid() {
		return (String) this.get("chid", String.class);
	}

	public void setChid(String chid) {
		this.put("chid", chid);
	}

	/**
	 * Mandatory. Filled by the hApi if empty.
	 * @return conversation id. NULL if undefined 
	 */
	public String getConvid() {
		return (String) this.get("convid", String.class);
	}

	public void setConvid(String convid) {
		this.put("convid", convid);
	}

	/**
	 * @return type of the message payload. NULL if undefined 
	 */
	public String getType() {
		return (String) this.get("type", String.class);
	}
	
	public void setType(String type) {
		this.put("type", type);
	}

	/**
	 * If UNDEFINED, priority lower to 0. 
	 * @return priority.
	 */
	public HMessagePriority getPriority() {
		HMessagePriority priority = null;
		int priorityInt = (Integer) this.get("priority", Integer.class);
		if(priorityInt < 0 || priorityInt > 5) {
			priority = null;
		} else {
			priority = HMessagePriority.constant(priorityInt);
		}
		return priority;
	}

	public void setPriority(HMessagePriority priority) {
		this.put("priority", priority.value());
	}

	
	/**
	 * Date-time until which the message is considered as relevant.
	 * @return relevance. NULL if undefined
	 */
	public Calendar getRelevance() {
		Calendar relevance = (Calendar) this.get("relevance", Calendar.class);
		if (relevance == null) {
			String relevanceStr = (String) this.get("relevance", String.class);
			if (relevanceStr != null) {
				relevance = (DateISO8601.toCalendar(relevanceStr));
			}
		}
		return relevance;
	}

	public void setRelevance(Calendar relevance) {
		this.put("relevance", relevance);
	}

	/**
	 * If true, the message is not persistent.
	 * @return persist message or not. NULL if undefined
	 */
	public Boolean getTransient() {
		return (Boolean) this.get("transient", Boolean.class);
	}

	public void setTransient(Boolean _transient) {
		this.put("transient", _transient);
	}	
	
	/**
	 * The geographical location to which the message refer.
	 * @return location. NULL if undefined
	 */
	public HLocation getLocation() {
		Map<?,?> locationNativeRep = (Map<?, ?>) this.get("location", Map.class);
		if (locationNativeRep != null) {
			HLocation location = new HLocation();
			location.setNativeObj(locationNativeRep);
			return location;
		}
		return null;
	}

	public void setLocation(HLocation location) {
		this.put("location", location.getNativeObj());
	}
	
	/**
	 * @return author of this message. NULL if undefined 
	 */
	public String getAuthor() {
		return (String) this.get("author", String.class);
	}
	
	public void setAuthor(String author) {
		this.put("author", author);
	}
	
	/**
	 * Mandatory
	 * @return publisher of this message. NULL if undefined 
	 */
	public String getPublisher() {
		return (String) this.get("publisher", String.class);
	}
	
	public void setPublisher(String publisher) {
		this.put("publisher", publisher);
	}
	
	/**
	 * Mandatory.
	 * The date and time at which the message has been published.
	 * @return published. NULL if undefined
	 */
	public Calendar getPublished() {
		Calendar published = (Calendar) this.get("published", Calendar.class);
		if (published == null) {
			String publishedStr = (String) this.get("published", String.class);
			if (publishedStr != null) {
				published = (DateISO8601.toCalendar(publishedStr));
			}
		}
		return published;
	}

	public void setPublished(Calendar published) {
		this.put("published", published);
	}
	
	/**
	 * The list of headers attached to this message.
	 * @return Headers. NULL if undefined
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getHeaders() {
		return (Map<String, Object>) this.get("headers", Map.class);
	}

	public void setHeaders(Map<String, Object> headers) {
		this.put("headers", headers);
	}
	
	/**
	 * The content of the message.
	 * Should be either a native object or an hobj object
	 * @return payload. NULL if undefined
	 */
	public Object getPayload() {
		Object payload;
		Object payloadObj = this.get("payload", Object.class);
		String type = this.getType().toLowerCase();
		if (type.equalsIgnoreCase("hmeasure")) {
			payload = new HMeasure();
			((HObj)payload).setNativeObj(payloadObj);
		} else if (type.equalsIgnoreCase("halert")) {
			payload = new HAlert();
			((HObj)payload).setNativeObj(payloadObj);
		} else if (type.equalsIgnoreCase("hack")) {
			payload = new HAck();
			((HObj)payload).setNativeObj(payloadObj);
		} else if (type.equalsIgnoreCase("hconvstate")) {
			payload = new HConvState();
			((HObj)payload).setNativeObj(payloadObj);
		} else {
			payload = payloadObj;
		}
		return payload;
	}

	/**
	 * @warning Object should either be a native object or a HObj obj
	 */
	public void setPayload(Object payload) {
		if (payload instanceof HObj) {
			this.put("payload", ((HObj)payload).getNativeObj());
		} else {
			this.put("paylaod", payload);
		}
	}
}