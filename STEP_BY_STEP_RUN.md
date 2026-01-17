# Step-by-Step Guide to Run the Project

## Prerequisites Check

### 1. Check Java Installation
```bash
java -version
```
**Expected:** Java 17 or higher
**If not installed:** `brew install openjdk@17`

### 2. Check Maven Installation
```bash
mvn --version
```
**Expected:** Maven 3.x
**If not installed:** `brew install maven`

### 3. Navigate to Project
```bash
cd /Users/princechaudhary/Desktop/Intenship_projects/duty-exchange
```

## Method 1: Using the Troubleshoot Script (Recommended)

```bash
chmod +x troubleshoot-and-run.sh
./troubleshoot-and-run.sh
```

## Method 2: Manual Steps

### Step 1: Clean Previous Builds
```bash
mvn clean
```

### Step 2: Compile the Project
```bash
mvn compile
```

**If you see errors:**
- Check Java version: `java -version` (should be 17+)
- Check Maven: `mvn --version`
- Check project structure: `ls src/main/java/duty/exchange/`

### Step 3: Check Port Availability
```bash
# Check if port 8081 is in use
lsof -ti:8081

# If a process is using it, kill it:
kill $(lsof -ti:8081)
```

### Step 4: Run the Application
```bash
mvn spring-boot:run
```

## Expected Output

You should see:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
...
Started DutyExchangeApplication in X.XXX seconds
```

## Common Issues and Solutions

### Issue 1: "Maven not found"
**Solution:**
```bash
brew install maven
```

### Issue 2: "Java not found" or wrong version
**Solution:**
```bash
brew install openjdk@17
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

### Issue 3: "Port already in use"
**Solution:**
```bash
# Option A: Kill the process
kill $(lsof -ti:8081)

# Option B: Change port in application.properties
# Edit: src/main/resources/application.properties
# Change: server.port=8082
```

### Issue 4: "Compilation failed"
**Solution:**
```bash
# Clean and rebuild
mvn clean
mvn compile

# Check for errors in output
# Make sure all files are in correct location:
ls src/main/java/duty/exchange/
```

### Issue 5: "Application failed to start"
**Check:**
1. Are all dependencies downloaded? `mvn dependency:resolve`
2. Is the database URL correct? Check `application.properties`
3. Are there any error messages in the output?

## Verify It's Running

### 1. Check API Endpoint
Open browser: http://localhost:8081/api/duty-exchange

Should show JSON with API information.

### 2. Check H2 Console
Open browser: http://localhost:8081/h2-console
- JDBC URL: `jdbc:h2:mem:dutyexchange`
- Username: `sa`
- Password: (empty)

### 3. Test with curl
```bash
curl http://localhost:8081/api/duty-exchange/requests/pending
```

Should return: `[]`

## If Still Not Working

### Get Detailed Error Messages
```bash
mvn spring-boot:run -X
```

### Check Logs
Look for error messages in the terminal output. Common errors:
- Port conflicts
- Database connection issues
- Missing dependencies
- Compilation errors

### Verify Project Structure
```bash
# Should show all Java files
find src/main/java -name "*.java" | wc -l
# Should be around 30+ files

# Check main class exists
ls src/main/java/duty/exchange/DutyExchangeApplication.java
```

## Quick Test Commands

```bash
# 1. Verify Java
java -version

# 2. Verify Maven
mvn --version

# 3. Clean and compile
mvn clean compile

# 4. Run
mvn spring-boot:run
```

## Still Having Issues?

Share the exact error message you're seeing, and I can help troubleshoot!
