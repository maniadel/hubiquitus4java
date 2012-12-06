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
