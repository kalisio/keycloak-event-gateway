# keycloak-event-gateway

> A Keycloak SPI that publishes events to a HTTP service.

## Build the SPI

Prerequisites: Java, Maven


```shell
$ mvn install
```

That builds a JAR in the `target` directory,
and you have to copy this JAR into the
`/opt/keycloak/providers/` directory
of your Keycloak installation so the event listener
can be seen by Keycloak at boot time.

![Diagram](xxx)

## Deploy locally

Prerequisites: docker-compose

### Run the stack

```shell
$ docker compose up -d
```

![Diagram](xxx)

### Stop the stack

```shell
$ docker compose down
```


## Run the tests

Prerequisites: Node, Selenium

```shell
$ cd test
$ docker-compose up -d
$ npm install
$ npx mocha integration_test.js
```




## Configure the SPI

### Basic configuration

Through the Keycloak GUI: 

1. Select the realm you want to declare the
   listener for.
2. In the Events → Event listeners tab, declare
   the `kalisio-event-gateway` Event listener.
3. Save.
4. In the same realm, create a user
   called `kalisio-event-gateway`.
5. In Keycloak 22.0.5, go to the Attributes tab.
6. Add the following attributes:

| Name | Value (examples) |
| :-- | :-- |
| `accessToken` | `abcdef1234` |
| `keycloakEventHttpListenerUrl` | `https://kapp.mydomain/api/keycloak-events` |

See screenshots of those steps here: [xxx](xxx)


### Advanced configuration

You can declare multiple HTTP
endpoints that will listen
to the JSON payload emitted by this listener for
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

The SPI sends HTTP requests asynchronously.

Errors during calls
are just displayed in the logs.

If the further treatments you implement
require to handle events
in the order they have been emitted by Keycloak,
you should reconcile the events through some
mechanism, e.g. their timestamps.

## TODO

As an alternative choice of configuration,
you should be able to
declare a messaging
queue with `keycloakEventQueue` as an attribute name.

This is not implemented.

## Test the SPI

If the endpoint you declared in the configuration
is active and accepts `POST` request, each time an
event is emitted by Keycloak in the realm you
configured the listener for, the endpoint will receive
a `POST` request with a JSON payload corresponding
to the Keycloak event.


## Examples of JSON Payloads

xxx

xxx

## Troubleshooting

If you have just declared the listener in 
the Event listeners section of the Keycloak GUI,
you will start seeing some warnings in the Keycloak
logs. That is because you have not configured the
listener yet.

* No `kalisio-event-gateway` user.
* No `accessToken` and/or `keycloakEventHttpListenerUrl`
  attributes declared.

If a HTTP endpoint does not respond, the listener
just logs the error.

