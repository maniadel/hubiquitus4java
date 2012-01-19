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

package org.hubiquitus.hserver;

import java.util.Map;

import org.springframework.beans.factory.annotation.Required;

/**
 * hServer configuration
 * @author o.chauvie
 *
 */
public class HServerConfiguration {
	
	
	/**
	 * hServer configuration
	 */
	protected Map<String, Object> hServerConfigMap;
	
	
	/**
	 * Constructor
	 */
	public HServerConfiguration() {
		super();
	}
	
	/**
	 * Get the xmpp server host
	 * @return xmpp server host
	 */
	public String getXmppServerHost() {
		return (String)hServerConfigMap.get("xmppServerHost");
	}
	
	/**
	 * Get the xmpp server port
	 * @return xmpp server port
	 */
	public int getXmppServerPort() {
		String port = (String)hServerConfigMap.get("xmppServerPort");
		return Integer.valueOf(port);
	}
	
	/**
	 * Get the sub domain for component identification
	 * @return the sub domain
	 */
	public String getSubDomain() {
		return (String)hServerConfigMap.get("subDomain");
	}
	
	/**
	 * Get the secret key for component identification
	 * @return the secret key
	 */
	public String getSecretKey() {
		return (String)hServerConfigMap.get("secretKey");
	}
	
	/**
	 * Get the sub domain description
	 * @return the sub domain description
	 */
	public String getSubDomainDescription() {
		return (String)hServerConfigMap.get("subDomainDescription");
	}
	
	/**
	 * Get the xmpp user name
	 * @return the xmpp user name
	 */
	public String getXmppusername() {
		return (String)hServerConfigMap.get("xmppusername");
	}
	
	
	 // Spring injections ------------------------------------
    @Required
    public void setHServerConfigMap(Map<String, Object> hServerConfigMap) {
		this.hServerConfigMap = hServerConfigMap;
	}

}
