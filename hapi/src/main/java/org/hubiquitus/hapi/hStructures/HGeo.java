package org.hubiquitus.hapi.hStructures;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * since v0.5 Specifies the exact longitude and latitude of the location
 * @version v0.5
 * @author hnode
 * 
 */
public class HGeo implements HJsonObj {

	private JSONObject hgeo = new JSONObject();

	public HGeo() {
	}

	public HGeo(JSONObject jsonObj) {
		fromJSON(jsonObj);
	}
	
	public HGeo(double lng, double lat) {
		this();
		setLng(lng);
		setLat(lat);
	}

	@Override
	public JSONObject toJSON() {
		return this.hgeo;
	}

	@Override
	public void fromJSON(JSONObject jsonObj) {
		if (jsonObj != null) {
			this.hgeo = jsonObj;
		} else {
			this.hgeo = new JSONObject();
		}
	}

	@Override
	public String getHType() {
		return "hgeo";
	}

	@Override
	public String toString() {
		return this.hgeo.toString();
	}

	public boolean equals(HGeo obj) {
		return true;
	}

	/* Setter & Getter */

	public Double getLng() {
		Double lng;
		try {
			lng = hgeo.getDouble("lng");
		} catch (Exception e) {
			lng = null;
		}
		return lng;
	}

	public void setLng(double lng) {
		try {
			hgeo.put("lng", lng);
		} catch (JSONException e) {
		}
	}

	public Double getLat() {
		Double lat;
		try {
			lat = hgeo.getDouble("lat");
		} catch (Exception e) {
			lat = null;
		}
		return lat;
	}

	public void setLat(double lat) {
		try {
			hgeo.put("lat", lat);
		} catch (JSONException e) {
		}
	}
}
