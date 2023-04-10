FROM maven:latest AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-oracle
WORKDIR /app
COPY --from=build /target/hk-song-guesser-0.0.1-SNAPSHOT.jar /app/api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api.jar"]

