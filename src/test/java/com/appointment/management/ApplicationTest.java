package com.appointment.management;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.Test;

public class ApplicationTest {

    @Test
    public void testShowFunctionality() throws IOException {
        //GIVEN
        final PipedOutputStream testInput = new PipedOutputStream();
        final PipedOutputStream out = new PipedOutputStream();
        final PipedInputStream testOutput = new PipedInputStream(out);
        System.setIn(new PipedInputStream(testInput));
        System.setOut(new PrintStream(out));
        PrintStream testPrint = new PrintStream(testInput);
        BufferedReader testReader = new BufferedReader(new InputStreamReader(testOutput));

        new Thread(() -> Application.main(new String[]{})).start();

        assertFalse(testReader.ready());
        assertEquals(testReader.readLine(), "Welcome.");
        assertEquals(testReader.readLine(), "This is the appointment management system.");
        checkDefaultPrompt(testReader);
        assertFalse(testReader.ready());
        //WHEN
        testPrint.println("show");
        //THEN
        assertFalse(testReader.ready());
        assertEquals(testReader.readLine(), "The values below are your appointments");
        checkDefaultPrompt(testReader);
        assertFalse(testReader.ready());
        testPrint.println("exit");
    }

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
        testPrint.println("show");
        assertEquals(testReader.readLine(), "The values below are your appointments");
        assertEquals(testReader.readLine(), "appointment id: 12 appointment date: 2019-11-03 appointment assignee: John appointment description: my description");

        checkDefaultPrompt(testReader);
        testPrint.println("delete");
        assertEquals(testReader.readLine(), "enter the id of the appointment you want to delete");
        testPrint.println("12");
        checkDefaultPrompt(testReader);
        testPrint.println("show");
        assertEquals(testReader.readLine(), "The values below are your appointments");
        checkDefaultPrompt(testReader);
        testPrint.println("exit");
        thread.join();
        assertEquals(thread.isAlive(), false);
    }

    private void checkDefaultPrompt(BufferedReader testReader) throws IOException {
        assertEquals(testReader.readLine(), "enter \"show\" to see your appointments");
        assertEquals(testReader.readLine(), "enter \"create\" to create an appointment");
        assertEquals(testReader.readLine(), "enter \"delete\" to delete an appointment");
        assertEquals(testReader.readLine(), "enter \"exit\" to exit the system");
    }
}