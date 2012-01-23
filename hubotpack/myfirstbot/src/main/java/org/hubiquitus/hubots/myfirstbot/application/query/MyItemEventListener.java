package org.hubiquitus.hubots.myfirstbot.application.query;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hubiquitus.hapi.model.impl.ComplexPublishEntry;
import org.hubiquitus.hubots.myfirstbot.application.MyFirstBot;
import org.hubiquitus.hubots.myfirstbot.service.impl.MyFirstBotUtilsImpl;
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
public class MyItemEventListener implements ItemEventListener<Item> {
	
	protected Logger logger = LoggerFactory.getLogger(MyItemEventListener.class);
	
	/**
	 * BarometreBot
	 */
	private MyFirstBot bot;

	@Override
	public void handlePublishedItems(ItemPublishEvent<Item> items) {
        
        List<Item> itemsList = items.getItems();
        logger.debug("Receiving " + itemsList.size() + " items");
        
        for (Iterator<Item> iterator = itemsList.iterator(); iterator.hasNext();) {
               
            Item item = (Item) iterator.next();
            String xml = item.toXML();
               
            try {
				DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document document = parser.parse(new InputSource(new StringReader(xml)));
				
				NodeList results = document.getElementsByTagName("entry");
	   			for (int z=0; z<results.getLength(); z ++) {
	   				Node result = results.item(z);
	   				String entry = result.getTextContent();
	   				
	   				logger.debug("Message: " + entry);
	   				JSONObject jSonObject =  bot.getJSonParser().getJSonPayload(entry);

	   				bot.getXmppEntryPublisher().publishToNode(MyFirstBotUtilsImpl.subscribedDataToMessagePublishEntry(entry), bot.getNodeName());
	   				
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
	public MyFirstBot getbot() {
		return bot;
	}

	/**
	 * Setter barometreBot
	 * @param bot the barometreBot to set
	 */
	public void setbot(MyFirstBot bot) {
		this.bot = bot;
	}
	
	// Spring injections --------------------------------------------------------------

}
