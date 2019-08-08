FROM openjdk:8
ADD target/*.jar /
ADD pom.xml /pom.xml
ENTRYPOINT ["java", "-jar", "log-generator-1.0.jar"]
