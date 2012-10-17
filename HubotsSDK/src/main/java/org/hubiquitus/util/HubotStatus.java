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

public enum HubotStatus {
	
	CREATED (0), 			// just created, not connected througth to the hclient API
	STARTED (1), 			// connected to the hAPI but not ready
	INITIALIZED (2),		// connected and initialized
	READY (3), 				// connected and ready. Adapters are all initialized and started
	STOPPED (4);			// disconnected and adapters are all stopped
	
	private int value;
	
	private HubotStatus(int value) {
		this.value = value;
	}
	
	/**
	 * @return int equivalent.
	 */
	public int value() {
		return value;
	}
	
	/**
	 * Get constant for value
	 * @param value
	 * @return
	 */
	public static HubotStatus constant(int value) {
		HubotStatus[] _values = HubotStatus.values();
		return _values[value];
	}
}
