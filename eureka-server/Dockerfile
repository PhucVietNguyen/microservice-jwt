FROM openjdk:11
ADD target/eureka.server-0.0.1-SNAPSHOT.jar registry.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "registry.jar"]