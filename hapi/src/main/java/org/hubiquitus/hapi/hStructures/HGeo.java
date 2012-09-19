package org.hubiquitus.hapi.hStructures;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * since v0.5 Specifies the exact longitude and latitude of the location
 * 
 * @version v0.5
 * @author hnode
 * 
 */
public class HGeo extends JSONObject {

	final Logger logger = LoggerFactory.getLogger(HGeo.class);

	public HGeo() {
		super();
	}

	public HGeo(JSONObject jsonObj) throws JSONException {
		super(jsonObj, JSONObject.getNames(jsonObj));
	}

	public HGeo(double lng, double lat) {
		this();
		setLng(lng);
		setLat(lat);
	}

	/* Setter & Getter */

	public Double getLng() {
		Double lng;
		try {
			lng = this.getDouble("lng");
		} catch (Exception e) {
			lng = null;
		}
		return lng;
	}

	public void setLng(double lng) {
		try {
			this.put("lng", lng);
		} catch (JSONException e) {
			logger.error("message: ", e);
		}
	}

	public Double getLat() {
		Double lat;
		try {
			lat = this.getDouble("lat");
		} catch (Exception e) {
			lat = null;
		}
		return lat;
	}

	public void setLat(double lat) {
		try {
			this.put("lat", lat);
		} catch (JSONException e) {
			logger.error("message: ", e);
		}
	}
}
