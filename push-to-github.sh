#!/bin/bash

# Script to push Duty Exchange System to GitHub

echo "========================================="
echo "Pushing Duty Exchange System to GitHub"
echo "========================================="

# Navigate to project directory
cd "$(dirname "$0")"

# Check if git is initialized
if [ ! -d .git ]; then
    echo "Initializing git repository..."
    git init
fi

# Configure git (if not already configured)
if [ -z "$(git config user.name)" ]; then
    git config user.name "princeX02"
    git config user.email "princeX02@users.noreply.github.com"
fi

# Add remote (remove if exists, then add)
git remote remove origin 2>/dev/null
git remote add origin https://github.com/princeX02/Duty-Exchange-System.git

# Add all files
echo "Adding files..."
git add .

# Check if there are changes to commit
if [ -z "$(git status --porcelain)" ]; then
    echo "No changes to commit."
else
    echo "Committing changes..."
    git commit -m "Initial commit: Complete Duty Exchange System module with all components"
fi

# Set main branch
git branch -M main

# Show status
echo ""
echo "Repository status:"
git status

echo ""
echo "========================================="
echo "Ready to push!"
echo "========================================="
echo ""
echo "Run the following command to push to GitHub:"
echo "  git push -u origin main"
echo ""
echo "If you need to authenticate, use a Personal Access Token:"
echo "  1. Go to: https://github.com/settings/tokens"
echo "  2. Generate new token with 'repo' permissions"
echo "  3. Use token as password when prompted"
echo ""
