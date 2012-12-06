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

package org.hubiquitus.hubiquitus4j.hgoogleplus;

import java.util.ArrayList;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class used to get informations from Googleplus API 
 * @author MANI Adel
 */
public class HGooglePlus {

	final static Logger log = LoggerFactory.getLogger(HGooglePlus.class);
	private ArrayList<HGooglePlusListners> listeners = new ArrayList<HGooglePlusListners>();

	
	private String url;
	private long roundTime;
	

	HttpClient client = new HttpClient();

	private static String END_POINT_GOOGLE_PLUS_PAGE= "https://www.googleapis.com/plus/v1/people/";
	
	/**
	 * Constructor
	 * @param proxyHost your proxy host or null
	 * @param proxyPort your proxy port or null
	 * @param pageName the name of the page e.g. " " +cocacola" so we will fetch data on https://www.googleapis.com/plus/v1/people/ 
	 * @param roundTime the refresh rate in ms
	 */
	public HGooglePlus( String proxyHost,
			int  proxyPort,
			String pageName,
			long roundTime,
			String APIKey
			) {
		super();

		this.roundTime = roundTime;
		url = END_POINT_GOOGLE_PLUS_PAGE + pageName +"?key="+APIKey;
		
		if(proxyHost != null && proxyPort > 0){
			HostConfiguration config = client.getHostConfiguration();
			config.setProxy(proxyHost, proxyPort);
			log.info("proxy used : " + proxyHost + ":" + proxyPort);
		}
		
		log.info("url scanned : " + url + " (every "+roundTime+" ms)");

	}

	public void addListener(HGooglePlusListners listener) {
		listeners.add(listener);
		log.debug("listener added: "+listener);
	}

	public void removeListener(HGooglePlusListners listener) {
		listeners.remove(listener);
		log.debug("listener removed: "+listener);
	}


	private void fireEvent(GPStatus item) {  
		for (HGooglePlusListners listener : listeners)
			listener.onStatus(item);
	}


	private GPStatus getGooglePlusPage(){
		GetMethod method = new GetMethod(url);	
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode == 200) {
				return new GPStatus(new JSONObject(method.getResponseBodyAsString()));
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

		private HGooglePlus owner;
		
		Loop(HGooglePlus owner) {
			super("Thread used to ask a +1 to GooglePlus API .");
			this.owner = owner;
		}
		
		public void run() {
			while (true) {
				try {
					GPStatus gps =	getGooglePlusPage();

					if ((gps == null) || (!gps.isValid())){
						log.error("Error, The googlePlus page is not valid ! the page is not found or doesn't contain any plusOneCount and circledByCount");
						loop.interrupt();
						log.info("Round request is stopped with success. ");
						break;
					} else if(gps!=null)
						owner.fireEvent(gps);

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
