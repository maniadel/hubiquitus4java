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


import java.util.Calendar;

import org.hubiquitus.hapi.hStructures.HJsonObj;
import org.hubiquitus.hapi.util.DateISO8601;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * @version 0.3
 * Describes a twitter payload
 */

public class HTwitter implements HJsonObj{

	private JSONObject htwitter = new JSONObject();

	public HTwitter() {};

	public HTwitter(JSONObject jsonObj){
		fromJSON(jsonObj);
	}

	/* HJsonObj interface */

	public JSONObject toJSON() {
		return htwitter;
	}

	public void fromJSON(JSONObject jsonObj) {
		if(jsonObj != null) {
			this.htwitter = jsonObj; 
		} else {
			this.htwitter = new JSONObject();
		}
	}

	public String getHType() {
		return "htwitter";
	}

	@Override
	public String toString() {
		return htwitter.toString();
	}


	@Override
	public int hashCode() {
		return htwitter.hashCode();
	}

	/* Getters & Setters */

	public String getText() {
		String text;
		try {
			text = htwitter.getString("text");
		} catch (Exception e) {
			text = null;			
		}
		return text;
	}

	public void settext(String text) {
		try {
			if(text == null) {
				htwitter.remove("text");
			} else {
				htwitter.put("text", text);
			}
		} catch (JSONException e) {
		}
	}


	public String getSource() {
		String source;
		try {
			source = htwitter.getString("source");
		} catch (Exception e) {
			source = null;			
		}
		return source;
	}

	public void setSource(String source) {
		try {
			if(source == null) {
				htwitter.remove("source");
			} else {
				htwitter.put("source", source);
			}
		} catch (JSONException e) {
		}


	}

	public void setRetweetcount(int retweetcount) {
		try {
			if(retweetcount == 0) {
				htwitter.remove("retweetcount");
			} else {
				htwitter.put("retweetcount", retweetcount);
			}
		} catch (JSONException e) {
		}
	}

	public int getRetweetcount() {
		int retweetcount;
		try {
			retweetcount = htwitter.getInt("retweetcount");
		} catch (Exception e) {
			retweetcount = 0;			
		}
		return retweetcount;
	}


	public Calendar getCreatedat() {
		Calendar createdat;
		try {
			createdat = (DateISO8601.toCalendar(htwitter.getString("createdat")));;
		} catch (JSONException e) {
			createdat = null;
		}
		return createdat;
	}

	public void setCreatedat(Calendar createdat) {
		try {
			if(createdat == null) {
				htwitter.remove("createdat");
			} else {
				htwitter.put("createdat", DateISO8601.fromCalendar(createdat));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}

