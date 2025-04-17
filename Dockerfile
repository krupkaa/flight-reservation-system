FROM openjdk:21-jdk-slim AS build
WORKDIR /app
COPY . .
RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/flight-reservation-system-0.0.1-SNAPSHOT.jar /app/flight-reservation-system.jar
COPY src/main/resources/db/migration /app/db/migration
ENTRYPOINT ["java", "-jar", "/app/flight-reservation-system.jar"]



