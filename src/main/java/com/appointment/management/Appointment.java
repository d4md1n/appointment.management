package com.appointment.management;

import java.time.LocalDate;

class Appointment {
    private final int id;
    private final String description;
    private final String assignee;
    private final LocalDate date;

    public Appointment(int id, String description, String assignee, LocalDate date) {
        this.id = id;
        this.description = description;
        this.assignee = assignee;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getAssignee() {
        return assignee;
    }

    public LocalDate getDate() {
        return date;
    }

}
