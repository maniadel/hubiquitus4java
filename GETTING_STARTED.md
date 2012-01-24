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
      /**...**/
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
      /**...**/
      //Create node method
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
	    	wordPublishEntry.setType("HelloType");
	        wordPublishEntry.setPayload(mypayload);
	        wordPublishEntry.setPersistence(true);
	        wordPublishEntry.setDbName("MyDB");
	        
	        return wordPublishEntry;
	}
    }
```