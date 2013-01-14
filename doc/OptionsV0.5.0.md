###HOptions 

HOptions are the options used by the hApi.

```java
public class HOptions extends JSONObject{
	public String getTransport();
	public void setTransport(String transport);

	public JSONArray getEndpoints();
        public void setEndpoints(JSONArray endpoints);

        public int getTimeout();
        public void setTimeout(int timeout);

        public int getMsgTimeout();
        public void setMsgTimeout(int timeout);
        
        public HAuthCallback getAuthCB();
	public void setAuthCB(HAuthCallback authCB);
}
```
 
 * transport : Transport to connect to the hNode. Only one value is available nowadays: "socketio"  _Default value: "socketio"_
 * endpoint : Endpoint of the hNode. Expects an array from which one will be chosen randomly to do client side load balancing. Used only if socket mode.
 * timeout : Default timeout value used by the hAPI before rise a connection timeout error during connection attempt _default value is : 15000 ms_
 * msgTimeout : Default timeout value used by the hAPI for all the services except the send() function. _default value is : 30000 ms_
 * authCB : If you want use an external script for authentification you can add it here. You just need to use the user as attribut and return a user and his password

Example: 
```java
class ACB implements HAuthCallback{
       @Override
	public void authCb(String username, ConnectedCallback connectedCB) {
	       // do something	
               connectedCB.connect(login, password); 
	}
}
```
```java
	options.setAuthCB(new ACB()); // add an instance of ACB in HOptions
	client.connect(login, password, options);
```

###HMessageOptions

HMessageOptions are the options used by the HMessage.

```java
public class HMessageOptions {
	
	private String ref = null;
	private String convid = null;
	private HMessagePriority priority = null;
	private DateTime relevance = null;
	private Boolean persistent = null;
	private HLocation location = null;
	private String author = null;
	private JSONObject headers = null;
	private DateTime published = null;
	private Integer timeout = 0;
}
```
 * ref : The msgid of the message referred to
 * convid : The conversation id to use if the message should take place in a conversation
 * priority : priority of the message. If UNDEFINED, priority lower to 0. See [HMessagePrioity](https://github.com/hubiquitus/hubiquitus4j/wiki/Code-v-0.5.0)
 * relevance : specifies the end of relevance
 * persistent : indicate if the HMessage is persistent
 * location : the location of the HMessage. See [HLocation](https://github.com/hubiquitus/hubiquitus4j/wiki/hAPI-Datamodel-v-0.5.0)
 * author : the author of the HMessage
 * headers : the headers of the HMessage. See [HHeader](https://github.com/hubiquitus/hubiquitus4j/wiki/hAPI-Datamodel-v-0.5.0)
 * published : Allows the client to set a specific published date. If not specified the hServer will set the published date.
 * timeout : Time (in ms) to wait for a response before hAPI sends a timeout