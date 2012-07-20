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

/**
 * @version 0.4
 * This structure describe the location
 */

public class HLocation extends HStructure {

	public HLocation() {
		super();
	}
	
	public String getHType() {
		return "hlocation";
	}
	
	/**
	 * @return latitude of the location. 0 if undefined
	 */
	public Double getLat() {
		return (Double) this.get("lat", Double.class);
	}

	public void setLat(Double lat) {
		this.put("lat", lat);
	}

	/**
	 * @return longitude of the location. 0 if undefined
	 */
	public Double getLng() {
		return (Double) this.get("lng", Double.class);
	}

	public void setLng(Double lng) {
		this.put("lng", lng);
	}
	
	/**
	 * @return zip code of the location. NULL if undefined
	 */
	public String getZip() {
		return (String) this.get("zip", String.class);
	}

	public void setZip(String zip) {
		this.put("zip", zip);
	}
	
	/**
	 * @return address of the location. NULL if undefined
	 */
	public String getAddress() {
		return (String) this.get("addr", String.class);
	}

	public void setAddress(String address) {
		this.put("addr", address);
	}

	/**
	 * @return city of the location. NULL if undefined
	 */
	public String getCity() {
		return (String) this.get("city", String.class);
	}

	public void setCity(String city) {
		this.put("city", city);
	}
	
	/**
	 * @return country of the location. NULL if undefined
	 */
	public String getCountry() {
		return (String) this.get("country", String.class);
	}

	public void setCountry(String country) {
		this.put("country", country);
	}
}