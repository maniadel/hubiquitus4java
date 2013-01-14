## Topology
The toplogy of Hubot is defined in format Json in the file "config.txt" by client.
### HActor
Actors are defined with a topology description. Nowadays, this structure is used to set some filters for the received messages. In the huBots section this structure will be extended to describe the topology of an hubot. In the Channels section this structure will be extended to describe the topology of a channel.

It contains : 
* `String` type : The type of the hActor. It contains the name of the implementation used for the hActor. Mandatory.
* `String` actor : The JID of the actor. Used by the actor to establish a connection with the hserver. Mandatory.
* `String` pwd : The password used by the actor to establish a connection with the hserver.
* `String` hserver : The endpoint of the hserver, used to establish a connection with the hserver. 
* `String` endpoint : Reserved for future usage.
* `HCondtion` filter : The filter to set on the current session when the connection with the hserver is established. see setFilter for details.

Sample:

```js
{
        "type": "com.mycompany.WordCount",
        "name": "myWordCountBot@myDomain.com",
        "pwd":	"password”,
        "hserver": "http://localhost:8081",
        "filter":	{ "in" : { "publisher", ["u1@myDomain.com", "u2@myDomain.com"] }  }
}
```



### HAdapterConf
The hAdapterConf describe the configuration of the Adapters.

It contains : 
* `String` actor : The JID of the actor. If this value is a channel actor (begin with '#') then the hubot will subscribe to the channel automatically. Mandatory.
* `String` type : The type of adapter. It must be the class used to implement the adaptor. This value is ignore if the actor is a channel.

             Supported values are :
                  1. org.hubiquitus.hubotsdk.adapters.HTimerAdapterInbox
                  2. org.hubiquitus.hubotsdk.adapters.HHttpAdapterInbox
                  3. org.hubiquitus.hubotsdk.adapters.HHttpAdapterOutbox
                  4. org.hubiquitus.hubotsdk.adapters.HTwitterAdapterInbox
                  5. org.hubiquitus.hubotsdk.adapters.HTwitterAdapterOutbox

* `JSONObject` properties : Depends on the type of adapter used.


### HTopology

## Hubot API

```java
protected final void start();
protected void init(HClient hClient);
protected final void initialized();
protected final void stop();
protected abstract void inProcessMessage(HMessage incomingMessage); 
protected final void send(HMessage hmessage);
protected final void send(HMessage hmessage, HMessageDelegate callback);
protected final void send(HMessage hmessage, HMessageDelegate callback, long timeout);
protected void updateAdapterProperties(String actor, JSONObject properties);
protected final AdapterInbox getAdapterInbox(String actor);
protected final AdapterOutbox getAdapterOutbox(String actor);
protected final JSONObject getProperties();
protected HMessage buildMessage(String actor, String type, Object payload, HMessageOptions options) throws MissingAttrException;
protected HMessage buildConvState(String actor, String convid, String status, HMessageOptions options) throws MissingAttrException;
protected HMessage buildAck(String actor, String ref, HAckValue ack, HMessageOptions options) throws MissingAttrException;
protected HMessage buildAlert(String actor, String alert, HMessageOptions options) throws MissingAttrException;
protected HMessage buildMeasure(String actor, String value, String unit, HMessageOptions options) throws MissingAttrException;
protected HMessage buildCommand(String actor, String cmd, JSONObject params, HMessageOptions options) throws MissingAttrException;
protected HMessage buildResult(String actor, String ref, ResultStatus status, String result, HMessageOptions options) throws MissingAttrException;
protected HMessage buildResult(String actor, String ref, ResultStatus status, boolean result, HMessageOptions options) throws MissingAttrException;
protected HMessage buildResult(String actor, String ref, ResultStatus status, double result, HMessageOptions options) throws MissingAttrException;
protected HMessage buildResult(String actor, String ref, ResultStatus status, JSONArray result, HMessageOptions options) throws MissingAttrException;
protected HMessage buildResult(String actor, String ref, ResultStatus status, JSONObject result, HMessageOptions options) throws MissingAttrException;
```
where : 
* `start()` : Connect the Hubot to the hAPI with params set in the file "config.txt".
* `init(HClient hClient)` : An Hubot may override this method in order to perform its own initializations. At the end, the hubot must call the `initialized()` method to create and start the adapters and routes.
* `initialized()` : Create the hubotAdapter and all adapters declared in the file. Call the `startAdapters` to start all adapters. Set the status status READY when its over.
* `stop()` : Stop all the adapters and the hubot.
* `inProcessMessage(HMessage incommingMessage)` : A hubot must override this method in order to treat the message received.
* `send(HMessage hmessage)` : Send an HMessage to a specified adapter outbox.
* `send(HMessage hmessage, HMessageDelegate callback)` : Send an HMessage to another actor.
* `protected final void send(HMessage hmessage, HMessageDelegate callback, long timeout)` : Send an HMessage to another actor with a timeout for the response.
* `updateAdapterProperties(String actor, JSONObject properties)` : Update the specified adapter's properties. Note : it doesn't update the file _config.txt_.
* `getAdapterInbox(String actor)` : Get the instance of a specified adapterInbox.
* `getAdapterOutbox(String actor)` : Get the instance of a specified adapterOutbox.
* `getProperties()` : Get the properties of the hubot.
* `buildMessage(String actor, String type, Object payload, HMessageOptions options)` : Helper to create a HMessage. Payload type could be instance of JSONObject(HAlert, HAck, HCommand ...), JSONObject, JSONArray, String, Boolean, Number.
* `buildConvState(String actor, String convid, String status, HMessageOptions options)` : Helper to create a hMessage with a hConvState payload.
* `buildAck(String actor, String convid, String status, HMessageOptions options)` : Helper to create a hMessage wiht a hAck payload.
* `buildAlert(String actor, String alert, HMessageOptions options)` : Helper to create a hMessage with a hAlert payload.
* `buildMeasure(String actor, String value, String unit, HMessageOptions options)` : Helper to create a hMessage with a hMeasure payload.
* `buildCommand(String actor, String cmd, JSONObject params, HMessageOptions options)` : Helper to create a hMessage with a hCommand payload.
* `buildResult(String actor, String ref, ResultStatus status, result, HMessageOptions options)` : Helper to create a hMessage with a hResult payload. The result type could be : String, boolean, double, JSONArray, JSONObject.



## Adapter API
* `start()` : Start the adapter.
* `stop()` : Stop the adapter.
* `setProperties(JSONObject properties)` : Set the properties of the adapter.

### HChannelAdapterInbox
* `start()` : Subscribe to the channel.
* `stop()` : Unsubscribe from the channel.
* `setProperties(JSONObject properties)` : Set the properties of the channel adapter.

### HTimerAdapterInbox
* `start()` : Start the timer adapter. 
* `stop()` : Stop the timer adapter.
* `setProperties(JSONObject properties)` : Set the properties of the timer adapter.

### HTwitterAdapterInbox
* `start()` : Get ready to receive the tweet.
* `stop()` : Shutdown the adapter.
* `setProperties(JSONObject properties)` : Set the properties of the twitter adapter inbox.

### HTwitterAdapterOutbox
* `start()` : Get ready to send the tweet.
* `stop()` : Shutdown the adapter.
* `setProperties(JSONObject properties)` : Set the properties of the twitter adapter outbox.

### HHttpAdapterInbox
* `start()` : Get ready to receive the http message.
* `stop()` : Stop the http adapter inbox.
* `setProperties(JSONObject properties)` : Set the properties of the http adapter inbox.

### HHttpAdapterOutbox
* `start()` : No operation will be done.
* `stop()` : No operation will be done.
* `setProperties(JSONObject properties)` : No operation will be done.