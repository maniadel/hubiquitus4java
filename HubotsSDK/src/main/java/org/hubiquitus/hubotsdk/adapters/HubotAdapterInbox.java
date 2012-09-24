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

package org.hubiquitus.hubotsdk.adapters;

import java.util.Map;

import org.hubiquitus.hapi.client.HMessageDelegate;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.AdapterInbox;

public class HubotAdapterInbox extends AdapterInbox implements HMessageDelegate {
	
	private String jid;
	private String endpoint;

	public HubotAdapterInbox(String name) {
		this.name = name;
	}
	
	@Override
	public void setProperties(Map<String, String> params) {
		if(params.get("jid") != null) 
			this.jid = params.get("jid");
		if(params.get("endpoint") != null) 
			this.endpoint = params.get("endpoint");
	}

	@Override
	public void start() {
		hclient.onMessage(this);
	}

	@Override
	public void stop() {
	}
	
	public void onMessage(HMessage message) {
		if(!message.getPublisher().equals(jid))
			put(message);		
	}

    /**
     * get the Jid
     * @return the jid of the current client actor
     */
	public String getJid() {
		return jid;
	}

    /**
      * @return the endpoint used for the connection with the hNode
     */
	public String getEndpoint() {
		return endpoint;
	}

	@Override
	public String toString() {
		return "HubotAdapter [name=" + name + ", jid=" + jid + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endpoint == null) ? 0 : endpoint.hashCode());
		result = prime * result + ((jid == null) ? 0 : jid.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		HubotAdapterInbox other = (HubotAdapterInbox) obj;
		if (endpoint == null) {
			if (other.endpoint != null)
				return false;
		} else if (!endpoint.equals(other.endpoint))
			return false;
		if (jid == null) {
			if (other.jid != null)
				return false;
		} else if (!jid.equals(other.jid))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
