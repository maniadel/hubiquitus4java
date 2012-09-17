package org.hubiquitus.hapi.hStructures;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * since v0.5 Specifies the exact longitude and latitude of the location
 * 
 * @version v0.5
 * @author hnode
 * 
 */
public class HGeo extends JSONObject {

	// private JSONObject hgeo = new JSONObject();

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

//	@Override
//	public JSONObject toJSON() {
//		return this.hgeo;
//	}
//
//	@Override
//	public void fromJSON(JSONObject jsonObj) {
//		if (jsonObj != null) {
//			this.hgeo = jsonObj;
//		} else {
//			this.hgeo = new JSONObject();
//		}
//	}
//
//	@Override
//	public String getHType() {
//		return "hgeo";
//	}
//
//	@Override
//	public String toString() {
//		return this.hgeo.toString();
//	}
//
//	public boolean equals(HGeo obj) {
//		return true;
//	}

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
		}
	}
}
