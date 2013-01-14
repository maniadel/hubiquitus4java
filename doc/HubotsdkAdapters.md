In order to make the inbox accessible to message publishers, the JSDK provide a set of adapters responsible to provide a remote API to the inbox. These adapters provide also the set of transports required to pass the content of the outboxes to their final destinations.

Here is the adapters already provided : 

## HubotAdapter : 

**This Adapter is always used and don't need to be explicitly declared.**

### Inbox 

Populates the inbox with messages addressed explicitely to the hubot

### Outbox 

Sends the content of an outbox to a target entity.

## HChannelAdapter : 

### Inbox 

Populates the inbox with hmessages received from a suscribed hchannel. This Adapter automaticaly subscribe to the channel.

### Configuration Adapter : 
```js
{
    "actor" : "#myChan@myDomain"
}
```
where :
* actor : The channel id of the hChannel you subscribe.

## HTimerAdapter:
### inbox
Populates the inbox with a dedicated hMessage with a configured payload on timeout event.
### Configuration Adapter : 
```js
{
      "actor" : "myTimer@myDomain",
      "type" : "org.hubiquitus.hubotsdk.adapters.HTimerAdapterInbox",
      "properties" : {
              "mode" : "millisecond", 
              "period" : "30000" 
       }
}
or
{
      "actor" : "myTimer@myDomain",
      "type" : "org.hubiquitus.hubotsdk.adapters.HTimerAdapterInbox",
      "properties" : {
              "mode" : "crontab", 
              "crobtab" : "0 37 14 ? * *" 
       }
}
```
Note : see [crobtab format](http://quartz-scheduler.org/documentation/quartz-1.x/tutorials/crontrigger)

## HHttpAdapter : 

### inbox

 Populates the inbox with hmessages of type HHttpData.

### Configuration Adapter : 
```js
{
    "actor" : "myHttpAdapterInbox@myDomain", 
    "type" : "org.hubiquitus.hubotsdk.adapters.HHttpAdapterInbox",
    "properties" : {
          "host" : "0.0.0.0",
          "port" : "80",
          "path" : ""
    }
}
```
Where : 
* host : Hostname or ipadress of the http server. Will only be listening to this host. Default value: "0.0.0.0" (any host).
* port : Port of the http server. Default value : 80.
* path : Prefix of the watched path. (ie : login?user=hubi). Default value : "".

### outbox

Posts the message of the outbox to a remote HTTP entity


### Configuration Adapter : 
```js
{
    "actor" : "myHttpAdapterOutbox@myDomain", 
    "type" : "org.hubiquitus.hubotsdk.adapters.HHttpAdapterOutbox",
}
```

### HHttpData : 
```js
public class HHttpData implements HJsonObj {
    public JSONObject getAttachments();
    public void setAttachments(JSONObject attachments);

    public byte[] getRawBody();
    public void setRawBody(byte[] rawBody);

    public String getMethod();
    public void setMethod(String method);

    public String getQueryArgs();
    public void setQueryArgs(String queryArgs);

    public String getQueryPath();
    public void setQueryPath(String queryPath);

    public String getServerName();
    public void setServerName(String serverName);

    public Integer getServerPort();
    public void setServerPort(Integer serverPort);
}
```
Where : 
* attachements : Post attachements send with the query.
* rawBody : Body content as raw bytes encoded in Base64 for json.
* method : Define the method of the data. Possible values : get, post, put, delete. Mandatory.
* queryArgs : Parameters applied to the URI. (eg : "?a=2").
* queryPath : Path to the resource. (eg : "/path").
* serverName : Hostname used to do the query. (eg : "localhost"). Mandatory.
* serverPort : Port used to do the query. Mandatory.

### HHttpAttachement : 
```js
public class HHttpAttachement implements HJsonObj {
    public String getName();
    public void setName(String name);
    
    public byte[] getData();
    public void setData(byte[] data);

    public String getContentType();
    public void setContentType(String contentType);
}
```
Where : 
* name : Name of the attachment.
* data : Data raw bytes encoded in Base64 for json.
* contentType : Type of content. (eg : application/Octet-stream).

## HTwitterAdapter : 

### inbox

 Populates the inbox with hmessages of type HTweet.

#### Configuration Adapter : 
```js
{
    "actor" : "myTwitterAdapterInbox@twitter.com", 
    "type" : "org.hubiquitus.hubotsdk.adapters.HTwitterAdapterInbox",
    "properties" : {
                      "consumerKey" : "",
                      "consumerSecret" : "",
                      "twitterAccessToken" : "",
                      "twitterAccessTokenSecret" : "",
                      "tags" : "",
                      "content": "",
                      "langFilter" : ""
    }
}
```
Where : 
* consumerKey: Domain identifying the third-party web application, and Key for acces to the twitter api .
* consumerSecret: Domain identifying the third-party web application, and password for acces to the twitter api.
* twitterAccessToken: Used to sign requests with your own Twitter account.
* twitterAccessTokenSecret: Used to sign requests with your own Twitter account.
* content: Used to define the type of a hTweet. ex: "complete", "light".
* tags: Specified hashtag used for finding Tweets. ie : "tag1,tag2,tag3"
* langFilter: Applying language filter for finding Tweets. ie : "fr"

### Outbox
Tweets the content of an outbox to a twitter account.

#### Configuration Adapter : 
```js
{
    "actor" : "myTwitterAdapterOutbox@twitter.com", 
    "type" : "org.hubiquitus.hubotsdk.adapters.HTwitterAdapterOutbox",
    "properties" : {
                      "consumerKey" : "",
                      "consumerSecret" : "",
                      "twitterAccessToken" : "",
                      "twitterAccessTokenSecret" : ""
    }
}
```
Where : 
* consumerKey: Domain identifying the third-party web application, and Key for acces to the twitter api .
* consumerSecret: Domain identifying the third-party web application, and password for acces to the twitter api.
* twitterAccessToken: Used to sign requests with your own Twitter account.
* twitterAccessTokenSecret: Used to sign requests with your own Twitter account.


### HTweet : 
```java
public class HTweet implements HJsonObj {
    public String getText();
    public void setText(String tweetText);
   
    public String getSource();
    public void setSource(String source);

    public JSONObject getAuthor();
    public void setAuthor(HTweetAuthor tweetAuthor);

    public long getId();
    public void setId(long id);
}
```
Where :
* Text : Text of the status.
* source : The Application source.
* author : The author of tweet.
* id : Id of the status.


### HAuthorTweet : 
```java
public class HAuthorTweet implements HJsonObj {
    public int getStatus();
    public void setStatus(int status);

    public int getFollowers();
    public void setFollowerst(int followers);

    public String getName();
    public void setName(String name);

    public int 	getFriends();
    public void setFriends(int friends);

    public String getLocation();
    public void setLocation(String location);

    public String getDescription();
    public void setDescription(String description);

    public String getProfileImg();
    public void setProfileImg(String profileImg);
    
    public String getURL();
    public void setUrl(URL url);

    public Calendar getCreatedAt();
    public void setCreatedAt(Calendar createdAt);

    public String getLang();
    public void setLang(String lang);

   public boolean getGeo();
   public void setGeo(boolean geo);

   public boolean getVerified()
   public void setVerified(boolean verified);

   public int getListeds();
   public void setListeds(int listeds);
}
```
Where :
* author: Author Name.
* status: Count of Status on user timeline.
* followers: Count of followers on user profile.
* name: Complete Name Of author.
* friends: Count of friends on user profile.
* location: Location of user.
* description: Description on user profile.
* profileImg: Picture on user profile.
* url: url on user profile.
* createdAt : Date of creation of the profile.
* lang : language on user profile.
* geo : see if the geolocation if active on user profile.
* verif: see if the user profile is verified.
* listeds: Listed Count of user.


## HGooglePlus adapters : 

HGooglePlus is used in order to communicate with GooglePlus API and collect the information about a GooglePlus page. 

### Inbox 

Populates the inbox with HMessages of type GPStatus or GPActivity.

### Configuration Adapter :

#### 1. HGooglePlusOneCercledInBox

We use this adapter to collect number of +One of Google Plus page and number of people who cercled this page.
The adpater configuration is represented  as following:
```js
{
	"type" : "com.mycompany.GouglePlusOneCount",
	"actor" : "user@domain",
	"pwd" : "UserPassword",
	"hserver" : "YourEndPoint",
	"adapters" : [ 
		{
			"actor" : "HGooglePlusOneCercledInBox@googlePlus", 
			"type" :  "org.hubiquitus.hubotsdk.adapters.HGooglePlusOneCercledInBox",
			"properties" : {							
				"proxyHost":"yourProxyHost",
				"proxyPort":yourProxyPort,
				"googlePlusNameOrId" : "Your GooglePageName or GooglePageId",
				"roundTime" : RefreshTime on milliseconds,
				"APIKey" : "yourGoogleAPI Key"
			}			
		}	
	]
}
```
Where:

* _**googlePlusNameOrId:**_ Specified  Name or Id GooglePlus Page used for finding information. ie : "+Coca-cola, 111883881632877146615"
* _**roundTime:**_ is refresh time on milliseconds (if you use a free google plus developper account you have only 5.0 requests/second/user but you should set this value to 12 seconds in order to have a continus processing) 
* _**APIKey:**_  Key to acces to the GooglePlus API, it means Simple API Access. 

**Received Message:** 

The received message is looked like: 

```json
{
    "payload": {
        "plusOneCount": 654609,
        "circledByCount": 643929,
        "displayName": "Coca-Cola"
    },
    "type": "GPStatus"
}
```
#### 2. HGooglePlusActivityInbox

This adapter is used to collect the activities about an tag or a word in google plus.
The configuration is represented as following:

```json
{
    "type" : "com.mycompany.GPlusActivityCount",
    "actor" : "user@domain",
    "pwd" : "userPassword",
    "hserver" : "EndPoint",
    "adapters": [
        {
            "actor": "HGooglePlusActivityInbox@googlePlus",
            "type": "org.hubiquitus.hubotsdk.adapters.HGooglePlusActivityInbox",
            "properties": {
                "proxyHost": "yourProxyHost",
                "proxyPort": yourProxyPort,
		"query": "yourWord",
		"language": "en",
		"maxResults": 20,
		"orderBy": "best",

                "roundTime": 12000,
                "APIKey": "yourAPIKey"
            }
        }
    ]
}
```
Where :

* _**query:**_  word to search
* _**language:**_ google plus post language. ie (en, fr, ar) [see language code](https://developers.google.com/+/api/search#available-languages)
* _**maxResults:**_ The maximum number of activities to include in the response,  which is used for paging. For any response, the actual number returned might be less than the specified maxResults. Acceptable values are 1 to 20, inclusive. (Default: 10) 
* _**orderBy:**_ Specifies how to order search results. 
     * Acceptable values are:
             - _"best":_ Sort activities by relevance to the user, most relevant first.
             - _"recent":_ Sort activities by published date, most recent first. (default)

for more informations about, file parameters you can see this link [[Click here](https://developers.google.com/+/api/latest/activities/search) ]


### GPStatus  'GooglePlus Status'
```java
public class GPStatus implements HJsonObj {

    public long getPlusOneCount();
    public void setPlusOneCount(long plusOneCount);

    public long getCircledByCount();
    public void setCircledByCount(long circledByCount);

    public String getDisplayName();
    public void setDisplayName(String displayName);
}
```
Where:
* _**PlusOneCount:**_ number of people who put on +1 at GooglePlus page;
* _**CircledByCount:**_  number of people who cercled the GooglePlus page;
* _**DisplayName:**_  name of GooglePlus page.



### GPItem'GooglePlus Item'
```java
public class GPActivity implements HJsonObj {

public String getPublished();
public void setPublished(String published);

public String getTitle();
public void setTitle(String title);

public String getUpdated();
public void setUpdated(String updated);

public String getDisplayName();
public void setDisplayName(String displayName);

public String getId();
public void setId(String id);

public String getUrl();
public void setUrl(String url);

public String getImage();
public void setImage(String image);

public String getContent();
public void setContent(String content);

public JSONArray getAttachments();
public void setAttachments(JSONArray attachments);

public String getActorUrl();
public void setActorUrl(String actorUrl);

public String getActorId();
public void setActorId(String actorId);

}
```

## HFacebook adapters : 

This adapter is based on the project hfacebook (see ). It allow you to generate a simple stream of data based on informations provided by Facebook's API.

For more information you can [[See HFacebook Simple Client](https://github.com/maniadel/hubiquitus4j/wiki/HFacebook-For-a-Simple-Client)] sample.

### Inbox 

Populates the inbox with HMessages of type FBstatus.

### Configuration Adapter :

The HFacebook adpter is represented as following

```js
{
	"type" : "com.mycompany.LikeCount",
	"actor" : "user@domain",
	"pwd" : "YourPassword",
	"hserver" : "YourEndPoint",
	
	"adapters" : [ 
		{	"actor" : "yourfacebook@facbook.com", 
			"type" :  "org.hubiquitus.hubotsdk.adapters.HFacebookAdapterInbox",
			"properties" : {
				"proxyHost":"YourProxyHost",
				"proxyPort":yourProxyPort(Long not a String),
				"pageName":"FacebookPage",
				"roundTime":TimeToCheck on milliseconds(Long not a String)
			}			
		}
	  ]
}
```
Where :
* _**pageName:**_ is a facebook page desired to count the number of its likes. (ie: cocacola, M6, Mohamed-Fellag, shakira, ...).
* _**roundTime:**_ is the time in milliseconds to check the facebook page. (ie: roundTime = 6000 means an update every 6 seconds).


**Observation:**

If your facebook page does not exist or does not contain the attribute named "like"; The process will be stopped and message error will be displayed.

###FBStatus 'Facebook Status'

```java
public class FBStatus implements HJsonObj {
   
    public long getLikes();
    public long getTalkingAboutCount();
    public long getCheckins();
}
```

## HInstagram adapters : 

HInstagramRealTimeInbox is an Hubiquitus adpater used in order to collect data from Instagram API.

### Inbox 

Populates the inbox with HMessages of type InstagramStatus

### Configuration Adapter:

We use this adapter to retrieve data from Instagram API in "Real Time". The adpater configuration is represented as following:

```json
{
    "type": "com.mycompany.WordCount",
    "actor": "yourActor@domain",
    "pwd": "yourPassword",
    "hserver": "EndPoint",
    "adapters": [
        {
            "actor": "HInstagram@Instagram",
            "type": "org.hubiquitus.hubotsdk.adapters.HInstagramRealTimeInbox",
            "properties": {
                "port": yourPort,
		"clientId":"InstagramClientId",
                "clientSecret":"InstagramClientSecret",
        	"object":"tag",
                "aspect":"media",
                "object_id":"yourInstagramTag",
     		"verifyToken":"YourVerfyToken",
     		"callbackUrl": "yourUrlCallBack"   	
            }
        }
    ]
}
```

Where:

- **port** :         port of your choice to listen http. ie (8088)
- **clientId** :      Instagram clien id, you get it at the instagram subscription.
- **clientSecret** :  Instagram clien secret, you get it at the instagram subscription.
- **object**:       The object you'd like to subscribe to (in this case, "tag").
- **aspect** :        The aspect of the object you'd like to subscribe to (for this adapter, "media"). Note that Instagram API only support "media" at this time, but it might supports other types of subscriptions in the future. 
- **object_id** : used to subscribe to new photos tagged with certain words. ie (Paris, USA, TIKJDA, ...), you can use 'object_id = nofilter' to listen all notification of new photos. You  will receive a POST request at the callback URL every time anyone posts a new photo with the tag. 
- **verifyToken** : "you can chose it randomly" ie(19a78b66c)
- **callbackUrl** : is your url call back, ie( yourEndPoint:YourPort)

**Received message :**

The received message is looked like this exemple:

```JSON
{
    "headers": {
        "subscription_id": 2749099
    },
    "author": "HInstagram@Instagram",
    "payload": {
        "id": "355027241693577908_254738807",
        "tags": [
            "hubiquitus"
        ],
        "location": null,
        "likes": {
            "count": 0,
            "data": []
        },
        "link": "http://instagr.am/p/TtT2Uyu4K0/",
        "images": {
            "thumbnail": {
                "height": 150,
                "width": 150,
                "url": "http://distilleryimage8.s3.amazonaws.com/ddc5c3ee4f8011e2b2f422000a9f1255_5.jpg"
            },
            "low_resolution": {
                "height": 306,
                "width": 306,
                "url": "http://distilleryimage8.s3.amazonaws.com/ddc5c3ee4f8011e2b2f422000a9f1255_6.jpg"
            },
            "standard_resolution": {
                "height": 612,
                "width": 612,
                "url": "http://distilleryimage8.s3.amazonaws.com/ddc5c3ee4f8011e2b2f422000a9f1255_7.jpg"
            }
        },
        "caption": {
            "id": "355027288636227765",
            "text": "#hubiquitus hello",
            "from": {
                "id": "254738807",
                "profile_picture": "http://images.instagram.com/profiles/profile_254738807_75sq_1354702881.jpg",
                "username": "maniadel",
                "full_name": "mani"
            },
            "created_time": "1356542572"
        },
        "type": "image",
        "created_time": "1356542566",
        "user": {
            "id": "254738807",
            "profile_picture": "http://images.instagram.com/profiles/profile_254738807_75sq_1354702881.jpg",
            "username": "maniadel",
            "bio": "",
            "website": "",
            "full_name": "mani"
        },
        "filter": "Normal",
        "comments": {
            "count": 0,
            "data": []
        },
        "attribution": null
    },
    "published": "2012-12-26T18:16:55.397+01:00",
    "type": "HInstagramRT"
}

```

