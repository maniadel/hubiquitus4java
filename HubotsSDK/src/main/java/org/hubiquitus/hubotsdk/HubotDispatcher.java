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
package org.hubiquitus.hubotsdk;

import java.util.ArrayList;

import org.hubiquitus.hapi.client.HMessageDelegate;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HubotDispatcher {
	final  Logger logger = LoggerFactory.getLogger(HubotDispatcher.class);
	
	private static final String HUBOT_ADAPTER_OUTBOX = "hubotAdapterOutbox";
    // TODO vérifier la pertinence d'un ArrayList vs. une Map
	private ArrayList<String> adapterOutboxActors;
	
	public HubotDispatcher(ArrayList<String> adapterOutboxActors){
        this.adapterOutboxActors = adapterOutboxActors;
	}
	
	public  void dispatcher(HMessage msg, HMessageDelegate callback){
		if (msg != null) {
			HubotMessageStructure hubotStruct = new HubotMessageStructure(msg, callback);
			String msgActor = msg.getActor();
			if (!adapterOutboxActors.contains(msgActor)) {
				put(HUBOT_ADAPTER_OUTBOX, hubotStruct);
			} else {
				put(msgActor, hubotStruct);
			}
		}
	}
	
	private void put(String adapterActor, HubotMessageStructure hubotStruct) {
		String route = "seda:" + adapterActor;
		try {
			ProducerTemplateSingleton.getProducerTemplate().sendBody(route,hubotStruct);
		} catch (Exception e) {
			logger.debug("message: ", e);
		}
				
	}
}
