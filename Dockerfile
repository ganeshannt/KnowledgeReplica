FROM eclipse-temurin:17-jre-focal

EXPOSE 8080

COPY ./target/knowledgereplica-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "knowledgereplica-0.0.1-SNAPSHOT.jar"]