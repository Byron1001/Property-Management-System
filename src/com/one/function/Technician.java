package com.one.function;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Technician extends Employee{
    public static String Name;
    public static String Gender;
    public static String Contact_Number;
    public static String Position = "Technician";

    public Technician(String ID){
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
            if(temp1[0].compareTo(Employee_ID)==0){
                Name = temp1[1];
                Gender = temp1[2];
                Contact_Number = temp1[3];
            }
        }

    }
    public static String getID(){
        return Employee_ID;
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
    public static String getPosition(){
        return Position;
    }
    public static void Search_View_Visitor_Pass(String VisitorID){
        ArrayList<String> temp = new ArrayList<String>();               //Open Visitors text file and extract all of them
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Visitors.txt");
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
        System.out.println("1. Search");
        System.out.println("2. View");
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your choice >> ");
        String option = scan.nextLine();
        option = option.replace("\n","");
        if(option.compareTo("1")==0) {
            System.out.print("Search >> ");
            String code = scan.nextLine();
            for (int i = 0; i < temp.size(); i++) {
                String[] temp1 = temp.get(i).split("\\|");
                if(code.compareTo(temp1[0])==0){
                    System.out.println(temp.get(i));
                }
            }
        }
        if(option.compareTo("2")==0) {
            for (int i = 0; i < temp.size(); i++) {
                System.out.println(temp.get(i));
            }
        }
        return ;
    }
    public static void Record_Update_Visitor_Entry(String VisitorID){
        Visitor a = new Visitor(VisitorID);
        Visitor.Update_Entry();
        System.out.println("Update Successful");
    }
    public static void CheckPoint_Check_In(String CheckPoint){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM.dd.yyyy|HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        try {
            FileWriter myWriter = new FileWriter("SecurityGuard_CheckPoint.txt",true);
            //String temp = ";
            myWriter.write( Employee_ID + "|" + CheckPoint + "|" + formattedDate +  "\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return ;
    }
    public static void Record_Update_Incident(String Description){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM.dd.yyyy|HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        try {
            FileWriter myWriter = new FileWriter("SecurityGuard_Incident.txt",true);
            //String temp = ";
            myWriter.write( Employee_ID +  "|" + formattedDate + "|" + Description + "\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
