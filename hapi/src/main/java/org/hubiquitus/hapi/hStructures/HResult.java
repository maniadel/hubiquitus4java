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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author j.desousag
 * @version 0.5 hAPI result. For more info, see Hubiquitus reference
 */

public class HResult extends JSONObject {


	public HResult() {super();
	}

	public HResult(JSONObject jsonObj) throws JSONException {
		super(jsonObj, JSONObject.getNames(jsonObj));
	}


	/* Getters & Setters */

	/**
	 * Mandatory. Execution status.
	 * 
	 * @return status. NULL if undefined
	 */
	public ResultStatus getStatus() {
		ResultStatus reqid;
		try {
			reqid = ResultStatus.constant(this.getInt("status"));
		} catch (Exception e1) {
			reqid = null;
		}
		return reqid;
	}

	public void setStatus(ResultStatus status) {
		try {
			if (status == null) {
				this.remove("status");
			} else {
				this.put("status", status.value());
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * if result type is a JSONObject
	 * 
	 * @return result of a command operation or a subscriptions operation.
	 */
	public JSONObject getResultAsJSONObject() {
		JSONObject result;
		try {
			result = this.getJSONObject("result");
		} catch (JSONException e) {
			result = null;
		}
		return result;
	}

	/**
	 * if result type is a JSONArray
	 * 
	 * @return
	 */
	public JSONArray getResultAsJSONArray() {
		JSONArray result;
		try {
			result = this.getJSONArray("result");
		} catch (JSONException e) {
			result = null;
		}
		return result;
	}

	/**
	 * if result type is a String
	 * 
	 * @return result of a command operation or a subscriptions operation.
	 */
	public String getResultAsString() {
		String result;
		try {
			result = this.getString("result");
		} catch (JSONException e) {
			result = null;
		}
		return result;
	}

	/**
	 * if result type is Boolean
	 * 
	 * @return
	 */
	public Boolean getResultAsBoolean() {
		Boolean result;
		try {
			result = this.getBoolean("result");
		} catch (JSONException e) {
			result = null;
		}
		return result;
	}

	/**
	 * if result type is Integer
	 * 
	 * @return
	 */
	public Integer getResultAsInt() {
		Integer result;
		try {
			result = this.getInt("result");
		} catch (JSONException e) {
			result = null;
		}
		return result;
	}

	/**
	 * if result type is Double
	 * 
	 * @return
	 */
	public Double getResultAsDouble() {
		Double result;
		try {
			result = this.getDouble("result");
		} catch (JSONException e) {
			result = null;
		}
		return result;
	}

	/**
	 * if result type is Long
	 * 
	 * @return
	 */
	public Long getResultAsLong() {
		Long result;
		try {
			result = this.getLong("result");
		} catch (JSONException e) {
			result = null;
		}
		return result;
	}

	/**
	 * if result type is JSONObject
	 * 
	 * @param result
	 */
	public void setResult(JSONObject result) {
		try {
			if (result == null) {
				this.remove("result");
			} else {
				this.put("result", result);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * if result type is JSONArray
	 * 
	 * @param result
	 */
	public void setResult(JSONArray result) {
		try {
			if (result == null) {
				this.remove("result");
			} else {
				this.put("result", result);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * if result type is HJsonObj (i.e. HAlert, HAck ... )
	 * 
	 * @param result
	 */
	public void setResult(HJsonObj result) {
		try {
			if (result == null) {
				this.remove("result");
			} else {
				this.put("result", result.toJSON());
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * if result type is String
	 * 
	 * @param result
	 */
	public void setResult(String result) {
		try {
			if (result == null) {
				this.remove("result");
			} else {
				this.put("result", result);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * if result type is Boolean
	 * 
	 * @param result
	 */
	public void setResult(Boolean result) {
		try {
			if (result == null) {
				this.remove("result");
			} else {
				this.put("result", result);
			}
		} catch (JSONException e) {
		}
	}

	/**
	 * if result type is int
	 * 
	 * @param result
	 */
	public void setResult(int result) {
		try {
			this.put("result", result);
		} catch (JSONException e) {
		}
	}

	/**
	 * if result type is double
	 * 
	 * @param result
	 */
	public void setResult(double result) {
		try {
			this.put("result", result);
		} catch (JSONException e) {
		}
	}

	/**
	 * if result type is long
	 * 
	 * @param result
	 */
	public void setResult(long result) {
		try {
			this.put("result", result);
		} catch (JSONException e) {
		}
	}

}
