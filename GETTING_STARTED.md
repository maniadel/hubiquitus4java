MyFirstBot - How to
-------------------

In your new java project "myfirstbot", create your first class : `myfirstbot.java`

In this class, you should instantiate the method: `startDataRetriever()`

In this method you have to ask the server to create a node where you will publish your retrieved data :

``` java
  public class MyFirstBot extends Bot {
    //variable declaration
    /** Methods **/
    public void startDataRetriever() throws BotException {
      /.../
      this.createNodeFromHServer(getNodeName(), getTitle(), getAllowedRosterGroups());
    }
  }
```

Then, depending on the configuration you want to use, you have different specific methods.

To retrieve data from an external source and publish it to a node, use this following code :

``` java
  public class MyFirstBot extends Bot {
    //variable declaration
    /** Methods **/
    public void startDataRetriever() throws BotException {
      /.../
      MyRetriever myRetriever = new MyRetriever(this.hubotConfig.getInterval(), this);
      myRetriever.start();
      try {
        myRetriever.join(); 
      } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
```

`MyRetiever.java` is a class where you can retrieve data from any sources, each interval, and do the publication 
on the created node

``` java
  public class MyRetriever extends BotDataFetcherBase {
  //variable declaration
  /**Run methods**/
  public void retrieve(){
		bot.publishToNode(MyFirstBotUtilsImpl.wordToMessagePublishEntry(data), bot.getNodeName());		
  }
```

`MyFirstBotUtilsImpl.java` is a class used in order to transform the retrieved data into the right type to be understood 
by the node. This class can also be used to manipulate, overload a message before being send.

The right type for a message is "MessagePublishEntry". 
There is also the "ComplexPublishEntry" type which is a list of MessagePublishEntry.

Before publish something you have to fill at least those properties :

``` java
   public class MyFirstBotUtilsImpl {
   //variable declaration
	public static MessagePublishEntry  wordToMessagePublishEntry(String word) {
	    MessagePublishEntry wordPublishEntry = new MessagePublishEntry();
	    mypayload = new MyPublishEntry(word);
	    	
	    //List of properties
	    wordPublishEntry.generateMsgid();
	    wordPublishEntry.setAuthor("Moi");
	    wordPublishEntry.setPublished(new Date());
	    wordPublishEntry.setPublisher("MyBot");
	    wordPublishEntry.setType("HelloType"); //This type will allow you to re-find the published message.
	        
	    wordPublishEntry.setPayload(mypayload); //Payload is the attribute that contain the message description.

	    wordPublishEntry.setPersistence(true); //To save the published data
	    wordPublishEntry.setDbName("MyDB");
	        
	    return wordPublishEntry;
	}
    }
```

NB: All messages published (publishToNode) have to be converted in Json format.

Now, if you add correctly all required ressources describe below, your bot is able to run !

___
Or, another configuration allow you to subscribe to a node and a listener will retrieve data from it.
To do it, you have to a specific line in your MyfirstBot class :

``` java
  public class MyFirstBot extends Bot {
    //variable declaration
    /** Methods **/
    public void startDataRetriever() throws BotException {
      /	... /
      this.subscribeNode(node, new MyItemEventListener());
    }
  }
```

This method allows you to subscribe to a specific node and instantiate a listener to receive any data passing through it.
If you want to publish the data retrieved by the listener, you have to ask it to the server as seen above.

`MyItemEventListener.java` class will have only one specific method `handlePublishedItems(ItemPublishEvent<Item> items)`
This method allows you to configure the behavior of your listener when it receives something.

You can publish the retrieved data with this line:

``` java
    public class MyItemEventListener implements ItemEventListener<Item> {
	//variable declaration
	/**Methods**/
	public void handlePublishedItems(ItemPublishEvent<Item> items) {
	  /.../
	  //This line
	  bot.getXmppEntryPublisher().publishToNode(MyFirstBotUtilsImpl.subscribedDataToMessagePublishEntry(entry), bot.getNodeName());
	  /.../
	}
     }
```

Now, if you add correctly all required ressources describe below, your bot is able to run !

___
Or, another configuration send request to the server and retrieve the answer composed by requested elements
To do it, you have to a specific bloc in your MyfirstBot class :

``` java
  public class MyFirstBot extends Bot {
    //variable declaration
    /** Methods **/
    public void startDataRetriever() throws BotException {
      /	... /
      	DataRequestEntry dataRequestEntry = new DataRequestEntry();
      	dataRequestEntry.setRequestType(DataRequestEntry.TYPE_FIND);
    	dataRequestEntry.setDbName("MyDB");
    	dataRequestEntry.setCollectionName("HelloType");
	    	
    	List<ParamRequest> params = new ArrayList<ParamRequest>();
   	ParamRequest param = new ParamRequest();
   	KeyRequest kr1 = new KeyRequest(MessagePublishEntry.AUTHOR, DbOperator.EQUAL, "Moi");
   	param.addKeyRequest(kr1);
   	params.add(param);
      	
    }
  }
```

NB: The database has collections defined by the name of the message type 

The following code extracts data from the result : 

``` java
  public class MyFirstBot extends Bot {
    //variable declaration
    /** Methods **/
    public void startDataRetriever() throws BotException {
      	/ ... /
	for (int i=0; i< results.size(); i++) {
	   DataResultEntry r = results.get(i);
	   String myBrutData = r.getPayload();
	}	
    }
  }
```

To start your bot, you have to create a main class with this method: 

``` java
  public class BotSpringMain extends HubotMain {	

	public static void main(String[] args) {
		start(args);
	}
  }	
```
