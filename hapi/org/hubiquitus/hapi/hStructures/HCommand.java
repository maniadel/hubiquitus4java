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


package org.hubiquitus.hapi.hStructures;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import org.hubiquitus.hapi.util.DateISO8601;

/**
 * @version 0.4
 * hAPI Command. For more info, see Hubiquitus reference
 */

public class HCommand extends HStructure {
	
	public HCommand() {
		super();
		setSent(new GregorianCalendar());
		setTransient(true);
	}
	
	public HCommand(String entity, String cmd, Map<?, ?> params) {
		super();
		this();
		setEntity(entity);
		setCmd(cmd);
		setParams(params);
	}
	
	public String getHType() {
		return "hcommand";
	}
	
	/* Getters & Setters */
	
	/**
	 * Mandatory. Filled by the hApi.
	 * @return reqid. NULL if undefined
	 */
	public String getReqid() {
		return (String) this.get("reqid", String.class);
	}

	public void setReqid(String reqid) {
		this.put("reqid", reqid);
	}

	/**
	 * Filled by the hApi if empty
	 * @return requester jid. NULL if undefined 
	 */
	public String getRequester() {
		return (String) this.get("requester", String.class);
	}

	public void setRequester(String requester) {
		this.put("requester", requester);
	}

	/**
	 * Mandatory. Filled by the hApi.
	 * @return sender jid. NULL if undefined 
	 */
	public String getSender() {
		return (String) this.get("sender", String.class);
	}

	public void setSender(String sender) {
		this.put("sender", sender);
	}

	/**
	 * Mandatory.
	 * @return entity jid. NULL if undefined 
	 */
	public String getEntity() {
		return (String) this.get("entity", String.class);
	}
	
	public void setEntity(String entity) {
		this.put("entity", entity);
	}

	/**
	 * Mandatory. Filled by the hApi if empty
	 * @return date of submission. NULL if undefined
	 */
	public Calendar getSent() {
		Calendar sent = (Calendar) this.get("sent", Calendar.class);
		if (sent == null) {
			String sentStr = (String) this.get("sent", String.class);
			if (sentStr != null) {
				sent = (DateISO8601.toCalendar(sentStr));
			}
		}
		return sent;
	}

	public void setSent(Calendar sent) {
		this.put("sent", sent);
	}
	
	/**
	 * Mandatory.
	 * @return command. NULL if undefined
	 */
	public String getCmd() {
		return (String) this.get("cmd", String.class);
	}

	public void setCmd(String cmd) {
		this.put("cmd", cmd);
	}

	/**
	 * @return params throws to the hserver. NULL if undefined
	 */
	public Map<?, ?> getParams() {
		return (Map<?, ?>) this.get("params", Map.class);
	}

	public void setParams(Map<?, ?> params) {
		this.put("params", params);
	}

	/**
	 * @return persist message or not. NULL if undefined
	 */
	public Boolean getTransient() {
		return (Boolean) this.get("transient", Boolean.class);
	}

	public void setTransient(Boolean _transient) {
		this.put("transient", _transient);
	}	
	
	
}