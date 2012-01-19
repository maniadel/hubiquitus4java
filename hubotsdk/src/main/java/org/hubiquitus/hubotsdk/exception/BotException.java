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

package org.hubiquitus.hubotsdk.exception;

public class BotException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -6165537358243024433L;

    public BotException() {
        super();
    }

    public BotException(String message) {
        super(message);
    }

    public BotException(Throwable cause) {
        super(cause);
    }

    public BotException(String message, Throwable cause) {
        super(message, cause);
    }

}
