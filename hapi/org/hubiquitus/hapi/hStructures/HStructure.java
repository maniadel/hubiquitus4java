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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @version 0.4.0
 * Base class for hAPI native structures
 * Implements helper functions
 * All hAPI native structures are implemented as getters and setters on a Map
 * Equal is base on map content comparison
 * To string is based on map description
 */

public class HStructure implements HObj{

	private Map<String, Object> obj;
	
	public HStructure() {
		obj = new LinkedHashMap<String, Object>();
	}
	
	/**
	 * get a value from the structure with the given type
	 * @param key
	 * @param type
	 * @return
	 */
	Object get(String key, Class<?> type) {
		Object object = null;
		if(this.getNativeObj() != null && (this.getNativeObj() instanceof Map) 
				&& (object = ((Map<?, ?>)this.getNativeObj()).get(key)) != null
				&& (type.isAssignableFrom(object.getClass()))) {
			return object;
		}
		
		return null;
	}
	
	/**
	 * Set a value in the structure
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	void put(String key, Object value) {
		if(this.getNativeObj() != null && (this.getNativeObj() instanceof Map)) {
			((Map<String, Object>)this.getNativeObj()).put(key, value);
		}
	}

	@Override
	public Object getNativeObj() {
		return this.obj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setNativeObj(Object nativeRepresentationObj) {
		if(nativeRepresentationObj != null && nativeRepresentationObj instanceof Map) {
			this.obj = (Map<String, Object>)nativeRepresentationObj;
		} else {
			this.obj = new LinkedHashMap<String, Object>();
		}
	}
	
	/** equal and tostring **/
	public String toString() {
		return this.getNativeObj().toString();
	}

	/**
	 * Equal check is made on the map
	 * if given obj implements hobj, equal is made on native objects representation
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HObj) {
			return ((HObj) obj).getNativeObj().equals(this.getNativeObj());
		} else {
			return obj.equals(this.getNativeObj());
		}
	}
	
}
