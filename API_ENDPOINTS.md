# API Endpoints Guide

## Base URL
http://localhost:8081/api/duty-exchange

## Available Endpoints

### 1. API Information
- **GET** `/api/duty-exchange`
  - Returns API information and list of available endpoints
  - Example: `curl http://localhost:8081/api/duty-exchange`

### 2. Duty Exchange Requests

#### Get All Pending Requests
- **GET** `/api/duty-exchange/requests/pending`
  - Returns all pending duty exchange requests
  - Example: `curl http://localhost:8081/api/duty-exchange/requests/pending`

#### Get Request by ID
- **GET** `/api/duty-exchange/requests/{requestId}`
  - Returns a specific duty exchange request
  - Example: `curl http://localhost:8081/api/duty-exchange/requests/1`

#### Get Requests by Faculty
- **GET** `/api/duty-exchange/requests/faculty/{facultyId}`
  - Returns all requests for a specific faculty member
  - Example: `curl http://localhost:8081/api/duty-exchange/requests/faculty/1`

#### Create New Request
- **POST** `/api/duty-exchange/requests`
  - Creates a new duty exchange request
  - Headers: `X-User-Id: 1` (requester ID)
  - Body:
    ```json
    {
      "requesterDutyId": 1,
      "recipientFacultyId": 2,
      "recipientDutyId": 2,
      "reason": "Personal emergency"
    }
    ```
  - Example:
    ```bash
    curl -X POST http://localhost:8081/api/duty-exchange/requests \
      -H "Content-Type: application/json" \
      -H "X-User-Id: 1" \
      -d '{
        "requesterDutyId": 1,
        "recipientFacultyId": 2,
        "recipientDutyId": 2,
        "reason": "Personal emergency"
      }'
    ```

#### Cancel Request
- **PUT** `/api/duty-exchange/requests/{requestId}/cancel`
  - Cancels a pending request
  - Headers: `X-User-Id: 1` (faculty ID)
  - Example:
    ```bash
    curl -X PUT http://localhost:8081/api/duty-exchange/requests/1/cancel \
      -H "X-User-Id: 1"
    ```

#### Download B-Form PDF
- **GET** `/api/duty-exchange/requests/{requestId}/bform`
  - Downloads B-Form PDF for approved request
  - Example: `curl http://localhost:8081/api/duty-exchange/requests/1/bform -o bform.pdf`

### 3. Approvals

#### Approve Request
- **POST** `/api/duty-exchange/approvals/approve`
  - Approves a duty exchange request
  - Headers: `X-User-Id: 3` (approver ID)
  - Body:
    ```json
    {
      "requestId": 1,
      "status": "APPROVED",
      "comments": "Approved as per policy"
    }
    ```

#### Reject Request
- **POST** `/api/duty-exchange/approvals/reject`
  - Rejects a duty exchange request
  - Headers: `X-User-Id: 3` (approver ID)
  - Body:
    ```json
    {
      "requestId": 1,
      "status": "REJECTED",
      "comments": "Reason for rejection"
    }
    ```

### 4. Notifications

#### Get User Notifications
- **GET** `/api/duty-exchange/notifications/user/{userId}`
  - Returns all notifications for a user

#### Get Unread Count
- **GET** `/api/duty-exchange/notifications/user/{userId}/unread-count`
  - Returns unread notification count

## Testing the API

### Using curl

1. **Check API info:**
   ```bash
   curl http://localhost:8081/api/duty-exchange
   ```

2. **Get pending requests:**
   ```bash
   curl http://localhost:8081/api/duty-exchange/requests/pending
   ```

3. **Create a request (requires data in database first):**
   ```bash
   curl -X POST http://localhost:8081/api/duty-exchange/requests \
     -H "Content-Type: application/json" \
     -H "X-User-Id: 1" \
     -d '{
       "requesterDutyId": 1,
       "recipientFacultyId": 2,
       "recipientDutyId": 2,
       "reason": "Test request"
     }'
   ```

## H2 Database Console

Access at: http://localhost:8081/h2-console

- JDBC URL: `jdbc:h2:mem:dutyexchange`
- Username: `sa`
- Password: (leave empty)

## Common Issues

### "No static resource" error
- Make sure you're using a valid endpoint path
- Use `/api/duty-exchange/requests/pending` instead of just `/api/duty-exchange`

### 404 Not Found
- Check that the application is running
- Verify the endpoint path is correct
- Ensure the request method (GET, POST, etc.) matches

### 400 Bad Request
- Check request body format (must be valid JSON)
- Verify required headers are present (X-User-Id for some endpoints)
