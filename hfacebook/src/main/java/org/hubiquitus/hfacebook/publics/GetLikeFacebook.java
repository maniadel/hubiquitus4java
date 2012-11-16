package org.hubiquitus.hfacebook.publics;

import java.util.ArrayList;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetLikeFacebook {

	final static Logger log = LoggerFactory.getLogger(GetLikeFacebook.class);
	private ArrayList<HFacebookListners> listeners = new ArrayList<HFacebookListners>();

	//	private static String proxyHOST = "192.168.102.84";
	//	private static int proxyPORT = 3128;
	private static  String proxyHost;
	private static int    proxyPort;	

	private String pageName;
	private long roundTime;

	

	private static String END_POINT_LIKE_FACEBOOK_PAGE = "https://graph.facebook.com/";

	public GetLikeFacebook( String proxyHost,
			int  proxyPort,
			String pageName,
			long roundTime) {
		super();
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;

		this.pageName = pageName;
		this.roundTime = roundTime;
	}

	/**
	 * Used to add a listener
	 * @param listener which must implement the HStreamlistener to get the status
	 */
	public void addListener(HFacebookListners listener) {
		listeners.add(listener);
		log.debug("listener added: "+listener);
	}

	public void removeListener(HFacebookListners listener) {
		listeners.remove(listener);
		log.debug("listener removed: "+listener);
	}


	public void fireEvent(JSONObject item) {  	    
		for (HFacebookListners listener : listeners) {
			if (item.has("likes"))
				listener.onStatusLike(item);
			else  
				listener.onOthersLikeStatus(item);
		}
	}


	/*** 
	 * @param url: String 'is the end poind ti get like facebook page'
	 * @param pageName : String 'the name of page to get here like' )
	 * @return JSONObject 'result getting '  
	 */
	protected static JSONObject  getLikeFacebookPage(String pageName){
		JSONObject  response =null;    	
		String url = null;
		
		url =  END_POINT_LIKE_FACEBOOK_PAGE + pageName;    	
		//Instantiate an HttpClient
		HttpClient client = new HttpClient();
		//Instantiate a GET HTTP method
		GetMethod method = new GetMethod(url);	

		int statusCode;    	
		try {
			if(proxyHost != null && proxyPort > 0){
				HostConfiguration config = client.getHostConfiguration();
				config.setProxy(proxyHost, proxyPort);
			}
			statusCode = client.executeMethod(method);
			if (statusCode == 200) {

				String myBody = method.getResponseBodyAsString();
				response= new JSONObject(myBody);

			} else {
				log.debug("Error trying to read request to : " + url +  " status : " + statusCode);
			}
			method.releaseConnection();
		} catch (Exception e) {
			log.debug("Error while trying to launch request " + client + " : " + e.toString());
		}
		return response;
	}





	/****************************************************************************/

	private class Loop extends Thread {

		private GetLikeFacebook owner;
		
		Loop(GetLikeFacebook owner) {
			super("Thread used to ask a like to Facebook API .");
			this.owner = owner;
		}
		
		public void run() {
			while (true) {
				try {
					
				 JSONObject jsonObj =	getLikeFacebookPage(pageName);
				
				if(jsonObj!=null){
					owner.fireEvent(jsonObj);
					
					 if(jsonObj.has("error")){
							log.error("Error, The facebook page is not valid ! the page is not found ");
							
							loop.interrupt();
							log.info("Round request is stopped with success. ");
							break;
							
						 }else if(!jsonObj.has("likes")){
							log.error("Error, Your page does not contain the attribute like !!");
							
							loop.interrupt();
							log.info("Round request is stopped with success. ");
							break;
						 }	
					 Thread.sleep(roundTime);					
				}else{
					
					log.error("Error, Your name page is not found !!");
					loop.interrupt();
					log.info("Round request is stopped with success. ");
					break;
				}
				 
					
				 
					
					
				} catch (Exception e) {
					if (!isInterrupted())
						log.error("can not stop a Thread correctelly  :(", e);
						break;
				}
				
			}
			
		}
	}
	private Loop loop = new Loop(this);

	/*****************************************************************************/
	public void start(){
		loop.start();
	}

	public void stop(){
		
		if (!loop.isAlive() || !loop.isInterrupted())
			loop.interrupt(); 
		    log.debug(" Round request is stopped ");
		    
	} 


	//***************************************	
	public static void main(String[] args) {

		GetLikeFacebook like = new GetLikeFacebook("192.168.102.84", 3128, "cocacola", 1000);
		like.start();
		
	}



}
