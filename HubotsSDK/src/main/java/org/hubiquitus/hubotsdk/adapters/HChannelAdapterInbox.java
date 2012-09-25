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

package org.hubiquitus.hubotsdk.adapters;

import java.util.Map;

import org.hubiquitus.hapi.client.HMessageDelegate;
import org.hubiquitus.hapi.exceptions.MissingAttrException;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.AdapterInbox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HChannelAdapterInbox extends AdapterInbox {

    final Logger log = LoggerFactory.getLogger(HChannelAdapterInbox.class);
	private String actor;

    /** local delegate class to support subscribe and unsubscribe messages */
    private class MyLocalDelegate implements HMessageDelegate {

        @Override
        public void onMessage(HMessage hMessage) {
        	//TODO
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
    
    /** local delegate instance */
    private MyLocalDelegate localDelegate = new MyLocalDelegate();
    
	public HChannelAdapterInbox() {}
	
	public HChannelAdapterInbox(String name) {
		this.name = name;
	}

	@Override
	public void start() {
        try {
		    hclient.subscribe(actor, localDelegate);
        } catch (MissingAttrException e) {
            log.warn("error while starting : ",e);
        }
    }

	@Override
	public void stop() {
        try {
		    hclient.unsubscribe(actor, localDelegate);
        } catch (MissingAttrException e) {
            log.warn("error while stopping : ",e);
        }
	}


	@Override
	public void setProperties(Map<String,String> params) {	
		if(params.get("chid") != null) 
			setActor(params.get("chid"));
	}

	/* Getters and Setters */
	public String getActor() {
		return actor;
	}


	public void setActor(String actor) {
		this.actor = actor;
	}


	@Override
	public String toString() {
		return "HubotAdapter [name=" + name + ", chid" + actor + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actor == null) ? 0 : actor.hashCode());
		result = prime * result + ((hclient == null) ? 0 : hclient.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HChannelAdapterInbox other = (HChannelAdapterInbox) obj;
		if (actor == null) {
			if (other.actor != null)
				return false;
		} else if (!actor.equals(other.actor))
			return false;
		if (hclient == null) {
			if (other.hclient != null)
				return false;
		} else if (!hclient.equals(other.hclient))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
