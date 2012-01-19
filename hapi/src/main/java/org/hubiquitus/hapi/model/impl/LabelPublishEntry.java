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

package org.hubiquitus.hapi.model.impl;

import org.codehaus.jackson.map.ObjectMapper;
import org.hubiquitus.hapi.model.ObjectMapperInstanceDomainKey;
import org.hubiquitus.hapi.model.PublishEntry;


/**
 * Type hLocation
 * @author o.chauvie
 */
public class LabelPublishEntry implements PublishEntry {

	/**
	 * Label
	 */
	private String label;

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String getEntryType() {
		return null;
	}

	@Override
	public String entryToJsonFormat() {
		String result = null;
		try {
			ObjectMapper om = ObjectMapperInstanceDomainKey.PUBLISH_ENTRY.getMapper();
			result = om.writeValueAsString(this.getLabel());
		} catch (Exception e) {
			// Do nothing
		}
		return result;
	}
}
