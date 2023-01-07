package com.one.function;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Vendor extends Outsider{
    public static String Name;
    public static String Gender;
    public static String Contact_Number;
    public static String Position = "SecurityGuard";

    public Vendor(String ID){
        super(ID);
        ArrayList<String> temp = new ArrayList<String>();               //Open SecurityGuard text file and extract all of them
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\SecurityGuard_Information.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                temp.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for(String a : temp){
            String[] temp1 = a.split("\\|");
            if(temp1[0].compareTo(Outsider_ID)==0){
                Name = temp1[1];
                Gender = temp1[2];
                Contact_Number = temp1[3];
            }
        }

    }
    public static String getID(){
        return Outsider_ID;
    }
    public static String getName(){
        return Name;
    }
    public static String getGender(){
        return Gender;
    }
    public static String getContact_Number(){
        return Contact_Number;
    }
    public static void Change_Profile_Name(String New_Name){
        ArrayList<String> Old_temp = new ArrayList<String>();               //Open SecurityGuard text file and extract all of them
        ArrayList<String> New_temp = new ArrayList<String>();
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\SecurityGuard_Information.txt");
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
        for(String a : Old_temp){
            String[] temp1 = a.split("\\|");
            if(temp1[0].compareTo(Outsider_ID)==0){
                temp1[1] = New_Name;
            }

            New_temp.add(temp1[0]+"|"+temp1[1]+"|"+temp1[2]+"|"+temp1[3]+"\n");
        }

        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\SecurityGuard_Information.txt");
            for(String a : New_temp) {
                myWriter.write(a);
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void Change_Profile_Gender(){
        ArrayList<String> Old_temp = new ArrayList<String>();               //Open SecurityGuard text file and extract all of them
        ArrayList<String> New_temp = new ArrayList<String>();
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\SecurityGuard_Information.txt");
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
        for(String a : Old_temp){
            String[] temp1 = a.split("\\|");
            if(temp1[0].compareTo(Outsider_ID)==0){
                if(temp1[2].compareTo("Male")==0){
                    temp1[2] = "Female";
                }else{
                    temp1[2] = "Male";
                }
            }
            New_temp.add(temp1[0]+"|"+temp1[1]+"|"+temp1[2]+"|"+temp1[3]+"\n");
        }

        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\SecurityGuard_Information.txt");
            for(String a : New_temp) {
                myWriter.write(a);
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void Change_Profile_Contact_Number(String New_Contact_Number){
        ArrayList<String> Old_temp = new ArrayList<String>();               //Open SecurityGuard text file and extract all of them
        ArrayList<String> New_temp = new ArrayList<String>();
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Vendor_Information.txt");
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
        for(String a : Old_temp){
            String[] temp1 = a.split("\\|");

            if(temp1[0].compareTo(Outsider_ID)==0){
                temp1[3] = New_Contact_Number;
            }

            New_temp.add(temp1[0]+"|"+temp1[1]+"|"+temp1[2]+"|"+temp1[3]+"\n");
        }

        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Vendor_Information.txt");
            for(String a : New_temp) {
                myWriter.write(a);
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static ArrayList<String []> Profile_View(){
        ArrayList<String> Old_temp = new ArrayList<String>();
        String pathname = "JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Resident_Information.txt";

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
    //payment

    //complaint
    public static void Complaint_Log(String Description){
        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Complaint.txt",true);
            myWriter.write(Outsider_ID +  "|" + Description + "|Unread\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void Complaint_Delete(int number){
        ArrayList<String> Old_temp = new ArrayList<String>();               //Open SecurityGuard text file and extract all of them
        ArrayList<String> New_temp1 = new ArrayList<String>();
        ArrayList<String> New_temp2 = new ArrayList<String>();
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
        for (String a : Old_temp) {
            String[] temp1 = a.split("\\|");
            if (temp1[0].compareTo(Outsider_ID)==0) {
                New_temp1.add(a);
            }else{
                New_temp2.add(a);
            }
        }

        New_temp1.remove(number);

        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Complaint.txt");
            for (String a2 : New_temp2) {
                myWriter.write(a2+"\n");
            }
            for (String a1 : New_temp1) {
                myWriter.write(a1+"\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static ArrayList<String []> Complaint_View(){
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
