package org.hubiquitus.hubotsdk;

import java.util.ArrayList;

import org.hubiquitus.hapi.hStructures.HMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HubotDispatcher {
	final  Logger logger = LoggerFactory.getLogger(HubotDispatcher.class);
	
	private static final String HUBOT_ADAPTER_OUTBOX = "hubotAdapterOutbox";
	private ArrayList<String> adapterOutboxActors;
	
	public HubotDispatcher(){
		
	}
	
	public  void dispatcher(HMessage msg){
		if(msg == null){
			logger.warn("hmessage null.");
			return;
		}else{
			String msgActor = msg.getActor();
			if(!adapterOutboxActors.contains(msgActor)){
				put(HUBOT_ADAPTER_OUTBOX, msg);
			}else{
				put(msgActor, msg);
			}
		}
	}
	
	public ArrayList<String> getAdapterOutboxActor() {
		return adapterOutboxActors;
	}
	public void setAdapterOutboxActors(ArrayList<String> adapterOutboxActors) {
		this.adapterOutboxActors = adapterOutboxActors;
	}
	
	private void put(String adapterActor, HMessage msg) {
		String route = "seda:" + adapterActor;
		try {
			ProducerTemplateSingleton.getProducerTemplate().sendBody(route,msg);
		} catch (Exception e) {
			logger.debug("message: ", e);
		}
				
	}
}
