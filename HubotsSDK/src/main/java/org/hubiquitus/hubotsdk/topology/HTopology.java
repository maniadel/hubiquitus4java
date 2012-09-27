package org.hubiquitus.hubotsdk.topology;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTopology extends HActor {
	final Logger logger = LoggerFactory.getLogger(HTopology.class);
	public HTopology(){
		super();
	}
	
	public HTopology(JSONObject jsonObj) throws JSONException{
		super(jsonObj);
	}
	
	public JSONArray getAdapters(){
		JSONArray adapters;
		try {
			adapters = this.getJSONArray("adapters");
		} catch (Exception e) {
			adapters = null;
		}
		return adapters;
	}
	
	public void setAdapters(JSONArray adapters){
		try {
			if(adapters == null){
				this.remove("adapters");
			}else{
				this.put("adapters",adapters);
			}
		} catch (JSONException e) {
			logger.debug("Can not set the adapters attribute.", e);
		}
	}
}
