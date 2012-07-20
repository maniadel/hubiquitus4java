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
 * Alert message payload
 */

public class HAlert extends HStructure {
	
	public HAlert() {
		super();
	}
	
	public String getHType() {
		return "halert";
	}
	
	/* Getters & Setters */
	
	/**
	 * The message provided by the author to describe the alert. 
	 * @return alert message. NULL if undefined
	 */
	public String getAlert() {
		return (String) this.get("alert", String.class);
	}

	public void setAlert(String alert) {
		this.put("alert", alert);
	}
}