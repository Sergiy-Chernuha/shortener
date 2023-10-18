
Spring Profiles:
__________________________________________
TO SET VALUES FOR ENVIRONMENT VARIABLES:

    spring.datasource.url=jdbc:postgresql://${DB_URL}
    spring.datasource.username=${DB_USERNAME}
    spring.datasource.password=${DB_PASSWORD}

Environment variables:

    DB_URL - URL to Postgre data base;
    DB_USERNAME - Postgre data base username;
    DB_PASSWORD - Postgre data base user password.

By default active Spring Profile is dev.
to run in production  mode:
java -Dspring.profiles.active=production -jar shortener-0.0.1-SNAPSHOT.jar


Links to swagger documentation: http://localhost:9999/swagger-ui/index.html
