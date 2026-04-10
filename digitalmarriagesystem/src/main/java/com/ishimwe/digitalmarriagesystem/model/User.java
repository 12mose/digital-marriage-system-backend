package com.ishimwe.digitalmarriagesystem.model;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String nationalId;
    private java.time.LocalDate birthDate;
    private String status = "Active";
    private boolean verified = false;
    private String verificationToken;
    private java.time.LocalDateTime lastActive;
 
    public User(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public java.time.LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(java.time.LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }
 
    public boolean isVerified() {
        return verified;
    }
 
    public void setVerified(boolean verified) {
        this.verified = verified;
    }
 
    public String getVerificationToken() {
        return verificationToken;
    }
 
    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }
 
    public java.time.LocalDate getBirthDate() {
        return birthDate;
    }
 
    public void setBirthDate(java.time.LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
}