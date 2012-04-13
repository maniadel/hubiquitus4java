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


package org.hubiquitus.hapi.structure.connection;

/**
 * @author j.desousag
 * @version 0.3
 * Enumeration of different status of connection take by the client
 */

public enum ConnectionStatus {
	CONNECTED(1),
	CONNECTING(2),
	DISCONNECTING(3),
	DISCONNECTED(4),
	REATTACHING(5),
	REATTACHED(6),
	ERROR(7);
	
	private int value;
	
	private ConnectionStatus(int value) {
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
