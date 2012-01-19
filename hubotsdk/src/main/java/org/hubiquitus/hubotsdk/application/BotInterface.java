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

package org.hubiquitus.hubotsdk.application;

import java.util.List;

import org.hubiquitus.hubotsdk.exception.BotException;
import org.hubiquitus.hapi.model.ExceptionType;
import org.hubiquitus.hapi.model.PublishEntry;
import org.hubiquitus.hapi.model.impl.DataRequestEntry;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;

public interface BotInterface {

		
	/**
	 * Method which starts the XMPP publisher and create Node
	 * 
	 */
	public void startPublisherAndDefineNode();
	
	
	public void startDataRetriever() throws BotException;
	
    /*
      * stop bot
      */
    public void stop();

    /*
      * ping xmpp server
      */
    public void ping();

    
    /*
     * publish entry content to pubsub node
     */
   public void publish(PublishEntry entry);
   
   
   /**
    * Bot subscription to node
    * @param node the node name
    * @param itemEventListener the item event listener
    */
   public void subscribeNode(String node, ItemEventListener<Item> itemEventListener) throws BotException;
   
   /**
    * Publish a {@link PublishEntry} to a node
    * (The hserver receive this message)
    * @param entry the {@link PublishEntry} to publish
    * @param node the node
    */
   public void publishToNode(PublishEntry entry, String node);
  
  
   /**
    * Send a DB request to the hServer
    * @param dataRequestEntry the {@link DataRequestEntry}
    * @return the {@link DataRequestEntry} with the result
    */
   public DataRequestEntry sendDbRequest(DataRequestEntry dataRequestEntry);

    public void stopXmppPublisher();

    public void stopDataRetriever();

    /*
      * addException to collector
      */
    public void addException(Exception e, ExceptionType publishingError);

    public String getNodeName();
    
    
    
    public String getTitle();
    
    public List<String> getAllowedRosterGroups();
    
    public String getMainConfJSON();
        
    public String getExceptions();
    
    public String getXmlConfPath();
    
    public void setXmlConfPath(String xmlConfPath);

}
