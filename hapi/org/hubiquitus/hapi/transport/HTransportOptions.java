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

package org.hubiquitus.hapi.transport;

import org.hubiquitus.hapi.structures.JabberID;

/** 
 * @version 0.3
 *
 */

public class HTransportOptions {
	private JabberID jid = null;
	private String password = null;
	private String serverHost = null;
	private int serverPort = 0;
	private String endpointHost = null;
	private int endpointPort = 0;
	private String endpointPath = null;
	private String hNode = "hnode";
	
	
	public HTransportOptions() {
		super();
	}
	
	/* getters and setters */

	/**
	 * Convenient function
	 * give username part of the jid
	 * @return username(without domain or resource)
	 */
	public String getUsername() {
		if (jid == null) {
			throw new NullPointerException("Error : " + this.getClass().getName() + " need a jid");
		}
		
		return jid.getUsername();
	}
	
	/**
	 * Convenient function
	 * give resource part of the jid
	 * @return resource
	 */
	public String getResource() {
		if (jid == null) {
			throw new NullPointerException("Error : " + this.getClass().getName() + " need a jid");
		}
		
		return jid.getResource();
	}
	
	/**
	 * 
	 */
	public String gethNodeService() {
		String nodeService = null;
		
		if(this.jid != null) {
			nodeService = this.hNode + "." + this.jid.getDomain();
		}
		
		return nodeService;
	}
	/**
	 * user jid (ie : my_user@domain.com/resource)
	 * @return
	 */
	public JabberID getJid() {
		return jid;
	}


	public void setJid(JabberID jid) {
		this.jid = jid;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * server host (ie : localhost)
	 * @return
	 */
	public String getServerHost() {
		return serverHost;
	}


	public void setServerHost(String serverHost) {
		if (serverHost == null || serverHost.equals("")) {
			this.serverHost = null;
		} else {
			this.serverHost = serverHost;
		}
	}

	/**
	 * server port (ie : 5222 for xmpp)
	 * @return
	 */
	public int getServerPort() {
		return serverPort;
	}


	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}


	/**
	 * endpoint host (ie : localhost)
	 * @return
	 */
	public String getEndpointHost() {
		return endpointHost;
	}


	public void setEndpointHost(String endpointHost) {
		this.endpointHost = endpointHost;
	}
	
	/** 
	 * endpoint port (ie : 8080)
	 * @return
	 */
	public int getEndpointPort() {
		return endpointPort;
	}


	public void setEndpointPort(int endpointPort) {
		this.endpointPort = endpointPort;
	}

	/**
	 * endpoint path (ie my_path) without begin /
	 * @return
	 */
	public String getEndpointPath() {
		return endpointPath;
	}


	public void setEndpointPath(String endpointPath) {
		this.endpointPath = endpointPath;
	}

	public String gethNode() {
		return hNode;
	}

	public void sethNode(String hNode) {
		this.hNode = hNode;
	}
	/* overrides */
	
	@Override
	public String toString() {
		return "HTransportOptions [jid=" + jid + ", password=" + password
				+ ", serverHost=" + serverHost + ", serverPort=" + serverPort
				+ ", endpointHost=" + endpointHost + ", endpointPort="
				+ endpointPort + ", endpointPath=" + endpointPath + ", hNode="
				+ hNode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endpointHost == null) ? 0 : endpointHost.hashCode());
		result = prime * result
				+ ((endpointPath == null) ? 0 : endpointPath.hashCode());
		result = prime * result + endpointPort;
		result = prime * result + ((hNode == null) ? 0 : hNode.hashCode());
		result = prime * result + ((jid == null) ? 0 : jid.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((serverHost == null) ? 0 : serverHost.hashCode());
		result = prime * result + serverPort;
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
		HTransportOptions other = (HTransportOptions) obj;
		if (endpointHost == null) {
			if (other.endpointHost != null)
				return false;
		} else if (!endpointHost.equals(other.endpointHost))
			return false;
		if (endpointPath == null) {
			if (other.endpointPath != null)
				return false;
		} else if (!endpointPath.equals(other.endpointPath))
			return false;
		if (endpointPort != other.endpointPort)
			return false;
		if (hNode == null) {
			if (other.hNode != null)
				return false;
		} else if (!hNode.equals(other.hNode))
			return false;
		if (jid == null) {
			if (other.jid != null)
				return false;
		} else if (!jid.equals(other.jid))
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
		return true;
	}

	
	
}
