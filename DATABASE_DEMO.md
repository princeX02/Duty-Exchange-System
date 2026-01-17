# Database Demo - Testing the Duty Exchange System

## ✅ Database Connected Successfully!

You can see the H2 console is connected and showing:
- ✅ `DUTY_ASSIGNMENTS` table
- ✅ `DUTY_EXCHANGE_REQUESTS` table  
- ✅ `FACULTIES` table

## 🧪 Step 1: View Existing Tables

In the H2 Console SQL editor, run:

```sql
-- View all faculties
SELECT * FROM FACULTIES;

-- View all duty assignments
SELECT * FROM DUTY_ASSIGNMENTS;

-- View all duty exchange requests
SELECT * FROM DUTY_EXCHANGE_REQUESTS;
```

## 📝 Step 2: Insert Test Data

### Insert Faculty Members

```sql
-- Insert Faculty 1
INSERT INTO FACULTIES (employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
VALUES ('EMP001', 'John', 'Doe', 'john.doe@university.edu', 'Computer Science', 'Professor', '1234567890', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Faculty 2
INSERT INTO FACULTIES (employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
VALUES ('EMP002', 'Jane', 'Smith', 'jane.smith@university.edu', 'Mathematics', 'Associate Professor', '0987654321', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Faculty 3 (Approver)
INSERT INTO FACULTIES (employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
VALUES ('EMP003', 'Robert', 'Johnson', 'robert.johnson@university.edu', 'Administration', 'Dean', '1122334455', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
```

### Insert Duty Assignments

```sql
-- Duty Assignment for Faculty 1
INSERT INTO DUTY_ASSIGNMENTS (faculty_id, duty_date, start_time, end_time, venue, duty_type, subject, course_code, semester, status, created_at, updated_at)
VALUES (1, '2026-02-15', '09:00:00', '12:00:00', 'Hall A', 'EXAM', 'Data Structures', 'CS301', 'Spring 2026', 'ASSIGNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Duty Assignment for Faculty 2
INSERT INTO DUTY_ASSIGNMENTS (faculty_id, duty_date, start_time, end_time, venue, duty_type, subject, course_code, semester, status, created_at, updated_at)
VALUES (2, '2026-02-16', '14:00:00', '17:00:00', 'Hall B', 'EXAM', 'Calculus', 'MATH201', 'Spring 2026', 'ASSIGNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
```

## 🔍 Step 3: Verify Data

```sql
-- Check inserted faculties
SELECT id, employee_id, first_name, last_name, email, department FROM FACULTIES;

-- Check inserted duty assignments
SELECT id, faculty_id, duty_date, start_time, end_time, venue, subject FROM DUTY_ASSIGNMENTS;
```

## 🌐 Step 4: Test API Endpoints

Now that you have data, test the API:

### 1. Get All Pending Requests
**Browser:** http://localhost:8081/api/duty-exchange/requests/pending
**Expected:** `[]` (empty, no requests created yet)

### 2. Create a Duty Exchange Request
**Using curl:**
```bash
curl -X POST http://localhost:8081/api/duty-exchange/requests \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "requesterDutyId": 1,
    "recipientFacultyId": 2,
    "recipientDutyId": 2,
    "reason": "Personal emergency - need to exchange duties"
  }'
```

### 3. Check Pending Requests Again
**Browser:** http://localhost:8081/api/duty-exchange/requests/pending
**Expected:** JSON with the created request

### 4. View Request in Database
```sql
SELECT * FROM DUTY_EXCHANGE_REQUESTS;
```

## 📊 Step 5: Complete Workflow Demo

### 1. Create Request (via API)
```bash
curl -X POST http://localhost:8081/api/duty-exchange/requests \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "requesterDutyId": 1,
    "recipientFacultyId": 2,
    "recipientDutyId": 2,
    "reason": "Need to exchange due to scheduling conflict"
  }'
```

### 2. View Request in Database
```sql
SELECT 
    request_id,
    (SELECT first_name || ' ' || last_name FROM FACULTIES WHERE id = requester_faculty_id) as requester,
    (SELECT first_name || ' ' || last_name FROM FACULTIES WHERE id = recipient_faculty_id) as recipient,
    reason,
    status
FROM DUTY_EXCHANGE_REQUESTS;
```

### 3. Approve Request (via API)
```bash
curl -X POST http://localhost:8081/api/duty-exchange/approvals/approve \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 3" \
  -d '{
    "requestId": 1,
    "status": "APPROVED",
    "comments": "Approved as per policy"
  }'
```

### 4. Verify Exchange in Database
```sql
-- Check if duties were exchanged
SELECT 
    da.id,
    f.first_name || ' ' || f.last_name as faculty_name,
    da.duty_date,
    da.status
FROM DUTY_ASSIGNMENTS da
JOIN FACULTIES f ON da.faculty_id = f.id;
```

## 🎯 Quick Test Queries

### View All Data
```sql
-- All faculties
SELECT * FROM FACULTIES;

-- All assignments with faculty names
SELECT 
    da.id,
    f.first_name || ' ' || f.last_name as faculty,
    da.duty_date,
    da.start_time,
    da.end_time,
    da.venue,
    da.subject
FROM DUTY_ASSIGNMENTS da
JOIN FACULTIES f ON da.faculty_id = f.id;

-- All exchange requests with details
SELECT 
    der.request_id,
    r.first_name || ' ' || r.last_name as requester,
    rec.first_name || ' ' || rec.last_name as recipient,
    der.reason,
    der.status,
    der.requested_at
FROM DUTY_EXCHANGE_REQUESTS der
JOIN FACULTIES r ON der.requester_faculty_id = r.id
JOIN FACULTIES rec ON der.recipient_faculty_id = rec.id;
```

## ✅ Success Checklist

- [x] Database connected
- [x] Tables created (FACULTIES, DUTY_ASSIGNMENTS, DUTY_EXCHANGE_REQUESTS)
- [ ] Test data inserted
- [ ] API endpoints tested
- [ ] Duty exchange request created
- [ ] Request approved/rejected
- [ ] Database updated correctly

## 🎉 You're All Set!

The application is running successfully. You can now:
1. Insert test data via H2 console
2. Test API endpoints via browser or curl
3. View data changes in real-time
4. Complete the full duty exchange workflow
