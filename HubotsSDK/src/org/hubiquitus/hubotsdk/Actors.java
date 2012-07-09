package org.hubiquitus.hubotsdk;
import org.apache.camel.impl.DefaultCamelContext;
import org.hubiquitus.hapi.hStructures.HCommand;
import org.hubiquitus.hapi.hStructures.HMessage;


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

public class Actors {
	
	private DefaultCamelContext context = null;
	private ActorContext actorContext = null;
	
	public Actors() {}
	
	public static void main(String[] args) throws Exception {
	}
	
	public void initialize() {		
		context = new DefaultCamelContext();
		
	}
	
	public void put(String outboxName, HMessage msg) {
		
	}
	
	public void put(String outboxName, HCommand msg){
		
	}

	public ActorContext getContext() {
		return actorContext;
	}
	
	/* Return the camel context */
	public DefaultCamelContext getCamelContext() {
		return context;
	}
}
