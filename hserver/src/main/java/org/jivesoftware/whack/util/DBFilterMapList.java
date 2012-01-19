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
package org.jivesoftware.whack.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author l.chong-wing
 *
 */
public class DBFilterMapList {
	
	/**
	 * List of DBMap Objects
	 */
	List<DBFilterMap> dbMapList;

	/**
	 * @param dbMapList
	 */
	public DBFilterMapList() {
		super();
		this.dbMapList = new ArrayList<DBFilterMap>();
	}
	
	/**
	 * Add a DBMap object to the list
	 * @param dbMap
	 */
	public void addDBMapObject(DBFilterMap dbMap){
		dbMapList.add(dbMap);
	}
	
	/**
	 * Return all the DBMap object with the same filter
	 * @param filter the filter
	 * @return the list of DBMap objects with the same filter
	 */
	public List<DBFilterMap> GetDBMapObjects(String filter){
		List<DBFilterMap> results = new ArrayList<DBFilterMap>();
		for(int i=0; i<dbMapList.size(); i++){
			if(dbMapList.get(i).getFilter().equals(filter)){
				results.add(dbMapList.get(i));
			}
		}
		return results;
	}
	
	/**
	 * Delete DBMap object with the desired filter
	 * @param filter the filter associated to the object
	 */
	public void deleteDBMapObject(String filter){
		int i = countDBMapObject()-1;
		while(i>=0){
			if(dbMapList.get(i).getFilter().equals(filter)){
				dbMapList.remove(i);
			}
			i--;
		}
	}
	
	/**
	 * 
	 * @return the number of DBMap objects
	 */
	public int countDBMapObject(){
		return dbMapList.size();
	}

	/**
	 * Getter dbMapList
	 * @return the dbMapList
	 */
	public List<DBFilterMap> getDbMapList() {
		return dbMapList;
	}

	/**
	 * Setter dbMapList
	 * @param dbMapList the dbMapList to set
	 */
	public void setDbMapList(List<DBFilterMap> dbMapList) {
		this.dbMapList = dbMapList;
	}
	
	
}
