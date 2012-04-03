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


package org.hubiquitus.hapi.hStructure;

import org.hubiquitus.hapi.error.ErrorsCode;

/**
 * @author j.desousag
 * @version 0.3
 * This structure describe the connection status
 */

public class HStatus {

	/**
	 * Contain the connection status
	 */
	private Status status;
	
	/**
	 * Number of the error( cf : Hibiquitus ref)
	 */
	private int errorCode;

	/**
	 * Contain a message error
	 */
	private String errorMsg;
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public void setErrorCode(ErrorsCode errorCode) {
		this.errorCode = errorCode.getValue();
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "HStatus [status=" + status + ", errorCode=" + errorCode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + errorCode;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		HStatus other = (HStatus) obj;
		if (errorCode != other.errorCode)
			return false;
		if (status == null) { 
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	
}
