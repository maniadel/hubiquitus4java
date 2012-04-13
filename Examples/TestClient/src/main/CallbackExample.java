package main;

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


import org.hubiquitus.hapi.client.HCallback;
import org.hubiquitus.hapi.structure.HStatus;
import org.hubiquitus.hapi.structure.connection.ConnectionError;
import org.hubiquitus.hapi.structure.connection.ConnectionStatus;

/**
 * 
 * @author speed
 * @version 0.3
 * Callback of the example
 */
public class CallbackExample implements HCallback {
	
	private MainPanel panel;

	public CallbackExample(MainPanel panel2) {
		this.panel = panel2;
	}
	
	@Override
	public void hCallback(String type, Object objet) {
		// TODO Auto-generated method stub
		if(type.equals("HStatus")) {
			HStatus status = (HStatus) objet;
			panel.addTextArea(status.toString());
			if(status.getStatus() != ConnectionStatus.ERROR)
				panel.setStatusArea(status.getStatus().toString());
			else
				panel.setStatusArea(status.getStatus().toString() + " : " + status.getErrorMsg() );
		}
	}

}
