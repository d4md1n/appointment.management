package com.appointment.management;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class FunctionalE2ETest {

    @Test
    public void testFullScenario() throws IOException, InterruptedException {
        //GIVEN
        final PipedOutputStream testInput = new PipedOutputStream();
        final PipedOutputStream out = new PipedOutputStream();
        final PipedInputStream testOutput = new PipedInputStream(out);
        System.setIn(new PipedInputStream(testInput));
        System.setOut(new PrintStream(out));
        PrintStream testPrint = new PrintStream(testInput);
        BufferedReader testReader = new BufferedReader(new InputStreamReader(testOutput));

        Thread thread = new Thread(() -> Application.main(new String[]{}));
        thread.start();

        assertFalse(testReader.ready());
        assertEquals(testReader.readLine(), "Welcome.");
        assertEquals(testReader.readLine(), "This is the appointment management system.");
        checkDefaultPrompt(testReader);
        assertFalse(testReader.ready());
        //WHEN
        testPrint.println("create");
        //THEN
        assertFalse(testReader.ready());
        assertEquals(testReader.readLine(), "enter the id of the appointment you want to create");
        testPrint.println("12");
        assertEquals(testReader.readLine(), "enter the description of the appointment you want to create");
        testPrint.println("my description");
        assertEquals(testReader.readLine(), "enter the assignee of the appointment you want to create");
        testPrint.println("John");
        assertEquals(testReader.readLine(), "enter the date in yyyy-MM-dd format of the appointment you want to create");
        testPrint.println("2019-11-03");
        checkDefaultPrompt(testReader);
        assertFalse(testReader.ready());
        //WHEN
        testPrint.println("show");
        //THEN
        assertEquals(testReader.readLine(), "The values below are your appointments");
        assertEquals(testReader.readLine(), "appointment id: 12 appointment date: 2019-11-03 appointment assignee: John appointment description: my description");
        checkDefaultPrompt(testReader);
        //WHEN
        testPrint.println("delete");
        //THEN
        assertEquals(testReader.readLine(), "enter the id of the appointment you want to delete");
        testPrint.println("12");
        checkDefaultPrompt(testReader);
        //WHEN
        testPrint.println("show");
        //THEN
        assertEquals(testReader.readLine(), "The values below are your appointments");
        checkDefaultPrompt(testReader);
        //WHEN
        testPrint.println("exit");
        thread.join();
        //THEN

        assertFalse(thread.isAlive());
    }

    @Test
    public void testInvalidDeleteInputDoesNotDeleteTheZeroIdAppointment() throws IOException, InterruptedException {
        //GIVEN
        final PipedOutputStream testInput = new PipedOutputStream();
        final PipedOutputStream out = new PipedOutputStream();
        final PipedInputStream testOutput = new PipedInputStream(out);
        System.setIn(new PipedInputStream(testInput));
        System.setOut(new PrintStream(out));
        PrintStream testPrint = new PrintStream(testInput);
        BufferedReader testReader = new BufferedReader(new InputStreamReader(testOutput));

        Thread thread = new Thread(() -> Application.main(new String[]{}));
        thread.start();

        assertFalse(testReader.ready());
        assertEquals(testReader.readLine(), "Welcome.");
        assertEquals(testReader.readLine(), "This is the appointment management system.");
        checkDefaultPrompt(testReader);
        assertFalse(testReader.ready());
        //WHEN
        testPrint.println("create");
        //THEN
        assertFalse(testReader.ready());
        assertEquals(testReader.readLine(), "enter the id of the appointment you want to create");
        testPrint.println("0");
        assertEquals(testReader.readLine(), "enter the description of the appointment you want to create");
        testPrint.println("my description");
        assertEquals(testReader.readLine(), "enter the assignee of the appointment you want to create");
        testPrint.println("John");
        assertEquals(testReader.readLine(), "enter the date in yyyy-MM-dd format of the appointment you want to create");
        testPrint.println("2019-11-03");
        checkDefaultPrompt(testReader);
        assertFalse(testReader.ready());
        //WHEN
        testPrint.println("show");
        //THEN
        assertEquals(testReader.readLine(), "The values below are your appointments");
        assertEquals(testReader.readLine(), "appointment id: 0 appointment date: 2019-11-03 appointment assignee: John appointment description: my description");
        checkDefaultPrompt(testReader);
        //WHEN
        testPrint.println("delete");
        //THEN
        assertEquals(testReader.readLine(), "enter the id of the appointment you want to delete");
        testPrint.println("aaa1");
        checkDefaultPrompt(testReader);
        //WHEN
        testPrint.println("show");
        //THEN
        assertEquals(testReader.readLine(), "The values below are your appointments");
        assertEquals(testReader.readLine(), "appointment id: 0 appointment date: 2019-11-03 appointment assignee: John appointment description: my description");
        checkDefaultPrompt(testReader);
        //WHEN
        testPrint.println("exit");
        thread.join();
        //THEN
        assertFalse(thread.isAlive());
    }

    private void checkDefaultPrompt(BufferedReader testReader) throws IOException {
        assertEquals(testReader.readLine(), "enter \"show\" to see your appointments");
        assertEquals(testReader.readLine(), "enter \"create\" to create an appointment");
        assertEquals(testReader.readLine(), "enter \"delete\" to delete an appointment");
        assertEquals(testReader.readLine(), "enter \"exit\" to exit the system");
    }
}