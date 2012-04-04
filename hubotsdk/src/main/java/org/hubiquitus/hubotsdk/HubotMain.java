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
/**
 * 
 */
package org.hubiquitus.hubotsdk;

import org.hubiquitus.hubotsdk.application.BotInterface;
import org.hubiquitus.hubotsdk.exception.BotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author a.benkirane
 *
 */
public class HubotMain {

	/**
	 * Hubot main. Requires launch configuration type and data
	 *
	 * @param args
	 */
	public static void start(String[] args) {

		Logger logger = LoggerFactory.getLogger(HubotMain.class);

		ApplicationContext context = null;
		String xmlConfPath = "";
		if(args != null && args.length == 1 && args[0]!= "" && args[0]!= null) {
			xmlConfPath = args[0];
			context =
					new FileSystemXmlApplicationContext(new String[] {
							"file:"+xmlConfPath+"/hubot-applicationContext.xml",
							"file:"+xmlConfPath+"/hubot-conf-applicationContext.xml",
							"file:"+xmlConfPath+"/xmpp-conf-applicationContext.xml",
							"file:"+xmlConfPath+"/hubots-api-applicationContext.xml" });
							
		} else {
	    	context = new ClassPathXmlApplicationContext(new String[] {
	    			"classpath:/spring/hubots-api-applicationContext.xml",
	    			"classpath:/spring/applicationContext.xml"
	    	});
		}
		try {
			BotInterface hubot = (BotInterface) context.getBean("hubot");
			hubot.setXmlConfPath(xmlConfPath+"/");
			
			hubot.startPublisherAndDefineNode();

			hubot.startDataRetriever();
		} catch (BotException e) {
			logger.error("An error has been met starting the data retriever.");
			logger.error(e.getMessage());
		}
	}
}
