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

import org.apache.log4j.Logger;
import org.hubiquitus.hapi.client.HMessageDelegate;
import org.hubiquitus.hapi.exceptions.MissingAttrException;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.AdapterInbox;

public class HChannelAdapterInbox extends AdapterInbox {

    private static Logger log = Logger.getLogger(HChannelAdapterInbox.class);
	private String chid;

    /** local delegate class to support subscribe and unsubscribe messages */
    private class MyLocalDelegate implements HMessageDelegate {

        @Override
        public void onMessage(HMessage hMessage) {
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
		    hclient.subscribe(chid, localDelegate);
        } catch (MissingAttrException e) {
            log.warn("error while starting : ",e);
        }
    }

	@Override
	public void stop() {
        try {
		    hclient.unsubscribe(chid, localDelegate);
        } catch (MissingAttrException e) {
            log.warn("error while stopping : ",e);
        }
	}


	@Override
	public void setProperties(Map<String,String> params) {	
		if(params.get("chid") != null) 
			setChid(params.get("chid"));
	}

	/* Getters and Setters */
	public String getChid() {
		return chid;
	}


	public void setChid(String chid) {
		this.chid = chid;
	}


	@Override
	public String toString() {
		return "HubotAdapter [name=" + name + ", chid" + chid + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chid == null) ? 0 : chid.hashCode());
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
		if (chid == null) {
			if (other.chid != null)
				return false;
		} else if (!chid.equals(other.chid))
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
