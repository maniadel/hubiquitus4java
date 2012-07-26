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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;


import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.JndiRegistry;
import org.hubiquitus.hapi.client.HClient;
import org.hubiquitus.hapi.client.HStatusDelegate;
import org.hubiquitus.hapi.hStructures.ConnectionStatus;
import org.hubiquitus.hapi.hStructures.HCommand;
import org.hubiquitus.hapi.hStructures.HJsonObj;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hapi.hStructures.HOptions;
import org.hubiquitus.hapi.hStructures.HStatus;
import org.hubiquitus.hubotsdk.adapters.HubotAdapterInbox;
import org.hubiquitus.hubotsdk.adapters.HubotAdapterOutbox;
import org.hubiquitus.util.ActorStatus;
import org.hubiquitus.util.ConfigActor;
import org.hubiquitus.util.ConfigActor.AdapterConfig;

import com.fasterxml.jackson.databind.ObjectMapper;            

public abstract class Actor {

	private static Logger logger = Logger.getLogger(Actor.class);
	
	private static final String HUBOT_ADAPTER_OUTBOX = "hubotAdapterOutbox";
	private static final String HUBOT_ADAPTER_INBOX = "hubotAdapterInbox";
	
	private Actor outerclass = this;
	private ActorStatus status;
	
	private HClient hClient = new HClient();
	private DefaultCamelContext camelContext = null;
	private Map<String, Class<Object>> adapterOutClasses = new HashMap<String, Class<Object>>();
	private Map<String, Adapter> adapterInstances = new HashMap<String, Adapter>();
	private ConfigActor configActor;
	
	private String name; 
	private String jid;
	private String pwd;
	private String endpoint;
	
	private MessagesDelegate messageDelegate = new MessagesDelegate();
	private StatusDelegate statusDelegate = new StatusDelegate();
	
	/**
	 * Connect the Actor to the hAPI with params set in the file "config.txt" 
	 */
	protected final void start() {
		setStatus(ActorStatus.CREATED);
		try {
			// Create a default context for Camel
			camelContext = new DefaultCamelContext();
			ProducerTemplateSingleton.setContext(camelContext);
	
			// Parsing configuration file with Jackson 
			ObjectMapper mapper = new ObjectMapper();
			URL configFilePath = ClassLoader.getSystemResource("config.txt");
			configActor = mapper.readValue(new File(configFilePath.getFile()), ConfigActor.class);
			this.name = configActor.getName();
			this.jid = configActor.getJid();
			this.pwd = configActor.getPwdhash();
			this.endpoint = configActor.getEndpoint();
	
			//Connecting to HNode
			HOptions options = new HOptions();
			options.setTransport("socketio");
			ArrayList<String> endpoints = new ArrayList<String>();
			endpoints.add(endpoint);
			options.setEndpoints(endpoints);
			hClient.onStatus(statusDelegate);
			hClient.connect(jid, pwd , options);
			// see onStatus to know how this actor go the started status
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}
	
	/**
	 * An Hubot may override this method in order to perform its own initializations 
	 * At the end, the hubot must call the <i>initialized()</i> method to create and start the adapters
	 * and routes.
	 * @param hClient
	 */
	protected void init(HClient hClient) {
		initialized();
	}
	
	/**
	 * Create the hubotAdapter and all adapters declared in the file. Call the <i>startAdapters</i> for 
	 * start all adapters
	 * Set the status status READY when its over
	 */
	protected final void initialized() {
		setStatus(ActorStatus.INITIALIZED);

		//Create HubotAdapter (Mandatory)
		createHubotAdapter();

		//Create other adapters
		createAdapters();
		createRoutes();
		startAdapters();
		setStatus(ActorStatus.READY);
	}
	
	private void createRoutes() {
		
		RouteGenerator routes = new RouteGenerator(adapterOutClasses);
		try {
			camelContext.setRegistry(createRegistry());
			camelContext.addRoutes(routes);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		
	}

	private void createHubotAdapter() {
		HubotAdapterInbox hubotAdapterInbox = new HubotAdapterInbox(HUBOT_ADAPTER_INBOX);
		HubotAdapterOutbox hubotAdapterOutbox = new HubotAdapterOutbox(HUBOT_ADAPTER_OUTBOX);
		hubotAdapterInbox.setHClient(hClient);
		hubotAdapterInbox.setCamelContext(camelContext);
		hubotAdapterOutbox.setHClient(hClient);
		hubotAdapterOutbox.setCamelContext(camelContext);
	
		
		Map<String,String> propertiesMap = new HashMap<String,String>();
		propertiesMap.put("jid", configActor.getJid());
		propertiesMap.put("pwdhash", configActor.getPwdhash());
		propertiesMap.put("endpoint", configActor.getEndpoint());
		
		hubotAdapterInbox.setName(HUBOT_ADAPTER_INBOX);
		hubotAdapterOutbox.setName(HUBOT_ADAPTER_OUTBOX);
		hubotAdapterInbox.setProperties(propertiesMap);
		hubotAdapterOutbox.setProperties(propertiesMap);
		
		//Launch the HubotAdapter and put him in adapterInstances 
		hubotAdapterInbox.start();
		hubotAdapterOutbox.start();
		
		adapterInstances.put(HUBOT_ADAPTER_OUTBOX,hubotAdapterOutbox); 
		adapterInstances.put(HUBOT_ADAPTER_INBOX,hubotAdapterInbox); 
		
	}

	//Created other Adapters
	@SuppressWarnings("unchecked")
	private void createAdapters() {
		ArrayList<AdapterConfig> adapters = configActor.getAdapters();
		ArrayList<String> outAdaptersName = configActor.getOutboxes();
		ArrayList<String> inAdaptersName = configActor.getInbox();
		// Create instance of all Adapter
		if(adapters != null) {
			try {
				for(int i=0; i< adapters.size(); i++) {
					if(inAdaptersName != null && inAdaptersName.contains(adapters.get(i).getName())) {
						String nameAdapter = adapters.get(i).getName() + "Inbox";
						Class<Object> fc;
						fc = (Class<Object>) Class.forName("org.hubiquitus.hubotsdk.adapters." + adapters.get(i).getType() + "Inbox");
						Adapter newAdapterInbox = (AdapterInbox) fc.newInstance();
						newAdapterInbox.setName(nameAdapter);
						newAdapterInbox.setProperties(adapters.get(i).getProperties());
						newAdapterInbox.setHClient(hClient);
						newAdapterInbox.setCamelContext(camelContext);
						adapterInstances.put(nameAdapter, newAdapterInbox);
					}
					if(outAdaptersName != null && outAdaptersName.contains(adapters.get(i).getName())) {
						String nameAdapter = adapters.get(i).getName() + "Outbox";
						Class<Object> fc;
						fc = (Class<Object>) Class.forName("org.hubiquitus.hubotsdk.adapters." + adapters.get(i).getType() + "Outbox");
						Adapter newAdapterOutbox = (AdapterOutbox) fc.newInstance();
						newAdapterOutbox.setName(nameAdapter);
						newAdapterOutbox.setProperties(adapters.get(i).getProperties());
						newAdapterOutbox.setHClient(hClient);
						newAdapterOutbox.setCamelContext(camelContext);
						adapterInstances.put(nameAdapter, newAdapterOutbox);
						adapterOutClasses.put(nameAdapter, fc);			
					}
				} 
			}catch (Exception e) {
				logger.error(e.toString());
			}
		}
	}
	
	private void startAdapters() {
		if(adapterInstances != null) {
			for(String key : adapterInstances.keySet()) {
				adapterInstances.get(key).start();
			}   
		}
		try {
			camelContext.start();
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}
	
	/**
	 * Method used by camel. See Camel documentation for more information
	 * @return
	 * @throws Exception
	 */
	private JndiRegistry createRegistry() throws Exception {
		JndiRegistry jndi = new JndiRegistry();

		jndi.bind("actor", messageDelegate);
		jndi.bind(HUBOT_ADAPTER_OUTBOX, adapterInstances.get(HUBOT_ADAPTER_OUTBOX));

		
		if(adapterOutClasses != null) {
			for(String key : adapterOutClasses.keySet()) {
				jndi.bind(key, adapterInstances.get(key));
			}   
		}
		return jndi;
	}
	
	protected final void stop() {
		for(String key : adapterInstances.keySet()) {
		  adapterInstances.get(key).stop();
	  	}
	  	try {
		  camelContext.stop();
	  	} catch (Exception e) {
		  	logger.error(e.toString());
		  }
	}
		
	protected class MessagesDelegate {
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
	}
	
	protected abstract void inProcessMessage(HMessage incomingMessage);
	protected abstract void inProcessCommand(HCommand incomingCommand);

	/**
	 *  Send an object to a specified adapter outbox. Only HMessage and HCommand supported 
	 */
	protected final void put(String adapterName, HJsonObj hjson) {
		if(hjson.getHType().equalsIgnoreCase("hcommand")) {
			put(adapterName, new HCommand(hjson.toJSON()));
		} else if (hjson.getHType().equalsIgnoreCase("hmessage")){
			put(adapterName, new HMessage(hjson.toJSON()));
		} else {
			logger.error("Not supported");
		}
	}
	
	/**
	 * Send an HMessage to a specified adapter outbox.
	 * @param adapterName
	 * @param msg
	 */
	protected final void put(String adapterName, HMessage msg) {
		String route = "seda:" + adapterName + "Outbox";
		ProducerTemplateSingleton.getProducerTemplate().sendBody(route,msg);		
	}

	/**
	 * Send an HCommand to a specified adapter outbox.
	 * @param adapterName
	 * @param cmd
	 */
	protected final void put(String adapterName, HCommand cmd) {
		String route = "seda:" + adapterName + "Outbox";
		ProducerTemplateSingleton.getProducerTemplate().sendBody(route,cmd);		
	}

	/**
	 * Check if the hClient is connect and if connected, set the status to STARTED 
	 * and launch the init method
	 */
	private class StatusDelegate implements HStatusDelegate {
		/* Method use for incoming message/command */
		public final void onStatus(HStatus status) {
			if(status.getStatus() == ConnectionStatus.CONNECTED && outerclass.status == ActorStatus.CREATED) {
				setStatus(ActorStatus.STARTED); 
				init(hClient);			
			}
		}		
	}

	private void setStatus(ActorStatus status) {
		this.status = status;
		logger.info((name+"("+jid+") : "+status));
	}
	
	/**
	 * Retrived the instance of a specified AdapterInbox
	 * You can use this method to modified some properties of this adapter
	 * @param name
	 * @return
	 */
	protected final AdapterInbox getAdapterInbox(String name) {
		return (AdapterInbox) adapterInstances.get(name+"Inbox");
	}

	/**
	 * Retrived the instance of a specified AdapterOutbox
	 * You can use this method to modified some properties of this adapter
	 * @param name
	 * @return
	 */
	protected final AdapterOutbox getAdapterOutbox(String name) {
		return (AdapterOutbox) adapterInstances.get(name+"Outbox");
	}

}
