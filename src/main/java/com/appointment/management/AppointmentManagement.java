package com.appointment.management;

import com.appointment.management.domain.Appointment;
import com.appointment.management.domain.InputOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Optional;

public class AppointmentManagement {

    private static final String NON_VALUE = "nonValue";
    private final Printer printer;
    private final BufferedReader bufferedReader;
    private final AppointmentService appointmentService;


    public AppointmentManagement(InputStream inputStream, OutputStream outputStream, AppointmentService appointmentService) {
        this.printer = new Printer(outputStream);
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.appointmentService = appointmentService;
    }

    void run() {
        printer.printWelcomeMessage();
        while (true) {
            printer.printAvailableInstructions();
            String value = safeReadLine(bufferedReader).orElse(NON_VALUE);
            if (value.equals(InputOptions.SHOW.getValue())) {
                showAppointments();
            } else if (value.equals(InputOptions.CREATE.getValue())) {
                createAppointment();
            } else if (value.equals(InputOptions.DELETE.getValue())) {
                deleteAppointment();
            } else if (value.equals(InputOptions.EXIT.getValue())) {
                break;
            }
        }
    }

    private void deleteAppointment() {
        Optional<Integer> parsedAppointmentId = getParsedAppointmentIdToDelete(printer, bufferedReader);
        if (parsedAppointmentId.isPresent()) {
            appointmentService.deleteAppointment(parsedAppointmentId.get());
        }
    }

    private Optional<Integer> getParsedAppointmentIdToDelete(Printer printer, BufferedReader bufferedReader) {
        printer.printAppointmentDeleteIdPrompt();
        Optional<String> appointmentId = safeReadLine(bufferedReader);
        Integer parsedAppointmentId = null;
        if (appointmentId.isPresent()) {
            try {
                parsedAppointmentId = Integer.valueOf(appointmentId.get());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }
        return Optional.ofNullable(parsedAppointmentId);
    }

    private void createAppointment() {
        Optional<AppointmentDto> optionalAppointmentDto = getAppointmentDto(printer, bufferedReader);
        if (optionalAppointmentDto.isPresent()) {
            Optional<Appointment> appointment = mapAppointmentDtoToAppointment(optionalAppointmentDto.get());
            if (appointment.isPresent()){
                boolean successfulCreation = appointmentService.createAppointment(appointment.get());
                if (!successfulCreation) {
                    printer.printAppointmentCreationInvalidDateMessage();
                }
            }
        }
    }

    private void showAppointments() {
        printer.printAppointmentsListPrompt();
        appointmentService.getAllAppointments().forEach(printer::printAppointment);
    }

    private Optional<Appointment> mapAppointmentDtoToAppointment(AppointmentDto appointmentDto) {
        LocalDate parsedAppointmentDate;
        int parsedAppointmentId;
        try{
            parsedAppointmentDate = LocalDate.parse(appointmentDto.getDate());
            parsedAppointmentId = Integer.valueOf(appointmentDto.getId());
        }catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(new Appointment(parsedAppointmentId, appointmentDto.getDescription(), appointmentDto.getAssignee(), parsedAppointmentDate));
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
