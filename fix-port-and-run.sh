#!/bin/bash

# Script to fix port issue and run the application

cd "$(dirname "$0")"

echo "========================================="
echo "Fixing Port Issue and Running Application"
echo "========================================="
echo ""

# Check what's using port 8080
echo "1. Checking port 8080..."
PID=$(lsof -ti:8080 2>/dev/null)
if [ ! -z "$PID" ]; then
    echo "   Port 8080 is in use by process: $PID"
    echo "   Options:"
    echo "   a) Kill the process: kill $PID"
    echo "   b) Use a different port (already changed to 8081)"
    read -p "   Kill process $PID? (y/n): " answer
    if [ "$answer" = "y" ] || [ "$answer" = "Y" ]; then
        kill $PID 2>/dev/null
        echo "   ✓ Process killed"
        sleep 2
    fi
else
    echo "   ✓ Port 8080 is free"
fi
echo ""

# Change port to 8081 if needed
if grep -q "server.port=8080" src/main/resources/application.properties; then
    echo "2. Changing port to 8081..."
    sed -i '' 's/server.port=8080/server.port=8081/' src/main/resources/application.properties
    echo "   ✓ Port changed to 8081"
else
    echo "2. Port already configured (8081)"
fi
echo ""

# Run the application
echo "========================================="
echo "Starting Spring Boot Application..."
echo "========================================="
echo ""
echo "Application will start on:"
echo "  - API: http://localhost:8081/api/duty-exchange"
echo "  - H2 Console: http://localhost:8081/h2-console"
echo ""
echo "Press Ctrl+C to stop"
echo ""

mvn spring-boot:run
