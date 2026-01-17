#!/bin/bash

# Script to fix project structure and run the application

set -e  # Exit on error

echo "========================================="
echo "Fixing Duty Exchange System Structure"
echo "========================================="
echo ""

cd "$(dirname "$0")"

# Step 1: Create proper Maven directory structure
echo "1. Creating Maven directory structure..."
mkdir -p src/main/java/duty/exchange
mkdir -p src/main/resources
echo "   ✓ Directories created"
echo ""

# Step 2: Move main application class
if [ -f "DutyExchangeApplication.java" ]; then
    echo "2. Moving main application class..."
    mv DutyExchangeApplication.java src/main/java/duty/exchange/
    echo "   ✓ Main class moved"
else
    echo "2. Main class already in correct location"
fi
echo ""

# Step 3: Move all package directories
echo "3. Moving package directories..."
for dir in controller service repository model dto events exception notification workflow validator util config; do
    if [ -d "$dir" ] && [ ! -d "src/main/java/duty/exchange/$dir" ]; then
        mv "$dir" src/main/java/duty/exchange/
        echo "   ✓ Moved $dir"
    elif [ -d "src/main/java/duty/exchange/$dir" ]; then
        echo "   ✓ $dir already in correct location"
    fi
done
echo ""

# Step 4: Ensure application.properties is in resources
if [ -f "application.properties" ] && [ ! -f "src/main/resources/application.properties" ]; then
    echo "4. Moving application.properties..."
    mv application.properties src/main/resources/
    echo "   ✓ Properties file moved"
elif [ -f "src/main/resources/application.properties" ]; then
    echo "4. Properties file already in correct location"
fi
echo ""

# Step 5: Check Maven installation
echo "5. Checking Maven installation..."
if ! command -v mvn &> /dev/null; then
    echo "   ✗ Maven not found!"
    echo ""
    echo "   Please install Maven:"
    echo "   brew install maven"
    exit 1
fi
echo "   ✓ Maven found: $(mvn --version | head -1)"
echo ""

# Step 6: Clean and compile
echo "========================================="
echo "Compiling project..."
echo "========================================="
echo ""

mvn clean compile

if [ $? -eq 0 ]; then
    echo ""
    echo "========================================="
    echo "✓ Compilation successful!"
    echo "========================================="
    echo ""
    
    # Step 7: Run the application
    echo "========================================="
    echo "Starting Spring Boot Application..."
    echo "========================================="
    echo ""
    echo "Application will be available at:"
    echo "  - API: http://localhost:8080/api/duty-exchange"
    echo "  - H2 Console: http://localhost:8080/h2-console"
    echo ""
    echo "Press Ctrl+C to stop the application"
    echo ""
    
    mvn spring-boot:run
else
    echo ""
    echo "========================================="
    echo "✗ Compilation failed!"
    echo "========================================="
    echo ""
    echo "Please check the errors above and fix them."
    exit 1
fi
