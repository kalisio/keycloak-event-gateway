_[Back to the home page](../README.md)
— Previous page: [7. Troubleshooting](./Troubleshooting.md)
— Next page: [9. FAQ](./FAQ.md)_

---

# 8. Known Issues


## Threads usage

Use of threads for
asynchronous requests
could possibly lead to errors
not yet encountered in our tests.

To prevent those errors, we could think of a
a `asynchronous=false` option in the configuration.

This is not implemented.


## No messaging queue

An alternative choice of configuration
could be to use
a messaging
queue rather than an HTTP/HTTPS endpoint.

As for now,
one can declare a `keycloakEventQueue` 
attribute in the configuration
(see [2. Configuration](Configuration.md)).

The underlying service is not
implemented, though.


## `CloseableHttpClient` connections were not released [SOLVED]

How to reproduce:

1. Configure an valid `keycloakEventHttpListenerUrl`
2. Call the Keycloak admin API 70 times to create as many users
4. Call the Keycloak admi API to create an additional user

Result: The HTTP endpoint is not sollicited.


Example of error trace:

````
Exception in thread "Thread-1620" java.lang.RuntimeException: org.apache.http.impl.execchain.RequestAbortedException: Request execution failed
	at com.kalisio.keycloak.CustomHttpConnector.send(CustomHttpConnector.java:69)
	at com.kalisio.keycloak.CustomEventListenerProvider$1.run(CustomEventListenerProvider.java:268)
Caused by: org.apache.http.impl.execchain.RequestAbortedException: Request execution failed
	at org.apache.http.impl.execchain.MainClientExec.execute(MainClientExec.java:199)
	at org.apache.http.impl.execchain.ProtocolExec.execute(ProtocolExec.java:186)
	at org.apache.http.impl.execchain.RetryExec.execute(RetryExec.java:89)
	at org.apache.http.impl.execchain.RedirectExec.execute(RedirectExec.java:110)
	at org.apache.http.impl.client.InternalHttpClient.doExecute(InternalHttpClient.java:185)
	at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:83)
	at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:108)
	at com.kalisio.keycloak.CustomHttpConnector.send(CustomHttpConnector.java:58)
	... 1 more
Caused by: java.util.concurrent.CancellationException: Operation aborted
	at org.apache.http.pool.AbstractConnPool.operationAborted(AbstractConnPool.java:182)
	at org.apache.http.pool.AbstractConnPool.getPoolEntryBlocking(AbstractConnPool.java:395)
	at org.apache.http.pool.AbstractConnPool.access$300(AbstractConnPool.java:70)
	at org.apache.http.pool.AbstractConnPool$2.get(AbstractConnPool.java:253)
	at org.apache.http.pool.AbstractConnPool$2.get(AbstractConnPool.java:198)
	at org.apache.http.impl.conn.PoolingHttpClientConnectionManager.leaseConnection(PoolingHttpClientConnectionManager.java:306)
	at org.apache.http.impl.conn.PoolingHttpClientConnectionManager$1.get(PoolingHttpClientConnectionManager.java:282)
	at org.apache.http.impl.execchain.MainClientExec.execute(MainClientExec.java:190)
	... 8 more
````

> Note it is not a matter of
`java.net.ConnectException` (when 
> an invalid
`keycloakEventHttpListenerUrl` is used).


How it was solved: Use `SimpleHttp` rather than
`HttpClientProvider`.




---

_[Back to the home page](../README.md)
— Previous page: [7. Troubleshooting](./Troubleshooting.md)
— Next page: [9. FAQ](./FAQ.md)_

