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

import java.util.ArrayList;
import java.util.Map;

import org.hubiquitus.hapi.client.HClient;
import org.hubiquitus.hapi.client.HMessageDelegate;
import org.hubiquitus.hapi.client.HStatusDelegate;
import org.hubiquitus.hapi.hStructures.HCommand;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hapi.hStructures.HOptions;
import org.hubiquitus.hapi.hStructures.HStatus;
import org.hubiquitus.hubotsdk.Adapter;

public class HubotAdapter extends Adapter implements HMessageDelegate,HStatusDelegate{

	private String name;
	private String jid;
	private String pwdhash;
	private String endpoint;
	private HClient hclient;


	public HubotAdapter(String name) {
		this.name = name;
	}

	@Override
	public void sendCommand(HCommand command) {
		hclient.command(command, null);
	}

	@Override
	public void sendMessage(HMessage message) {
		hclient.publish(message, null);
	}

	@Override
	public void start() {
		hclient.onMessage(this);
		HOptions options = new HOptions();
		options.setTransport("socketio");
		ArrayList<String> endpoints = new ArrayList<String>();
		endpoints.add(endpoint);
		options.setEndpoints(endpoints);
		hclient.onMessage(this);
		hclient.onStatus(this);
		hclient.connect(jid, pwdhash, options);
	}

	public void onMessage(HMessage message) {
		if(message.getChid().contains("#") == false) {
			onInGoing(message);		
		}
	}
	
	public void onCommand(HCommand command) {
		
	}

	@Override
	public void stop() {
		hclient.disconnect();
	}


	@Override
	public void setProperties(Map<String,String> params) {	
		if(params.get("jid") != null) 
			setJid(params.get("jid"));
		if(params.get("pwdhash") != null) 
			setPwdhash(params.get("pwdhash"));
		if(params.get("endpoint") != null) 
			setEndpoint(params.get("endpoint"));
	}

	/* Getters and Setters */
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public HClient getHclient() {
		return hclient;
	}


	public void setHclient(HClient hclient) {
		this.hclient = hclient;
	}


	public String getJid() {
		return jid;
	}


	public void setJid(String jid) {
		this.jid = jid;
	}


	public String getPwdhash() {
		return pwdhash;
	}


	public void setPwdhash(String pwdhash) {
		this.pwdhash = pwdhash;
	}

	public String getEndpoint() {
		return endpoint;
	}


	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	


	@Override
	public String toString() {
		return "HubotAdapter [name=" + name + ", jid=" + jid + ", pwdhash="
				+ pwdhash + ", hclient=" + hclient + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hclient == null) ? 0 : hclient.hashCode());
		result = prime * result + ((jid == null) ? 0 : jid.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pwdhash == null) ? 0 : pwdhash.hashCode());
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
		HubotAdapter other = (HubotAdapter) obj;
		if (hclient == null) {
			if (other.hclient != null)
				return false;
		} else if (!hclient.equals(other.hclient))
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
		if (pwdhash == null) {
			if (other.pwdhash != null)
				return false;
		} else if (!pwdhash.equals(other.pwdhash))
			return false;
		return true;
	}

	@Override
	public void onStatus(HStatus status) {
		// TODO Auto-generated method stub
		System.out.println("status : " + status);
	}
	
}
