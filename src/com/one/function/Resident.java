package com.one.function;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Resident extends Outsider {
    public static String Name;
    public static String Gender;
    public static String Contact_Number;
    public static String Position = "SecurityGuard";

    public Resident(String ID) {
        super(ID);
        ArrayList<String> temp = new ArrayList<String>();               //Open SecurityGuard text file and extract all of them
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Resident_Information.txt");
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
        for (String a : temp) {
            String[] temp1 = a.split("\\|");
            if (temp1[0].compareTo(Outsider_ID) == 0) {
                Name = temp1[1];
                Gender = temp1[2];
                Contact_Number = temp1[3];
            }
        }

    }

    public static String getID() {
        return Outsider_ID;
    }

    public static String getName() {
        return Name;
    }

    public static String getGender() {
        return Gender;
    }

    public static String getContact_Number() {
        return Contact_Number;
    }
    public static void Change_Profile_Name(String New_Name) {
        ArrayList<String> Old_temp = new ArrayList<String>();               //Open SecurityGuard text file and extract all of them
        ArrayList<String> New_temp = new ArrayList<String>();
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Resident_Information.txt");
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

            if (temp1[0].compareTo(Outsider_ID) == 0) {
                temp1[1] = New_Name;
            }

            New_temp.add(temp1[0] + "|" + temp1[1] + "|" + temp1[2] + "|" + temp1[3] + "\n");
        }

        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Resident_Information.txt");
            for (String a : New_temp) {
                myWriter.write(a);
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void Change_Profile_Gender() {
        ArrayList<String> Old_temp = new ArrayList<String>();               //Open SecurityGuard text file and extract all of them
        ArrayList<String> New_temp = new ArrayList<String>();
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Resident_Information.txt");
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
            if (temp1[0].compareTo(Outsider_ID) == 0) {
                if (temp1[2].compareTo("Male") == 0) {
                    temp1[2] = "Female";
                } else {
                    temp1[2] = "Male";
                }
            }

            New_temp.add(temp1[0] + "|" + temp1[1] + "|" + temp1[2] + "|" + temp1[3] + "\n");
        }

        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Vendor_Information.txt");
            for (String a : New_temp) {
                myWriter.write(a);
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void Change_Profile_Contact_Number(String New_Contact_Number) {
        ArrayList<String> Old_temp = new ArrayList<String>();               //Open SecurityGuard text file and extract all of them
        ArrayList<String> New_temp = new ArrayList<String>();
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Unit_Information.txt");
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

            if (temp1[0].compareTo(Outsider_ID) == 0) {
                temp1[3] = New_Contact_Number;
            }

            New_temp.add(temp1[0] + "|" + temp1[1] + "|" + temp1[2] + "|" + temp1[3] + "\n");
        }

        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Vendor_Information.txt");
            for (String a : New_temp) {
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
    //payment history,pending fee,statement,invoice,receipt
    public static void make_payment() {
        ArrayList<String> Old_temp = new ArrayList<String>();               //Open SecurityGuard text file and extract all of them
        ArrayList<String> New_temp = new ArrayList<String>();
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Unit_Information.txt");
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
            if (temp1[9].compareTo(Outsider_ID) == 0) {
                temp1[11] = "1";
                //New_temp.add(temp1[0] + "|" + temp1[1] + "|" + temp1[2] + "|" + temp1[3] + temp1[4] + "|" + temp1[5] + "|" + temp1[6] + "|" + temp1[7] + "|" + temp1[8] + "|" + temp1[9] + "|" + temp1[10] + "|" + temp1[11] + "\n");
            }
            New_temp.add(temp1[0] + "|" + temp1[1] + "|" + temp1[2] + "|" + temp1[3] + "|" + temp1[4] + "|" + temp1[5] + "|" + temp1[6] + "|" + temp1[7] + "|" + temp1[8] + "|" + temp1[9] + "|" + temp1[10] + "|" + temp1[11] + "\n");


        }

            try {
                FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Unit_Information.txt");
                for (String a1 : New_temp) {
                    myWriter.write(a1);
                }
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

    }
    public static void make_deposit(){
        ArrayList<String> Old_temp = new ArrayList<String>();               //Open SecurityGuard text file and extract all of them
        ArrayList<String> New_temp = new ArrayList<String>();
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Unit_Information.txt");
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
            if (temp1[9].compareTo(Outsider_ID) == 0) {
                temp1[10] = "1";
                //New_temp.add(temp1[0] + "|" + temp1[1] + "|" + temp1[2] + "|" + temp1[3] + temp1[4] + "|" + temp1[5] + "|" + temp1[6] + "|" + temp1[7] + "|" + temp1[8] + "|" + temp1[9] + "|" + temp1[10] + "|" + temp1[11] + "\n");
            }
            New_temp.add(temp1[0] + "|" + temp1[1] + "|" + temp1[2] + "|" + temp1[3] + "|" + temp1[4] + "|" + temp1[5] + "|" + temp1[6] + "|" + temp1[7] + "|" + temp1[8] + "|" + temp1[9] + "|" + temp1[10] + "|" + temp1[11] + "\n");


        }

        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Unit_Information.txt");
            for (String a1 : New_temp) {
                myWriter.write(a1);
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static ArrayList<String []> Payment_History_View(){
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
    public static ArrayList<String []> Pending_Fee_View(){
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
    public static ArrayList<String []> Statement_View(){
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
    public static ArrayList<String []> Invoice_View(){
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
    public static ArrayList<String []> Receipt_View(){
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
    //facility booking management
    public static void Facility_Booking_Management_Add(String Facility, int Unit, int date){
        ArrayList<String> Old_temp = new ArrayList<String>();               //Open SecurityGuard text file and extract all of them
        ArrayList<String> New_temp = new ArrayList<String>();
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Facility_Information.txt");
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
            if (temp1[0].compareTo(Facility) == 0 &&temp1[1].compareTo(String.valueOf(Unit)) == 0) {
                temp1[date+1] = Outsider_ID;
            }
            New_temp.add(temp1[0] + "|" + temp1[1] + "|" + temp1[2] + "|" + temp1[3] + "|" + temp1[4] + "|" + temp1[5] + "\n");
        }

        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Facility_Information.txt");
            for (String a1 : New_temp) {
                myWriter.write(a1);
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void Facility_Booking_Management_Cancel(String Facility, int Unit, int date){
        ArrayList<String> Old_temp = new ArrayList<String>();               //Open SecurityGuard text file and extract all of them
        ArrayList<String> New_temp = new ArrayList<String>();
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Facility_Information.txt");
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
            if (temp1[0].compareTo(Facility) == 0 &&temp1[1].compareTo(String.valueOf(Unit)) == 0) {
                temp1[date+1] = "-";
            }
            New_temp.add(temp1[0] + "|" + temp1[1] + "|" + temp1[2] + "|" + temp1[3] + "|" + temp1[4] + "|" + temp1[5] + "\n");
        }

        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Facility_Information.txt");
            for (String a1 : New_temp) {
                myWriter.write(a1);
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static ArrayList<String []> Facility_Booking_Management_View(){
        ArrayList<String> Old_temp = new ArrayList<String>();
        String pathname = "JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Facility_Information.txt";

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
    //visitor pass
    public static void visitor_pass_add(String begindate, String endate){
        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Visitors.txt",true);
            myWriter.write( Outsider_ID +  "|" + Name + "|" + Contact_Number + "|" + begindate + "|" + endate +"\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void visitor_pass_cancel(String begindate){
        ArrayList<String> Old_temp = new ArrayList<String>();               //Open SecurityGuard text file and extract all of them
        ArrayList<String> New_temp = new ArrayList<String>();
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Visitors.txt");
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
            if (temp1[0].compareTo(Outsider_ID) == 0 &&temp1[3].compareTo(begindate) == 0) {

            }else {
                New_temp.add(temp1[0] + "|" + temp1[1] + "|" + temp1[2] + "|" + temp1[3] + "|" + temp1[4] + "|" + temp1[5] + "\n");
            }
        }

        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Visitors.txt");
            for (String a1 : New_temp) {
                myWriter.write(a1);
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static ArrayList<String []> visitor_pass_View(){
        ArrayList<String> Old_temp = new ArrayList<String>();
        String pathname = "JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Visitors.txt";

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
