/*
 * Copyright (c) Novedia Group 2012.
 *
 *    This file is part of Hubiquitus
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy
 *    of this software and associated documentation files (the "Software"), to deal
 *    in the Software without restriction, including without limitation the rights
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *    of the Software, and to permit persons to whom the Software is furnished to do so,
 *    subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies
 *    or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *    INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *    PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 *    FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 *    ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *    You should have received a copy of the MIT License along with Hubiquitus.
 *    If not, see <http://opensource.org/licenses/mit-license.php>.
 */

package org.hubiquitus.HelloBot;


import org.hubiquitus.hapi.client.HClient;
import org.hubiquitus.hapi.exceptions.MissingAttrException;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.Hubot;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class HelloHubot extends Hubot{

	final Logger logger = LoggerFactory.getLogger(HelloHubot.class);
	
	public static void main(String[] args) throws Exception{
		HelloHubot hubot = new HelloHubot();
		hubot.start();

	}
	
	@Override
	public void init(HClient hclient) {
		initialized();
	}
	
	@Override
	protected void inProcessMessage(HMessage messageIncoming) {
		HMessage message = new HMessage();
		message.setType("hello");
		JSONObject jsonObj = messageIncoming.getPayloadAsJSONObject();
		String name = "Hello ";
		JSONObject payload = new JSONObject();
		try {
			name += jsonObj.getString("text");
			payload.put("text", name);
		} catch (JSONException e) {
			logger.error(e.toString());
		}
		message.setPayload(payload);
		try {
			message.setActor(messageIncoming.getPublisher());
		} catch (MissingAttrException e) {
			logger.error(e.toString());
		}
		send(message);
	}

}
