/*
 * Copyright (c) Novedia Group 2011.
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

package org.hubiquitus.hubots.myfirstbot.application;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.hubiquitus.hubotsdk.application.Bot;
import org.hubiquitus.hubotsdk.application.push.impl.SmackApiControllerImpl;
import org.hubiquitus.hubotsdk.application.push.impl.XmppEntryPublisherImpl;
import org.hubiquitus.hubotsdk.exception.BotException;
import org.hubiquitus.hapi.model.ExceptionType;
import org.hubiquitus.hapi.model.impl.ComplexPublishEntry;
import org.hubiquitus.hapi.model.impl.DataRequestEntry;
import org.hubiquitus.hapi.model.impl.DataResultEntry;
import org.hubiquitus.hapi.model.impl.DbOperator;
import org.hubiquitus.hapi.model.impl.KeyRequest;
import org.hubiquitus.hapi.model.impl.LabelPublishEntry;
import org.hubiquitus.hapi.model.impl.LocationPublishEntry;
import org.hubiquitus.hapi.model.impl.MessagePublishEntry;
import org.hubiquitus.hapi.model.impl.ParamRequest;
import org.hubiquitus.hapi.utils.impl.CommonJSonParserImpl;
import org.hubiquitus.hubots.myfirstbot.application.query.MyItemEventListener;
import org.hubiquitus.hubots.myfirstbot.application.query.MyRetriever;
import org.hubiquitus.hubots.myfirstbot.service.MyBotConfiguration;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.expression.Operation;

public class MyFirstBot extends Bot {
	
	/*
	 * Query fetcher. Retrieves all data from hServer data base
	 */
	private CommonJSonParserImpl jSonParser;
	
	private MyItemEventListener myItemEventListener;
	
	private MyBotConfiguration hubotConfig;

	/**
     * Constructor
     *
     */
    public MyFirstBot() {
        super();
    }

    /*
     * Start fetcher with general conf
     * 
     * Specific to Spring
     */
    public void startDataRetriever() throws BotException {
    	fetcherState = BotState.STARTED;
    	
    	if (this.getXmppEntryPublisher() == null) {
    		logger.error("Publisher not started yet. Not allowed to start fetcher.");
    	    BotException botException = new BotException(
                    "Publisher not started yet. Not allowed to start fetcher.");
    		fetcherState = BotState.ERROR;
            exceptionCollector.addException(botException, ExceptionType.FETCHER_LAUNCH_ERROR);
            throw botException;
        }
    	
    	//Data access from any sources 
    	this.createNodeFromHServer(getNodeName(), getTitle(), getAllowedRosterGroups());
    
    	//Subscribed to one specific node
    	this.subscribeNode(node, new MyItemEventListener());
    	//this.createNodeFromHServer(getNodeName(), getTitle(), getAllowedRosterGroups());
    	
    	
    	MyRetriever myRetriever = new MyRetriever(this.hubotConfig.getInterval(), this);
    	myRetriever.start();
    	try {
			myRetriever.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	/*
    	
    	//Direct communication to Hserver by request
    	DataRequestEntry dataRequestEntry = new DataRequestEntry();
    	dataRequestEntry.setRequestType(DataRequestEntry.TYPE_FIND);
    	dataRequestEntry.setDbName("MyDB");
    	dataRequestEntry.setCollectionName("HelloType");
    	
    	List<ParamRequest> params = new ArrayList<ParamRequest>();
   		ParamRequest param = new ParamRequest();
   		KeyRequest kr1 = new KeyRequest(MessagePublishEntry.AUTHOR, DbOperator.EQUAL, "Moi");
   		param.addKeyRequest(kr1);
   		params.add(param);
    		
   		
   		DataRequestEntry result = this.sendDbRequest(dataRequestEntry);
   		
   		
   		List<DataResultEntry> results = result.getResultsList();
   		for (int i=0; i< results.size(); i++) {
   			DataResultEntry r = results.get(i);
   			String myBlabla = r.getPayload();
   		}
   		
    	*/
    	
    }
   
	/**
	 * Getter jSonParser
	 * @return the jSonParser
	 */
	public CommonJSonParserImpl getJSonParser() {
		return jSonParser;
	}
	
    @Required
	public void setJSonParser(CommonJSonParserImpl jSonParser) {
		this.jSonParser = jSonParser;
	}
    
   

	@Override
	public String getNodeName() {
		return this.hubotConfig.getNodeName();
	}

	@Override
	public String getTitle() {
		return this.hubotConfig.getTitle();
	}

	@Override
	public List<String> getAllowedRosterGroups() {
		return this.hubotConfig.getAllowedRosterGroups();
	}

	@Override
	public String getMainConfJSON() {
		// TODO
		String config = "{'TODO':'TODO'}";
		return config;
	}
	
	// Spring injections --------------------------------------------------------------
	@Required
	public void setHubotConfig(MyBotConfiguration hubotConfig) {
		this.hubotConfig = hubotConfig;
	}
	
	@Required
	public void setMyItemEventListener(MyItemEventListener myItemEventListener) {
		this.myItemEventListener = myItemEventListener;
	}
	
	
}
