FROM gradle:7.3.3-jdk17 AS builder

WORKDIR /app

COPY . .

RUN ./gradlew shadowJar

FROM openjdk:17

COPY --from=builder /app/build/libs/your-app.jar /app.jar

RUN apt-get update && apt-get install -y postgresql-client

ENV POSTGRES_USER=goit

ENV POSTGRES_PASSWORD=password12345

ENV POSTGRES_DB=projectDb

CMD ["java", "-jar", "/app.jar"]
