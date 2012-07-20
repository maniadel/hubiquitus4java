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

/**
 * @author j.desousag
 * @version 0.4
 * hAPI result. For more info, see Hubiquitus reference
 */

public class HResult extends HStructure {

	public HResult(String reqid, String cmd, HJsonObj result) {
		super();
		setReqid(reqid);
		setCmd(cmd);
		setResult(result);
	}
	
	public HResult() {
		super();
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
	 * Mandatory. Filled by the hApi
	 * @return reqid. NULL if undefined
	 */
	public String getReqid() {
		return (String) this.get("reqid", String.class);
	}

	public void setReqid(String reqid) {
		this.put("reqid", reqid);
	}

	/**
	 * Mandatory. Execution status.
	 * @return status. NULL if undefined
	 */
	public ResultStatus getStatus() {
		int statusInt = (Integer) this.get("status", Integer.class);
		return ResultStatus.constant(statusInt);
	}

	public void setStatus(ResultStatus status) {
		this.put("status", status.value());
	}

	/**
	 * Only if result type is a JsonObject
	 * @warning object should either be a native object or a HObj
	 * @see getResultString()
	 * @see getResultArray()
	 * @return result of a command operation or a subscriptions operation. 
	 */
	public Object getResult() {
		return this.get("result", Object.class);
	}

	public void setResult(Object result) {
		this.put("result", result);
	}

	@Override
	public String getHType() {
		return "hresult";
	}

	
}