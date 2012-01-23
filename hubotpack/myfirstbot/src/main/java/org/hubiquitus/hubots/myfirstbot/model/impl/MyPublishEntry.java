package org.hubiquitus.hubots.myfirstbot.model.impl;

import org.hubiquitus.hapi.model.PublishEntry;
import org.hubiquitus.hapi.model.impl.MessagePublishEntry;

public class MyPublishEntry extends MessagePublishEntry implements PublishEntry {

	private String mydata;
	
	public MyPublishEntry(String string) {
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
