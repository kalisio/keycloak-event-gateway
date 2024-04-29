_[Back to the home page](../README.md)
— Previous page: [2. Configuration](./Configuration.md)
— Next page: [4. How to deploy locally](./Deploy.md)_

---

# 3. How to build

## Build the JAR file

Prerequisites: Java, Maven


```shell
$ mvn install
```

That builds a JAR file in the `target` directory.

## Embed the JAR file in a Docker image

Prerequisites: Docker


```shell
$ ./build.sh
```

That stores the JAR file at the root
directory of a minimal Docker image,
making it able to be accessed from a
Docker container running Keycloak. See
the [`docker-compose.yml`](../docker-compose.yml) file
for an exemple of configuration.

---

_[Back to the home page](../README.md)
— Previous page: [2. Configuration](./Configuration.md)
— Next page: [4. How to deploy locally](./Deploy.md)_

