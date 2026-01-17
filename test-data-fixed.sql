-- FIXED Test Data for Duty Exchange System
-- Run these queries ONE AT A TIME in H2 Console

-- Step 1: Clear existing data first
DELETE FROM DUTY_EXCHANGE_REQUESTS;
DELETE FROM DUTY_ASSIGNMENTS;
DELETE FROM FACULTIES;

-- Step 2: Insert Faculty 1
INSERT INTO FACULTIES (employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
VALUES ('EMP001', 'John', 'Doe', 'john.doe@university.edu', 'Computer Science', 'Professor', '1234567890', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Step 3: Insert Faculty 2
INSERT INTO FACULTIES (employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
VALUES ('EMP002', 'Jane', 'Smith', 'jane.smith@university.edu', 'Mathematics', 'Associate Professor', '0987654321', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Step 4: Insert Faculty 3 (Approver)
INSERT INTO FACULTIES (employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
VALUES ('EMP003', 'Robert', 'Johnson', 'robert.johnson@university.edu', 'Administration', 'Dean', '1122334455', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Step 5: Insert Duty Assignment for Faculty 1
INSERT INTO DUTY_ASSIGNMENTS (faculty_id, duty_date, start_time, end_time, venue, duty_type, subject, course_code, semester, status, created_at, updated_at)
VALUES (1, '2026-02-15', '09:00:00', '12:00:00', 'Hall A', 'EXAM', 'Data Structures', 'CS301', 'Spring 2026', 'ASSIGNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Step 6: Insert Duty Assignment for Faculty 2
INSERT INTO DUTY_ASSIGNMENTS (faculty_id, duty_date, start_time, end_time, venue, duty_type, subject, course_code, semester, status, created_at, updated_at)
VALUES (2, '2026-02-16', '14:00:00', '17:00:00', 'Hall B', 'EXAM', 'Calculus', 'MATH201', 'Spring 2026', 'ASSIGNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Step 7: Verify the data
SELECT 'Faculties: ' || COUNT(*) FROM FACULTIES;
SELECT 'Duty Assignments: ' || COUNT(*) FROM DUTY_ASSIGNMENTS;

-- Step 8: View inserted data
SELECT id, employee_id, first_name, last_name, email, department FROM FACULTIES;
SELECT id, faculty_id, duty_date, start_time, end_time, venue, subject FROM DUTY_ASSIGNMENTS;
