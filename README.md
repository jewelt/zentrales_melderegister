# Zentrales Melderegister

## Problemstellung und Lösungsansatz

Jedes Bundesland bzw. Kommune meldet ihre positiv getesteten Fälle. Dies passiert über alle möglichen Kommunikationsformen wie Mail, Excel und teilweise auch noch Fax. Dies führt zu viel Verwaltungsaufwand und frisst Ressourcen.

Unser Ziel ist es, eine zentrale Meldeplattform für durchgeführte Corona-Tests den Kommunen zur Verfügung zu stellen, um den Prozess der Meldung zu optimieren.

Dafür haben wir eine Webanwendung erstellt, die von den Komunen aber auch auswertende Institutionen genutzt werden kann. Das Erfassen eines Tests wurde möglichst simpel gehalten. Durch diese zentrale Erfassung lassen sich aussagekräftige Statistiken erstellen. Beispielsstatisken haben wir in einem Dashboard dargestellt. Daten lassen sich exportieren. Außerdem können andere Systeme die Daten über eine REST-API abfragen und weiterverwenden.

## Architektur

Das Projekt besteht aus einem Spring Boot Webservice, der eine REST-API zur Verfügung stellt.
Zur Datenhaltung wird eine Postgres-Datenbank genutzt.

Die Oberfläche ist in Angular geschrieben und greift auf das Backend zu.

Die Kommunikation zwischen Frontend und Backend geschieht über einen generierten Swagger-Client.
Dieser wurde im Rahmen des Hackathons erstmal in den Sourcen des Frontends inkludiert.


## Ausführungshinweise

### Backend

- Konfiguration für Postgres-Datenbank hinterlegen (Springtypisch in `backend/src/main/resources/application.properties` oder als Environmentparameter)
- Spring-REST-Service starten. Über Flyway wird automatisch das Datenbankschema angelegt.
- SQL Skript in `backend/sql/init.sql` ausführen, um erste Daten anzulegen.
- Der REST-Service ist jetzt unter `localhost:8080` verfügbar

### Frontend

- In den Ordner `frontend` wechseln
- `npm start` ausführen
- Das Frontend steht jetzt unter `localhost:4200` zur Verfügung
- Einloggen als `max.mustermann.user@test.test` mit Passwort `1234`
