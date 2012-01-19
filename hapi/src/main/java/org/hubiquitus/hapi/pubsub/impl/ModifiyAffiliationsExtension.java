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

import java.util.Collections;
import java.util.List;

import org.jivesoftware.smackx.pubsub.NodeExtension;
import org.jivesoftware.smackx.pubsub.PubSubElementType;

/**
 * Represents the <b>affiliations</b> element of a request for affiliations.
 * It is defined in the specification in section <a href="http://xmpp.org/extensions/xep-0060.html#owner-affiliations-modify">8.9.2 Modify Affiliation</a>.
 * 
 * @author Mathieu MA
 */
public class ModifiyAffiliationsExtension extends NodeExtension {

	private String node;
	
	@SuppressWarnings("unchecked")
	protected List<ModifyAffiliation> items = Collections.EMPTY_LIST;
	
	public ModifiyAffiliationsExtension(String node, List<ModifyAffiliation> subList) {
		super(PubSubElementType.AFFILIATIONS);
		this.items = subList;
		this.node = node;
	}

	/**
	 * @return the node
	 */
	public String getNode() {
		return node;
	}

	public List<ModifyAffiliation> getAffiliations() {
		return items;
	}
	
	@Override
	public String toXML()
	{
		StringBuilder builder = new StringBuilder("<");
		builder.append(getElementName()).append(" node='").append(node).append("'");
		builder.append(">");
		if (items != null) {
			for (ModifyAffiliation item : items) {
				builder.append(item.toXML());
			}
		}
		
		builder.append("</");
		builder.append(getElementName());
		builder.append(">");
		return builder.toString();
	}
}
