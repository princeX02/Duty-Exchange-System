# Duty Exchange System - Build and Run Demo Guide

## ✅ All Errors Fixed!

The project is ready to build and run. All compilation errors have been resolved.

## 🚀 Quick Start - Run the Demo

### Option 1: Use the Demo Script (Recommended)
```bash
cd /Users/princechaudhary/Desktop/Intenship_projects/duty-exchange
chmod +x build-and-run-demo.sh
./build-and-run-demo.sh
```

### Option 2: Manual Build and Run
```bash
cd /Users/princechaudhary/Desktop/Intenship_projects/duty-exchange

# Step 1: Clean
mvn clean

# Step 2: Build
mvn package -DskipTests

# Step 3: Clear port (if needed)
lsof -ti:8081 | xargs kill -9 2>/dev/null

# Step 4: Run
mvn spring-boot:run
```

## 📊 What You'll See

### 1. Build Output
```
[INFO] Scanning for projects...
[INFO] Building Duty Exchange System 1.0.0
[INFO] Compiling...
[INFO] Packaging...
[INFO] BUILD SUCCESS
```

### 2. Application Startup
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
...
Started DutyExchangeApplication in X.XXX seconds
```

### 3. Database Tables Created
You'll see Hibernate creating tables:
- `faculties`
- `duty_assignments`
- `duty_exchange_requests`

## 🧪 Demo Testing

Once the application starts, test these endpoints:

### 1. API Information
**Browser:** http://localhost:8081/api/duty-exchange
**Expected:** JSON with API info and endpoints list

### 2. Get Pending Requests
**Browser:** http://localhost:8081/api/duty-exchange/requests/pending
**Expected:** `[]` (empty array - no requests yet)

### 3. H2 Database Console
**Browser:** http://localhost:8081/h2-console
- **JDBC URL:** `jdbc:h2:mem:dutyexchange`
- **Username:** `sa`
- **Password:** (leave empty)

### 4. Using curl (Terminal)
```bash
# Test API info
curl http://localhost:8081/api/duty-exchange

# Test pending requests
curl http://localhost:8081/api/duty-exchange/requests/pending
```

## 📝 Complete Demo Workflow

### Step 1: Start the Application
```bash
./build-and-run-demo.sh
```

Wait for: `Started DutyExchangeApplication`

### Step 2: Test API Endpoints

**Open Browser:**
1. http://localhost:8081/api/duty-exchange
   - Should show API information

2. http://localhost:8081/api/duty-exchange/requests/pending
   - Should show `[]`

### Step 3: Access Database Console

1. Open: http://localhost:8081/h2-console
2. Connect with:
   - JDBC URL: `jdbc:h2:mem:dutyexchange`
   - Username: `sa`
   - Password: (empty)
3. View tables: `SELECT * FROM FACULTIES;`

### Step 4: Create Test Data (Optional)

In H2 Console, you can insert test data:
```sql
-- Insert a faculty
INSERT INTO FACULTIES (employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
VALUES ('EMP001', 'John', 'Doe', 'john.doe@university.edu', 'Computer Science', 'Professor', '1234567890', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert duty assignment
INSERT INTO DUTY_ASSIGNMENTS (faculty_id, duty_date, start_time, end_time, venue, duty_type, subject, course_code, semester, status, created_at, updated_at)
VALUES (1, '2026-02-01', '09:00:00', '12:00:00', 'Hall A', 'EXAM', 'Data Structures', 'CS301', 'Spring 2026', 'ASSIGNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
```

## ✅ Success Indicators

- ✅ Build completes without errors
- ✅ Application starts successfully
- ✅ "Started DutyExchangeApplication" message appears
- ✅ API endpoints respond correctly
- ✅ H2 console accessible
- ✅ Database tables created

## 🐛 Troubleshooting

### If build fails:
```bash
# Clean and rebuild
mvn clean
mvn compile
mvn package -DskipTests
```

### If port is in use:
```bash
kill $(lsof -ti:8081)
```

### If application doesn't start:
Check the error message in terminal and share it for help.

## 📍 Access Points Summary

- **API Base:** http://localhost:8081/api/duty-exchange
- **H2 Console:** http://localhost:8081/h2-console
- **Pending Requests:** http://localhost:8081/api/duty-exchange/requests/pending

## 🎉 Ready to Demo!

The project is fully built and ready to run. Execute the commands above to see it in action!
