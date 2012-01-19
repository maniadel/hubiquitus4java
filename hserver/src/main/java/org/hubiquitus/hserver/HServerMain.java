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

import javax.inject.Named;

import org.jivesoftware.whack.ExternalComponentManager;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmpp.component.ComponentException;

@Named
public class HServerMain {

    /**
     * The server component
     */
    private HServerComponent component;

    
    public static void main(String[] args) {
    	
    	ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext.xml");
    			     
    	HServerMain main =(HServerMain) context.getBean("hserver");
        main.run();
    }

    private void run() {

    	HServerConfiguration hServerConfiguration = component.getHServerConfiguration();
    	
        ExternalComponentManager mgr = new ExternalComponentManager(hServerConfiguration.getXmppServerHost(), hServerConfiguration.getXmppServerPort());
        mgr.setSecretKey(hServerConfiguration.getSubDomain(), hServerConfiguration.getSecretKey());
        try {
            mgr.addComponent(hServerConfiguration.getSubDomain(), component);
        } catch (ComponentException e) {
            LoggerFactory.getLogger(HServerMain.class).error("main", e);
            System.exit(-1);
        }
        //Keep it alive
        while (true)
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                LoggerFactory.getLogger(HServerMain.class).error("main", e);
            }

    }

    
    // Spring injections ------------------------------------
    @Required
    public void setComponent(HServerComponent component) {
		this.component = component;
	}
    
    
    
}
