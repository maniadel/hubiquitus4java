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

package org.hubiquitus.hapi.error;

/**
 * @author j.desousag
 * @version 0.3
 * ErrorCode contain the error name return during a connection
 */


public enum ErrorsCode {
	
	NO_ERROR(0),
	JID_MALFORMAT(1),
	CONN_TIMEOUT(2),
	AUTH_FAILED(3),
	ATTACH_FAILED(4),
	ALREADY_CONNECTED(5),
	TECH_ERROR(6);
	
	private int value;
	
	private ErrorsCode(int value) {
		this.value = value;
	}
	
	/**
	 * Method to get the value of ErrorCode
	 * @return ErrorCode's value
	 */
	
	public int getValue() {
		return value;
	}
	
}
