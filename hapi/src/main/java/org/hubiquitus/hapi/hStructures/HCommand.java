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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @version 0.5 hAPI Command. For more info, see Hubiquitus reference
 */

public class HCommand extends JSONObject {

	// private JSONObject hcommand = new JSONObject();

	public HCommand() {
		super();
	}

	public HCommand(String cmd, JSONObject params) {
		this();
		setCmd(cmd);
		setParams(params);
	}

	public HCommand(JSONObject jsonObj) throws JSONException {
		super(jsonObj, JSONObject.getNames(jsonObj));
	}

	/* HJsonObj interface */

//	public JSONObject toJSON() {
//		return this.hcommand;
//	}
//
//	public void fromJSON(JSONObject jsonObj) {
//		if (jsonObj != null) {
//			this.hcommand = jsonObj;
//		} else {
//			this.hcommand = new JSONObject();
//		}
//	}
//
//	public String getHType() {
//		return "hcommand";
//	}
//
//	@Override
//	public String toString() {
//		return hcommand.toString();
//	}
//
//	/**
//	 * Check are made on : cmd.
//	 * 
//	 * @param HCommand
//	 * @return Boolean
//	 */
//	public boolean equals(HCommand obj) {
//		if (obj.getCmd() != this.getCmd())
//			return false;
//		return true;
//	}
//
//	@Override
//	public int hashCode() {
//		return hcommand.hashCode();
//	}

	/* Getters & Setters */

	/**
	 * Mandatory.
	 * 
	 * @return command. NULL if undefined
	 */
	public String getCmd() {
		String cmd;
		try {
			cmd = this.getString("cmd");
		} catch (JSONException e) {
			cmd = null;
		}
		return cmd;
	}

	public void setCmd(String cmd) {
		try {
			if (cmd == null) {
				this.remove("cmd");
			} else {
				this.put("cmd", cmd);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return params throws to the hserver. NULL if undefined
	 */
	public JSONObject getParams() {
		JSONObject params;
		try {
			params = this.getJSONObject("params");
		} catch (JSONException e) {
			params = null;
		}
		return params;
	}

	public void setParams(JSONObject params) {
		try {
			if (params == null) {
				this.remove("params");
			} else {
				this.put("params", params);
			}
		} catch (JSONException e) {
		}
	}

}