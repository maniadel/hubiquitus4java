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
import java.io.File; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.JndiRegistry;
import org.hubiquitus.hapi.client.HClient;
import org.hubiquitus.hapi.hStructures.HCommand;
import org.hubiquitus.hapi.hStructures.HJsonObj;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.adapters.HubotAdapterInbox;
import org.hubiquitus.hubotsdk.adapters.HubotAdapterOutbox;
import org.hubiquitus.util.ConfigActor;
import org.hubiquitus.util.ConfigActor.AdapterConfig;

import com.fasterxml.jackson.databind.ObjectMapper;            

public abstract class Actor {

	protected HClient hClient = new HClient();
	protected DefaultCamelContext context = null;
	protected Map<String, Class<Object>> adapterOutClasses = new HashMap<String, Class<Object>>();
	protected Map<String, Adapter> adapterIntances = new HashMap<String, Adapter>();
	protected ConfigActor configActor;


	private void initialize() {		
		try {
			// Create a default context for Camel
			context = new DefaultCamelContext();
			ProducerTemplateSingleton.setContext(context);

			// Parsing configuration file with Jackson 
			ObjectMapper mapper = new ObjectMapper();
			configActor = mapper.readValue(new File("./resources/config.txt"), ConfigActor.class);

			//Create HubotAdapter;
			createHubotAdapter();
			
			//Create Adapters
			createAdapters();
			
			//Create routes for Camel
			RouteGenerator routes = new RouteGenerator(adapterOutClasses);
			context.setRegistry(createRegistry());
			context.addRoutes(routes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void createHubotAdapter() {
		HubotAdapterInbox hubotAdapterInbox = new HubotAdapterInbox("hubotAdapterInbox");
		HubotAdapterOutbox hubotAdapterOutbox = new HubotAdapterOutbox("hubotAdapterOutbox");
		hubotAdapterInbox.setHclient(hClient);
		hubotAdapterOutbox.setHclient(hClient);
	
		
		Map<String,String> propertiesMap = new HashMap<String,String>();
		propertiesMap.put("jid", configActor.getJid());
		propertiesMap.put("pwdhash", configActor.getPwdhash());
		propertiesMap.put("endpoint", configActor.getEndpoint());
		
		hubotAdapterInbox.setname("hubotAdapterInbox");
		hubotAdapterOutbox.setname("hubotAdapterOutbox");
		hubotAdapterInbox.setProperties(propertiesMap);
		hubotAdapterOutbox.setProperties(propertiesMap);
		
		//Launch the HubotAdapter and put him in adapterInstances 
		hubotAdapterInbox.start();
		hubotAdapterOutbox.start();
		
		adapterIntances.put("hubotAdapterOutbox",hubotAdapterOutbox); 
		adapterIntances.put("hubotAdapterInbox",hubotAdapterInbox); 
		
	}

	@SuppressWarnings("unchecked")
	protected void createAdapters() {
		ArrayList<AdapterConfig> adapters = configActor.getAdapters();
		ArrayList<String> outAdaptersName = configActor.getOutboxes();
		ArrayList<String> inAdaptersName = configActor.getOutboxes();

		// Create instance of all Adapter
		if(adapters != null) {
			try {
				for(int i=0; i< adapters.size(); i++) {
					if(inAdaptersName != null && inAdaptersName.contains(adapters.get(i).getName())) {
						Class<Object> fc;
						fc = (Class<Object>) Class.forName("org.hubiquitus.hubotsdk.adapters." + adapters.get(i).getType() + "Inbox");
						Adapter newAdapterInbox = (AdapterInbox) fc.newInstance();
						newAdapterInbox.setname(adapters.get(i).getName() + "Inbox");
						newAdapterInbox.setProperties(adapters.get(i).getProperties());
						newAdapterInbox.setname(adapters.get(i).getName() + "Inbox");
						newAdapterInbox.sethclient(hClient);
						newAdapterInbox.start();
						adapterIntances.put(adapters.get(i).getName() + "Inbox", newAdapterInbox);
					}
					if(outAdaptersName != null && outAdaptersName.contains(adapters.get(i).getName())) {
						Class<Object> fc;
						fc = (Class<Object>) Class.forName("org.hubiquitus.hubotsdk.adapters." + adapters.get(i).getType() + "Outbox");
						Adapter newAdapterOutbox = (AdapterOutbox) fc.newInstance();
						newAdapterOutbox.setname(adapters.get(i).getName() + "Outbox");
						newAdapterOutbox.setProperties(adapters.get(i).getProperties());
						newAdapterOutbox.setname(adapters.get(i).getName() + "Inbox");
						newAdapterOutbox.sethclient(hClient);
						newAdapterOutbox.start();	
						adapterIntances.put(adapters.get(i).getName() + "Outbox", newAdapterOutbox);
						adapterOutClasses.put(adapters.get(i).getName() + "Outbox", fc);
										
					}
				} 
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private JndiRegistry createRegistry() throws Exception {
		JndiRegistry jndi = new JndiRegistry();

		jndi.bind("actor", this);
		jndi.bind("hubotAdapterOutbox", adapterIntances.get("hubotAdapterOutbox"));

		
		if(adapterOutClasses != null) {
			for(String key : adapterOutClasses.keySet()) {
				jndi.bind(key, adapterIntances.get(key));
			}   
		}
		return jndi;
	}

	protected final void start() {
		try {
			initialize();
			context.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected final void stop() {
		for(String key : adapterIntances.keySet()) {
			adapterIntances.get(key).stop();
		}  
		try {
			context.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Method use for incoming message/command */
	public final void inProcess(Object obj) {
		if (obj != null) {
			if (obj instanceof HMessage) {
				inProcessMessage((HMessage)obj);
			}
			if (obj instanceof HCommand) {
				inProcessCommand((HCommand)obj);
			}
		}		
	}

	protected abstract void inProcessMessage(HMessage messageIncoming);
	protected abstract void inProcessCommand(HCommand commandIncoming);

	/* Send message to a specified adapter */
	protected final void put(String boxName, HJsonObj hjson) {
		if(hjson.getHType().equalsIgnoreCase("hcommand")) {
			put(boxName, new HCommand(hjson.toJSON()));
		} else if (hjson.getHType().equalsIgnoreCase("hmessage")){
			put(boxName, new HMessage(hjson.toJSON()));
		}
	}

	protected final void put(String outboxName, HMessage msg) {
		String route = "seda:" + outboxName;
		ProducerTemplateSingleton.getProducerTemplate().sendBody(route,msg);		
	}

	protected final void put(String outboxName, HCommand cmd) {
		String route = "seda:" + outboxName;
		ProducerTemplateSingleton.getProducerTemplate().sendBody(route,cmd);		
	}

}
