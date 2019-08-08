FROM openjdk:8
ADD log-generator/target/*.jar /
ADD log-listener-producer/target/*.jar /
ADD run.sh /run.sh
ENTRYPOINT ["sh", "/run.sh"]
