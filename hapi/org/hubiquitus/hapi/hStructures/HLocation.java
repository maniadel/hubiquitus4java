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

package org.hubiquitus.hapi.hStructures;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @version 0.3
 * This structure describe the location
 */

public class HLocation implements HJsonObj{

	private JSONObject hlocation = new JSONObject();
		
	public HLocation() {};
	
	public HLocation(JSONObject jsonObj){
		fromJSON(jsonObj);
	}
	
	/* HJsonObj interface */
	
	public JSONObject toJSON() {
		return hlocation;
	}
	
	public void fromJSON(JSONObject jsonObj) {
		if(jsonObj != null) {
			this.hlocation = jsonObj; 
		} else {
			this.hlocation = new JSONObject();
		}
	}
	
	public String getHType() {
		return "hlocation";
	}
	
	@Override
	public String toString() {
		return hlocation.toString();
	}
	
	/**
	 * Check are made on : lng, lat, zip, num, building, floor, way, waytype, addr, city and countryCode. 
	 * @param HLocation 
	 * @return Boolean
	 */
	public boolean equals(HLocation obj) {
		if(obj.getLat() != this.getLat())
			return false;
		if(obj.getLng() != this.getLng())
			return false;
		if(obj.getZip() != this.getZip())
			return false;
		if(obj.getNum() != this.getNum())
			return false;
		if(obj.getBuilding() != this.getBuilding())
			return false;
		if(obj.getFloor() != this.getFloor())
			return false;
		if(obj.getWay() != this.getWay())
			return false;
		if(obj.getWayType() != this.getWayType())
			return false;
		if(obj.getAddr() != this.getAddr())
			return false;
		if(obj.getCity() != this.getCity())
			return false;
		if(obj.getCountryCode() != this.getCountryCode())
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		return hlocation.hashCode();
	}
	
	/* Getters & Setters */
	
	/**
	 * @return the latitude of the location. 0 if undefined
	 */
	public double getLat() {
		double lat;
		try {
			lat = hlocation.getDouble("lat");
		} catch (Exception e) {
			lat = 0;			
		}
		return lat;
	}

	public void setLat(double lat) {
		try {
			if(lat == 0) {
				hlocation.remove("lat");
			} else {
				hlocation.put("lat", lat);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * @return the longitude of the location. 0 if undefined
	 */
	public double getLng() {
		double lng;
		try {
			lng = hlocation.getDouble("lng");
		} catch (Exception e) {
			lng = 0;			
		}
		return lng;
	}

	public void setLng(double lng) {
		try {
			if(lng == 0) {
				hlocation.remove("lng");
			} else {
				hlocation.put("lng", lng);
			}
		} catch (JSONException e) {
		}
	}
	
	/**
	 * @return the zip code of the location. NULL if undefined
	 */
	public String getZip() {
		String zip;
		try {
			zip = hlocation.getString("zip");
		} catch (Exception e) {
			zip = null;			
		}
		return zip;
	}

	public void setZip(String zip) {
		try {
			if(zip == null) {
				hlocation.remove("zip");
			} else {
				hlocation.put("zip", zip);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * @return the way number of the location. NULL if undefined
	 */
	public String getNum() {
		String num;
		try {
			num = hlocation.getString("num");
		} catch (Exception e) {
			num = null;			
		}
		return num;
	}

	public void setNum(String num) {
		try {
			if(num == null) {
				hlocation.remove("num");
			} else {
				hlocation.put("num", num);
			}
		} catch (JSONException e) {
		}
	}
	
	/**
	 * @return the type of the way of the location. NULL if undefined
	 */
	public String getWayType() {
		String wayType;
		try {
			wayType = hlocation.getString("wayType");
		} catch (Exception e) {
			wayType = null;			
		}
		return wayType;
	}

	public void setWayType(String wayType) {
		try {
			if(wayType == null) {
				hlocation.remove("wayType");
			} else {
				hlocation.put("wayType", wayType);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * @return the name of the street/way of the location. NULL if undefined
	 */
	public String getWay() {
		String way;
		try {
			way = hlocation.getString("way");
		} catch (Exception e) {
			way = null;			
		}
		return way;
	}
	
	public void setWay(String way) {
		try {
			if(way == null) {
				hlocation.remove("way");
			} else {
				hlocation.put("way", way);
			}
		} catch (JSONException e) {
		}
	}
	
	/**
	 * @return the address complement of the location. NULL if undefined
	 */
	public String getAddr() {
		String addr;
		try {
			addr = hlocation.getString("addr");
		} catch (Exception e) {
			addr = null;			
		}
		return addr;
	}
	
	public void setAddr(String addr) {
		try {
			if(addr == null) {
				hlocation.remove("addr");
			} else {
				hlocation.put("addr", addr);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * @return the floor number of the location. NULL if undefined
	 */
	public String getFloor() {
		String floor;
		try {
			floor = hlocation.getString("floor");
		} catch (Exception e) {
			floor = null;			
		}
		return floor;
	}
	
	public void setFloor(String floor) {
		try {
			if(floor == null) {
				hlocation.remove("floor");
			} else {
				hlocation.put("floor", floor);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * @return the building’s identifier of the location. NULL if undefined
	 */
	public String getBuilding() {
		String building;
		try {
			building = hlocation.getString("building");
		} catch (Exception e) {
			building = null;			
		}
		return building;
	}
	
	public void setBuilding(String building) {
		try {
			if(building == null) {
				hlocation.remove("building");
			} else {
				hlocation.put("building", building);
			}
		} catch (JSONException e) {
		}
	}

	
	/**
	 * @return city of the location. NULL if undefined
	 */
	public String getCity() {
		String city;
		try {
			city = hlocation.getString("city");
		} catch (Exception e) {
			city = null;			
		}
		return city;
	}

	public void setCity(String city) {
		try {
			if(city == null) {
				hlocation.remove("city");
			} else {
				hlocation.put("city", city);
			}
		} catch (JSONException e) {
		}
	}
	
	/**
	 * @return countryCode of the location. NULL if undefined
	 */
	public String getCountryCode() {
		String countryCode;
		try {
			countryCode = hlocation.getString("countryCode");
		} catch (Exception e) {
			countryCode = null;			
		}
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		try {
			if(countryCode == null) {
				hlocation.remove("countryCode");
			} else {
				hlocation.put("countryCode", countryCode);
			}
		} catch (JSONException e) {
		}
	}
}