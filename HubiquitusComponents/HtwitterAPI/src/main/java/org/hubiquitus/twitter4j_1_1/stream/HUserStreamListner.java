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

package org.hubiquitus.twitter4j_1_1.stream;

import org.json.JSONObject;

public interface HUserStreamListner {	

	/**
	 * called when a status is received throw the Streaming API
	 * @param status
	 */
	public void onStatus(JSONObject status);

	/**
	 * called when a technical message is received throw the Streaming API
	 * @param message
	 */
	public void onOtherMessage(JSONObject message);
	
	/**
	 * called when a stallWarning message is received throw the Streaming API
	 * @param stallWarning
	 */
	public void onStallWarning(JSONObject stallWarning);

	/**
	 * called when a Deletion notices message is received throw the Streaming API
	 * @param delete
	 */
	public void onStatusDeletionNotices(JSONObject delete);

	/**
	 * called when a Location Deletion notices message is received throw the Streaming API
	 * @param scrubGeo
	 */
	public void onLocationDeletionNotices(JSONObject scrubGeo);
	
	/**
	 * called when a Location Deletion notices message is received throw the Streaming API
	 * @param scrubGeo
	 */
	public void onLimitNotices(JSONObject limit);
	
	/**
	 * called when a status withheld message is received throw the Streaming API
	 * @param statusWithheld
	 */
	public void onStatusWithheld(JSONObject statusWithheld);
	
	/**
	 * called when a UserWithheld message is received throw the Streaming API
	 * @param userWithheld
	 */
	public void onUserWithheld(JSONObject userWithheld);

	/**
	 * called when a Disconnect  message is received throw the Streaming API
	 * @param userWithheld
	 */
	public void onDisconnectMessages(JSONObject disconnect);	
	
}
