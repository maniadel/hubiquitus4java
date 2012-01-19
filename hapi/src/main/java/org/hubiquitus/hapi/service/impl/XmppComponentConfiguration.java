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

package org.hubiquitus.hapi.service.impl;

import java.util.Map;

/**
 * Xmpp component configuration
 * @author o.chauvie
 *
 */
public class XmppComponentConfiguration {

	/**
	 * xmpp component configuration
	 */
    protected Map<String, Object> hServerConfigMap;
    
    
   /**
    * Constructor 
    */
    public XmppComponentConfiguration() {
    	super();
    }
    
	/**
	 * Get the xmpp component sub domain
	 * @return the sub domain
	 */
    public String getSubDomain() {
    	return (String)hServerConfigMap.get("subDomain");
    }
    
    /**
     * Get the timeout of component IQ request
     * @return the timeout (in ms)
     */
    public int getRequestTimeOut() {
    	return new Integer((String)hServerConfigMap.get("requestTimeout")).intValue();
    }
    
    
    
    
    // Spring injections ------------------------------------------------------
    public void setHServerConfigMap(Map<String, Object> hServerConfigMap) {
		this.hServerConfigMap = hServerConfigMap;
	}
    
    
}
