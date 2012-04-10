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

package org.hubiquitus.hapi.client.impl;

import org.hubiquitus.hapi.client.HCallback;
import org.hubiquitus.hapi.client.HOption;
import org.hubiquitus.hapi.client.HTransport;
import org.hubiquitus.hapi.client.HTransportCallback;
import org.hubiquitus.hapi.client.HStatus;
import org.hubiquitus.hapi.error.ErrorsCode;
import org.hubiquitus.hapi.model.ConnectionStatus;

import de.javawi.jstun.attribute.ErrorCode;




/**
 * @author j.desousag
 * @version 0.3
 * Class HClient implement HTransportCallback
 */

public class HClient implements HTransportCallback{
	
	/* Parameters */
	
	/**
	 * Connection status
	 */	
	private ConnectionStatus status;
	
	
	/**
	 * Connection option
	 */
	private HOption options;
	
	/**
	 * CallBack
	 */
	private HCallback callback = null;
	
	/**
	 * Transport used 
	 */
	private HTransport transport;

	public HClient() {
		this.status = ConnectionStatus.DISCONNECTED;
	}

	public void init(String publisher, String password,HOption options) {

		
		if(options == null) {
			this.options = new HOption();
		} else {
			this.options = options;
		}
		this.options.setJabberID(publisher);
		this.options.setPassword(password);
		
		this.transport = new HTransportXMPP();
	}
	/**
	 * Method connect which set the option and launch the connection
	 * @param publisher
	 * @param password
	 * @param callback
	 * @param options
	 */
	
	public void connect(String publisher, String password, HCallback callback,HOption options){
		
		synchronized(this) { 
			if(this.status != ConnectionStatus.CONNECTED && this.status != ConnectionStatus.CONNECTING )
			{
				this.callback = callback;
				this.status =  ConnectionStatus.CONNECTING;
				callbackConnection(new HStatus(ConnectionStatus.CONNECTING, ErrorsCode.NO_ERROR, ""));
				// Check the JID is well formed 
				if(!publisher.contains("@")) {
					this.status = ConnectionStatus.ERROR;
					callbackConnection(new HStatus(ConnectionStatus.ERROR, ErrorsCode.JID_MALFORMAT, "JID malformat"));
					return;
				}
				
				init(publisher,password,options);
				transport.connect(this ,this.options);	
			} else {
				if(status == ConnectionStatus.CONNECTED) {
					callbackConnection(new HStatus(ConnectionStatus.CONNECTED, 
							ErrorsCode.ALREADY_CONNECTED, "Already connected"));
				}
				
			}
		}
	}
	
	/**
	 * Method disconnect
	 */
	
	public void disconnect() {
		if(status == ConnectionStatus.CONNECTED) {
			transport.disconnect(this, this.options);
		} else {
			callbackConnection(new HStatus(ConnectionStatus.DISCONNECTED, 
					ErrorsCode.TECH_ERROR, "Must be connect before disconnecting"));;
		}
	}
	
	/**
	 * Method callback used during a connection 
	 */
	@Override
	public void callbackConnection(HStatus hstatus){
		
		this.status = hstatus.getStatus();
		if(callback != null) {
			HStatus temp = hstatus;
			this.callback.hCallback(temp);
		}
	}
	
	/**
	 * Affiche le status de l'utilisateur dans la console
	 */
	public void printStatusConnection() {
		System.out.println(status);
	}
}
