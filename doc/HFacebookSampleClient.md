# HFacebook For a Simple Client 

The HFacebook in this part, it used by a simple client independently of Hubiquitus. it provides the same function as in the case Hubiquitus.

## How to use HFacebook  for the Simple Client? 

In this part, we use Facebook to get periodically number of likes of an facebook page desired. to use it, you just have to generate the jar _HFacebook-1.0.0.jar_ (with maven). In your project you just need to import the pakage named _org.hubiquitus.hfacebook_ and use the class GetLikeFacebook. 
Bellow you can see a sample of code. 

## Exemple HFacebook client 

```java

package org.hubiquitus.hfacebookSimpleClient;

import org.hubiquitus.hfacebook.publics.FBStatus;
import org.hubiquitus.hfacebook.publics.GetLikeFacebook;
import org.hubiquitus.hfacebook.publics.HFacebookListners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FaceBookSimpleClient implements HFacebookListners
{
	final static Logger log = LoggerFactory.getLogger(FaceBookSimpleClient.class);
	
    public void onStatus(FBStatus fbs) {
    	log.info("[Facebook] Recieved status  :"+fbs.getLikes());
	}
    
	public static void main( String[] args )
    {		
		GetLikeFacebook glfb = new GetLikeFacebook(
					null, 		// yourProxyHost if any
					0,			// yourProxyPort if any
					"cocacola",	// facebook page name
					2000);		// On milliseconds refresh rate
		glfb.addListener(new FaceBookSimpleClient());
		glfb.start();
    }
	
}
```

The adequate pom.xml is :

```xml
<dependency>
        <groupId>org.hubiquitus.hubiquitus4j.hfacebook</groupId>
	<artifactId>HFacebook</artifactId>
	<version>1.0.0</version>
</dependency>
```