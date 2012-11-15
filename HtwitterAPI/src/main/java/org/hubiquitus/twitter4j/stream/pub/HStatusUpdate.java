package org.hubiquitus.twitter4j.stream.pub;

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

	/****
	 * This method post a tweets and return a code answer recived in the Header Http
	 * 
	 * @return int (Connexion code "200 :Succes", "403 : alerdy text tweeted", ...  )
	 */
	public int postTweet(String status){
		int code = 0;
		HttpResponse response = null;

		String url = UPDATE_STATUS_1_ENDPOINT;

		DefaultHttpClient client = new DefaultHttpClient();	
		
		if ((proxyHost != null) && (proxyPort > 0)) {
			HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http");
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			log.debug("using a proxy : " + proxyHost+":"+proxyPort);
		}

		HttpPost post = new HttpPost(url);

		ArrayList<BasicNameValuePair> nValuePairs = new ArrayList <BasicNameValuePair>();		
       
		
		if( status!=null){
			nValuePairs.add(new BasicNameValuePair("status", status));
		}
		
		try {
			post.setEntity(new UrlEncodedFormEntity(nValuePairs));
		} catch (UnsupportedEncodingException e1) {
			log.debug(" Error on setEntity :",e1);		
		}
		
		//--------  Sign a Post with oauth using oauth.signpost --------

		CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);		
		consumer.setTokenWithSecret(token, tokenSecret);
		//--------------------------------------------------------------
		try {
			consumer.sign(post);
			try {
				response = client.execute(post);
				
				log.info(" ----- CONNECTION RESPONSE   : "+response);				
				
				if(response != null){
					if(response.toString().contains("HTTP/1.1 200")){
						log.debug(" ----- YOUR TWEET IS POSTED WITH SUCCES ");
						code  = 200;
					}else if(response.toString().contains("HTTP/1.1 403") ){
						code = 403;
						log.debug(" ----- YOU HAVE ALERDY TWEET THIS TEXT");	
					}else {
						log.debug("ERROR TRYING TO EXCUTE POST TWEET REQUEST,  STATUS ERROR : " + response);
					}					
				}
			} catch (ClientProtocolException e) {
				log.debug("Error in excute post (ClientProtocolException) :",e);
			} catch (IOException e) {
				log.debug("Error in excute post (IOException) :",e);
			}

		} catch (OAuthMessageSignerException e) {
			log.info("ERROR AT OAUTH, OAuthMessageSignerException ,ERROR TYPE ",e);			
		} catch (OAuthExpectationFailedException e) {
			log.info("ERROR AT OAUTH,  OAuthExpectationFailedException   ,ERROR TYPE ",e);			
		} catch (OAuthCommunicationException e) {
			log.info("ERROR AT OAUTH,  OAuthCommunicationException   ,ERROR TYPE ",e);			
		}	    
		//------------------------------------------------------------------
		return code;
	}
	
}
