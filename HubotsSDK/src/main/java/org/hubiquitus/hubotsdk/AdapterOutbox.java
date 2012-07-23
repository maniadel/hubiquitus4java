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

import org.hubiquitus.hapi.hStructures.HCommand;
import org.hubiquitus.hapi.hStructures.HJsonObj;
import org.hubiquitus.hapi.hStructures.HMessage;

public abstract class AdapterOutbox extends Adapter {
	
	// Method for output message and command 
	public final void onOutGoing(HJsonObj hjson) {
		System.out.println("hello4");
		if(hjson.getHType() == "hcommand") {
			sendCommand(new HCommand(hjson.toJSON()));
		} else if (hjson.getHType() == "hmessage"){	
			sendMessage(new HMessage(hjson.toJSON()));
		}
	}

	public abstract void sendCommand(HCommand command);
	public abstract void sendMessage(HMessage message);

}
