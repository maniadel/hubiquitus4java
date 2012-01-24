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