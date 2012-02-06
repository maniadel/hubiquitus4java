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

package org.hubiquitus.hapi.model.impl;

import java.util.HashMap;
import java.util.Map;

public enum DbOperator {
	
	EQUAL("") {
		public void appendQuery(Map<String, Object> filtersContainer, String filter, Object value) {
			filtersContainer.put(filter, value);
		}
	},
	SUP("$gt"),
	INF("$lt"),
	SUP_EQUAL("$gte"),
	INF_EQUAL("$lte");

	private String code;

	private DbOperator(String code) {
		this.code = code;
	}
	
	public void appendQuery(Map<String, Object> filtersContainer, String filter, Object value) {
		Map<String, Object> subFilter = new HashMap<String, Object>();
		subFilter.put(code, value);
		filtersContainer.put(filter, subFilter);
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
}

