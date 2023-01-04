package User;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class Visitor {
    public static String name;
    public static String visitorID;
    public static String date_Start;
    public static String date_End;
    public Visitor(String Visitor_ID){
        this.visitorID  = Visitor_ID;
        ArrayList<String> temp = new ArrayList<String>();
        try {
            File myObj = new File("Visitors.txt");
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
            if(this.visitorID.compareTo(split[0])==0){
                this.visitorID = split[0];
                this.name = split[1];
                this.date_Start = split[2];
                this.date_End = split[3];
            }
        }
    }
    public static void View_Visitor_Information(){
        System.out.println(name);
        System.out.println(visitorID);
        System.out.println(date_Start);
    }
}
