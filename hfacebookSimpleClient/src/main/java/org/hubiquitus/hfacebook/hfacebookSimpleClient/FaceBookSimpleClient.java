package org.hubiquitus.hfacebook.hfacebookSimpleClient;


import org.hubiquitus.hfacebook.publics.GetLikeFacebook;
import org.hubiquitus.hfacebook.publics.HFacebookListners;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class FaceBookSimpleClient extends GetLikeFacebook implements HFacebookListners
{
	final static Logger log = LoggerFactory.getLogger(FaceBookSimpleClient.class);
	
    public FaceBookSimpleClient(String proxyHost, int proxyPort,
			String pageName, long roundTime) {
		super(proxyHost, proxyPort, pageName, roundTime);
		
	}

	public void onStatusLike(JSONObject statusLike) {
		
		try {
			log.info("-----> Recived status like   :"+statusLike);
			log.info("Your page contain   : "+statusLike.getInt("likes")+" likes ");
		} catch (JSONException e) {
			log.error("Error in get Likes, Type :"+e);
			e.printStackTrace();
		}
	}

	public void onOthersLikeStatus(JSONObject othersMsg) {
		log.info("-----> Recived status like   :"+othersMsg);
	}
	
	public static void main( String[] args )
    {
		String proxyHost = "0.0.0.0"; //your proxyHost 
		int proxyPort = 0000; // your proxyPort
		String pageName ="cocacola"; // facebook page desired
		long roundRequest = 6000; // On milliseconds 
		
	
		
		FaceBookSimpleClient like = new FaceBookSimpleClient(proxyHost, proxyPort, pageName, roundRequest);
		like.addListener(like);
		like.start();
		
    }
	
}
