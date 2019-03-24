package com.appointment.management.domain;

public enum InputOptions {
    SHOW ("show"),
    CREATE ("create"),
    DELETE ("delete"),
    EXIT ("exit");

    private String value;

    private InputOptions(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
