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


package org.jivesoftware.whack;

import org.dom4j.Element;
import org.dom4j.io.XPPPacketReader;
import org.xmlpull.v1.XmlPullParserException;
import org.xmpp.packet.*;

import java.io.EOFException;
import java.net.SocketException;

/**
 * Reads XMPP XML packets from a socket and asks the component to process the packets.
 *
 * @author Gaston Dombiak
 */
class SocketReadThread extends Thread {

    private ExternalComponent component;
    private boolean shutdown = false;

    XPPPacketReader reader = null;

    /**
     * Create dedicated read thread for this socket.
     *
     * @param component The component for which this thread is reading for
     * @param reader    The reader to use for reading
     */
    public SocketReadThread(ExternalComponent component, XPPPacketReader reader) {
        super("Component socket reader");
        this.component = component;
        this.reader = reader;
    }

    /**
     * A dedicated thread loop for reading the stream and sending incoming
     * packets to the appropriate router.
     */
    public void run() {
        try {
            readStream();
        } catch (EOFException eof) {
            // Normal disconnect
        } catch (SocketException se) {
            // Do nothing if the exception occured while shutting down the component otherwise
            // log the error and try to establish a new connection
            if (!shutdown) {
                component.getManager().getLog().error(se.getLocalizedMessage());
                component.connectionLost();
            }
        } catch (XmlPullParserException ie) {
            component.getManager().getLog().error(ie.getLocalizedMessage());
        } catch (Exception e) {
            component.getManager().getLog().warn(e.getLocalizedMessage());
        }
    }

    /**
     * Read the incoming stream until it ends.
     */
    private void readStream() throws Exception {
        while (!shutdown) {
            Element doc = reader.parseDocument().getRootElement();

            if (doc == null) {
                // Stop reading the stream since the server has sent an end of stream element and
                // probably closed the connection
                return;
            }

            Packet packet;
            String tag = doc.getName();
            if ("message".equals(tag)) {
                packet = new Message(doc);
            } else if ("presence".equals(tag)) {
                packet = new Presence(doc);
            } else if ("iq".equals(tag)) {
                packet = getIQ(doc);
            } else {
                throw new XmlPullParserException("Unknown packet type was read: " + tag);
            }
            // Request the component to process the received packet
            component.processPacket(packet);
        }
    }

    private IQ getIQ(Element doc) {
        Element query = doc.element("query");
        if (query != null && "jabber:iq:roster".equals(query.getNamespaceURI())) {
            return new Roster(doc);
        } else {
            return new IQ(doc);
        }
    }

    /**
     * Aks the thread to stop reading packets. The thread may not stop immediatelly so if a socket
     * exception occurs because the connection was lost then no exception will be logged nor the
     * component will try to reestablish the connection.<p>
     * <p/>
     * Once this method was sent this instance should be discarded and created a new one if a new
     * connection with the server is established.
     */
    public void shutdown() {
        shutdown = true;
    }
}
