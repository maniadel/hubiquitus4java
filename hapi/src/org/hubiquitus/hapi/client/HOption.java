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

import org.hubiquitus.hapi.model.JabberID;


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

	private JabberID jid;
	
	/**
	 * Password of the client. Don't appear in the log.
	 */

	private String password;
	
	/**
	 * Precise the host of the server
	 */
	private String serverHost;
	
	/**
	 * Precise the server port
	 */
	private int serverPort = 5222;
	
	/**
	 * Transport use to connect to hNode
	 */
	private String transport;
	
	/**
	 * List of endpoints to connect to the server
	 */
	private List<String> endpoints = new ArrayList<String>();
	
	/**
	 * Number of message request by the getLastMessage method
	 */
	private int nbLastMessage;
	
	/**
	 * Constructor by default
	 */
	public HOption() {
		this(null, 0, null, null, 0);
	}
	
	/**
	 * Constructor with param
	 * @param serverHost
	 * @param serverPort
	 * @param transport
	 * @param endPoints
	 * @param nbLastMessage
	 */
	
	public HOption(String serverHost ,int serverPort ,String transport, List<String> endPoints ,int nbLastMessage){
		this.serverHost = serverHost;
		setTransport(transport);
		setServerPort(serverPort);
		setEndpoints(endpoints);
		setNbLastMessage(nbLastMessage);
	}
	
	@Override
	public String toString() {
		return "HOption [jid=" + jid + ", password=" + password
				+ ", serverHost=" + serverHost + ", serverPort=" + serverPort
				+ ", transport=" + transport + ", endpoints=" + endpoints
				+ ", nbLastMessage=" + nbLastMessage + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endpoints == null) ? 0 : endpoints.hashCode());
		result = prime * result + ((jid == null) ? 0 : jid.hashCode());
		result = prime * result + nbLastMessage;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((serverHost == null) ? 0 : serverHost.hashCode());
		result = prime * result + serverPort;
		result = prime * result
				+ ((transport == null) ? 0 : transport.hashCode());
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
		if (jid == null) {
			if (other.jid != null)
				return false;
		} else if (!jid.equals(other.jid))
			return false;
		if (nbLastMessage != other.nbLastMessage)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (serverHost == null) {
			if (other.serverHost != null)
				return false;
		} else if (!serverHost.equals(other.serverHost))
			return false;
		if (serverPort != other.serverPort)
			return false;
		if (transport == null) {
			if (other.transport != null)
				return false;
		} else if (!transport.equals(other.transport))
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
	
	public JabberID getJabberID() {
		return jid;
	}

	public void setJabberID(String login) {
		this.jid = new JabberID(login);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		if(serverPort == 0)
			this.serverPort = 5222;
		else
			this.serverPort = serverPort;
	}
	
	public void setServerPort(String serverPort) {
		this.serverPort = Integer.valueOf(serverPort).intValue();
	}
	
	public String getTransport() {
		return transport;
	}	

	public void setTransport(String transport) {
		if(transport != null)
			this.transport = transport;
		else
			this.transport = "xmpp";
	}

	public List<String> getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(List<String> endpoints) {
		if(endpoints != null && endpoints.size() != 0)
			this.endpoints = endpoints;
		else {
			this.endpoints.clear();
			this.endpoints.add("http://localhost:5280/xmpp-bind/");
		}	
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
