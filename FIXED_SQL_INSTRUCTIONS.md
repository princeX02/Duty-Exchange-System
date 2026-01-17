# Fixed SQL Instructions - How to Insert Test Data

## ❌ The Problem

You encountered two errors:
1. **Unique constraint violation** - Email already exists (data was inserted twice)
2. **Syntax error** - Invalid SQL statement

## ✅ The Solution

I've created a fixed SQL file. Here's how to use it:

## 📝 Step-by-Step Instructions

### Option 1: Use the Fixed SQL File (Recommended)

1. **Clear existing data first:**
   ```sql
   DELETE FROM DUTY_EXCHANGE_REQUESTS;
   DELETE FROM DUTY_ASSIGNMENTS;
   DELETE FROM FACULTIES;
   ```
   Click "Run" to execute.

2. **Insert data ONE statement at a time:**
   
   Copy and paste each INSERT statement separately and click "Run" after each one:

   ```sql
   -- Faculty 1
   INSERT INTO FACULTIES (employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
   VALUES ('EMP001', 'John', 'Doe', 'john.doe@university.edu', 'Computer Science', 'Professor', '1234567890', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
   ```

   ```sql
   -- Faculty 2
   INSERT INTO FACULTIES (employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
   VALUES ('EMP002', 'Jane', 'Smith', 'jane.smith@university.edu', 'Mathematics', 'Associate Professor', '0987654321', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
   ```

   ```sql
   -- Faculty 3 (Approver)
   INSERT INTO FACULTIES (employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
   VALUES ('EMP003', 'Robert', 'Johnson', 'robert.johnson@university.edu', 'Administration', 'Dean', '1122334455', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
   ```

   ```sql
   -- Duty Assignment 1
   INSERT INTO DUTY_ASSIGNMENTS (faculty_id, duty_date, start_time, end_time, venue, duty_type, subject, course_code, semester, status, created_at, updated_at)
   VALUES (1, '2026-02-15', '09:00:00', '12:00:00', 'Hall A', 'EXAM', 'Data Structures', 'CS301', 'Spring 2026', 'ASSIGNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
   ```

   ```sql
   -- Duty Assignment 2
   INSERT INTO DUTY_ASSIGNMENTS (faculty_id, duty_date, start_time, end_time, venue, duty_type, subject, course_code, semester, status, created_at, updated_at)
   VALUES (2, '2026-02-16', '14:00:00', '17:00:00', 'Hall B', 'EXAM', 'Calculus', 'MATH201', 'Spring 2026', 'ASSIGNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
   ```

### Option 2: Quick Fix - Clear and Re-insert

If you want to start fresh:

1. **Clear all data:**
   ```sql
   DELETE FROM DUTY_EXCHANGE_REQUESTS;
   DELETE FROM DUTY_ASSIGNMENTS;
   DELETE FROM FACULTIES;
   ```

2. **Then insert one by one** using the statements above.

## ✅ Verify Data

After inserting, verify with:

```sql
-- Check counts
SELECT 'Faculties: ' || COUNT(*) FROM FACULTIES;
SELECT 'Duty Assignments: ' || COUNT(*) FROM DUTY_ASSIGNMENTS;

-- View data
SELECT * FROM FACULTIES;
SELECT * FROM DUTY_ASSIGNMENTS;
```

## 🎯 Quick Test After Inserting

Once data is inserted, test the API:

1. **Browser:** http://localhost:8081/api/duty-exchange/requests/pending
   - Should return: `[]`

2. **Create a request:**
   ```bash
   curl -X POST http://localhost:8081/api/duty-exchange/requests \
     -H "Content-Type: application/json" \
     -H "X-User-Id: 1" \
     -d '{
       "requesterDutyId": 1,
       "recipientFacultyId": 2,
       "recipientDutyId": 2,
       "reason": "Need to exchange duties"
     }'
   ```

## 💡 Tips

- **Always clear data first** if you're re-running inserts
- **Run one INSERT at a time** to avoid errors
- **Check for existing data** before inserting:
  ```sql
  SELECT * FROM FACULTIES WHERE email = 'john.doe@university.edu';
  ```

## 📁 Files Available

- `test-data-fixed.sql` - Fixed version with step-by-step inserts
- Use this file for reference
