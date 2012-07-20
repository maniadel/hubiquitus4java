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
 * Describes a measure payload
 * Message acknowledgements
 * acknowledgements are used to identify the participants that have received or not received, read or not read a message 
 * Note, when a hMessage contains a such kind of payload, the convid must be provided with the same value has the acknowledged hMessage.
 */

public class HAck extends HStructure {
	
	public HAck() {
		super();
	}
	
	public String getHType() {
		return "hack";
	}

	/* Getters & Setters */
	
	/**
	 * Mandatory.
	 * The “msgid” of the message to which this acknowledgment refers.
	 * @return ackid. NULL if undefined
	 */
	public String getAckid() {
		return (String) this.get("ackid", String.class);
	}

	public void setAckid(String ackid) {
		this.put("ackid", ackid);
	}

	/**
	 * The status of the acknowledgement.
	 * @return acknowledgement status. NULL if undefined
	 */
	public HAckValue getAck() {
		String ackString = (String) this.get("ack", String.class);
		HAckValue ack = HAckValue.constant(ackString);
		return ack;
	}

	public void setAck(HAckValue ack) {
		this.put("ack", ack.value());
	}	
}

