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
 * @version 0.3 hAPI result. For more info, see Hubiquitus reference
 */

public class HResult implements HJsonObj {

	private JSONObject hresult = new JSONObject();

	public HResult() {
	}

	public HResult(JSONObject jsonObj) {
		this.fromJSON(jsonObj);
	}

	/* HJsonObj interface */

	public JSONObject toJSON() {
		return this.hresult;
	}

	public String getHType() {
		return "hresult";
	}

	public void fromJSON(JSONObject jsonObj) {
		if (jsonObj != null) {
			this.hresult = jsonObj;
		} else {
			this.hresult = new JSONObject();
		}
	}

	@Override
	public String toString() {
		return hresult.toString();
	}

	/**
	 * Check are made on : and status.
	 * 
	 * @param HResult
	 * @return Boolean
	 */
	public boolean equals(HResult obj) {
		if (obj.getStatus().value() != this.getStatus().value())
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		return hresult.hashCode();
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
			reqid = ResultStatus.constant(hresult.getInt("status"));
		} catch (Exception e1) {
			reqid = null;
		}
		return reqid;
	}

	public void setStatus(ResultStatus status) {
		try {
			if (status == null) {
				hresult.remove("status");
			} else {
				hresult.put("status", status.value());
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
			result = hresult.getJSONObject("result");
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
			result = hresult.getJSONArray("result");
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
			result = hresult.getString("result");
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
			result = hresult.getBoolean("result");
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
			result = hresult.getInt("result");
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
			result = hresult.getDouble("result");
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
			result = hresult.getLong("result");
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
				hresult.remove("result");
			} else {
				hresult.put("result", result);
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
				hresult.remove("result");
			} else {
				hresult.put("result", result);
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
				hresult.remove("result");
			} else {
				hresult.put("result", result.toJSON());
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
				hresult.remove("result");
			} else {
				hresult.put("result", result);
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
				hresult.remove("result");
			} else {
				hresult.put("result", result);
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
			hresult.put("result", result);
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
			hresult.put("result", result);
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
			hresult.put("result", result);
		} catch (JSONException e) {
		}
	}

}
