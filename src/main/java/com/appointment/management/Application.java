package com.appointment.management;

import com.appointment.management.repository.AppointmentRepository;
import com.appointment.management.repository.ListAppointmentRepository;

public class Application {
    public static void main(String[] args) {
        new AppointmentManagement(System.in, System.out, new AppointmentService(new ListAppointmentRepository())).run();
    }
}

