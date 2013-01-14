Hubots are Hubiquitus actors who can run completely isolated from each other. They can communicate with other actors by message or command and with the network in the same way.

All the hubots share common characteristics :

* A hubot is an entity of a hubiquitus domain to which it connects through a hServer.
* A hubot has a inbox, a FIFO message queue populated with messages/commands to be processed by the hubot. We use [camel](http://camel.apache.org/) for this purpose.
* A hubot has a set of message outboxes, FIFO message queues populated with the outcoming messages produced by the hubot. All adapters had their own outbox.
* Any hubot MUST implement the HubotAdapter.

The hubotSDK provide structure for create hubot quickly : 
* Actor : the class which possess the main and the business code. This class must be extent ,and methods inProcessCommand and inProcessMessage must be overide by the BusinessActor.
* Adapters : the class is used to communicate with others actors or the network. This class can be extent and, if extended, must override methods setProperties, send Command and Message.  

### The HubotAdapter is automaticaly added in all bot.

## Example HelloBot

```java

public class HelloHubot extends Hubot{

public static void main(String[] args) throws Exception{
       HelloHubot hubot = new HelloHubot();
       hubot.start();
}
	
@Override
//Used to process incoming message. This method contain the business code for message 
protected void inProcessMessage(HMessage messageIncoming) {
// do something with the incoming message
}
```

# Configuring your hubot.

Each hubot need a configuration file to work. This file is used to set all the properties of the adapters and initialized them.

A simple example of config file without adapter. Sure, the HubotAdapter is provided.

```
{
"type" : "com.mycompany.WordCount",
"actor" : "mybot@myDomain.com",
"pwd" : "MotDePasse",
"hserver" : "http://localhost:8080"
}
```
The HubotAdapter will connected the actor with the parameters actor, pwd and hserver.

Another sample example of config file 

```
{
    "type" : "com.mycompany.WordCount",
    "actor" : "mybot@myDomain.com",
    "pwd" : "MotDePasse",
    "hserver":"http://localhost:8080",
    "filter" : {"in" : {"publisher" : ["u1@myDomain.com", "u2@myDomain.com"]}},
    "adapters" : [ 
                 {"actor" : "#myChan1@myDomain.com"},
                 {"actor" : "#myChan2@myDomain.com"}
    ]
}
```

Note: 
In this sample, the hubot will receive some hMessage only if the publisher is u1 or u2 and if they publish a hMessage directly to “myBot” or through the channel “#myChan” or “#myChan2”. 

You can modifie your adapter properties. For it, you should use this method :

```java
protected void updateAdapterProperties(String actor, JSONObject params);
```