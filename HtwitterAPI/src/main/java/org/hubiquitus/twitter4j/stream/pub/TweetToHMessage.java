package org.hubiquitus.twitter4j.stream.pub;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.hubiquitus.hapi.exceptions.MissingAttrException;
import org.hubiquitus.hapi.hStructures.HGeo;
import org.hubiquitus.hapi.hStructures.HLocation;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.adapters.HtwitterAdapter.HTweet;
import org.hubiquitus.hubotsdk.adapters.HtwitterAdapter.HTweetAuthor;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TweetToHMessage {

	final Logger log = LoggerFactory.getLogger(TweetToHMessage.class);
	/**
	 * The map where the JSONObject's properties are kept.
	 */
	@SuppressWarnings("rawtypes")
	private Map map;
	
	/***
	 * 
	 * @param date
	 * @return Joda date
	 * @throws ParseException
	 */
	public static java.util.Date getTwitterDate(String date) throws ParseException {

		final String TWITTER="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(TWITTER);
		sf.setLenient(true);
		return sf.parse(date);
	}

	
	/**
	 * Get an optional value associated with a key.
	 *
	 * @param key A key string.
	 * @return An object which is the value, or null if there is no value.
	 */
	public Object opt(String key) {
		return key == null ? null : this.map.get(key);
	}

	/**
	 * Determine if the value associated with the key is null or if there is
	 * no value.
	 *
	 * @param key A key string.
	 * @return true if there is no value associated with the key or if
	 *         the value is the JSONObject.NULL object.
	 */
	public boolean isNull(String key) {
		return JSONObject.NULL.equals(opt(key));
	}

	/**
	 * Function for transforming Tweet to HMessage
	 * @param tweet from Twitter API 
	 * @return HMessage of type hTweet
	 * @throws JSONException 
	 */
	public HMessage transformtweet(JSONObject tweet) throws JSONException{
		HMessage message = new HMessage();

		HTweet htweet = new HTweet();
		HTweetAuthor hauthortweet = new HTweetAuthor();

		//Construct the location 
		HLocation location = new HLocation();

		//Construct the Place
		if( !tweet.isNull("place") ){

			if("point".equalsIgnoreCase(  tweet.getJSONObject("place").getJSONObject("bounding_box").getString("type") )){				 
				HGeo geo = new HGeo(tweet.getJSONObject("place").getJSONObject("bounding_box").getJSONObject("coordinates")   ); 
				location.setPos(geo);
				message.setLocation(location);
			}	

			if(  !(tweet.getJSONObject("place").isNull("full_name"))     ){
				location.setAddr(tweet.getJSONObject("place").getString("full_name"));					
			}		
			if(!(tweet.getJSONObject("place").isNull("country_code") )){
				location.setCountryCode(tweet.getJSONObject("place").getString("country_code"));				
			}
			if((! ( tweet.getJSONObject("place").isNull("place_type"))) && ("city".equalsIgnoreCase(tweet.getJSONObject("place").getString("place_type")))){
				location.setCity( tweet.getJSONObject("place").getString("name") );					
			}
		}		

		// Init The date
		try {
			DateTime createdAt = new DateTime(getTwitterDate(tweet.getString("created_at")) );
			DateTime createdAtAuthor = new DateTime(getTwitterDate(tweet.getJSONObject("user").getString("created_at")));
			message.setPublished(new DateTime(createdAt));
			hauthortweet.setCreatedAt(createdAtAuthor);

		} catch (ParseException e2) {
			log.error("ERROR IN DATE PARSE :",e2);
		}

		//Construct the Authortweet JSONObject

		hauthortweet.setStatus      (tweet.getJSONObject("user").getInt   ("statuses_count"));		
		hauthortweet.setFollowers   (tweet.getJSONObject("user").getInt   ("followers_count"));
		hauthortweet.setFriends     (tweet.getJSONObject("user").getInt   ("friends_count"));
		hauthortweet.setLocation    (tweet.getJSONObject("user").getString("location"));
		hauthortweet.setDescription (tweet.getJSONObject("user").getString("description"));
		hauthortweet.setProfileImg  (tweet.getJSONObject("user").getString("profile_image_url_https"));

		if(! "null".equalsIgnoreCase(tweet.getJSONObject("user").getString("url"))){
			try {
				hauthortweet.setUrl( new URL(tweet.getJSONObject("user").getString("url"))  );
			} catch (MalformedURLException e1) {			
				log.debug("MAL FORMED URL  :"+e1);			
			}			
		}

		hauthortweet.setLang(   tweet.getJSONObject("user").getString("lang"));
		hauthortweet.setListeds(tweet.getJSONObject("user").getInt("listed_count"));
		hauthortweet.setGeo( tweet.getJSONObject("user").getBoolean("geo_enabled")); 
		hauthortweet.setVerified(tweet.getJSONObject("user").getBoolean("verified"));
		hauthortweet.setName(tweet.getJSONObject("user").getString("name")); 	


		//Construct the tweet JSONObject		
		htweet.setId(tweet.getLong("id"));
		try {
			htweet.setSource(tweet.getString("source"));
			htweet.setText(tweet.getString("text"));
			htweet.setAuthor(hauthortweet); //TODO see this 
		} catch (MissingAttrException e) {
			log.error("mssage: ", e);
		}

		message.setPayload(htweet);
		message.setType("hTweet");
		message.setAuthor(tweet.getJSONObject("user").getString("screen_name")+ "@twitter.com");		

		if (log.isDebugEnabled()) {
			log.debug("tweet("+tweet+") -> hMessage :"+message);
		}		
		return message;
	}
	
}
