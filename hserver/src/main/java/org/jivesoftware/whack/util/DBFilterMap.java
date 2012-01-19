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

package org.jivesoftware.whack.util;

import org.hubiquitus.hapi.model.impl.KeyRequest;

/**
 * @author l.chong-wing
 *
 */
public class DBFilterMap {
	
	/**
	 * Data filter field of {@link KeyRequest}
	 */
	private String filter;
	
	/**
	 * KeyRequest object
	 */
	private KeyRequest key;
	
	/**
	 * Constructor
	 * @param filter
	 * @param key
	 */
	public DBFilterMap(String filter, KeyRequest key) {
		super();
		this.filter = filter;
		this.key = key;
	}

	/**
	 * Getter filter
	 * @return filter the filter
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * Setter filter
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * Getter key
	 * @return key the key
	 */
	public KeyRequest getKey() {
		return key;
	}

	/**
	 * Setter key
	 * @param key the key to set
	 */
	public void setKey(KeyRequest key) {
		this.key = key;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DBMap [filter=" + filter + ", key=" + key.toString() + "]";
	}

	
	
	
}
