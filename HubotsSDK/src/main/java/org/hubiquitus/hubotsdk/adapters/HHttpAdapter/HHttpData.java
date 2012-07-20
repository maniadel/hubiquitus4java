/*
 * Copyright (c) Novedia Group 2012.
 *
 *     This file is part of Hubiquitus.
 *
 *     Hubiquitus is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Hubiquitus is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Hubiquitus.  If not, see <http://www.gnu.org/licenses/>.
 */


package org.hubiquitus.hubotsdk.adapters.HHttpAdapter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.hubiquitus.hapi.hStructures.HJsonObj;
import org.json.JSONException;
import org.json.JSONObject;

public class HHttpData implements HJsonObj {
	private Map<String, HHttpAttachement> attachments = null;

	private byte[] rawBody = null;
	private String method = null;
	private String queryArgs = null;
	private String queryPath = null;
	
	public HHttpData() {};
	
	public HHttpData(JSONObject jsonObj){
		fromJSON(jsonObj);
	}
	
	/* HJsonObj interface */
	public JSONObject toJSON() {
		JSONObject jsonObj = new JSONObject();
		
		JSONObject jsonAttachements = new JSONObject();
		for (String key : attachments.keySet()) {
			try {
				jsonAttachements.put(key, attachments.get(key).toJSON());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			jsonObj.put("attachements", jsonAttachements);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (this.rawBody != null) {
			try {
				jsonObj.put("rawBody", Base64.encodeBase64String(this.rawBody));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			jsonObj.put("method", method);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			jsonObj.put("queryArgs", queryArgs);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			jsonObj.put("queryPath", queryPath);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void fromJSON(JSONObject jsonObj) {
		if (jsonObj != null) {
			try {
				this.method = jsonObj.getString("method");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				this.queryArgs = jsonObj.getString("queryArgs");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				this.queryPath = jsonObj.getString("queryPath");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String encodedRawBody;
			try {
				encodedRawBody = jsonObj.getString("rawBody");
				if (encodedRawBody != null) {
					this.rawBody = Base64.decodeBase64(encodedRawBody);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			JSONObject jsonAttachements = null;
			try {
				jsonAttachements = jsonObj.getJSONObject("attachements");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (jsonAttachements != null) {
				String[] keys = JSONObject.getNames(jsonAttachements);
				this.attachments = new HashMap<String, HHttpAttachement>();
				for (int i = 0; i < keys.length; i++) {
					HHttpAttachement hattachement;
					try {
						hattachement = new HHttpAttachement(jsonAttachements.getJSONObject(keys[i]));
						this.attachments.put(keys[i], hattachement);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			}
		}
	}
	
	public String getHType() {
		return "hhttpdata";
	}
	
	/* getters and setters */
	public Map<String, HHttpAttachement> getAttachments() {
		return attachments;
	}

	public void setAttachments(Map<String, HHttpAttachement> attachments) {
		this.attachments = attachments;
	}

	public byte[] getRawBody() {
		return rawBody;
	}

	public void setRawBody(byte[] rawBody) {
		this.rawBody = rawBody;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getQueryArgs() {
		return queryArgs;
	}

	public void setQueryArgs(String queryArgs) {
		this.queryArgs = queryArgs;
	}

	public String getQueryPath() {
		return queryPath;
	}

	public void setQueryPath(String queryPath) {
		this.queryPath = queryPath;
	}

}
