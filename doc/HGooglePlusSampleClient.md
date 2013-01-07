The HGooglePlus can be used independently Hubiquitus, we talk about a simple client. it provides the same function as in the case Hubiquitus. 
HGooglePlus generate a simple stream of data based on informations provided by GooglePlus API.


##  How to use HGooglePlus ?

First of all, you need to install HGooglePlus. You either generate the jar  from the github project [[click here](https://github.com/maniadel/hubiquitus4j/tree/master/HubiquitusComponents/HGooglePlus)]  and include it in your pom.xml file like:

```xml
<dependency>
	<groupId>org.hubiquitus.hubiquitus4j.hgoogleplus</groupId>
	<artifactId>HGooglePlus</artifactId>
	<version>1.0.0</version>
</dependency>
```

## Exemple GooglePlus simple client

### 1. Get PlusOne Cercledby of GooglePlus page desired

```java
package org.hubiquitus.hubiquitus4j.HGooglePlusSimpleClient;
import org.hubiquitus.hubiquitus4j.hgoogleplus.GPStatus;
import org.hubiquitus.hubiquitus4j.hgoogleplus.HGooglePlus;
import org.hubiquitus.hubiquitus4j.hgoogleplus.HGooglePlusListners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HGooglePlusSimpleClient implements HGooglePlusListners
{
	final static Logger log = LoggerFactory.getLogger(HGooglePlusSimpleClient.class);

    public void onStatus(GPStatus gps) {
    	
    	log.info("[GooglePlus] Recieved status :["+gps.getDisplayName()+"] --> [COUNT PLUS]: "+gps.getPlusOneCount()+" [Circled By Count]: "+gps.getCircledByCount());
	}
    
	public static void main( String[] args )
    {		
		HGooglePlus googlePlusClient = new HGooglePlus(
					null,  // yourProxyHost if any else null
					0,     // yourProxyPort if any else null
					"+Coca-cola", // GooglePlus page name or Id page
					15000,    // On milliseconds refresh rate
				    "YourAPIKey"  //API Key
				);		
		googlePlusClient.addListener(new HGooglePlusSimpleClient());
		googlePlusClient.start();
    }
}
```

**Result message received**

The message received when you run the last exemple (asking for Cocacola page), is looked like:

```json
{
    "etag": "\"xy7AeD5bgijDLuyUn5Oks8fofoI/fXy0WT3h2rtn-6xidYksdA7NDvw\"",
    "urls": [
        {
            "value": "http://www.coca-cola.com",
            "type": "other"
        }
    ],
    "plusOneCount": 648872,
    "image": {
        "url": "https://lh3.googleusercontent.com/-3o0qMaMvuwc/AAAAAAAAAAI/AAAAAAAAAng/in9Wik1E0eI/photo.jpg?sz=50"
    },
    "isPlusUser": true,
    "kind": "plus#person",
    "aboutMe": "<span><div>Coca-Cola is the most popular and biggest-selling soft drink in history, as well as the best-known product in the world.</div><div><br />Created in Atlanta, Georgia, by Dr. John S. Pemberton, Coca-Cola was first offered as a fountain beverage by mixing Coca-Cola syrup with carbonated water. Coca-Cola was introduced in 1886, patented in 1887, registered as a trademark in 1893 and by 1895 it was being sold in every state and territory in the United States. In 1899, The Coca-Cola Company began franchised bottling operations in the United States.<br /><br />Coca-Cola might owe its origins to the United States, but its popularity has made it truly universal. Today, you can find Coca-Cola in virtually every part of the world.</div><div><br /></div><div>House Rules: <a href=\"http://CokeURL.com/muwx\">http://CokeURL.com/muwx</a></div></span>",
    "url": "https://plus.google.com/113050383214450284645",
    "id": "113050383214450284645",
    "cover": {
        "coverInfo": {
            "leftImageOffset": 0,
            "topImageOffset": 0
        },
        "coverPhoto": {
            "height": 183,
            "width": 940,
            "url": "https://lh6.googleusercontent.com/-CEkdAivziSE/UABIM9aqVVI/AAAAAAAABVo/-1XDAkUwFFo/w940-h183/googleplusbanner-7.13.12.jpg"
        },
        "layout": "banner"
    },
    "verified": true,
    "circledByCount": 638245,
    "displayName": "Coca-Cola",
    "objectType": "page",
    "tagline": "Open Happiness. The official Coca-Cola page on Google+."
}
```