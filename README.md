# javalab
This includes:
- Gradle build and package as WAR file
- Get dependencies from Maven
- Servlets
- SOAP service
- Quartz job scheduling
- Log4j logging

The only server specific file is jboss-web.xml which is where the application context root is specified. This will change depending on the server. The rest of the web application is server agnostic.
