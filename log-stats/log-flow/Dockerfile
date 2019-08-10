FROM storm:1.2.2
ADD target/*.jar /
ADD pom.xml /pom.xml
ADD run.sh /run.sh
ENTRYPOINT ["sh", "/run.sh"]