/*
 * Copyright (c) Novedia Group 2012.
 *
 *    This file is part of Hubiquitus
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy
 *    of this software and associated documentation files (the "Software"), to deal
 *    in the Software without restriction, including without limitation the rights
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *    of the Software, and to permit persons to whom the Software is furnished to do so,
 *    subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies
 *    or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *    INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *    PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 *    FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 *    ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *    You should have received a copy of the MIT License along with Hubiquitus.
 *    If not, see <http://opensource.org/licenses/mit-license.php>.
 */


package org.hubiquitus.hubotsdk.adapters;

import org.hubiquitus.hapi.client.HMessageDelegate;
import org.hubiquitus.hapi.exceptions.MissingAttrException;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hapi.hStructures.ResultStatus;
import org.hubiquitus.hubotsdk.AdapterInbox;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HChannelAdapterInbox extends AdapterInbox {

    final Logger log = LoggerFactory.getLogger(HChannelAdapterInbox.class);

    /** local delegate class to support subscribe and unsubscribe messages */
    private class MyLocalDelegate implements HMessageDelegate {

        @Override
        public void onMessage(HMessage hMessage) {
            if (hMessage.getPayloadAsHResult().getStatus() != ResultStatus.NO_ERROR)
                log.error("Error : " + hMessage.getPayloadAsHResult());
        }
    }
    
    /** local delegate instance */
    private MyLocalDelegate localDelegate = new MyLocalDelegate();
    
	public HChannelAdapterInbox() {}
	
	/*public HChannelAdapterInbox(String actor) {
		this.actor = actor;
	}*/

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
	public void setProperties(JSONObject properties) {	
		if(properties == null){
			return;
		}
		try {
			if(properties.getString("actor") != null) 
				setActor(properties.getString("actor"));
		} catch (Exception e) {
			log.warn("message :", e);
		}
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actor == null) ? 0 : actor.hashCode());
		result = prime * result + ((hclient == null) ? 0 : hclient.hashCode());
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
		return true;
	}

}
