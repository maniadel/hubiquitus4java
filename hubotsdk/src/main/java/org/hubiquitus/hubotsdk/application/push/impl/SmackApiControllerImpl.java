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

package org.hubiquitus.hubotsdk.application.push.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.hubiquitus.hubotsdk.application.Bot;
import org.hubiquitus.hubotsdk.application.BotInterface;
import org.hubiquitus.hubotsdk.application.push.ProcessMessage;
import org.hubiquitus.hubotsdk.application.push.SmackApiController;
import org.hubiquitus.hubotsdk.exception.BotException;
import org.hubiquitus.hubotsdk.iq.provider.DataRequestIQProvider;
import org.hubiquitus.hubotsdk.iq.provider.DataResultIQProvider;
import org.hubiquitus.hubotsdk.iq.provider.PayloadResultIQProvider;
import org.hubiquitus.hubotsdk.iq.provider.PubSubSubscriptionExtensionProvider;
import org.hubiquitus.hapi.model.JabberId;
import org.hubiquitus.hapi.model.impl.DataRequestEntry;
import org.hubiquitus.hapi.model.impl.DataResultEntry;
import org.hubiquitus.hapi.model.impl.NodeRequestEntry;
import org.hubiquitus.hapi.model.impl.PayloadResultEntry;
import org.hubiquitus.hapi.model.impl.UpdateDataRequestEntry;
import org.hubiquitus.hapi.pubsub.impl.ModifiyAffiliationsExtension;
import org.hubiquitus.hapi.pubsub.impl.ModifyAffiliation;
import org.hubiquitus.hubotsdk.service.impl.XmppBotConfiguration;
import org.hubiquitus.hapi.service.impl.XmppComponentConfiguration;
import org.hubiquitus.hapi.utils.IStringUtils;
import org.hubiquitus.hapi.utils.impl.CommonJSonParserImpl;
import org.hubiquitus.hapi.utils.impl.StringUtils;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.IQ.Type;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.PrivateDataManager;
import org.jivesoftware.smackx.packet.DiscoverItems;
import org.jivesoftware.smackx.pubsub.AccessModel;
import org.jivesoftware.smackx.pubsub.ConfigureForm;
import org.jivesoftware.smackx.pubsub.FormType;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.Node;
import org.jivesoftware.smackx.pubsub.NodeExtension;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubElementType;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.PublishModel;
import org.jivesoftware.smackx.pubsub.SimplePayload;
import org.jivesoftware.smackx.pubsub.Subscription;
import org.jivesoftware.smackx.pubsub.Subscription.State;
import org.jivesoftware.smackx.pubsub.SubscriptionsExtension;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;
import org.jivesoftware.smackx.pubsub.packet.PubSub;
import org.jivesoftware.smackx.pubsub.packet.PubSubNamespace;
import org.jivesoftware.smackx.pubsub.packet.SyncPacketSend;
import org.jivesoftware.smackx.pubsub.provider.SubscriptionProvider;
import org.jivesoftware.smackx.pubsub.provider.SubscriptionsProvider;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

public class SmackApiControllerImpl implements SmackApiController, ChatManagerListener, MessageListener {

	Logger logger = LoggerFactory.getLogger(SmackApiControllerImpl.class);

	/**
	 * XMPP Connection object
	 */
	protected XMPPConnection connection;
	
	/**
	 * Domain name
	 */
	protected String domain;

	/**
	 * Pubsub address
	 */
	protected String pubSubAddress;

	/**
	 * PubSub Manager
	 */
	protected PubSubManager psMgr;

	/**
	 * List of pubsub nodes. Used to check if one exists
	 */
	protected List<String> pubSubNodes;

	/**
	 * JSON Parser
	 */
	protected CommonJSonParserImpl jSonParser;
	
	/**
     * hServer component configuration
     */
    protected XmppComponentConfiguration xmppComponentConf;

	/**
	 * Xmpp Bot configuration
	 */
	protected XmppBotConfiguration xmppBotConfiguration;

	/**
	 * Bot interface
	 */
	protected BotInterface botInterface;
	
	
	/**
	 * The message processor
	 */
	protected ProcessMessage processMessage;

    /**
     * Utility for strings.
     */
    private IStringUtils stringUtils = new StringUtils(); 
    
	/**
	 * Constructor
	 */
	public SmackApiControllerImpl() {
		super();
	}


	/**
	 * Constructor
	 */
	public SmackApiControllerImpl(BotInterface botInterface) {
		super();
		this.botInterface = botInterface;
	}

	
	/**
	 * Connect to XMPP Server
	 */
	public void connect(String nodeName)
					throws XMPPException {

		
		// Add news private data providers for data base request on hServer 
		DataRequestIQProvider customPrivateDataIQProvider = new DataRequestIQProvider();
		PayloadResultIQProvider payloadResultIQProvider = new PayloadResultIQProvider();
		DataResultIQProvider dataResultIQProvider = new DataResultIQProvider();
		ProviderManager.getInstance().addIQProvider("query",DataRequestEntry.NAMESPACE, customPrivateDataIQProvider );
		PrivateDataManager.addPrivateDataProvider(PayloadResultEntry.ELEMENTNAME, PayloadResultEntry.NAMESPACE, payloadResultIQProvider);
		PrivateDataManager.addPrivateDataProvider(DataResultEntry.ELEMENTNAME, DataResultEntry.NAMESPACE, dataResultIQProvider);
		ProviderManager.getInstance().addExtensionProvider(PubSubElementType.SUBSCRIPTION.getElementName(), PubSubNamespace.EVENT.getXmlns(), new PubSubSubscriptionExtensionProvider());
		// This provider is used when receiving response of calling method processNodeSubscription(Chat chat, Message message)
		ProviderManager.getInstance().addExtensionProvider(PubSubElementType.SUBSCRIPTIONS.getElementName(), PubSubNamespace.OWNER.getXmlns(), new SubscriptionsProvider());
		// This provider is used when receiving response of calling method getNodeOwnerSubscriptions(String nodeId)
		ProviderManager.getInstance().addExtensionProvider(PubSubElementType.SUBSCRIPTION.getElementName(), PubSubNamespace.OWNER.getXmlns(), new SubscriptionProvider());
				
		if (xmppBotConfiguration.isXmppDebugEnable()) {
			Connection.DEBUG_ENABLED = true;
		} else {
			Connection.DEBUG_ENABLED = false;
		}
		ConnectionConfiguration config = new  ConnectionConfiguration(xmppBotConfiguration.getXmppHostAddress(), xmppBotConfiguration.getXmppHostPort(), xmppBotConfiguration.getXmppService());
		config.setSASLAuthenticationEnabled(true);
		config.setSecurityMode(SecurityMode.required);
		config.setCompressionEnabled(true);
		
		connection = new XMPPConnection(config);
		connection.connect();
		
		String node = "UNKNOWN-NODE";

		if ((nodeName != null) && (!nodeName.equals(""))) {
			node = nodeName;
		}

		logger.debug(xmppBotConfiguration.getXmppUserName() + "-" + xmppBotConfiguration.getXmppUserPassword());
		connection.login(xmppBotConfiguration.getXmppUserName(), xmppBotConfiguration.getXmppUserPassword(), "BOT_" + node + "_" + xmppBotConfiguration.getXmppHostAddress() + "_" + xmppBotConfiguration.getId());
		//getConnection(xmppBotConfiguration.getXmppUserName(), xmppBotConfiguration.getXmppUserPassword(), "BOT_" + node + "_" + xmppBotConfiguration.getXmppHostAddress() + "_" + xmppBotConfiguration.getId());

		domain = connection.getServiceName();

		pubSubAddress = "pubsub." + domain;
		psMgr = new PubSubManager(connection, pubSubAddress);
		
		pubSubNodes = new ArrayList<String>();

		connection.getChatManager().addChatListener(this);

		
		Roster roster = connection.getRoster();
		if (roster != null)
			roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);
	}


	/*
	 * Create node (if not exists) and configure it
	 */
	public void createNode(String node, String title, List<String> allowedRosterGroups) throws BotException, XMPPException {
		try {
			if (!connection.isConnected()) {
				tryToReconnect();
			} 
			if (!connection.isAuthenticated() ) {
				tryToReconnect();
			}
		} catch (InterruptedException e) {
			throw new XMPPException("Create node error: " + e.getMessage(), e);
		}
		if (existsNode(node)) {
			throw new BotException("Node creation aborted. Node " + node
					+ " already exists.");
		}

		LeafNode nod = (LeafNode) psMgr.createNode(node);
		configureNode(nod, title, allowedRosterGroups);
		logger.info("Creating node : " + node);
	}

	/**
	 * Publish message to xmpp node
	 * @param node : the node 
	 * @param jsonSequence: the message to publish in json format
	 * @param eltName: message name
	 * 
	 */
	public void publishToNode(String node, String jsonSequence, String eltName) throws BotException, XMPPException {
		try {
			if (!connection.isConnected()) {
				tryToReconnect();
			} 
			if (!connection.isAuthenticated() ) {
				tryToReconnect();
			}
		} catch (InterruptedException e) {
			throw new XMPPException("Publish to node error: " + e.getMessage(), e);
		}
		
		if (!existsNode(node)) {
			throw new BotException("Publishment aborted. Node " + node
					+ " doesn't exist yet.");
		}
		LeafNode leaf = (LeafNode) psMgr.getNode(node);

		String content = "<entry>" + jsonSequence + "</entry>";
		PayloadItem<SimplePayload> payloadItem = new PayloadItem<SimplePayload>(new SimplePayload(eltName, pubSubAddress, content));
		
		leaf.send(payloadItem);
		
		logger.debug("publishToNode " + node + " jsonSequence=" + content);
		
		if (Bot.getFetcherState().equals(Bot.BotState.ERROR)) {
			Bot.setPublisherState(Bot.BotState.STARTED_ERRORS_RAISED);
		}

	}

	/*
	 * Reload local list of existing pubsub nodes from server
	 */
	protected void discoverPubSubNodes() throws XMPPException {
		try {
			if (!connection.isConnected()) {
				tryToReconnect();
			} 
			if (!connection.isAuthenticated() ) {
				tryToReconnect();
			}
		} catch (InterruptedException e) {
			throw new XMPPException("Discover pubsub nodes error: " + e.getMessage(), e);
		}
		DiscoverItems discoverItems = psMgr.discoverNodes(null);
		Iterator<DiscoverItems.Item> items = discoverItems.getItems();
		pubSubNodes.clear();
		while (items.hasNext()) {
			DiscoverItems.Item item = items.next();
			//logger.debug(item.getEntityID() + "-" + item.getName() + "-" + item.getNode());
			String pubsubNode = item.getNode();
			if (!pubSubNodes.contains(pubsubNode))
				pubSubNodes.add(pubsubNode);
		}
	}


	/*
	 * Try to reconnect to XMPP Server when connection is lost.
	 */
	public boolean tryToReconnect() throws InterruptedException, XMPPException {
		if (!Bot.isStoppingPublisher()) {
			int delay = 5000;
			logger.info("[SmackApiController] Trying to reconnect...");

			//try {
			if (connection.isConnected()) {
				logger.info("[SmackApiController] disconnecting...");
				connection.disconnect();
			}
			Thread.sleep(delay);
			if (!connection.isConnected()) {
				logger.info("[SmackApiController] reconnecting...");
				connection.connect();
				
				if (!connection.isAuthenticated()) {
					
					connection.login(xmppBotConfiguration.getXmppUserName(), 
							xmppBotConfiguration.getXmppUserPassword(), generateId());
				}

				Bot.setPublisherState(Bot.BotState.STARTED);
			}

		}
		return connection.isConnected();
	}
	
	
	 @Override
	 public void sendMessage(String jsonSequence) throws XMPPException  {
         try {
                 if (!connection.isConnected()) {
                        tryToReconnect();
                 }
                 
                 if (!connection.isAuthenticated() ) {
                        tryToReconnect();
                 }
                 
          } catch (InterruptedException e) {
                 throw new XMPPException("Send Message to hserver error: " + e.getMessage(), e);
          }
   
         String xmppUserName = getXmppBotConfiguration().getXmppUserName();
	     String xmppService = getXmppBotConfiguration().getXmppService();
	     
	     String hServer = xmppComponentConf.getSubDomain() + "." + xmppService;
	     logger.debug("hserver: " + hServer);
     
	     Message message = new Message();
	     message.setTo(hServer);
	     message.setFrom(xmppUserName+"@"+xmppService);
	     message.setType(org.jivesoftware.smack.packet.Message.Type.chat);
	     message.setBody(jsonSequence);
	   
	     this.connection.sendPacket(message);
	 }

	
	 
	

	/*
	 * Disconnect from XMPP Server
	 */
	public void disconnect() {
		connection.disconnect();
		psMgr = null;
	}


	/*
	 * check if a node name exists in local pusub nodes list
	 */
	private boolean containsPubSubNode(String node) {
		for (String psn : pubSubNodes) {
			if (psn.equals(node))
				return true;
		}
		return false;
	}

	/*
	 * check if node exists on server
	 */
	public boolean existsNode(String node) throws XMPPException {
		discoverPubSubNodes();
		return containsPubSubNode(node);
	}

	/*
	 * Configure node
	 */
	protected void configureNode(LeafNode node, String title,
			List<String> allowedRosterGroups) throws XMPPException {
		ConfigureForm form = new ConfigureForm(FormType.submit);
		if (allowedRosterGroups.contains("all")) {
			form.setAccessModel(AccessModel.open);
			form.setPublishModel(PublishModel.open);
		} else if (allowedRosterGroups.contains("access.model.authorize")) {
			form.setAccessModel(AccessModel.authorize);
			form.setPublishModel(PublishModel.publishers);
		} else {
			form.setAccessModel(AccessModel.roster);
			List<String> groups = new ArrayList<String>();
			for (String s : allowedRosterGroups) {
				groups.add(s);
			}
			form.setRosterGroupsAllowed(groups);
			form.setPublishModel(PublishModel.publishers);
		}
		form.setDeliverPayloads(true);
		form.setNotifyRetract(true);
		form.setPersistentItems(true);
		form.setTitle(title);
		form.setSubscribe(true);

//		FormField field = new FormField("pubsub#notification_type");
//		field.setType(FormField.TYPE_LIST_SINGLE);
//		field.addValue("normal");
//		form.addField(field);
		
//		field = new FormField("pubsub#purge_offline");
//		field.setType(FormField.TYPE_BOOLEAN);
//		field.addValue("1");
//		form.addField(field);
		
		node.sendConfigurationForm(form);
	}

	/*
	 * Get node object from  XMPP server using node name
	 */
	public LeafNode getLeafNode(String node) throws XMPPException {
		LeafNode leafNode = (LeafNode) psMgr.getNode(node);
		return leafNode;
	}

	/*
	 * Generate unique ID
	 */
	protected String generateId() {
		UUID id = UUID.randomUUID();
		return id.toString();
	}

	/*
	 * Delete node
	 */
	public void deleteNode(String node) throws XMPPException {
		psMgr.deleteNode(node);
	}

	/*
	 * Check if connection is currently active
	 */
	public boolean isConnected() {
		return connection.isConnected();
	}

	/*
	 * Generate final message to be sent
	 */
	private String prepareAdminMessageToSend(String result, String action) {
		String toSend = "{ \"alert\": { \"type\": \"admin\", \"action\": \""
				+ action + "\", \"result\": ";
		if ((!result.startsWith("{")) || (!result.endsWith("}")))
			toSend += "\"" + result + "\" }}";
		else
			toSend += result + " }}";
		return toSend;
	}

	/**
	 * Send message result to chat participant
	 * @param message the message to send
	 * @param chat the chat where message is published
	 */
	protected void sendMessage(String message, Chat chat) {
		try {
			if (message!=null && !"".equals(message)) {
				chat.sendMessage(message);
			}
		} catch (XMPPException e) {
			logger.error(e.getMessage());
		}
	}

	/*
	 * Check if chat participant is member of any authorized (bot admin) XMPP groups
	 */
	protected boolean isAuthorizedUser(Chat chat, List<String> adminGroups) {
		String chatParticipant = chat.getParticipant();
		String use = chatParticipant.split("/")[0];
		RosterEntry rE = connection.getRoster().getEntry(use);
		if (rE != null) {
			Collection<RosterGroup> chatParticipantGroupsCollection = rE
					.getGroups();

			String chatParticipantGroups = "";
			for (RosterGroup r : chatParticipantGroupsCollection) {
				chatParticipantGroups += "<name='" + r.getName()
						+ "'><entries='" + r.getEntries() + "'>";
				for (String s : adminGroups) {
					//if (r.getName().contains(s + " ")) {
					if (r.getName().trim().contains(s.trim())) {
						logger.info("******\nChat participant: ["
								+ chatParticipant + "]\nGroups: ["
								+ chatParticipantGroups + "]\n******");

						return true;
					}
				}
			}
		}
		return false;
		
		
	}


	/*
	 * Ping XMPP Server
	 */
	public void ping() {
		IQ ping = new IQ() {
			@Override
			public String getChildElementXML() {
				return "<ping xmlns='urn:xmpp:ping'/>";
			}
		};
		ping.setTo(domain);
		ping.setPacketID("c2s1");
		ping.setType(Type.GET);

		connection.sendPacket(ping);
		//		} catch (Exception e) {
		//			logger.error(e.getMessage());
		//			bot.addException(e, ExceptionType.PUBLISHER_PING_ERROR);
		//			bot.setPublisherState(Bot.BotState.ERROR);
		//		}
	}

	/*
	 * Manages new chatrooms
	 * (non-Javadoc)
	 * @see org.jivesoftware.smack.ChatManagerListener#chatCreated(org.jivesoftware.smack.Chat, boolean)
	 */
	@Override
	public void chatCreated(Chat chat, boolean createdLocally) {
		if (!createdLocally)
			chat.addMessageListener(this);
	}
	
	/**
	 * Receipts all incoming chat messages and answers
	 */
	public void processMessage(Chat chat, Message message) {
		String messageBody = message.getBody();
		String type = null;
		String content = null;
		String answer = null;
		boolean toStop = false;
		JSONObject jSonObject = null;

		boolean isProcessedNodeSubscription = false;
		try {
			isProcessedNodeSubscription = this.processNodeSubscription(chat, message);
		} catch (XMPPException e) {
			logger.error("Could not process Node Subscription", e);
		}
		if (isProcessedNodeSubscription) {
			return;
		}
		
		try {
			messageBody = stringUtils.unescapeXml(messageBody);
			jSonObject = jSonParser.getJSon(messageBody);
			type = (String) jSonObject.get("type");
			if ( "admin".equals(type)) {
				
				//logger.debug("Admin message received - user: " + chat.getParticipant() + " - message: " + messageBody);
				
				content = (String) jSonObject.get("action");
			
				if (content != null) {
					if (isAuthorizedUser(chat, xmppBotConfiguration.getAdminGroups())) {
						
						if (content.equals("discover")) {
							answer = "{ \"id\" : \"" + xmppBotConfiguration.getId()
									+ "\", \"hostAdress\" : \""
									+ xmppBotConfiguration.getHostAddress() + "\", \"bottype\" : \""
									+ xmppBotConfiguration.getType()
									+ "\", \"mainconfiguration\" : "
									+ botInterface.getMainConfJSON() + " }";
						} else if (content.equals("getState")) {
							answer = "{ \"publisherstate\" : \""
									+ Bot.getPublisherState()
									+ "\", \"fetcherstate\" : \""
									+ Bot.getFetcherState() + "\"}";
						} else if (content.equals("getConfiguration")) {
							answer = xmppBotConfiguration.getXmppConfigurationXML();
						} else if (content.equals("deleteNodeAndShutdown")) {
							answer = "{ \"answer\" : \"stopping fetcher...\" }";
							answer = prepareAdminMessageToSend(answer, content);
							sendMessage(answer, chat);
							try {
								botInterface.stopDataRetriever();
								answer = "{ \"answer\" : \"fetcher stopped\" }";
							} catch (Exception e) {
								answer = "{ \"answer\" : \"error while stopping fetcher\", \"more\" : \""
										+ e.getMessage() + "\" }";
							}
							answer = prepareAdminMessageToSend(answer, content);
							sendMessage(answer, chat);

							answer = "{ \"answer\" : \"deleting node...\" }";
							try {
								deleteNode(xmppBotConfiguration.getNodeName());
								answer = "{ \"answer\" : \"node deleted\" }";
							} catch (XMPPException e) {
								logger.error(e.getMessage());
								answer = "{ \"answer\" : \"error while deleting node\", \"more\" : \""
										+ e.getMessage() + "\" }";
							}
							answer = prepareAdminMessageToSend(answer, content);
							sendMessage(answer, chat);

							answer = "{ \"answer\" : \"stopping publisher...\" }";
							answer = prepareAdminMessageToSend(answer, content);
							sendMessage(answer, chat);
							answer = null;
							toStop = true;

						} else if (content.equals("shutdown")) {
							answer = "{ \"answer\" : \"stopping fetcher...\" }";
							answer = prepareAdminMessageToSend(answer, content);
							sendMessage(answer, chat);
							try {
								botInterface.stopDataRetriever();
								answer = "{ \"answer\" : \"fetcher stopped\" }";
							} catch (Exception e) {
								answer = "{ \"answer\" : \"error while stopping fetcher\", \"more\" : \""
										+ e.getMessage() + "\" }";
							}
							answer = prepareAdminMessageToSend(answer, content);
							sendMessage(answer, chat);
							answer = null;
							toStop = true;
						} else if (content.equals("errors")) {
							answer = "{ \"answer\" : \"" + botInterface.getExceptions()
									+ "\" }";
							answer = prepareAdminMessageToSend(answer, content);
						} else if (content.equals("ping")) {
							answer = "{ \"type\" : \"Bot\", \"id\" : \""
									+ xmppBotConfiguration.getId() + "\", \"hostAdress\" : \""
									+ xmppBotConfiguration.getHostAddress() + "\", \"bottype\" : \""
									+ xmppBotConfiguration.getType() + "\", \"date\" : \""
									+ new Date() + "\" }";
							answer = prepareAdminMessageToSend(answer, content);

							
						} 
						/*  
						 * // -- Not to use --
						else if (content.equals("deleteAllNodes")) {
							for (int i=0; i<pubSubNodes.size(); i++) {
								String node = pubSubNodes.get(i);
								try {
									System.out.println("Deleting node:" + node);
									deleteNode(node);
									answer = "{ \"answer\" : \"node " + node + " deleted\" }";
								} catch (XMPPException e) {
									logger.error(e.getMessage());
									answer = "{ \"answer\" : \"error while deleting node\", \"more\" : \""
											+ e.getMessage() + "\" }";
								}
								
							}
							answer = prepareAdminMessageToSend(answer, content);
							sendMessage(answer, chat);
						}
						*/
					} else {
						answer = "{ \"answer\" : \"unauthorized user\" }";
						answer = prepareAdminMessageToSend(answer, content);
					}
					sendMessage(answer, chat);
				}
			
				
			// Other types, each bot process the message
			} else {
				if (processMessage!=null) {
					answer = processMessage.getMessageAnswer(botInterface, jSonObject);
					if (answer!=null) {
						answer = "{ \"alert\":" + answer + "}";
						sendMessage(answer, chat);
					}
				} 
			}
			
		} catch (ParseException e) {
			logger.error("Parsing error", e);
			answer = "{ \"answer\" : \"json parsing error\" }";
			answer = prepareAdminMessageToSend(answer, content);
			sendMessage(answer, chat);
		}

		if (toStop) {
			botInterface.stopXmppPublisher();
		}

	}

	private boolean processNodeSubscription(Chat chat, Message message) throws XMPPException {
		logger.debug("START processNodeSubscription");
		boolean result = pubSubAddress.equals(message.getFrom());

		if (result) {
			// Get the message with the form to fill out
	        Message response = new Message();
	        response.setPacketID(message.getPacketID());
	        response.setFrom(message.getTo());
	        response.setTo(message.getFrom());
	        
	        // Retrieve the form to fill out from the message
	        Form formToRespond = Form.getFormFrom(message);
	        if (formToRespond != null) {
		        // Obtain the form to send with the replies
		        Form completedForm = formToRespond.createAnswerForm();
		        // Add the answers to the form
		        String variable = "pubsub#subid";
		        FormField field = formToRespond.getField(variable);
		        if (field != null) {
		        	completedForm.setAnswer(variable, field.getValues().next());
		        }
		        variable = "pubsub#node";
		        field = formToRespond.getField(variable);
		        String nodeId = field.getValues().next();
	        	completedForm.setAnswer(variable, nodeId);

		        variable = "pubsub#subscriber_jid";
		        String subscriberJid = null;
		        field = formToRespond.getField(variable);
		        if (field != null) {
		        	subscriberJid = field.getValues().next();
		        	completedForm.setAnswer(variable, subscriberJid);
		        }

		        variable = "pubsub#allow";
		        boolean allow = true;
		        if (this.isUserAlreadySubscribedToNode(subscriberJid, nodeId)) {
		        	allow = false;
		        }
		        completedForm.setAnswer(variable, allow);
		     	
		        response.setThread(null);
		        response.setBody(null);
		        // Add the completed form to the message to send back
		        response.addExtension(completedForm.getDataFormToSend());
		        // Send the message with the completed form
		        connection.sendPacket(response);
	        }
		}
		logger.debug("END processNodeSubscription");
		return result;
	}

	@Override
	public DataRequestEntry sendDbRequest(DataRequestEntry dataRequestEntry) throws XMPPException {
		logger.debug("sendDbRequest:" + dataRequestEntry.entryToJsonFormat());
		try {
			if (!connection.isConnected()) {
				tryToReconnect();
			} 
			if (!connection.isAuthenticated() ) {
				tryToReconnect();
			}
		} catch (InterruptedException e) {
			throw new XMPPException("Send dbRequest error: " + e.getMessage(), e);
		}
		
		
		dataRequestEntry.setPacketID(dataRequestEntry.getRequestId());
		dataRequestEntry.setType(IQ.Type.GET);
		String hServer = xmppComponentConf.getSubDomain() + "." + xmppBotConfiguration.getXmppHostAddress();
		logger.debug("hserver:" + hServer);
		
		// Address the packet to the hServer component
		dataRequestEntry.setTo(hServer);	
		
		// Setup a listener for the reply to the set operation.
        PacketCollector collector = connection.createPacketCollector(new PacketIDFilter(dataRequestEntry.getPacketID()));
        
        // Send the private data.
        connection.sendPacket(dataRequestEntry);

        // Wait up to five seconds for a response from the server.
        IQ response = (IQ) collector.nextResult(xmppComponentConf.getRequestTimeOut());
        
        // No response from the server
        if (response == null) {
            throw new XMPPException("No response from the server for the sendDbRequest id:" + dataRequestEntry.getRequestId());
        } else if (response.getType() == IQ.Type.ERROR) {
            throw new XMPPException("Error for sendDbRequest id:" + dataRequestEntry.getRequestId() + " : " +  response.getError());
        }
        
        // Stop queuing results
        collector.cancel();
        
        // Transform the response 
        //logger.debug("response.toXML():" + response.toXML()) ;
        DataRequestEntry answer = dataRequestEntry.createCopy();
        
        answer.addDataResultsEntry(response.toXML());
        
        return  answer;
	}
	
	@Override
	public void sendUpdateDbRequest(UpdateDataRequestEntry updateDataRequestEntry) throws XMPPException {
		logger.debug("sendUpdateDbRequest:" + updateDataRequestEntry.entryToJsonFormat());
		try {
			if (!connection.isConnected()) {
				tryToReconnect();
			} 
			if (!connection.isAuthenticated() ) {
				tryToReconnect();
			}
		} catch (InterruptedException e) {
			throw new XMPPException("Send dbRequest error: " + e.getMessage(), e);
		}
		
		
		updateDataRequestEntry.setPacketID(updateDataRequestEntry.getUpdateDataQuery().getRequestId());
		updateDataRequestEntry.setType(IQ.Type.SET);
		String hServer = xmppComponentConf.getSubDomain() + "." + xmppBotConfiguration.getXmppHostAddress();
		logger.debug("hserver:" + hServer);
		
		// Address the packet to the hServer component
		updateDataRequestEntry.setTo(hServer);	
		
		// Setup a listener for the reply to the set operation.
        PacketCollector collector = connection.createPacketCollector(new PacketIDFilter(updateDataRequestEntry.getPacketID()));
        
        // Send the private data.
        connection.sendPacket(updateDataRequestEntry);

        // Wait up to five seconds for a response from the server.
        IQ response = (IQ) collector.nextResult(xmppComponentConf.getRequestTimeOut());
        
        // No response from the server
        if (response == null) {
            throw new XMPPException("No response from the server for the sendDbRequest id:" + updateDataRequestEntry.getUpdateDataQuery().getRequestId());
        } else if (response.getType() == IQ.Type.ERROR) {
            throw new XMPPException("Error for sendDbRequest id:" + updateDataRequestEntry.getUpdateDataQuery().getRequestId() + " : " +  response.getError());
        }
        
        // Stop queuing results
        collector.cancel();
        
        // Transform the response 
        //logger.debug("response.toXML():" + response.toXML()) ;
//        DataRequestEntry answer = dataRequestEntry.createCopy();
//        
//        answer.addDataResultsEntry(response.toXML());
//        
//        return  answer;
	}

	@Override
	public void subscribeNode(String subscribeNode, ItemEventListener<Item> itemEventListener) throws XMPPException {
		try {
			if (!connection.isConnected()) {
				tryToReconnect();
			} 
			if (!connection.isAuthenticated() ) {
				tryToReconnect();
			}
		} catch (InterruptedException e) {
			throw new XMPPException("Subscription error to node: " + subscribeNode +  " - " + e.getMessage(), e);
		}
		
    	// Subscribe node
		discoverPubSubNodes();
    	Node leafNodeToRead =  psMgr.getNode(subscribeNode);
    		
		if (leafNodeToRead == null) {
			logger.error("Subscription error: " + subscribeNode + " not found on server.");
			throw new XMPPException("Subscription error: " + subscribeNode +  " not found on server.");
		} else {
			
            // Subscribe to the node 
			String user = connection.getUser();
			if (!this.isUserAlreadySubscribedToNode(user, subscribeNode)) {
				leafNodeToRead.subscribe(user);
			}
			// Each time someone publish to this node then the event is fired to the each listener
			if (itemEventListener != null) {
				leafNodeToRead.addItemEventListener(itemEventListener);
			}
			
			logger.debug("Succeed to suscribe node " + subscribeNode);
			
		}
	}

	private boolean isUserAlreadySubscribedToNode(String subscriberJid, String nodeId) throws XMPPException {
		boolean result = false;
        // Check if the entity is already subscribe to this node
        Node node = psMgr.getNode(nodeId);
        if (node != null) {
        	// First Try to Check from the node Subscriptions List: this never thrown any exception 
        	List<Subscription> subscriptions = node.getSubscriptions();
        	result = isSubscriberInSubscriptions(subscriberJid, subscriptions);
        	if (!result) {
        		// Second Try to check from Node Owner Subscriptions List: this may thrown exception if the node is not belong the connected owner
        		try {
        			subscriptions = this.getNodeOwnerSubscriptions(nodeId);
            		result = isSubscriberInSubscriptions(subscriberJid, subscriptions);
        		} catch (Exception e) {
        			logger.warn("The node "+ nodeId + " may not belong to the user " + xmppBotConfiguration.getXmppUserName(), e);
        		}
        	}
        }
		return result;
	}
	
	private boolean isSubscriberInSubscriptions(String subscriberJid, List<Subscription> subscriptions) {
		boolean result = false;
		if (subscriptions != null && subscriberJid != null) {
    		for (Iterator<Subscription> iterator = subscriptions.iterator(); iterator.hasNext();) {
				Subscription subscription = (Subscription) iterator.next();
				JabberId subscriberJidObject = new JabberId(subscriberJid);
				JabberId subscriptionJidObject = new JabberId(subscription.getJid());
				State state = subscription.getState();
				if (subscriberJidObject.getBareJid().equals(subscriptionJidObject.getBareJid())
					&& State.subscribed.equals(state)) {
					result = true;
					break;
				}
			}
    	}
		return result;
	}
	
	/**
	 * Get the subscriptions currently associated with this node.
	 * 
	 * @return List of {@link Subscription}
	 * 
	 * @throws XMPPException
	 */
	public List<Subscription> getNodeOwnerSubscriptions(String nodeId)
		throws XMPPException {
		PubSub request = new PubSub();
		JabberId userJid = new JabberId(connection.getUser());
		request.setFrom(userJid.getBareJid());
		request.setTo(pubSubAddress);
		request.setType(Type.GET);
		request.setPubSubNamespace(PubSubNamespace.OWNER);
		PacketExtension extension = new NodeExtension(PubSubElementType.SUBSCRIPTIONS, nodeId);
		request.addExtension(extension);
		Packet reply = SyncPacketSend.getReply(connection, request);
		// Normally, we have to get the extension with PubSubElementType.SUBSCRIPTIONS.getElementName() and PubSubElementType.OWNER.getNamespace().getXmlns()
		// But the provider will generate extension with PubSubElementType.SUBSCRIPTIONS.getElementName() and PubSubElementType.SUBSCRIPTIONS.getNamespace().getXmlns()
		SubscriptionsExtension subElem = (SubscriptionsExtension) reply.getExtension(
				PubSubElementType.SUBSCRIPTIONS.getElementName(), PubSubElementType.SUBSCRIPTIONS.getNamespace().getXmlns());
		return subElem.getSubscriptions();
	}

	
	public IQ sendNodeRequest(NodeRequestEntry nodeRequestEntry) throws XMPPException {
		try {
			if (!connection.isConnected()) {
				tryToReconnect();
			} 
			if (!connection.isAuthenticated() ) {
				tryToReconnect();
			}
		} catch (InterruptedException e) {
			throw new XMPPException("Send NodeRequest error: " + e.getMessage(), e);
		}
		
		String hServer = xmppComponentConf.getSubDomain() + "." + xmppBotConfiguration.getXmppHostAddress();
		logger.debug("hserver:" + hServer);

		// Address the packet to the hServer component
		nodeRequestEntry.setTo(hServer);

		// Setup a listener for the reply to the set operation.
		PacketCollector collector = connection.createPacketCollector(new PacketIDFilter(nodeRequestEntry.getPacketID()));
		
		// Send the private data.
		connection.sendPacket(nodeRequestEntry);

		// Wait up to five seconds for a response from the server.
		IQ response = (IQ) collector.nextResult(xmppComponentConf.getRequestTimeOut());

		// No response from the server
		if (response == null) {
			throw new XMPPException("No response from the server for the sendNodeRequest id:"
							+ nodeRequestEntry.getRequestId());
		}
		// Stop queuing results
		collector.cancel();

		// Transform the response
		logger.debug("response.toXML():" + response.toXML());

		return response;
	}
	
	public void sendNodeCreationRequest(final NodeRequestEntry nodeRequestEntry) {
		try {
			this.sendNodeRequest(nodeRequestEntry);
		} catch (XMPPException e) {
			logger.error("Could not create node", e);
		}
	}

	public void modifyAffiliation(String node, String modifiedJid, String affiliation) throws XMPPException {
    	PubSub psPacket = new PubSub();
    	psPacket.setFrom(connection.getUser());
    	psPacket.setTo(pubSubAddress);
    	psPacket.setPubSubNamespace(PubSubNamespace.OWNER);
    	psPacket.setType(IQ.Type.SET);
    	List<ModifyAffiliation> subList =  new ArrayList<ModifyAffiliation>();
    	modifiedJid = modifiedJid + "@" + connection.getServiceName();
    	subList.add(new ModifyAffiliation(modifiedJid, ModifyAffiliation.Type.valueOf(affiliation)));
    	psPacket.addExtension(new ModifiyAffiliationsExtension(node, subList));
    	Packet response = SyncPacketSend.getReply(connection, psPacket);
    	XMPPError error = response.getError();
    	if (error != null) {
    		String message = error.getMessage();
    		logger.error(message);
    		throw new XMPPException(message);
    	}
	}

	// Getters and setters
	public BotInterface getBotInterface() {
		return this.botInterface;
	}

	public XmppBotConfiguration getXmppBotConfiguration() {
		return xmppBotConfiguration;
	}
	
	/**
	 * getter processMessage
	 * @return processMessage
	 */
	public ProcessMessage getProcessMessage() {
		return processMessage;
	}

	/**
	 * setter processMessage
	 * @param processMessage the processMessage to set
	 */
	public void setProcessMessage(ProcessMessage processMessage) {
		this.processMessage = processMessage;
	}
	
	// Spring injections ---------------------------------------------------------------
	@Required
	public void setJSonParser(CommonJSonParserImpl jSonParser) {
		this.jSonParser = jSonParser;
	}

	@Required
	public void setXmppBotConfiguration(XmppBotConfiguration xmppBotConfiguration) {
		this.xmppBotConfiguration = xmppBotConfiguration;
	}
	
	@Required
	public void setBotInterface(BotInterface botInterface) {
		this.botInterface = botInterface;
	}
	
	@Required
	public void setXmppComponentConf(XmppComponentConfiguration xmppComponentConf) {
		this.xmppComponentConf = xmppComponentConf;
	}

	/**
	 * @param stringUtils the stringUtils to set
	 */
	public void setStringUtils(IStringUtils stringUtils) {
		this.stringUtils = stringUtils;
	}
}

