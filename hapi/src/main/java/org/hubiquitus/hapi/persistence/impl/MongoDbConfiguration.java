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

package org.hubiquitus.hapi.persistence.impl;

import java.util.Map;

import org.hubiquitus.hapi.persistence.impl.MongoDbConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

/**
 * MongoDB configuration
 * @author o.chauvie
 *
 */

public class MongoDbConfiguration {
	
	Logger logger = LoggerFactory.getLogger(MongoDbConfiguration.class);
	
	/**
	 * hServer configuration
	 */
	protected Map<String, Object> mongoDbConfigMap;
	
	
	/**
	 * Constructor
	 */
	public MongoDbConfiguration() {
		super();
	}
	
	/**
	 * Get the MongoDb host
	 * @return the host
	 */
	public String getMongoDbHost() {
		return (String)mongoDbConfigMap.get("mongoDbHost");
	}
	
	/**
	 * Get the MongoDb port
	 * @return the port
	 */
	public int geMongoDbPort() {
		String port = (String)mongoDbConfigMap.get("mongoDbPort");
		return Integer.valueOf(port);
	}
	
	/**
	 * Get the MongoDb poll size
	 * @return the poll size
	 */
	public int getMongoDbPoolSize() {
		String pollSize = (String)mongoDbConfigMap.get("mongoDbPoolSize");
		logger.debug("mongoDbPollSize:" + pollSize);
		return Integer.valueOf(pollSize);
	}

	
	/**
	 * Get the MongoDb default db name
	 * @return the default collection
	 */
	public String getMongoDbDefaultDbName() {
		return (String) mongoDbConfigMap.get("mongoDbDefaultDbName");
	}
	
	/**
	 * Get the MongoDb default collection
	 * @return the default collection
	 */
	public String getMongoDbDefaultCollection() {
		return (String) mongoDbConfigMap.get("mongoDbDefaultCollection");
	}
		
	 // Spring injections ------------------------------------
    @Required
    public void setMongoDbConfigMap(Map<String, Object> mongoDbConfigMap) {
		this.mongoDbConfigMap = mongoDbConfigMap;
	}
}