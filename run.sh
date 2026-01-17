#!/bin/bash

# Script to run the Duty Exchange System

echo "========================================="
echo "Duty Exchange System - Starting..."
echo "========================================="

# Check if Maven is installed
if command -v mvn &> /dev/null; then
    echo "Maven found. Building and running the project..."
    mvn clean spring-boot:run
elif [ -f "./mvnw" ]; then
    echo "Maven Wrapper found. Building and running the project..."
    chmod +x ./mvnw
    ./mvnw clean spring-boot:run
else
    echo "ERROR: Maven is not installed and Maven Wrapper is not available."
    echo ""
    echo "Please install Maven using one of the following methods:"
    echo "1. Using Homebrew: brew install maven"
    echo "2. Download from: https://maven.apache.org/download.cgi"
    echo "3. Or use Maven Wrapper by running: mvn wrapper:wrapper"
    echo ""
    exit 1
fi
