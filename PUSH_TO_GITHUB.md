# Push to GitHub Repository

Your project has been prepared for GitHub. To push to the repository, run these commands:

## Step 1: Navigate to project directory
```bash
cd /Users/princechaudhary/Desktop/Intenship_projects/duty-exchange
```

## Step 2: Push to GitHub
```bash
git push -u origin main
```

If you encounter authentication issues, you may need to:

### Option A: Use Personal Access Token
1. Go to GitHub Settings > Developer settings > Personal access tokens
2. Generate a new token with `repo` permissions
3. Use the token as password when prompted

### Option B: Use SSH (if configured)
```bash
git remote set-url origin git@github.com:princeX02/Duty-Exchange-System.git
git push -u origin main
```

## Alternative: Manual Push Steps

If automatic push fails, you can manually execute:

```bash
# 1. Initialize git (if not done)
git init

# 2. Add remote
git remote add origin https://github.com/princeX02/Duty-Exchange-System.git

# 3. Add all files
git add .

# 4. Commit
git commit -m "Initial commit: Complete Duty Exchange System module"

# 5. Set main branch
git branch -M main

# 6. Push
git push -u origin main
```

## Repository URL
https://github.com/princeX02/Duty-Exchange-System.git
