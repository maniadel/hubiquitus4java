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
		int numb = 0;
		
		int size = list.size();
		numb = (int) (Math.random() * size);
		return numb;
	}
	
	/**
	 * Method to obtain the Domain with an HOption
	 * @param HOption
	 * @return String (the domain)
	 */
	public static String getDomain(HOption option){
		String domain = getDomain(option.getPublisher());
		
		return domain;
	}
	
	/**
	 * Method to obtain the Domain with a String
	 * @param String
	 * @return String (the Domain)
	 */
	public static String getDomain(String login) {
		String ressource = null;
				
		if (login.contains("/"))
			ressource = login.split("/")[1];
				
		return ressource;
	}
	
	/**
	 * Method to obtain the Ressource with an HOption
	 * @param HOption
	 * @return String (the Ressource)
	 */
	public static String getRessource(HOption option) {
		String ressource = null;
		getRessource(option.getPublisher());
				
		return ressource;
	}
	
	
	/**
	 * Method to obtain the Ressource with a String
	 * @param String
	 * @returnString (the Ressource)
	 */
	public static String getRessource(String login) {
		String ressource = null;
				
		if (login.contains("@"))
			ressource = login.split("@")[1];
				
		return ressource;
	}
	
	/**
	 * Method to obtain the Host with an HOption
	 * @param HOption
	 * @return String (the Ressource)
	 */
	public static String getHost(HOption option) {
		String host = null;
		
		int randIndex = pickIndex(option.getEndpoints());
		host = getHost(option.getEndpoints().get(randIndex));
		
		return host;
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
			port = String.valueOf(uri.getPort());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		if(port == null)
			port = String.valueOf(5222);
			
		return port;
	}
}
