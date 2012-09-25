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

import java.net.URL;
import java.util.Calendar;

import org.hubiquitus.hapi.util.DateISO8601;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @version 0.5
 * Describes a twitter payload
 */
public class HAuthorTweet extends JSONObject {

	private JSONObject hauthortweet= new JSONObject();
	final Logger log = LoggerFactory.getLogger(HAuthorTweet.class);

	public HAuthorTweet() {};

	public HAuthorTweet(JSONObject jsonObj){
		fromJSON(jsonObj);
	}

	/* HJsonObj interface */

	public JSONObject toJSON() {
		return hauthortweet;
	}

	public void fromJSON(JSONObject jsonObj) {
		if(jsonObj != null) {
			this.hauthortweet = jsonObj; 
		} else {
			this.hauthortweet = new JSONObject();
		}
	}

	public String getHType() {
		return "hauthortweet";
	}

	@Override
	public String toString() {
		return hauthortweet.toString();
	}


	@Override
	public int hashCode() {
		return hauthortweet.hashCode();
	}

	/* Getters & Setters */
	
	
	public String getScrName() {
		String scrName;
		try {
			scrName = hauthortweet.getString("scrName");
		} catch (Exception e) {
			scrName = null;			
		}
		return scrName;
	}

	public void setScrName(String scrName) {
		try {
			if(scrName == null & scrName.length()== 0) {
				hauthortweet.remove("scrName");
			} else {
				hauthortweet.put("scrName", scrName);
			}
		} catch (JSONException e) {
			log.error("Can't update the scrName attribut",e);
		}
	}
	
	
	/**
	 * The Status counter
	 * @return
	 */
	public int getStatus() {
		int status;
		try {
			status = hauthortweet.getInt("status");
		} catch (Exception e) {
			status = 0;			
		}
		return status;
	}
	
	public void setStatus(int status) {
		try {
			if(status == 0) {
				hauthortweet.remove("status");
			} else {
				hauthortweet.put("status", status);
			}
		} catch (JSONException e) {
			log.error("Can't update the status attribut",e);
		}
	}
	
	/**
	 * The number of followers
	 * @return
	 */
	public int getFollowers() {
		int followers;
		try {
			followers = hauthortweet.getInt("followers");
		} catch (Exception e) {
			followers = 0;		
		}
		return followers;
	}

	public void setFollowers(int followers) {
		try {
			if(followers== 0) {
				hauthortweet.remove("followers");
			} else {
				hauthortweet.put("followers", followers);
			}
		} catch (JSONException e) {
			log.error("Can't update the followers attribut",e);
		}


	}
	
	
	/** 
	 * The screen name of the user : see TwitterApi
	 * @return
	 */
	public String getName() {
		String name;
		try {
			name = hauthortweet.getString("name");
		} catch (Exception e) {
			name = null;			
		}
		return name;
	}

	public void setName(String name) {
		try {
			if(name == null & name.length()== 0) {
				hauthortweet.remove("name");
			} else {
				hauthortweet.put("name", name);
			}
		} catch (JSONException e) {
			log.error("Can't update the name attribut",e);
		}
	}
	
	
	/**
	 * The friends Count
	 * @return
	 */
	public int 	getFriends() {
		int friends;
		try {
			friends = hauthortweet.getInt("friends");
		} catch (Exception e) {
			friends = 0;			
		}
		return friends;
	}
	
	public void setFriends(int friends) {
		try {
			if(friends == 0 ) {
				hauthortweet.remove("friends");
			} else {
				hauthortweet.put("friends", friends);
			}
		} catch (JSONException e) {
			log.error("Can't update the friends attribut",e);
			
		}
	}
	
	

	/**
	 * The location that this tweet refers to if available
	 * @return
	 */
	public String getLocation() {
		String location;
		try {
			location = hauthortweet.getString("location");
		} catch (Exception e) {
			location = null;		
		}
		return location;
	}

	public void setLocation(String location) {
		try {
			if(location == null & location.length()== 0) {
				hauthortweet.remove("location");
			} else {
				hauthortweet.put("location", location);
			}
		} catch (JSONException e) {
			log.error("Can't update the location attribut",e);
		}


	}
	
	
	
	/**
	 * The Descritpion
	 * @return
	 */
	public String getDescription() {
		String description;
		try {
			description = hauthortweet.getString("description");
		} catch (Exception e) {
			description = null;			
		}
		return description;
	}
	
	public void setDescription(String description) {
		try {
			if(description == null & description.length()==0 ) {
				hauthortweet.remove("description");
			} else {
				hauthortweet.put("description", description);
			}
		} catch (JSONException e) {
			log.error("Can't update the description attribut",e);
		}
	}
	
	
	/**
	 * The Profile Image
	 * @return
	 */
	public String getProfileImg() {
		String profileImg;
		try {
			profileImg = hauthortweet.getString("profileImg");
		} catch (Exception e) {
			profileImg = null;			
		}
		return profileImg;
	}
	
	public void setProfileImg(String profileImg) {
		try {
			if(profileImg == null & profileImg.length()== 0) {
				hauthortweet.remove("profileImg");
			} else {
				hauthortweet.put("profileImg", profileImg);
			}
		} catch (JSONException e) {
			log.error("Can't update the profile Image attribut",e);
		}
	}
	
	
	/**
	 * The URL
	 * @return
	 */
	public String getURL() {
		String url;
		try {
			url = hauthortweet.getString("url").toString();
		} catch (Exception e) {
			url = null;			
		}
		return url;
	}
	
	public void setUrl(URL url) {
		try {
			if(url == null  ) {
				hauthortweet.remove("url");
			} else {
				hauthortweet.put("url", url);
			}
		} catch (JSONException e) {
			log.error("Can't update the url attribut",e);
		}
	}
	
	
	
	/**
	 * The date of profile creation 
	 * @return
	 */
	public Calendar getCreatedAt() {
		Calendar createdAt;
		try {
			createdAt = (DateISO8601.toCalendar(hauthortweet.getString("createdAt")));;
		} catch (JSONException e) {
			createdAt = null;
		}
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		try {
			if(createdAt == null ) {
				hauthortweet.remove("createdAt");
			} else {
				hauthortweet.put("createdAt", DateISO8601.fromCalendar(createdAt));
			}
		} catch (JSONException e) {
			e.printStackTrace();
			log.error("Can't update the createdAt  attribut",e);
		}
	}
	
	
	/**
	 * The language of tweet
	 * @return
	 */	
	public String getLang() {
		String lang;
		try {
			lang = hauthortweet.getString("lang");
		} catch (Exception e) {
			lang = null;		
		}
		return lang;
	}

	public void setLang(String lang) {
		try {
			if(lang == null) {
				hauthortweet.remove("lang");
			} else {
				hauthortweet.put("lang", lang);
			}
		} catch (JSONException e) {
			log.error("Can't update the languge attribut",e);
		}
	}
	
	
	/**
	 * The Geo Location
	 * @return
	 */	
	public boolean getGeo() {
		boolean geo;
		try {
			geo = hauthortweet.getBoolean("geo");
		} catch (Exception e) {
			geo = false;		
		}
		return geo;
	}

	public void setGeo(boolean geo) {
		try {
			if(geo == false) {
				hauthortweet.remove("geo");
			} else {
				hauthortweet.put("geo", geo);
			}
		} catch (JSONException e) {
			log.error("Can't update the Geolocation attribut",e);
		}
	}
	
	

	/**
	 * The verified
	 * @return
	 */	
	public boolean getVerified() {
		boolean verified;
		try {
			verified = hauthortweet.getBoolean("verified");
		} catch (Exception e) {
			verified = false;		
		}
		return verified;
	}

	public void setVerified(boolean verified) {
		try {
			if(verified == false) {
				hauthortweet.remove("verified");
			} else {
				hauthortweet.put("verified", verified);
			}
		} catch (JSONException e) {
			log.error("Can't update the verified attribut",e);
		}
	}

	
	
	/**
	 * The Listed counter
	 * @return
	 */
	public int getListeds() {
		int listeds;
		try {
			listeds = hauthortweet.getInt("listeds");
		} catch (Exception e) {
			listeds = 0;			
		}
		return listeds;
	}
	
	public void setListeds(int listeds) {
		try {
			if(listeds == 0) {
				hauthortweet.remove("listed");
			} else {
				hauthortweet.put("listed", listeds);
			}
		} catch (JSONException e) {
			log.error("Can't update the Listed  attribut",e);
		}
	}

}

