Implementation of the hubiquitusClient. It allow to connect to an hNode server and use basic functions (Publish, suscribe, etc ...)

### Connect

Establishes a connection to hNode to allow the reception and sending of messages and commands.
Connection status are notified through the HStatusDelegate defined by the onStatus function.

```java
public void connect(String publisher, String password, HOption options)
```
Where:

* publisher : login of the publisher (ie : user@domain)
* password : publisher's password
* hOptions : hOptions object as defined in [HOptions](https://github.com/hubiquitus/hubiquitus4j/wiki/Options-v-0.5.0)

Note : if a technical disconnection is raised then the system will try to reconnect by itself automatically.

### Disconnect

Disconnect the user from the current working session.

```java
public void disconnect()
```

### onStatus

Set the delegate used to manage the hStatus structure sent by the hAPI when the status of the connection changed.

```java
public void onStatus(HStatusDelegate statusDelgate);
```
Where:
* statusDelegate : Delegate called on connection status update

```java
public interface HStatusDelegate {
	public void onStatus(HStatus status);
}
```
Where:
* status : new connection status

### onMessage

Set the delegate used to manage the hMessage structure sent by the hAPI when a message is sent by the hServer to the client.

```java
public void onMessage(HMessageDelegate messageDelegate);
```
Where:
* messageDelegate : Delegate called on incoming messages

```java
public interface HMessageDelegate {
	public void onMessage(HMessage message);
}
```
Where:
* message : Received message



### Send

_The client MUST be connected_ 

The hAPI sends the hMessage to the hserver which transfer it to the specified actor
The hserver will perform one of the following actions :
* If the actor is a channel (ie : #channelName@domain) the hserver will perform a publish operation of the provided hMessage to the channel and send an hMessage with hResult payload containing the published message and cmd name set with hsend to acknowledge publishing
* If the actor is either ‘session’ and payload type is ‘hCommand’ the server will handle it. In other cases, it will send an hMessage with a hResult error NOT_AUTHORIZED.
* If the actor is a jid, hserver will relay the message to the relevant actor.

Nominal response :
* If callback provided, an hMessage referring to sent message (eg : ref = hAPI client  msgid of sent message).
* If a timeout is provided and message didn’t arrive before timeout, callback is called with hMessage with an hResult error of type EXEC_TIMEOUT
* If response is from the hAPI or hserver, response will be an hMessage with an hResult payload.

```java
public void send(final HMessage message, final HMessageDelegate messageDelegate) 
```
Where:
* message : the HMessage to send
* messageDelegate : Delegate that will be notify when response message is available

```java
public interface HMessageDelegate {
	public void onMessage(HMessage message);
}
```
Where:
* message : response of the message

### Subscribe

_The client MUST be connected_ 

Demands the channel to subscribe.
The server will check if not already subscribed and if authorized subscribe him.

Nominal response : a hMessage with an hResult payload with no error.

```java
public void subscribe(String actor, HMessageDelegate messageDelegate) throws MissingAttrException
```
Where:
* actor : The channel jid to subscribe to.(ie : #test@domain”)
* messageDelegate : Delegate that will be notify when command result is available. See send for HMessageDelegate structure

```java
public class MissingAttrException extends Exception {
      public MissingAttrException(String attrName);

      public String getAttrName();
      public void setAttrName(String attrName);

      public String getMessage();
      public String getLocalizedMessage();
      public String toString();
}
```

### Unsubscribe

_The client MUST be connected_

Demands the channel an unsubscription.
The hAPI checks the current publisher’s subscriptions and if he is subscribed, performs an unsubscribe.

Nominal response : an hMessage with an hResult where the status 0.

```java
public void unsubscribe(String actor, HMessageDelegate messageDelegate) throws MissingAttrException
```
Where:
* actor : The channel to unsubscribe from.
* messageDelegate : Delegate that will be notify when command result is available. See command for HMessageDelegate structure

### GetLastMessages

_The client MUST be connected_

Demands the channel a list of the last messages saved. The requester must be in the channel’s subscribers list.

Nominal response: a hMessage with an hResult having an array of hMessages.

If nbLastMsg is not provided, the default value found in the channel header will be used and as fall back a default value of 10.

```java
public void getLastMessages(String actor, int nbLastMsg, HMessageDelegate messageDelegate) throws MissingAttrException
```
or 
```java
public void getLastMessages(String actor, HMessageDelegate messageDelegate) throws MissingAttrException
```
Where:
* actor : The channel jid of the messages. Mandatory.
* nbLastMsg : the number of message request to the server. if <= 0, the default value found in the channel header will be used and as fall back a default value of 10.
* messageDelegate : Delegate that will be notify when command result is available. See command for HMessageDelegate structure

### GetSubscriptions

_The client MUST be connected_

Demands the server a list of the publisher’s subscriptions.

Nominal response : a hMessage with a hResult payload contains an array of channel id which are all active.

```java
public void getSubscriptions(HMessageDelegate messageDelegate) throws MissingAttrException
```
Where:
* messageDelegate : Delegate that will be notify when command result is available. See command for HMessageDelegate structure

### GetThread

_The client MUST be connected_

Demands to the channel the list of messages correlated by the convid value.
Nominal response : hMessage with an hResult payload where the status is 0 and result is an array of hMessage.

```java
public void getThread(String actor, String convid, HMessageDelegate messageDelegate) throws MissingAttrException
```

Where:
* actor : The channel where the conversations are searched. Mandatory.
* convid : Convid searched. Mandatory.
* messageDelegate : Delegate that will be notify when command result is available. See command for HMessageDelegate structure.

### GetThreads

_The client MUST be connected_

Demands to the channel the list of convid where there is a hConvState with the status value searched.
Nominal response : hMessage with hResult where the status is 0 and result is an array of convid.

```java
public void getThreads(String actor, String status, HMessageDelegate messageDelegate) throws MissingAttrException
```

Where:
* actor : The channel id where the conversations are searched. Mandatory.
* status : Status searched. Mandatory.
* messageDelegate : Delegate that will be notify when command result is available. See command for HMessageDelegate structure.

### GetRelevantMessages

_The client MUST be connected_

Demands to the channel the list of the available relevant message.
Nominal response : hMessage with hResult where the status is 0 and result is an array of hMessage.

```java
public void getRelevantMessages(String actor, HMessageDelegate messageDelegate) throws MissingAttrException
```

Where:
* actor : The channel where the relevant messages are searched. Mandatory.
* messageDelegate : Delegate that will be notify when command result is available. See command for HMessageDelegate structure.

### SetFilter

_The client MUST be connected_

Set a filter to be applied to upcoming messages at the session level 

```java
public void setFilter(HCondition filter, HMessageDelegate messageDelegate) throws MissingAttrException
```

Where:
* filter : The filter to apply on the current session managed on the hnode side for this actor.
* messageDelegate : Delegate that will be notify when command result is available. See command for HMessageDelegate structure.



### Builders

For all the builders, if a mandatory field is missing, builders throw an exception :

```java
public class MissingAttrException extends Exception {
	public String getAttrName(); //name of the missing atttribute
	public String getMessage();
}
```


#### BuildMessage
Helper function to create a basic hMessage.

```java
public HMessage buildMessage(String actor, String type, Object payload, HMessageOption options) throws MissingAttrException
```
Where:
* actor : The actor the hMessage. Mandatory.
* type : The type of the hMessage. (ie: hack, halert, ...)
* payload : The payload for the hMessage (the payload should be of type described in type)
* options : hMessage creation options. See below for hMessageOptions Structure.
* return : hMessage msg created with the payload.

HMessageOptions Strucutre : 
```java
public class HMessageOptions {
	
	private String ref = null;
	private String convid = null;
	private HMessagePriority priority = null;
	private DateTime relevance = null;
	private int relevanceOffset = 0;
	private Boolean persistent = null;
	private HLocation location = null;
	private String author = null;
	private JSONObject headers = null;
	private DateTime published = null;
	private Integer timeout = 0;
}
```
Where (All fields are optional) :
 * ref : The msgid of the message refered to
 * convid : conversation id
 * priority : priority of the message. If UNDEFINED, priority lower to 0. See HMessagePrioity in codes and enum section.
 * relevance : Date and time until which the message is relevant
 * relevanceOffset : If you use this parameter, it will override the relevance one by updating the date-time for the relevance of the hMessage.
 * persistent : do the server need to persist the hmessage.
 * location : The location of the HMessage. See HLocation in hAPI DataModel
 * author : the author of the HMessage
 * headers : the headers of the HMessage. See HHeader in hAPI DataModel
 * published : Specify a publishing date.
 * timeout : Time (in ms) to wait for a response before hAPI sends a timeout.

#### BuildConvState

Build a hMessage with a hConvState payload

```java
public HMessage buildConvState(String actor, String convid, String status, HMessageOptions options) throws MissingAttrException
```
Where:
* actor : The channel id where to publish. Mandatory.
* convid : The conversation id. Mandatory. This will replace any convid set in hMessageOption.
* status : Status of the conversation. Mandatory.
* options : hMessage creation options. See buildMessage for hMessageOptions Structure.
* return : hMessage msg created with the payload.

#### BuildAck

Build a hMessage with a hAck payload

```java
public HMessage buildAck(String actor, String ref, HAckValue ack, HMessageOptions options) throws MissingAttrException
```
Where:
* actor : The actor for the hMessage.  Mandatory.
* ref : The msgid to acknowledged. Mandatory.
* ack : The ack value. See AckValue in Codes and enum. Mandatory.
* options : hMessage creation options. See buildMessage for hMessageOptions Structure.
* return : A hMessage with a hAck payload.

#### BuildAlert

Build a hMessage with a hAlert payload

```java
public HMessage buildAlert(String actor, String alert, HMessageOptions options) throws MissingAttrException
```
Where:
* chid : The channel id of the hMessage. Mandatory.
* alert : description of the alert. Mandatory.
* options : hMessage creation options. See buildMessage for hMessageOptions Structure.
* return : hMessage msg created with the payload.

#### BuildMeasure

Build a hMessage with a hMeasure payload

```java
public HMessage buildMeasure(String actor, String value, String unit, HMessageOptions options) throws MissingAttrException 
```
Where:
* actor : The actor for the hMessage. Mandatory.
* value : value of the measure in "unit" type.
* unit : unit type of the value (cm, m, g ...).
* options : hMessage creation options. See buildMessage for hMessageOptions Structure.
* return : A hMessage with hMeasure payload.


#### BuildCommand

Build a hMessage with a hCommand payload

```java
public HMessage buildCommand(String actor, String cmd, JSONObject params, HMessageOptions options) throws MissingAttrException 
```
Where:
* actor : The actor for the hMessage. Mandatory.
* cmd : The name of the command.
* params : Parameters of the command. Not mandatory.
* options : hMessage creation options. See buildMessage for hMessageOptions Structure.
* return : A hMessage with a hCommand payload.

#### BuildResult

Build a hMessage with a hResult payload

```java
public HMessage buildResult(String actor, String ref, ResultStatus status, JSONObject result, HMessageOptions options) throws MissingAttrException
public HMessage buildResult(String actor, String ref, ResultStatus status, JSONArray result, HMessageOptions options) throws MissingAttrException
public HMessage buildResult(String actor, String ref, ResultStatus status, String result, HMessageOptions options) throws MissingAttrException
public HMessage buildResult(String actor, String ref, ResultStatus status, boolean result, HMessageOptions options) throws MissingAttrException
public HMessage buildResult(String actor, String ref, ResultStatus status, double result, HMessageOptions options) throws MissingAttrException
```
Where:
* actor : The actor for the hMessage. Mandatory.
* ref : The id of the message received, for correlation purpose.
* status : Result status code. Mandatory.
* result : Result of the command.
* options : hMessage creation options. See buildMessage for hMessageOptions Structure.
* return : A hMessage with a hResult payload.