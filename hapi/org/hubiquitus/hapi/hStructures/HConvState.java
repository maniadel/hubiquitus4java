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
 * This kind of payload is used to describe the status of a thread of correlated messages identified by its convid.
 * Multiple hConvStates with the same convid can be published into a channel, specifying the evolution of the state of the thread during time.
 */

public class HConvState extends HStructure {
	
	public HConvState() {
		super();
	}
	
	public String getHType() {
		return "hconv";
	}
	
	/**
	 * The status of the thread
	 * @return topic description. NULL if undefined
	 */
	public String getStatus() {
		return (String) this.get("status", String.class);
	}

	public void setStatus(String status) {
		this.put("status", status);
	}
	
}

