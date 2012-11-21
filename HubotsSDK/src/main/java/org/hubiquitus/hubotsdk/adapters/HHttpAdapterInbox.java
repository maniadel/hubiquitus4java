/*
 * Copyright (c) Novedia Group 2012.
 *
 *    This file is part of Hubiquitus
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy
 *    of this software and associated documentation files (the "Software"), to deal
 *    in the Software without restriction, including without limitation the rights
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *    of the Software, and to permit persons to whom the Software is furnished to do so,
 *    subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies
 *    or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *    INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *    PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 *    FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 *    ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *    You should have received a copy of the MIT License along with Hubiquitus.
 *    If not, see <http://opensource.org/licenses/mit-license.php>.
 */


package org.hubiquitus.hubotsdk.adapters;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import javax.activation.DataHandler;
import javax.servlet.http.HttpServletRequest;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.AdapterInbox;
import org.hubiquitus.hubotsdk.adapters.HHttpAdapter.HHttpAdapterRouteBuilder;
import org.hubiquitus.hubotsdk.adapters.HHttpAdapter.HHttpAttachement;
import org.hubiquitus.hubotsdk.adapters.HHttpAdapter.HHttpData;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("unused")
public class HHttpAdapterInbox extends AdapterInbox implements Processor{

	final Logger logger = LoggerFactory.getLogger(HHttpAdapterInbox.class);
	
	private String host = "0.0.0.0";
	private int port = 80;
	private String path = "";
	
	@Override
	public void setProperties(JSONObject properties) {
		if(properties != null){
			try {
				if (properties.has("host")){
					this.host = properties.getString("host");
				}
				if (properties.has("port")){
					this.port = properties.getInt("port");
				}
				if (properties.has("path")){
					this.path = properties.getString("path");
					if (this.path.contains("?")) {
						int interrogationIndex = this.path.indexOf("?");
						this.path = this.path.substring(interrogationIndex, this.path.length());
					}
				}
			} catch (JSONException e) {
				logger.warn("message: ", e);
			}
		}
	}

	@Override
	public void start() {
		String jettyOptions = "matchOnUriPrefix=true";
		String jettyCamelUri = "jetty:http://" + this.host + ":" + this.port + this.path;
		jettyCamelUri += "?" + jettyOptions;
		
		logger.info("Starting HttpAdapter with jettyCamelUri : " + jettyCamelUri);
		
		//add route 
		HHttpAdapterRouteBuilder routes = new HHttpAdapterRouteBuilder(jettyCamelUri, this);
		try {
			camelContext.addRoutes(routes);
		} catch (Exception e) {
			logger.warn("message: ", e);
		}
	}

	@Override
	public void stop() {
				
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		HttpServletRequest request = in.getBody(HttpServletRequest.class);
		
		//Gather data to send through an hmessage
		byte[] rawBody = in.getBody(byte[].class);
		Map<String, Object> headers = in.getHeaders();
		Map<String, DataHandler> attachments = in.getAttachments();
		
		String method = request.getMethod().toLowerCase();
		String queryArgs = request.getQueryString();
		String queryPath = request.getRequestURI();
		String serverName = request.getServerName();
		Integer serverPort = request.getServerPort();
		
		
		//create message to send
		HMessage message = new HMessage();
		message.setAuthor(this.actor);

        // headers management
        JSONObject jsonHeaders = new JSONObject();
        if (headers != null) {
			for (String key : headers.keySet()) {
				Object header = headers.get(key);
				String value = null;
				if (header != null) {
					value = header.toString();
				}
				jsonHeaders.put(key, value);
			}
		}
        message.setHeaders(jsonHeaders);
		
		message.setPublished(new DateTime());
		
		message.setType("hHttpData");
		//create payload
		HHttpData httpData = new HHttpData();
		httpData.setMethod(method);
		httpData.setQueryArgs(queryArgs);
		httpData.setQueryPath(queryPath);
		httpData.setServerName(serverName);
		httpData.setServerPort(serverPort);
		httpData.setRawBody(rawBody);
		
		//create attachements
		JSONObject hattachements = new JSONObject();
		for (String key : attachments.keySet()) {
			DataHandler attachement = attachments.get(key);
			if(attachement != null) {
				HHttpAttachement hattachement = new HHttpAttachement();
				hattachement.setContentType(attachement.getContentType());
				hattachement.setName(attachement.getName());
				
				//read attachement
                byte[] buffer = new byte[8 * 1024];
                InputStream input = attachement.getInputStream();
                ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    byteOutputStream.write(buffer, 0, bytesRead);
                }
                hattachement.setData(byteOutputStream.toByteArray());
				try {
					hattachements.put(key, hattachement);
				} catch (JSONException e) {
					logger.debug(e.toString());
				}
			} else {
				//if attachment is null, do not put the key in hattachments.
			}
		}
		
		httpData.setAttachments(hattachements);
		message.setPayload(httpData);

		//finally send message to actor
		this.put(message);
	}

}
