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


package org.hubiquitus.hserver.pubsub.impl;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hubiquitus.hapi.pubsub.impl.ModifiyAffiliationsExtension;
import org.hubiquitus.hapi.pubsub.impl.ModifyAffiliation;
import org.hubiquitus.hapi.service.impl.XmppPubSubConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.pubsub.NodeExtension;
import org.jivesoftware.smackx.pubsub.PubSubElementType;
import org.jivesoftware.smackx.pubsub.SubscribeExtension;
import org.jivesoftware.smackx.pubsub.packet.PubSub;
import org.jivesoftware.smackx.pubsub.packet.PubSubNamespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.IQ;

/**
 * Helper to mainly convert Smack PubSub Packet to Whack packet.
 * 
 * @author m.ma
 *
 */
public class PubSubRequester {

	private Logger logger = LoggerFactory.getLogger(PubSubRequester.class);
	
	/**
	 * XMPP Connection object
	 */
	protected XMPPConnection connection;

	/**
	 * Xmpp Bot configuration
	 */
	protected XmppPubSubConfiguration xmppPubSubConfiguration;

	private String pubSubAddress;

	private String hServerAddress = "hserver.nsltdpn0094";

	public PubSubRequester() {
	}

	public IQ getPubSubCreateNode(String nodeName) {
		IQ result = null;
    	PubSub psPacket = new PubSub();
    	psPacket.setFrom(hServerAddress);
    	psPacket.setTo(pubSubAddress);
    	psPacket.setPubSubNamespace(PubSubNamespace.BASIC);
    	psPacket.setType(org.jivesoftware.smack.packet.IQ.Type.SET);
    	psPacket.addExtension(new NodeExtension(PubSubElementType.CREATE, nodeName));
    	//psPacket.addExtension(new NodeExtension(PubSubElementType.CONFIGURE, null));

    	String pubSubPacketToXml = psPacket.toXML();
    	logger.debug("PubSub Packet to xml " + pubSubPacketToXml);
    	Element element = null;
    	try {
			element = DocumentHelper.parseText(pubSubPacketToXml).getRootElement();
		} catch (DocumentException e) {
	    	logger.warn("Could convert PubSub Packet " + pubSubPacketToXml + " to Standard Packet " + e);
		}
    	if (element != null) {
    		result =  new IQ(element);
	    	logger.debug("Standard Packet to xml " + result.toXML());
    	}
    	return result;
	}
	
	
	
	public IQ getPubSubCreateNodeAffiliation(String node, String jid, String affiliation) {
		IQ result = null;
    	PubSub psPacket = new PubSub();
    	psPacket.setFrom(hServerAddress);
    	psPacket.setTo(pubSubAddress);
    	psPacket.setPubSubNamespace(PubSubNamespace.OWNER);
    	psPacket.setType(org.jivesoftware.smack.packet.IQ.Type.SET);
    	List<ModifyAffiliation> subList =  new ArrayList<ModifyAffiliation>();
    	subList.add(new ModifyAffiliation(jid, ModifyAffiliation.Type.valueOf(affiliation)));
    	psPacket.addExtension(new ModifiyAffiliationsExtension(node, subList));
    	String pubSubPacketToXml = psPacket.toXML();
    	logger.debug("PubSub Packet to xml " + pubSubPacketToXml);
    	Element element = null;
    	try {
			element = DocumentHelper.parseText(pubSubPacketToXml).getRootElement();
		} catch (DocumentException e) {
	    	logger.warn("Could convert PubSub Packet " + pubSubPacketToXml + " to Standard Packet " + e);
		}
    	if (element != null) {
    		result =  new IQ(element);
	    	logger.debug("Standard Packet to xml " + result.toXML());
    	}
    	return result;
	}

	public IQ getPubSubSubscribeNode(String node, String fromJid, String jid) {
		IQ result = null;
    	PubSub psPacket = new PubSub();
    	//psPacket.setFrom(hServerAddress);
    	psPacket.setFrom(fromJid);
    	psPacket.setTo(pubSubAddress);
    	psPacket.setType(org.jivesoftware.smack.packet.IQ.Type.SET);
    	psPacket.addExtension(new SubscribeExtension(jid, node));
    	String pubSubPacketToXml = psPacket.toXML();
    	logger.debug("PubSub Packet to xml " + pubSubPacketToXml);
    	Element element = null;
    	try {
			element = DocumentHelper.parseText(pubSubPacketToXml).getRootElement();
		} catch (DocumentException e) {
	    	logger.warn("Could convert PubSub Packet " + pubSubPacketToXml + " to Standard Packet " + e);
		}
    	if (element != null) {
    		result =  new IQ(element);
	    	logger.debug("Standard Packet to xml " + result.toXML());
    	}
    	return result;
	}

//	public static void main(String[] args) {
//		PubSubRequester p = new PubSubRequester();
//		System.out.println(p.getPubSubSubscribeNode("node", "jid"));
//	}
//	
	// Spring injections ------------------------------------
	/**
	 * @param pubSubAddress the pubSubAddress to set
	 */
	public void setPubSubAddress(String pubSubAddress) {
		this.pubSubAddress = pubSubAddress;
	}

	// Spring injections ------------------------------------
	/**
	 * @param hServerAddress the hServerAddress to set
	 */
	public void setHServerAddress(String hServerAddress) {
		this.hServerAddress = hServerAddress;
	}
}
