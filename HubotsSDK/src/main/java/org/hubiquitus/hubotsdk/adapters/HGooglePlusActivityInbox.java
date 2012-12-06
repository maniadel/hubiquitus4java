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
package org.hubiquitus.hubotsdk.adapters;

import java.util.HashMap;
import java.util.Map;

import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubiquitus4j.hgoogleplus.GPActivity;
import org.hubiquitus.hubiquitus4j.hgoogleplus.GPItem;
import org.hubiquitus.hubiquitus4j.hgoogleplus.HGooglePlusActivity;
import org.hubiquitus.hubiquitus4j.hgoogleplus.HGooglePlusActivityListners;
import org.hubiquitus.hubotsdk.AdapterInbox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HGooglePlusActivityInbox extends AdapterInbox implements HGooglePlusActivityListners{

	final Logger log = LoggerFactory.getLogger(HGooglePlusActivityInbox.class);

	private Map <String, JSONObject> itemMap= new HashMap<String, JSONObject>();

	private String proxyHost;
	private int    proxyPort;	

	private String query;
	private String langage;
	private int    maxResults;
	private String orderBy;

	private long roundTime;
	private String APIKey;




	private HGooglePlusActivity gplusActivity;


	/**
	 * The map where the JSONObject's properties are kept.
	 */
	@SuppressWarnings("rawtypes")
	private Map map;

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



	@Override
	public void setProperties(JSONObject properties) {

		if(properties != null){
			try{
				if (properties.has("proxyHost")) {
					this.proxyHost = properties.getString("proxyHost");
				}				
				if (properties.has("proxyPort")) {
					this.proxyPort = properties.getInt("proxyPort");
				}
				if (properties.has("query")) {
					this.query = properties.getString("query");
				}
				if (properties.has("langage")) {
					this.langage = properties.getString("langage");
				}
				if (properties.has("maxResults")) {
					this.maxResults = properties.getInt("maxResults");
				}
				if (properties.has("orderBy")) {
					this.orderBy = properties.getString("orderBy");
				}

				if (properties.has("roundTime")) {
					this.roundTime = properties.getLong("roundTime");
				}
				if (properties.has("APIKey")) {
					this.APIKey = properties.getString("APIKey");
				}

			}catch(JSONException e){
				log.error("ERROR ON CONFIG FILE 'JSON NOT VALID', ERROR TYPE : ",e);
			}
		}
		log.info("Properties Initialized : " + this);
	}

	@Override
	public void start() {
		log.info("Starting request GooglePlus Activity ...");			
		gplusActivity = new HGooglePlusActivity(proxyHost, proxyPort, query, langage, maxResults, orderBy, roundTime, APIKey);
		gplusActivity.addListener(this);
		gplusActivity.start();
		log.info("Started request GooglePlus Activity. ");

	}

	@Override
	public void stop() {
		log.info("Stopping request...");
		if (gplusActivity != null)
			gplusActivity.stop();
		log.info("Stopped request.");

	}

	@Override
	public String toString() {
		return "HGooglePlusOneCercedInBox [" 
				+ "  proxyHost  = " + proxyHost 
				+ ", proxyPort  = " + proxyPort 
				+ ", query      = " + query 
				+ ", langage    = " + langage
				+ ", maxResults = " + maxResults
				+ ", orderBy    = " + orderBy
				+ ", roundTime  = " + roundTime  
				+ ", APIKey     = " + APIKey  +
				"]";
	}

	/**
	 * Used to transform the GPstatus into HMessage <br>
	 * 
	 * @param gPStatus
	 * @return HMessage 
	 */
	public HMessage tranformeGPActivityToHMessage(GPActivity gpActivity){
		HMessage msg = new HMessage();
		msg.setType("GPActivity");	
		msg.setPayload(gpActivity);

		return msg;
	}


	@Override
	public void onStatusActivity(GPActivity gpActivity) {
		
		JSONArray jsArray = gpActivity.getItems();
		for (int i=0;i< jsArray.length();i++){
			try {

				if (!itemMap.containsKey( jsArray.getJSONObject(i).getString("id"))  ){
					put ( transformItem( jsArray.getJSONObject(i) ) );
					itemMap.put(jsArray.getJSONObject(i).getString("id"), null );
				}


			} catch (JSONException e) {
				log.error(" Error tranformation item  (e: "+e);				
			}
		}

	}

	public HMessage transformItem(JSONObject item) throws JSONException{
		HMessage msg  = new HMessage();
		msg.setType("GPActivity");	

		GPItem gpItem = new GPItem(); 

		if( !item.isNull("published") ){
			gpItem.setPublished(item.getString("published") );
		}

		if( !item.isNull("title") ){
			gpItem.setTitle(item.getString("title") );
		}

		if( !item.isNull("updated") ){
			gpItem.setUpdated(item.getString("updated") );
		}
		if( !item.isNull("id") ){
			gpItem.setId( item.getString("id") );
		}
		if( !item.isNull("url") ){
			gpItem.setUrl( item.getString("url") );
		}


		if( !item.isNull("actor") ){

			if( !item.getJSONObject("actor").isNull("id") ){
				gpItem.setActorId( item.getJSONObject("actor").getString("id") );
			}
			if( !item.getJSONObject("actor").isNull("displayName") ){
				gpItem.setDisplayName( item.getJSONObject("actor").getString("displayName") );
			}
			if( !item.getJSONObject("actor").isNull("image") ){
				gpItem.setImage( item.getJSONObject("actor").getString("image") );
			}
			if( !item.getJSONObject("actor").isNull("url") ){
				gpItem.setActorUrl( item.getJSONObject("actor").getString("url") );
			}
		}

		if( !item.isNull("object") ){
			if( !item.getJSONObject("object").isNull("attachments") ){
				gpItem.setAttachments( item.getJSONObject("object").getJSONArray("attachments") );
			}
		}

		msg.setPayload(gpItem);
		return (msg);
	}

}
