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

package org.hubiquitus.persistence;

import org.hubiquitus.hapi.model.impl.DataRequestEntry;
import org.hubiquitus.hapi.model.impl.PayloadResultEntry;
import org.hubiquitus.persistence.exeption.DBException;
import org.json.simple.JSONObject;




public interface DataBase {

	/**
	 * Save message type of ComplexPublishEntry
	 * @param jSONObject
	 * @throws DBException
	 */
	public void saveComplexPublishEntry(JSONObject jSONObject) throws DBException ;
	
	/**
	 * Save message type of messagePublishEntry
	 * @param messagePublishEntry
	 * @throws DBException
	 */
	public void saveMessagePublishEntry(JSONObject jSonObject) throws DBException ;
	
	/**
	 * 
	 * @param lists
	 * @param dataRequestEntry
	 * @return
	 * @throws DBException
	 */
	public PayloadResultEntry findDocumentsByKeys(DataRequestEntry dataRequestEntry) throws DBException ;
	
	public PayloadResultEntry groupDocumentsByKeys(DataRequestEntry dataRequestEntry)  throws DBException;
	
	public PayloadResultEntry distinctDocumentsByKeys(DataRequestEntry dataRequestEntry)  throws DBException;
	
	void updateDocumentByKeys(JSONObject jsonObject) throws DBException;
}