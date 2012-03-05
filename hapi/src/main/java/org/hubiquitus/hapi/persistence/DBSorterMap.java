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

package org.hubiquitus.hapi.persistence;

import org.hubiquitus.hapi.model.impl.SortRequest;

/**
 * @author l.chong-wing
 *
 */
public class DBSorterMap {
	
	/**
	 * Data filter field of {@link SortRequest}
	 */
	private String sorter;
	
	/**
	 * SortRequest object
	 */
	private SortRequest sort;
	
	/**
	 * Constructor
	 * @param sorter
	 * @param sort
	 */
	public DBSorterMap(String sorter, SortRequest sort) {
		super();
		this.sorter = sorter;
		this.sort = sort;
	}

	/**
	 * Getter sorter
	 * @return sorter the sorter
	 */
	public String getSorter() {
		return sorter;
	}

	/**
	 * Setter filter
	 * @param sorter the filter to set
	 */
	public void setSorter(String sorter) {
		this.sorter = sorter;
	}

	/**
	 * Getter sort
	 * @return sort the sort
	 */
	public SortRequest getSort() {
		return sort;
	}

	/**
	 * Setter sort
	 * @param sort the sort to set
	 */
	public void setSort(SortRequest sort) {
		this.sort = sort;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DBMap [Sorter=" + sorter + ", sort=" + sort.toString() + "]";
	}

	
	
	
}
