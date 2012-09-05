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
import org.hubiquitus.hubotsdk.adapters.HTwitterAdapterInbox;
import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.Status;

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
	
	public String getAuthorName() {
		String author;
		try {
			author = htweet.getString("author");
		} catch (Exception e) {
			author = null;			
		}
		return author;
	}

	public void setAuthorName(String author) {
		try {
			if(author == null) {
				htweet.remove("author");
			} else {
				htweet.put("author", author);
			}
		} catch (JSONException e) {
			log.error("Can't update the author attribut",e);
		}
	}

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
			if(text == null) {
				htweet.remove("text");
			} else {
				htweet.put("text", text);
			}
		} catch (JSONException e) {
			log.error("Can't update the author attribut",e);
		}
	}
	
	/** 
	 * The screen name of the user : see TwitterApi
	 * @return
	 */
	public String getScreenName() {
		String screen;
		try {
			screen = htweet.getString("screen");
		} catch (Exception e) {
			screen = null;			
		}
		return screen;
	}

	public void setScreenName(String screen) {
		try {
			if(screen == null) {
				htweet.remove("screen");
			} else {
				htweet.put("screen", screen);
			}
		} catch (JSONException e) {
			log.error("Can't update the screen attribut",e);
		}
	}
	
	/**
	 * The screen name of the users: See TwitterApi
	 * @return
	 */
//	public String getInReplyToScreenName() {
//		String replyToScreen;
//		try {
//			replyToScreen = htweet.getString("replyToScreen");
//		} catch (Exception e) {
//			replyToScreen = null;			
//		}
//		return replyToScreen;
//	}
//
//	public void setInReplyToScreenName(String replyToScreen) {
//		try {
//			if(replyToScreen == null) {
//				htweet.remove("replyToScreen");
//			} else {
//				htweet.put("replyToScreen", replyToScreen);
//			}
//		} catch (JSONException e) {
//		}
//	}

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
			if(source == null) {
				htweet.remove("src");
			} else {
				htweet.put("src", source);
			}
		} catch (JSONException e) {
			log.error("Can't update the source attribut",e);
		}


	}
	
	/**
	 * The ISOLanguage of the tweet
	 * @return
	 */
//	public String getIsoLanguageCode() {
//		String langCode;
//		try {
//			langCode = htweet.getString("langCode");
//		} catch (Exception e) {
//			langCode = null;			
//		}
//		return langCode;
//	}
//
//	public void setIsoLanguageCode(String langCode) {
//		try {
//			if(langCode == null) {
//				htweet.remove("langCode");
//			} else {
//				htweet.put("langCode", langCode);
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
			location = htweet.getString("location");
		} catch (Exception e) {
			location = null;		
		}
		return location;
	}

	public void setLocation(String location) {
		try {
			if(location == null) {
				htweet.remove("location");
			} else {
				htweet.put("location", location);
			}
		} catch (JSONException e) {
			log.error("Can't update the location attribut",e);
		}


	}
	
	/**
	 * The language of tweet
	 * @return
	 */	
//	public String getLang() {
//		String lang;
//		try {
//			lang = htweet.getString("lang");
//		} catch (Exception e) {
//			lang = null;		
//		}
//		return lang;
//	}
//
//	public void setLang(String lang) {
//		try {
//			if(lang == null) {
//				htweet.remove("lang");
//			} else {
//				htweet.put("lang", lang);
//			}
//		} catch (JSONException e) {
//		}
//
//
//	}

	/**
	 * The current Status of user
	 * @return
	 */
	public String getStatus() {
		String status;
		try {
			status = htweet.getString("status");
		} catch (Exception e) {
			status = null;		
		}
		return status;
	}

	public void setStatus(Status status) {
		try {
			if(status == null) {
				htweet.remove("status");
			} else {
				htweet.put("status", status);
			}
		} catch (JSONException e) {
			log.error("Can't update the status attribut",e);
		}


	}
	/**
	 * The number of times this tweet has been retweeted
	 * @return
	 */
	public int getRetweetcount() {
		int retweetcount;
		try {
			retweetcount = htweet.getInt("rts");
		} catch (Exception e) {
			retweetcount = 0;			
		}
		return retweetcount;
	}

	public void setRetweetcount(long retweetcount) {
		try {
			if(retweetcount == 0) {
				htweet.remove("rts");
			} else {
				htweet.put("rts", retweetcount);
			}
		} catch (JSONException e) {
			log.error("Can't update the retweetcount attribut",e);
		}
	}

	/**
	 * The id of the status
	 * @return
	 */
//	public int getIdtweet() {
//		int idTweet;
//		try {
//			idTweet = htweet.getInt("id");
//		} catch (Exception e) {
//			idTweet = 0;			
//		}
//		return idTweet;
//	}
//	
//	public void setIdTweet(long idTweet) {
//		try {
//			if(idTweet == 0) {
//				htweet.remove("id");
//			} else {
//				htweet.put("id", idTweet);
//			}
//		} catch (JSONException e) {
//		}
//	}
	
	
	
	/**
	 * The friends Count
	 * @return
	 */
	public int 	getFriendsCount() {
		int friendCnt;
		try {
			friendCnt = htweet.getInt("friends");
		} catch (Exception e) {
			friendCnt = 0;			
		}
		return friendCnt;
	}
	
	public void setFriendsCount(int friendCnt) {
		try {
			if(friendCnt == 0) {
				htweet.remove("friends");
			} else {
				htweet.put("friends", friendCnt);
			}
		} catch (JSONException e) {
			log.error("Can't update the friendCount attribut",e);
			
		}
	}

	/**
	 * The date of tweet creation 
	 * @return
	 */
	public Calendar getCreatedAt() {
		Calendar createdAt;
		try {
			createdAt = (DateISO8601.toCalendar(htweet.getString("createdAt")));;
		} catch (JSONException e) {
			createdAt = null;
		}
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		try {
			if(createdAt == null) {
				htweet.remove("createdAt");
			} else {
				htweet.put("createdAt", DateISO8601.fromCalendar(createdAt));
			}
		} catch (JSONException e) {
			e.printStackTrace();
			log.error("Can't update the createdAt attribut",e);
		}
	}
	
	/**
	 * The number of followers
	 * @return
	 */
	public int getFollowerscount() {
		int followerscnt;
		try {
			followerscnt = htweet.getInt("followers");
		} catch (Exception e) {
			followerscnt = 0;		
		}
		return followerscnt;
	}

	public void setFollowerscount(int followerscnt) {
		try {
			if(followerscnt == 0) {
				htweet.remove("followers");
			} else {
				htweet.put("followers", followerscnt);
			}
		} catch (JSONException e) {
			log.error("Can't update the followersCount attribut",e);
		}


	}
	
	/**
	 * The Status counter
	 * @return
	 */
	public int getStatusesCount() {
		int statusCnt;
		try {
			statusCnt = htweet.getInt("statusCnt");
		} catch (Exception e) {
			statusCnt = 0;			
		}
		return statusCnt;
	}
	
	public void setStatusesCount(int statusCnt) {
		try {
			if(statusCnt == 0) {
				htweet.remove("statusCnt");
			} else {
				htweet.put("statusCnt", statusCnt);
			}
		} catch (JSONException e) {
			log.error("Can't update the statusCount attribut",e);
		}
	}

}

