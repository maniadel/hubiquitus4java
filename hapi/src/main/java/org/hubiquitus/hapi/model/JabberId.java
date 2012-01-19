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

public class JabberId
{
	private String bareJid;
	private String fullJid;

	public JabberId(String fullJid) {
		this.fullJid = fullJid;
	}
	
	/**
	 * @return the bareJid
	 */
	public String getBareJid() {
		if (bareJid == null && fullJid != null) {
			bareJid = fullJid.split("/")[0];
		}
		return bareJid;
	}
	/**
	 * @param bareJid the bareJid to set
	 */
	public void setBareJid(String bareJid) {
		this.bareJid = bareJid;
	}
	/**
	 * @return the fullJid
	 */
	public String getFullJid() {
		return fullJid;
	}
	/**
	 * @param fullJid the fullJid to set
	 */
	public void setFullJid(String fullJid) {
		this.fullJid = fullJid;
	}
}
