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

package org.hubiquitus.hapi.hStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author j.desousag
 * @version 0.3
 *  The HOption is used to set the parameter of the connexion 
 */

public class HOption {

	/**
	 * Username of the client.
	 */

	private String username;
	
	/**
	 * Password of the client. Don't appear in the log.
	 */

	private String password;
	
	/**
	 * Precise the XMPP port and Domain to be used instead of the default values
	 */
	private String route;
	
	/**
	 * Transport use to connect to hNode
	 */
	private String transport;
	
	/**
	 * List of endpoints to connect to the server
	 */
	private List<String> endpoints;
	
	/**
	 * Number of message request by the getLastMessage method
	 */
	private int nbLastMessage;
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		if(transport.compareTo("xmpp")==0)
			this.transport = transport;
		else
			this.transport = "xmpp";
	}

	public List<String> getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(List<String> endpoints) {
		if(endpoints != null)
			this.endpoints = endpoints;
		else
			this.endpoints = new ArrayList<String>();
	}

	public int getNbLastMessage() {
		return nbLastMessage;
	}

	public void setNbLastMessage(int nbLastMessage) {
		if(nbLastMessage >=1)
			this.nbLastMessage = nbLastMessage;
		else
			this.nbLastMessage = 10;
	}

	@Override
	public String toString() {
		return "HOption [route=" + route + ", transport=" + transport
				+ ", endpoints=" + endpoints.toArray() + ", nbLastMessage="
				+ nbLastMessage + ", username=" + username + ", password="
				+ "********" + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endpoints == null) ? 0 : endpoints.hashCode());
		result = prime * result + nbLastMessage;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((route == null) ? 0 : route.hashCode());
		result = prime * result
				+ ((transport == null) ? 0 : transport.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HOption other = (HOption) obj;
		if (endpoints == null) {
			if (other.endpoints != null)
				return false;
		} else if (!endpoints.equals(other.endpoints))
			return false;
		if (nbLastMessage != other.nbLastMessage)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (route == null) {
			if (other.route != null)
				return false;
		} else if (!route.equals(other.route))
			return false;
		if (transport == null) {
			if (other.transport != null)
				return false;
		} else if (!transport.equals(other.transport))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
		
}
