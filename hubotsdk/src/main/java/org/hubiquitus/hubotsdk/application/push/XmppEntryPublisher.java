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
package org.hubiquitus.hubotsdk.application.push;

import java.util.List;

import org.hubiquitus.hubotsdk.exception.BotException;
import org.hubiquitus.hapi.model.PublishEntry;
import org.hubiquitus.hapi.model.impl.DataRequestEntry;
import org.hubiquitus.hapi.model.impl.UpdateDataRequestEntry;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.Item;


public interface XmppEntryPublisher {

	
	/**
	 * Check if node with the given name already exists in server
	 * 
	 * @param node
	 * @return
	 * @throws XMPPException
	 */
	public boolean checkExists(String node) throws XMPPException;
	
	/**
	 * Check if node with the given name already exists in server
	 * 
	 * @param node
	 * @param title
	 * @param allowedRosterGroups
	 * @throws BotException
	 * @throws XMPPException
	 */
	public void createNode(String node, String title, List<String> allowedRosterGroups) throws BotException, XMPPException;


	public void run();
	
	/**
	 * Publish  entry to node
	 * @param entry {@link PublishEntrey}
	 * @param node the node where entry is published
	 */
	public void publishToNode(PublishEntry entry, String node);
	
	
	/**
	 * Publish PublishEntrey to node  and send it to hserver
	 * 
	 * @param entry then {@link PublishEntrey}
	 * @param node the node where entry is published
	 */
	public void publish(PublishEntry entry, String node);
	
	
	
	/**
	 * Send PublishEntrey to hServer
	 * 
	 * @param entry the {@link PublishEntry}
	 */
	public void sendMessage(PublishEntry entry);
	
	
	/**
	 * Send DB request to the hServer
	 *
	 * @param dataRequestEntry the {@link DataRequestEntry} to send
	 * @return the {@link DataRequestEntry} with the result
	 */
	public DataRequestEntry sendDbRequest(DataRequestEntry dataRequestEntry);
	

	/**
	 * Send DB request to the hServer for updating data
	 *
	 * @param updateDataRequestEntry the {@link UpdateDataRequestEntry} to send
	 */
	public void sendUpdateDbRequest(UpdateDataRequestEntry updateDataRequestEntry);
	
	/**
     * Retrieve items from a node 
      * @param nodeName the node name
     * @param maxItems number of items to retrieve
     * @return the list of items
     * @throws XMPPException xmpp exception
     */
     public List<Item> getItemsFromNode(String nodeName, int maxItems) throws XMPPException;

	
	/**
	 * Disconnect from XMPP Server
	 */
	public void disconnect();
	
	/**
	 * Ping XMPP Server
	 * @throws Exception
	 */
	public void ping() throws Exception;

	void modifyAffiliation(String subscribedNodeName, String userAffiliation, String subscribedNodeAffiliationType);
}
