/*
 * Copyright (c) Novedia Group 2012.
 *
 *    This file is part of Hubiquitus
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy
 *    of this software and associated documentation files (the "Software"), to deal
 *    in the Software without restriction, including without limitation the rights
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *    of the Software, and to permit persons to whom the Software is furnished to do so,
 *    subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies
 *    or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *    INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *    PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 *    FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 *    ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *    You should have received a copy of the MIT License along with Hubiquitus.
 *    If not, see <http://opensource.org/licenses/mit-license.php>.
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
				// if the actor's domain is twitter.com 
				// we have to use a twitter outbox adapter
				// search the first outbox adapter where the domain is "twitter.com"
				// and send the hMessage to this adapter
				if (msgActor.endsWith("@twitter.com")) {
					msgActor = getFirstTwitterOutboxAdapter();
					if (msgActor != null) {
						put(msgActor, hubotStruct);
						return;
					} 
				}
				// in other cases we try to send the hMessage through the hubiquitus
				// legacy adapter
				put(HUBOT_ADAPTER_OUTBOX, hubotStruct);
			} else {
				put(msgActor, hubotStruct);
			}
		}
	}
	
	private String getFirstTwitterOutboxAdapter() {
		// TODO Auto-generated method stub
		for(String actor : adapterOutboxActors){
			if(actor.endsWith("@twitter.com")){
				return actor;
			}
		}
		return null;
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
