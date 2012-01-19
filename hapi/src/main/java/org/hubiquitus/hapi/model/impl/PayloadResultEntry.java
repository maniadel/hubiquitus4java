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
package org.hubiquitus.hapi.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smackx.packet.PrivateData;

/**
 * Payload result entry
 * @author o.chauvie
 */
public class PayloadResultEntry  implements PrivateData {

	public static final String NAMESPACE="hubiquitus:payloadresultentry";
	public static final String ELEMENTNAME="results";
	
	public static final String ENTRY_TYPE = "hDataResult";
	public static final String TYPE = "type";
	public static final String AUTHOR = "author";
	public static final String PUBLISHER = "publisher";
	public static final String PUBLISHEDDATE = "published";
	public static final String PAYLOAD = "payload";
	
	/**
	 * The type of the message payload 
	 */
	private List<DataResultEntry> results = new ArrayList<DataResultEntry>();
	
	/**
	 * getter results
	 * @return the results
	 */
	public List<DataResultEntry> getResults() {
		return results;
	}
	
	/**
	 * setter results
	 * @param results the results to set
	 */
	public void setResults(List<DataResultEntry> results) {
		this.results = results;
	}
	
	/**
	 * Add result
	 * @param result the result to add
	 */
	public void addResult(DataResultEntry result) {
		this.results.add(result);
	}

	@Override
	public String getElementName() {
		return PayloadResultEntry.ELEMENTNAME;
	}

	@Override
	public String getNamespace() {
		return PayloadResultEntry.NAMESPACE;
	}

	@Override
	public String toXML() {
		StringBuffer buf = new StringBuffer();
        	buf.append("<" + ELEMENTNAME + " xlmns=\"" + getNamespace() + "\" >");
        	for(int i=0; i<results.size(); i++){
        		DataResultEntry dataResultEntry = results.get(i);
        		buf.append(dataResultEntry.toXML());
        	}
        	buf.append("</" + ELEMENTNAME + ">");

        return buf.toString();
	}
}