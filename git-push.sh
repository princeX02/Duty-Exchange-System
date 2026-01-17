#!/bin/bash

# Complete script to push Duty Exchange System to GitHub

echo "========================================="
echo "Pushing Duty Exchange System to GitHub"
echo "========================================="
echo ""

cd "$(dirname "$0")"

# Step 1: Initialize git if needed
if [ ! -d .git ]; then
    echo "1. Initializing git repository..."
    git init
    echo "   ✓ Git initialized"
else
    echo "1. Git repository already initialized"
fi
echo ""

# Step 2: Configure git user (if not set)
if [ -z "$(git config user.name)" ]; then
    echo "2. Configuring git user..."
    git config user.name "princeX02"
    git config user.email "princeX02@users.noreply.github.com"
    echo "   ✓ Git user configured"
else
    echo "2. Git user already configured: $(git config user.name)"
fi
echo ""

# Step 3: Add remote repository
echo "3. Setting up remote repository..."
git remote remove origin 2>/dev/null
git remote add origin https://github.com/princeX02/Duty-Exchange-System.git
echo "   ✓ Remote added: https://github.com/princeX02/Duty-Exchange-System.git"
echo ""

# Step 4: Add all files
echo "4. Staging files..."
git add .
echo "   ✓ Files staged"
echo ""

# Step 5: Check if there are changes to commit
if [ -z "$(git status --porcelain)" ]; then
    echo "5. No changes to commit (everything already committed)"
else
    echo "5. Committing changes..."
    git commit -m "Initial commit: Complete Duty Exchange System module with all components"
    echo "   ✓ Changes committed"
fi
echo ""

# Step 6: Set main branch
echo "6. Setting main branch..."
git branch -M main
echo "   ✓ Branch set to main"
echo ""

# Step 7: Show status
echo "7. Repository status:"
git status --short | head -10
echo ""

# Step 8: Push to GitHub
echo "========================================="
echo "Pushing to GitHub..."
echo "========================================="
echo ""

git push -u origin main

if [ $? -eq 0 ]; then
    echo ""
    echo "========================================="
    echo "✓ Successfully pushed to GitHub!"
    echo "========================================="
    echo ""
    echo "Your repository is now available at:"
    echo "https://github.com/princeX02/Duty-Exchange-System"
    echo ""
else
    echo ""
    echo "========================================="
    echo "Push failed. This might be due to:"
    echo "========================================="
    echo ""
    echo "1. Authentication required:"
    echo "   - Go to: https://github.com/settings/tokens"
    echo "   - Generate new token (classic) with 'repo' scope"
    echo "   - Use token as password when prompted"
    echo ""
    echo "2. Repository might not exist or you don't have access"
    echo ""
    echo "3. Try running manually:"
    echo "   git push -u origin main"
    echo ""
fi
