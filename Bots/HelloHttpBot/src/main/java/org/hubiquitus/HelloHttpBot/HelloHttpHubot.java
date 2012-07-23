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

import org.hubiquitus.hapi.hStructures.HCommand;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.Actor;
import org.hubiquitus.hubotsdk.adapters.HHttpAdapter.HHttpData;
public class HelloHttpHubot extends Actor{

	public static void main(String[] args) throws Exception{
		HelloHttpHubot hubot = new HelloHttpHubot();
		hubot.start();
	}
	
	@Override
	public void inProcessMessage(HMessage incomingMessage) {
		System.out.println(incomingMessage);
		if (incomingMessage != null && incomingMessage.getType().equals("hHttpData")) {
			System.out.println("httpdata : " + incomingMessage.getPayload());
			HHttpData httpData = new HHttpData(incomingMessage.getPayload().toJSON());
			System.out.println(httpData);
		}
		
	}
	
	public void inProcessCommand(HCommand incomingCommand) {
	}

}
