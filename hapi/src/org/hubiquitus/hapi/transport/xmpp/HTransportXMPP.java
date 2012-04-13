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

package org.hubiquitus.hapi.transport.xmpp;

import org.hubiquitus.hapi.client.HOption;
import org.hubiquitus.hapi.client.impl.HClient;
import org.hubiquitus.hapi.structure.HStatus;
import org.hubiquitus.hapi.structure.connection.ConnectionError;
import org.hubiquitus.hapi.structure.connection.ConnectionStatus;
import org.hubiquitus.hapi.transport.HTransport;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
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
	
	private HClient callback;
	
	public HTransportXMPP() {};	
	
	/**
	 * Method connect : connection with the server and return an HStatus
	 * @param callback
	 * @param options
	 */
	@Override
	public void connect(final HClient callback,final HOption options){
		this.callback = callback;
		
		if(options.getServerHost() != null ) {
			config = new ConnectionConfiguration(options.getServerHost(), options.getServerPort());
		} else if(options.getJabberID().getDomain() != null ) {
			config = new ConnectionConfiguration(options.getJabberID().getDomain(), options.getServerPort());
		} else {
			sendErrorCallBack(ConnectionError.TECH_ERROR, "hostname can't be null");
			return;
		}
		
		
		config.setSecurityMode(SecurityMode.required);
		connection = new XMPPConnection(config);
				
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					connection.connect();
					try {
						connection.login(options.getJabberID().getUsername(), options.getPassword());
						sendOkCallBack(ConnectionStatus.CONNECTED);
					}catch(XMPPException e) {
						//TODO : Utilise la console log4j
						connection.disconnect();
						sendErrorCallBack(ConnectionError.TECH_ERROR,  "login/password error : " + e.getMessage());
					}
				
				} catch(XMPPException e) {
					sendErrorCallBack(ConnectionError.CONN_TIMEOUT, e.getMessage());
				} 				
			}
		}).start();
		
		
	}

	/**
	 * Method disconnect : disconnection with the server and return an HStatus
	 * @param callback
	 * @param options
	 */
	@Override
	public void disconnect(HClient callback){
		sendOkCallBack(ConnectionStatus.DISCONNECTING);
		try {
			connection.disconnect();
			sendOkCallBack(ConnectionStatus.DISCONNECTED);
		} catch(Exception e) {
			sendErrorCallBack(ConnectionError.TECH_ERROR, e.getMessage());			
		}
	}

	public void sendOkCallBack(ConnectionStatus status) {
		this.status = status;
		this.callback.callbackConnection(new HStatus(status,ConnectionError.NO_ERROR, ""));
	}
	
	public void sendErrorCallBack(ConnectionError error, String msg) {
		this.status = ConnectionStatus.ERROR;
		this.callback.callbackConnection(new HStatus(status,error,msg));
	}
	
	

}
