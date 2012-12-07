/**
 Copyright (c) Novedia Group 2012.
 This file is part of Hubiquitus
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 of the Software, and to permit persons to whom the Software is furnished to do so,
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies
 or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 You should have received a copy of the MIT License along with Hubiquitus.
 If not, see <http://opensource.org/licenses/mit-license.php>. 
 
*/
package org.hubiquitus.hubotsdk.adapters;

import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubiquitus4j.hgoogleplus.GPStatus;
import org.hubiquitus.hubiquitus4j.hgoogleplus.HGooglePlus;
import org.hubiquitus.hubiquitus4j.hgoogleplus.HGooglePlusListners;
import org.hubiquitus.hubotsdk.AdapterInbox;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HGooglePlusOneCercledInBox extends AdapterInbox implements HGooglePlusListners{

	final Logger log = LoggerFactory.getLogger(HGooglePlusOneCercledInBox.class);
	
	private String proxyHost;
	private int    proxyPort;	

	private String googlePlusNameOrId;
	private long roundTime;
	private String APIKey;

	private HGooglePlus gplusOneCercled;
	
	
	@Override
	public void setProperties(JSONObject properties) {

		if(properties != null){
			try{
				if (properties.has("proxyHost")) {
					this.proxyHost = properties.getString("proxyHost");
				}				
				if (properties.has("proxyPort")) {
					this.proxyPort = properties.getInt("proxyPort");
				}
				if (properties.has("googlePlusNameOrId")) {
					this.googlePlusNameOrId = properties.getString("googlePlusNameOrId");
				}
				if (properties.has("roundTime")) {
					this.roundTime = properties.getLong("roundTime");
				}
				if (properties.has("APIKey")) {
					this.APIKey = properties.getString("APIKey");
				}
				
			}catch(JSONException e){
				log.error("ERROR ON CONFIG FILE 'JSON NOT VALID', ERROR TYPE : ",e);
			}
		}
		log.info("Properties Initialized : " + this);
	}

	@Override
	public void start() {
		log.info("Starting request GooglePlus...");
		gplusOneCercled = new HGooglePlus(proxyHost, proxyPort, googlePlusNameOrId,roundTime, APIKey);
		gplusOneCercled.addListener(this);
		gplusOneCercled.start();
		log.info("Started request GooglePlus. ");
		
	}
	
	@Override
	public void stop() {
		log.info("Stopping request...");
		if (gplusOneCercled != null)
			gplusOneCercled.stop();
		log.info("Stopped request.");
		
	}
	
	@Override
	public String toString() {
		return "HGooglePlusOneCercledInBox ["
				+ "  proxyHost  = " + proxyHost 
				+ ", proxyPort  = " + proxyPort 
				+ ", pageName   = " + googlePlusNameOrId
				+ ", roundTime  = " + roundTime  
				+ ", APIKey     = " + APIKey  +
				  "]";
	}

	/**
	 * Used to transform the GPstatus into HMessage <br>
	 * 
	 * @param gPStatus a google plus status for a page
	 * @return HMessage an hMessage with a filtered payload of type GPStatus
	 */
	public HMessage tranformeGPstatusToHMessage(GPStatus gPStatus){
		HMessage msg = new HMessage();
		msg.setType("GPStatus");		
		
		try {
			GPStatus gps = new GPStatus();
			gps.setCircledByCount(gPStatus.getCircledByCount());
			gps.setPlusOneCount(gPStatus.getPlusOneCount());
			gps.setDisplayName(gPStatus.getDisplayName());			
			msg.setPayload(gps);
			
		} catch (JSONException e) {
			log.error(" Error in building GPStatus, error type : ("+e );
		}
		
		return msg;
	}
	
	@Override
	public void onStatus(GPStatus gPStatus) {
		
		HMessage msg = tranformeGPstatusToHMessage(gPStatus);
		put(msg);	
	}
}
