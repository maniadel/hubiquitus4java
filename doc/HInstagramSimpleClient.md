The HInstagram is an Hubiquitus component can be used independently of Hubiquitus, we want to say used it with a simple client. It provides the same function as in the case Hubiquitus. HInstagram retrive data from Instagram API.

## How to use HInstagram ?

You need to add the HInstagram dependecy, if  you use maven project the pom is looked like:

```xml
<dependency>
	<groupId>org.hubiquitus.hubiquitus4j.HInstagramAPI</groupId>
	<artifactId>HInstagramAPI</artifactId>
	<version>1.0.0</version>
</dependency>
```

## Exemple HInstagram simple client

```java
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
				null,                                 // YourProxyHost if any else null
				0,                                    // YourProxyPort if any else 0
				"cocacola",                           // YourTag
				"full",                               // YourOption 
				"c78fd8e9de7c0c5575298fce",           // Your Client_id
				3000);                                // refresh time
		instagramTags.addListener(   new HInstagramGetTagsSClient()   );	
		instagramTags.start();		
	}
}
```

