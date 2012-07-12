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

package main;

import java.util.GregorianCalendar;

import org.hubiquitus.hapi.hStructures.HCommand;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hapi.util.HJsonDictionnary;
import org.hubiquitus.hubotsdk.Actor;
import org.hubiquitus.hubotsdk.ProducerTemplateSingleton;

public class HelloHubot extends Actor{

	public HelloHubot() {
		super();
	}
	@Override
	public void inProcess(Object obj) {
		// TODO Auto-generated method stub
		
		HMessage messageIn = (HMessage) obj;

		HMessage message = new HMessage();
		message.setPublisher(messageIn.getChid());
		message.setChid(messageIn.getPublisher());
		message.setPublished(new GregorianCalendar());
		message.setType("hMessage");
		message.setTransient(true);
		
		HJsonDictionnary payload = new HJsonDictionnary();
		payload.put("text", "test");
		message.setPayload(payload);
		put("hubotAdapter",message);
	}

	@Override
	public void putMessage(String outboxName, HMessage msg) {
		System.out.println("outboxName:"  + outboxName + " msg : " + msg);
		String route = "seda:" + outboxName;
		System.out.println("route : " + route );
		ProducerTemplateSingleton.getProducerTemplate().sendBody(route,msg);		
	}

	@Override
	public void putCommand(String outboxName, HCommand cmd) {
		String route = "seda:" + outboxName;
		ProducerTemplateSingleton.getProducerTemplate().sendBody(route,cmd);		
	}

}
