#!/bin/bash

# Build and Run Demo Script

set -e

echo "========================================="
echo "Duty Exchange System - Build & Run Demo"
echo "========================================="
echo ""

cd "$(dirname "$0")"

# Step 1: Clean
echo "Step 1: Cleaning previous builds..."
mvn clean -q
echo "✓ Cleaned"
echo ""

# Step 2: Compile
echo "Step 2: Compiling project..."
mvn compile -q
if [ $? -ne 0 ]; then
    echo "✗ Compilation failed!"
    mvn compile
    exit 1
fi
echo "✓ Compiled successfully"
echo ""

# Step 3: Package
echo "Step 3: Packaging application..."
mvn package -DskipTests -q
if [ $? -ne 0 ]; then
    echo "✗ Packaging failed!"
    exit 1
fi
echo "✓ Packaged successfully"
echo ""

# Step 4: Clear port
echo "Step 4: Checking port 8081..."
if lsof -ti:8081 &> /dev/null; then
    kill $(lsof -ti:8081) 2>/dev/null
    sleep 2
    echo "✓ Port 8081 cleared"
else
    echo "✓ Port 8081 is available"
fi
echo ""

# Step 5: Run
echo "========================================="
echo "Starting Application..."
echo "========================================="
echo ""
echo "Application will be available at:"
echo "  📍 API Base: http://localhost:8081/api/duty-exchange"
echo "  📍 H2 Console: http://localhost:8081/h2-console"
echo ""
echo "Test endpoints:"
echo "  • GET http://localhost:8081/api/duty-exchange"
echo "  • GET http://localhost:8081/api/duty-exchange/requests/pending"
echo ""
echo "Press Ctrl+C to stop the application"
echo ""
echo "========================================="
echo ""

mvn spring-boot:run
