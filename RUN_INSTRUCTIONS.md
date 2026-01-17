# How to Run the Duty Exchange System

## Prerequisites

1. **Java 17** (or higher) - ✅ Already installed
2. **Maven** - Needs to be installed

## Step 1: Install Maven

### Option A: Using Homebrew (Recommended for macOS)
```bash
brew install maven
```

### Option B: Manual Installation
1. Download Maven from: https://maven.apache.org/download.cgi
2. Extract and add to PATH
3. Verify: `mvn -version`

## Step 2: Navigate to Project Directory
```bash
cd /Users/princechaudhary/Desktop/Intenship_projects/duty-exchange
```

## Step 3: Build and Run the Project

### Option A: Using Maven directly
```bash
mvn clean spring-boot:run
```

### Option B: Using the run script
```bash
./run.sh
```

### Option C: Build JAR and run
```bash
mvn clean package
java -jar target/duty-exchange-1.0.0.jar
```

## Step 4: Access the Application

Once the application starts, you'll see:
```
Started DutyExchangeApplication in X.XXX seconds
```

The application will be available at:
- **API Base URL**: http://localhost:8080/api/duty-exchange
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:dutyexchange`
  - Username: `sa`
  - Password: (leave empty)

## Testing the API

### 1. Create a Duty Exchange Request
```bash
curl -X POST http://localhost:8080/api/duty-exchange/requests \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "requesterDutyId": 1,
    "recipientFacultyId": 2,
    "recipientDutyId": 2,
    "reason": "Personal emergency"
  }'
```

### 2. Get All Pending Requests
```bash
curl http://localhost:8080/api/duty-exchange/requests/pending
```

### 3. Approve a Request
```bash
curl -X POST http://localhost:8080/api/duty-exchange/approvals/approve \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 3" \
  -d '{
    "requestId": 1,
    "status": "APPROVED",
    "comments": "Approved as per policy"
  }'
```

## Troubleshooting

### Issue: "Maven not found"
**Solution**: Install Maven using `brew install maven` or download from Maven website

### Issue: "Port 8080 already in use"
**Solution**: Change port in `src/main/resources/application.properties`:
```properties
server.port=8081
```

### Issue: "Java version mismatch"
**Solution**: Ensure Java 17+ is installed and set JAVA_HOME:
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

## Project Structure

The project follows standard Maven structure:
```
duty-exchange/
├── src/
│   └── main/
│       ├── java/
│       │   └── duty/exchange/
│       │       ├── DutyExchangeApplication.java
│       │       ├── controller/
│       │       ├── service/
│       │       └── ...
│       └── resources/
│           └── application.properties
├── pom.xml
└── README.md
```

## Next Steps

1. Install Maven: `brew install maven`
2. Run: `mvn spring-boot:run`
3. Access: http://localhost:8080/api/duty-exchange
