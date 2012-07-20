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
 * @version 0.4
 * This structure describe the connection status
 */

public class HStatus extends HStructure {
	
	public HStatus(ConnectionStatus status ,ConnectionError errorCode ,String errorMsg) {
		super();
		setStatus(status);
		setErrorCode(errorCode);
		setErrorMsg(errorMsg);
	}
	
	public HStatus() {
		super();
	}

	public String getHType() {
		return "hstatus";
	}
	
	/**
	 * Mandatory. Connection status.
	 * @return status. NULL if undefined
	 */
	public ConnectionStatus getStatus() {
		return ConnectionStatus.constant((Integer)this.get("status", Integer.class));
	}

	public void setStatus(ConnectionStatus status) {
		this.put("status", status.value());
	}

	/**
	 * Mandatory. Valid only if status = error
	 * @return error code. NULL if undefined
	 */
	public ConnectionError getErrorCode() {
		return ConnectionError.constant((Integer)this.get("errorCode", Integer.class));
	}

	
	public void setErrorCode(ConnectionError errorCode) {
		this.put("errorCode", errorCode.value());
	}
	
	/**
	 * Error message. Platform dependent (low level layer messages)
	 * Should only be used for debug
	 * @return error message. NULL if undefined
	 */
	public String getErrorMsg() {
		return (String) this.get("errorMsg", String.class);
	}

	public void setErrorMsg(String errorMsg) {
		this.put("errorMsg", errorMsg);
	}


}
