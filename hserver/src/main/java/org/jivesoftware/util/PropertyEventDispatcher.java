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


package org.jivesoftware.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dispatches property events. Each event has a {@link EventType type}
 * and optional parameters, as follows:<p>
 * <p/>
 * <table border="1">
 * <tr><th>Event Type</th><th>Extra Params</th></tr>
 * <tr><td>{@link EventType#property_set property_set}</td><td>A param named <tt>value</tt> that
 * has the value of the property set.</td></tr>
 * <tr><td>{@link EventType#property_deleted property_deleted}</td><td><i>None</i></td></tr>
 * <tr><td>{@link EventType#xml_property_set xml_property_set}</td><td>A param named <tt>value</tt> that
 * has the value of the property set.</td></tr>
 * <tr><td>{@link EventType#xml_property_deleted xml_property_deleted}</td><td><i>None</i></td></tr>
 * </table>
 *
 * @author Matt Tucker
 */
public class PropertyEventDispatcher {

	private static Logger logger = LoggerFactory.getLogger(PropertyEventDispatcher.class);

	
    private static List<PropertyEventListener> listeners =
            new CopyOnWriteArrayList<PropertyEventListener>();

    private PropertyEventDispatcher() {
        // Not instantiable.
    }

    /**
     * Registers a listener to receive events.
     *
     * @param listener the listener.
     */
    public static void addListener(PropertyEventListener listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        listeners.add(listener);
    }

    /**
     * Unregisters a listener to receive events.
     *
     * @param listener the listener.
     */
    public static void removeListener(PropertyEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Dispatches an event to all listeners.
     *
     * @param property  the property.
     * @param eventType the event type.
     * @param params    event parameters.
     */
    public static void dispatchEvent(String property, EventType eventType, Map params) {
        for (PropertyEventListener listener : listeners) {
            try {
                switch (eventType) {
                    case property_set: {
                        listener.propertySet(property, params);
                        break;
                    }
                    case property_deleted: {
                        listener.propertyDeleted(property, params);
                        break;
                    }
                    case xml_property_set: {
                        listener.xmlPropertySet(property, params);
                        break;
                    }
                    case xml_property_deleted: {
                        listener.xmlPropertyDeleted(property, params);
                        break;
                    }
                    default:
                        break;
                }
            } catch (Exception e) {
            	logger.error(e.getMessage());
            }
        }
    }

    /**
     * Represents valid event types.
     */
    public enum EventType {

        /**
         * A property was set.
         */
        property_set,

        /**
         * A property was deleted.
         */
        property_deleted,

        /**
         * An XML property was set.
         */
        xml_property_set,

        /**
         * An XML property was deleted.
         */
        xml_property_deleted
    }
}