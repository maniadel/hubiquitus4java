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
import org.hubiquitus.hubotsdk.adapters.HubotAdapter;
import org.hubiquitus.util.ConfigActor;
import org.hubiquitus.util.ConfigActor.AdapterConfig;

import com.fasterxml.jackson.databind.ObjectMapper;            

public abstract class Actor {

	private HClient hClient = new HClient();
	private DefaultCamelContext context = null;
	private Map<String, Class<Object>> adapterOutClasses = new HashMap<String, Class<Object>>();
	private Map<String, Adapter> adapterIntances = new HashMap<String, Adapter>();
	private ConfigActor configActor;

	public Actor() {
		initialize();
	}
	
	public final void initialize() {		
		try {
			// Create a default context for Camel
			context = new DefaultCamelContext();
			ProducerTemplateSingleton.setContext(context);

			// Parsing configuration file with Jackson 
			ObjectMapper mapper = new ObjectMapper();
			configActor = mapper.readValue(new File("./resources/config.txt"), ConfigActor.class);
			System.out.println("configActor : " + configActor);

			// Create HubotAdapter
			HubotAdapter hubotAdapter = new HubotAdapter("hubotAdapter");
			hubotAdapter.setHclient(hClient);
			Map<String,String> propertiesMap = new HashMap<String,String>();
			propertiesMap.put("jid", configActor.getJid());
			propertiesMap.put("pwdhash", configActor.getPwdhash());
			propertiesMap.put("endpoint", configActor.getEndpoint());
			hubotAdapter.setProperties(propertiesMap);
			//Launch the HubotAdapter and put him in adapterInstances 
			hubotAdapter.start();
			adapterIntances.put("hubotAdapter",hubotAdapter); 



			ArrayList<AdapterConfig> adapters = configActor.getAdapters();
			ArrayList<String> outAdaptersName = configActor.getOutboxes();

			// Create instance of all Adapter
			if(adapters != null) {
				for(int i=0; i< adapters.size(); i++) {
					@SuppressWarnings("unchecked")
					Class<Object> fc = (Class<Object>) Class.forName(adapters.get(i).getType());
					Adapter newAdapter = (Adapter) fc.newInstance();
					newAdapter.setProperties(adapters.get(i).getProperties());
					adapterIntances.put(adapters.get(i).getName(), newAdapter);
					if(outAdaptersName != null && outAdaptersName.contains(adapters.get(i).getName())) {
						adapterOutClasses.put(adapters.get(i).getName(), fc);
					}
				}
			}	
			
			System.out.println("Route create");
			
			//Create routes for Camel
			RouteGenerator routes = new RouteGenerator(adapterOutClasses);
			context.setRegistry(createRegistry());
			context.addRoutes(routes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected final JndiRegistry createRegistry() throws Exception {
        JndiRegistry jndi = new JndiRegistry();
        jndi.bind("actor", this);
        System.out.println("adapter : " + adapterIntances.get("hubotAdapter"));
        jndi.bind("hubotAdapter", adapterIntances.get("hubotAdapter"));
        if(adapterOutClasses != null) {
	        for(String key : adapterOutClasses.keySet()) {
	        	jndi.bind(key, adapterOutClasses.get(key));
			}   
        }
        return jndi;
    }
	
	public final void start() {
		try {
			context.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public final void stop() {
		for(String key : adapterIntances.keySet()) {
        	adapterIntances.get(key).stop();
		}  
		try {
			context.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Method use for incoming message/command */
	public abstract void inProcess(Object obj);

	/* Send message to a specified adapter */
	public final void put(String boxName, HJsonObj hjson) {
		if(hjson.getHType() == "hcommand") {
			putCommand(boxName, new HCommand(hjson.toJSON()));
		} else if (hjson.getHType() == "hmessage"){
			putMessage(boxName, new HMessage(hjson.toJSON()));
		}
	}

	public abstract void putMessage(String outboxName, HMessage msg);

	public abstract void putCommand(String outboxName, HCommand cmd);

}
