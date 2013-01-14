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


package org.hubiquitus.twitter4j_1_1.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class HStream {

	private String proxyHost;
	private int proxyPort;

	private String tags;
	private String consumerKey;
	private String consumerSecret;	    
	private String token;
	private String tokenSecret;

	private String delimited;
	private String stallWarnings; 
	private String with;
	private String replies;
	private String locations;
	private String count;


	private int  timeToWaitTcpLevel   = 1;
	private int  timeToWaitHttpError  = 2500;
	private int  timeToWaitHttp420    = 30000;


	protected HCommonsFunctions hcommonsFunctions = new HCommonsFunctions();


	private class GZipStream extends GZIPInputStream {
		private final InputStream wrapped;
		public GZipStream(InputStream is) throws IOException {
			super(is);
			wrapped = is;
		}

		/**
		 * Overrides behavior of GZIPInputStream which assumes we have all the data available
		 * which is not true for streaming. We instead rely on the underlying stream to tell us
		 * how much data is available.
		 * 
		 * Programs should not count on this method to return the actual number
		 * of bytes that could be read without blocking.
		 *
		 * @return - whatever the wrapped InputStream returns
		 * @exception  IOException  if an I/O error occurs.
		 */
		public int available() throws IOException {
			return wrapped.available();
		}
	}

	private class Loop extends Thread {

		private Logger log = Logger.getLogger(HStream.class);
		private HStream owner;

		Loop(HStream owner) {
			super("Thread used to read the Twitter Streaming API v1.1");
			this.owner = owner;
		}

		BufferedReader buffer;

		public void run() {
			boolean run = true;
			while (run) {
				try {
					String line = buffer.readLine();
					try {
						if ((line != null) && (line.startsWith("{"))) { 
							JSONObject object = new JSONObject(line);							
							owner.fireEvent(object);
							if(object.has("disconnect")){
								run = false;
								log.debug(" recived an disconnect message and stoped Thread");
							}

						}
					} catch (JSONException e) {						
						log.error("There is some trouble with the JSON Parsing of a line from twitter :(", e);
					}
				} catch (IOException e) {
					if (!isInterrupted())
						log.error("can not read a line :(", e);
					else {
						try {
							buffer.close();
						} catch (IOException e1) {
							log.error("ooops can not close properly the buffer...", e1);
						}
						break;
					}
				}
			}
		}
	}

	private Loop loop = new Loop(this);

	private static final String STREAMING_API_1_1_ENDPOINT = "https://stream.twitter.com/1.1/statuses/filter.json";

	private static Logger log = Logger.getLogger(HStream.class);

	private ArrayList<HStreamListner> listeners = new ArrayList<HStreamListner>();

	public HStream(String proxyHost, 
			int proxyPort, 
			String tags, 
			String delimited,  
			String stallWarnings, 
			String with, 
			String replies, 
			String locations, 
			String count, 
			String consumerKey, 
			String consumerSecret, 
			String token, 
			String tokenSecret) {
		super();
		this.proxyHost =proxyHost;
		this.proxyPort = proxyPort;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.token = token;
		this.tokenSecret = tokenSecret;

		this.tags = tags;
		this.delimited =delimited;
		this.stallWarnings = stallWarnings; 
		this.with = with;
		this.replies = replies;
		this.locations = locations;
		this.count = count;


	}

	/**
	 * Used to add a listener
	 * @param listener which must implement the HStreamlistener to get the status
	 */
	public void addListener(HStreamListner listener) {
		listeners.add(listener);
		log.debug("listener added: "+listener);
	}

	public void removeListener(HStreamListner listener) {
		listeners.remove(listener);
		log.debug("listener removed: "+listener);
	}


	public void fireEvent(JSONObject item) {  	    
		int code = 	item.has("text")?1: item.has("warning")?2 : item.has("delete")?3 : item.has("scrub_geo")?4 : 
			item.has("limit")?5 : item.has("status_withheld")?6 : item.has("user_withheld")?7 : item.has("disconnect")?8 : 0;

		for (HStreamListner listener : listeners) {			
			switch (code) {
			case 1 :listener.onStatus(item); break;
			case 2 :listener.onStallWarning(item); break;
			case 3 :listener.onLocationDeletionNotices(item); break;
			case 4 :listener.onLimitNotices(item); break;
			case 5 :listener.onStatusDeletionNotices(item); break;
			case 6 :listener.onStatusWithheld(item); break;
			case 7 :listener.onUserWithheld(item); break;
			case 8 :listener.onDisconnectMessages(item); break;
			default : listener.onOtherMessage(item); 
			}
		}

		if (code ==  8) {
			try {
				displayLogMessage(item.getJSONObject("disconnect").getInt("code"));
			} catch (JSONException e) {
				log.error("can not fetch the error code for disconnection", e);
			}
			stop();					
			loop = new Loop(this);
			loop.owner.start();
		}

	}

	private void displayLogMessage(int raison) {
		log.info("RECONNECTING ...");		
		switch(raison){
		case 1:
			log.error("Shudown | The feed was shutdown (possibly a machine restart)");	
			break;
		case 2:
			log.error("Duplicate stream | The same endpoint was connected too many times.");		
			break;
		case 3:
			log.info("Control request | Control streams was used to close a stream (applies to sitestreams).");
			break;
		case 4:
			log.info("Stall | The client was reading too slowly and was disconnected by the server. ");
			break;
		case 5:
			log.info("Normal | The client appeared to have initiated a disconnect.");	
			break;
		case 6:
			log.info("Token revoqued |An Oauth token was revoked for a user.");	
			break;
		case 7:
			log.info("Admin logout | The same credentials were used to connect a new stream and the oldest was disconnected.");		
			break;
		case 8:
			log.info("... | Reserved for internal use. Will not be delivered to external clients.");	
			break;
		case 9:
			log.info("Max message limit | The stream connected with a negative count parameter and was disconnected after all backfill was delivered.");	
			break;
		case 10:
			log.info("Stream expeption| An internal issue disconnected the stream.");	
			break;
		case 11:
			log.debug("Broker stall | An internal issue disconnected the stream.");	
			break;
		case 12:
			log.debug("Shed load | The host the stream was connected to became overloaded and streams were disconnected to balance load. Reconnect as usual.");							
			break;
		}
	}

	/**
	 * This method is used to build a params post
	 * @return ArrayList of Basic value pairs 
	 */
	private ArrayList<BasicNameValuePair> setPostparms(){
		ArrayList<BasicNameValuePair> nValuePairs = new ArrayList <BasicNameValuePair>();		

		if( stallWarnings!=null){
			nValuePairs.add(new BasicNameValuePair("stallWarnings", stallWarnings));	
		}
		if( tags!=null){
			nValuePairs.add(new BasicNameValuePair("track", tags));	
		}
		if( delimited != null){
			nValuePairs.add(new BasicNameValuePair("delimited", delimited));
		}
		if( with != null){
			nValuePairs.add(new BasicNameValuePair("with", with));
		}
		if( replies!=null){
			nValuePairs.add(new BasicNameValuePair("replies", replies));
		}
		if( locations!=null){
			nValuePairs.add(new BasicNameValuePair("locations", locations));
		}
		if( count!=null){
			nValuePairs.add(new BasicNameValuePair("count", count));
		}
		return nValuePairs;
	}

	/**
	 * Used to set proxy params to the DefaultHttpClient
	 * @param client
	 */
	private void setProxyParams (DefaultHttpClient client){		
		HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http");
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		log.debug("using a proxy : " + proxyHost+":"+proxyPort);		
	}

	/**
	 * Is used to determine, if it is necessary to try the reconnection according the http code recived.
	 */
	private void checkreconnection(int code){		
		switch(code){

		case 200 :
			// reinitalize all variable Time
			timeToWaitTcpLevel   = 1;
			timeToWaitHttpError  = 2500;
			timeToWaitHttp420    = 30000;				
			break;
		case 100:
			// TCP/IP level network errors Increase the delay in reconnects by 250ms each attempt, up to 16 seconds.
			if(timeToWaitTcpLevel<16000){
				timeToWaitTcpLevel = timeToWaitTcpLevel +250;	
			}			
			try {
				log.debug("RECONNECTING ...");			
				Thread.sleep(timeToWaitTcpLevel);
				start();					
			} catch (InterruptedException e) {
				log.error("Error Thread donst sleep correctelly "+e);					
			}			
			break;

		case 101:
			// Start with a 5 second wait, doubling each attempt, up to 320 seconds.
			if(timeToWaitHttpError < 320000){
				timeToWaitHttpError = timeToWaitHttpError * 2;				
			}

			try {
				Thread.sleep(timeToWaitHttpError);
				start();
			} catch (InterruptedException e) {
				log.error("Error Thread donst sleep correctelly "+e);					
			}			

		case 420:
			// 1 minute wait and double
			timeToWaitHttp420 = timeToWaitHttp420 * 2;
			try {
				Thread.sleep(timeToWaitHttp420);
				start();
			} catch (InterruptedException e) {
				log.error("Error Thread donst sleep correctelly "+e);					
			}
			break;	
		case 503:
			//Service unvailable | streaming server is temporaily overloaded  // 
			try {
				Thread.sleep(300000); //? wait 5 minutes
				this.start();
			} catch (InterruptedException e) {
				log.error("Error Thread donst sleep correctelly "+e);					
			}
			break;	
		}
	}

	public void start(){
		stop();
		String url = STREAMING_API_1_1_ENDPOINT;
		
		DefaultHttpClient client = new DefaultHttpClient();		
		if ((proxyHost != null) && (proxyPort > 0)) {
			setProxyParams(client);
		}	
		
		HttpPost post = new HttpPost(url);
		try {
			post.addHeader("SIGNED BY :", "HUBIQUITUS");
			post.setEntity(new UrlEncodedFormEntity(setPostparms()));
			log.debug("post : "+post + "Entity = "+post.getEntity());
			//--------  Ask the twitter stream  API the gzip stream ------	
			post.setHeader("Accept-Encoding", "deflate, gzip"); 
			log.trace("using deflate, gzip");
			//--------  Sign a Post with oauth using oauth.signpost ------	  
			CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
			consumer.setTokenWithSecret(token, tokenSecret);
			log.trace("consumer OAuth created");
			try {
				consumer.sign(post);
				log.debug("consumer OAuth signed");				 
				try {
					HttpResponse response = client.execute(post);
					log.trace("post executed");
					log.debug("-------->   :"+response);	
					checkreconnection(hcommonsFunctions.getHTTPResponse (response));
					HttpEntity entity = response.getEntity();
					log.trace("HttpEntity fetched");			
					if (entity == null) throw new IOException("No entity");
					log.trace("HttpEntity not null :)");
					loop.buffer = new BufferedReader(new InputStreamReader(new GZipStream( entity.getContent() ), "UTF-8"));
					log.trace("Buffer created");			
					loop.start();
					log.debug("Stream started");
				} catch (ClientProtocolException e) {	
					log.error("Error during execution, ",e);			
				} catch (IOException e) {
					log.error("Error during execution, ",e);			
				}

			} catch (OAuthMessageSignerException e) { log.error("ERROR AT OAUTH, ",e);}
			catch (OAuthExpectationFailedException e) {log.error("ERROR AT OAUTH, ",e);	} 
			catch (OAuthCommunicationException e) {	log.error("ERROR AT OAUTH, ",e); }

		} catch (UnsupportedEncodingException e1) {
			log.debug(" Error on setEntity :",e1);	}
	}

	public void stop(){ 
		if (!loop.isAlive() || !loop.isInterrupted()){
			loop.interrupt();
			log.debug("Stream stopped");
		}
	} 

}

