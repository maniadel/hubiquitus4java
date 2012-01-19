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

/**
 *
 */
package org.hubiquitus.hapi.model;

import java.util.ArrayList;

/**
 * Payload to publish
 * 
 * @author m.ma
 */
public abstract class PayloadPublishEntry<T extends PublishEntry> implements PublishEntry {
	/**
	 * List of data 
	 */
	private ArrayList<T> datas;
	
	/**
	 * Constructor with default values
	 */
	public PayloadPublishEntry() {
	}

	/**
	 * getter datas
	 * @return datas
	 */
	public ArrayList<T> getDatas() {
		if (datas == null) {
			datas = new ArrayList<T>();
		}
		return datas;
	}


	/**
	 * setter 
	 * @param datas the datas to set
	 */
	public void setDatas(ArrayList<T> datas) {
		this.datas = datas;
	}

	/**
	 * Add a weekStatData to the list of weekStatDatas
	 * @param weekStatData the weekStatData to add
	 */
	public void addData(T data) {
		if (data != null) {
			this.getDatas().add(data);			
		}
	}
	
	@Override
	public String entryToJsonFormat() {
		String result = null;
		try {
			result = ObjectMapperInstanceDomainKey.PUBLISH_ENTRY.getMapper().writeValueAsString(this.getDatas());
		} catch (Exception e) {
			// Do nothing
		}
		return result;
	}
}
