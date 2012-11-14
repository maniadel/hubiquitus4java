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
package org.hubiquitus.twitter4j.stream.pub;

import org.json.JSONObject;

public interface HUserStreamListner {	

	/**
	 * called when a status is received throw the Streaming API
	 * @param status
	 */
	public void onStatus(JSONObject status);

	/**
	 * called when a technical message is received throw the Streaming API
	 * @param message
	 */
	public void onOtherMessage(JSONObject message);
	
	/**
	 * called when a stallWarning message is received throw the Streaming API
	 * @param stallWarning
	 */
	public void onStallWarning(JSONObject stallWarning);

	/**
	 * called when a Deletion notices message is received throw the Streaming API
	 * @param delete
	 */
	public void onStatusDeletionNotices(JSONObject delete);

	/**
	 * called when a Location Deletion notices message is received throw the Streaming API
	 * @param scrubGeo
	 */
	public void onLocationDeletionNotices(JSONObject scrubGeo);
	
	/**
	 * called when a Location Deletion notices message is received throw the Streaming API
	 * @param scrubGeo
	 */
	public void onLimitNotices(JSONObject limit);
	
	/**
	 * called when a status withheld message is received throw the Streaming API
	 * @param statusWithheld
	 */
	public void onStatusWithheld(JSONObject statusWithheld);
	
	/**
	 * called when a UserWithheld message is received throw the Streaming API
	 * @param userWithheld
	 */
	public void onUserWithheld(JSONObject userWithheld);

	/**
	 * called when a Disconnect  message is received throw the Streaming API
	 * @param userWithheld
	 */
	public void onDisconnectMessages(JSONObject disconnect);
	
	
	
}
