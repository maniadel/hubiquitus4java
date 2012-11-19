/*
 * Copyright (c) Novedia Group 2012.
 *
 *    This file is part of Hubiquitus
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy
 *    of this software and associated documentation files (the "Software"), to deal
 *    in the Software without restriction, including without limitation the rights
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *    of the Software, and to permit persons to whom the Software is furnished to do so,
 *    subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies
 *    or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *    INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *    PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 *    FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 *    ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *    You should have received a copy of the MIT License along with Hubiquitus.
 *    If not, see <http://opensource.org/licenses/mit-license.php>.
 */

package org.hubiquitus.hubotsdk.topology;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTopology extends HActor {
	final Logger logger = LoggerFactory.getLogger(HTopology.class);
	public HTopology(){
		super();
	}
	
	public HTopology(JSONObject jsonObj) throws JSONException{
		super(jsonObj);
	}
	
	public HTopology(String jsonString) throws JSONException{
		super(jsonString);
	}
	
	
	public JSONArray getAdapters(){
		JSONArray adapters;
		try {
			adapters = this.getJSONArray("adapters");
		} catch (Exception e) {
			adapters = null;
		}
		return adapters;
	}
	
	public void setAdapters(JSONArray adapters){
		try {
			if(adapters == null){
				this.remove("adapters");
			}else{
				this.put("adapters",adapters);
			}
		} catch (JSONException e) {
			logger.debug("Can not set the adapters attribute.", e);
		}
	}

    public JSONObject getProperties(){
        try {
            return getJSONObject("properties");
        } catch (Exception e) {
            return null;
        }
    }

    public void setProperties(JSONObject properties){
        try {
            if(properties == null){
                this.remove("properties");
            }else{
                this.put("properties",properties);
            }
        } catch (JSONException e) {
            logger.error("Can not set the properties attribute.", e);
            // TODO throw e
        }
    }
	
}
