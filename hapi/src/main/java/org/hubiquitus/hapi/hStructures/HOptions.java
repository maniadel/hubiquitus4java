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

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @version 0.5 hAPI options. For more info, see Hubiquitus reference
 */

public class HOptions implements Cloneable {

	private String transport = "socketio";
	private List<String> endpoints = null;

	public HOptions() {
		setEndpoints(null);
	}

	public HOptions(JSONObject jsonObj) {
		setEndpoints(null);
		try {

			if (jsonObj.has("transport")) {
				setTransport(jsonObj.getString("transport"));
			}

			if (jsonObj.has("endpoints")) {
				JSONArray jsonEndpoints = jsonObj.getJSONArray("endpoints");
				ArrayList<String> arrayEndpoints = new ArrayList<String>();
				for (int i = 0; i < jsonEndpoints.length(); i++) {
					arrayEndpoints.add(jsonEndpoints.getString(i));
				}
				setEndpoints(arrayEndpoints);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public HOptions(HOptions options) {
		this.setEndpoints(options.getEndpoints());
		this.setTransport(options.getTransport());
	}

	/* Getters & Setters */

	/**
	 * Transport layer used to connect to hNode (ie : socketio)
	 */
	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {

		this.transport = transport;

	}

	
	public List<String> getEndpoints() {
		return new ArrayList<String>(this.endpoints);
	}

	public void setEndpoints(List<String> endpoints) {
		if (endpoints != null && endpoints.size() > 0)
			this.endpoints = new ArrayList<String>(endpoints);
		else {
			this.endpoints = new ArrayList<String>();
			this.endpoints.add("http://localhost:8080/");
		}
	}


	/* overrides */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endpoints == null) ? 0 : endpoints.hashCode());
		result = prime * result
				+ ((transport == null) ? 0 : transport.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "HOptions [transport=" + transport + ", endpoints="
				+ endpoints + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HOptions other = (HOptions) obj;
		if (endpoints == null) {
			if (other.endpoints != null)
				return false;
		} else if (!endpoints.equals(other.endpoints))
			return false;
		if (transport == null) {
			if (other.transport != null)
				return false;
		} else if (!transport.equals(other.transport))
			return false;
		return true;
	}
}