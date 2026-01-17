# Quick Start Guide - Duty Exchange System

## ✅ Project Structure Fixed!

All files have been moved to the correct Maven directory structure:
- `src/main/java/duty/exchange/` - All Java source files
- `src/main/resources/` - Configuration files

## 🚀 Run the Project

### Option 1: Using the Fix & Run Script (Recommended)
```bash
cd /Users/princechaudhary/Desktop/Intenship_projects/duty-exchange
chmod +x fix-and-run.sh
./fix-and-run.sh
```

### Option 2: Manual Steps
```bash
# 1. Navigate to project
cd /Users/princechaudhary/Desktop/Intenship_projects/duty-exchange

# 2. Install Maven (if not installed)
brew install maven

# 3. Clean and compile
mvn clean compile

# 4. Run the application
mvn spring-boot:run
```

## 📍 Access Points

Once the application starts, you can access:

- **API Base URL**: http://localhost:8080/api/duty-exchange
- **H2 Database Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:dutyexchange`
  - Username: `sa`
  - Password: (leave empty)

## 🧪 Test the API

### 1. Check if server is running
```bash
curl http://localhost:8080/api/duty-exchange/requests/pending
```

### 2. Create a Duty Exchange Request
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

## ✅ What's Fixed

1. ✅ All Java files moved to proper Maven structure
2. ✅ Main application class in correct location
3. ✅ All packages organized correctly
4. ✅ Configuration files in resources folder
5. ✅ Code compilation ready

## 📝 Project Structure

```
duty-exchange/
├── src/
│   └── main/
│       ├── java/
│       │   └── duty/
│       │       └── exchange/
│       │           ├── DutyExchangeApplication.java
│       │           ├── controller/
│       │           ├── service/
│       │           ├── repository/
│       │           ├── model/
│       │           └── ...
│       └── resources/
│           └── application.properties
├── pom.xml
└── README.md
```

## 🐛 Troubleshooting

### Issue: "Maven not found"
**Solution**: Install Maven
```bash
brew install maven
```

### Issue: "Port 8080 already in use"
**Solution**: Change port in `src/main/resources/application.properties`
```properties
server.port=8081
```

### Issue: "Compilation errors"
**Solution**: Check Java version (should be 17+)
```bash
java -version
```

## 🎉 Ready to Run!

The project is now properly structured and ready to run. Execute the commands above to start the application!
