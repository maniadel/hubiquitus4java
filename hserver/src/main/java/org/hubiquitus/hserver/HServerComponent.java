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


package org.hubiquitus.hserver;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Element;
import org.hubiquitus.hapi.model.NodeQuery;
import org.hubiquitus.hapi.model.ObjectMapperInstanceDomainKey;
import org.hubiquitus.hapi.model.impl.ComplexPublishEntry;
import org.hubiquitus.hapi.model.impl.DataRequestEntry;
import org.hubiquitus.hapi.model.impl.DataResultEntry;
import org.hubiquitus.hapi.model.impl.DbOperator;
import org.hubiquitus.hapi.model.impl.KeyRequest;
import org.hubiquitus.hapi.model.impl.Limit;
import org.hubiquitus.hapi.model.impl.MessagePublishEntry;
import org.hubiquitus.hapi.model.impl.NodeRequestEntry;
import org.hubiquitus.hapi.model.impl.ParamRequest;
import org.hubiquitus.hapi.model.impl.PayloadResultEntry;
import org.hubiquitus.hapi.model.impl.SortRequest;
import org.hubiquitus.hapi.model.impl.UpdateDataRequestEntry;
import org.hubiquitus.hapi.utils.IStringUtils;
import org.hubiquitus.hapi.utils.impl.CommonJSonParserImpl;
import org.hubiquitus.hapi.utils.impl.DateUtils;
import org.hubiquitus.hapi.utils.impl.StringUtils;
import org.hubiquitus.hserver.pubsub.impl.PubSubRequester;
import org.hubiquitus.persistence.DataBase;
import org.hubiquitus.persistence.exeption.DBException;
import org.hubiquitus.persistence.impl.DataBaseMongoImpl;
import org.jivesoftware.smackx.pubsub.PublishModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmpp.component.AbstractComponent;
import org.xmpp.component.ComponentException;
import org.xmpp.packet.IQ;
import org.xmpp.packet.IQ.Type;
import org.xmpp.packet.Message;

/**
 * HServer xmpp component 
 * @author v.dibi
 *
 */
@Named
public class HServerComponent extends AbstractComponent {
	 
	Logger logger = LoggerFactory.getLogger(HServerComponent.class);
	
	/**
	 * Json convert in object java
	 */
		
	protected PublishModel publishModel;
	/**
	 * JSON Parser
	 */
	protected CommonJSonParserImpl jSonParser;
	
	/**
	 * hServer configuration
	 */
	protected HServerConfiguration hServerConfiguration;
	
	/**
	 * hServer data base configuration
	 */
	DataBase hServerDataBase;
	
	/**
	 * PubSub Helper to convert PubSub Smack Object to Whack Object.
	 */
	private PubSubRequester pubSubRequester;
	
	/**
	 * Map used for synchronizing server response 
	 */
    private Map<String, IQ> nodesWaitingForNotify = new HashMap<String, IQ>();
    
    /**
     * Utility for strings.
     */
    private IStringUtils stringUtils = new StringUtils(); 

	/**
	 * HServer constructor
	 * AbstractComponent will no longer respond with service-unavailable errors if null is returned by any of those two methods
	 */
	public HServerComponent() {
	    super(17, 1000, false);
		
	}
	
	/**
	 * getter jSonParser
	 * @return the jSonParser
	 */
    public CommonJSonParserImpl getjSonParser() {
		return jSonParser;
	}

	@Override
    public String getName() {
        return hServerConfiguration.getSubDomain();
    }

    @Override
    protected void handleMessage(Message original) {
    	// Try with message body
    	String content = original.getBody();
    	if (content ==  null) {
    		// Exclude delayed message
    		Element delay = original.getChildElement("delay", "urn:xmpp:delay");
    		if (delay == null) {
	        	Element event = original.getChildElement("event", "http://jabber.org/protocol/pubsub#event");
	        	Element items = event.element("items");
	        	if (items !=null) {
	            	Element item = items.element("item");
	            	if (item != null) {
	            		Element entry = item.element("entry");
	            		if (entry != null) {
	            			content = entry.getText();
	            		}
	            	}
	        	}
    		} else {
    			logger.warn("Exclude delayed message");
    		}
    	}
    	/*
    	Message response = original.createCopy();
        response.setTo(hServerConfiguration.getXmppusername() + "@" + hServerConfiguration.getXmppServerHost());
        response.setFrom(original.getTo());
        response.setBody(originalBody);
        
        
        // Send the response message
        this.send(response);
        */
    	content = stringUtils.unescapeXml(content);
    	if (content !=  null) {
    		content = CommonJSonParserImpl.decode(content);
    		logger.debug("Content:" + content);
    		
    		// check the first characters of content
    		if(content.startsWith("<iq")){
    			if(content.contains("xmlns=\"hubiquitus:monitoring\"")){
    				//logger.error("Dans le bon IQ");
    				String answer = "";
    				Message response = original.createCopy();
    				
    				// XML Parsing
    				DocumentBuilder parser = null;
    				Document document = null;
					try {
						parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
						document = (Document) parser.parse(new InputSource(new StringReader(content)));
					} catch (ParserConfigurationException e) {logger.error("Parsing error");}
					catch (SAXException e) {logger.error("SAX exception");} 
					catch (IOException e){logger.error("IO Exception");} 
					
					org.w3c.dom.Element iq = (org.w3c.dom.Element) document.getElementsByTagName("iq").item(0);
					String from = iq.getAttribute("from");
					String to = iq.getAttribute("to");
					//logger.error("from : " + from);
					//logger.error("to : " + to);
    				
    				DataBaseMongoImpl dataBaseMongoImpl = (DataBaseMongoImpl) hServerDataBase;
    				List<String> databaseNames = dataBaseMongoImpl.getDatabaseNames();
    				if(databaseNames != null){
    					answer = "{\"alert\":{\"type\":\"hServer\", \"action\":\"exists\"}}";
    				}
    				else{
    					answer = "{\"alert\":{\"type\":\"hServer\", \"action\":\"dead\"}}";
    				}
    				//logger.error("answer : " + answer);
    				response.setTo(from);
    				response.setFrom(to);
    				response.setBody(answer);
    				this.send(response);
    			}
    		}
    		else if(content.startsWith("{")){
	        // Interception for data base storage
		        JSONObject jSonObject;
				try {
					//parse the "original messag" in format JSON
					jSonObject = jSonParser.getJSon(content);
					
					logger.debug("Message json: " + jSonObject.toString());
					
					String convId = (String) jSonObject.get(ComplexPublishEntry.CONVID);
					//logger.debug("convId - convId received: " + convId);
					String msgId = (String) jSonObject.get(MessagePublishEntry.MSGID);
					//logger.debug("msgId - msgId received: " + msgId);
		
					//message is type complexePublishEntry
					if (convId!=null && !"".equals(convId)) {
						hServerDataBase.saveComplexPublishEntry(jSonObject);			
					
					//message is type MessagePublishEntry
					} else if (msgId!=null && !"".equals(msgId)) {
						hServerDataBase.saveMessagePublishEntry(jSonObject);
					}
				} catch (DBException dbE) {
					logger.error("hSever error - cant't save message: " + dbE.getMessage(), dbE);
				} catch (ParseException pE) {
					logger.error("hSever error - cant't parse message in json: " + pE.getMessage(), pE);
				}
    		}
    	}
    }
      
    @Override
    public String getDescription() {
        return hServerConfiguration.getSubDomainDescription();
    }
   
    
    @Override
    protected IQ handleIQGet(IQ iq) throws java.lang.Exception  { 
    	// Transformation de l'IQ en DataRequestEntry
    	IQ iqResults = new IQ() {
            public String getChildElementXML() {
            	return "";
             }
        };
        
        iqResults.setTo(iq.getFrom());
        iqResults.setFrom(iq.getTo());
        iqResults.setID(iq.getID());
    	
    	
    	logger.debug("handleIQGet : " + iq.toXML());
    	Element query = iq.getChildElement();
    	String name = query.getName();
    	String namespace = query.getNamespaceURI();
    	
    	if("query".equals(name) && DataRequestEntry.NAMESPACE.equals(namespace)){
   	
    		DataRequestEntry dataRequestEntry = iQToDataRequestEntry(query);

    		if(dataRequestEntry != null){
		    	
    			logger.debug("==> Request type: " + dataRequestEntry.getRequestType() + " start : " +  System.currentTimeMillis());
    			
    			PayloadResultEntry results = null;
    			
    			// Requete de type FIND()
    			if (DataRequestEntry.TYPE_FIND.equals(dataRequestEntry.getRequestType())) { 
			    	results = hServerDataBase.findDocumentsByKeys(dataRequestEntry);
			        
			    // Requete de type GROUP()   
    			} else if (DataRequestEntry.TYPE_GROUP.equals(dataRequestEntry.getRequestType())) {
    				results = hServerDataBase.groupDocumentsByKeys(dataRequestEntry);
    			
    			// Requete de type cmd
    			} else if (DataRequestEntry.TYPE_CMD.equals(dataRequestEntry.getRequestType())) {
    				// TODO
    			
    			// Requete de type ditsinct
    			} else if (DataRequestEntry.TYPE_DISTINCT.equals(dataRequestEntry.getRequestType())) {
    				results = hServerDataBase.distinctDocumentsByKeys(dataRequestEntry);
     			}
    			
    			
    			// Construction du resultat
    			if (results!=null) {
    				
			        iqResults.setType(Type.result);
			        iqResults.setChildElement("query", DataRequestEntry.NAMESPACE);
			        Element queryE = iqResults.getChildElement();
			        Element resultsE = queryE.addElement(PayloadResultEntry.ELEMENTNAME, PayloadResultEntry.NAMESPACE);
			        resultsE.addAttribute(PayloadResultEntry.ATTRIBUTE_COUNT_NAME, results.getCount() + "");
			        for(int i=0; i<results.getResults().size(); i++){
			        	Element resultE = resultsE.addElement(DataResultEntry.ELEMENTNAME, DataResultEntry.NAMESPACE);
			        	Element typeE = resultE.addElement(DataResultEntry.TYPE);
			        	typeE.setText(results.getResults().get(i).getType());
			        	Element authorE = resultE.addElement(DataResultEntry.AUTHOR);
			        	authorE.setText(results.getResults().get(i).getAuthor());
			        	Element publisherE = resultE.addElement(DataResultEntry.PUBLISHER);
			        	publisherE.setText(results.getResults().get(i).getPublisher());
			        	Element publisheddateE = resultE.addElement(DataResultEntry.PUBLISHEDDATE);
			        	publisheddateE.setText(DateUtils.formatDate(results.getResults().get(i).getPublishedDate(), DateUtils.FORMAT_DATE_YYYYslMMslDD_HHMMSS));
			        	Element payloadE = resultE.addElement(DataResultEntry.PAYLOAD);
			        	payloadE.setText(results.getResults().get(i).getPayload());
			        	Element headerE = resultE.addElement(DataResultEntry.HEADER);
			        	headerE.setText(results.getResults().get(i).getHeader());
			        	Element locationE = resultE.addElement(DataResultEntry.LOCATION);
			        	String location = results.getResults().get(i).getLocation();
			        	if(location == null){
			        		location ="";
			        	}
			        	locationE.setText(location);
			        }
    			}
    			
    			logger.debug("==> Request type: " + dataRequestEntry.getRequestType() + " end : " +  System.currentTimeMillis());
    			
    		}
    		else {
    			logger.warn("Invalid Invalid data request");
    			iqResults.setType(Type.error);
    			iqResults.setChildElement("query", DataRequestEntry.NAMESPACE);
    			Element queryE = iqResults.getChildElement();
    			queryE.setText("Invalid data request");
    		}
        
    	} 
    	else {
    		logger.warn("IQ not implemented");
			iqResults.setType(Type.error);
			Element queryE = iqResults.setChildElement("query", "");
			queryE.setText("IQ not implemented");
    	}
    	return iqResults;
    }
    
    @Override
    protected IQ handleIQSet(IQ iq) throws Exception  { 

        logger.debug("handleIQSet : " + iq.toXML());

    	IQ iqResults = null;
    	// Transformation de l'IQ en DataRequestEntry
    	iqResults = new IQ() {
            public String getChildElementXML() {
            	return "";
             }
        };
		// Change the ID
    	iqResults.setID(iq.getID());
		// Change start and end points
    	iqResults.setFrom(iq.getTo());
    	iqResults.setTo(iq.getFrom());
		iqResults.setType(Type.result);
    	
    	Element query = iq.getChildElement();
    	String name = query.getName();
    	String namespace = query.getNamespaceURI();
        if(NodeRequestEntry.QUERY_ROOT_ELEMENT.equals(name) && NodeRequestEntry.NAMESPACE.equals(namespace)) {
        	String json = getQueryJsonData(query, NodeRequestEntry.QUERY_JSON_DATA_QUERY_ELEMENT);
			// Do not take into account properties in json string that do not exist in BoxProfilePublishEntry
        	NodeQuery nodeQuery = ObjectMapperInstanceDomainKey.HSERVER.getMapper().readValue(json, NodeQuery.class);
        	// Synchronized method
        	this.createNode(nodeQuery);
        	// In the pubsub spec XEP-0060: Publish-Subscribe http://xmpp.org/extensions/xep-0060.html#affiliations, we have to use bare JID instead of full JID
        	this.createNodeAffiliation(nodeQuery.getName(), iq.getFrom().toBareJID(), "publisher");
        	// Subscribe to the node for the entity hserver
        	this.subscribeNode(nodeQuery.getName(), iq.getTo().toBareJID());
        	// Subscribe to the node for the entity bot
        	this.subscribeNode(nodeQuery.getName(), iq.getFrom().toBareJID());
    		iqResults.setChildElement(NodeRequestEntry.QUERY_ROOT_ELEMENT, NodeRequestEntry.NAMESPACE);
        	
        } else if (UpdateDataRequestEntry.QUERY_ROOT_ELEMENT.equals(name) && UpdateDataRequestEntry.NAMESPACE.equals(namespace)) {
        	String json = getQueryJsonData(query, UpdateDataRequestEntry.QUERY_JSON_DATA_QUERY_ELEMENT);
        	JSONObject jsonObject = new JSONObject();
			JSONParser jsonParser = new JSONParser();
			try {
				jsonObject = (JSONObject) jsonParser.parse(json);
			} catch (Exception e) {
				// Do nothing
				logger.warn("Could not convert String to Json Object", e);
			}
        	hServerDataBase.updateDocumentByKeys(jsonObject);
    		iqResults.setChildElement(UpdateDataRequestEntry.QUERY_ROOT_ELEMENT, UpdateDataRequestEntry.NAMESPACE);
        }
		
		Element responseElement = iqResults.getChildElement();
		responseElement.setText("Done");
		
		return iqResults; 
    }
    
	public String getQueryJsonData(Element query, String queryElementName) {
		String result = null;
    	
    	List<Element> queryChildren = query.content();
    	if (queryChildren != null && queryElementName != null) {
	    	for (Element element : queryChildren) {
	    		if (queryElementName.equals(element.getName())) {
	    			JSONParser jsonParser = new JSONParser();
	    			try {
	    				result = element.getText();
					} catch (Exception e) {
						// Do nothing
						logger.warn("Could not convert String to Json Object", e);
					}
	    		} else {
	    			logger.warn("Unknown tag in received IQ");
	    		}	
			}
    	}
    	return result;
	}

 	public DataRequestEntry iQToDataRequestEntry(Element query){
    	DataRequestEntry dataRequestEntry = new DataRequestEntry();
    	
    	List<Element> queryChildren = query.content();
    	for(int i=0; i<queryChildren.size(); i++){
    		if(queryChildren.get(i).getName().equals("dbname")){
    			dataRequestEntry.setDbName(queryChildren.get(i).getText());	
    		}
    		else if(queryChildren.get(i).getName().equals("collectionName")){
    			dataRequestEntry.setCollectionName(queryChildren.get(i).getText());	
    		}
    		else if (queryChildren.get(i).getName().equals("commandRequest")) {
    			dataRequestEntry.setCommandRequest(queryChildren.get(i).getText());
    		}
    		
    		else if (queryChildren.get(i).getName().equals("requestType")) {
    			dataRequestEntry.setRequestType(queryChildren.get(i).getText());
    		}
    		
    		else if (queryChildren.get(i).getName().equals("reduce")) {
    			dataRequestEntry.setReduce(queryChildren.get(i).getText());
    		}
    		
    		else if (queryChildren.get(i).getName().equals("initialValue")) {
    			dataRequestEntry.setInitialValue(Integer.valueOf(queryChildren.get(i).getText()));
    		}
    		else if (queryChildren.get(i).getName().equals("initialKey")) {
    			dataRequestEntry.setInitialKey(queryChildren.get(i).getText());
    		}
    		
    		else if (queryChildren.get(i).getName().equals("groupKeys")) {
    			List<Element> keys = queryChildren.get(i).content();
    			for(int k=0; k<keys.size(); k++){
    				dataRequestEntry.addGroupKey(keys.get(k).getText());
    			}
    		}
    		
    		else if(queryChildren.get(i).getName().equals("requestKeys")){
    			List<Element> requestKeys = queryChildren.get(i).content();	
    			List<Element> params = requestKeys.get(0).content();
    			
    			for(int j=0; j<params.size(); j++){
    				ParamRequest paramRequest = new ParamRequest();
    				List<Element> param = params.get(j).content();
    				for(int k=0; k<param.size(); k++){
    					
    					// récupération de l'opérateur				
    					DbOperator operator = KeyRequest.getDBOperator(param.get(k).attributeValue("operator"));
    					
    					String attributeValue = param.get(k).attributeValue("filter");
						if(attributeValue != null && !"".equals(attributeValue)){
    						paramRequest.addKeyRequest(new KeyRequest(attributeValue, operator, param.get(k).getText()));
    					}
    					attributeValue = param.get(k).attributeValue("sorter");
						if(attributeValue != null && !"".equals(attributeValue)){
    						paramRequest.addSortRequest(new SortRequest(attributeValue, param.get(k).getText()));
    					}
    					attributeValue = param.get(k).attributeValue("limit");
						if(attributeValue != null && !"".equals(attributeValue)){
    						paramRequest.setLimit(new Limit(attributeValue, param.get(k).getText()));
    					}
						if (PayloadResultEntry.ATTRIBUTE_COUNT_NAME.equals(param.get(k).getName())) {
							paramRequest.setCount("true".equalsIgnoreCase(param.get(k).getText()));
						}
    				}
    				// Une fois le paramètre rempli, on l'ajoute à la liste de paramètre du DataRequestEntry
    				dataRequestEntry.addParamsRequest(paramRequest);
    			}
    			//logger.debug(dataRequestEntry.toString());    			
    		}
    		else {
    			logger.warn("Unknown tag in received IQ");
    			dataRequestEntry = null;
    		}	
    	}
    	
    	return dataRequestEntry;
	}
    
    @Override
	protected void handleIQResult(IQ iq) {
		log.info("(serving component '{}') IQ stanza "
				+ "of type <tt>result</tt> received: ", getName(), iq.toXML());
		notifyIQResultAndError(iq);
	}

    @Override
	protected void handleIQError(IQ iq) {
		log.info("(serving component '{}') IQ stanza "
				+ "of type <tt>error</tt> received: ", getName(), iq.toXML());
		notifyIQResultAndError(iq);
	}
    
	/**
	 * This method will notify IQ object that wait for result or error.
	 * @param iq The IQ result or Error.
	 */
	private void notifyIQResultAndError(IQ iq) {
		IQ iqNode = nodesWaitingForNotify.get(iq.getID());
		if (iqNode != null) {
			synchronized (iqNode) {
				// Change the value object of the key iq.getID(). 
				nodesWaitingForNotify.put(iq.getID(), iq);
				// Notify the waited node
				iqNode.notify();
			}
		}
	}
	
	/**
	 * Create node (if not exists) and configure it
	 */
	private IQ createNode(NodeQuery nodeQuery) {
		logger.debug("Creating node : " + nodeQuery.getName());
		IQ result = null;
		String nodeName = nodeQuery.getName();
		IQ iq = pubSubRequester.getPubSubCreateNode(nodeName);
		try {
			nodesWaitingForNotify.put(iq.getID(), iq);
			// Send node creation packet 
			super.compMan.sendPacket(this, iq);
			synchronized (iq) {
				// Wait for server response, maybe we do not want to wait forever
				//iq.wait(5000);
				iq.wait();
				// Response of server come back then get the result
				result = nodesWaitingForNotify.get(iq.getID());
			}
			
		} catch (Exception e) {
			logger.error("Could not create this node " + nodeName, e);
		}
		return result;
	}
	
	/**
	 * This method adds the affiliation of node in order to add the jid to be the specific affiliation.
	 * In openfire, the pubsub affiliation also subscribe the user to the node.
	 * @param nodeRequestEntry
	 */
	private void createNodeAffiliation(String node, String jid, String affiliation) {
		logger.debug("Creating create node affiliation for node : " + node + " with user : " + jid + " with affiliation : " + affiliation);
		IQ iq = pubSubRequester.getPubSubCreateNodeAffiliation(node, jid, affiliation);
		try {
			super.compMan.sendPacket(this, iq);
		} catch (ComponentException e) {
			logger.error("Could not create this node affiliation for node : " + node + " with user : " + jid + " with affiliation : " + affiliation);
		}
	}

	/**
	 * This method subscribes an entity to a node.
	 * In ejabberd, the node owner doesn't automatically subscribe to the node.
	 * @param nodeRequestEntry
	 */
	private void subscribeNode(String node, String jid) {
		logger.debug("Creating subscribe to a node : " + node + " with user : " + jid);
		IQ iq = pubSubRequester.getPubSubSubscribeNode(node, jid, jid);
		try {
			super.compMan.sendPacket(this, iq);
		} catch (ComponentException e) {
			logger.error("Could not  subscribe to a node : " + node + " with user : " + jid);
		}
	}


    /**
     * getter hServerConfiguration
     * @return the hServerConfiguration
     */
    public HServerConfiguration getHServerConfiguration() {
    	return hServerConfiguration;
    }
    
    // Spring injections ------------------------------------
    @Required
    public void setHServerConfiguration(HServerConfiguration hServerConfiguration) {
		this.hServerConfiguration = hServerConfiguration;
	}
    
    @Required
    public void setJSonParser(CommonJSonParserImpl jSonParser) {
		this.jSonParser = jSonParser;
	}
        
    @Required
    public void setHServerDataBase(DataBase hServerDataBase) {
		this.hServerDataBase = hServerDataBase;
	}
    
    @Required
    public void setPubSubRequester(PubSubRequester pubSubRequester) {
		this.pubSubRequester = pubSubRequester;
	}  
    
	/**
	 * @param stringUtils the stringUtils to set
	 */
	public void setStringUtils(IStringUtils stringUtils) {
		this.stringUtils = stringUtils;
	}

}
