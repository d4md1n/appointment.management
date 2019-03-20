package com.appointment.management;

public class Printer {
    public void printAppointmentDeleteIdPrompt() {
        System.out.println("enter the id of the appointment you want to delete");
    }

    public void printAppointmentCreationInvalidDateMessage() {
        System.out.println("Appointment creation failed: appointment can not be in the past");
    }

    public void printAppointmentCreateAssigneePrompt() {
        System.out.println("enter the assignee of the appointment you want to create");
    }

    public void printAppointmentCreateDatePrompt() {
        System.out.println("enter the date in yyyy-MM-dd format of the appointment you want to create");
    }

    public void printAppointmentCreateDescriptionPrompt() {
        System.out.println("enter the description of the appointment you want to create");
    }

    public void printAppointmentCreateIdPrompt() {
        System.out.println("enter the id of the appointment you want to create");
    }

    public void printAppointment(Appointment appointment) {
        System.out.println("appointment id: " + appointment.getId() + " " +
                "appointment date: " + appointment.getDate().toString() + " " +
                "appointment assignee: " + appointment.getAssignee() + " " +
                "appointment description: " + appointment.getDescription());
    }

    public void printAppointmentsListPrompt() {
        System.out.println("The values below are your appointments");
    }

    public void printAvailableInstructions() {
        System.out.println("enter \"show\" to see your appointments");
        System.out.println("enter \"create\" to create an appointment");
        System.out.println("enter \"delete\" to delete an appointment");
        System.out.println("enter \"exit\" to exit the system");
    }

    public void printWelcomeMessage() {
        System.out.println("Welcome.\nThis is the appointment management system.");
    }
}
