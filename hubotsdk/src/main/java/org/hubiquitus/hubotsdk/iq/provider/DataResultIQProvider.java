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


import java.util.Date;

import org.hubiquitus.hapi.model.impl.DataResultEntry;
import org.hubiquitus.hapi.model.impl.MessagePublishEntry;
import org.jivesoftware.smackx.packet.PrivateData;
import org.jivesoftware.smackx.provider.PrivateDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParser;
       

/**
 * IQ provider for DataResultEntry
 * @author o.chauvie
 *
 */
public class DataResultIQProvider implements PrivateDataProvider{

	
	Logger logger = LoggerFactory.getLogger(DataResultIQProvider.class);
	
	/**
	 * Constructor
	 */
	public DataResultIQProvider() {
		super();
	}
	
	@Override
	public PrivateData parsePrivateData(XmlPullParser parser) throws Exception {
		
        DataResultEntry dataResultEntry = new DataResultEntry();
        
        boolean done = false;
        String name = "";
        while (!done) {
            int eventType = parser.next();
            
            if (eventType == XmlPullParser.START_TAG) {
            	name = parser.getName();

            }
            if(eventType == XmlPullParser.TEXT) {
            	String text = parser.getText();

            	
                if (DataResultEntry.TYPE.equals(name)) {
                	dataResultEntry.setType(text);
                }
                
                if (DataResultEntry.AUTHOR.equals(name)) {
                	dataResultEntry.setAuthor(text);
                }
                
                if (DataResultEntry.PUBLISHER.equals(name)) {
                	dataResultEntry.setPublisher(text);
                }
                
                if (DataResultEntry.PUBLISHEDDATE.equals(name)) {
            		Date date = MessagePublishEntry.getFormatedPublishedDate(text);
            		if (date==null) {
						logger.error("Can't parse date " + text + " in <" + DataResultEntry.PUBLISHEDDATE + ">.");
					}
               		dataResultEntry.setPublishedDate(date);
                }
                
                if (DataResultEntry.PAYLOAD.equals(name)) {
                	dataResultEntry.setPayload(text);
                }
                
                if (DataResultEntry.HEADER.equals(name)) {
                	dataResultEntry.setHeader(text);
                }
                
                if (DataResultEntry.LOCATION.equals(name)) {
                	dataResultEntry.setLocation(text);
                }
                      
            } 
            if (eventType == XmlPullParser.END_TAG) {
                if (parser.getName().equals(DataResultEntry.ELEMENTNAME)) {
                    done = true;
                }
            }
        }
        return dataResultEntry;
	}	
  
}