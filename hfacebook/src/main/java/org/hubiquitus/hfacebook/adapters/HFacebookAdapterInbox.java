package org.hubiquitus.hfacebook.adapters;

import org.hubiquitus.hapi.exceptions.MissingAttrException;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hfacebook.publics.GetLikeFacebook;
import org.hubiquitus.hfacebook.publics.HFacebookListners;
import org.hubiquitus.hubotsdk.AdapterInbox;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HFacebookAdapterInbox extends AdapterInbox implements HFacebookListners{


	final Logger log = LoggerFactory.getLogger(HFacebookAdapterInbox.class);

	private String proxyHost;
	private int    proxyPort;	

	private String pageName;
	private long roundTime;

	private GetLikeFacebook like;

	@Override
	public void setProperties(JSONObject properties) {

		if(properties != null){
			try{


				if (properties.has("proxyHost")) {
					this.proxyHost = properties.getString("proxyHost");
				}				
				if (properties.has("proxyPort")) {
					this.proxyPort = properties.getInt("proxyPort");
				}
				if (properties.has("pageName")) {
					this.pageName = properties.getString("pageName");
				}
				if (properties.has("roundTime")) {
					this.roundTime = properties.getLong("roundTime");
				}
			}catch(JSONException e){
				log.error("ERROR ON CONFIG FILE 'JSON NOT VALID', ERROR TYPE : ",e);
			}
		}
		log.info("Properties Initialized : " + this);
	}

	@Override
	public void start() {
		log.info("Starting request...");
		like = new GetLikeFacebook(proxyHost, proxyPort, pageName, roundTime);

		like.addListener(this);
		like.start();
		log.info("Started requet.");

	}

	@Override
	public void stop() {
		log.info("Stopping request...");
		if (like != null)
			like.stop();
		    log.info("Stopped request.");
	}
	
	
	/*************************************************************** */

	@Override
	public String toString() {
		return "HFacebookInBox [proxyHost =" + proxyHost 
				+ ", proxyPort ="      + proxyPort 
				+ ", pageName ="  + pageName 
				+ ", roundTime =" + roundTime  + "]";
	}

	/*************************************************************** */
	/***
	 *   "name": "Paulo Coelho",
   "is_published": true,
   "website": "http://paulocoelhoblog.com",
   "username": "paulocoelho",
   "about": "BLOG: http://paulocoelhoblog.com            TWITTER: http://twitter.com/paulocoelho",
   "personal_interests": "Writing, Archery, Alchemy, Books, ",
   "talking_about_count": 399177,
   "category": "Author",
   "id": "11777366210",
   "link": "https://www.facebook.com/paulocoelho",
   "likes": 9669546
	 * 
	 */
	
	public HMessage transformFacebookMsg(JSONObject facebookMsg) throws JSONException{
		HMessage message = new HMessage();
		try {
			message.setActor(facebookMsg.getString("name"));
			JSONObject json = new JSONObject();
			json.put("likes", facebookMsg.getInt("likes"));
			
			message.setPayload(json);
			
		} catch (MissingAttrException e) {
			log.error("Transformation to Hmessage Error, Type  :("+e);
		}
		
		return message;
	}
	
	/*************************************************************** */
	public void onStatusLike(JSONObject statusLike) {
		log.info("-----> Recived like  :"+statusLike);
	 
		try {
			put(transformFacebookMsg(statusLike));
		} catch (JSONException e) {
			log.error(" Error at transformation facebook Message ("+e);
		}
		
	}

	
	public void onOthersLikeStatus(JSONObject item) {
		log.info("-----> Others messages  :"+item);

	}



}
