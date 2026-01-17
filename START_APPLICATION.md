# Start the Application - Quick Guide

## 🚀 Running the Application

The application is starting in the background. Here's how to run it manually if needed:

### Method 1: Using Maven (Recommended)
```bash
cd /Users/princechaudhary/Desktop/Intenship_projects/duty-exchange
mvn spring-boot:run
```

### Method 2: Using the Demo Script
```bash
cd /Users/princechaudhary/Desktop/Intenship_projects/duty-exchange
./build-and-run-demo.sh
```

### Method 3: Using the Troubleshoot Script
```bash
cd /Users/princechaudhary/Desktop/Intenship_projects/duty-exchange
./troubleshoot-and-run.sh
```

## ⏱️ Wait for Startup

The application takes about 10-15 seconds to start. You'll see:

1. **Maven build output**
2. **Spring Boot banner** (ASCII art)
3. **Database initialization**
4. **"Started DutyExchangeApplication"** message

## ✅ Verify It's Running

### Check 1: API Endpoint
Open browser: http://localhost:8081/api/duty-exchange

Should show JSON with API information.

### Check 2: H2 Console
Open browser: http://localhost:8081/h2-console

Connect with:
- JDBC URL: `jdbc:h2:mem:dutyexchange`
- Username: `sa`
- Password: (empty)

### Check 3: Using curl
```bash
curl http://localhost:8081/api/duty-exchange
curl http://localhost:8081/api/duty-exchange/requests/pending
```

## 🛑 Stop the Application

Press `Ctrl+C` in the terminal where the application is running.

## 📍 Access Points

Once running:
- **API Base:** http://localhost:8081/api/duty-exchange
- **H2 Console:** http://localhost:8081/h2-console
- **Pending Requests:** http://localhost:8081/api/duty-exchange/requests/pending

## 🐛 If It Doesn't Start

1. **Check port:** `lsof -ti:8081` - if something is using it, kill it
2. **Check Java:** `java -version` (should be 17+)
3. **Check Maven:** `mvn --version`
4. **Clean and rebuild:** `mvn clean compile`

## 🎉 Success!

When you see "Started DutyExchangeApplication", the app is ready to use!
