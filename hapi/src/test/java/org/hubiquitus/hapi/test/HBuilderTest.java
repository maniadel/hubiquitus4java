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

package org.hubiquitus.hapi.test;

import org.hubiquitus.hapi.client.HClient;
import org.hubiquitus.hapi.exceptions.MissingAttrException;
import org.hubiquitus.hapi.hStructures.HAck;
import org.hubiquitus.hapi.hStructures.HAckValue;
import org.hubiquitus.hapi.hStructures.HAlert;
import org.hubiquitus.hapi.hStructures.HConvState;
import org.hubiquitus.hapi.hStructures.HGeo;
import org.hubiquitus.hapi.hStructures.HLocation;
import org.hubiquitus.hapi.hStructures.HMeasure;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hapi.hStructures.HMessageOptions;
import org.hubiquitus.hapi.hStructures.HMessagePriority;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * @cond internal
 */

public class HBuilderTest {

	@Test
	public void HMessageBuildTest() {
		HClient hclient = new HClient();

		HMessageOptions hmessageOption = new HMessageOptions();

		hmessageOption.setAuthor("me");
		hmessageOption.setConvid("convid:123456789");

		JSONObject headers = new JSONObject();
		try {
			headers.put("header1", "1");
			headers.put("header2", "2");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		hmessageOption.setHeaders(headers);

		HLocation location = new HLocation();
		HGeo pos = new HGeo(12.32,56.23);
		location.setPos(pos);
		hmessageOption.setLocation(location);

		hmessageOption.setPriority(HMessagePriority.INFO);

		DateTime date = new DateTime();
		hmessageOption.setRelevance(date);

		hmessageOption.setPersistent(false);

		JSONObject payload = new JSONObject();
		try {
			payload.put("test", "test");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		HMessage hmessage = null;
		try {
			hmessage = hclient.buildMessage("chid:123456789", "string",
					payload, hmessageOption);
		} catch (MissingAttrException e) {
			Assert.fail();
		}

		
		Assert.assertEquals(hmessage.getAuthor(), "me");
		Assert.assertEquals(hmessage.getActor(), "chid:123456789");
		Assert.assertEquals(hmessage.getConvid(), "convid:123456789");
		Assert.assertEquals(hmessage.getMsgid(), null);
		Assert.assertEquals(hmessage.getType(), "string");
		Assert.assertEquals(hmessage.getHeaders().toString(),
				headers.toString());
		Assert.assertEquals(hmessage.getLocation().toString(),
				location.toString());
		
		Assert.assertEquals(hmessage.getPayloadAsJSONObject().toString(),
				payload.toString());
		Assert.assertEquals(hmessage.getPriority(), HMessagePriority.INFO);
		Assert.assertEquals(hmessage.getPublished(), null);
		Assert.assertEquals(hmessage.getPersistent(), false);
		Assert.assertEquals(hmessage.getRelevance(), date); 

	}

	@Test
	public void HConvStateBuildTest() {
		HClient hclient = new HClient();

		HMessageOptions hmessageOption = new HMessageOptions();

		hmessageOption.setAuthor("me");

		JSONObject headers = new JSONObject();
		try {
			headers.put("header1", "1");
			headers.put("header2", "2");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		hmessageOption.setHeaders(headers);

		HLocation location = new HLocation();
		hmessageOption.setLocation(location);

		hmessageOption.setPriority(HMessagePriority.INFO);

		DateTime date = new DateTime();
		hmessageOption.setRelevance(date);

		hmessageOption.setPersistent(false);

		HMessage hmessage = null;
		try {
			hmessage = hclient.buildConvState("test channel", "test conv id",
					"test status", hmessageOption);
		} catch (MissingAttrException e) {
			Assert.fail();
		}

		HConvState hconvstate = new HConvState();
		try {
			hconvstate.setStatus("test status");
		} catch (MissingAttrException e) {
			Assert.fail();
			e.printStackTrace();
		}

		Assert.assertEquals(hmessage.getType(), "hConvState");
		Assert.assertEquals(hmessage.getPayloadAsJSONObject().toString(),
				hconvstate.toString());
	}

	@Test
	public void HAckBuildTest() {
		HClient hclient = new HClient();

		HMessageOptions hmessageOption = new HMessageOptions();

		hmessageOption.setAuthor("me");
		hmessageOption.setConvid("convid:123456789");

		JSONObject headers = new JSONObject();
		try {
			headers.put("header1", "1");
			headers.put("header2", "2");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		hmessageOption.setHeaders(headers);

		HLocation location = new HLocation();
		hmessageOption.setLocation(location);

		hmessageOption.setPriority(HMessagePriority.INFO);

		DateTime date = new DateTime();
		hmessageOption.setRelevance(date);

		hmessageOption.setPersistent(false);

		HAckValue ackvalue = HAckValue.READ;

		HMessage hmessage = null;
		try {
			hmessage = hclient.buildAck("chid:123456789", "chid:123456789", ackvalue,
					hmessageOption);
		} catch (MissingAttrException e) {
			Assert.fail();
		}

		HAck hack = new HAck();
		try {
			hack.setAck(ackvalue);
		} catch (MissingAttrException e) {
			e.printStackTrace();
			Assert.fail();
		}

		Assert.assertEquals(hmessage.getType(), "hAck");
		Assert.assertEquals(hmessage.getPayloadAsJSONObject().toString(), hack.toString());
	}

	@Test
	public void HAlertBuildTest() {
		HClient hclient = new HClient();

		HMessageOptions hmessageOption = new HMessageOptions();

		hmessageOption.setAuthor("me");
		hmessageOption.setConvid("convid:123456789");

		JSONObject headers = new JSONObject();
		try {
			headers.put("header1", "1");
			headers.put("header2", "2");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		hmessageOption.setHeaders(headers);

		HLocation location = new HLocation();
		hmessageOption.setLocation(location);

		hmessageOption.setPriority(HMessagePriority.INFO);

		DateTime date = new DateTime();
		hmessageOption.setRelevance(date);

		hmessageOption.setPersistent(false);

		String alert = "WARNING WARNING";

		HMessage hmessage = null;
		try {
			hmessage = hclient.buildAlert("chid:123456789", alert,
					hmessageOption);
		} catch (MissingAttrException e) {
			Assert.fail();
		}

		HAlert halert = new HAlert();
		try {
			halert.setAlert(alert);
		} catch (MissingAttrException e) {
			e.printStackTrace();
			Assert.fail();
		}

		Assert.assertEquals(hmessage.getType(), "hAlert");
		Assert.assertEquals(hmessage.getPayloadAsJSONObject().toString(), halert.toString());
	}

	@Test
	public void HBuildMeasureTest() {
		HClient hclient = new HClient();

		HMessageOptions hmessageOption = new HMessageOptions();

		hmessageOption.setAuthor("me");
		hmessageOption.setConvid("convid:123456789");

		JSONObject headers = new JSONObject();
		try {
			headers.put("header1", "1");
			headers.put("header2", "2");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		hmessageOption.setHeaders(headers);

		HLocation location = new HLocation();
		hmessageOption.setLocation(location);

		hmessageOption.setPriority(HMessagePriority.INFO);

		DateTime date = new DateTime();
		hmessageOption.setRelevance(date);

		hmessageOption.setPersistent(false);

		String unit = "metre";
		String value = "100";

		HMessage hmessage = null;
		try {
			hmessage = hclient.buildMeasure("chid:123456789", value, unit,
					hmessageOption);
		} catch (MissingAttrException e) {
			Assert.fail();
		}

		HMeasure hmeasure = new HMeasure();
		try {
			hmeasure.setUnit(unit);
			hmeasure.setValue(value);
		} catch (MissingAttrException e) {
			e.printStackTrace();
			Assert.fail();
		}
		

		Assert.assertEquals(hmessage.getType(), "hMeasure");
		Assert.assertEquals(hmessage.getPayloadAsJSONObject().toString(),
				hmeasure.toString());
	}
}

/**
 * @endcond
 */
