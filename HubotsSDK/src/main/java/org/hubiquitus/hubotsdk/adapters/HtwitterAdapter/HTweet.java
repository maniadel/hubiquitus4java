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
import org.hubiquitus.hapi.hStructures.HJsonObj;
import org.hubiquitus.hapi.util.DateISO8601;
import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.Status;

/**
 * @version 0.3
 * Describes a twitter payload
 */
public class HTweet implements HJsonObj{

	private JSONObject htweet= new JSONObject();

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
		String tweetText;
		try {
			tweetText = htweet.getString("authorName");
		} catch (Exception e) {
			tweetText = null;			
		}
		return tweetText;
	}

	public void setAuthorName(String authorName) {
		try {
			if(authorName == null) {
				htweet.remove("authorName");
			} else {
				htweet.put("authorName", authorName);
			}
		} catch (JSONException e) {
		}
	}

	public String getTweetText() {
		String tweetText;
		try {
			tweetText = htweet.getString("tweetText");
		} catch (Exception e) {
			tweetText = null;			
		}
		return tweetText;
	}

	public void setTweetText(String tweetText) {
		try {
			if(tweetText == null) {
				htweet.remove("tweetText");
			} else {
				htweet.put("tweetText", tweetText);
			}
		} catch (JSONException e) {
		}
	}
	
	/** 
	 * The screen name of the user : see TwitterApi
	 * @return
	 */
	public String getScreenName() {
		String screenName;
		try {
			screenName = htweet.getString("screenName");
		} catch (Exception e) {
			screenName = null;			
		}
		return screenName;
	}

	public void setScreenName(String screenName) {
		try {
			if(screenName == null) {
				htweet.remove("screenName");
			} else {
				htweet.put("screenName", screenName);
			}
		} catch (JSONException e) {
		}
	}
	
	/**
	 * The screen name of the users: See TwitterApi
	 * @return
	 */
	public String getInReplyToScreenName() {
		String inReplyToScreenName;
		try {
			inReplyToScreenName = htweet.getString("inReplyToScreenName");
		} catch (Exception e) {
			inReplyToScreenName = null;			
		}
		return inReplyToScreenName;
	}

	public void setInReplyToScreenName(String inReplyToScreenName) {
		try {
			if(inReplyToScreenName == null) {
				htweet.remove("inReplyToScreenName");
			} else {
				htweet.put("inReplyToScreenName", inReplyToScreenName);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * The Application source
	 * @return
	 */

	public String getSource() {
		String source;
		try {
			source = htweet.getString("source");
		} catch (Exception e) {
			source = null;			
		}
		return source;
	}

	public void setSource(String source) {
		try {
			if(source == null) {
				htweet.remove("source");
			} else {
				htweet.put("source", source);
			}
		} catch (JSONException e) {
		}


	}
	
	/**
	 * The ISOLanguage of the tweet
	 * @return
	 */
	public String getIsoLanguageCode() {
		String isoLanguageCode;
		try {
			isoLanguageCode = htweet.getString("isoLanguageCode");
		} catch (Exception e) {
			isoLanguageCode = null;			
		}
		return isoLanguageCode;
	}

	public void setIsoLanguageCode(String isoLanguageCode) {
		try {
			if(isoLanguageCode == null) {
				htweet.remove("isoLanguageCode");
			} else {
				htweet.put("isoLanguageCode", isoLanguageCode);
			}
		} catch (JSONException e) {
		}


	}

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
		}


	}
	
	/**
	 * The language of tweet
	 * @return
	 */	
	public String getLang() {
		String lang;
		try {
			lang = htweet.getString("lang");
		} catch (Exception e) {
			lang = null;		
		}
		return lang;
	}

	public void setLang(String lang) {
		try {
			if(lang == null) {
				htweet.remove("lang");
			} else {
				htweet.put("lang", lang);
			}
		} catch (JSONException e) {
		}


	}

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
		}


	}
	/**
	 * The number of times this tweet has been retweeted
	 * @return
	 */
	public int getRetweetcount() {
		int retweetcount;
		try {
			retweetcount = htweet.getInt("retweetcount");
		} catch (Exception e) {
			retweetcount = 0;			
		}
		return retweetcount;
	}

	public void setRetweetcount(long retweetcount) {
		try {
			if(retweetcount == 0) {
				htweet.remove("retweetcount");
			} else {
				htweet.put("retweetcount", retweetcount);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * The id of the status
	 * @return
	 */
	public int getIdtweet() {
		int idTweet;
		try {
			idTweet = htweet.getInt("idTweet");
		} catch (Exception e) {
			idTweet = 0;			
		}
		return idTweet;
	}
	
	public void setIdTweet(long idTweet) {
		try {
			if(idTweet == 0) {
				htweet.remove("idTweet");
			} else {
				htweet.put("idTweet", idTweet);
			}
		} catch (JSONException e) {
		}
	}
	
	
	
	/**
	 * The friends Count
	 * @return
	 */
	public int 	getFriendsCount() {
		int friendsCount;
		try {
			friendsCount = htweet.getInt("friendsCount");
		} catch (Exception e) {
			friendsCount = 0;			
		}
		return friendsCount;
	}
	
	public void setFriendsCount(int friendsCount) {
		try {
			if(friendsCount == 0) {
				htweet.remove("friendsCount");
			} else {
				htweet.put("friendsCount", friendsCount);
			}
		} catch (JSONException e) {
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
		}
	}
	
	/**
	 * The number of followers
	 * @return
	 */
	public int getFollowerscount() {
		int followerscount;
		try {
			followerscount = htweet.getInt("followerscount");
		} catch (Exception e) {
			followerscount = 0;		
		}
		return followerscount;
	}

	public void setFollowerscount(int followerscount) {
		try {
			if(followerscount == 0) {
				htweet.remove("followerscount");
			} else {
				htweet.put("followerscount", followerscount);
			}
		} catch (JSONException e) {
		}


	}
	
	/**
	 * The Status counter
	 * @return
	 */
	public int getStatusesCount() {
		int statusesCount;
		try {
			statusesCount = htweet.getInt("statusesCount");
		} catch (Exception e) {
			statusesCount = 0;			
		}
		return statusesCount;
	}
	
	public void setStatusesCount(int statusesCount) {
		try {
			if(statusesCount == 0) {
				htweet.remove("statusesCount");
			} else {
				htweet.put("statusesCount", statusesCount);
			}
		} catch (JSONException e) {
		}
	}

}

