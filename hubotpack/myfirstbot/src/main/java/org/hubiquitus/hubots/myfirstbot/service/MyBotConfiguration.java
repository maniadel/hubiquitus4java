package org.hubiquitus.hubots.myfirstbot.service;

import java.util.List;

import org.hubiquitus.hubotsdk.application.BotInterface;

/**
 * 
 * @author o.chauvie
 *
 */
public interface MyBotConfiguration extends BotInterface {

	String NODE_PUBLISHED_NAME_KEY = "published.node.name";
	String NODE_PUBLISHED_TITLE_KEY = "published.node.title";
	String NODE_PUBLISHED_ALLOWED_ROSTER_GROUPS_KEY = "published.node.allowedrostergroups";

    //<!-- The node for listening Event messages  -->
	String NODE_SUBSCRIBED_EVENT_NAME_KEY = "subscribed.node.event.name";
	//<!-- The node for listening Indicator messages  -->
	String NODE_SUBSCRIBED_INDICATOR_NAME_KEY = "subscribed.node.indicator.name";

	String NODE_SUBSCRIBED_NAME_KEY = "subscribed.node.name";
	String NODE_SUBSCRIBED_TITLE_KEY = "subscribed.node.title";
	String NODE_SUBSCRIBED_AFFILIATION_USER_KEY = "subscribed.node.affiliation.user";
	String NODE_SUBSCRIBED_AFFILIATION_TYPE_KEY = "subscribed.node.affiliation.type";
	String NODE_SUBSCRIBED_ALLOWED_ROSTER_GROUPS_KEY = "subscribed.node.allowedrostergroups";

	String FETCHER_DATABASE_COLLECTION_PROFILE = "fetcher.database.collection.profile";
	String FETCHER_DATABASE_COLLECTION_EVENT = "fetcher.database.collection.event";
	Object FETCHER_DATABASE_COLLECTION_INDICATOR = "fetcher.database.collection.indicator";
	
	 // Default node for communication between HServer and this Bot
	String DEFAULT_NODE_TITLE = "default.node.title";
	String DEFAULT_NODE_NAME = "default.node.name";
	String DEFAULT_NODE_ALLOWEDROSTERGROUPS_KEY = "default.node.allowedrostergroups";
	
	String DB_NAME_KEY = "fetcher.database.name";
	
	String getPublishedNodeTitle();

    String getPublishedNodeName();
    
	List<String> getPublishedNodeAllowedRosterGroups();

    String getSubscribedNodeEventName();

    String getSubscribedNodeIndicatorName();

	String getSubscribedNodeTitle();

    String getSubscribedNodeName();

    String getSubscribedNodeAffiliationUser();
    
    String getSubscribedNodeAffiliationType();

	List<String> getSubscribedNodeAllowedRosterGroups();

	int getInterval();
}
