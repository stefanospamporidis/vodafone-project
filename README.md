# Vodafone Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

## Prerequisites

- **PostgreSQL Database**: This project requires a PostgreSQL database. You can set it up using Docker.

## Setting up the Database

1. Navigate to the `vodafone-project/development` directory.
2. Run the following command to start the database:
   ```bash
   docker-compose up -d


## Running the application

Once the database is up, navigate to the root of the project (/vodafone-project) and run the application in development mode:

```shell script
mvn clean install
```

```shell script
mvn quarkus:dev
```

> **_NOTE:_**  Quarkus  provides a Dev UI, available only in development mode at <http://localhost:8080/q/dev/>.

## Packaging the application

To package the application, run:

```shell script
./mvnw package
```

This generates the quarkus-run.jar file in the target/quarkus-app/ directory. You can run the application using:

```shell script
java -jar target/quarkus-app/quarkus-run.jar
```

If you prefer an über-jar:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

Run the packaged application with:

```shell script
java -jar target/*-runner.jar
```

## Native Executable

To build a native executable:

```shell script
./mvnw package -Dnative
```

Alternatively, without GraalVM installed, use:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

Run the native executable with:

```shell script
./target/vodafone-project-1.0.0-SNAPSHOT-runner
```

## Testing

Only unit tests are included in this project. No integration tests were provided as they were considered out of scope.

## Related Guides

- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code for Hibernate ORM via the active record or the repository pattern
- Cache ([guide](https://quarkus.io/guides/cache)): Enable application data caching in CDI beans
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC

