FROM openjdk:17

ENV POSTGRES_USER=goit
ENV POSTGRES_PASSWORD=password12345
ENV POSTGRES_DB=projectDb
ENV POSTGRES_URL=team3-testdb.cv2h6ytr7csv.eu-north-1.rds.amazonaws.com

WORKDIR /app

ADD build/libs/*.jar /app/shortener.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=production", "-jar", "shortener.jar"]
