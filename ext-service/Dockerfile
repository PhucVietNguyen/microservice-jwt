FROM openjdk:11
ADD target/ext.service-0.0.1-SNAPSHOT.jar auth.jar
EXPOSE 8002
ENTRYPOINT ["java", "-jar", "auth.jar"]