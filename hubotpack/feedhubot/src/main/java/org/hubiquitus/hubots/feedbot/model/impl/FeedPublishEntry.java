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
package org.hubiquitus.hubots.feedbot.model.impl;

import org.hubiquitus.hapi.model.PublishEntry;
import org.hubiquitus.hapi.model.impl.MessagePublishEntry;

public class FeedPublishEntry extends MessagePublishEntry implements PublishEntry {

	private String mydata;
	
	public FeedPublishEntry(String string) {
		super();
		mydata = string;
	}
	
	@Override
	public String getEntryType() {
		return null;
	}

	@Override
	public String entryToJsonFormat() {
		String divider = ",";
		String json = "";
		
		json = "{ \"" + MSGID + "\" : \"" + getMsgid() + "\"" + divider;

        json = json + " \"" + PAYLOAD + "\" : \"" + mydata + "\"";
        
        json = json + "}";
    	
    	return json;
	}

}
