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

package org.hubiquitus.hserver.jaxrs;

import java.util.Map;

import org.springframework.beans.factory.annotation.Required;

/**
 * jaxServer configuration
 * @author o.chauvie
 *
 */
public class JaxServerConfiguration {
	
	
	/**
	 * jaxServer configuration
	 */
	protected Map<String, Object> jaxServerConfigMap;
	
	
	/**
	 * Constructor
	 */
	public JaxServerConfiguration() {
		super();
	}
	
	/**
	 * Get the jax server host
	 * @return jax server host
	 */
	public String getJaxServerHost() {
		return (String)jaxServerConfigMap.get("jaxServerHost");
	}
	
	/**
	 * Get the jax server port
	 * @return jax server port
	 */
	public int getJaxServerPort() {
		String port = (String)jaxServerConfigMap.get("jaxServerPort");
		return Integer.valueOf(port);
	}
	
	 // Spring injections ------------------------------------
    @Required
    public void setJaxServerConfigMap(Map<String, Object> jaxServerConfigMap) {
		this.jaxServerConfigMap = jaxServerConfigMap;
	}

}
