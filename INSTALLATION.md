# Required elements
----------------------

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
--------------------------

XMPP server
-----------
You have to define one host (eg. myHost), one domain (eg. myDomain) and one user admin for your server.
Then, create at least two users that will be able to communicate with it :

* One for the hserver - Define the name and the password (eg. nameOfThisUser@mydomain and any password)
* One for each bot you want to run - Define the name and the password (eg. nameOfThisUser@mydomain and any password)

After that, you have to configure the hserver component into the xmpp server.
For the main node, configure the opened ports with :

* The port number
* The protocle type
* The name of the module
* And options like this : 
[{access, all}, {shaper_rule, fast},
 {host,
  "hserver.socialtv-livebattle.fr",
  [{password,
    "secret du hserver hubiquitus"}]}]

