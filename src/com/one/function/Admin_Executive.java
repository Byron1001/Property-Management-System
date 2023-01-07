package com.one.function;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin_Executive extends Executive{
    public static String Name;
    public static String Admin_Executive_ID;
    public static String Position = "Admin_Executive";

    public Admin_Executive(String Executive_ID){
        super(Executive_ID);
        this.Admin_Executive_ID = super.ExecutiveID;
    }
    // Unit Management System start from here
    public static void Unit_Management_Add(String Floor, String Location, String bedroom, String bathroom,String parking,String price, String Deposit){
        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Unit_Information.txt",true);
            //String temp = ";
            myWriter.write( Floor+"|"+Location+"|"+bedroom+"|"+bathroom+"|"+parking+"|"+
                    "for rent"+"|"+"-"+"|"+price+"|"+Deposit+"|"+"-"+"|"+"0"+"|"+"0\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    public static void Unit_Management_Modify(String Floor, String Location, String category , String New){
        int n = 0;
        if(category.compareTo("Floor")==0){
            n=0;
        }else if(category.compareTo("Location")==0){
            n=1;
        }else if(category.compareTo("bedroom")==0){
            n=2;
        }else if(category.compareTo("bathroom")==0){
            n=3;
        }else if(category.compareTo("parking")==0){
            n=4;
        }else if(category.compareTo("status")==0){
            n=5;
        }else if(category.compareTo("date")==0){
            n=6;
        }else if(category.compareTo("price")==0){
            n=7;
        }else if(category.compareTo("Deposit")==0){
            n=8;
        }else if(category.compareTo("ResidentTenantID")==0){
            n=9;
        }else if(category.compareTo("Deposit_Status")==0){
            n=10;
        }else if(category.compareTo("Payment_Stat1us")==0){
            n=11;
        }
        ArrayList<String> Old_temp = new ArrayList<String>();
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
        for(String a : Old_temp) {
            String[] temp1 = a.split("\\|");
            if(temp1[0].compareTo(Floor)==0 && temp1[1].compareTo(Location)==0){
                temp1[n] = New;
                New_temp.add(temp1[0]+"|"+temp1[1]+"|"+temp1[2]+"|"+temp1[3]+"|"+temp1[4]+"|"+temp1[5]+"|"+
                        temp1[6]+"|"+temp1[7]+"|"+temp1[8]+"|"+temp1[9]+"|"+temp1[10]+"|"+temp1[11]);
            }else{
                New_temp.add(a);
            }
        }
        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Unit_Information.txt");
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
    public static void Unit_Management_Search(){
        return;
    }
    public static ArrayList<String[]> Unit_Management_View(){
        ArrayList<String> Old_temp = new ArrayList<String>();
        String pathname = "JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Unit_Information.txt";

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
    public static void Unit_Management_Delete(String Floor, String Location){
        ArrayList<String> Old_temp = new ArrayList<String>();
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
            if (temp1[0].compareTo(Floor)==0 && temp1[1].compareTo(Location)==0) {

            }else
            {
                New_temp.add(a);
            }
        }
        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Unit_Information.txt");
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
    // Resident Tenant Management System start from here
    public static void Resident_Tenant_Management(String VisitorID){
        return;
    }
    public static void Resident_Tenant_Management_Add(String ResidentID,String Name,String Gender, String contact_number){
        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Resident_Information.txt",true);
            //String temp = ";
            myWriter.write( ResidentID+"|"+Name+"|"+Gender +"|"+ contact_number + "\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void Resident_Tenant_Management_Modify(String ResidentID, String category, String New){
        int n = 0;
        if(category.compareTo("ResidentID")==0){
            n=0;
        }else if(category.compareTo("Name")==0){
            n=1;
        }else if(category.compareTo("Gender")==0){
            n=2;
        }else if(category.compareTo("Contact_Number")==0){
            n=3;
        }
        ArrayList<String> Old_temp = new ArrayList<String>();
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
        for(String a : Old_temp) {
            String[] temp1 = a.split("\\|");
            if(temp1[0].compareTo(ResidentID)==0){
                temp1[n] = New;
                New_temp.add(temp1[0]+"|"+temp1[1]+"|"+temp1[2]+"|"+temp1[3]);
            }else{
                New_temp.add(a);
            }
        }
        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Resident_Information.txt");
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
    public static void Resident_Tenant_Management_Search(String VisitorID){
        return;
    }
    public static ArrayList<String[]> Resident_Tenant_Management_View(String VisitorID){
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

    // Complaint Management System start from here
    public static void Complaint_Management_Add(String Description){
        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Complaint.txt",true);
            myWriter.write(ExecutiveID +  "|" + Description + "|Unread\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void Complaint_Management_View(String VisitorID){
        return;
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
        String pathname = "JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Complaint.txt";

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

    // Employee Management System start from here
    public static void Employee_Management_Add_Technician(String TechnicianID, String Name, String Gender, String contact_number, String password){
        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Technician_Information.txt",true);
            myWriter.write(TechnicianID +  "|" + Name +  "|" + Gender+  "|" + contact_number+   "|" + password + "\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void Employee_Management_Add_Cleaner(String CleanerID, String Name, String Gender, String contact_number, String password){
        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Cleaner_Information.txt",true);
            myWriter.write(CleanerID +  "|" + Name +  "|" + Gender+  "|" + contact_number+   "|" + password +"\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void Employee_Management_Add_SecurityGuard(String SecurityGuardID, String Name, String Gender, String contact_number, String password){
        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\SecurityGuard_Information.txt",true);
            myWriter.write(SecurityGuardID +  "|" + Name +  "|" + Gender+  "|" + contact_number+   "|" + password + "\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void Employee_Management_Update(String VisitorID){
        return;
    }
    public static ArrayList<String[]> Employee_Management_View_SecurityGuard(){
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
    public static ArrayList<String[]> Employee_Management_View_Technician(){
        ArrayList<String> Old_temp = new ArrayList<String>();
        String pathname = "JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Technician_Information.txt";

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
    public static ArrayList<String[]> Employee_Management_View_Cleaner(){
        ArrayList<String> Old_temp = new ArrayList<String>();
        String pathname = "JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Cleaner_Information.txt";

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
    // Facility Management System start from here
    public static void Facility_Management_Add(String Facilities ,String Unit){
        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Facility_Information.txt",true);
            //String temp = ";
            myWriter.write( Facilities+"|"+Unit+"|-|-|-|-\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void Facility_Management_Delete(String Facilities ,String Unit) {
        ArrayList<String> Old_temp = new ArrayList<String>();
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
            if (temp1[0].compareTo(Facilities) == 0 && temp1[1].compareTo(Unit) == 0) {

            } else {
                New_temp.add(a);
            }
        }
    }
    public static void Facility_Management_Update(String Facilities ,String Unit, String category, String New){
            int n = 0;
            if(category.compareTo("Facilities")==0){
                n=0;
            }else if(category.compareTo("Unit")==0){
                n=1;
            }else if(category.compareTo("Time1")==0){
                n=2;
            }else if(category.compareTo("Time2")==0){
                n=3;
            }else if(category.compareTo("Time3")==0){
                n=4;
            }else if(category.compareTo("Time4")==0){
                n=5;
            }
            ArrayList<String> Old_temp = new ArrayList<String>();
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
            for(String a : Old_temp) {
                String[] temp1 = a.split("\\|");
                if(temp1[0].compareTo(Facilities)==0&&temp1[1].compareTo(Unit)==0){
                    temp1[n] = New;
                    New_temp.add(temp1[0]+"|"+temp1[1]+"|"+temp1[2]+"|"+temp1[3]+"|"+temp1[4]+"|"+temp1[5]);
                }else{
                    New_temp.add(a);
                }
            }
            try {
                FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Facility_Information.txt");
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
    public static ArrayList<String[]> Facility_Management_View(){
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

    // Facility Booking Management System start from here
    public static void Facility_Booking_Management_Add(String Facility, int Unit, int date, String Outsider_ID){
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
    public static void Facility_Booking_Management_Update(String Facilities ,String Unit, String category, String New){
        int n = 0;
        if(category.compareTo("Facilities")==0){
            n=0;
        }else if(category.compareTo("Unit")==0){
            n=1;
        }else if(category.compareTo("Time1")==0){
            n=2;
        }else if(category.compareTo("Time2")==0){
            n=3;
        }else if(category.compareTo("Time3")==0){
            n=4;
        }else if(category.compareTo("Time4")==0){
            n=5;
        }
        ArrayList<String> Old_temp = new ArrayList<String>();
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
        for(String a : Old_temp) {
            String[] temp1 = a.split("\\|");
            if(temp1[0].compareTo(Facilities)==0&&temp1[1].compareTo(Unit)==0){
                temp1[n] = New;
                New_temp.add(temp1[0]+"|"+temp1[1]+"|"+temp1[2]+"|"+temp1[3]+"|"+temp1[4]+"|"+temp1[5]);
            }else{
                New_temp.add(a);
            }
        }
        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Facility_Information.txt");
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
    public static ArrayList<String[]> Facility_Booking_Management_View(){
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
}
