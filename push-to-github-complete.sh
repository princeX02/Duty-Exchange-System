#!/bin/bash

# Complete script to push all files to GitHub with proper commit message

set -e

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

# Step 2: Configure git user
echo "2. Configuring git user..."
git config user.name "princeX02"
git config user.email "princeX02@users.noreply.github.com"
echo "   ✓ Git user configured: $(git config user.name)"
echo ""

# Step 3: Add remote repository
echo "3. Setting up remote repository..."
git remote remove origin 2>/dev/null
git remote add origin https://github.com/princeX02/Duty-Exchange-System.git
echo "   ✓ Remote added: https://github.com/princeX02/Duty-Exchange-System.git"
echo ""

# Step 4: Add all files
echo "4. Staging all files..."
git add .
echo "   ✓ All files staged"
echo ""

# Step 5: Show what will be committed
echo "5. Files to be committed:"
git status --short | head -20
if [ $(git status --short | wc -l) -gt 20 ]; then
    echo "   ... and more files"
fi
echo ""

# Step 6: Commit with detailed message
echo "6. Committing changes..."
git commit -m "Initial commit: Complete Duty Exchange System Module

- Implemented complete Duty Exchange System with all components
- Added controllers: DutyExchangeController, ApprovalController, NotificationController
- Added services: DutyExchangeService, ValidationService, ApprovalWorkflowService, BFormGenerationService
- Added repositories: DutyExchangeRepository, FacultyRepository, DutyAssignmentRepository
- Added models: DutyExchangeRequest, DutyAssignment, Faculty, ApprovalStatus
- Added DTOs: DutyExchangeRequestDTO, ApprovalRequestDTO, DutyExchangeResponseDTO
- Added workflow handlers and event system
- Added notification services (Email and In-App)
- Added validators and exception handlers
- Added PDF generation for B-Form
- Configured Spring Boot with H2 database
- Added comprehensive documentation and setup scripts
- Fixed code structure and compilation issues
- Ready for deployment and testing"
echo "   ✓ Changes committed"
echo ""

# Step 7: Set main branch
echo "7. Setting main branch..."
git branch -M main
echo "   ✓ Branch set to main"
echo ""

# Step 8: Show commit info
echo "8. Commit information:"
git log --oneline -1
echo ""

# Step 9: Push to GitHub
echo "========================================="
echo "Pushing to GitHub..."
echo "========================================="
echo ""

git push -u origin main

if [ $? -eq 0 ]; then
    echo ""
    echo "========================================="
    echo "✓ SUCCESS! All files pushed to GitHub"
    echo "========================================="
    echo ""
    echo "Repository URL:"
    echo "https://github.com/princeX02/Duty-Exchange-System"
    echo ""
    echo "You can now view your code on GitHub!"
    echo ""
else
    echo ""
    echo "========================================="
    echo "Push requires authentication"
    echo "========================================="
    echo ""
    echo "Please run the push command manually:"
    echo "  git push -u origin main"
    echo ""
    echo "When prompted:"
    echo "  Username: princeX02"
    echo "  Password: [Use Personal Access Token]"
    echo ""
    echo "To create a token:"
    echo "  1. Go to: https://github.com/settings/tokens"
    echo "  2. Click 'Generate new token (classic)'"
    echo "  3. Select 'repo' scope"
    echo "  4. Copy the token and use it as password"
    echo ""
fi
