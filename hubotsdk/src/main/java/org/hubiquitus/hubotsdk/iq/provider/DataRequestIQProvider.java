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

import org.hubiquitus.hapi.model.impl.DataRequestEntry;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.jivesoftware.smackx.PrivateDataManager;
import org.jivesoftware.smackx.packet.DefaultPrivateData;
import org.jivesoftware.smackx.packet.PrivateData;
import org.jivesoftware.smackx.provider.PrivateDataProvider;
import org.xmlpull.v1.XmlPullParser;

/**
 * IQ provider for DataRequestEntry
 * @author o.chauvie
 *
 */
public class DataRequestIQProvider implements IQProvider {
	
	/**
	 * An IQ packet to hold PrivateData GET results.
	 */
	private static class PrivateDataResult extends IQ {

	    private PrivateData privateData;

	    PrivateDataResult(PrivateData privateData) {
	        this.privateData = privateData;
	    }

	    @SuppressWarnings("unused")
		public PrivateData getPrivateData() {
	        return privateData;
	    }

	    public String getChildElementXML() {
	        StringBuffer buf = new StringBuffer();
	        buf.append("<query xmlns=\"" + DataRequestEntry.NAMESPACE+ "\">");
	        if (privateData != null) {
	            buf.append(privateData.toXML());
	        }
	        buf.append("</query>");
	        return buf.toString();
	    }
	}
	
    public IQ parseIQ(XmlPullParser parser) throws Exception {
        PrivateData privateData = null;
        boolean done = false;
        while (!done) {
            int eventType = parser.next();
            if (eventType == XmlPullParser.START_TAG) {
                String elementName = parser.getName();
                String namespace = parser.getNamespace();
                
                // See if any objects are registered to handle this private data type.
                PrivateDataProvider provider = PrivateDataManager.getPrivateDataProvider(elementName, namespace); 
                
                // If there is a registered provider, use it.
                if (provider != null) {
                    privateData = provider.parsePrivateData(parser);
                }
                // Otherwise, use a DefaultPrivateData instance to store the private data.
                else {
                    DefaultPrivateData data = new DefaultPrivateData(elementName, namespace);
                    boolean finished = false;
                    while (!finished) {
                        int event = parser.next();
                        if (event == XmlPullParser.START_TAG) {
                            String name = parser.getName();
                            // If an empty element, set the value with the empty string.
                            if (parser.isEmptyElementTag()) {
                                data.setValue(name,"");
                            }
                            // Otherwise, get the the element text.
                            else {
                                event = parser.next();
                                if (event == XmlPullParser.TEXT) {
                                    String value = parser.getText();
                                    data.setValue(name, value);
                                }
                            }
                        }
                        else if (event == XmlPullParser.END_TAG) {
                            if (parser.getName().equals(elementName)) {
                                finished = true;
                            }
                        }
                    }
                    privateData = data;
                }
            }
            else if (eventType == XmlPullParser.END_TAG) {
                if (parser.getName().equals("query")) {
                    done = true;
                }
            }
        }
        IQ result = new PrivateDataResult(privateData);
        return result;
    }
    
    
}



