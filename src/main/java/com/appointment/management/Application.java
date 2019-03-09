package com.appointment.management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        List<Ap> aps = new ArrayList<>();
        boolean done = false;
        System.out.println("Welcome.\nThis is the appointment management system.");
        while (!done) {
            System.out.println("enter \"show\" to see your appointments");
            System.out.println("enter \"create\" to create an appointment");
            System.out.println("enter \"delete\" to delete an appointment");
            System.out.println("enter \"exit\" to exit the system");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String value = null;
            try {
                value = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (value != null && value.equals("show")) {
                System.out.println("The values below are your appointments");
                for (int i = 0; i < aps.size(); i++) {
                    System.out.println("appointment id: " + aps.get(i).id + " " +
                            "appointment date: " + aps.get(i).dt.toString() + " " +
                            "appointment assignee: " + aps.get(i).assig + " " +
                            "appointment description: " + aps.get(i).desc);
                }
            } else if (value != null && value.equals("create")) {
                Ap ap = new Ap();
                aps.add(ap);
                System.out.println("enter the id of the appointment you want to create");
                try {
                    ap.id = Integer.valueOf(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("enter the description of the appointment you want to create");
                try {
                    ap.desc = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("enter the assignee of the appointment you want to create");
                try {
                    ap.assig = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("enter the date in yyyy-MM-dd format of the appointment you want to create");
                try {
                    ap.dt = LocalDate.parse(br.readLine());
                    if(ap.dt.isBefore(LocalDate.now())) {
                        System.out.println("Appointment creation failed: appointment can not be in the past");
                        aps.remove(ap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (value != null && value.equals("delete")) {
                System.out.println("enter the id of the appointment you want to delete");
                int idint = 0;
                try {
                    idint = Integer.valueOf(br.readLine());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < aps.size(); i++) {
                    if (aps.get(i).id == idint) {
                        aps.remove(i);
                    }
                }
            } else if (value != null && value.equals("exit")) {
                done = true;
            }
        }

    }

}

class Ap {
    int id;
    String desc;
    String assig;
    LocalDate dt;
}