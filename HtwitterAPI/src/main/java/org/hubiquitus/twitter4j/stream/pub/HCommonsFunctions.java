package org.hubiquitus.twitter4j.stream.pub;

import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;

public class HCommonsFunctions {

	private static Logger log = Logger.getLogger(HCommonsFunctions.class);
	
	
	

	/****
	 * Besides the existing Http codes for twitter api we added : <br> 
	 * 100 : TCP/IP level network errors, need to wait 250ms incrément is same http code recived.<br>
	 * 101 : HTTP ERRORS
	 *  
	 * @param response HttpResponse 
	 * @return int Error code
	 */
	public int getHTTPResponse (HttpResponse response){	
		int code = -1;

		if(response.toString().contains("HTTP/1.1 200")){
			log.info("HTTP SUCCESS !!!");
			code = 200;

		}else if (response.toString().contains("TCP/IP level network errors")){
			log.error("TCP/IP level network errors");
			code = 100;

		}else if(response.toString().contains("HTTP errors") ){
			log.error("HTTP errors ");
			code = 101;

		}else if(response.toString().contains("HTTP 420 errors")){				
			log.error("HTTP 420 errors ");
			code = 420;

		}else if(response.toString().contains("HTTP 401")){
			log.error("HTTP authentication failed");
			code = 401;

		}else if(response.toString().contains("HTTP 403")){
			log.error("The connecting account is not permitted to access this endpoint. ");
			code = 401;

		}else if(response.toString().contains("HTTP 404")){
			log.error("There is nothing at this URL, which means the resource does not exist.");
			code = 404;

		}else if(response.toString().contains("HTTP 406")){
			log.error("Not Acceptable | At least one request parameter is invalid ");
			code = 406;

		}else if(response.toString().contains("HTTP 413")){
			log.error("A parameter list is too long");
			code = 413; 

		}else if(response.toString().contains("HTTP 416")){
			log.error("Range Unacceptable");
			code = 416;

		}else if(response.toString().contains("HTTP 503")){
			log.error("A streaming server is temporarily overloaded.");
			code = 503;
		}

		return code;
	}
	//------	
	
	
	
	
	
}
