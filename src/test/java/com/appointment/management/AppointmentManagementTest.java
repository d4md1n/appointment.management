package com.appointment.management;

import com.appointment.management.repository.AppointmentRepository;
import com.appointment.management.repository.ListAppointmentRepository;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AppointmentManagementTest {

    private PrintStream printStream;
    private BufferedReader bufferedReader;
    private Thread thread;

    @Before
    public void setUp() throws IOException {
        final PipedOutputStream testInput = new PipedOutputStream();
        final PipedOutputStream out = new PipedOutputStream();
        final PipedInputStream testOutput = new PipedInputStream(out);
        this.printStream = new PrintStream(testInput);
        this.bufferedReader = new BufferedReader(new InputStreamReader(testOutput));
        AppointmentManagement appointmentManagement = new AppointmentManagement(new PipedInputStream(testInput), new PrintStream(out), new AppointmentService(new ListAppointmentRepository()));
        this.thread = new Thread(appointmentManagement::run);
        thread.start();
    }

    @Test
    public void testFullScenario() throws IOException, InterruptedException {
        //GIVEN
        assertFalse(bufferedReader.ready());
        assertEquals(bufferedReader.readLine(), "Welcome.");
        assertEquals(bufferedReader.readLine(), "This is the appointment management system.");
        checkDefaultPrompt(bufferedReader);
        assertFalse(bufferedReader.ready());
        //WHEN
        printStream.println("create");
        //THEN
        assertFalse(bufferedReader.ready());
        assertEquals(bufferedReader.readLine(), "enter the id of the appointment you want to create");
        printStream.println("12");
        assertEquals(bufferedReader.readLine(), "enter the description of the appointment you want to create");
        printStream.println("my description");
        assertEquals(bufferedReader.readLine(), "enter the assignee of the appointment you want to create");
        printStream.println("John");
        assertEquals(bufferedReader.readLine(), "enter the date in yyyy-MM-dd format of the appointment you want to create");
        printStream.println("2019-11-03");
        checkDefaultPrompt(bufferedReader);
        assertFalse(bufferedReader.ready());
        //WHEN
        printStream.println("show");
        //THEN
        assertEquals(bufferedReader.readLine(), "The values below are your appointments");
        assertEquals(bufferedReader.readLine(), "appointment id: 12 appointment date: 2019-11-03 appointment assignee: John appointment description: my description");
        checkDefaultPrompt(bufferedReader);
        //WHEN
        printStream.println("delete");
        //THEN
        assertEquals(bufferedReader.readLine(), "enter the id of the appointment you want to delete");
        printStream.println("12");
        checkDefaultPrompt(bufferedReader);
        //WHEN
        printStream.println("show");
        //THEN
        assertEquals(bufferedReader.readLine(), "The values below are your appointments");
        checkDefaultPrompt(bufferedReader);
        //WHEN
        printStream.println("exit");
        thread.join();
        //THEN

        assertFalse(thread.isAlive());
    }

    @Test
    public void testInvalidDeleteInputDoesNotDeleteTheZeroIdAppointment() throws IOException, InterruptedException {
        //GIVEN

        assertFalse(bufferedReader.ready());
        assertEquals(bufferedReader.readLine(), "Welcome.");
        assertEquals(bufferedReader.readLine(), "This is the appointment management system.");
        checkDefaultPrompt(bufferedReader);
        assertFalse(bufferedReader.ready());
        //WHEN
        printStream.println("create");
        //THEN
        assertFalse(bufferedReader.ready());
        assertEquals(bufferedReader.readLine(), "enter the id of the appointment you want to create");
        printStream.println("0");
        assertEquals(bufferedReader.readLine(), "enter the description of the appointment you want to create");
        printStream.println("my description");
        assertEquals(bufferedReader.readLine(), "enter the assignee of the appointment you want to create");
        printStream.println("John");
        assertEquals(bufferedReader.readLine(), "enter the date in yyyy-MM-dd format of the appointment you want to create");
        printStream.println("2019-11-03");
        checkDefaultPrompt(bufferedReader);
        assertFalse(bufferedReader.ready());
        //WHEN
        printStream.println("show");
        //THEN
        assertEquals(bufferedReader.readLine(), "The values below are your appointments");
        assertEquals(bufferedReader.readLine(), "appointment id: 0 appointment date: 2019-11-03 appointment assignee: John appointment description: my description");
        checkDefaultPrompt(bufferedReader);
        //WHEN
        printStream.println("delete");
        //THEN
        assertEquals(bufferedReader.readLine(), "enter the id of the appointment you want to delete");
        printStream.println("aaa1");
        checkDefaultPrompt(bufferedReader);
        //WHEN
        printStream.println("show");
        //THEN
        assertEquals(bufferedReader.readLine(), "The values below are your appointments");
        assertEquals(bufferedReader.readLine(), "appointment id: 0 appointment date: 2019-11-03 appointment assignee: John appointment description: my description");
        checkDefaultPrompt(bufferedReader);
        //WHEN
        printStream.println("exit");
        thread.join();
        //THEN
        assertFalse(thread.isAlive());
    }

    private void checkDefaultPrompt(BufferedReader bufferedReader) throws IOException {
        assertEquals(bufferedReader.readLine(), "enter \"show\" to see your appointments");
        assertEquals(bufferedReader.readLine(), "enter \"create\" to create an appointment");
        assertEquals(bufferedReader.readLine(), "enter \"delete\" to delete an appointment");
        assertEquals(bufferedReader.readLine(), "enter \"exit\" to exit the system");
    }
}