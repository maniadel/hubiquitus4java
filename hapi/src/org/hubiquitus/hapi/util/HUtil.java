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

package org.hubiquitus.hapi.util;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.hubiquitus.hapi.client.HOption;

/**
 * 
 * @author j.desousag
 * @version 0.3
 * Contain some util function
 */

public class HUtil {

	/**
	 * Pick the index of a random element in a list
	 * @param list
	 * @return one number
	 */
	
	public static <T> int pickIndex(List<T> list) {
		int index = 0;
		
		int size = list.size();
		index = (int) (Math.random() * size);
		return index;
	}
	
	
	/**
	 * Method to obtain the Host with the String
	 * @param String
	 * @return S
	 */
	public static String getHost(String endPoint) {
		String host = null;
		
		try {
			if(endPoint.contains("://")) {
				URI uri = new URI(endPoint);
				endPoint = uri.getAuthority();
			}			
			host = endPoint.split(":")[0];
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return host;
	}
	
	/**
	 * Method to obtain the Port with an HOption
	 * @param option
	 * @return
	 */
	public static String getPort(HOption option) {
		String port = null;
		int randIndex = pickIndex(option.getEndpoints());
		
		port = getPort(option.getEndpoints().get(randIndex));
		
		return port;
	}
	
	/**
	 * Method to obtain the Port with the String
	 * @param option
	 * @return
	 */
	public static String getPort(String endpoint) {
		String port = null;
		
		try {
			if(!endpoint.contains("://"))
				endpoint = "temp://" + endpoint;
			URI uri = new URI(endpoint);
			int value = uri.getPort();
			if(value == -1)
				value = 5222;
			port = String.valueOf(value);	
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		if(port == null)
				port = String.valueOf(5222);
			
		return port;
	}
}
