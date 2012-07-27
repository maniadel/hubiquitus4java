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

import java.util.GregorianCalendar;
import java.util.Map;

import org.hubiquitus.hapi.hStructures.HCommand;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.AdapterOutbox;

public class HubotAdapterOutbox extends AdapterOutbox {

	private String name;
	private String jid;
	
	public HubotAdapterOutbox(String name) {
		this.name = name;
	}
	
	@Override
	public void sendCommand(HCommand command) {
		
		hclient.command(command, this);
	}

	@Override
	public void sendMessage(HMessage message) {
		hclient.publish(message, this);
	}


	@Override
	public void setProperties(Map<String,String> params) {	
		if(params.get("jid") != null) 
			this.jid = params.get("jid");
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

	/* Getters and Setters */
	public String getJid() {
		return jid;
	}


	@Override
	public String toString() {
		return "HubotAdapter [name=" + name + ", jid=" + jid + "]";
	}
}
