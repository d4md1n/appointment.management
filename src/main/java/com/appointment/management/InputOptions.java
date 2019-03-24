package com.appointment.management;

public enum InputOptions {
    SHOW ("show"),
    CREATE ("create"),
    DELETE ("delete"),
    EXIT ("exit");

    String value;

    InputOptions(String value){
        this.value = value;
    }
}
