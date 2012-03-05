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

package org.hubiquitus.persistence.impl;

import static org.junit.Assert.*;

import org.hubiquitus.hapi.persistence.impl.DataBaseMongoImpl;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class DataBaseMongoImplTest {

	private static DataBaseMongoImpl dataBaseMongoImpl;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dataBaseMongoImpl = new DataBaseMongoImpl();
	}

	@Test
	public void testSaveDocumentMessagePublishEntryString() {
		
//		try {
//			
//		String dbName = "testJunit";
//		MessagePublishEntry messagePublishEntry = new MessagePublishEntry();
//		messagePublishEntry.getAuthor();
//		messagePublishEntry.getPayload();
//		messagePublishEntry.getPublishedDate();	
//
//		//dataBaseMongoImpl.saveDocument(messagePublishEntry, dbName);
//	
//						
//		} catch (DBException e) {
//			logger.error(e.getMessage());
//		}
		
		assertTrue(true);
		//fail("Not yet implemented");
	}
	
	@Ignore
	@Test
	public void testSaveDocumentComplexPublishEntryString() {
		assertTrue(true);
		//fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testFindDocumentsByKeys() {
		assertTrue(true);
		//fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testSetDataBaseConfigMap() {
		assertTrue(true);
		//fail("Not yet implemented");
	}
}