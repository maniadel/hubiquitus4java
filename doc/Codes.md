### ConnectionStatus

Currently supported connection status are:

```java
public enum ConnectionStatus {
	CONNECTING (1),
	CONNECTED (2),
        DISCONNECTING (5),
	DISCONNECTED (6),
}
```

* (1) CONNECTION : On connection progress
* (2) CONNECTED : Once connected
* (5) DISCONNECTING : On disconnect in progress
* (6) DISCONNECTED : Once disconnected

_A disconnect can't occur while a CONNECTION is in progress, you should wait first for connection_

### ConnectionError

Currently supported connection error are:

```java
public enum ConnectionError {
	NO_ERROR(0),
	JID_MALFORMAT(1),
	CONN_TIMEOUT(2),
	AUTH_FAILED(3),
	ALREADY_CONNECTED(5),
	TECH_ERROR(6),
	NOT_CONNECTED(7);
        CONN_PROGRESS(8);
}
```

* (1) ID_MALFORMAT : Publisher name should comply with jid format (ie : me@my_domain or me@my_domain/resource)
* (2) CONN_TIMEOUT : Happens connection take too long to connect
* (3) AUTH_FAILED : Authentification failure
* (5) ALREADY_CONNECTED : Happens if trying to reconnect while already connected
* (6) TECH_ERROR : On unknown technical error. See errorMsg low level error for more informations
* (7) NOT_CONNECTED : Trying to do something while not connected
* (8) CONN_PROGRESS : Trying to reconnect or do something while connection in progress


### ResultStatus

Currently supported result status are:

```java
public enum ResultStatus {
	NO_ERROR (0),
	TECH_ERROR (1),
	NOT_CONNECTED (3),
	NOT_AUTHORIZED (5),
	MISSING_ATTR (6),
	INVALID_ATTR (7),
	NOT_AVAILABLE (9),
	EXEC_TIMEOUT (10);
}
```

* (0) OK : ok
* (1) TECH_ERROR : technical error (check result attributes for details)
* (3) NOT_CONNECTED : the current session is not connected 
* (5) NOT_AUTHORIZED : the publisher did not have the right to do this action
* (6) MISSING_ATTR : a mandatory attribute was not provided (the name of the mandatory attribute will be set in the result) 
* (7) INVALID_ATTR : a mandatory attribute was invalide
* (9) NOT_AVAILABLE : the cmd is not available on the entity / necessary attribute not found
* (10) EXEC_TIMEOUT : the cmd exceeded the timeout

###HMessagePrioity

Currently supported message priority are : 

```java
public enum HMessagePriority {
	TRACE(0),
	INFO(1),
	WARNING(2),
	ALERT(3),
	CRITICAL(4),
	PANIC(5);
}
```

* (0) TRACE : the lowest priority
* (1) INFO : low priority
* (2) WARNING : normal priority (used by defaut)
* (3) ALERT : higher priority than normal
* (4) CRITICAL : high priority
* (5) PANIC : the highest priority

###AckValue

Currently supported ack value are :

```java
public enum HAckValue {
	RECV("recv"),
	READ("read");
}
```

* ("recv") RECV : The message has been received by the participant.
* ("read") READ : The message has been read by the participant.
