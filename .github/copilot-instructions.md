# AI Agent Instructions — Duty Exchange System

These instructions help AI coding agents work productively in this Spring Boot module. Focus on concrete patterns and workflows in this repo.

## Big Picture
- **Module goal:** Manage exam duty exchanges with validation, approval workflow, notifications, and B‑Form PDF generation.
- **Architecture layers:**
  - Controllers: REST endpoints under `/api/duty-exchange` (see `src/main/java/duty/exchange/controller/*`).
  - Services: Business logic, transactions, events, PDF generation (see `service/*`).
  - Repositories: Spring Data JPA for `DutyExchangeRequest`, `DutyAssignment`, `Faculty` (see `repository/*`).
  - Models/DTOs: Entities + request/response DTOs (see `model/*`, `dto/*`).
  - Events + Notifications: Asynchronous event publishing and in‑app/email notifications (see `events/*`, `notification/*`).
  - Config: Async `taskExecutor` and JPA repo enablement (see `config/DutyExchangeConfig.java`, `DutyExchangeApplication.java`).
- **Data flow (typical):**
  1. `POST /requests` with `X-User-Id` → `DutyExchangeService` validates and persists `DutyExchangeRequest`.
  2. Approver calls `/approvals/approve|reject` → `ApprovalWorkflowService` updates status, swaps duties, publishes event.
  3. Notifications dispatched; for approved requests `/requests/{id}/bform` generates a PDF.

## Conventions & Patterns
- **Base URL:** All endpoints under `/api/duty-exchange` (see `controller/DutyExchangeController.java`).
- **Auth header:** Require `X-User-Id` on mutating endpoints (create, cancel, approve/reject).
- **Validation:** Use `@Valid` on DTOs; additional domain checks in `ValidationService` and service layer.
- **Status handling:** `ApprovalStatus` drives workflow; only `PENDING` can be approved/rejected.
- **Transactions:** Service methods are `@Transactional` where needed (e.g., `ApprovalWorkflowService`).
- **Events:** Publish `DutyExchangeApprovedEvent` / `DutyExchangeRejectedEvent` on state changes.
- **Async:** Configured via `@EnableAsync` and `taskExecutor` bean; name prefix `duty-exchange-`.
- **CORS:** Controllers annotate `@CrossOrigin(origins = "*")` for demo use.
- **DTO mapping:** Manual mapping in controllers/services; keep fields in sync with entities when adding new data.

## Key Files
- App entry: `src/main/java/duty/exchange/DutyExchangeApplication.java` (enables JPA repos).
- Config: `src/main/java/duty/exchange/config/DutyExchangeConfig.java` (async executor).
- Controllers: `DutyExchangeController.java`, `ApprovalController.java`, `NotificationController.java`.
- Services: `ApprovalWorkflowService.java`, `BFormGenerationService.java`, plus `DutyExchangeService`, `ValidationService`.
- Repos: `DutyExchangeRepository.java`, `DutyAssignmentRepository.java`, `FacultyRepository.java`.
- Entities/DTOs: `model/*`, `dto/*`.
- Properties: `src/main/resources/application.properties` (port 8081, H2, JPA, logging).

## Build & Run (macOS)
- Maven required; Java 17 per `pom.xml`.
- Quick run:
  ```bash
  mvn clean spring-boot:run
  ```
- Helpful scripts in repo:
  - `run.sh`: detect Maven or wrapper, run app.
  - `fix-and-run.sh` / `setup-and-run.sh`: ensure Maven structure, then compile + run.
  - `build-and-run-demo.sh`: clean/compile/package, free port 8081, run.
  - `troubleshoot-and-run.sh`: checks Java/Maven, structure, port; compiles and runs.
- Access points after start:
  - API base: `http://localhost:8081/api/duty-exchange`
  - H2 console: `http://localhost:8081/h2-console` (`jdbc:h2:mem:dutyexchange`, user `sa`).

## Endpoint Examples
- API info: `GET /api/duty-exchange` lists common endpoints.
- Create request:
  ```bash
  curl -X POST http://localhost:8081/api/duty-exchange/requests \
    -H "Content-Type: application/json" -H "X-User-Id: 1" \
    -d '{"requesterDutyId":1,"recipientFacultyId":2,"recipientDutyId":2,"reason":"Personal emergency"}'
  ```
- Approve request:
  ```bash
  curl -X POST http://localhost:8081/api/duty-exchange/approvals/approve \
    -H "Content-Type: application/json" -H "X-User-Id: 3" \
    -d '{"requestId":1,"comments":"Approved as per policy"}'
  ```
- Download B‑Form PDF: `GET /api/duty-exchange/requests/{id}/bform`.

## Integration Points
- **Persistence:** H2 dev DB (`ddl-auto=update`); swap for prod by changing datasource in `application.properties`.
- **PDF:** iText via `PdfGeneratorUtil.generateBForm()` used in `BFormGenerationService`.
- **Email:** Optional Spring Mail props; currently commented in `application.properties`.
- **Notifications:** In‑app via `InAppNotificationService`; events can drive future channels.

## When Adding Features
- Place new endpoints under the `/api/duty-exchange` namespace; require `X-User-Id` where applicable.
- Extend DTOs and manual mappings consistently across controllers/services.
- Prefer repository query methods (and JPQL) consistent with `DutyExchangeRepository` patterns.
- Emit domain events for workflow‑relevant state changes; keep async constraints in mind.
- Update `application.properties` when changing port or DB; scripts assume 8081.

If any part of these instructions is unclear or missing (e.g., details on `DutyExchangeService` or `ValidationService`), tell us what you need and I’ll refine this doc. 