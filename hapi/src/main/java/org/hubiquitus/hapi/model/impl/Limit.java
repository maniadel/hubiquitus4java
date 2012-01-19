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

package org.hubiquitus.hapi.model.impl;

public class Limit {

	private String active = "false";
	
	private String value = "0";

	/**
	 * @return the active
	 */
	public String isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	public Limit(String active, String value) {
		super();
		this.active = active;
		this.value = value;
	}


	public Limit() {
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Get KeyRequest in xml format 
	 * @return the xml
	 */
	public String toXML() {
		StringBuffer xml = new StringBuffer();
		xml.append("<key limit=\"");
		xml.append(active);
		xml.append("\">");
		xml.append(value);
		xml.append("</key>");
		return xml.toString();
	}
	
	// methode toString
	public String toString(){
		String objet = "\t\t\tlimit : " + value;
		objet = objet + "\n\t\t\tactive : " + active;
		return objet;
	}
	
}
