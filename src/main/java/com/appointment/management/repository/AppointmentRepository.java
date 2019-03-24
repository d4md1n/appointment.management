package com.appointment.management.repository;

import com.appointment.management.domain.Appointment;

import java.util.List;

public interface AppointmentRepository {

    void add(Appointment appointment);

    void remove(int id);

    List<Appointment> list();

}
