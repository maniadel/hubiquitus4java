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

package org.hubiquitus.hubotsdk.adapters;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hubiquitus.hapi.client.HMessageDelegate;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.AdapterOutbox;
import org.hubiquitus.hubotsdk.adapters.HHttpAdapter.HHttpAttachement;
import org.hubiquitus.hubotsdk.adapters.HHttpAdapter.HHttpData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HHttpAdapterOutbox extends AdapterOutbox {
	final Logger logger = LoggerFactory.getLogger(HHttpAdapterOutbox.class);
	
	private String host = "0.0.0.0";
	private int port = 80;
	private String path = "";
	
	public HHttpAdapterOutbox(){
		super();
	}
	@Override
	public void sendMessage(HMessage message, HMessageDelegate callback) {
		logger.info("-------------------> message send to http.");
		if("hhttpdata".equalsIgnoreCase(message.getType())){
			try {
				HHttpData httpData = new HHttpData(message.getPayloadAsJSONObject());
				String uriString = "http://" + httpData.getServerName() + ":" + httpData.getServerPort();
				if(httpData.getQueryPath()!= null&&httpData.getQueryPath().length()>0){
					uriString += httpData.getQueryPath();
				}
				if(httpData.getQueryArgs()!=null&&httpData.getQueryArgs().length()>0){
					uriString += httpData.getQueryArgs();
				}
				HttpClient client = new DefaultHttpClient();
				if("get".equalsIgnoreCase(httpData.getMethod())){
					// method : get
					HttpGet get = new HttpGet(uriString);
					HttpResponse response = client.execute(get);
					//TODO if need to handle the response
					logger.info("http response status : " + response.getStatusLine().getStatusCode());
					
				}else if("post".equalsIgnoreCase(httpData.getMethod())){
					// method : post
					HttpPost post = new HttpPost(uriString);
					if(httpData.getAttachments()!=null){
						// add post attachments
					    MultipartEntity reqEntity = new MultipartEntity();
					    JSONArray nameArray = httpData.getAttachments().names();
					    for(int i = 0 ; i < nameArray.length() ; i++){
					    	HHttpAttachement hattachment = new HHttpAttachement(httpData.getAttachments().getJSONObject(nameArray.getString(i)));
					    	reqEntity.addPart(nameArray.getString(i), new ByteArrayBody(hattachment.getData(), hattachment.getContentType(), hattachment.getName()));
					    }
					    post.setEntity(reqEntity);	
					    HttpResponse response = client.execute(post);
					    //TODO if need to handle the response
					}
					
					
				}else if("put".equalsIgnoreCase(httpData.getMethod())){
					// method : put
					HttpPut put = new HttpPut(uriString);
					HttpResponse response = client.execute(put);
					//TODO if need to handle the response
					
				}else if("delete".equalsIgnoreCase(httpData.getMethod())){
					// method : delete
					HttpDelete delete = new HttpDelete(uriString);
					HttpResponse response = client.execute(delete);
					//TODO if need to handle the response
					
				}else{
					logger.error("Method not supported!");
				}
			} catch (Exception e) {
				logger.error("message: ", e);
			}
		}else{
			logger.warn("Only type hHttpData is supported to be sent by http adapter outbox.");
		}
		logger.info("<------------------- message send to http.");
	}

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
			} catch (Exception e) {
				logger.info("message: ", e);
			}
		}

	}

	@Override
	public void start() {
		logger.info("---> hHttpAdapterOutbox start!!");
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		logger.info("---> hHttpAdapterOutbox stop!!");
		// TODO Auto-generated method stub

	}

}
