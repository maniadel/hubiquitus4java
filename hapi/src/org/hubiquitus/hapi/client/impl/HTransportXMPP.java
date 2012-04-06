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

import org.hubiquitus.hapi.client.HOption;
import org.hubiquitus.hapi.client.HStatus;
import org.hubiquitus.hapi.client.HTransport;
import org.hubiquitus.hapi.error.ErrorsCode;
import org.hubiquitus.hapi.model.ConnectionStatus;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * 
 * @author j.desousag
 * @version 0.3
 * Class HTransportXMPP implement HTransport
 */

public class HTransportXMPP implements HTransport {

	
	/**
	 * Connection object
	 */
	private Connection connection;
	
	/**
	 * Connection configuration
	 */
	private ConnectionConfiguration config;
	
	/**
	 * Connection status
	 */
	private ConnectionStatus status;
	
	
	/**
	 * Builder
	 * @param publisher
	 * @param password
	 * @param callback
	 * @param options
	 */
	public HTransportXMPP() {
		
	};	
	
	/**
	 * Method connect : connection with the server and return an HStatus
	 * @param callback
	 * @param options
	 */
	@Override
	public void connect(HClient callback,HOption options){
			
		config = new ConnectionConfiguration(options.getJabberID().getDomain(), options.getServerPort());
		connection = new XMPPConnection(config);
		status = ConnectionStatus.CONNECTING;
		callback.callbackConnection(new HStatus(status,ErrorsCode.NO_ERROR, null));
		try {
			connection.connect();
			connection.login(options.getJabberID().getBareJID(), options.getPassword());
			status = ConnectionStatus.CONNECTED;
			System.out.println("Connected");
			callback.callbackConnection(new HStatus(status,ErrorsCode.NO_ERROR, null));
		} catch(XMPPException e) {
			status = ConnectionStatus.ERROR;
			callback.callbackConnection(new HStatus(status, ErrorsCode.UNKNOWN, e.getMessage()));
		}
		
	}

	/**
	 * Method disconnect : disconnection with the server and return an HStatus
	 * @param callback
	 * @param options
	 */
	@Override
	public void disconnect(HClient callback,HOption options){
		status = ConnectionStatus.DISCONNECTING;
		callback.callbackConnection(new HStatus(status,ErrorsCode.NO_ERROR, null));
		try {
			connection.disconnect();
			if( !connection.isConnected())
				status = ConnectionStatus.DISCONNECTED;
			callback.callbackConnection(new HStatus(status,ErrorsCode.NO_ERROR, null));
		} catch(Exception a) {
			status = ConnectionStatus.ERROR;
			callback.callbackConnection(new HStatus(status, ErrorsCode.UNKNOWN, a.getMessage()));			
		}
	}

}
