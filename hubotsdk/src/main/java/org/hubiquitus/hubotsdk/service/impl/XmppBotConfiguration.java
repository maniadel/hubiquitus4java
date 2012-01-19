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
package org.hubiquitus.hubotsdk.service.impl;

import java.util.List;
import java.util.Map;

/**
 * @author a.benkirane
 *
 */
public class XmppBotConfiguration {

	/**
	 * xmpp server configuration
	 */
    protected Map<String, Object> xmppConfigMap;
    
    
   /**
    * Constructor 
    */
    public XmppBotConfiguration() {
    	super();
    }
    
    /**
     * getter xmpp host adress
     * @return the xmpp host adresss
     */
    public String getXmppHostAddress() {
    	return (String)xmppConfigMap.get("publisher.xmpphostaddress");
    }
    
    /**
     * getter xmpp service
     * @return the xmpp service
     */
    public String getXmppService() {
       return (String)xmppConfigMap.get("publisher.xmppservice");
       
    }
    
    /**
     * getter xmpp host port
     * @return the xmpp host port
     */
    public int getXmppHostPort() {
    	return new Integer((String)xmppConfigMap.get("publisher.xmpphostport")).intValue();
    }
    
    /**
     * getter xmpp user name
     * @return the xmpp user name
     */
    public String getXmppUserName() {
    	return (String)xmppConfigMap.get("publisher.xmppusername");
    }
    
    /**
     * getter xmpp user password
     * @return the xmpp user password
     */
    public String getXmppUserPassword() {
    	return (String)xmppConfigMap.get("publisher.xmppuserpassword");
    }
    
    /**
     * getter type of configuration
     * @return the type of configuration
     */
    public String getType() {
    	return (String)xmppConfigMap.get("configuration.type");
    }
    
    /**
     * getter address of local configuration
     * @return the address of local configuration
     */
    public String getHostAddress() {
    	return (String)xmppConfigMap.get("configuration.localhostaddress");
    }
    
    /**
     * getter configuration id
     * @return the configuration id
     */
    public String getId() {
    	return (String)xmppConfigMap.get("configuration.id");
    }
    
    /**
     * getter list of admin groups
     * @return the list of admin groups
     */
    @SuppressWarnings("unchecked")
	public List<String> getAdminGroups() {
    	return (List<String>)xmppConfigMap.get("publisher.admingroups");
    }
    
    /**
     * getter if proxy is used
     * @return proxy used or not used
     */
    public String getUseProxy() {
    	return (String)xmppConfigMap.get("fetcher.useproxy");
    }
    
    /**
     * getter xmpp proxy address
     * @return the xmpp proxy address
     */
    public String getProxyAddress() {
    	return (String)xmppConfigMap.get("fetcher.proxyaddress");
    }
    
    /**
     * getter xmpp proxy port
     * @return the xmpp proxy port
     */
    public int getProxyPort() {
    	return new Integer((String)xmppConfigMap.get("fetcher.proxyport")).intValue();
    }    
    
    /**
     * getter node name
     * @return the node name
     */
    public String getNodeName() {
    	return (String)xmppConfigMap.get("nodename");
    }
    
    /**
     * getter if xmpp debug console is enabled or not
     * @return debug console enabled
     */
    public boolean isXmppDebugEnable() {
    	String result = (String)xmppConfigMap.get("xmpp.debug.enable");
    	if ("true".equals(result)) {
    		return true;
    	} else {
    		return false;
    	}
    	
    }
    
    
    /**
     * Return the xmpp configuration in jSon format
     * @return the configuration in jso, format
     */
    public String getXmppConfigurationXML() {
        String ret = "";
        ret += "{ ";
        ret += "\"XMPP Host\" : \"" + this.getXmppHostAddress() + "\", ";
        ret += "\"XMPP Port\" : \"" + this.getXmppHostPort() + "\", ";
        ret += "\"XMPP User name\" : \"" + this.getXmppUserName() + "\", ";
        ret += "\"XMPP User password\" : \"" + /*this.xmppUserPassword*/"*****" + "\", ";
        ret += "\"Admin Groups\" : \"";
        for (String s : this.getAdminGroups()) {
            ret += s + "; ";
        }
        ret += "\", ";
        if ((this.getUseProxy() != null) && (this.getUseProxy().equals("true"))) {
            ret += "Proxy IP\" : \"" + this.getProxyAddress() + "\", ";
            ret += "Proxy Port\" : \"" + this.getProxyPort() + "\"";
        }
        ret += "}";
        return ret;
    }
    
    // Spring injections ------------------------------------------------------
    public void setXmppConfigMap(Map<String, Object> xmppConfigMap) {
		this.xmppConfigMap = xmppConfigMap;
	}
    
    
}
