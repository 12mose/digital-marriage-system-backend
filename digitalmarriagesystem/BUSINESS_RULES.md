# Task 8: Advanced Features & Business Logic - Implementation Report

In this phase, we have implemented the core business logic, automated workflows, and data validation for the Digital Marriage Registration System.

## 1. Implemented Business Rules

### Marriage Application Validation
- **Applicant Identity**: An application requires two distinct applicants. The system validates that `applicant1Id` and `applicant2Id` are not the same.
- **User Verification**: The system verifies that both applicant IDs correspond to existing users.
- **Legal Age Validation**: The system now checks the `birthDate` of both applicants. If provided, applicants must be at least **18 years old** to submit a marriage application.
- **Bigamy Prevention**: The system performs a lookup to ensure that neither applicant is currently in an **ACTIVE** marriage. Applicants already married cannot submit new applications.
- **Default State**: New applications are automatically assigned a `PENDING` status.

### Automated Workflow (The "Happy Path")
- **Approval Logic**: When an admin updates a marriage application status to `APPROVED`, the following automated steps occur:
    1. **Marriage Creation**: A new `Marriage` record is automatically created with the applicants' information and the current date.
    2. **Certificate Issuance**: A new `Certificate` is automatically generated for the marriage, including a unique certificate number.
- **Transaction Integrity**: The update process is wrapped in a `@Transactional` block. If any step fails (e.g., certificate creation), the entire transition is rolled back to maintain data consistency.

### Filtering and Search
- **Status Filtering**: API endpoints for both Applications and Marriages now support filtering by status via query parameters (e.g., `/api/applications?status=APPROVED`).

## 2. Advanced Backend Logic

### Audit Logging
- **Activity Tracking**: Every status change on a marriage application is recorded in an `AuditLog`.
- **Identity Tracking**: The logs capture the email of the authenticated user who performed the action using `SecurityUtils`.
- **Action Description**: Logs include clear descriptions of status transitions (e.g., "UPDATED APPLICATION STATUS FROM PENDING TO APPROVED").

### Security Integration
- The system leverages `SecurityContextHolder` to automatically identify the acting user for audit purposes, ensuring accountability.

## 3. Technical Changes Summary

| Component | Key Enhancement |
| :--- | :--- |
| `MarriageApplicationRepository` | Added status-based filtering. |
| `MarriageApplicationService` | Implemented complex status update workflow. |
| `MarriageApplicationController` | Added `@RequestParam` support for filtering. |
| `MarriageRepository` | Added status-based filtering. |
| `MarriageServiceImpl` | Added status-based filtering. |
| `MarriageController` | Added `@RequestParam` support for filtering. |
| `MarriageApplicationServiceImpl` | Integrated `AuditLogService`, `MarriageService`, and `CertificateService`. |
