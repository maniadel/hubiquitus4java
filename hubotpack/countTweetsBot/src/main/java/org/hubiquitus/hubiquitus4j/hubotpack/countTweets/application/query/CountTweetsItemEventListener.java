package org.hubiquitus.hubiquitus4j.hubotpack.countTweets.application.query;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hubiquitus.hapi.model.impl.MessagePublishEntry;
import org.hubiquitus.hubiquitus4j.hubotpack.countTweets.application.CountTweetsBot;
import org.hubiquitus.hubiquitus4j.hubotpack.countTweets.model.CountTweetsPayloadPublishEntry;
import org.hubiquitus.hubiquitus4j.hubotpack.countTweets.model.countTweetsDataPublishEntry;
import org.hubiquitus.hubotsdk.application.push.impl.SmackApiControllerImpl;
import org.hubiquitus.hubotsdk.application.push.impl.XmppEntryPublisherImpl;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Event listener for node subscription
 * @author o.chauvie
 *
 */
public class CountTweetsItemEventListener implements ItemEventListener<Item> {
	
	protected Logger logger = LoggerFactory.getLogger(CountTweetsItemEventListener.class);
	
	/**
	 * CountTweetsBot
	 */
	private CountTweetsBot countTweetsBot;
	
	
	@Override
	public void handlePublishedItems(ItemPublishEvent<Item> items) {
        
        List<Item> itemsList = items.getItems();
  
        logger.debug("Receiving " + itemsList.size() + " items");
            
        for (Iterator<Item> iterator = itemsList.iterator(); iterator.hasNext();) {
     
            Item item = (Item) iterator.next(); 
            String xml = item.toXML();  
            itemsList.add(item);
                     
            try {
				DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document document = parser.parse(new InputSource(new StringReader(xml)));
				
				NodeList results = document.getElementsByTagName("entry");
	   			for (int z=0; z<results.getLength(); z ++) {
	   				Node result = results.item(z);
	   				logger.debug("result = " + result);
	   				String entry = result.getTextContent();
	   				
	   				countTweetsBot.setNbMessages(itemsList);
	   				
	   				logger.debug("Message: " + entry);
	   				JSONObject jSonObject =  countTweetsBot.getJSonParser().getJSon(entry);
	   				String type = (String) jSonObject.get("type");
	   				
	   				// Message from any Bot 
	   				if (countTweetsBot.getTwitterNodeNameToRead().equals(type)) {	   					
	   					/*ComplexPublishEntry complexPublishEntry = countTweetsBot.getComplexPublishEntry();
	   					MessagePublishEntry messagePublishEntry = complexPublishEntry.getMessages().get(0);*/
	   					MessagePublishEntry messagePublishEntry = new MessagePublishEntry();
	   					
	   					CountTweetsPayloadPublishEntry countTweetsPayloadPublishEntry = (CountTweetsPayloadPublishEntry) messagePublishEntry.getPayload();
	   					ArrayList<countTweetsDataPublishEntry> counttweetsDatas =  countTweetsPayloadPublishEntry.getCountTweetsDatas();
	   						
	   					XmppEntryPublisherImpl xmppEntryPublisherImpl = countTweetsBot.getXmppEntryPublisher();
	   					SmackApiControllerImpl smackApiControllerImpl = xmppEntryPublisherImpl.getSmackApiController();
	   					
	   					@SuppressWarnings("unchecked")
	   					ArrayList<JSONObject> payload = (ArrayList<JSONObject>) jSonObject.get("payload");
	   					logger.debug("Payload: " + payload.toString());	   					   					
	   				}	   				
	   			}
            } catch (org.json.simple.parser.ParseException e) {
				logger.error("Json parser error for message:" + xml + " - " + e.getMessage());
			} catch (ParserConfigurationException e) {
				logger.error("Xml parser error for message:" + xml + " - " + e.getMessage());
			} catch (SAXException e) {
				logger.error("Saxe error for message:" + xml + " - " + e.getMessage());
			} catch (IOException e) {
				logger.error("IO error for message:" + xml + " - " + e.getMessage());
			}
   			
        }     
	}
	   		
	/**
	 * Getter barometreBot
	 * @return the barometreBot
	 */
	public CountTweetsBot getCountTweetsBot() {
		return countTweetsBot;
	}

	/**
	 * Setter barometreBot
	 * @param barometreBot the barometreBot to set
	 */
	public void setCountTweetsBot(CountTweetsBot countTweetsBot) {
		this.countTweetsBot = countTweetsBot;
	}
}
