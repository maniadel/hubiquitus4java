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
	
	private HStatus status;
	
	private HOption options;
	
	private HCallback callback;
	
	private HTransport transport;

	public HClient() {
		this.status = new HStatus(ConnectionStatus.DISCONNECTED, null, null);
	}

	
	public void connect(String publisher, String password, HCallback callback,HOption options){
		if(status.getStatus() != ConnectionStatus.CONNECTED)
		{
			if(options == null) {
				this.options = new HOption();
			} else {
				this.options = options;
			}
			this.options.setJabberID(publisher);
			this.options.setPassword(password);
			this.callback = callback;
			
			this.transport = new HTransportXMPP();
			
			transport.connect(this ,this.options);	
		} else {
			System.out.println("Already connect");
		}
	}
	
	
	public void disconnect() {
		transport.disconnect(this, this.options);
	}
	
	@Override
	public void callbackConnection(HStatus hstatus){
		this.status = hstatus;
		if(status.getErrorCode() != -1)
			System.out.println(status.getErrorMsg());
	}
	
	public void printStatusConnection() {
		System.out.println(status.getStatus());
	}
}
