# Digital Marriage Registration System - Security Model Explanation

This document outlines the security architecture and implementation for Phase 7 (Authentication & Security) of the Digital Marriage Registration System.

## 1. Security Overview
The system implements a robust security layer based on **Spring Security 6+**, ensuring that all API endpoints are protected and accessible only to authorized users with the correct roles.

## 2. Authentication Mechanism (JWT)
The system uses **JSON Web Tokens (JWT)** for stateless authentication. 

### Flow:
1.  **Login**: Users provide their email and password to the `/api/auth/login` endpoint.
2.  **Verification**: The system verifies the credentials against the database using `BCrypt`.
3.  **Token Generation**: Upon successful login, the system generates a signed JWT containing the user's identification.
4.  **Authorization**: For subsequent requests, the client must include the JWT in the `Authorization: Bearer <token>` HTTP header.
5.  **Validation**: The `JwtAuthenticationFilter` intercepts requests, extracts the token, validates its signature and expiration, and sets the security context.

## 3. Password Security
User passwords are never stored in plain text. The system uses the **BCrypt hashing algorithm** via Spring Security's `PasswordEncoder`. 
*   **Registration**: When a user registers, their password is encoded before being saved to the database.
*   **Authentication**: During login, the provided password is compared with the stored hash using `BCrypt.matches()`.

## 4. Authorization & Role-Based Access Control (RBAC)
The system distinguishes between two primary roles:

| Role | Permissions |
| :--- | :--- |
| **ADMIN** | Full access to all endpoints, including User Management and Audit Logs. |
| **USER** | Access to Marriage Applications, Certificates, and Documents related to their own lifecycle. |

### Access Rules Summary:
- **Public**: `/api/auth/login`, `/api/auth/register`, and static web resources (`/index.html`, `/dashboard.html`).
- **Admin Only**: `/api/users/**`, `/api/auditlogs/**`.
- **Admin & User**: `/api/applications/**`, `/api/marriages/**`, `/api/certificates/**`, `/api/documents/**`.

## 5. Session Management
The system is entirely **stateless**. No session information is stored on the server side. This improves scalability and simplifies the security model by relying solely on the JWT for each request.

## 6. CORS Configuration
The system includes a Cross-Origin Resource Sharing (CORS) configuration that allows frontend applications (like the dashboard) to communicate with the backend API from different origins securely.

---
*Date: March 19, 2026*
*Phase: Phase 7 - Authentication & Security Deliverable*
