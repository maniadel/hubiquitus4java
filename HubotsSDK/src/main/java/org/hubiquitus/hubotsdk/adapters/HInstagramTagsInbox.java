package org.hubiquitus.hubotsdk.adapters;

import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubiquitus4j.HInstagramAPI.HInstagramAPI.GetInstagramTags;
import org.hubiquitus.hubiquitus4j.HInstagramAPI.HInstagramAPI.InstagStatus;
import org.hubiquitus.hubiquitus4j.HInstagramAPI.HInstagramAPI.InstagramTagsListners;
import org.hubiquitus.hubotsdk.AdapterInbox;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HInstagramTagsInbox extends AdapterInbox implements InstagramTagsListners{


	final Logger log = LoggerFactory.getLogger(HInstagramTagsInbox.class);

	private String proxyHost;
	private int    proxyPort;	

	private String tags;
	private String options;
	private long roundTime;
	private String clientId;

	private GetInstagramTags instagramTags;

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
				if (properties.has("tags")) {
					this.tags = properties.getString("tags");
				}
				if (properties.has("options")) {
					this.options = properties.getString("options");
				}
				if (properties.has("roundTime")) {
					this.roundTime = properties.getLong("roundTime");
				}
				if (properties.has("clientId")) {
					this.clientId = properties.getString("clientId");
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
		instagramTags = new GetInstagramTags(proxyHost, proxyPort, tags, options ,clientId, roundTime);
		instagramTags.addListener(this);
		instagramTags.start();
		log.info("Started request.");

	}

	@Override
	public void stop() {
		log.info("Stopping request...");
		if (instagramTags != null)
			instagramTags.stop();
		log.info("Stopped request.");
	}
	
	
	@Override
	public String toString() {
		return "HFacebookInBox [proxyHost =" + proxyHost 
				+ ", proxyPort ="      + proxyPort 
				+ ", tags ="  + tags
				+ ", options ="  + options
				+ ", clientId ="  + clientId
				+ ", roundTime =" + roundTime  + "]";
	}


	@Override
	public void onStatus(InstagStatus instagramStatus) {
		HMessage msg = new HMessage();
		msg.setType("InstagramStatus");
		msg.setPayload(instagramStatus);
		put(msg);
		
	}
}
