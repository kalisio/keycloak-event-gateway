
_[Back to the home page](../README.md)
— Previous Page: [6. Examples of JSON payloads](./Examples.md)
— Next Page: [8. Known Issues](./Known_Issues.md)_

--

# 7. Troubleshooting

## Warnings found in the Keycloak logs

If you have just declared the listener in 
the Event listeners section of the Keycloak GUI,
you will start seeing some warnings in the Keycloak
logs. That is because you have not configured the
listener yet.

e.g.:

* No `kalisio-event-gateway` user.
* No `accessToken` and/or `keycloakEventHttpListenerUrl`
  attributes declared.

If a HTTP endpoint does not respond, the listener
just logs the error.

## The listener cannot start


Make sure you built the listener with a
version of Java compatible with your Keycloak runtime.



--


_[Back to the home page](../README.md)
— Previous Page: [6. Examples of JSON payloads](./Examples.md)
— Next Page: [8. Known Issues](./Known_Issues.md)_
