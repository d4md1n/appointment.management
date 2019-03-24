package com.appointment.management;

import com.appointment.management.domain.Appointment;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class PrinterTest {

    private Printer printer;
    private ByteArrayOutputStream byteArrayOutputStream;

    @Before
    public void setUp() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        printer = new Printer(byteArrayOutputStream);
    }

    @Test
    public void printAppointmentDeleteIdPrompt() {
        //WHEN
        printer.printAppointmentDeleteIdPrompt();
        //THEN
        String printedValue = new String(byteArrayOutputStream.toByteArray());
        assertEquals(printedValue, "enter the id of the appointment you want to delete" + System.lineSeparator());
    }

    @Test
    public void printAppointmentCreationInvalidDateMessage() {
        //WHEN
        printer.printAppointmentCreationInvalidDateMessage();
        //THEN
        String printedValue = new String(byteArrayOutputStream.toByteArray());
        assertEquals(printedValue, "Appointment creation failed: appointment can not be in the past" + System.lineSeparator());
    }

    @Test
    public void printAppointmentCreateAssigneePrompt() {
        //WHEN
        printer.printAppointmentCreateAssigneePrompt();
        //THEN
        String printedValue = new String(byteArrayOutputStream.toByteArray());
        assertEquals(printedValue, "enter the assignee of the appointment you want to create" + System.lineSeparator());
    }

    @Test
    public void printAppointmentCreateDatePrompt() {
        //WHEN
        printer.printAppointmentCreateDatePrompt();
        //THEN
        String printedValue = new String(byteArrayOutputStream.toByteArray());
        assertEquals(printedValue, "enter the date in yyyy-MM-dd format of the appointment you want to create" + System.lineSeparator());
    }

    @Test
    public void printAppointmentCreateDescriptionPrompt() {
        //WHEN
        printer.printAppointmentCreateDescriptionPrompt();
        //THEN
        String printedValue = new String(byteArrayOutputStream.toByteArray());
        assertEquals(printedValue, "enter the description of the appointment you want to create" + System.lineSeparator());
    }

    @Test
    public void printAppointmentCreateIdPrompt() {
        //WHEN
        printer.printAppointmentCreateIdPrompt();
        //THEN
        String printedValue = new String(byteArrayOutputStream.toByteArray());
        assertEquals(printedValue, "enter the id of the appointment you want to create" + System.lineSeparator());
    }

    @Test
    public void printAppointment() {
        //GIVEN
        Appointment appointment = new Appointment(1,"this a description message", "another", LocalDate.of(2018, 5, 1));
        //WHEN
        printer.printAppointment(appointment);
        //THEN
        String printedValue = new String(byteArrayOutputStream.toByteArray());
        assertEquals(printedValue, "appointment id: "+ appointment.getId() + " appointment date: " +
                appointment.getDate().format(DateTimeFormatter.ISO_DATE) +
                " appointment assignee: " + appointment.getAssignee() + " appointment description: " + appointment.getDescription() + System.lineSeparator());
    }

    @Test
    public void printAppointmentsListPrompt() {
        //WHEN
        printer.printAppointmentsListPrompt();
        //THEN
        String printedValue = new String(byteArrayOutputStream.toByteArray());
        assertEquals(printedValue, "The values below are your appointments" + System.lineSeparator());
    }

    @Test
    public void printAvailableInstructions() {
        //WHEN
        printer.printAvailableInstructions();
        //THEN
        String printedValue = new String(byteArrayOutputStream.toByteArray());
        assertEquals(printedValue, "enter \"show\" to see your appointments" + System.lineSeparator() +
                "enter \"create\" to create an appointment" + System.lineSeparator() +
                "enter \"delete\" to delete an appointment" + System.lineSeparator() +
                "enter \"exit\" to exit the system" + System.lineSeparator());
    }

    @Test
    public void printWelcomeMessage() {
        //WHEN
        printer.printWelcomeMessage();
        //THEN
        String printedValue = new String(byteArrayOutputStream.toByteArray());
        assertEquals(printedValue, "Welcome." + System.lineSeparator()
                + "This is the appointment management system." + System.lineSeparator());
    }
}