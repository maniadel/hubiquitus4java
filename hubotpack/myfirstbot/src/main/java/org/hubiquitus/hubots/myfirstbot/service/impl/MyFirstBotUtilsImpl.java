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

package org.hubiquitus.hubots.myfirstbot.service.impl;

import java.util.Date;

import org.hubiquitus.hapi.model.impl.MessagePublishEntry;
import org.hubiquitus.hubots.myfirstbot.model.impl.MyPublishEntry;


public class MyFirstBotUtilsImpl {

	private static MyPublishEntry mypayload;
	private static MyPublishEntry mynewpayload;
	
	/**
	 * Change retrieved string into the right type before publish
	 * @param word
	 * @return
	 */
    public static MessagePublishEntry  wordToMessagePublishEntry(String word) {
    	MessagePublishEntry wordPublishEntry = new MessagePublishEntry();
    	mypayload = new MyPublishEntry(word);
    	
    	wordPublishEntry.generateMsgid();
    	wordPublishEntry.setAuthor("Moi");
    	wordPublishEntry.setPublished(new Date());
    	wordPublishEntry.setPublisher("MyBot");
    	wordPublishEntry.setType("HelloType");
        wordPublishEntry.setPayload(mypayload);
        
        wordPublishEntry.setPersistence(true);
        wordPublishEntry.setDbName("MyDB");
        

        return wordPublishEntry;
    }
    
    /**
	 * In case of retrieve subscribed informations, overload this data and send it back to another node
	 * @param word
	 * @return
	 */
    public static MessagePublishEntry  subscribedDataToMessagePublishEntry(String subscribedData) {
    	MessagePublishEntry subscribedDataPublishEntry = new MessagePublishEntry();
    	
        subscribedData = subscribedData + " Word"; //Build a new sentence : "Hello Word"

        mynewpayload = new MyPublishEntry(subscribedData); //New/overload string
        
        subscribedDataPublishEntry.generateMsgid();
        subscribedDataPublishEntry.setAuthor("Moi");
        subscribedDataPublishEntry.setPublished(new Date());
        subscribedDataPublishEntry.setPublisher("MyBot");
        subscribedDataPublishEntry.setType("HelloType");
        subscribedDataPublishEntry.setPayload(mypayload);
        
        subscribedDataPublishEntry.setPersistence(true);
        subscribedDataPublishEntry.setDbName("MyDB");

        return subscribedDataPublishEntry;
    }


}
