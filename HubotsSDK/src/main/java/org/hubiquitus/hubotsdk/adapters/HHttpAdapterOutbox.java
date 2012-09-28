package org.hubiquitus.hubotsdk.adapters;

import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.AdapterOutbox;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HHttpAdapterOutbox extends AdapterOutbox {
	final Logger logger = LoggerFactory.getLogger(HHttpAdapterOutbox.class);
	
	public HHttpAdapterOutbox(){
		super();
	}
	@Override
	public void sendMessage(HMessage message) {
		logger.info("message send to http.");
	}

	@Override
	public void setProperties(JSONObject properties) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
