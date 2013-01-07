The hAPI datamodel defines semantics core to the Hubiquitus platform. It defines the core concepts of the hubiquitus programming model, the data structures associated with it and the way data is transmitted and processed through the network.

API heavily relies on JSON, which has been preferred to XML for two main reasons :

* Efficiency : JSON is structured but lightweight ; it is a perfect choice for exchanging data over constraint networks such as wireless mobile networks.
* Web-readiness: as a Web fellow, JSON is natively understood by any web browser complying to the W3C standards ; it is today the best candidate for an ubiquitous format (please notice that the hAPI model could easily be translated to any other structured semantic such as XML).

**_Android HAPI objects are internally JSON Objects. Getters and setters are convenient functions to set and access JSON object values._**

Hubiquitus defines a set of basic data structure used to express elementary piece of typed data:

* Messages
  - hMessage
* hMessage payloads
  - hCommand
  - hResult
  - hMeasure
  - hAlert
  - hAck
  - hConvState
  - hTweet
  - hHTTPData
* Metadata
  - hHeader
  - hLocation
* Filter
  - hCondition




##Messages

###HMessage

HStructure for hubiquitus messages.

```java
public class HMessage extends JSONObject {
        public String getMsgid()
	public void setMsgid(String msgid);

	public String getActor();
	public void setActor(String actor) throws MissingAttrException;

	public String getConvid();
	public void setConvid(String convid);

        public String getRef();
        public void setRef(String ref);

	public String getType();
	public void setType(String type);

	public HMessagePriority getPriority();
	public void setPriority(HMessagePriority priority);
	
	public DateTime getRelevance();
	public void setRelevance(DateTime relevance);
	
	public Boolean getPersistent();
	public void setPersistent(Boolean _persistent);

	public HLocation getLocation();
	public void setLocation(HLocation location);
	
	public String getAuthor();
	public void setAuthor(String author);

	public String getPublisher();
	public void setPublisher(String publisher);
	
	public DateTime getPublished();
	public void setPublished(DateTime published);

	public JSONObject getHeaders();
	public void setHeaders(JSONObject headers);

        public Object getPayload();
        public JSONObject getPayloadAsJSONObject();
        public JSONArray getPayloadAsJSONArray();
        public String getPayloadAsString();
        public Boolean getPayloadAsBoolean();
        public Integer getPayloadAsInt();
        public Double getPayloadAsDouble();
        public HAlert getPayloadAsHAlert();
        public HAck getPayloadAsAck();
        public HMeasure getPayloadAsHMeasure();
        public HConvState getPayloadAsHConvState();
        public HResult getPayloadAsHResult();
        public HCommand getPayloadAsHCommand();

        public void setPayload(Object payload);
        public void setPayload(JSONObject payload);
        public void setPayload(JSONArray payload);
        public void setPayload(String payload);
        public void setPayload(Boolean payload);
        public void setPayload(Integer payload);
        public void setPayload(Double payload);
        public void setPayload(HAlert payload);
        public void setPayload(HAck payload);
        public void setPayload(HMeasure payload);
        public void setPayload(HConvState payload);
        public void setPayload(HResult payload);
        public void setPayload(HCommand payload);
        
        public DateTime getSent();
        public void setSent(DateTime sent);
        
        public int getTimeout();
        public void setTimeout(Integer timeout);
}
```
Where : 
* msgid : Message id. Mandatory. Can be filled by the hApi. 
* actor : The unique ID of the channel through which the message is published. Mandatory.
* convid : Conversation if to which the message belongs. Mandatory. Filled by the hNode if empty.
* ref : Refers to another hMessage msgid. Provide a mechanism to do correlation between messages.
* type : Type of the message payload.
* priority : Message priority. If UNDEFINED, priority lower to 0. Mandatory. Can be filled by hApi. 
* relevance : Define is the message is persistent. If true, the message is not persistent. True by defaut.
* location : The geographical location to which the message refer. See HLocation below.
* author : Author's id of this message.
* publisher : Publisher id. Mandatory. Can be filled by hApi.
* published : The date and time at which the message has been published. Mandatory. Can be filled by hApi.
* headers : A Headers object attached to this message. It is a key-value pair map. It is possible to not specify any header.
* payload : The content of the message.
* timeout : The timeout to get an answer to the hMessage. The hAPI will manage the value and response messages will be sent through callback set on send command.
* sent : Set by the hAPI when sending the message. As the published attribute can contain the original creation date of the information know by the author, this attribute contains the creation datetime of the hMessage



## hMessage Payload

### HCommand

The purpose of a hCommand is to execute an operation on a specific component, a hubot or a hServer.

```java
public class HCommand extends JSONObject {
	public String getCmd();
	public void setCmd(String cmd) throws MissingAttrException;

	public JSONObject getParams() ;
	public void setParams(JSONObject params);
}
```

Where : 
* cmd : Name of the command which will be executed. Mandatory.
* params : Parameters of the command.

### HResult

The purpose of a HResult is to get an information on a commmand execution result.
This is returned once a command has been executed by the server.

```java
public class HResult extends JSONObject {
	
	public ResultStatus getStatus();
	public void setStatus(ResultStatus status) throws MissingAttrException;

        public Object getResult();
	public JSONObject getResultAsJSONObject();
        public JSONArray getResultAsJSONArray();
        public String getResultAsString();
        public Boolean getResultAsBoolean();
        public Integer getResultAsInt();
        public Double getResultAsDouble();

        public void setResult(Object result);
        public void setResult(JSONObject result);
        public void setResult(JSONArray result);
        public void setResult(String result);
        public void setResult(Boolean result);
        public void setResult(Integer result);
        public void setResult(Double result);
}
```
Where : 
* status : result status. See Codes and enums. Mandatory.
* result : Command result object. Command dependent.

###HStatus

This structure describe the connection status

```java
public class HStatus extends JSONObject{
        public ConnectionStatus getStatus();
	public void setStatus(ConnectionStatus status) throws MissingAttrException;
	
	public ConnectionError getErrorCode();
	public void setErrorCode(ConnectionError errorCode) throws MissingAttrException;

	public String getErrorMsg();
	public void setErrorMsg(String errorMsg);
}
```
Where : 
* status : Current connection status. See ConnectionStatus in codes and enums. Mandatory
* errorCode : 0 if no error. For more informations, see ConnectionError in codes and enums. Mandatory
* errorMsg : error message. Null if no error or no description.


###HMeasure

HStructure for measure payload.

```java
public class HMeasure extends JSONObject{
	public String getUnit();
	public void setUnit(String unit) throws MissingAttrException;
	
	public String getValue();
	public void setValue(String value) throws MissingAttrException;
}
```
Where: 
* unit : Unit in which the measure is expressed, should be in lowercase. Mandatory. (ie : "celsius" , "fahrenheit")
* value : Specify the value of the measure. Mandatory. (ie : "31.2")


###HAck

hAPI allows to attach acknowledgements to each message
Acknowledgements are used to identify the participants that have received or not received, read or not read a message.
_When a hMessage contains a such kind of payload, the convid must be provided with the same value has the acknowledged hMessage._

```java
public class HAck extends JSONObject{
      
	public HAckValue getAck();
	public void setAck(HAckValue ack) throws MissingAttrException;
}
```
Where : 
* ack : The status of the acknowledgement. See AckValue in codes and enums. Mandatory.

###HAlert

HStructure for alert payload.
_For a such kind of payload, the hMessage’s priority should be greater or equals to 2 and may be greater than the default channel’s priority._

```java
public class HAlert extends JSONObject{
	public String getAlert();
	public void setAlert(String alert) throws MissingAttrException;
}
```

Here is an enumeration of these properties : 
* alert : Description of the alert. Mandatory.


###HConvState

This kind of payload is used to describe the status of a thread of correlated messages identified by its convid.
Multiple hConvStates with the same convid can be published into a channel, specifying the evolution of the state of the thread during time.

```java
public class HConvState extends JSONObject {
	public String getStatus();
	public void setStatus(String status) throws MissingAttrException;
}
```
where :
* status : The status of the thread. Mandatory. (Can be read and set)

###Metadata

####HLocation

HStructure for hubiquitus location.


```java
public class HLocation extends JSONObject{
        public HGeo getPos();
	public void setPos(HGeo pos);
        
        public String getNum();
	public void setNum(String num);

        public String getWayType();
	public void setWayType(String wayType);

        public String getWay();
	public void setWay(String way);

        public String getFloor();
	public void setFloor(String floor);

        public String getBuilding();
	public void setBuilding(String building);

	public String getZip();
	public void setZip(String zip);
	
	public String getAddr();
	public void setAddr(String addr);
	
	public String getCity();
	public void setCity(String city);

	public String getCountry();
	public void setCountry(String country);

        public String getCountryCode();
	public void setCountryCode(String countryCode);
}
```
Where : 
* pos : Specifies the exact longitude and latitude of the location.
* num : Number of the address
* waytype : Type of the way
* way : Name of the street/way
* floor : Floor number of the address
* building : Building’s identifier of the address
* zip : Zip code of the location
* addr : Address of the location
* city : City of the location
* country : Country of the location
* countryCode : Country code 
 

###HGeo

HStructure for hubiquitus geo.
```java
public class HLocation extends JSONObject{
       public Double getLng();
       public void setLng(double lng);

       public Double getLat();
       public void setLat(double lat);
}
```
Where : 
* lng : The longitude of the location. Mandatory.
* lat : The latitude of the location. Mandatory.

##Filter
###hCondition
```java
public class HCondition extends JSONObject {
	public HValue getValue(OperandNames operand);
	public void setValue(OperandNames operand, HValue value);

	public HArrayOfValue getArrayOfValue(OperandNames operand);
	public void setValueArray(OperandNames operand, HArrayOfValue values);
	
	public HCondition getCondition(OperandNames operand);
	public void setCondition(OperandNames operand, HCondition condition);

	public JSONArray getConditionArray(OperandNames operand);
	public void setConditionArray(OperandNames operand, JSONArray conditionArray);

	public Boolean getRelevant();
	public void setRelevant(Boolean relevant);

	public HPos getGeo();
	public void setGeo(HPos geo);
}
```

Samples : 
   { } /* no condition equals to always true */
   { “eq” : { “author” :  “u1@myDomain.com” }}
   { “ne” : { “author” :  “u1@myDomain.com” }}
   { “gt” : { “priority” : 1 }}
   { “gte” : { “priority” : 1 }}
   { “lt” : { “priority” : 3 }}
   { “lte” : { “priority” : 3 }}

   { “in” : { “author” : [ “u1@myDomain.com” , “u2@myDomain.com” ] } }
   { “nin” : { “author” : [ “u1@myDomain.com” , “u2@myDomain.com” ] } }

   { “and” : [
       { “in” : { “author” : [ “u1@myDomain.com” , “u2@myDomain.com” ] } },
       { “lte” : { “priority” : 3 }}
   ] }

   { “or” : [
       { “in” : { “author” : [ “u1@myDomain.com” , “u2@myDomain.com” ] } },
       { “lte” : { “priority” : 3 }}
   ] }

   { “not “ :
      { “and” : [
          { “in” : { “author” : [ “u1@myDomain.com” , “u2@myDomain.com” ] } },
          { “lte” : { “priority” : 3 }}
          ]
       }
   }

   { relevant : true }

   { geo : {
       lat : 40.2,
       lng : 2.4,
       radius : 10000
      }
   }

####OperandNames
```java
public enum OperandNames {
	EQ("eq"),
	NE("ne"),
	GT("gt"),
	GTE("gte"),
	LT("lt"),
	LTE("lte"),
	IN("in"),
	NIN("nin"),
	AND("and"),
	OR("or"),
	NOR("nor"),
	NOT("not")
}
```

Where:
 * eq : equals
 * ne : not equals
 * gt : greater than
 * gte : greater than or equals
 * lt : lower than
 * lte : lower than or equals
 * in : the attribute must be equals to one of the values
 * nin : the attribute must be different with all the values
 * and : all the conditions must be true
 * or : one of the conditions must be true
 * nor : all the conditions must be false
 * not : the condition must be false

Operand relevant : used to filter relevant or non relevant messages

Operand geo : used to filter messages with a latitude, longitude and radius definition (see hPos for details)


Available name of operand : 
 * hValue : eq, ne, gt, gte, lt, lte
 * hArrayOfValue : in, nin
 * Array of hCondition : and, or, nor
 * hCondition : not

####hValue
```java
public class HValue extends JSONObject {
	public String getName();
	public void setName(String name);

	public Object getValue();
	public void setValue(Object value);
}
```

Where :

 * name : The name of an attribute.
 * value : The value of the attribute to compare with.

Sample : 

   { “author” :  “u1@myDomain.com” } 

####hArrayOfValue
```java
public class HArrayOfValue extends JSONObject {
	public String getName();
	public void setName(String name);

	public JSONArray getValues();
	public void setValues(JSONArray values);
}
```

Where : 

 * name : The name of an attribute.
 * values : The values of the attribute to compare with.

Sample : 

   { “author” : [ “u1@myDomain.com” , “u2@myDomain.com” ] } 


####hPos
```java
public class HPos extends JSONObject {
	public Double getLat();
	public void setLat(Double v);

	public Double getLng();
	public void setLng(Double v);

	public Double getRadius();
	public void setRadius(Double v);
}
```

Where : 

 * lat : The latitude of the searched location.
 * lng : The longitude of the searched location.
 * radius : the precision of the searched location (meter)

Sample : 

{ lat: 48, lng: 2,  radius: 10000  }
 