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

package org.hubiquitus.hapi.hStructures;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @version 0.4
 * This structure describe the location
 */

public class HFilterTemplate implements HJsonObj{

	private JSONObject hFilter = new JSONObject();
		
	public HFilterTemplate() {};
	
	public HFilterTemplate(JSONObject jsonObj){
		fromJSON(jsonObj);
	}
	
	/* HJsonObj interface */
	
	public JSONObject toJSON() {
		return hFilter;
	}
	
	public void fromJSON(JSONObject jsonObj) {
		if(jsonObj != null) {
			this.hFilter = jsonObj; 
		} else {
			this.hFilter = new JSONObject();
		}
	}
	
	public String getHType() {
		return "hFilter";
	}
	
	@Override
	public String toString() {
		return hFilter.toString();
	}
	


	/**
	 * @return chid : The channel id where the filter must be applied.
	 *  NULL if not defined. Mandatory
	 */
	public String getChid() {
		String chid;
		try {
			chid = hFilter.getString("chid");
		} catch (Exception e) {
			chid = null;			
		}
		return chid;
	}

	public void setChid(String chid) {
		try {
			if(chid == null) {
				hFilter.remove("chid");
			} else {
				hFilter.put("chid", chid);
			}
		} catch (JSONException e) {
		}
	}
	
	/**
	 * @return name : the name of the filter to apply. 
	 * This name will be the filter’s unique name in a channel. 
	 * NULL if not defined. Mandatory
	 */
	public String getName() {
		String name;
		try {
			name = hFilter.getString("name");
		} catch (Exception e) {
			name = null;			
		}
		return name;
	}

	public void setName(String name) {
		try {
			if(name == null) {
				hFilter.remove("name");
			} else {
				hFilter.put("name", name);
			}
		} catch (JSONException e) {
		}
	}
	

	/**
	 * @return name : An instance of a hmessage used as template for filtering upcoming messages. 
	 * If not defined then a radius or relevant flag must be set.
	 * This name will be the filter’s unique name in a channel. 
	 * NULL if not defined.
	 */
	public HMessage getTemplate() {
		HMessage template;
		try {
			template = new HMessage(hFilter.getJSONObject("name"));
		} catch (Exception e) {
			template = null;			
		}
		return template;
	}

	public void setTemplate(HMessage template) {
		try {
			if(template == null) {
				hFilter.remove("template");
			} else {
				hFilter.put("template", template);
			}
		} catch (JSONException e) {
		}
	}
	
	/**
	 * @return name : When specified, the upcoming messages must be located within ‘x’ 
	 * 	meters radius starting from the template location, ‘x’ being the value specified
	 * 0 if not defined
	 */
	public int getRadius() {
		int radius;
		try {
			radius = hFilter.getInt("radius");
		} catch (Exception e) {
			radius = 0;			
		}
		return radius;
	}

	public void setRadius(int radius) {
		try {
			if(radius <= 0) {
				hFilter.remove("radius");
			} else {
				hFilter.put("radius", radius);
			}
		} catch (JSONException e) {
		}
	}
	
	/**
	 * @return When specified to true, the upcoming messages must be relevant
	 * False if not defined
	 */
	public Boolean getRelevant() {
		Boolean relevant;
		try {
			relevant = hFilter.getBoolean("relevant");
		} catch (Exception e) {
			relevant = false;			
		}
		return relevant;
	}

	public void setRelevant(Boolean relevant) {
		try {
			if(relevant == null) {
				hFilter.remove("relevant");
			} else {
				hFilter.put("relevant", relevant);
			}
		} catch (JSONException e) {
		}
	}
	
}