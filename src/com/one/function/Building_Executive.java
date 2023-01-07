package com.one.function;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Building_Executive extends Executive{
    public static String Name;
    public static String Building_Executive_ID;
    public static String Position = "Admin_Executive";

    public Building_Executive(String Executive_ID){
        super(Executive_ID);
        this.Building_Executive_ID = super.ExecutiveID;
    }
    // Unit Management System start from here
    public static void Employee_Management_Assign_Job(String EmployeeID,String Task){
        ArrayList<String> Old_temp = new ArrayList<String>();
        ArrayList<String> New_temp = new ArrayList<String>();
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Employee_Task.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Old_temp.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for (String a : Old_temp) {
            String[] temp1 = a.split("\\|");
            if (temp1[0].compareTo(EmployeeID)==0) {
                New_temp.add(a+ Task + "|");
            }else {
                New_temp.add(a);
            }
        }
        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Employee_Task.txt");
            for (String a1 : New_temp) {
                myWriter.write(a1+"\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void Employee_Management_Delete_Job(String EmployeeID,int number){
        ArrayList<String> Old_temp = new ArrayList<String>();
        ArrayList<String> New_temp = new ArrayList<String>();
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Employee_Task.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Old_temp.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for (String a : Old_temp) {
            String[] temp1 = a.split("\\|");
            if (temp1[0].compareTo(EmployeeID)==0) {
                String new_temp = "";
                for(int i=0;i<temp1.length;i++){
                    if(i!=(number+1)){
                        new_temp = new_temp + temp1[i] +"|";
                    }
                }
                New_temp.add(new_temp);

            }else {
                New_temp.add(a);
            }
        }
        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Employee_Task.txt");
            for (String a1 : New_temp) {
                myWriter.write(a1+"\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static ArrayList<String[]> Employee_Management_View(){
        ArrayList<String> Old_temp = new ArrayList<String>();
        String pathname = "JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Employee_Task.txt";
        try {
            File myObj = new File(pathname);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Old_temp.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        int feature_number = Old_temp.get(0).split("\\|").length;
        ArrayList<String[]> answer = new ArrayList<>();

        for (String a : Old_temp) {
            String[] temp1 = a.split("\\|");
            answer.add(temp1);
        }
        return answer;
    }
    public static void Complaint_Management_Update(int number , String new_status){
        ArrayList<String> Old_temp = new ArrayList<String>();
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Complaint.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Old_temp.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        String a = Old_temp.get(number-1);
        String[] temp1 = a.split("\\|");
        temp1[2] = new_status;
        Old_temp.set(number-1, temp1[0] + "|" + temp1[1] + "|" + temp1[2] + "");
        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Complaint.txt");
            for (String a1 : Old_temp) {
                myWriter.write(a1+"\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static ArrayList<String []> Complaint_Management_View(){
        ArrayList<String> Old_temp = new ArrayList<String>();
        String pathname = "JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Employee_Task.txt";

        try {
            File myObj = new File(pathname);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Old_temp.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        int feature_number = Old_temp.get(0).split("\\|").length;
        ArrayList<String[]> answer = new ArrayList<>();

        for (String a : Old_temp) {
            String[] temp1 = a.split("\\|");
            answer.add(temp1);
        }
        return answer;
    }
}
