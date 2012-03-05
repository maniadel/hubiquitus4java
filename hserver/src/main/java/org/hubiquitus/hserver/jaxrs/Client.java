/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.hubiquitus.hserver.jaxrs;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.resource.URIResolver;

public final class Client {

    private Client() {
    }

    public static void main(String args[]) throws Exception {
        // Sent HTTP GET request to query all customer info
        /*
         * URL url = new URL("http://localhost:9000/customers");
         * System.out.println("Invoking server through HTTP GET to query all
         * customer info"); InputStream in = url.openStream(); StreamSource
         * source = new StreamSource(in); printSource(source);
         */

/*
        // Sent HTTP GET request to query customer info
        System.out.println("Sent HTTP GET request to query customer info");
        URL url = new URL("http://localhost:9000/customerservice/customers/123");
        InputStream in = url.openStream();
        System.out.println(getStringFromInputStream(in));

        // Sent HTTP GET request to query sub resource product info
        System.out.println("\n");
        System.out.println("Sent HTTP GET request to query sub resource product info");
        url = new URL("http://localhost:9000/customerservice/orders/223/products/323");
        in = url.openStream();
        System.out.println(getStringFromInputStream(in));

        // Sent HTTP PUT request to update customer info
        System.out.println("\n");
        System.out.println("Sent HTTP PUT request to update customer info");
        Client client = new Client();
        String inputFile = client.getClass().getResource("update_customer.xml").getFile();
        URIResolver resolver = new URIResolver(inputFile);
        File input = new File(resolver.getURI());
        PutMethod put = new PutMethod("http://localhost:9000/customerservice/customers");
        RequestEntity entity = new FileRequestEntity(input, "text/xml; charset=ISO-8859-1");
        put.setRequestEntity(entity);
        HttpClient httpclient = new HttpClient();

        try {
            int result = httpclient.executeMethod(put);
            System.out.println("Response status code: " + result);
            System.out.println("Response body: ");
            System.out.println(put.getResponseBodyAsString());
        } finally {
            // Release current connection to the connection pool once you are
            // done
            put.releaseConnection();
        }
*/
		
    	/*
    	{ "dbName" : "intent", 
    		"collectionName" : "itEvent", 
    		paramsRequest
    		"requestKeys" : "<params>
    							<param>
    								<key filter="headers.provider" operator="EQUAL">Otis</key>
    								<key filter="headers.deviceType" operator="EQUAL">LIFT_SENSOR</key>
    								<key filter="headers.eventType" operator="EQUAL">LIFT_OUT_OF_ORDER</key>
    								<key filter="headers.eventDate" operator="SUP">20120227</key>
    								<key filter="headers.eventDate" operator="INF_EQUAL">20120226</key>
    								<key limit="false">0</key>
    								<count>false</count>
    							</param>
    						</params>"}
    						
    						
    						
    	{"dbName":"intent",
    	"paramsRequest":[{"params":[{"operator":"EQUAL","value":"Otis","filter":"headers.provider"},
    				{"operator":"EQUAL","value":"LIFT_SENSOR","filter":"headers.deviceType"},
    				{"operator":"EQUAL","value":"LIFT_OUT_OF_ORDER","filter":"headers.eventType"},
    				{"operator":"SUP","value":"20120227","filter":"headers.eventDate"},
    				{"operator":"INF_EQUAL","value":"20120226","filter":"headers.eventDate"}]}],
    		
    		"requestType":"find",
    		"collectionName":"itEvent"
    		}					
    						
*/

        // Sent HTTP POST request : generateMessageItIndicatorLiftCategory
        
    	StringBuffer buf = new StringBuffer();
        buf.append("{\"dbName\" : \"intent\",");
        buf.append("\"collectionName\" : \"itEvent\",");
        buf.append("\"requestType\" : \"find\",");
        buf.append("\"paramsRequest\":[{\"params\":[");
        buf.append("{\"operator\":\"EQUAL\",\"value\":\"Otis\",\"filter\":\"headers.provider\"},");
        buf.append("{\"operator\":\"EQUAL\",\"value\":\"LIFT_SENSOR\",\"filter\":\"headers.deviceType\"},");
        buf.append("{\"operator\":\"EQUAL\",\"value\":\"LIFT_OUT_OF_ORDER\",\"filter\":\"headers.eventType\"},");
        buf.append("{\"operator\":\"SUP\",\"value\":\"20120227\",\"filter\":\"headers.eventDate\"},");
        buf.append("{\"operator\":\"INF_EQUAL\",\"value\":\"20120226\",\"filter\":\"headers.eventDate\"}");
        buf.append("]}]}");
        
        
        
        String hRequest = buf.toString();
    	
        PostMethod post = new PostMethod("http://localhost:9000/requestservice/hrequest");
        post.addRequestHeader("Accept" , "application/json");
        RequestEntity entity = new StringRequestEntity(hRequest, "application/json", "utf-8");
        post.setRequestEntity(entity);
        HttpClient httpclient = new HttpClient();
        try {
            int result = httpclient.executeMethod(post);
            System.out.println("Response status code: " + result);
            System.out.println("Response body: ");
            System.out.println(post.getResponseBodyAsString());
        } finally {
            // Release current connection to the connection pool once you are
            // done
            post.releaseConnection();
        }

        System.out.println("\n");
        System.exit(0);
    }



}
