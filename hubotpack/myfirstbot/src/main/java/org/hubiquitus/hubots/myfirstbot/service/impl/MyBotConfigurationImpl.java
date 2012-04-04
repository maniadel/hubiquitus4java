/**
 * 
 */
package org.hubiquitus.hubots.myfirstbot.service.impl;

import java.util.List;
import java.util.Map;

import org.hubiquitus.hubotsdk.exception.BotException;
import org.hubiquitus.hapi.model.ExceptionType;
import org.hubiquitus.hapi.model.PublishEntry;
import org.hubiquitus.hapi.model.impl.ComplexPublishEntry;
import org.hubiquitus.hapi.model.impl.DataRequestEntry;
import org.hubiquitus.hapi.model.impl.MessagePublishEntry;
import org.hubiquitus.hubots.myfirstbot.service.MyBotConfiguration;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;
import org.springframework.beans.factory.annotation.Required;

/**
 * @author o.chauvie
 *
 */
public class MyBotConfigurationImpl implements MyBotConfiguration {

	protected Map<String, Object> hubotConfigMap;

	
	public MyBotConfigurationImpl() {
		super();
	}
	
	@Override
	public String getTitle() {
		return (String)hubotConfigMap.get(MyBotConfiguration.DEFAULT_NODE_TITLE);
	}

	@Override
	public String getNodeName() {
        return (String)hubotConfigMap.get(MyBotConfiguration.DEFAULT_NODE_NAME);
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllowedRosterGroups() {
		return (List<String>)hubotConfigMap.get(MyBotConfiguration.DEFAULT_NODE_ALLOWEDROSTERGROUPS_KEY);
	}

	@Override
	public String getSubscribedNodeEventName() {
        return (String)hubotConfigMap.get(MyBotConfiguration.NODE_SUBSCRIBED_EVENT_NAME_KEY);
	}

	@Override
	public String getSubscribedNodeIndicatorName() {
        return (String)hubotConfigMap.get(MyBotConfiguration.NODE_SUBSCRIBED_INDICATOR_NAME_KEY);
	}

	// Get Configuration of the Bot in JSON format
	@Override
	public String getMainConfJSON() {
		return null;
	}

	@Override
	public String getPublishedNodeTitle() {
        return (String) hubotConfigMap.get(MyBotConfiguration.NODE_PUBLISHED_TITLE_KEY);
	}

	@Override
    public String getPublishedNodeName() {
		return (String) hubotConfigMap.get(MyBotConfiguration.NODE_PUBLISHED_NAME_KEY);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPublishedNodeAllowedRosterGroups() {
		return (List<String>) hubotConfigMap.get(MyBotConfiguration.NODE_PUBLISHED_ALLOWED_ROSTER_GROUPS_KEY);
	}

	@Override
	public String getSubscribedNodeTitle() {
        return (String) hubotConfigMap.get(MyBotConfiguration.NODE_SUBSCRIBED_TITLE_KEY);
	}

	@Override
    public String getSubscribedNodeName() {
		return (String) hubotConfigMap.get(MyBotConfiguration.NODE_SUBSCRIBED_NAME_KEY);
    }

	@Override
	public String getSubscribedNodeAffiliationUser() {
		return (String) hubotConfigMap.get(MyBotConfiguration.NODE_SUBSCRIBED_AFFILIATION_USER_KEY);
	}

	@Override
	public String getSubscribedNodeAffiliationType() {
		return (String) hubotConfigMap.get(MyBotConfiguration.NODE_SUBSCRIBED_AFFILIATION_TYPE_KEY);
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSubscribedNodeAllowedRosterGroups() {
		return (List<String>) hubotConfigMap.get(MyBotConfiguration.NODE_SUBSCRIBED_ALLOWED_ROSTER_GROUPS_KEY);
	}
	
	@Override
	public int getInterval() {
		return new Integer((String)hubotConfigMap.get("fetcher.interval")).intValue();
	}

	// Spring injections ------------------------------------------------------------
	@Required
	public void setHubotConfigMap(Map<String, Object> hubotConfigMap) {
		this.hubotConfigMap = hubotConfigMap;
	}

	@Override
	public void startPublisherAndDefineNode() {	
	}

	@Override
	public void startDataRetriever() throws BotException {
	}

	@Override
	public void stop() {
	}

	@Override
	public void ping() {
	}

	@Override
	public void publish(PublishEntry entry) {
	}

	@Override
	public void subscribeNode(String node,
		ItemEventListener<Item> itemEventListener) throws BotException {
	}

	@Override
	public void publishToNode(PublishEntry entry, String node) {
	}

	@Override
	public DataRequestEntry sendDbRequest(DataRequestEntry dataRequestEntry) {
		return null;
	}

	@Override
	public void stopXmppPublisher() {
	}

	@Override
	public void stopDataRetriever() {
	}

	@Override
	public void addException(Exception e, ExceptionType publishingError) {	
	}

	@Override
	public String getExceptions() {
		return null;
	}

	@Override
	public String getXmlConfPath() {
		return null;
	}

	@Override
	public void setXmlConfPath(String xmlConfPath) {		
	}
}
