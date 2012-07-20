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
 * @version 0.4.0
 * Any structure of the hapi or any object that will be manipulated by the
 * hapi (ie : message payloads) should implement this interface
 */
public interface HObj {

	/**
	 * Representation of the object with basic native objects which are :
	 * LinkedHashMap<String,Object> (json object), Map<String, Object>, ArrayList<Object> (json array)
	 * String, Integer(number), Long(number), BigInteger(number), Double(number)
	 * Boolean, null, Calendar
	 * This will be mainly used to convert it to json
	 * @return
	 */
	Object getNativeObj();
	void setNativeObj(Object nativeRepresentationObj);
	
	public String getHType();
	
}
