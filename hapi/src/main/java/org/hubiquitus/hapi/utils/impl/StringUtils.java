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


package org.hubiquitus.hapi.utils.impl;

import org.apache.commons.lang.StringEscapeUtils;
import org.hubiquitus.hapi.utils.IStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtils implements IStringUtils {

	private static Logger logger = LoggerFactory.getLogger(StringUtils.class);

	@Override
	public String unescapeXml(String str) {
		String result = StringEscapeUtils.unescapeXml(str);
		logger.debug("The result of the string " + str +" transformation " + result);
		return result;
	}
	
}
