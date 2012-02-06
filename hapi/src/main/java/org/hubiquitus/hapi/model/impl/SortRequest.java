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
public class SortRequest {

	/**
	 * Data filter field
	 */
	private String sorter;
	
	
	/**
	 * Data filter value 
	 */
	private String value;
	
	
	/**
	 * Constructor with default operatir egual 
	 * @param sorter the data filter field
	 * @param value the data filter value 
	 */
	public SortRequest(String sorter, String value) {
		super();
		this.sorter = sorter;
		this.value = value;
	}

	/**
	 * Constructor with default operatir egual 
	 * @param sorter the data filter field
	 * @param value the data filter value 
	 */
	public SortRequest(String sorter) {
		super();
		this.sorter = sorter;
		this.value = "ASC";
	}

	public SortRequest() {
	}

	/**
	 * Get KeyRequest in xml format 
	 * @return the xml
	 */
	public String toXML() {
		StringBuffer xml = new StringBuffer();
		xml.append("<key sorter=\"");
		xml.append(sorter);
		xml.append("\">");
		xml.append(value.toString());
		xml.append("</key>");
		return xml.toString();
	}

	
	/**
	 * Getter Sorter
	 * @return the sorter
	 */
	public String getSorter() {
		return sorter;
	}

	/**
	 * Setter sorter
	 * @param sorter the sorter to set
	 */
	public void setSorter(String sorter) {
		this.sorter = sorter;
	}


	/**
	 * Getter value
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Setter value
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	// methode toString
	public String toString(){
		String objet = "\t\t\tsorter : " + sorter;
		objet = objet + "\n\t\t\tvalue : " + value;	
		return objet;
	}
	
	static public Integer getMongoSort(DbSort operator){
		Integer mongoOperator = 0;
		
		switch(operator){
			case ASC : mongoOperator = 1;
				break;
			case DESC : mongoOperator = -1;
				break;
			default:
				mongoOperator = null;
				break;
		}
		
		return mongoOperator;
	}
	
	
	static public DbSort getDBSort(String sort){
		DbSort dbSort = null;
		
		if(sort.equals("ASC"))dbSort = DbSort.ASC;
		else if(sort.equals("DESC"))dbSort = DbSort.DESC;
		
		return dbSort;
	}
	
	/**
	 * Create a copy of this object
	 * @return the copy
	 */
	public SortRequest createCopy(){
		return new SortRequest(this.getSorter(), this.getValue());
	}
	
}
