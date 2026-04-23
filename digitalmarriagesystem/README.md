# Digital Marriage System 💍

A comprehensive, secure, and modern web application for managing marriage registrations, appointments, and digital certificates.

## 🚀 Key Features
- **Online Applications**: Citizens can register and submit marriage applications digitally.
- **Appointment Management**: Integrated booking system for marriage ceremonies.
- **Digital Certificates**: Generation of high-quality, verifiable digital certificates.
- **Administrative Dashboard**: Role-based access for Admins and Marriage Officers.
- **Audit Logs**: Full transparency with system-wide activity tracking.
- **Reporting**: Exportable reports in Excel/CSV formats for data analysis.

## 🛠 Tech Stack
- **Backend**: Spring Boot 3.x, JPA, Hibernate, MySQL.
- **Frontend**: Vanilla JavaScript, Modern CSS3, HTML5.
- **Security**: Spring Security with custom authentication and role-based access control.
- **Communications**: SMTP Email integration for status updates and verification.

## 📦 Deployment Guide

### 1. Prerequisites
- Java 17+
- MySQL Server 8.0+
- Maven

### 2. Environment Setup
Create a `.env` file in the root directory (refer to `.env.example`) and configure the following variables:
```bash
DB_URL=jdbc:mysql://your-server:3306/digitalmarriagedb_v2
DB_USER=your_username
DB_PASS=your_password
SMTP_USERNAME=your_email@gmail.com
SMTP_PASSWORD=your_app_password
APP_URL=http://your-domain.com
```

### 3. Build & Run
```bash
# Clean and build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```
The application will be available at `http://localhost:8081` (default).

## 📄 Documentation
Detailed phase reports are available in the project root:
- [Phase 1-4: Design & Planning](PHASE1_4_DESIGN.md)
- [Phase 7: Security Model](SECURITY_MODEL.md)
- [Phase 8: Business Rules](BUSINESS_RULES.md)
- [Phase 9: Integration Report](PHASE9_REPORT.md)
- [Phase 10: Testing Report](PHASE10_REPORT.md)

---
Developed by **Ishimwe** | 2026
