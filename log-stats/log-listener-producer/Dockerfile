FROM openjdk:8
ADD target/*.jar /
ADD pom.xml /pom.xml
ENTRYPOINT ["java", "-jar", "log-listener-producer-1.0.jar", "kafka:9092", "cityLog-raw"]
