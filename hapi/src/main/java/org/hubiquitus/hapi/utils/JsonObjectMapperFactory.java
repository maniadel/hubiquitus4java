/*
 * Copyright (c) Novedia Group 2011.
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

/**
 *
 */
package org.hubiquitus.hapi.utils;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.hubiquitus.hapi.model.ObjectMapperInstanceDomainKey;



/**
 * Factory to deliver JSON Object Mapper.
 * The ObjectMapper must be global for a specific domain. Those domains can be add to ObjectMapperInstanceDomainKey.
 * @author m.ma
 */
public class JsonObjectMapperFactory {
	
	/**
	 * Used Hashtable for synchronization.
	 */
	private static List<ObjectMapper> instances = new ArrayList<ObjectMapper>();
	
	/**
	 * Get the instance JSON Object Mapper for a specific domain.
	 * @param key the domain key.
	 * @return JSON Object Mapper.
	 */
	public static ObjectMapper getInstance(ObjectMapperInstanceDomainKey key) {
		ObjectMapper result = key.getMapper();
		return result;
	}
	
}