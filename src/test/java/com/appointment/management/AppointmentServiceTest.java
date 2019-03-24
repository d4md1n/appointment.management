package com.appointment.management;

import com.appointment.management.domain.Appointment;
import com.appointment.management.repository.AppointmentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentServiceTest {

    private AppointmentService appointmentService;
    @Mock
    private AppointmentRepository appointmentRepository;

    @Before
    public void setUp() {
        appointmentService = new AppointmentService(appointmentRepository);
    }

    @Test
    public void deleteAppointment() {
        //WHEN
        int id = 12;
        appointmentService.deleteAppointment(id);
        //THEN
        verify(appointmentRepository, times(1)).remove(id);
    }

    @Test
    public void createAppointment() {
        //WHEN
        Appointment appointment = new Appointment(42, "this is a Description", "someone", LocalDate.of(2019,8,12));
        boolean succeed = appointmentService.createAppointment(appointment);
        //THEN
        verify(appointmentRepository, times(1)).add(appointment);
        Assert.assertTrue(succeed);
    }

    @Test
    public void createAppointmentWithWrongDate() {
        //GIVEN
        Appointment appointment = new Appointment(42, "this is a Description", "someone", LocalDate.of(2017,8,12));
        //WHEN
        boolean succeed = appointmentService.createAppointment(appointment);
        //THEN
        verify(appointmentRepository, times(0)).add(appointment);
        Assert.assertFalse(succeed);
    }

    @Test
    public void getAllAppointments() {
        //GIVEN
        Mockito.when(appointmentRepository.list()).thenAnswer(invocation -> Collections.EMPTY_LIST);
        //WHEN
        List<Appointment> serviceAllAppointments = appointmentService.getAllAppointments();
        //THEN
        verify(appointmentRepository, times(1)).list();
        Assert.assertEquals(serviceAllAppointments, Collections.EMPTY_LIST);
    }
}