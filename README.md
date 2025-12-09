# Biblioteksapp

En biblioteksapp byggd med Spring Boot 4.0 och SQL som en del av en workshop.

## Programkrav

- Java 24
- PostgreSQL

## Bygga och köra applikationen

- Kopiera `application.properties.example` till `application.properties` i `src/main/resources/` och uppdatera databasuppgifterna för att matcha din lokala PostgreSQL-instans.

- Skapa tabellerna i databasen med hjälp av SQL-skriptet `schema.sql` som finns i projektets resources-mapp.

- Kör applicationen med kommandot `./gradlew bootRun` i terminalen.
