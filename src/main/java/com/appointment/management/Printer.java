package com.appointment.management;

import com.appointment.management.domain.Appointment;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Printer {

    private final BufferedWriter bufferedWriter;

    Printer(OutputStream outputStream){
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public void printAppointmentDeleteIdPrompt() {
        safeWrite("enter the id of the appointment you want to delete");
    }

    public void printAppointmentCreationInvalidDateMessage() {
        safeWrite("Appointment creation failed: appointment can not be in the past");
    }

    public void printAppointmentCreateAssigneePrompt() {
        safeWrite("enter the assignee of the appointment you want to create");
    }

    public void printAppointmentCreateDatePrompt() {
        safeWrite("enter the date in yyyy-MM-dd format of the appointment you want to create");
    }

    public void printAppointmentCreateDescriptionPrompt() {
        safeWrite("enter the description of the appointment you want to create");
    }

    public void printAppointmentCreateIdPrompt() {
        safeWrite("enter the id of the appointment you want to create");
    }

    public void printAppointment(Appointment appointment) {
        safeWrite("appointment id: " + appointment.getId() + " " +
                "appointment date: " + appointment.getDate().toString() + " " +
                "appointment assignee: " + appointment.getAssignee() + " " +
                "appointment description: " + appointment.getDescription());
    }

    public void printAppointmentsListPrompt() {
        safeWrite("The values below are your appointments");
    }

    public void printAvailableInstructions() {
        safeWrite("enter \"show\" to see your appointments");
        safeWrite("enter \"create\" to create an appointment");
        safeWrite("enter \"delete\" to delete an appointment");
        safeWrite("enter \"exit\" to exit the system");
    }

    public void printWelcomeMessage() {
        safeWrite("Welcome.");
        safeWrite("This is the appointment management system.");
    }

    private void safeWrite(String string) {
        try {
            bufferedWriter.write(string);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
