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

import org.hubiquitus.hubotsdk.application.BotInterface;
import org.hubiquitus.hubotsdk.exception.BotException;
import org.hubiquitus.hapi.model.impl.DataRequestEntry;
import org.hubiquitus.hapi.model.impl.UpdateDataRequestEntry;
import org.hubiquitus.hapi.utils.impl.CommonJSonParserImpl;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;

public interface SmackApiController {

	/**
	 * Connect to XMPP Server
	 * 
	 * @param nodeName
	 * @throws XMPPException
	 */
	public void connect(String nodeName)
            		throws XMPPException;
	
	/**
	 *  Create node (if not exists) and configure it
	 * 
	 * @param node
	 * @param title
	 * @param allowedRosterGroups
	 * @throws BotException
	 * @throws XMPPException
	 */
	public void createNode(String node, String title,
            List<String> allowedRosterGroups) throws BotException, XMPPException;
	
	/**
	 * Publish sequence to node
	 * 
	 * @param node
	 * @param jsonSequence
	 * @param eltName
	 * @throws BotException
	 * @throws XMPPException
	 */
	public void publishToNode(String node, String jsonSequence, String eltName)
            throws BotException, XMPPException;
	
	/**
	 * Try to reconnect to XMPP Server when connection is lost.
	 * 
	 * @return
	 * @throws InterruptedException
	 * @throws XMPPException
	 */
	public boolean tryToReconnect() throws InterruptedException, XMPPException;
	
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
     * check if node exists on server
     */
	public boolean existsNode(String node) throws XMPPException;
	
	/**
     * Get node object from  XMPP server using node name
     */
	public LeafNode getLeafNode(String node) throws XMPPException;
	
	/**
	 * Delete node
	 * 
	 * @param node
	 * @throws XMPPException
	 */
	public void deleteNode(String node) throws XMPPException;
	
	
	/**
	 * Check if connection is currently active
	 * 
	 * @return
	 */
	public boolean isConnected();
	
	/**
	 * Ping XMPP Server
	 */
	public void ping();
	
	/**
	 * Manages new chatrooms
     * (non-Javadoc)
     * @see org.jivesoftware.smack.ChatManagerListener#chatCreated(org.jivesoftware.smack.Chat, boolean)
	 * @param chat
	 * @param createdLocally
	 */
	public void chatCreated(Chat chat, boolean createdLocally);
	
	
	/**
	 * Send jsonSequence in a message to the hServer component
	 * @param jsonSequence
	 * @throws XMPPException
	 */
	 public void sendMessage(String jsonSequence) throws XMPPException;
	 
	 
	
	
	/**
	 * Receipts all incoming chat messages and answers
     * (non-Javadoc)
     * @see org.jivesoftware.smack.MessageListener#processMessage(org.jivesoftware.smack.Chat, org.jivesoftware.smack.packet.Message)
	 * 
	 * @param chat
	 * @param message
	 */
	public void processMessage(Chat chat, Message message);
	
	
	/**
	 * Send DB request to the hServer
	 * @param dataRequestEntry the {@link DataRequestEntry} to send
	 * @return the {@link DataRequestEntry} with the result
	 */
	public DataRequestEntry sendDbRequest(DataRequestEntry dataRequestEntry)  throws XMPPException;
	
	/**
    * Bot subscription to node
    * @param subscribeNode the node name to subscribe
    * @param itemEventListener the item event listener
    * @throws XMPPException xmpp exception
    */
	public void subscribeNode(String subscribeNode, ItemEventListener<Item> itemEventListener) throws XMPPException;
	
	void sendUpdateDbRequest(UpdateDataRequestEntry updateDataRequestEntry) throws XMPPException;
	
	public BotInterface getBotInterface();
	
	
	public void setJSonParser(CommonJSonParserImpl jSonParser);

}
