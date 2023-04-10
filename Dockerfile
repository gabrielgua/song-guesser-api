FROM openjdk:17-oracle

WORKDIR /app

COPY target/hk-song-guesser-0.0.1-SNAPSHOT.jar /app/api.jar

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]

