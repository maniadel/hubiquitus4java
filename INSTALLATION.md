# Required elements
-------------------

Mandatory
---------

Maven - General repository where all needed and used .jar are stored.

* Link : http://maven.apache.org/
* Version : The last released.

Github - Public repository where you can find all open source files.

* Link : https://github.com/hubiquitus

Ejabberd 2 (or any other XMPP server, like openfire)

* Link : http://www.ejabberd.im/
* Version : The last released.

MangoDB - Your database

* Link : http://www.mongodb.org
* Version : Depending on your computer configuration.

Not mandatory
-------------

Eclipse

* Link : http://www.eclipse.org/downloads/
* IDE : Eclipse IDE for Java Developers or Eclipse IDE for Java EE Developers
* Version : Depending on your computer configuration.

Github console - Helps you to execute easily several actions like commit, clean…

* Link : http://help.github.com/win-set-up-git/



# Required configuration
------------------------

XMPP server (We are using ejabberd)
-----------------------------------
You have to define one host (eg. myHost), one domain (eg. myDomain) and one user admin for your server.
Then, create at least two users that will be able to communicate with it :

* One for the hserver - Define the name and the password (eg. nameOfThisServerUser@mydomain and any password)
* One for each bot you want to run - Define the name and the password (eg. nameOfThisBotUser@mydomain and any password)

After that, you have to configure the hserver component into the xmpp server.

For the main node, configure the opened port with :

* The port number
* The protocle type
* The name of the module
* And options like this : 
`[{access, all}, {shaper_rule, fast},
 {host,
  "hserver.mydomain",
  [{password,
    "TheOneYouWant"}]}]`

MangoDB
-------
You can find help to install the database here : http://www.mongodb.org/display/DOCS/Home


Hubiquitus Server
-----------------
In your retrieved package, you have to change two specifics files :

* database-conf-applicationContext

In this file, you have to replace the value "localhost" here `<entry key="mongoDbHost" value="localhost"/>` 
and "27017" here `<entry key="mongoDbPort" value="27017"/>` by those of your database.

* hserver-conf-applicationContext

In this file, you have to change :

1. The value of the "xmppServerHost" bean by the name of the computer where ejabberd is installed.

2. The value of the "xmppServerPort" by the right one.

3. The value of the "xmppusername" entry by the name you choose for the hserver user in ejabberd

4. The value of the "xmppuserpassword" entry by the password you choose for the hserver user in ejabberd

5. The value of the "secretKey" entry by the password you choose when you were configure the opened port


Now you are ready to run your first bot ! 

Please read the following file : GETTING_STARTED.md 
