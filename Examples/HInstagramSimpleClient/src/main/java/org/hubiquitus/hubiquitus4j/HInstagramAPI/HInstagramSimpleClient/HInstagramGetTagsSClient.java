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




package org.hubiquitus.hubiquitus4j.HInstagramAPI.HInstagramSimpleClient;

import org.hubiquitus.hubiquitus4j.HInstagramAPI.HInstagramAPI.GetInstagramTags;
import org.hubiquitus.hubiquitus4j.HInstagramAPI.HInstagramAPI.InstagStatus;
import org.hubiquitus.hubiquitus4j.HInstagramAPI.HInstagramAPI.InstagramTagsListners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HInstagramGetTagsSClient implements InstagramTagsListners
{
	final static Logger log = LoggerFactory.getLogger(HInstagramGetTagsSClient.class);
	

	@Override
	public void onStatus(InstagStatus item) {
		log.info("[Instagram Messaage] :"+item );
		
	}
   
	public static void main(String[] args) {
		
		GetInstagramTags  instagramTags = new GetInstagramTags(
				"0.0.0.0",                         // YourProxyHost if any else null
				 0,                                // YourProxyPort if any else 0
				"cocacola",                        // YourTag
				"full",
				"yourInstagramClient_id",          // Your Instagram Client_id
				3000);                             // refresh time
		instagramTags.addListener(   new HInstagramGetTagsSClient()   );	
		instagramTags.start();
		
	}
}
