#!/bin/bash

# Script to run the application and show output

cd "$(dirname "$0")"

echo "========================================="
echo "Duty Exchange System - Starting..."
echo "========================================="
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed!"
    echo "Please install Maven: brew install maven"
    exit 1
fi

echo "Maven version: $(mvn --version | head -1)"
echo ""

# Clean and compile first
echo "Step 1: Cleaning and compiling..."
mvn clean compile -q
if [ $? -ne 0 ]; then
    echo "ERROR: Compilation failed!"
    exit 1
fi
echo "✓ Compilation successful"
echo ""

# Run the application
echo "========================================="
echo "Step 2: Starting Spring Boot Application"
echo "========================================="
echo ""
echo "The application will start on:"
echo "  - API: http://localhost:8080/api/duty-exchange"
echo "  - H2 Console: http://localhost:8080/h2-console"
echo ""
echo "Press Ctrl+C to stop the application"
echo ""
echo "Starting server..."
echo ""

# Run and show output
mvn spring-boot:run
