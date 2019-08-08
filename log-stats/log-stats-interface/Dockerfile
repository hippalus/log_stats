FROM openjdk:8
ADD log-consumer/target/*.jar /
ADD pom.xml /pom.xml
ENTRYPOINT ["java", "-jar", "log-consumer-1.0.jar"]
