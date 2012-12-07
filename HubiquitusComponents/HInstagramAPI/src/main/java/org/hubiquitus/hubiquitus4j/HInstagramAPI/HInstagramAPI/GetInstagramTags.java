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

package org.hubiquitus.hubiquitus4j.HInstagramAPI.HInstagramAPI;


import java.util.ArrayList;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to fetch a data from instagram API.
 * 
 * @author A.MANI
 */
public class GetInstagramTags {

	final static Logger log = LoggerFactory.getLogger(GetInstagramTags.class);

	private ArrayList<InstagramTagsListners> listeners = new ArrayList<InstagramTagsListners>();

	private String url;
	private long roundTime;

	HttpClient client = new HttpClient();

	private static String END_POINT_TAGS_INSTAGRAM = "https://api.instagram.com/v1/tags/";

	/**
	 * Constructor
	 * @param proxyHost your proxy host or null
	 * @param proxyPort your proxy port or null
	 * @param tag,  e.g. "cocacola" so we will fetch data on https://api.instagram.com/v1/tags/
	 * @param roundTime the refresh rate in ms
	 */
	public GetInstagramTags( String proxyHost,
			int  proxyPort,
			String tags,
			String options,
			String clientID,			
			long roundTime) {
		super();
		
		this.roundTime = roundTime;
		url = END_POINT_TAGS_INSTAGRAM + buildParamsURL(tags,clientID, options);

		if(proxyHost != null && proxyPort > 0){
			HostConfiguration config = client.getHostConfiguration();
			config.setProxy(proxyHost, proxyPort);
			log.info("proxy used : " + proxyHost + ":" + proxyPort);
		}

		log.info("url scanned : " + url + " (every "+roundTime+" ms)");

	}
	
	public String buildParamsURL (String tags, String clientId, String options){
		
		if(options!=null){
			if (options.equalsIgnoreCase("full")){
				return tags+"/media/recent?client_id="+clientId;
			}else if (options.equalsIgnoreCase("light")){
				return  tags+"?client_id="+clientId;
			}
		}
		
		return null;
	} 
	
	public void addListener(InstagramTagsListners listener) {
		listeners.add(listener);
		log.debug("listener added: "+listener);
	}

	public void removeListener(InstagramTagsListners listener) {
		listeners.remove(listener);
		log.debug("listener removed: "+listener);
	}


	private void fireEvent(InstagStatus item) {  
		for (InstagramTagsListners listener : listeners)
			listener.onStatus(item);
	}

	/**
	 * This method ask instagram API the tags with option (light, full) 
	 * 
	 * @return InstagramStatus
	 */
	private InstagStatus getTagsInstagram(){
		GetMethod method = new GetMethod(url);	
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode == 200) {

				return new InstagStatus(new JSONObject(method.getResponseBodyAsString()));
			} else {
				log.debug("Error trying to read request to : " + url +  " status : " + statusCode);
			}
		} catch (Exception e) {
			log.debug("Error while trying to launch request " + client + " : " + e.toString());
		} finally {
			method.releaseConnection();
		}
		return null;
	}



	/**
	 * Internal thread to fetch the data periodically
	 */
	private class Loop extends Thread {

		private GetInstagramTags owner;

		Loop(GetInstagramTags owner) {
			super("Thread used to ask data from Instagram API .");
			this.owner = owner;
		}

		public void run() {
			while (true) {
				try {
					InstagStatus instagStatus =	getTagsInstagram();

					if ((instagStatus == null) || (!instagStatus.isValid())){
						log.error("Error, The instagram tag is not valid !  the page is not found or doesn't contain any tag");
						loop.interrupt();
						log.info("Round request is stopped with success. ");
						break;
					} else if(instagStatus!=null)
						owner.fireEvent(instagStatus);

					Thread.sleep(roundTime);					

				} catch (Exception e) {
					if (!isInterrupted())
						log.error("can not stop a Thread correctelly  :(", e);
					break;
				}
			}
		}
	}

	private Loop loop = new Loop(this);

	public void start(){
		loop.start();
	}

	public void stop(){
		if (!loop.isAlive() || !loop.isInterrupted()) {
			loop.interrupt(); 
			log.debug(" Round request is stopped ");
		}
	}

}





