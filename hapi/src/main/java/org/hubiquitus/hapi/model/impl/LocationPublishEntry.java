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

import java.util.Map;

import org.hubiquitus.hapi.model.ObjectMapperInstanceDomainKey;
import org.hubiquitus.hapi.model.PublishEntry;


/**
 * Type hLocation
 * @author o.chauvie
 */
public class LocationPublishEntry implements PublishEntry {

	/**
	 * Specifies the exact latitude of the location
	 */
	
	private String lat;
	/**
	 * Specifies the exact latitude of the location
	 */
    private String ing;
    
    /**
     * Specifies the number of the street
     */
    private String streetNumber;
    
    /**
     * Specifies the type of the street
     */
    private String streetType;
    
    /**
     * Specifies the name of the street
     */
    private String street;
    
    /**
     * Specifies the zipcode of the city
     */
    private String zipCode;    

    /**
     * Specifies the city
     */
    private String city; 

    /**
     * Specifies the Country
     */
    private String Country; 
    
    /**
     * Specifies the info of the location
     */
    private String Info;
    /**
     * Map of extra details
     */
    private Map<String, String> details;
    
    /**
	 * @return the details
	 */
	public Map<String, String> getDetails() {
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(Map<String, String> details) {
		this.details = details;
	}

	/**
     * getter lat
     * @return lat
     */
    public String getLat() {
        return lat;
    }

    /**
     * setter lat
     * @param lat the lat to set
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * getter ing
     * @return ing
     */
    public String getIng() {
        return ing;
    }

    /**
     * setter ing
     * @param ing the ing to set
     */
    public void setIng(String ing) {
        this.ing = ing;
    }
    
    /**
	 * @return the streetNumber
	 */
	public String getStreetNumber() {
		return streetNumber;
	}

	/**
	 * @param streetNumber the streetNumber to set
	 */
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	/**
	 * @return the streetType
	 */
	public String getStreetType() {
		return streetType;
	}

	/**
	 * @param streetType the streetType to set
	 */
	public void setStreetType(String streetType) {
		this.streetType = streetType;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return Country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		Country = country;
	}

	/**
	 * @return the info
	 */
	public String getInfo() {
		return Info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		Info = info;
	}

	@Override
	public String getEntryType() {
		return ("hLocation");
	}
    
    @Override
	public String entryToJsonFormat() {
		String result = null;
		try {
			result = ObjectMapperInstanceDomainKey.PUBLISH_ENTRY.getMapper().writeValueAsString(this);
		} catch (Exception e) {
			// Do nothing
		}
		return result;
	}

}
