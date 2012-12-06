package org.hubiquitus.hubiquitus4j.hgoogleplus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Describe a Gougle plus Activity 'GPActivity' 
 * 
 * @author MANI Adel
 *
 */
public class GPActivity extends JSONObject{

	final Logger log = LoggerFactory.getLogger(GPActivity.class);


	public GPActivity(JSONObject jsonObj) throws JSONException{
		super(jsonObj, JSONObject.getNames(jsonObj));
	}

	public GPActivity() throws JSONException{
		
	}

	/********************************************************
	 *              The Getters
	 *******************************************************/

	/**
	 * 
	 * @return kind
	 */
	public String getkind() {
		String result = null;

		if (has("kind")) {
			try {
				result = getString("kind");
			} catch (JSONException e) {
				log.error("strange I can't read the displayName value ???!!! :(", e);
			}
		}

		return result;		
	}
	
	/**
	 * 
	 * @return nextPageToken
	 */
	public String getNextPageToken() {
		String result = null;

		if (has("nextPageToken")) {
			try {
				result = getString("nextPageToken");
			} catch (JSONException e) {
				log.error("strange I can't read the nextPageToken value ???!!! :(", e);
			}
		}
		return result;		
	}
	
	/**
	 * 
	 * @return items
	 */
	public JSONArray getItems() {
		JSONArray result = null;

		if (has("items")) {
			try {
				result = getJSONArray("items");
			} catch (JSONException e) {
				log.error("strange I can't read the items value ???!!! :(", e);
			}
		}
		return result;		
	}
	
	
	/**
	 * Check if the message is error
	 * @return bolean
	 */
	public boolean isError() {
		boolean result = false;

		if (has("error")) {
			try {
				result = getBoolean("error");
			} catch (JSONException e) {
				log.error("strange I can't read the error value ???!!! :(", e);
			}
		}

		return result;		
	}

	/**
	 * Check if the message is valid (contain the plusCount attribute)
	 * 
	 * @return boolean
	 */
	public boolean isValid() {
		return !isError()  && has("kind");
	}

}

