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
