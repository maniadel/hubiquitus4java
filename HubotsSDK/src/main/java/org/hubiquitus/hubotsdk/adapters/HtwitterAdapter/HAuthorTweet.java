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

import org.apache.log4j.Logger;
import org.hubiquitus.hapi.hStructures.HJsonObj;
import org.hubiquitus.hapi.util.DateISO8601;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @version 0.3
 * Describes a twitter payload
 */
public class HAuthorTweet implements HJsonObj{

	private JSONObject hauthortweet= new JSONObject();
	private static Logger log = Logger.getLogger(HAuthorTweet.class);

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
	
	
	public String getAuthorName() {
		String author;
		try {
			author = hauthortweet.getString("author");
		} catch (Exception e) {
			author = null;			
		}
		return author;
	}

	public void setAuthorName(String author) {
		try {
			if(author == null & author.length()== 0) {
				hauthortweet.remove("author");
			} else {
				hauthortweet.put("author", author);
			}
		} catch (JSONException e) {
			log.error("Can't update the author attribut",e);
		}
	}
	
	
	/**
	 * The Status counter
	 * @return
	 */
	public int getStatusesCount() {
		int statusCnt;
		try {
			statusCnt = hauthortweet.getInt("status");
		} catch (Exception e) {
			statusCnt = 0;			
		}
		return statusCnt;
	}
	
	public void setStatusesCount(int statusCnt) {
		try {
			if(statusCnt == 0) {
				hauthortweet.remove("status");
			} else {
				hauthortweet.put("status", statusCnt);
			}
		} catch (JSONException e) {
			log.error("Can't update the status attribut",e);
		}
	}
	
	/**
	 * The number of followers
	 * @return
	 */
	public int getFollowerscount() {
		int followerscnt;
		try {
			followerscnt = hauthortweet.getInt("followers");
		} catch (Exception e) {
			followerscnt = 0;		
		}
		return followerscnt;
	}

	public void setFollowerscount(int followerscnt) {
		try {
			if(followerscnt == 0) {
				hauthortweet.remove("followers");
			} else {
				hauthortweet.put("followers", followerscnt);
			}
		} catch (JSONException e) {
			log.error("Can't update the followersCount attribut",e);
		}


	}
	
	
	/** 
	 * The screen name of the user : see TwitterApi
	 * @return
	 */
	public String getName() {
		String screen;
		try {
			screen = hauthortweet.getString("name");
		} catch (Exception e) {
			screen = null;			
		}
		return screen;
	}

	public void setName(String screen) {
		try {
			if(screen == null & screen.length()== 0) {
				hauthortweet.remove("name");
			} else {
				hauthortweet.put("name", screen);
			}
		} catch (JSONException e) {
			log.error("Can't update the name attribut",e);
		}
	}
	
	
	/**
	 * The friends Count
	 * @return
	 */
	public int 	getFriendsCount() {
		int friendCnt;
		try {
			friendCnt = hauthortweet.getInt("friends");
		} catch (Exception e) {
			friendCnt = 0;			
		}
		return friendCnt;
	}
	
	public void setFriendsCount(int friendCnt) {
		try {
			if(friendCnt == 0 ) {
				hauthortweet.remove("friends");
			} else {
				hauthortweet.put("friends", friendCnt);
			}
		} catch (JSONException e) {
			log.error("Can't update the friendCount attribut",e);
			
		}
	}
	
	

	
	/**
	 * The screen name of the users: See TwitterApi
	 * @return
	 */
//	public String getInReplyToScreenName() {
//		String replyToScreen;
//		try {
//			replyToScreen = hauthortweet.getString("replyToScreen");
//		} catch (Exception e) {
//			replyToScreen = null;			
//		}
//		return replyToScreen;
//	}
//
//	public void setInReplyToScreenName(String replyToScreen) {
//		try {
//			if(replyToScreen == null) {
//				hauthortweet.remove("replyToScreen");
//			} else {
//				hauthortweet.put("replyToScreen", replyToScreen);
//			}
//		} catch (JSONException e) {
//		}
//	}

	
	/**
	 * The ISOLanguage of the tweet
	 * @return
	 */
//	public String getIsoLanguageCode() {
//		String langCode;
//		try {
//			langCode = hauthortweet.getString("langCode");
//		} catch (Exception e) {
//			langCode = null;			
//		}
//		return langCode;
//	}
//
//	public void setIsoLanguageCode(String langCode) {
//		try {
//			if(langCode == null) {
//				hauthortweet.remove("langCode");
//			} else {
//				hauthortweet.put("langCode", langCode);
//			}
//		} catch (JSONException e) {
//		}
//
//
//	}

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
			description = hauthortweet.getString("desc");
		} catch (Exception e) {
			description = null;			
		}
		return description;
	}
	
	public void setDescription(String description) {
		try {
			if(description == null & description.length()==0 ) {
				hauthortweet.remove("desc");
			} else {
				hauthortweet.put("desc", description);
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
			profileImg = hauthortweet.getString("prfImg");
		} catch (Exception e) {
			profileImg = null;			
		}
		return profileImg;
	}
	
	public void setProfileImg(String profileImg) {
		try {
			if(profileImg == null & profileImg.length()== 0) {
				hauthortweet.remove("prfImg");
			} else {
				hauthortweet.put("prfImg", profileImg);
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
	public Calendar getCreatedAtAuthor() {
		Calendar createdAtAuthor;
		try {
			createdAtAuthor = (DateISO8601.toCalendar(hauthortweet.getString("createdAt")));;
		} catch (JSONException e) {
			createdAtAuthor = null;
		}
		return createdAtAuthor;
	}

	public void setCreatedAtAuthor(Calendar createdAtAuthor) {
		try {
			if(createdAtAuthor == null ) {
				hauthortweet.remove("createdAt");
			} else {
				hauthortweet.put("createdAt", DateISO8601.fromCalendar(createdAtAuthor));
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
	public boolean getVerif() {
		boolean verif;
		try {
			verif = hauthortweet.getBoolean("verified");
		} catch (Exception e) {
			verif = false;		
		}
		return verif;
	}

	public void setVerif(boolean verif) {
		try {
			if(verif == false) {
				hauthortweet.remove("verified");
			} else {
				hauthortweet.put("verified", verif);
			}
		} catch (JSONException e) {
			log.error("Can't update the Geolocation attribut",e);
		}
	}

	
	
	/**
	 * The Listed counter
	 * @return
	 */
	public int getListedCount() {
		int listeds;
		try {
			listeds = hauthortweet.getInt("listed");
		} catch (Exception e) {
			listeds = 0;			
		}
		return listeds;
	}
	
	public void setListedCount(int listeds) {
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

