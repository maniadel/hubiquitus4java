/**
 Copyright (c) Novedia Group 2012.
 This file is part of Hubiquitus
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 of the Software, and to permit persons to whom the Software is furnished to do so,
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies
 or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 You should have received a copy of the MIT License along with Hubiquitus.
 If not, see <http://opensource.org/licenses/mit-license.php>. 
 
*/



package org.hubiquitus.hubotsdk.adapters.HTwitter_1_1;

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
			code = 403;
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
			log.error("Service Unavailable | A streaming server is temporarily overloaded.");
			code = 503;
		}
		return code;
	}
}
