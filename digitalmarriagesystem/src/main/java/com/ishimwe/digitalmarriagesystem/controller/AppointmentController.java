package com.ishimwe.digitalmarriagesystem.controller;

import com.ishimwe.digitalmarriagesystem.model.Appointment;
import com.ishimwe.digitalmarriagesystem.service.AppointmentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public Appointment scheduleAppointment(@RequestBody Appointment appointment) {
        return appointmentService.scheduleAppointment(appointment);
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public Appointment getAppointment(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    @GetMapping("/application/{applicationId}")
    public List<Appointment> getAppointmentsByApplication(@PathVariable Long applicationId) {
        return appointmentService.getAppointmentsByApplication(applicationId);
    }

    @PutMapping("/{id}/status")
    public Appointment updateStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String status = payload.get("status");
        return appointmentService.updateAppointmentStatus(id, status);
    }
}
