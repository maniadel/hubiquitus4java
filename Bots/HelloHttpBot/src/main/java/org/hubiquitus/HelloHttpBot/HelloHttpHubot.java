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

package org.hubiquitus.HelloHttpBot;

import org.hubiquitus.hapi.exceptions.MissingAttrException;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.Hubot;
import org.hubiquitus.hubotsdk.adapters.HHttpAdapter.HHttpData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class HelloHttpHubot extends Hubot{

	final Logger logger = LoggerFactory.getLogger(HelloHttpHubot.class);
	public static void main(String[] args) throws Exception{
		HelloHttpHubot hubot = new HelloHttpHubot();
		hubot.start();
	}
	
	@Override
	public void inProcessMessage(HMessage incomingMessage) {
		logger.info("received message from " + incomingMessage.getAuthor() + " : ");
		logger.info("---------------------------------------------------------");
		logger.info(incomingMessage.toString());
		logger.info("---------------------------------------------------------\n");
		
		if (incomingMessage != null && incomingMessage.getType().equals("hHttpData")) {
			HHttpData httpData = null;
			try {
				httpData = new HHttpData(incomingMessage.getPayloadAsJSONObject());
			} catch (Exception e) {
				logger.info("message: ",e);
			}
			logger.info("the httpdata : ");
			logger.info("---------------------------------------------------------");
			logger.info(httpData.toString());
			logger.info("---------------------------------------------------------");
		
			/*****test out box*****/
			// try to use out box to send the message to http://localhost:8082
			try {
				incomingMessage.setActor("httpOutbox@domain.com");
				if (httpData.getServerPort() != 8082){
					httpData.setServerPort(8082);

					incomingMessage.setPayload(httpData);
					logger.info("send message to http://" + httpData.getServerName() + ":" + httpData.getServerPort() + httpData.getQueryPath());
					send(incomingMessage);
				}
			} catch (MissingAttrException e) {
				logger.info("",e);
			}
			/**************************/
		}
	}

}
