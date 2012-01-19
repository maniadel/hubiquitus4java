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

package org.hubiquitus.hapi.pubsub.impl;

import org.jivesoftware.smack.packet.PacketExtension;

/**
 * Represents the <b>affiliations</b> element of a request for affiliations.
 * It is defined in the specification in section <a href="http://xmpp.org/extensions/xep-0060.html#owner-affiliations-modify">8.9.2 Modify Affiliation</a>.
 * 
 * @author Mathieu MA
 */
public class ModifyAffiliation implements PacketExtension {
	
	
	private String jid;
	private Type affiliation;
	
	public enum Type
	{
		member, none, outcast, owner, publisher
	}
	
	public ModifyAffiliation(String jid, Type affiliation) {
		this.jid = jid;
		this.affiliation = affiliation;
	}

	@Override
	public String getElementName() {
		return "affiliation";
	}

	@Override
	public String getNamespace() {
		return null;
	}

	@Override
	public String toXML() {
		StringBuilder builder = new StringBuilder("<");
		builder.append(getElementName());
		appendAttribute(builder, "jid", jid);
		appendAttribute(builder, "affiliation", affiliation.toString());
		
		builder.append("/>");
		return builder.toString();
	}

	private void appendAttribute(StringBuilder builder, String att, String value) {
		builder.append(" ");
		builder.append(att);
		builder.append("='");
		builder.append(value);
		builder.append("'");
	}
}
