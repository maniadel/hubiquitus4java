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
public class DBSorterMapList {
	
	/**
	 * List of DBMap Objects
	 */
	List<DBSorterMap> dbMapList;

	/**
	 * @param dbMapList
	 */
	public DBSorterMapList() {
		super();
		this.dbMapList = new ArrayList<DBSorterMap>();
	}
	
	/**
	 * Add a DBMap object to the list
	 * @param dbMap
	 */
	public void addDBMapObject(DBSorterMap dbMap){
		dbMapList.add(dbMap);
	}
	
	/**
	 * Return all the DBMap object with the same sorter
	 * @param sorter the sorter
	 * @return the list of DBMap objects with the same sorter
	 */
	public List<DBSorterMap> GetDBMapObjects(String filter){
		List<DBSorterMap> results = new ArrayList<DBSorterMap>();
		for(int i=0; i<dbMapList.size(); i++){
			if(dbMapList.get(i).getSorter().equals(filter)){
				results.add(dbMapList.get(i));
			}
		}
		return results;
	}
	
	/**
	 * Delete DBMap object with the desired sorter
	 * @param sorter the sorter associated to the object
	 */
	public void deleteDBMapObject(String sorter){
		int i = countDBMapObject()-1;
		while(i>=0){
			if(dbMapList.get(i).getSorter().equals(sorter)){
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
	public List<DBSorterMap> getDbMapList() {
		return dbMapList;
	}

	/**
	 * Setter dbMapList
	 * @param dbMapList the dbMapList to set
	 */
	public void setDbMapList(List<DBSorterMap> dbMapList) {
		this.dbMapList = dbMapList;
	}
	
	
}
