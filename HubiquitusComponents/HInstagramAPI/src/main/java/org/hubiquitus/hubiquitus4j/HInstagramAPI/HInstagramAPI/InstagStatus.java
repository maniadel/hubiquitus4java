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


package org.hubiquitus.hubiquitus4j.HInstagramAPI.HInstagramAPI;


import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Adel MANI
 *
 */
public class InstagStatus extends JSONObject {

	final Logger log = LoggerFactory.getLogger(InstagStatus.class);

	public InstagStatus(JSONObject jsonObj) throws JSONException{
		super(jsonObj, JSONObject.getNames(jsonObj));
	}

	/**
	 * 
	 * @return long
	 */
	public long getMediaCount() {
		long result = 0;

		if (has("data")) {
			try {
				result = getJSONObject("data").getLong("media_count");
			} catch (JSONException e) {
				log.error("strange I can't read the media_count value ???!!! :(", e);
			}
		}

		return result;		
	}

	/**
	 * 
	 * @return String
	 */
	public String getName() {
		String result = null;

		if (has("data")) {
			try {
				result = getJSONObject("data").getString("name");
			} catch (JSONException e) {
				log.error("strange I can't read the name value ???!!! :(", e);
			}
		}

		return result;		
	}

	

	/**
	 * Check if the received object is not an error
	 * @return bolean
	 */
	public boolean isError() {
		boolean result = false;

		try {
			if (getJSONObject("meta").has("error_type")) {
				try {
					result = getJSONObject("meta").getBoolean("error_type");
				} catch (JSONException e) {
					log.error("strange I can't read the error value ???!!! :(", e);
				}
			}
		} catch (JSONException e) {
			log.error("strange I can't read the meta object value ???!!! :(", e);
		}

		return result;		
	}
	/**
	 * check if the received json is valid
	 * @return boolean
	 */
	public boolean isValid() {
		return !isError()  && has("data");
	}


}
