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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class used to get informations from Googleplus API ()
 * @author MANI Adel
 */
public class HGooglePlusActivity {

	final static Logger log = LoggerFactory.getLogger(HGooglePlusActivity.class);
	private ArrayList<HGooglePlusActivityListners> listeners = new ArrayList<HGooglePlusActivityListners>();


	private String url;
	private long roundTime;
	  


	HttpClient client = new HttpClient();
	String nextToken;

	private static String END_POINT_GOOGLE_PLUS_ACTIVITY= "https://www.googleapis.com/plus/v1/activities?query=";


	/**
	 * 
	 * @param proxyHost your proxy host or null
	 * @param proxyPort your proxy port or 0
	 * @param query  your tag so we will fetch data at google API activity
	 * @param language  ie (fr, en, ..) null if else
	 * @param maxResults number between (0 -20)
	 * @param orderBy  Acceptable values are:"best" or "recent"
	 * @param roundTime refresh time on milliseconds
	 * @param APIKey your google API key
	 */
	public HGooglePlusActivity( String proxyHost, int  proxyPort, 
			String query,
			String langage,
			int maxResults,
			String orderBy,
			long roundTime,
			String APIKey
			) {
		super();

		this.roundTime = roundTime;
		url = END_POINT_GOOGLE_PLUS_ACTIVITY+query+buildUrlOptionalParams(langage, maxResults, orderBy)+"&key="+APIKey;

		if(proxyHost != null && proxyPort > 0){
			HostConfiguration config = client.getHostConfiguration();
			config.setProxy(proxyHost, proxyPort);
			log.info("proxy used : " + proxyHost + ":" + proxyPort);
		}

		log.info("url scanned : " + url + " (every "+roundTime+" ms)");

	}

	/**
	 *  
	 * @param language String (fr, en )
	 * @param maxResults long (between 1 - 20)
	 * @param orderBy 
	 * @return
	 */
	public String buildUrlOptionalParams(String language, long maxResults, String orderBy){
		String concatURL="";

		if(language != null){
			concatURL = concatURL+"&language="+language;
		}
		if(maxResults != 0){
			concatURL = concatURL+"&maxResults="+maxResults;
		}
		if(orderBy != null){
			concatURL = concatURL+"&orderBy="+orderBy;
		}
		return concatURL;
	}


	public void addListener(HGooglePlusActivityListners listener) {
		listeners.add(listener);
		log.debug("listener added: "+listener);
	}

	public void removeListener(HGooglePlusActivityListners listener) {
		listeners.remove(listener);
		log.debug("listener removed: "+listener);
	}


	private void fireEvent(GPActivity item) {    
		for (HGooglePlusActivityListners listener : listeners)
			listener.onStatusActivity(item);
	}

	/**
	 * 
	 * @param br: BufferedReader
	 * @return String 
	 * @throws IOException
	 */
	private String readBuffer(BufferedReader br) throws IOException{

		String data="";
		String line = br.readLine();
		while (line!=null) {
			if (line != null && !line.isEmpty()) {
				data = data + line;
			}
			 line = br.readLine();
		}
		return data;
	}
	

	private GPActivity getGooglePlusActivity(){
		
		String urlToAsk=null;
		if(nextToken == null) { urlToAsk = url; }else {urlToAsk = url +"&nextPageToken="+nextToken; }
				
		GetMethod method = new GetMethod(urlToAsk);	

		try {
			int statusCode = client.executeMethod(method);

			if (statusCode == 200) {

				BufferedReader br = new BufferedReader(new InputStreamReader(
						method.getResponseBodyAsStream(), "UTF-8"));
				
				GPActivity result  = new GPActivity(new JSONObject(readBuffer(br)));	
				nextToken = result.getNextPageToken();
				
				return new GPActivity(result);

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

		private HGooglePlusActivity owner;

		Loop(HGooglePlusActivity owner) {
			super("Thread used to ask  to GooglePlus Activity .");
			this.owner = owner;
		}

		public void run() {
			while (true) {
				try {
					GPActivity gps =	getGooglePlusActivity(); 

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
