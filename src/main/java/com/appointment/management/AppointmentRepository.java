package com.appointment.management;

import java.util.List;

public interface AppointmentRepository {

    void add(Appointment appointment);

    void remove(int id);

    List<Appointment> list();

}
