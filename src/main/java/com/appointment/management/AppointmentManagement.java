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
    private final Printer printer;
    private final BufferedReader bufferedReader;
    private final List<Appointment> appointmentList;

    public AppointmentManagement(InputStream inputStream, OutputStream outputStream) {
        this.printer = new Printer(outputStream);
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.appointmentList = new ArrayList<>();
    }

    void run() {
        printer.printWelcomeMessage();
        while (true) {
            printer.printAvailableInstructions();
            String value = safeReadLine(bufferedReader).orElse(NON_VALUE);
            if (value.equals(InputOptions.SHOW.value)) {
                showAppointments(printer, appointmentList);
            } else if (value.equals(InputOptions.CREATE.value)) {
                createAppointment(printer, bufferedReader, appointmentList);
            } else if (value.equals(InputOptions.DELETE.value)) {
                deleteAppointment(printer, bufferedReader, appointmentList);
            } else if (value.equals(InputOptions.EXIT.value)) {
                break;
            }
        }
    }

    private void deleteAppointment(Printer printer, BufferedReader bufferedReader, List<Appointment> appointmentList) {
        printer.printAppointmentDeleteIdPrompt();
        Optional<String> appointmentId = safeReadLine(bufferedReader);
        if (appointmentId.isPresent()) {
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
        }
    }

    private void createAppointment(Printer printer, BufferedReader bufferedReader, List<Appointment> appointmentList) {
        Optional<AppointmentDto> optionalAppointmentDto = getAppointmentDto(printer, bufferedReader);
        if (optionalAppointmentDto.isPresent()) {
            Appointment appointment = mapAppointmentDtoToAppointment(optionalAppointmentDto.get());
            if (appointment.getDate().isAfter(LocalDate.now())) {
                appointmentList.add(appointment);
            } else {
                printer.printAppointmentCreationInvalidDateMessage();
            }
        }
    }

    private void showAppointments(Printer printer, List<Appointment> appointmentList) {
        printer.printAppointmentsListPrompt();
        appointmentList.forEach(printer::printAppointment);
    }

    private Appointment mapAppointmentDtoToAppointment(AppointmentDto appointmentDto) {
        LocalDate parsedAppointmentDate = LocalDate.parse(appointmentDto.getDate());
        int parsedAppointmentId = Integer.valueOf(appointmentDto.getId());
        return new Appointment(parsedAppointmentId, appointmentDto.getDescription(), appointmentDto.getAssignee(), parsedAppointmentDate);
    }

    private Optional<AppointmentDto> getAppointmentDto(Printer printer, BufferedReader bufferedReader) {
        printer.printAppointmentCreateIdPrompt();
        Optional<String> appointmentId = safeReadLine(bufferedReader);
        printer.printAppointmentCreateDescriptionPrompt();
        Optional<String> appointmentDescription = safeReadLine(bufferedReader);
        printer.printAppointmentCreateAssigneePrompt();
        Optional<String> appointmentAssignee = safeReadLine(bufferedReader);
        printer.printAppointmentCreateDatePrompt();
        Optional<String> appointmentDate = safeReadLine(bufferedReader);
        if (createFlowDataValidation(appointmentId, appointmentDescription, appointmentAssignee, appointmentDate)) {
            return Optional.of(new AppointmentDto(appointmentId.get(), appointmentDescription.get(), appointmentAssignee.get(), appointmentDate.get()));
        }
        return Optional.empty();
    }

    private boolean createFlowDataValidation(Optional<String> appointmentId, Optional<String> appointmentDescription, Optional<String> appointmentAssignee, Optional<String> appointmentDate) {
        return appointmentDate.isPresent() && appointmentId.isPresent() && appointmentAssignee.isPresent() && appointmentDescription.isPresent();
    }

    private Optional<String> safeReadLine(BufferedReader bufferedReader) {
        try {
            return Optional.ofNullable(bufferedReader.readLine());
        } catch (IOException exception) {
            return Optional.empty();
        }
    }
}
