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
import org.hubiquitus.hapi.structure.HStatus;
import org.hubiquitus.hapi.structure.connection.ConnectionError;
import org.hubiquitus.hapi.structure.connection.ConnectionStatus;
import org.hubiquitus.hapi.transport.HTransport;
import org.hubiquitus.hapi.transport.HTransportCallback;
import org.hubiquitus.hapi.transport.JabberID;
import org.hubiquitus.hapi.transport.xmpp.HTransportXMPP;

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
	
	/**
	 * Constructor
	 */
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
	public void connect(String publisher, String password, HCallback callback,HOption options) {
		synchronized(this) { 
			if(this.status != ConnectionStatus.CONNECTED && this.status != ConnectionStatus.CONNECTING )
			{
				this.callback = callback;
				callbackConnection(new HStatus(ConnectionStatus.CONNECTING, ConnectionError.NO_ERROR, ""));
				// Check the JID is well formed 
				try {
					new JabberID(publisher);
					init(publisher,password,options);
					transport.connect(this ,this.options);	
				} catch (Exception e) {
					callbackConnection(new HStatus(ConnectionStatus.ERROR, ConnectionError.JID_MALFORMAT, "JID malformat"));
					return;
				}				
			} else {
				if(status == ConnectionStatus.CONNECTED) {
					callbackConnection(new HStatus(ConnectionStatus.CONNECTED, 
							ConnectionError.ALREADY_CONNECTED, "Already connected"));
				}
				
			}
		}
	}
	
	/**
	 * Method disconnect
	 */
	public void disconnect() {
		if(status == ConnectionStatus.CONNECTED) {
			transport.disconnect(this);
		} else {
			callbackConnection(new HStatus(ConnectionStatus.DISCONNECTED, 
					ConnectionError.NOT_CONNECTED,
					"Must be connect before disconnecting"));;
		}
	}
	
	/**
	 * Method callback used during a connection 
	 */
	@Override
	public void callbackConnection(HStatus hstatus) {
		this.status = hstatus.getStatus();
		if(callback != null) {
			HStatus temp = hstatus;
			this.callback.hCallback("HStatus",temp);
		}
	}
	
	/**
	 * Affiche le status de l'utilisateur dans la console
	 */
	public void printStatusConnection() {
		System.out.println(status);
	}
}
