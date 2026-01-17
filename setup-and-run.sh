#!/bin/bash

# Script to setup Maven structure and run the Duty Exchange System

echo "========================================="
echo "Setting up Duty Exchange System"
echo "========================================="

cd "$(dirname "$0")"

# Create Maven directory structure
echo "Creating Maven directory structure..."
mkdir -p src/main/java/duty/exchange
mkdir -p src/main/resources

# Move main application class if it's in root
if [ -f "DutyExchangeApplication.java" ]; then
    echo "Moving main application class..."
    mv DutyExchangeApplication.java src/main/java/duty/exchange/
fi

# Move all package directories if they're in root
for dir in controller service repository model dto events exception notification workflow validator util config; do
    if [ -d "$dir" ] && [ ! -d "src/main/java/duty/exchange/$dir" ]; then
        echo "Moving $dir directory..."
        mv "$dir" src/main/java/duty/exchange/
    fi
done

# Move resources if needed
if [ -f "application.properties" ] && [ ! -f "src/main/resources/application.properties" ]; then
    echo "Moving application.properties..."
    mv application.properties src/main/resources/
fi

echo ""
echo "========================================="
echo "Checking Maven installation..."
echo "========================================="

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Maven is not installed!"
    echo ""
    echo "Please install Maven first:"
    echo "  brew install maven"
    echo ""
    echo "Or download from: https://maven.apache.org/download.cgi"
    exit 1
fi

echo "Maven found: $(mvn --version | head -1)"
echo ""

# Clean and run
echo "========================================="
echo "Building and starting the application..."
echo "========================================="
echo ""

mvn clean spring-boot:run
