#!/bin/bash

# Complete script: Fix code, Run application, and Push to GitHub

set -e

echo "========================================="
echo "Duty Exchange System - Complete Setup"
echo "========================================="
echo ""

cd "$(dirname "$0")"

# Step 1: Verify Maven
echo "1. Checking Maven installation..."
if ! command -v mvn &> /dev/null; then
    echo "   ✗ Maven not found!"
    echo "   Please install: brew install maven"
    exit 1
fi
echo "   ✓ Maven found: $(mvn --version | head -1)"
echo ""

# Step 2: Clean and compile
echo "2. Cleaning and compiling project..."
mvn clean compile -q
if [ $? -eq 0 ]; then
    echo "   ✓ Compilation successful"
else
    echo "   ✗ Compilation failed"
    exit 1
fi
echo ""

# Step 3: Setup Git
echo "3. Setting up Git repository..."
if [ ! -d .git ]; then
    git init
    echo "   ✓ Git initialized"
fi

if [ -z "$(git config user.name)" ]; then
    git config user.name "princeX02"
    git config user.email "princeX02@users.noreply.github.com"
    echo "   ✓ Git user configured"
fi

git remote remove origin 2>/dev/null
git remote add origin https://github.com/princeX02/Duty-Exchange-System.git
echo "   ✓ Remote configured"
echo ""

# Step 4: Commit changes
echo "4. Committing changes..."
git add .
if [ -n "$(git status --porcelain)" ]; then
    git commit -m "Complete Duty Exchange System: Fixed code, compiled successfully, ready to run" -q
    echo "   ✓ Changes committed"
else
    echo "   ✓ No new changes"
fi

git branch -M main
echo ""

# Step 5: Push to GitHub
echo "========================================="
echo "Pushing to GitHub..."
echo "========================================="
echo ""

git push -u origin main 2>&1

if [ $? -eq 0 ]; then
    echo ""
    echo "========================================="
    echo "✓ SUCCESS! Pushed to GitHub"
    echo "========================================="
    echo ""
    echo "Repository: https://github.com/princeX02/Duty-Exchange-System"
    echo ""
else
    echo ""
    echo "========================================="
    echo "Push requires authentication"
    echo "========================================="
    echo ""
    echo "Please authenticate and run:"
    echo "  git push -u origin main"
    echo ""
    echo "Use Personal Access Token:"
    echo "  https://github.com/settings/tokens"
    echo ""
fi

# Step 6: Run application
echo "========================================="
echo "Starting Spring Boot Application..."
echo "========================================="
echo ""
echo "Application will be available at:"
echo "  - API: http://localhost:8080/api/duty-exchange"
echo "  - H2 Console: http://localhost:8080/h2-console"
echo ""
echo "Press Ctrl+C to stop"
echo ""

mvn spring-boot:run
