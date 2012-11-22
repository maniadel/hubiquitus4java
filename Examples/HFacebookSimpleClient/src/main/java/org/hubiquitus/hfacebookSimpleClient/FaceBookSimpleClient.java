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


package org.hubiquitus.hfacebookSimpleClient;



import org.hubiquitus.hfacebook.publics.FBStatus;
import org.hubiquitus.hfacebook.publics.GetLikeFacebook;
import org.hubiquitus.hfacebook.publics.HFacebookListners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FaceBookSimpleClient extends GetLikeFacebook implements HFacebookListners
{
	final static Logger log = LoggerFactory.getLogger(FaceBookSimpleClient.class);
	
    public FaceBookSimpleClient(String proxyHost, int proxyPort,
			String pageName, long roundTime) {
		super(proxyHost, proxyPort, pageName, roundTime);
		
	}
    public void onStatus(FBStatus fbs) {
    	log.info("-----> [Facebook] Recived status  :"+fbs);
	}
    
	public static void main( String[] args )
    {		
		String proxyHost = "0.0.0.0";   // yourProxyHost
		int proxyPort = 3128;           // yourProxyPort
		String pageName ="cocacola";    // facebook page name
		long roundTime = 6000;          // On milliseconds
		
		FaceBookSimpleClient like = new FaceBookSimpleClient(proxyHost, proxyPort, pageName, roundTime);
		like.addListener(like);
		like.start();		
    }
	
		
}
