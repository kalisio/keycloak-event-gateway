_[Back to the home page](../README.md)
— Previous Page: [1. Installation and usage](./Usage.md)
— Next Page: [3. How to build](./Build.md)_

--

# 2. Configuration

## Basic configuration

We are using Keycloak 22.0.5.

Through the Keycloak GUI: 

1. Select the realm you want to declare the
   listener for.
2. In the Events → Event listeners tab, declare
   the `kalisio-event-gateway` Event listener.
3. Save.
4. In the same realm, create a user
   called `kalisio-event-gateway`.
5. Go to the Attributes tab.
6. Add the following attributes:

| Name | Value (examples) |
| :-- | :-- |
| `accessToken` | `abcdef1234` |
| `keycloakEventHttpListenerUrl` | `https://kapp.mydomain/api/keycloak-events` |

Here are some screenshots of theses configuration steps:

| 1. Select the realm | 2. Declare the listener | 3. Save | 4. kalisio-event-gateway user | 5. Attributes tab | 6. Attribute values |
| :-- | :-- | :-- | :-- | :-- | :-- |
| [![Go to the realm settings](https://gitlab.com/avcompris/kalisio/feathers-keycloak-listener-screenshots/-/raw/c607820462d12cb82d1c5206778b7aa6c54a8b42/screenshots/keycloak_setUp/00000008.png)](https://gitlab.com/avcompris/kalisio/feathers-keycloak-listener-screenshots/-/blob/c607820462d12cb82d1c5206778b7aa6c54a8b42/screenshots/keycloak_setUp/00000008.md) | [![Select the listener](https://gitlab.com/avcompris/kalisio/feathers-keycloak-listener-screenshots/-/raw/c607820462d12cb82d1c5206778b7aa6c54a8b42/screenshots/keycloak_setUp/00000011.png)](https://gitlab.com/avcompris/kalisio/feathers-keycloak-listener-screenshots/-/blob/c607820462d12cb82d1c5206778b7aa6c54a8b42/screenshots/keycloak_setUp/00000011.md) |  [![Save the config](https://gitlab.com/avcompris/kalisio/feathers-keycloak-listener-screenshots/-/raw/c607820462d12cb82d1c5206778b7aa6c54a8b42/screenshots/keycloak_setUp/00000013.png)](https://gitlab.com/avcompris/kalisio/feathers-keycloak-listener-screenshots/-/blob/c607820462d12cb82d1c5206778b7aa6c54a8b42/screenshots/keycloak_setUp/00000013.md) | [![Create user](https://gitlab.com/avcompris/kalisio/feathers-keycloak-listener-screenshots/-/raw/c607820462d12cb82d1c5206778b7aa6c54a8b42/screenshots/keycloak_setUp/00000016.png)](https://gitlab.com/avcompris/kalisio/feathers-keycloak-listener-screenshots/-/blob/c607820462d12cb82d1c5206778b7aa6c54a8b42/screenshots/keycloak_setUp/00000016.md) | [![Attributes tab](https://gitlab.com/avcompris/kalisio/feathers-keycloak-listener-screenshots/-/raw/c607820462d12cb82d1c5206778b7aa6c54a8b42/screenshots/keycloak_setUp/00000018.png)](https://gitlab.com/avcompris/kalisio/feathers-keycloak-listener-screenshots/-/blob/c607820462d12cb82d1c5206778b7aa6c54a8b42/screenshots/keycloak_setUp/00000018.md) | [![Attribute values](https://gitlab.com/avcompris/kalisio/feathers-keycloak-listener-screenshots/-/raw/c607820462d12cb82d1c5206778b7aa6c54a8b42/screenshots/keycloak_setUp/00000020.png)](https://gitlab.com/avcompris/kalisio/feathers-keycloak-listener-screenshots/-/blob/c607820462d12cb82d1c5206778b7aa6c54a8b42/screenshots/keycloak_setUp/00000020.md)  |

Keycloak 22.0.5 limits attribute values to 255 characters.
If your accessToken is too long, you can split it in parts, like this:

| Name | Value (examples) |
| :-- | :-- |
| `accessToken.1` | `abcde` |
| `accessToken.2` | `f1234` |
| `accessToken.3` | `xyzt` |
| `keycloakEventHttpListenerUrl` | `https://kapp.mydomain/api/keycloak-events` |

This will resolve in
`accessToken=abcdef1234xyzt` in the internal
representation.

If the configuration is incorrect,
warning messages are logged to
the Keycloak console each time an event is emitted.



## Advanced configuration

You can declare multiple HTTP
endpoints. Each one will be notified
and receive the JSON payload emitted for
each Keycloak event.

This is done by adding prefixes to the attribute
names. For example:

| Name | Value (examples) |
| :-- | :-- |
| `myapp1.accessToken` | `abcd1234` |
| `myapp1.keycloakEventHttpListenerUrl` | `https://kapp.mydomain1/api/keycloak-events` |
| `thebigapp4.accessToken` | `71239def` |
| `thebigapp4.keycloakEventHttpListenerUrl` | `https://kapp.mybigapp4/api/keycloak-events` |


## Asynchronous calls

This service provider sends HTTP requests asynchronously.

Errors during calls
are simply added to the logs, 
and do not interfere
with the Keycloak runtime.

If the further treatments you implement
require to handle events
in the order they have been emitted by Keycloak,
you should reconcile the events through some
mechanism, e.g. their timestamps.


## HTTPS

If you need to use a custom certificate
to reach HTTPS endpoints,
use the `--spi-truststore-file-*` Keycloak options
at launch, and possibly a Java keystore.



--


_[Back to the home page](../README.md)
— Previous Page: [1. Installation and usage](./Usage.md)
— Next Page: [3. How to build](./Build.md)_
