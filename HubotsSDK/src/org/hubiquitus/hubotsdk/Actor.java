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
import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.hubiquitus.hapi.client.HClient;
import org.hubiquitus.hapi.hStructures.HCommand;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hapi.util.HJsonDictionnary;
import org.hubiquitus.util.ConfigActor;
import org.hubiquitus.util.ConfigActor.AdapterConfig;

import com.fasterxml.jackson.databind.ObjectMapper;            

public abstract class Actor {

	private HClient hClient = null;
	private DefaultCamelContext context = null;
	private ProducerTemplate template = null;
	private Map<String, Class<Object>> adapterClasses;
	private Map<String, Adapter> adapterIntances;

	public Actor() {
		initialize();
	}

	public static void main(String[] args) throws Exception {
	}

	public final void initialize() {		
		try {
			
			// Create a default client
			HClient client = new HClient();
			
			// Create a default context for Camel
			context = new DefaultCamelContext();
			ProducerTemplateSingleton.setContext(context);

			// Parsing configuration file with Jackson 
			ObjectMapper mapper = new ObjectMapper();
			ConfigActor config = mapper.readValue(new File("./resources/config.txt"), ConfigActor.class);

			ArrayList<AdapterConfig> adapters = config.getAdapters();
			ArrayList<String> outAdaptersName = config.getOutboxes();

			// Create instance of all Adapter
			for(int i=0; i< adapters.size(); i++) {
				@SuppressWarnings("unchecked")
				Class<Object> fc = (Class<Object>) Class.forName(adapters.get(i).getType());
				Adapter newAdapter = (Adapter) fc.newInstance();
				if(outAdaptersName.contains(adapters.get(i).getName())) {
					adapterClasses.put(adapters.get(i).getName(), fc);
					adapterIntances.put(adapters.get(i).getName(), newAdapter);
				}

			}

			//Create routes for Camel
			RouteGenerator routes = new RouteGenerator(this, adapterClasses);
			context.addRoutes(routes);
			context.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public final void put(String boxName, HJsonDictionnary hjson) {
		if(hjson.getHType() == "hCommand") {
			putCommand(boxName, new HCommand(hjson.toJSON()));
		} else if (hjson.getHType() == "hMessage"){
			putMessage(boxName, new HMessage(hjson.toJSON()));
		}
	}

	public abstract void putMessage(String outboxName, HMessage msg);

	public abstract void putCommand(String outboxName, HCommand msg);

}
