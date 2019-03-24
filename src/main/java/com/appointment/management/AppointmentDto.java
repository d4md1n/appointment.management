package com.appointment.management;

class AppointmentDto {
    private final String id;
    private final String description;
    private final String assignee;
    private final String date;

    public AppointmentDto(String id, String description, String assignee, String date) {
        this.id = id;
        this.description = description;
        this.assignee = assignee;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getDate() {
        return date;
    }
}
