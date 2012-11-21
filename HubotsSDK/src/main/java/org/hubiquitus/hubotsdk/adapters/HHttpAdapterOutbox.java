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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hubiquitus.hapi.client.HMessageDelegate;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.AdapterOutbox;
import org.hubiquitus.hubotsdk.adapters.HHttpAdapter.HHttpAttachement;
import org.hubiquitus.hubotsdk.adapters.HHttpAdapter.HHttpData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("unused")
public class HHttpAdapterOutbox extends AdapterOutbox {
	final Logger logger = LoggerFactory.getLogger(HHttpAdapterOutbox.class);

    public HHttpAdapterOutbox(){
		super();
	}
	@Override
	public void sendMessage(HMessage message, HMessageDelegate callback) {
		if ("hhttpdata".equalsIgnoreCase(message.getType())) {
			 try {
                HHttpData httpData = new HHttpData(message.getPayloadAsJSONObject());
                // Only http post is supported for now.
                // Only handle with the attachments.
                httpPost(httpData);
            } catch (JSONException e) {
				logger.error("can not read the HttpData: ", e);
			}
		} else {
			logger.warn("Only type hHttpData is supported to be sent by http adapter outbox.");
		}
	}
	
	private void httpPost(HHttpData httpData){
		String uriString = "http://";

		uriString = uriString + httpData.getServerName() + ":"
				+ httpData.getServerPort();
		if (httpData.getQueryPath() != null
				&& httpData.getQueryPath().length() > 0) {
			uriString += httpData.getQueryPath();
		}
		if (httpData.getQueryArgs() != null
				&& httpData.getQueryArgs().length() > 0) {
			uriString += httpData.getQueryArgs();
		}
		
		try {
				HttpClient client = new DefaultHttpClient();
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
				    logger.debug("Http response status : " + response.getStatusLine());
				}
			
		} catch (Exception e) {
			logger.error("can not send an HttpData : ", e);
		}
	}
	
	
	@Override
	public void setProperties(JSONObject properties) {
		// NOP
	}

	@Override
	public void start() {
        // NOP
	}

	@Override
	public void stop() {
        // NOP
	}

}
