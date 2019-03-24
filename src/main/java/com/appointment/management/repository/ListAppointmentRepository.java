package com.appointment.management.repository;

import com.appointment.management.domain.Appointment;

import java.util.ArrayList;
import java.util.List;

public class ListAppointmentRepository implements AppointmentRepository {
    final List<Appointment> appointmentList;

    public ListAppointmentRepository() {
        appointmentList = new ArrayList<>();
    }

    @Override
    public void add(Appointment appointment) {
        appointmentList.add(appointment);
    }

    @Override
    public void remove(int id) {
        for (int i = 0; i < appointmentList.size(); i++) {
            if (appointmentList.get(i).getId() == id) {
                appointmentList.remove(i);
            }
        }
    }

    @Override
    public List<Appointment> list() {
        return new ArrayList<>(appointmentList);
    }
}