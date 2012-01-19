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

package org.hubiquitus.hapi.model;

public enum ExceptionType {
        PUBLISHER_LAUNCH_ERROR,
        FETCHER_LAUNCH_ERROR,
        FETCHER_CONNECTION_ERROR,
        PUBLISHER_CONNECTION_ERROR,
        PUBLISHER_NODE_CREATION_ERROR,
        PUBLISHER_PUBLISHING_ERROR,
        PUBLISHER_INTERRUPTED_ERROR,
        PUBLISHER_PUSUB_DISCOVER_NODES_ERROR,
        PUBLISHER_PING_ERROR,
        FETCHER_RETRIEVE_ERROR,
        FETCHER_INTERRUPTED_ERROR,
        PUBLISHER_SEND_MESSAGE_ERROR,
        PUBLISHER_SEND_REQUEST_ERROR
}
