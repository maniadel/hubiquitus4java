package org.hubiquitus.hubotsdk.adapters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.AdapterInbox;
import org.hubiquitus.hubotsdk.adapters.HHttpAdapter.HHttpAdapterRouteBuilder;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HInstagramRealTime extends AdapterInbox implements Processor{

	final Logger log = LoggerFactory.getLogger(HInstagramRealTime.class);


	private String host = "0.0.0.0";
	private int port = 80;
	private String path = "";


	private String clientId;
	private String clientSecret;
	private String object;
	private String aspect;
	private String objectID;
	private String verifyToken;
	private String callbackUrl;
	private String option;


	private static final String INSTAGRAM_SUBSCRIPTION_ENDPOINT = "https://api.instagram.com/v1/subscriptions/";


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

				if (properties.has("clientId")){
					this.clientId = properties.getString("clientId");
				}
				if (properties.has("clientSecret")){
					this.clientSecret = properties.getString("clientSecret");
				}
				if (properties.has("object")){
					this.object = properties.getString("object");
				}
				if (properties.has("aspect")){
					this.aspect = properties.getString("aspect");
				}
				if (properties.has("verifyToken")){
					this.verifyToken = properties.getString("verifyToken");
				}
				if (properties.has("callbackUrl")){
					this.callbackUrl = properties.getString("callbackUrl");
				}
				if (properties.has("option")){
					this.option = properties.getString("option");
				}

				if (properties.has("object_id")){
					this.objectID = properties.getString("object_id");
				}


			} catch (JSONException e) {
				log.warn("message: ", e);
			}
		}
	}


	@Override
	public void start() {
		String jettyOptions = "matchOnUriPrefix=true";
		String jettyCamelUri = "jetty:http://" + this.host + ":" + this.port + this.path;
		jettyCamelUri += "?" + jettyOptions;

		log.info("Starting HttpAdapter with jettyCamelUri : " + jettyCamelUri);

		//add route 
		HHttpAdapterRouteBuilder routes = new HHttpAdapterRouteBuilder(jettyCamelUri, this);
		try {
			camelContext.addRoutes(routes);
		} catch (Exception e) {
			log.warn("message: ", e);
		}


		//***************
		Thread thread1 = new Thread(new RunnableInstagramThread(), "thread1");
		thread1.start();

		//************

	}

	@Override
	public void stop() {
		// Delete all subscriptions  
		deleteSubscription();
		log.info(" Delete subscription excuted with succes !!");
	}



	/********************************************/
	class RunnableInstagramThread implements Runnable {

		Thread runner;
		public RunnableInstagramThread() {
		}
		public RunnableInstagramThread(String threadName) {
			runner = new Thread(this, threadName); // (1) Create a new thread.
			System.out.println(runner.getName());
			runner.start(); 
		}
		public void run() {

			try {
				Thread.sleep(3000);
				log.debug("OPTION TO EXCUTE  : "+option );

				deleteSubscription();
				log.debug("All last subscription are deleted ...");

				//Subscription to user subscription
				if("subscribe".equalsIgnoreCase(option)){

					log.info(" subscription params "+setSubscriptionParams());
					postUrl( setSubscriptionParams(), INSTAGRAM_SUBSCRIPTION_ENDPOINT );
					log.info(" Subscription executed  ...");

				}else if  ("tagsubscription".equalsIgnoreCase(option)){

					log.info(" Tag subscription params "+setTagSubscriptionParams());
					postUrl( setTagSubscriptionParams(), INSTAGRAM_SUBSCRIPTION_ENDPOINT );
					log.info(" Subscription Tag executed with succes ...");

				}else if  ("geography".equalsIgnoreCase(option)){
					log.info("excuted delete subscription !!");
					deleteSubscription();					
				}
			} catch (InterruptedException e) {
				log.error("Thread can't sleep correctelly !!! ");
			}
		}
		public void stop(){
			//Not supported 
		}
	}


	@Override
	public void process(Exchange exchange) throws Exception {
        log.info("HTTP Notfication ");
		Message in = exchange.getIn();
		HttpServletRequest request = in.getBody(HttpServletRequest.class);
		//Get Exchange body
		byte[] rawBody = in.getBody(byte[].class);

		//TODO verifier verfiy token
		//if (this.verifyToken.equalsIgnoreCase(request.getParameter("hub.verify_token"))){
		if(request.getParameter("hub.challenge") != null){
			Object body = request.getParameter("hub.challenge");
			exchange.getOut().setBody(body);
			log.trace(" Challenge is posted,  his value  :"+request.getParameter("hub.challenge"));
		}
		//}

		if(in.getBody() != null){
			// TODO change log.info to log.debug after test 
			String instagramBody = new String( rawBody );
			log.info("Received body value is  :  "+instagramBody);

			if(instagramBody.startsWith("{")){
				log.info("Instagram response  :"+new JSONObject(instagramBody));

			}else if (instagramBody.startsWith("[{")){

				// Get the first pagination order by the recent element 
				JSONArray instagramJSArray = new JSONArray(instagramBody);
				long time            =  instagramJSArray.getJSONObject(0).getLong("time");
				long subscriptionID  =  instagramJSArray.getJSONObject(0).getLong("subscription_id");
				String tag = instagramJSArray.getJSONObject(0).getString("object_id");


				JSONArray data = getInstagramTag(tag);
				if(data != null){
					//find the element using time notification and data.caption.created_time (we have a diffrence of 1 millisecond)
					JSONObject instagramResult = findTagObject(time, data);

					if(instagramResult !=null){
						HMessage msg = new HMessage();
						msg = transformInstagramToHMessage(instagramResult);
						JSONObject header = new JSONObject();
						header.put("subscription_id", subscriptionID);
						msg.setHeaders(header);
						put(msg);
					}
				}
			}
		} 

	}



	/**
	 * This method get the last the recent object from the first Instagram pagination.
	 * @param tag String object
	 * @return JSONArray as data : [{},{},{}, ...] recent instagram posted object
	 */
	public JSONArray getInstagramTag(String tag){
		String url = "https://api.instagram.com/v1/tags/"+tag+"/media/recent?client_id="+clientId;
		String result =  getURL(url);
		if(result !=null){
			try {
				JSONObject json = new JSONObject(result);
				if(json.has("data")){
					return ( json.getJSONArray("data"));
				}
			} catch (Exception e) {
				log.error("Error to build a json from a post url, error type : "+e);
			}
		}
		return null;
	}

	/**
	 * This method used to find the json object using created time in JSONArray collection.
	 * 
	 * @param time : long (r)
	 * @param data JSONArray
	 * @return JSONObject
	 * @throws JSONException
	 */
	public JSONObject findTagObject(long time, JSONArray data){
		log.info(" <<<<<<<<<<<<<<<  Data  : "+data);	
		
		for(int i=0; i<data.length() ; i++){
			try {
				if(data.getJSONObject(i).has("caption")){

					if (time - data.getJSONObject(i).getJSONObject("caption").getLong("created_time") < 3 ){
						
						return data.getJSONObject(i); 
					}

				}
			} catch (JSONException e) {
				log.error(" Error in extraction the JSONObject, error type : ("+e);

			}
		}
		return null;
	}



	/**
	 * Used to build an HMessage, with payload conatain JSONObject received from instagram 
	 * 
	 * @param instagramResult JSONObject received from Instagram
	 * @return HMessage
	 */
	public HMessage transformInstagramToHMessage(JSONObject instagramResult){

		HMessage message = new HMessage();
		message.setAuthor(this.actor);
		message.setType("HInstagramRT");
		message.setPublished(new DateTime());
		message.setPayload(instagramResult);
		return message; 
	}


	@Override
	public String toString() {
		return "HInstagram  [clientId="    + clientId
				+ ", clientSecret="        + clientSecret
				+ ", object="  + object
				+ ", aspect=" + aspect
				+ ", verifyToken="   + verifyToken
				+ ", callbackUrl="   + callbackUrl
				+"]";
	}


	/**
	 * This method is used to build a user subscription params params 
	 * @return ArrayList of Basic value pairs 
	 */
	public  ArrayList<BasicNameValuePair> setSubscriptionParams(){

		ArrayList<BasicNameValuePair> nValuePairs = new ArrayList <BasicNameValuePair>();
		nValuePairs.add(new BasicNameValuePair("client_id",     clientId));
		nValuePairs.add(new BasicNameValuePair("client_secret", clientSecret));
		nValuePairs.add(new BasicNameValuePair("object", "user"  ));
		nValuePairs.add(new BasicNameValuePair("aspect", aspect));
		nValuePairs.add(new BasicNameValuePair("verify_token", verifyToken));
		nValuePairs.add(new BasicNameValuePair("callback_url", callbackUrl)); 
		return nValuePairs;
	}



	/**
	 * used to build subscription tag's subscription parameters 
	 * @return  the params for the Tag's params
	 */
	public  ArrayList<BasicNameValuePair> setTagSubscriptionParams(){
		ArrayList<BasicNameValuePair> nValuePairs = new ArrayList <BasicNameValuePair>();

		nValuePairs.add(new BasicNameValuePair("client_id",     clientId));
		nValuePairs.add(new BasicNameValuePair("client_secret", clientSecret));
		nValuePairs.add(new BasicNameValuePair("object", object ));  
		nValuePairs.add(new BasicNameValuePair("aspect", aspect));
		nValuePairs.add(new BasicNameValuePair("object_id", objectID));

		//nValuePairs.add(new BasicNameValuePair("verify_token", verifyToken));
		nValuePairs.add(new BasicNameValuePair("callback_url", callbackUrl)); 

		return nValuePairs;
	}


	/**
	 * This method is used to delete all Instagram subscription.<br>
	 * It used when we call to stop method. 
	 */
	public void deleteSubscription(){

		DefaultHttpClient client = new DefaultHttpClient();		
		String url = "https://api.instagram.com/v1/subscriptions?object=all&client_id="+this.clientId+"&client_secret="+this.clientSecret;

		HttpDelete delete = new HttpDelete(url);

		try {

			log.debug("Delete  : "+delete + "Header = "+delete.getAllHeaders());
			HttpResponse response = client.execute(delete);
			log.info("-----> Delete  response  :"+response);

		} catch (ClientProtocolException e) {
			log.error(" Error in client Protocol, error type : "+e);
		} catch (IOException e) {
			log.error(" Error IO, error type :"+e);
		}
	}




	/***
	 * 
	 * @param br Buffer 
	 * @return String 'body page as a strem'
	 * @throws IOException
	 */
	private  String readBuffer(BufferedReader br) throws IOException{

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

	/**
	 * 
	 * @param myParmas a post parameters ('client_id, client_secret, url_callback, ...')
	 */
	public String postUrl(ArrayList<BasicNameValuePair> myParmas, String url){

		DefaultHttpClient client = new DefaultHttpClient();		
		HttpPost post = new HttpPost(url);

		try {

			if (myParmas != null){
				post.setEntity(new UrlEncodedFormEntity(myParmas));
			}

			log.debug("post : "+post + "Entity = "+post.getEntity());

			try {
				HttpResponse response = client.execute(post);

				log.trace("post executed");
				log.debug("-------->   :"+response);	

				HttpEntity entity = response.getEntity();
				log.trace("HttpEntity fetched");			
				if (entity == null) throw new IOException("No entity");
				log.trace("HttpEntity not null :)");

				BufferedReader br = new BufferedReader(new InputStreamReader(
						entity.getContent(), "UTF-8"));
				//TODO change log.info to debug.info
				String answer = readBuffer(br);
				log.info(" ------> Answer data  : "+answer);
				return answer;


			} catch (ClientProtocolException e) {	
				log.error("Error during execution, ",e);			
			} catch (IOException e) {
				log.error("Error during execution, ",e);			
			}
		} catch (Exception e1) {
			log.debug(" Error on setEntity :",e1);	}

		return null;
	}


	public String getURL( String url){
		DefaultHttpClient client = new DefaultHttpClient();		
		HttpGet get = new HttpGet(url);

		try {
			try {
				HttpResponse response = client.execute(get);

				log.trace("Get executed");
				log.debug("-------->   :"+response);	

				HttpEntity entity = response.getEntity();
				log.trace("HttpEntity fetched");			
				if (entity == null) throw new IOException("No entity");
				log.trace("HttpEntity not null :)");

				BufferedReader br = new BufferedReader(new InputStreamReader(
						entity.getContent(), "UTF-8"));
				//TODO change log.info to debug.info
				return ( readBuffer(br) );
				//log.info(" ------> Answer data  : "+answer);
				//return answer;


			} catch (ClientProtocolException e) {	
				log.error("Error during execution, ",e);			
			} catch (IOException e) {
				log.error("Error during execution, ",e);			
			}
		} catch (Exception e1) {
			log.debug(" Error on setEntity :",e1);	}

		return null;
	}




}
