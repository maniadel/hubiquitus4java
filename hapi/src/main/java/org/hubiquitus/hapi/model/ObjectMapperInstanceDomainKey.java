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


/**
 *
 */
package org.hubiquitus.hapi.model;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize;



/**
 * JSON Object Mapper instance key for Factory.
 * 
 * @author m.ma
 */
public enum ObjectMapperInstanceDomainKey {
	BASIC(true), PUBLISH_ENTRY(false), BOT_CONFIGURATION(false), HSERVER(false);
	
	ObjectMapper mapper;
	ObjectMapperInstanceDomainKey(boolean isBasicConfig) {
		mapper = new ObjectMapper();
		if (isBasicConfig) {
			return;
		}
		// Prevent serialization of null property
		mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		// Do not take into account properties in json string that do not exist in java Object when deserialization
		mapper.getDeserializationConfig().disable(Feature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	/**
	 * Get the instance JSON Object Mapper for a specific domain.
	 * @param key the domain key.
	 * @return JSON Object Mapper.
	 */
	public ObjectMapper getMapper() {
		return mapper;
	}
}
