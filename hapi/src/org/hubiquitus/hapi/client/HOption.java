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

package org.hubiquitus.hapi.client;

import java.util.ArrayList;
import java.util.List;


/**
 * @author j.desousag
 * @version 0.3
 *  The HOption is used to set the parameter of the connexion 
 */

public class HOption implements Cloneable {

	
	/* Parameters */
	
	/**
	 * JId of the client.
	 */

	private String publisher;
	
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
	
	/* Function */
	
	@Override
	public String toString() {
		return "HOption [route=" + route + ", transport=" + transport
				+ ", endpoints=" + endpoints.toArray() + ", nbLastMessage="
				+ nbLastMessage + ", publisher=" + publisher + ", password="
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
				+ ((publisher == null) ? 0 : publisher.hashCode());
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
		if (publisher == null) {
			if (other.publisher != null)
				return false;
		} else if (!publisher.equals(other.publisher))
			return false;
		return true;
	}
	
	@Override
	public HOption clone() {
		HOption option = null;
		
		try {
			option = (HOption) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return option;
	}

	
	
	/* Getters & Setters */
	
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
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

}
