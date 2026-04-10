package com.ishimwe.digitalmarriagesystem.service;

import com.ishimwe.digitalmarriagesystem.model.Appointment;
import java.util.List;

public interface AppointmentService {
    Appointment scheduleAppointment(Appointment appointment);
    List<Appointment> getAllAppointments();
    Appointment getAppointmentById(Long id);
    List<Appointment> getAppointmentsByApplication(Long applicationId);
    Appointment updateAppointmentStatus(Long id, String status);
}
