# Karate Spring Boot Automation Scaffold

A sample Spring Boot + Karate automation framework that exercises API, multi-database, and Cosmos DB validations. The project uses Karate for BDD-style feature files with supporting Spring beans and JavaScript helpers.

## Project structure
- `pom.xml` – Spring Boot, Karate, JDBC (Postgres & SQL Server), and Azure Cosmos dependencies.
- `src/main/java/com/pharmacy/automation` – Spring Boot application, multi-database routing, Cosmos service abstractions, and sample API.
- `src/main/resources` – Schema/data seed scripts and application configuration.
- `src/test/resources/karate-config.js` – Wires Spring beans into Karate and exposes JS helper modules.
- `src/test/resources/helpers` – JavaScript helpers for DB, Cosmos, reusable step utilities, and shared example data.
- `src/test/resources/features` – Karate feature files written in a Cucumber-like style.
- `src/test/java` – Karate JUnit5 runner and Spring context bridge.

## Running tests
1. Start the Spring Boot server with the Karate test profile so the test runner can connect: `./mvnw -Dspring-boot.run.profiles=test spring-boot:run`
2. In another terminal, execute the Karate suite: `./mvnw -Dtest=UsersRunner test`

> Note: In restricted environments the Maven build may fail to download the Spring Boot parent POM. If that happens, run the commands in an internet-enabled environment or add a local Maven cache/mirror.

Karate produces multiple artifacts after a run:
- The built-in HTML and JSON reports land under `target/karate-reports`.
- An Extent Spark summary is generated at `target/karate-reports/extent-report.html` from the Karate `Results` object.
- Consolidated debug logs (HTTP payloads, database calls, Cosmos queries) stream to `target/karate-tests.log` via `logback-test.xml`.
- Runtime hooks log suite/feature/scenario lifecycle events to the console so you can quickly see execution progress when running in CI.

## Cucumber-style scenarios
The sample feature `src/test/resources/features/users/users.feature` uses tagged scenarios and human-readable steps (Given/When/Then/And) to stay close to Cucumber conventions while still leveraging Karate's DSL. Scenarios cover:
- Validating the REST API response against relational data (main + reporting databases)
- Aligning Cosmos DB documents with API data, including container-level queries

You can extend these scenarios by adding new tags, step texts, or JS helper methods without changing the Java plumbing.

### Scenario Outline & data externalization
`users.feature` now demonstrates a Scenario Outline that pulls example rows from `helpers/users-data.js`, keeping test data close to code while avoiding inline duplication. Shared utility helpers are available for both Java (`PropertiesLoader`, `SampleDataUtils`) and JavaScript (`helpers/util.js`) to read properties, format values, and compose reusable Cosmos seed data.

Additional helper highlights:
- `helpers/util.js` now exposes `deserializeUser(...)`, which wraps the shared `SerializationUtils` class to turn API JSON payloads into Java POJOs for deeper assertions.
- `ExtentReportGenerator` converts Karate `Results` into an Extent Spark HTML report so you get a Cucumber-style execution summary alongside the default Karate output.
