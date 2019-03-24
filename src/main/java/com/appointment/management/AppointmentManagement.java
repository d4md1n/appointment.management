package com.appointment.management;

import com.appointment.management.domain.Appointment;
import com.appointment.management.domain.InputOptions;
import com.appointment.management.repository.AppointmentRepository;
import com.appointment.management.repository.ListAppointmentRepository;

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
    private final ListAppointmentRepository appointmentRepository = new ListAppointmentRepository();

    public AppointmentManagement(InputStream inputStream, OutputStream outputStream) {
        this.printer = new Printer(outputStream);
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    void run() {
        printer.printWelcomeMessage();
        while (true) {
            printer.printAvailableInstructions();
            String value = safeReadLine(bufferedReader).orElse(NON_VALUE);
            if (value.equals(InputOptions.SHOW.getValue())) {
                showAppointments(printer, appointmentRepository);
            } else if (value.equals(InputOptions.CREATE.getValue())) {
                createAppointment(printer, bufferedReader, appointmentRepository);
            } else if (value.equals(InputOptions.DELETE.getValue())) {
                deleteAppointment(printer, bufferedReader, appointmentRepository);
            } else if (value.equals(InputOptions.EXIT.getValue())) {
                break;
            }
        }
    }

    private void deleteAppointment(Printer printer, BufferedReader bufferedReader, AppointmentRepository appointmentRepository) {
        printer.printAppointmentDeleteIdPrompt();
        Optional<String> appointmentId = safeReadLine(bufferedReader);
        if (appointmentId.isPresent()) {
            try {
                int parsedAppointmentId = Integer.valueOf(appointmentId.get());
                appointmentRepository.remove(parsedAppointmentId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void createAppointment(Printer printer, BufferedReader bufferedReader, AppointmentRepository appointmentRepository) {
        Optional<AppointmentDto> optionalAppointmentDto = getAppointmentDto(printer, bufferedReader);
        if (optionalAppointmentDto.isPresent()) {
            Appointment appointment = mapAppointmentDtoToAppointment(optionalAppointmentDto.get());
            if (appointment.getDate().isAfter(LocalDate.now())) {
                appointmentRepository.add(appointment);
            } else {
                printer.printAppointmentCreationInvalidDateMessage();
            }
        }
    }

    private void showAppointments(Printer printer, AppointmentRepository appointmentList) {
        printer.printAppointmentsListPrompt();
        appointmentList.list().forEach(printer::printAppointment);
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
