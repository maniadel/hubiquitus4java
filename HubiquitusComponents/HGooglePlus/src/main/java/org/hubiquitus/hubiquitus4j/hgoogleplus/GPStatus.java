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

package org.hubiquitus.hubiquitus4j.hgoogleplus;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Describe a Google plus Status 'GPSatus' payload
 * 
 * @author MANI Adel
 *
 */
public class GPStatus extends JSONObject {

	final Logger log = LoggerFactory.getLogger(GPStatus.class);

	public GPStatus(JSONObject jsonObj) throws JSONException{
		super(jsonObj, JSONObject.getNames(jsonObj));
	}
	
	public GPStatus() throws JSONException{
	}

	
	/**
	 *              The Getters
	 */


	/**
	 * 
	 * @return plusOneCount
	 */
	public long getPlusOneCount() {
		long result = 0;

		if (has("plusOneCount")) {
			try {
				result = getLong("plusOneCount");
			} catch (JSONException e) {
				log.error("strange I can't read the plusOneCount value ???!!! :(", e);
			}
		}

		return result;		
	}

	/**
	 * 
	 * @return circleByCount
	 */
	public long getCircledByCount() {
		long result = 0;

		if (has("circledByCount")) {
			try {
				result = getLong("circledByCount");
			} catch (JSONException e) {
				log.error("strange I can't read the circledByCount value ???!!! :(", e);
			}
		}

		return result;		
	}

	/**
	 * 
	 * @return displayName
	 */
	public String getDisplayName() {
		String result = null;

		if (has("displayName")) {
			try {
				result = getString("displayName");
			} catch (JSONException e) {
				log.error("strange I can't read the displayName value ???!!! :(", e);
			}
		}

		return result;		
	}

	/**
	 * The Setters 
	 * 
	 */

	/**
	 * Set the plusOneCount of the GPStatus.
	 * @param plusOneCount
	 */
	public void setPlusOneCount(long plusOneCount) {
		try {
			this.put("plusOneCount", plusOneCount);
		} catch (JSONException e) {
			log.error("Can't update the Id Tweet  attribut",e);
		}
	}

	/**
	 * Set the circledByCount of the GPStatus.
	 * @param circledByCount
	 */
	public void setCircledByCount(long circledByCount) {
		try {
			this.put("circledByCount", circledByCount);
		} catch (JSONException e) {
			log.error("Can't update the Id Tweet  attribut",e);
		}
	}

	/**
	 * Set the displayName of the GPStatus.
	 * @param displayName
	 */
	public void setDisplayName(String displayName) {
		try {
			if(displayName == null || displayName.length()<= 0 ) {
				log.error("The attribute is null ");
			} else {
				this.put("displayName", displayName);
			}
		} catch (JSONException e) {
			log.error("Can't update the source attribut",e);
		}
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
		return !isError()  && has("plusOneCount");
	}

}
