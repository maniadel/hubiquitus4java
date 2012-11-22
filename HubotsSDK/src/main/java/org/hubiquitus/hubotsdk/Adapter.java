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

import org.apache.camel.impl.DefaultCamelContext;
import org.hubiquitus.hapi.client.HClient;
import org.hubiquitus.hapi.client.HMessageDelegate;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hapi.hStructures.ResultStatus;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public abstract class Adapter {
	final Logger logger = LoggerFactory.getLogger(Adapter.class);
	
	protected HClient hclient;
	protected String actor;
	protected DefaultCamelContext camelContext;
	
	/**
	 * Method used to set properties of the adapters. 
	 * SHOULD BE OVERWRITE
	 * @param properties
	 */
	public abstract void setProperties(JSONObject properties);

	/**
	 *  Method to start the bot
	 */
	public abstract void start();

	/**
	 * Method to stop the bot
	 */
	public abstract void stop();
	
	
	public final void setCamelContext(DefaultCamelContext camelContext) {
		this.camelContext = camelContext;				
	}
	
	public final void setHClient(HClient hclient) {
		this.hclient = hclient;				
	}
	
	public final void setActor(String actor) {
		this.actor = actor;				
	}
	
	public final String getActor() {
		return actor;
	}
	
	
	/**
	 * Allow the user to update the properties of the adapter. During this update, this adapter is stop.
	 * @param properties
	 */
	public final void updateProperties(JSONObject properties) {
		stop();
		setProperties(properties);
		start();
	}

}
