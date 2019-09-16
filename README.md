# Javalab

Example front end web development using Vaadin. This is a Spring Boot project built with Maven.

## Table of Contents

[TOC]

---

#### Requirements

* Java 1.8
* [Maven](https://maven.apache.org/)

#### Build and run locally

```sh
mvn spring-boot:run
```

Vaadin requires the use of Node.js and npm. If it is not installed on your system, then the first time you build and run the application, it will be downloaded into the local project.

Various other run options:

```
mvn test
mvn clean spring-boot:run -DskipTests
```

#### Production build

```sh
mvn clean package -Pproduction
```

This activates the production profile in pom.xml and produces a jar file packaged with all front end dependencies so that Node.js and npm are not required to be installed on the production server. The application can then be started with

```sh
java -jar jarName.jar
```

#### External configuration

See: https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html

Spring loads properties from application.properties from the following locations:

1. A /config subdirectory of the current directory
2. The current directory
3. A classpath /config package
4. The classpath root

Properties defined in locations higher in the list override those defined in lower locations.

#### TLS

To run the application locally with TLS enabled, generate a self-signed certificate:

```
keytool -genkeypair -alias my_alias -keyalg RSA -keysize 2048 -keystore javalab.jks -validity 3650
```

* Enter keystore password: changeme
* Re-enter new password: changeme
* Copy the file to config/javalab.jks

Set the following properties in application.properties:

```
server.ssl.enabled=true
server.ssl.key-store=config/javalab.jks
server.ssl.key-store-password=changeme
server.ssl.keyStoreType=JKS
server.ssl.keyAlias=my_alias
```

#### Test the application

Post requests using a client like [Postman](https://www.getpostman.com/) using the endpoint https://localhost:8080/javalab

See the Vaadin page at: https://localhost:8080/javalab/vaadin
