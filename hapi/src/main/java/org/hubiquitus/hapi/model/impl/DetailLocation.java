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


public class DetailLocation {

	/**
	 * Specifies the name of the header, used as a key to identify it
	 */
	
	private String hKey;
	/**
	 * Specifies the value of the header
	 */
    private String hValue;

    /**
     * getter hKey
     * @return hKey
     */
    public String getHKey() {
        return hKey;
    }

    /**
     * setter hKey
     * @param hKey the hKey to set
     */
    public void setHKey(String hKey) {
        this.hKey = hKey;
    }

    /**
     * getter hValue
     * @return hValue
     */
    public String getHValue() {
        return hValue;
    }

    /**
     * setter hValue
     * @param hValue the hValue to set
     */
    public void setHValue(String hValue) {
        this.hValue = hValue;
    }

}
