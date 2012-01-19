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
package org.hubiquitus.hubotsdk.iq.provider;


import org.hubiquitus.hapi.model.impl.DataResultEntry;
import org.hubiquitus.hapi.model.impl.PayloadResultEntry;
import org.jivesoftware.smackx.PrivateDataManager;
import org.jivesoftware.smackx.packet.PrivateData;
import org.jivesoftware.smackx.provider.PrivateDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParser;

public class PayloadResultIQProvider implements PrivateDataProvider{

	Logger logger = LoggerFactory.getLogger(PayloadResultIQProvider.class);
	
	/**
	 * Constructor
	 */
	public PayloadResultIQProvider() {
		super();
	}
	
	@Override
	public PrivateData parsePrivateData(XmlPullParser parser) throws Exception {
		
		 PayloadResultEntry payloadResultEntry = new PayloadResultEntry();
	        
	        boolean done = false;
	        while (!done) {
	            int eventType = parser.next();
	            if (eventType == XmlPullParser.START_TAG) {
	                String elementName = parser.getName();
	                String namespace = parser.getNamespace();
	             
	                if (DataResultEntry.ELEMENTNAME.equals(elementName)) {
	                	DataResultIQProvider dataResultIQProvider = (DataResultIQProvider) PrivateDataManager.getPrivateDataProvider(elementName, namespace);
	                	if (dataResultIQProvider!=null) {
		                	DataResultEntry dataResultEntry = (DataResultEntry) dataResultIQProvider.parsePrivateData(parser);
		                	if (dataResultEntry!=null) {
		                		payloadResultEntry.addResult(dataResultEntry);
		                	} else {
		                		logger.warn("Data result parsing error for element: " + elementName + " - " + namespace);
		                	}
	                	} else {
	                		logger.warn("No data provider found for element: " + elementName + " - " + namespace);
	                	}
	                }
	            }    
	            else if (eventType == XmlPullParser.END_TAG) {
	                if (parser.getName().equals("results")) {
	                    done = true;
	                }
	            }
	        }
	        
	        return payloadResultEntry;
		}
}
