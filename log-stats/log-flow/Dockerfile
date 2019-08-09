FROM storm:1.2.2
ADD target/*.jar /
ADD pom.xml /pom.xml
ENTRYPOINT ["storm", "jar", "log-generator-1.0.jar"]
