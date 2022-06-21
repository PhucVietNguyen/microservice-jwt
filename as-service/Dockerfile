FROM openjdk:11
ADD target/as.service-0.0.1-SNAPSHOT.jar auth.jar
EXPOSE 8001
ENTRYPOINT ["java", "-jar", "auth.jar"]