-- Test Data for Duty Exchange System
-- Run these queries in H2 Console to populate test data

-- Clear existing data first (to avoid duplicate errors)
DELETE FROM DUTY_EXCHANGE_REQUESTS;
DELETE FROM DUTY_ASSIGNMENTS;
DELETE FROM FACULTIES;

-- Insert Faculty Members (using MERGE to avoid duplicates)
MERGE INTO FACULTIES (id, employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
KEY(employee_id)
VALUES 
(1, 'EMP001', 'John', 'Doe', 'john.doe@university.edu', 'Computer Science', 'Professor', '1234567890', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO FACULTIES (id, employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
KEY(employee_id)
VALUES 
(2, 'EMP002', 'Jane', 'Smith', 'jane.smith@university.edu', 'Mathematics', 'Associate Professor', '0987654321', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO FACULTIES (id, employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
KEY(employee_id)
VALUES 
(3, 'EMP003', 'Robert', 'Johnson', 'robert.johnson@university.edu', 'Administration', 'Dean', '1122334455', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO FACULTIES (id, employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
KEY(employee_id)
VALUES 
(4, 'EMP004', 'Sarah', 'Williams', 'sarah.williams@university.edu', 'Computer Science', 'Assistant Professor', '2233445566', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO FACULTIES (id, employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
KEY(employee_id)
VALUES 
(5, 'EMP005', 'Michael', 'Brown', 'michael.brown@university.edu', 'Mathematics', 'Professor', '3344556677', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Duty Assignments
INSERT INTO DUTY_ASSIGNMENTS (id, faculty_id, duty_date, start_time, end_time, venue, duty_type, subject, course_code, semester, status, created_at, updated_at)
VALUES 
(1, 1, '2026-02-15', '09:00:00', '12:00:00', 'Hall A', 'EXAM', 'Data Structures', 'CS301', 'Spring 2026', 'ASSIGNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO DUTY_ASSIGNMENTS (id, faculty_id, duty_date, start_time, end_time, venue, duty_type, subject, course_code, semester, status, created_at, updated_at)
VALUES 
(2, 2, '2026-02-16', '14:00:00', '17:00:00', 'Hall B', 'EXAM', 'Calculus I', 'MATH201', 'Spring 2026', 'ASSIGNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO DUTY_ASSIGNMENTS (id, faculty_id, duty_date, start_time, end_time, venue, duty_type, subject, course_code, semester, status, created_at, updated_at)
VALUES 
(3, 1, '2026-02-20', '10:00:00', '13:00:00', 'Hall C', 'INVIGILATION', 'Algorithms', 'CS302', 'Spring 2026', 'ASSIGNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO DUTY_ASSIGNMENTS (id, faculty_id, duty_date, start_time, end_time, venue, duty_type, subject, course_code, semester, status, created_at, updated_at)
VALUES 
(4, 4, '2026-02-18', '09:00:00', '12:00:00', 'Hall A', 'EXAM', 'Database Systems', 'CS401', 'Spring 2026', 'ASSIGNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO DUTY_ASSIGNMENTS (id, faculty_id, duty_date, start_time, end_time, venue, duty_type, subject, course_code, semester, status, created_at, updated_at)
VALUES 
(5, 5, '2026-02-19', '14:00:00', '17:00:00', 'Hall B', 'EXAM', 'Linear Algebra', 'MATH301', 'Spring 2026', 'ASSIGNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Verify inserted data
SELECT 'Faculties inserted: ' || COUNT(*) FROM FACULTIES;
SELECT 'Duty assignments inserted: ' || COUNT(*) FROM DUTY_ASSIGNMENTS;

-- View the data
SELECT id, employee_id, first_name, last_name, email, department FROM FACULTIES;
SELECT id, faculty_id, duty_date, start_time, end_time, venue, subject FROM DUTY_ASSIGNMENTS;
