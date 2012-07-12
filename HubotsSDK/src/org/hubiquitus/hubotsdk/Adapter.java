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

import java.util.Map;

import org.hubiquitus.hapi.hStructures.HCommand;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hapi.util.HJsonDictionnary;

public abstract class Adapter {

	public abstract void setProperties(Map<String,String> params);

	// Method for input message and command 
	public final void onInGoing(Object object) {
		ProducerTemplateSingleton.getProducerTemplate().sendBody("seda:inbox",object);
	}


	// Method for output message and command 
	public final void onOutGoing(HJsonDictionnary hjson) {
		if(hjson.getHType() == "hCommand") {
			sendCommand(new HCommand(hjson.toJSON()));
		} else if (hjson.getHType() == "hMessage"){
			sendMessage(new HMessage(hjson.toJSON()));
		}
	}

	public abstract void sendCommand(HCommand command);
	public abstract void sendMessage(HMessage message);

	// Method to start the bot
	public abstract void start();

	// Method to stop the bot
	public abstract void stop();
}
