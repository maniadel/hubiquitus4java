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
 */

public class HMeasure extends HStructure {
	
	public HMeasure() {
		super();
	}
	
	public String getHType() {
		return "hmeasure";
	}
	
	/**
	 * Specifies the unit in which the measure is expressed, should be in lowercase. 
	 * @return unit. NULL if undefined
	 */
	public String getUnit() {
		return (String) this.get("unit", String.class);
	}

	public void setUnit(String unit) {
		this.put("unit", unit);
	}

	/**
	 * Specify the value of the measure (ie : 31.2)
	 * @return value. NULL if undefined
	 */
	public String getValue() {
		return (String) this.get("value", String.class);
	}

	public void setValue(String value) {
		this.put("value", value);
	}	
}
