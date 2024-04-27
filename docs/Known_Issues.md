_[Back to the home page](../README.md)
— Previous page: [7. Troubleshooting](./Troubleshooting.md)
— Next page: [9. FAQ](./FAQ.md)_

--

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
could be to use a 
a messaging
queue rather than an HTTP/HTTPS endpoint.

As for now,
one can declare a `keycloakEventQueue` 
attribute in the configuration
(see [2. Configuration](Configuration.md)).

The underlying service is not
implemented though.




--

_[Back to the home page](../README.md)
— Previous page: [7. Troubleshooting](./Troubleshooting.md)
— Next page: [9. FAQ](./FAQ.md)_

