package org.hubiquitus.hubotsdk.adapters.HtwitterAdapter;

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

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.hubiquitus.hapi.hStructures.HJsonObj;
import org.hubiquitus.hapi.util.DateISO8601;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @version 0.3
 * Describes a twitter payload
 */
public class HTweet implements HJsonObj{

	private JSONObject htweet= new JSONObject();
	private static Logger log = Logger.getLogger(HTweet.class);

	public HTweet() {};

	public HTweet(JSONObject jsonObj){
		fromJSON(jsonObj);
	}

	/* HJsonObj interface */

	public JSONObject toJSON() {
		return htweet;
	}

	public void fromJSON(JSONObject jsonObj) {
		if(jsonObj != null) {
			this.htweet = jsonObj; 
		} else {
			this.htweet = new JSONObject();
		}
	}

	public String getHType() {
		return "htweet";
	}

	@Override
	public String toString() {
		return htweet.toString();
	}


	@Override
	public int hashCode() {
		return htweet.hashCode();
	}
	

	/* Getters & Setters */
	
	

	public String getTweetText() {
		String text;
		try {
			text = htweet.getString("text");
		} catch (Exception e) {
			text = null;			
		}
		return text;
	}

	public void setTweetText(String text) {
		try {
			if(text == null & text.length()== 0) {
				htweet.remove("text");
			} else {
				htweet.put("text", text);
			}
		} catch (JSONException e) {
			log.error("Can't update the author attribut",e);
		}
	}
	
	
	
	
	/**
	 * The Application source
	 * @return
	 */

	public String getSource() {
		String source;
		try {
			source = htweet.getString("src");
		} catch (Exception e) {
			source = null;			
		}
		return source;
	}

	public void setSource(String source) {
		try {
			if(source == null & source.length()== 0 ) {
				htweet.remove("src");
			} else {
				htweet.put("src", source);
			}
		} catch (JSONException e) {
			log.error("Can't update the source attribut",e);
		}


	}
	
	

		
	
	public void setAuthortwt(JSONObject authortweet) {
		try {
			if(authortweet == null) {
				htweet.remove("author");
			} else {
				htweet.put("author", authortweet);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * The date of tweet creation 
	 * @return
	 */
	public Calendar getCreatedAt() {
		Calendar createdAt;
		try {
			createdAt = (DateISO8601.toCalendar(htweet.getString("publish")));;
		} catch (JSONException e) {
			createdAt = null;
		}
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		try {
			if(createdAt == null ) {
				htweet.remove("publish");
			} else {
				htweet.put("publish", DateISO8601.fromCalendar(createdAt));
			}
		} catch (JSONException e) {
			e.printStackTrace();
			log.error("Can't update the publish attribut",e);
		}
	}
	
	public JSONObject getAuthortwt() {
		JSONObject authortweet;
		try {
			authortweet  = htweet.getJSONObject("authortweet");
		} catch (JSONException e) {
			authortweet = null;
		}
		return authortweet;
	}
	
	/**
	 * The Idtweet
	 * @return
	 */
	public long getIdTweet() {
		long id;
		try {
			id = htweet.getLong("IdTweet");
		} catch (Exception e) {
			id = 0;			
		}
		return id;
	}
	
	public void setIdTweet(long id) {
		try {
			if(id == 0) {
				htweet.remove("IdTweet");
			} else {
				htweet.put("IdTweet", id);
			}
		} catch (JSONException e) {
			log.error("Can't update the Id Tweet  attribut",e);
		}
	}
	
}

