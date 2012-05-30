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
package main;


import org.hubiquitus.hapi.client.HDelegate;
import org.hubiquitus.hapi.hStructures.HJsonObj;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hapi.hStructures.HResult;
import org.hubiquitus.hapi.hStructures.HStatus;
import org.hubiquitus.hapi.hStructures.ConnectionError;

/**
 * 
 * @author j.desousag
 * @version 0.3
 * Callback of the example
 */
public class CallbackExample implements HDelegate {
	
	private MainPanel panel;

	public CallbackExample(MainPanel panel2) {
		this.panel = panel2;
	}
	
	@Override
	public void hDelegate(String type, HJsonObj data) {
		if(type.equals("hstatus")) {
			panel.setStatusArea(type);
			HStatus status = (HStatus) data;
			
			panel.addTextArea(status.toString());
			if(status.getErrorCode() == ConnectionError.NO_ERROR || status.getErrorMsg() == null) {
				panel.setStatusArea(status.getStatus().toString());
			} else {
				panel.setStatusArea(status.getStatus().toString() + " : " + status.getErrorMsg() );
			}	
		}
		
		if(type.equalsIgnoreCase("hresult")) {
			HResult result = (HResult) data;
			panel.addTextArea(result.toString());
		}
		
		if(type.equalsIgnoreCase("hmessage")) {
			HMessage result = (HMessage) data;
			panel.addTextArea(result.toString());
		}
	}

}
