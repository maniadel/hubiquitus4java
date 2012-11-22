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

package org.hubiquitus.hfacebook.publics;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A such object is used to know the activity on a FB Page.
 * Note we consider that a such object is invalid if there is no likes
 */
public class FBStatus extends JSONObject {

	final Logger logger = LoggerFactory.getLogger(FBStatus.class);

	public FBStatus(JSONObject jsonObj) throws JSONException{
		super(jsonObj, JSONObject.getNames(jsonObj));
	}
	
	public long getLikes() {
		long result = 0;
		
		if (has("likes")) {
			try {
				result = getLong("likes");
			} catch (JSONException e) {
				logger.error("strange I can't read the likes value ???!!! :(", e);
			}
		}
		
		return result;		
	}

	public long getTalkingAboutCount() {
		long result = 0;
		
		if (has("talking_about_count")) {
			try {
				result = getLong("talking_about_count");
			} catch (JSONException e) {
				logger.error("strange I can't read the talking_about_count value ???!!! :(", e);
			}
		}
		
		return result;		
	}

	public long getCheckins() {
		long result = 0;
		
		if (has("checkins")) {
			try {
				result = getLong("checkins");
			} catch (JSONException e) {
				logger.error("strange I can't read the checkins value ???!!! :(", e);
			}
		}
		
		return result;		
	}
	
	public boolean isError() {
		boolean result = false;
		
		if (has("error")) {
			try {
				result = getBoolean("error");
			} catch (JSONException e) {
				logger.error("strange I can't read the error value ???!!! :(", e);
			}
		}
		
		return result;		
	}

	public boolean isValid() {
		return !isError()  && has("likes");
	}
	
	
}
