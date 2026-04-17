package com.ishimwe.digitalmarriagesystem.repository;

import com.ishimwe.digitalmarriagesystem.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByApplicationId(Long applicationId);
    List<Appointment> findByApplicationIdIn(List<Long> applicationIds);
}
