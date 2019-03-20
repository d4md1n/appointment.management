package com.appointment.management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppointmentManagement {

    public static final String NON_VALUE = "nonValue";

    void run() {
        List<Appointment> appointmentList = new ArrayList<>();
        Printer printer = new Printer();
        printer.printWelcomeMessage();
        while (true) {
            printer.printAvailableInstructions();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            Optional<String> value = safeReadLine(bufferedReader);

            if ("show".equals(value.orElse(NON_VALUE))) {
                printer.printAppointmentsListPrompt();
                appointmentList.forEach(printer::printAppointment);
            } else if ("create".equals(value.orElse(NON_VALUE))) {
                Appointment appointment = new Appointment();
                printer.printAppointmentCreateIdPrompt();
                Optional<String> appointmentId = safeReadLine(bufferedReader);
                printer.printAppointmentCreateDescriptionPrompt();
                Optional<String> appointmentDescription = safeReadLine(bufferedReader);
                printer.printAppointmentCreateAssigneePrompt();
                Optional<String> appointmentAssignee = safeReadLine(bufferedReader);
                printer.printAppointmentCreateDatePrompt();
                Optional<String> appointmentDate = safeReadLine(bufferedReader);

                if(!appointmentDate.isPresent()
                        || !appointmentId.isPresent()
                        || !appointmentAssignee.isPresent()
                        || !appointmentDescription.isPresent()) {
                    break;
                }

                LocalDate parsedAppointmentDate = LocalDate.parse(appointmentDate.get());
                int parsedAppointmentId = Integer.valueOf(appointmentId.get());

                appointment.setDate(parsedAppointmentDate);
                appointment.setAssignee(appointmentAssignee.get());
                appointment.setDescription(appointmentDescription.get());
                appointment.setId(parsedAppointmentId);

                if (appointment.getDate().isBefore(LocalDate.now())) {
                    printer.printAppointmentCreationInvalidDateMessage();
                    continue;
                }

                appointmentList.add(appointment);
            } else if ("delete".equals(value.orElse(NON_VALUE))) {
                printer.printAppointmentDeleteIdPrompt();

                Optional<String> appointmentId = safeReadLine(bufferedReader);
                if(!appointmentId.isPresent()) {
                    continue;
                }


                try {
                    int parsedAppointmentId = Integer.valueOf(appointmentId.get());
                    for (int i = 0; i < appointmentList.size(); i++) {
                        if (appointmentList.get(i).getId() == parsedAppointmentId) {
                            appointmentList.remove(i);
                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


            } else if ("exit".equals(value.orElse(NON_VALUE))) {
                break;
            }
        }
    }

    private Optional<String> safeReadLine(BufferedReader bufferedReader) {
        try {
            return Optional.ofNullable(bufferedReader.readLine());
        } catch (IOException exception) {
            return Optional.empty();
        }
    }
}
