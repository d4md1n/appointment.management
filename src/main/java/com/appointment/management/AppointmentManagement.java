package com.appointment.management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppointmentManagement {

    private static final String NON_VALUE = "nonValue";

    void run(InputStream inputStream, OutputStream outputStream) {
        Printer printer = new Printer(outputStream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        List<Appointment> appointmentList = new ArrayList<>();
        printer.printWelcomeMessage();
        while (true) {
            printer.printAvailableInstructions();
            String value = safeReadLine(bufferedReader).orElse(NON_VALUE);

            if (value.equals(InputOptions.SHOW.value)) {
                printer.printAppointmentsListPrompt();
                appointmentList.forEach(printer::printAppointment);
            } else if (value.equals(InputOptions.CREATE.value)) {
                //create flow
                printer.printAppointmentCreateIdPrompt();
                Optional<String> appointmentId = safeReadLine(bufferedReader);
                printer.printAppointmentCreateDescriptionPrompt();
                Optional<String> appointmentDescription = safeReadLine(bufferedReader);
                printer.printAppointmentCreateAssigneePrompt();
                Optional<String> appointmentAssignee = safeReadLine(bufferedReader);
                printer.printAppointmentCreateDatePrompt();
                Optional<String> appointmentDate = safeReadLine(bufferedReader);
                //end of create flow

                if (createFlowValidationFails(appointmentId, appointmentDescription, appointmentAssignee, appointmentDate)) {
                    break;
                }
                //Parse necessary input
                LocalDate parsedAppointmentDate = LocalDate.parse(appointmentDate.get());
                int parsedAppointmentId = Integer.valueOf(appointmentId.get());
                //enf of parsing
                if (appointmentDateValidation(printer, parsedAppointmentDate)) {
                    continue;
                }

                Appointment appointment = createAppointment(appointmentDescription, appointmentAssignee, parsedAppointmentDate, parsedAppointmentId);
                appointmentList.add(appointment);
            } else if (value.equals(InputOptions.DELETE.value)) {
                printer.printAppointmentDeleteIdPrompt();
                Optional<String> appointmentId = safeReadLine(bufferedReader);

                if (!appointmentId.isPresent()) {
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


            } else if (value.equals(InputOptions.EXIT.value)) {
                break;
            }
        }
    }

    private Appointment createAppointment(Optional<String> appointmentDescription, Optional<String> appointmentAssignee, LocalDate parsedAppointmentDate, int parsedAppointmentId) {
        return new Appointment(parsedAppointmentId, appointmentDescription.get(), appointmentAssignee.get(), parsedAppointmentDate);
    }

    private boolean appointmentDateValidation(Printer printer, LocalDate parsedAppointmentDate) {
        if (parsedAppointmentDate.isBefore(LocalDate.now())) {
            printer.printAppointmentCreationInvalidDateMessage();
            return true;
        }
        return false;
    }

    private boolean createFlowValidationFails(Optional<String> appointmentId, Optional<String> appointmentDescription, Optional<String> appointmentAssignee, Optional<String> appointmentDate) {
        return !appointmentDate.isPresent()
                || !appointmentId.isPresent()
                || !appointmentAssignee.isPresent()
                || !appointmentDescription.isPresent();
    }

    private Optional<String> safeReadLine(BufferedReader bufferedReader) {
        try {
            return Optional.ofNullable(bufferedReader.readLine());
        } catch (IOException exception) {
            return Optional.empty();
        }
    }
}
