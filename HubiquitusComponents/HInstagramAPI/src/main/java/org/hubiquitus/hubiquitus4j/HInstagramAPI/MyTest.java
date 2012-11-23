package org.hubiquitus.hubiquitus4j.HInstagramAPI;

import java.util.ArrayList;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpHost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MyTest 
{
	final static Logger log = LoggerFactory.getLogger(MyTest.class);
	//URL
	//String url="https://instagram.com/oauth/authorize/";
	//https://instagram.com/oauth/authorize/?display=touch&client_id=c17f8f295b76423d9de7c0c557529fc8&redirect_uri=http://www.novediagroup.com/&response_type=code
	
	//API Credentials
	private  String client_id = "c17f8f295b76423d9de7c0c557529fc8";
	private  String redirect_uri = "http://www.novediagroup.com/";
	
	//String url ="https://instagram.com/oauth/authorize/?client_id="+client_id+"&redirect_uri="+ redirect_uri+"&response_type=code";
	String url = "https://instagram.com/oauth/authorize/?display=touch&client_id=c17f8f295b76423d9de7c0c557529fc8&redirect_uri=http://www.novediagroup.com/&response_type=code";
	
	HttpClient client = new HttpClient();

	private String proxyHost = "192.168.102.84";
	private int proxyPort = 3128;
	
	
	
	/**
	 * Used to set proxy params to the DefaultHttpClient
	 * @param client
	 */
	private void setProxyParams (DefaultHttpClient client){		
		HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http");
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			
	}
	
	
	private void getTokenInstagram(){
		GetMethod method = new GetMethod(url);
		
		
		
		if(proxyHost != null && proxyPort > 0){
			HostConfiguration config = client.getHostConfiguration();
			config.setProxy(proxyHost, proxyPort);
			log.info("proxy used : " + proxyHost + ":" + proxyPort);
		}
		
		ArrayList<BasicNameValuePair> nValuePairs = new ArrayList <BasicNameValuePair>();
		nValuePairs.add(new BasicNameValuePair("client_id",     client_id));
		nValuePairs.add(new BasicNameValuePair("redirect_uri",  redirect_uri));
		nValuePairs.add(new BasicNameValuePair("response_type",     "code"));
		
		
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode == 200) {
				//return new FBStatus(new JSONObject(method.getResponseBodyAsString()));
			
			log.info("-------------------------------> "+method.getResponseBodyAsString());
			} else {
				log.debug("Error trying to read request to : " + url +  " status : " + statusCode);
			}
		} catch (Exception e) {
			log.debug("Error while trying to launch request " + client + " : " + e.toString());
		} finally {
			method.releaseConnection();
		}
		
	}

	
	
	
	
		
	
    public static void main( String[] args )
    {
    	MyTest a = new MyTest();
    	a.getTokenInstagram();  
    	
    }
}
