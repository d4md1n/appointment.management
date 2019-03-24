package com.appointment.management;

import com.appointment.management.domain.Appointment;
import com.appointment.management.repository.AppointmentRepository;

import java.time.LocalDate;
import java.util.List;

public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void deleteAppointment(Integer id) {
        appointmentRepository.remove(id);
    }

    public boolean createAppointment(Appointment appointment){
        if (appointment.getDate().isAfter(LocalDate.now())) {
            appointmentRepository.add(appointment);
            return true;
        } else {
            return false;
        }
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.list();
    }

}
