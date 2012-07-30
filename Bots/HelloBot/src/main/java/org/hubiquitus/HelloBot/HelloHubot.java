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


package org.hubiquitus.HelloBot;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.hubiquitus.hapi.client.HClient;
import org.hubiquitus.hapi.hStructures.HCommand;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hapi.util.HJsonDictionnary;
import org.hubiquitus.hubotsdk.Actor;
import org.json.JSONException;
import org.json.JSONObject;



public class HelloHubot extends Actor{

	private static Logger logger = Logger.getLogger(HelloHubot.class);
	
	public static void main(String[] args) throws Exception{
		BasicConfigurator.configure();
		HelloHubot hubot = new HelloHubot();
		hubot.start();
	}
	
	@Override
	public void init(HClient hclient) {
		initialized();
	}
	
	@Override
	protected void inProcessMessage(HMessage messageIncoming) {
		HMessage message = new HMessage();
		message.setType("hello");
		JSONObject jsonObj = messageIncoming.getPayload().toJSON();
		String name = "Hello ";
		try {
			 name += jsonObj.getString("text");
		} catch (JSONException e) {
			logger.error(e.toString());
		}
		HJsonDictionnary payload = new HJsonDictionnary();
		payload.put("text", name);
		message.setPayload(payload);
		message.setChid(messageIncoming.getPublisher());
		put("hubotAdapter",message);	
	}
	
	protected void inProcessCommand(HCommand commandIncoming) {
		logger.debug("not supported");
	}
	

}
