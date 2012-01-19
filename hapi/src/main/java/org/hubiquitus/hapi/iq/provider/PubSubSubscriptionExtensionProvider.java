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

package org.hubiquitus.hapi.iq.provider;

import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.jivesoftware.smackx.pubsub.EventElementType;
import org.jivesoftware.smackx.pubsub.Subscription;
import org.jivesoftware.smackx.pubsub.Subscription.State;
import org.xmlpull.v1.XmlPullParser;

/**
 * This class is used after response of the sending subscription allow form.
 * @author mathieu
 *
 */
public class PubSubSubscriptionExtensionProvider implements PacketExtensionProvider {

	@Override
	public PacketExtension parseExtension(XmlPullParser parser) throws Exception {
		Subscription result = null; //new Subscription("");
		String jid = null;
		String nodeId =  null;
		String subscriptionId = null;
		State state = null;
        int eventType = parser.getEventType();
        if (eventType == XmlPullParser.START_TAG) {
            if (EventElementType.subscription.name().equals(parser.getName())) {
                jid = parser.getAttributeValue(null, "jid");
                nodeId = parser.getAttributeValue(null, "node");
                state = State.valueOf(parser.getAttributeValue(null, "subscription"));
                result = new Subscription(jid, nodeId, subscriptionId, state);
            }
        }
		return result;
	}

}
