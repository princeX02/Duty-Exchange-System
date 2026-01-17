-- Auto-loaded seed data for Duty Exchange System
-- Generated from test-data-fixed.sql

-- Clear existing data (order matters for FK constraints)
DELETE FROM DUTY_EXCHANGE_REQUESTS;
DELETE FROM DUTY_ASSIGNMENTS;
DELETE FROM FACULTIES;

-- Faculties
INSERT INTO FACULTIES (employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
VALUES ('EMP001', 'John', 'Doe', 'john.doe@university.edu', 'Computer Science', 'Professor', '1234567890', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO FACULTIES (employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
VALUES ('EMP002', 'Jane', 'Smith', 'jane.smith@university.edu', 'Mathematics', 'Associate Professor', '0987654321', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO FACULTIES (employee_id, first_name, last_name, email, department, designation, phone_number, status, created_at, updated_at)
VALUES ('EMP003', 'Robert', 'Johnson', 'robert.johnson@university.edu', 'Administration', 'Dean', '1122334455', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Duty Assignments
INSERT INTO DUTY_ASSIGNMENTS (faculty_id, duty_date, start_time, end_time, venue, duty_type, subject, course_code, semester, status, created_at, updated_at)
VALUES (1, '2026-02-15', '09:00:00', '12:00:00', 'Hall A', 'EXAM', 'Data Structures', 'CS301', 'Spring 2026', 'ASSIGNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO DUTY_ASSIGNMENTS (faculty_id, duty_date, start_time, end_time, venue, duty_type, subject, course_code, semester, status, created_at, updated_at)
VALUES (2, '2026-02-16', '14:00:00', '17:00:00', 'Hall B', 'EXAM', 'Calculus', 'MATH201', 'Spring 2026', 'ASSIGNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
