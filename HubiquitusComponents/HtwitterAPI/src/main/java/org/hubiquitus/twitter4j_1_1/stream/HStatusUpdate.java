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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;


public class HStatusUpdate {

	private String proxyHost;
	private int proxyPort;

	private String consumerKey;
	private String consumerSecret;	    
	private String token;
	private String tokenSecret;

				                                          
	private static final String UPDATE_STATUS_1_ENDPOINT = "https://api.twitter.com/1.1/statuses/update.json";
	private static Logger log = Logger.getLogger(HStatusUpdate.class);


	public HStatusUpdate(
			String proxyHost, 
			int proxyPort, 
			String consumerKey, 
			String consumerSecret, 
			String token, 
			String tokenSecret){

		super();
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.token = token;
		this.tokenSecret = tokenSecret;
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
	 * 
	 * @param status String
	 * @return  ArrayList of BasicValuePair 
	 */
	private ArrayList<BasicNameValuePair>  setStatus(String status){
		ArrayList<BasicNameValuePair> nValuePairs = new ArrayList <BasicNameValuePair>();
		if( status!=null){
			nValuePairs.add(new BasicNameValuePair("status", status));
		}
		return nValuePairs;
	}
	/**
	 * Used to display the message connection response. The diffrents messages are : <br>
	 *  <br> TWEET POSTED WITH SUCCES.
	 *  <br> TWEET ALREADY EXIST
	 *  <br> ERROR TRYING TO EXCUTE POST TWEET REQUEST
	 * */
	private void displayConnectionResponse(HttpResponse response){
		if(response != null){
			if(response.toString().contains("HTTP/1.1 200")){
				log.debug(" -----> YOUR TWEET IS POSTED WITH SUCCES ");
			}else if(response.toString().contains("HTTP/1.1 403") ){				
				log.error(" -----> YOU HAVE ALREADY TWEET THIS TEXT");	
			}else {
				log.error("ERROR TRYING TO EXCUTE POST TWEET REQUEST,  STATUS ERROR : " + response);
			}					
		}
	}	
	/****
	 * This method post a tweets and return a code answer recived in the Header Http
	 * 
	 * @return int (Connexion code "200 :Succes", "403 : alerdy text tweeted", ...  )
	 */
	public void postTweet(String status){		
		String url = UPDATE_STATUS_1_ENDPOINT;

		DefaultHttpClient client = new DefaultHttpClient();
		if ((proxyHost != null) && (proxyPort > 0)) {
			setProxyParams(client);
		}
		HttpPost post = new HttpPost(url);		
		try {
			post.addHeader("SIGNED BY :", "HUBIQUITUS");
			post.setEntity(new UrlEncodedFormEntity(setStatus(status)));
			//--------  Sign a Post with oauth using oauth.signpost --------
			CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);		
			consumer.setTokenWithSecret(token, tokenSecret);
			
			try {
				consumer.sign(post);
				try {
					HttpResponse response  = client.execute(post);					
					log.debug("--->[Update status CONNECTION RESPONSE]    : "+response);				
					displayConnectionResponse(response);
					
				} catch (ClientProtocolException e) {
					log.error("Error in excute post (ClientProtocolException) :",e);
				} catch (IOException e) {
					log.error("Error in excute post (IOException) :",e);
				}
			} catch (OAuthMessageSignerException e) {
				log.error("ERROR AT OAUTH, OAuthMessageSignerException ,ERROR TYPE ",e);			
			} catch (OAuthExpectationFailedException e) {
				log.error("ERROR AT OAUTH,  OAuthExpectationFailedException   ,ERROR TYPE ",e);			
			} catch (OAuthCommunicationException e) {
				log.error("ERROR AT OAUTH,  OAuthCommunicationException   ,ERROR TYPE ",e);			
			}			
		} catch (UnsupportedEncodingException e1) {
			log.debug(" Error on setEntity :",e1);		
		}	
	}	
}
