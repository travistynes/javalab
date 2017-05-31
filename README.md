# Web Application
This includes:
- Gradle build and package as WAR file
- Get dependencies from Maven
- Servlets
- SOAP service
- Quartz job scheduling
- Log4j logging

The only server specific file is jboss-web.xml which is where the application context root is specified. This will change depending on the server.

On Tomcat, the context root will be the name of the WAR file (webapp.war) deployed on the server.

## Build project and deploy on Tomcat
For quick prototyping, gradle tasks are provided to start and deploy on a local Tomcat server.

**gradle.properties**<br>
Update your Java JDK directory and Tomcat home.<br>

**Start Tomcat, build, and deploy**<br>
```
$ gradle start_tomcat
$ gradle deploy
$ gradle undeploy
$ gradle stop_tomcat