package org.hubiquitus.hapi.hStructures;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HPos extends JSONObject {

	final Logger logger = LoggerFactory.getLogger(HPos.class);
	public HPos() {
		super();
	};

	public HPos(JSONObject jsonObj) throws JSONException {
		super(jsonObj, JSONObject.getNames(jsonObj));
	}
	
	/* Getters & Setters */

	/**
	 * The latitude . Mandatory
	 */
	public Double getLat() {
		Double lat;
		try {
			lat = this.getDouble("lat");
		} catch (Exception e) {
			logger.error("messag: lat is mandatory in HPos.");
			lat = null;
		}
		return lat;
	}

	public void setLat(double lat) {
		try {
				this.put("lat", lat);
		} catch (JSONException e) {
			logger.warn("message: ", e);
		}
	}
	
	/**
	 * The longitude. Mandatory
	 */
	public Double getLng() {
		Double lng;
		try {
			lng = this.getDouble("lng");
		} catch (Exception e) {
			logger.error("messag: lng is mandatory in HPos.");
			lng = null;
		}
		return lng;
	}

	public void setLng(double lng) {
		try {
				this.put("lng", lng);
		} catch (JSONException e) {
			logger.warn("message: ", e);
		}
	}
	
	/**
	 * The radius expressed in meters. Mandatory
	 */
	public Double getRadius() {
		Double radius;
		try {
			radius = this.getDouble("radius");
		} catch (Exception e) {
			logger.error("messag: lat is mandatory in HPos.");
			radius = null;
		}
		return radius;
	}

	public void setRadius(double radius) {
		try {
				this.put("radius", radius);
		} catch (JSONException e) {
			logger.warn("message: ", e);
		}
	}
}
