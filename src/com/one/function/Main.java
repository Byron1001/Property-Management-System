package com.one.function;
import javax.swing.text.View;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
public class Main {
    public static void main(String[] args) {
        Admin_Executive a = new Admin_Executive("AE0001");
        ArrayList<String[]> answer = a.Facility_Booking_Management_View();




    }
}

