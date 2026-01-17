# How to Run the Project and See Output

## Quick Start

Run this command in your terminal:

```bash
cd /Users/princechaudhary/Desktop/Intenship_projects/duty-exchange
./run-and-show-output.sh
```

Or manually:

```bash
cd /Users/princechaudhary/Desktop/Intenship_projects/duty-exchange
mvn spring-boot:run
```

## Expected Output

When the application starts successfully, you should see output like:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

... (Spring Boot startup logs) ...

Started DutyExchangeApplication in X.XXX seconds (process running for X.XXX)
```

## What You'll See

1. **Spring Boot Banner** - The ASCII art Spring Boot logo
2. **Configuration Loading** - Application properties being loaded
3. **Database Initialization** - H2 database starting
4. **JPA/Hibernate** - Entity scanning and table creation
5. **Tomcat Starting** - Embedded server starting on port 8080
6. **Application Started** - Final success message

## Access Points

Once you see "Started DutyExchangeApplication", the application is ready:

- **API Base URL**: http://localhost:8080/api/duty-exchange
- **H2 Database Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:dutyexchange`
  - Username: `sa`
  - Password: (leave empty)

## Test the API

Open a new terminal and test:

```bash
# Check if server is running
curl http://localhost:8080/api/duty-exchange/requests/pending

# Expected response: [] (empty array, since no requests exist yet)
```

## Troubleshooting

### If you see "Port 8080 already in use":
Change the port in `src/main/resources/application.properties`:
```properties
server.port=8081
```

### If you see compilation errors:
Make sure Java 17+ is installed:
```bash
java -version
```

### If Maven is not found:
Install Maven:
```bash
brew install maven
```

## Stop the Application

Press `Ctrl+C` in the terminal where the application is running.

## Next Steps

After the application starts:
1. Open http://localhost:8080/h2-console in your browser
2. Connect to the database
3. Test the API endpoints
4. Create duty exchange requests
