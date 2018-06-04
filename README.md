# javalab

Spring boot with maven build.

Run with:

```
$ mvn spring-boot:run
```

## External configuration
See: https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html

Spring loads properties from application.properties from the following locations:
1. A /config subdirectory of the current directory
2. The current directory
3. A classpath /config package
4. The classpath root

Properties defined in locations higher in the list override those defined in lower locations.

## SSL
To run the application locally with SSL enabled, generate a self-signed certificate:

```
keytool -genkeypair -alias my_alias -keyalg RSA -keysize 2048 -keystore javalab.jks -validity 3650
```

* Enter keystore password: changeme
* Re-enter new password: changeme
* Copy the file to config/javalab.jks
