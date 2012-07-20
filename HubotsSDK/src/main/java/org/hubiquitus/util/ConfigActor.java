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

package org.hubiquitus.util;

import java.util.ArrayList;
import java.util.Map;

public class ConfigActor {

	public static class AdapterConfig {
		public String name,type;
		public Map<String,String> properties;

		public Map<String,String> getProperties() { return properties; }
		public String getName() { return name; }
		public String getType() { return type; }

		public void setName(String s) { name = s; }
		public void setType(String s) { type = s; }
		public void setProperties(Map<String,String> map) { properties = map;}

		@Override
		public String toString() {
			return "Adapter [name=" + name + ", type=" + type + ", properties="
					+ properties + "]";
		}
	}

	public String name;
	public String jid;
	public String pwdhash;
	public String endpoint;
	public ArrayList<AdapterConfig> adapters;
	public ArrayList<String> inbox;
	public ArrayList<String> outboxes;

	public String getName() { return name; }
	public String getJid() { return jid; }
	public String getPwdhash() { return pwdhash; }
	public String getEndpoint() { return endpoint; }
	public ArrayList<AdapterConfig> getAdapters() { return adapters; }
	public ArrayList<String> getInbox() { return inbox; }
	public ArrayList<String> getOutboxes() { return outboxes; }

	public void setName(String s) { name = s; }
	public void setJid(String s) { jid = s; }
	public void setPwdhash(String s) { pwdhash = s; }
	public void setEndpoint(String s) { endpoint = s; }
	public void setAdapters(ArrayList<AdapterConfig> a) { adapters = a; }
	public void setInbox(ArrayList<String> a) { inbox = a; }
	public void setOutboxes(ArrayList<String> a) { outboxes = a; }

	@Override
	public String toString() {
		String test =  "ConfigActor [name=" + name + ", jid=" + jid + ", pwdhash="
				+ pwdhash + ", endpoints=" + endpoint + ", adapters=";
		if( adapters == null )
			test += "null , inbox=" + inbox + ", outbox=" + outboxes + "]";
		else 
			test += adapters.toString() +" , inbox=" + inbox + ", outbox=" + outboxes + "]";
		return test;
	}
}
