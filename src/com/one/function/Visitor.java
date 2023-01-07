package com.one.function;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;
public class Visitor {
    public static String Name;
    public static String VisitorID;
    public static String Gender;
    public static String Contact_Number;
    public static String Date_Start;
    public static String Date_End;
    public Visitor(String Visitor_ID){
        this.VisitorID  = Visitor_ID;
        ArrayList<String> temp = new ArrayList<String>();
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
        for(int i = 0; i < temp.size(); i++) {
            String[] split = temp.get(i).split("\\|");
            if(this.VisitorID.compareTo(split[0])==0){
                this.VisitorID = split[0];
                this.Name = split[1];
                this.Gender = split[2];
                this.Contact_Number = split[3];
                this.Date_Start = split[4];
                this.Date_End = split[5];
            }
        }
    }
    public static String getName(){
        return Name;
    }
    public static String getID(){
        return VisitorID;
    }
    public static String getGender(){
        return Gender;
    }
    public static String getContact_Number(){
        return Contact_Number;
    }
    public static String getDate_Start(){
        return Date_Start;
    }
    public static String getDate_End(){
        return Date_End;
    }

    public static void Update_Entry(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM.dd.yyyy|HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        //write into Visitors_Entry
        try {
            FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Visitors_Entry.txt",true);
            myWriter.write(VisitorID + "|" + formattedDate + "\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
