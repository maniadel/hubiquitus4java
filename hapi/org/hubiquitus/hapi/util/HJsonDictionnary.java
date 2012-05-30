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

package org.hubiquitus.hapi.util;

import org.hubiquitus.hapi.hStructures.HJsonObj;
import org.json.JSONObject;

/**
 * @version 0.3
 * Basic implementation of HJonObj. Can be use as a dictionnary.
 */
public class HJsonDictionnary implements HJsonObj{
	
	private JSONObject jsonObj;

	public HJsonDictionnary() {
		jsonObj = new JSONObject();
	}
	
	public HJsonDictionnary(JSONObject jsonObj) {
		this.fromJSON(jsonObj);
	}
	
	public Object get(String key) {
		Object value; 
		try {
			value = jsonObj.get(key);
		} catch (Exception e) {
			value = null;
		}
		return value;
	}
	
	public void put(String key, Object value) {
		try {
			jsonObj.put(key, value);
		} catch (Exception e) {
			System.out.println("erreur :" + this.getClass());
		}		
	}
	
	@Override
	public JSONObject toJSON() {
		return jsonObj;
	}

	@Override
	public void fromJSON(JSONObject jsonObj) {
		this.jsonObj = jsonObj;		
	}

	@Override
	public String getHType() {
		return "hjsondictionnary";
	}
	
	@Override
	public String toString() {
		return "HJsonDictionnary [jsonObj=" + jsonObj.toString() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return jsonObj.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return jsonObj.hashCode();
	}
}
