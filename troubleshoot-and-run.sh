#!/bin/bash

# Comprehensive troubleshooting and run script

set -e

echo "========================================="
echo "Duty Exchange System - Troubleshooting"
echo "========================================="
echo ""

cd "$(dirname "$0")"

# Step 1: Check Java
echo "1. Checking Java installation..."
if ! command -v java &> /dev/null; then
    echo "   ✗ Java not found!"
    echo "   Please install Java 17+: brew install openjdk@17"
    exit 1
fi
JAVA_VERSION=$(java -version 2>&1 | head -1)
echo "   ✓ $JAVA_VERSION"
echo ""

# Step 2: Check Maven
echo "2. Checking Maven installation..."
if ! command -v mvn &> /dev/null; then
    echo "   ✗ Maven not found!"
    echo "   Please install: brew install maven"
    exit 1
fi
MAVEN_VERSION=$(mvn --version | head -1)
echo "   ✓ $MAVEN_VERSION"
echo ""

# Step 3: Check project structure
echo "3. Checking project structure..."
if [ ! -f "src/main/java/duty/exchange/DutyExchangeApplication.java" ]; then
    echo "   ✗ Main application class not found!"
    exit 1
fi
echo "   ✓ Project structure correct"
echo ""

# Step 4: Check port availability
echo "4. Checking port 8081..."
if lsof -ti:8081 &> /dev/null; then
    echo "   ⚠ Port 8081 is in use"
    read -p "   Kill process on port 8081? (y/n): " answer
    if [ "$answer" = "y" ] || [ "$answer" = "Y" ]; then
        kill $(lsof -ti:8081) 2>/dev/null
        sleep 2
        echo "   ✓ Port 8081 freed"
    fi
else
    echo "   ✓ Port 8081 is available"
fi
echo ""

# Step 5: Clean and compile
echo "5. Cleaning and compiling..."
mvn clean compile -q
if [ $? -ne 0 ]; then
    echo "   ✗ Compilation failed!"
    echo "   Running verbose compilation..."
    mvn clean compile
    exit 1
fi
echo "   ✓ Compilation successful"
echo ""

# Step 6: Run application
echo "========================================="
echo "Starting Spring Boot Application"
echo "========================================="
echo ""
echo "Application will be available at:"
echo "  - API: http://localhost:8081/api/duty-exchange"
echo "  - H2 Console: http://localhost:8081/h2-console"
echo ""
echo "Press Ctrl+C to stop"
echo ""

mvn spring-boot:run
