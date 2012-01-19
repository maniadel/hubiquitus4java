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

import java.util.Date;

import org.hubiquitus.hapi.utils.CommonJSonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonJSonParserImpl implements CommonJSonParser {

	private static Logger logger = LoggerFactory.getLogger(CommonJSonParserImpl.class);
	
    public JSONObject getJSon(String s) throws ParseException {
        JSONObject json = (JSONObject) new JSONParser().parse(s);
        JSONObject jsonAlert = (JSONObject) json.get("alert");
        return jsonAlert;
    }
    
    public JSONObject getJSonPayload(String s) throws ParseException {
        JSONObject json = (JSONObject) new JSONParser().parse(s);
        return json;
    }
    
    public static JSONObject stringJsonMessagesToJSONObject(String the_json) {
        JSONObject jsonMessages = null;

        try {
            jsonMessages = (JSONObject) new JSONParser().parse(the_json);
        } catch (ParseException e) {
        	logger.error(e.getMessage());
        }

        return jsonMessages;
    }
    
    /*
     * Replace unsupported characters
     */
    public static String encode(String text) {
        String result = text.replace("'", "#!39#");
        result = result.replace("\"", "#!34#");
        result = result.replace("&amp;", "#!000#");
        result = result.replace("&", "&amp;");
        result = result.replace("#!000#", "&amp;");
        result = result.replace("<", "&#60;");

        //System.out.println("Encodage----\n" + result + "\n--------");

        return result;
    }
    
    /*
     * Replace the code of unsupported characters byt the characters
     */
    public static String decode(String text) {
        String result = text.replace("#!39#","'");
        result = result.replace("#!34#","\"");
        result = result.replace("#!000#", "&amp;");
        result = result.replace("&amp;", "&");
        result = result.replace("&amp;", "#!000#");
        result = result.replace("&#60;","<");
        result = result.replace("&quot;","\"");
        result = result.replace("&lt;","<");
        result = result.replace("&gt;",">");

        //System.out.println("Encodage----\n" + result + "\n--------");

        return result;
    }

    public static Date jsonStringToDate(String jsonStringDate) throws java.text.ParseException {
        return DateUtils.parseDate(jsonStringDate, "yyyy/MM/dd HH:mm:ss Z");
    }
    
    public static void main(String[] args) {
        String s = "{\"alert\":{\"type\":\"admin\", \"action\":\"xxpingxx\"}}";
        CommonJSonParserImpl jsonP = new CommonJSonParserImpl();

        try {
            jsonP.getJSon(s);
        } catch (ParseException e) {
        	logger.error(e.getMessage());
        }
    }

}
