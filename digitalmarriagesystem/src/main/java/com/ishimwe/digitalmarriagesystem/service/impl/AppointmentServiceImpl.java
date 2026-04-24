package com.ishimwe.digitalmarriagesystem.service.impl;

import com.ishimwe.digitalmarriagesystem.model.Appointment;
import com.ishimwe.digitalmarriagesystem.repository.AppointmentRepository;
import com.ishimwe.digitalmarriagesystem.service.AppointmentService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final com.ishimwe.digitalmarriagesystem.security.SecurityUtils securityUtils;
    private final com.ishimwe.digitalmarriagesystem.repository.UserRepository userRepository;
    private final com.ishimwe.digitalmarriagesystem.repository.MarriageApplicationRepository applicationRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  com.ishimwe.digitalmarriagesystem.security.SecurityUtils securityUtils,
                                  com.ishimwe.digitalmarriagesystem.repository.UserRepository userRepository,
                                  com.ishimwe.digitalmarriagesystem.repository.MarriageApplicationRepository applicationRepository) {
        this.appointmentRepository = appointmentRepository;
        this.securityUtils = securityUtils;
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Appointment scheduleAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        String email = securityUtils.getCurrentUserEmail();
        if (securityUtils.hasRole("CITIZEN") && email != null) {
            java.util.Optional<com.ishimwe.digitalmarriagesystem.model.User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                Long userId = userOpt.get().getUserId();
                List<com.ishimwe.digitalmarriagesystem.model.MarriageApplication> apps = applicationRepository.findByApplicant1IdOrApplicant2Id(userId, userId);
                List<Long> appIds = apps.stream().map(com.ishimwe.digitalmarriagesystem.model.MarriageApplication::getApplicationId).collect(java.util.stream.Collectors.toList());
                if (appIds.isEmpty()) return java.util.Collections.emptyList();
                List<Appointment> appts = appointmentRepository.findByApplicationIdIn(appIds);
                populateNames(appts);
                return appts;
            }
        }
        List<Appointment> appts = appointmentRepository.findAll();
        populateNames(appts);
        return appts;
    }

    @Override
    public List<Appointment> getAppointmentsByStatus(String status) {
        String email = securityUtils.getCurrentUserEmail();
        if (securityUtils.hasRole("CITIZEN") && email != null) {
            java.util.Optional<com.ishimwe.digitalmarriagesystem.model.User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                Long userId = userOpt.get().getUserId();
                List<com.ishimwe.digitalmarriagesystem.model.MarriageApplication> apps = applicationRepository.findByApplicant1IdOrApplicant2Id(userId, userId);
                List<Long> appIds = apps.stream().map(com.ishimwe.digitalmarriagesystem.model.MarriageApplication::getApplicationId).collect(java.util.stream.Collectors.toList());
                if (appIds.isEmpty()) return java.util.Collections.emptyList();
                List<Appointment> appts = appointmentRepository.findByApplicationIdIn(appIds).stream()
                        .filter(a -> a.getStatus() != null && a.getStatus().equalsIgnoreCase(status))
                        .collect(java.util.stream.Collectors.toList());
                populateNames(appts);
                return appts;
            }
        }
        List<Appointment> appts = appointmentRepository.findByStatus(status);
        populateNames(appts);
        return appts;
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            if (securityUtils.hasRole("CITIZEN")) {
                String email = securityUtils.getCurrentUserEmail();
                java.util.Optional<com.ishimwe.digitalmarriagesystem.model.User> userOpt = userRepository.findByEmail(email);
                if (userOpt.isPresent()) {
                    Long userId = userOpt.get().getUserId();
                    com.ishimwe.digitalmarriagesystem.model.MarriageApplication app = applicationRepository.findById(appointment.getApplicationId()).orElse(null);
                    if (app == null || (!userId.equals(app.getApplicant1Id()) && !userId.equals(app.getApplicant2Id()))) {
                        throw new RuntimeException("Unauthorized access to this appointment");
                    }
                }
            }
            populateNames(appointment);
            return appointment;
        }
        return null;
    }

    @Override
    public List<Appointment> getAppointmentsByApplication(Long applicationId) {
        if (securityUtils.hasRole("CITIZEN")) {
            String email = securityUtils.getCurrentUserEmail();
            java.util.Optional<com.ishimwe.digitalmarriagesystem.model.User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                Long userId = userOpt.get().getUserId();
                com.ishimwe.digitalmarriagesystem.model.MarriageApplication app = applicationRepository.findById(applicationId).orElse(null);
                if (app == null || (!userId.equals(app.getApplicant1Id()) && !userId.equals(app.getApplicant2Id()))) {
                    throw new RuntimeException("Unauthorized access to appointments for this application");
                }
            }
        }
        List<Appointment> appts = appointmentRepository.findByApplicationId(applicationId);
        populateNames(appts);
        return appts;
    }

    @Override
    public Appointment updateAppointmentStatus(Long id, String status) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setStatus(status);
            return appointmentRepository.save(appointment);
        }
        return null;
    }

    private void populateNames(Appointment appt) {
        if (appt == null) return;
        applicationRepository.findById(appt.getApplicationId()).ifPresent(app -> {
            StringBuilder names = new StringBuilder();
            userRepository.findById(app.getApplicant1Id()).ifPresent(u -> names.append(u.getFirstName()).append(" ").append(u.getLastName()));
            names.append(" & ");
            userRepository.findById(app.getApplicant2Id()).ifPresent(u -> names.append(u.getFirstName()).append(" ").append(u.getLastName()));
            appt.setApplicantNames(names.toString());
        });
    }

    private void populateNames(List<Appointment> appts) {
        if (appts == null) return;
        appts.forEach(this::populateNames);
    }
}
