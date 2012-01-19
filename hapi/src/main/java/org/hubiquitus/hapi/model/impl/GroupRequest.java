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


/**
 * Key for data base request
 * @author l.chong-wing
 *
 */
public class GroupRequest {

	/**
	 * Data filter field
	 */
	private String grouper;
	
	
	/**
	 * Constructor with default operatir egual 
	 * @param sorter the data filter field
	 * @param value the data filter value 
	 */
	public GroupRequest(String grouper) {
		super();
		this.grouper = grouper;
	}


	public GroupRequest() {
	}

	/**
	 * Get KeyRequest in xml format 
	 * @return the xml
	 */
	public String toXML() {
		StringBuffer xml = new StringBuffer();
		xml.append("<key grouper=\"");
		xml.append(grouper);
		xml.append("\">");
		xml.append("</key>");
		return xml.toString();
	}

	
	/**
	 * Getter Sorter
	 * @return the sorter
	 */
	public String getSorter() {
		return grouper;
	}

	/**
	 * Setter sorter
	 * @param sorter the sorter to set
	 */
	public void setSorter(String sorter) {
		this.grouper = sorter;
	}


	// methode toString
	public String toString(){
		String objet = "\t\t\tgrouper : " + grouper;
		return objet;
	}
	
	/**
	 * Create a copy of this object
	 * @return the copy
	 */
	public GroupRequest createCopy(){
		return new GroupRequest(this.getSorter());
	}
	
}
