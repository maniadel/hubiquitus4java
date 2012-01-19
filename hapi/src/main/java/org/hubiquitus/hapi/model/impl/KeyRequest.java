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
public class KeyRequest {

	/**
	 * Data filter field
	 */
	private String filter;
	
	/**
	 * Operator applied 
	 */
	private DbOperator operator;
	
	/**
	 * Data filter value 
	 */
	private Object value;
	
	
	/**
	 * Constructor 
	 * @param filter the data filter field
	 * @param operator the operator applied 
	 * @param value the data filter value 
	 */
	public KeyRequest(String filter, DbOperator operator, Object value) {
		super();
		this.filter = filter;
		this.operator = operator;
		this.value = value;
	}
	
	/**
	 * Constructor with default operatir egual 
	 * @param filter the data filter field
	 * @param value the data filter value 
	 */
	public KeyRequest(String filter, Object value) {
		super();
		this.filter = filter;
		this.operator = DbOperator.EQUAL;
		this.value = value;
	}


	/**
	 * Get KeyRequest in xml format 
	 * @return the xml
	 */
	public String toXML() {
		StringBuffer xml = new StringBuffer();
		xml.append("<key filter=\"");
		xml.append(filter);
		xml.append("\" operator=\"");
		xml.append(operator.toString());
		xml.append("\">");
		xml.append(value.toString());
		xml.append("</key>");
		return xml.toString();
	}

	
	/**
	 * Getter filter
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * Setter filter
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * Getter operator
	 * @return the operator
	 */
	public DbOperator getOperator() {
		return operator;
	}

	/**
	 * Setter operator
	 * @param operator the operator to set
	 */
	public void setOperator(DbOperator operator) {
		this.operator = operator;
	}

	/**
	 * Getter value
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter value
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	// methode toString
	public String toString(){
		String objet = "\t\t\tfilter : " + filter;
		objet = objet + "\n\t\t\toperator : " + operator.toString();
		objet = objet + "\n\t\t\tvalue : " + value;	
		return objet;
	}
	
	static public String getMongoOperator(DbOperator operator){
		String mongoOperator = "";
		
		switch(operator){
			case EQUAL : mongoOperator = "equal";
				break;
			case SUP : mongoOperator = "$gt";
				break;
			case INF : mongoOperator = "$lt";
				break;
			case SUP_EQUAL : mongoOperator = "$gte";
				break;
			case INF_EQUAL : mongoOperator = "$lte";
				break;
			default:
				mongoOperator = null;
				break;
		}
		
		return mongoOperator;
	}
	
	
	static public DbOperator getDBOperator(String operator){
		DbOperator dbOperator = null;
		
		if("EQUAL".equals(operator))dbOperator = DbOperator.EQUAL;
		else if("SUP".equals(operator))dbOperator = DbOperator.SUP;
		else if("INF".equals(operator))dbOperator = DbOperator.INF;
		else if("SUP_EQUAL".equals(operator))dbOperator = DbOperator.SUP_EQUAL;
		else if("INF_EQUAL".equals(operator))dbOperator = DbOperator.INF_EQUAL;
		
		return dbOperator;
	}
	
	/**
	 * Create a copy of this object
	 * @return the copy
	 */
	public KeyRequest createCopy(){
		return new KeyRequest(this.getFilter(), this.getOperator(), this.getValue());
	}
	
}
