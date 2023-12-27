### The file generation for a maven project:
--------------------------------------------

In addition to using spring initializr, maven project files can be manually created or can be auto generated using the following command. This creates the necesary blocks in the pom.xml as well as the directory structure of the project

  mvn -B archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4

In this case, the main directory is 'my-app' and the following directory structure adds 

  /com/mycompany/app/

to /src/java/main/
   /src/java/test/

and the default package is package com.mycompany.app;

add dependencies for rest
