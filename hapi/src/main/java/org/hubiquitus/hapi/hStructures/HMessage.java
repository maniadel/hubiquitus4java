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

import org.hubiquitus.hapi.util.DateISO8601;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @version 0.5 hAPI Command. For more info, see Hubiquitus reference
 */

public class HMessage extends JSONObject {


	public HMessage() {
		super();
	}

	public HMessage(JSONObject jsonObj) throws JSONException {
		super(jsonObj, JSONObject.getNames(jsonObj));
	}

	/* Getters & Setters */

	/**
	 * Mandatory. Filled by the hApi.
	 * 
	 * @return message id. NULL if undefined
	 */
	public String getMsgid() {
		String msgid;
		try {
			msgid = this.getString("msgid");
		} catch (Exception e) {
			msgid = null;
		}
		return msgid;
	}

	public void setMsgid(String msgid) {
		try {
			if (msgid == null) {
				this.remove("msgid");
			} else {
				this.put("msgid", msgid);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * Mandatory The unique ID of the channel through which the message is
	 * published.
	 * 
	 * The JID through which the message is published. The JID can be that of a
	 * channel (beginning with #) or a user.
	 * 
	 * A special actor called ‘session’ indicates that the HServer should handle
	 * the hMessage.
	 * 
	 * @return actor. NULL if undefined
	 */
	public String getActor() {
		String actor;
		try {
			actor = this.getString("actor");
		} catch (Exception e) {
			actor = null;
		}
		return actor;
	}

	public void setActor(String actor) {
		try {
			if (actor == null) {
				this.remove("actor");
			} else {
				this.put("actor", actor);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * Mandatory. Filled by the hApi if empty.
	 * 
	 * @return conversation id. NULL if undefined
	 */
	public String getConvid() {
		String convid;
		try {
			convid = this.getString("convid");
		} catch (Exception e) {
			convid = null;
		}
		return convid;
	}

	public void setConvid(String convid) {
		try {
			if (convid == null) {
				this.remove("convid");
			} else {
				this.put("convid", convid);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * Since v0.5
	 * 
	 * @return reference to another hMessage msgid. NULL if undefined.
	 */
	public String getRef() {
		String ref;
		try {
			ref = this.getString("ref");
		} catch (Exception e) {
			ref = null;
		}
		return ref;
	}

	/**
	 * Since v0.5 Refers to another hMessage msgid. Provide a mechanism to do
	 * correlation between messages. For example, it is used by the command
	 * pattern and the acknowledgement (see. hAck)
	 * 
	 * @param ref
	 */
	public void setRef(String ref) {
		try {
			if (ref == null) {
				this.remove("ref");
			} else {
				this.put("ref", ref);
			}
		} catch (JSONException e) {
		}

	}

	/**
	 * @return type of the message payload. NULL if undefined
	 */
	public String getType() {
		String type;
		try {
			type = this.getString("type");
		} catch (Exception e) {
			type = null;
		}
		return type;
	}

	public void setType(String type) {
		try {
			if (type == null) {
				this.remove("type");
			} else {
				this.put("type", type);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * If UNDEFINED, priority lower to 0.
	 * 
	 * @return priority.
	 */
	public HMessagePriority getPriority() {
		HMessagePriority priority;
		try {
			int priorityInt = this.getInt("priority");
			if (priorityInt < 0 || priorityInt > 5) {
				priority = null;
			} else {
				priority = HMessagePriority.constant(priorityInt);
			}
		} catch (Exception e1) {
			priority = null;
		}
		return priority;
	}

	public void setPriority(HMessagePriority priority) {
		try {
			if (priority == null) {
				this.remove("priority");
			} else {
				this.put("priority", priority.value());
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * Date-time until which the message is considered as relevant.
	 * 
	 * @return relevance. NULL if undefined
	 */
	public Calendar getRelevance() {
		Calendar relevance;
		try {
			relevance = (DateISO8601.toCalendar(this.getString("relevance")));
			;
		} catch (JSONException e) {
			relevance = null;
		}
		return relevance;
	}

	public void setRelevance(Calendar relevance) {
		try {
			if (relevance == null) {
				this.remove("relevance");
			} else {
				this.put("relevance", DateISO8601.fromCalendar(relevance));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Since v0.5
	 * 
	 * @return persist message or not. NULL if undefined
	 */
	public Boolean getPersistent() {
		Boolean _persistent;
		try {
			_persistent = this.getBoolean("persistent");
		} catch (JSONException e) {
			_persistent = null;
		}
		return _persistent;
	}

	/**
	 * Since v0.5 Possible values are : true : indicates if the message MUST be
	 * persisted by the middleware false: indicates that the message is volatile
	 * and will not be persisted by the middleware. Defaults to true if omitted.
	 * 
	 * @param _persistent
	 */
	public void setPersistent(Boolean _persistent) {
		try {
			if (_persistent == null) {
				this.remove("persistent");
			} else {
				this.put("persistent", _persistent);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * The geographical location to which the message refer.
	 * 
	 * @return location. NULL if undefined
	 */
	public HLocation getLocation() {
		HLocation location;
		try {
			location = new HLocation(this.getJSONObject("location"));
		} catch (JSONException e) {
			location = null;
		}
		return location;
	}

	public void setLocation(HLocation location) {
		try {
			if (location == null) {
				this.remove("location");
			} else {
				this.put("location", location);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return author of this message. NULL if undefined
	 */
	public String getAuthor() {
		String author;
		try {
			author = this.getString("author");
		} catch (Exception e) {
			author = null;
		}
		return author;
	}

	public void setAuthor(String author) {
		try {
			if (author == null) {
				this.remove("author");
			} else {
				this.put("author", author);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * Mandatory
	 * 
	 * @return publisher of this message. NULL if undefined
	 */
	public String getPublisher() {
		String publisher;
		try {
			publisher = this.getString("publisher");
		} catch (Exception e) {
			publisher = null;
		}
		return publisher;
	}

	public void setPublisher(String publisher) {
		try {
			if (publisher == null) {
				this.remove("publisher");
			} else {
				this.put("publisher", publisher);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * Mandatory. The date and time at which the message has been published.
	 * 
	 * @return published. NULL if undefined
	 */
	public Calendar getPublished() {
		Calendar published;
		try {
			published = (DateISO8601.toCalendar(this.getString("published")));
		} catch (JSONException e) {
			published = null;
		}
		return published;
	}

	public void setPublished(Calendar published) {
		try {
			if (published == null) {
				this.remove("published");
			} else {
				this.put("published", DateISO8601.fromCalendar(published));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The list of headers attached to this message.
	 * 
	 * @return Headers. NULL if undefined
	 */
	public JSONObject getHeaders() {
		// HJsonDictionnary headers = new HJsonDictionnary();
		JSONObject headers = null;
		try {
			headers = this.getJSONObject("headers");
		} catch (JSONException e) {
			headers = null;
		}
		return headers;
	}

	public void setHeaders(JSONObject headers) {
		try {
			if (headers == null) {
				this.remove("headers");
			} else {
				this.put("headers", headers);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * When we don't know the type of payload. It will return an object.
	 * 
	 * @return payload. NULL if undefined
	 */
	public Object getPayload() {
		Object payload;
		try {
			payload = this.get("payload");
		} catch (JSONException e) {
			payload = null;
		}
		return payload;
	}

	/**
	 * if payload type is JSONObject
	 * 
	 * @return payload. NULL if undefined
	 */

	public JSONObject getPayloadAsJSONObject() {
		JSONObject payload;
		try {
			payload = this.getJSONObject("payload");
		} catch (JSONException e) {
			payload = null;
		}
		return payload;
	}

	/**
	 * if payload type is JSONArray
	 * 
	 * @return payload. NULL if undefined
	 */
	public JSONArray getPayloadAsJSONArray() {
		JSONArray payload;
		try {
			payload = this.getJSONArray("payload");
		} catch (JSONException e) {
			payload = null;
		}
		return payload;
	}

	/**
	 * if payload type is String
	 * 
	 * @return payload. NULL if undefined
	 */
	public String getPayloadAsString() {
		String payload;
		try {
			payload = this.getString("payload");
		} catch (JSONException e) {
			payload = null;
		}
		return payload;
	}

	/**
	 * if payload type is Boolean
	 * 
	 * @return payload. Null if undefined
	 */
	public Boolean getPayloadAsBoolean() {
		Boolean payload;
		try {
			payload = this.getBoolean("payload");
		} catch (JSONException e) {
			payload = null;
		}
		return payload;
	}

	/**
	 * if payload type is Integer
	 * 
	 * @return payload. Null if undefined.
	 */
	public Integer getPayloadAsInt() {
		Integer payload;
		try {
			payload = this.getInt("payload");
		} catch (JSONException e) {
			payload = null;
		}
		return payload;
	}

	/**
	 * if payload type is Double
	 * 
	 * @return payload, Null if undefined.
	 */
	public Double getPayloadAsDouble() {
		Double payload;
		try {
			payload = this.getDouble("payload");
		} catch (JSONException e) {
			payload = null;
		}
		return payload;
	}


	/**
	 * if payload type is HAlert. if not return null.
	 * @return HAlert. NULL if undefined
	 */
	public HAlert getPayloadAsHAlert() {
		try {
			if (this.getType().toLowerCase().equalsIgnoreCase("halert")) {
				HAlert halert = new HAlert(this.getJSONObject("payload"));
				return halert;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * if payload type is HAck, if not return null.
	 * @return HAck. Null if undefined.
	 */
	public HAck getPayloadAsHAck() {
		try {
			if (this.getType().toLowerCase().equalsIgnoreCase("hack")) {
				HAck hack = new HAck(this.getJSONObject("payload"));
				return hack;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * if payload is HMeasure, if not return null.
	 * @return HMeasure. Null if undefined.
	 */
	public HMeasure getPayloadAsHmeasure() {
		try {
			if (this.getType().toLowerCase().equalsIgnoreCase("hmeasure")) {
				HMeasure hmeasure = new HMeasure(this.getJSONObject("payload"));
				return hmeasure;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * if payload is HConvState, if not return null.
	 * @return HConvState. Null if undefined.
	 */
	public HConvState getPayloadAsHConvState() {
		try {
			if (this.getType().toLowerCase().equalsIgnoreCase("hconvstate")) {
				HConvState hconvstate = new HConvState(this.getJSONObject("payload"));
				return hconvstate;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * if payload is HResult, if not return null.
	 * @return HResult. Null if undefined.
	 */
	public HResult getPayloadAsHResult() {
		try {
			if (this.getType().toLowerCase().equalsIgnoreCase("hresult")) {
				HResult hresult = new HResult(this.getJSONObject("payload"));
				return hresult;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * if payload is HCommand, if not return null.
	 * @return HCommand. Null if undefined.
	 */
	public HCommand getPayloadAsHCommand() {
		try {
			if (this.getType().toLowerCase().equalsIgnoreCase("hcommand")) {
				HCommand hcommand = new HCommand(this.getJSONObject("payload"));
				return hcommand;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	
	

	/**
	 * Payload type could be JSONObject, JSONArray, String, Boolean, Number
	 * 
	 * @param payload
	 */
	public void setPayload(Object payload) {
		try {
			if (payload == null) {
				this.remove("payload");
			} else {
				this.put("payload", payload);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Since v0.5
	 * 
	 * @return timeout. 0 if undefined.
	 */
	public Integer getTimeout() {
		Integer timeout;
		try {
			timeout = this.getInt("timeout");
		} catch (Exception e) {
			timeout = null;
		}
		return timeout;
	}

	/**
	 * Since v0.5 Define the timeout in ms to get an answer to the hMessage.
	 * 
	 * @param timeout
	 */
	public void setTimeout(Integer timeout) {
		try {
			if (timeout == null) {
				this.remove("timeout");
			} else {
				this.put("timeout", timeout);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}