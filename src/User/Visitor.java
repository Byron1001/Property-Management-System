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
    private ArrayList<String> temp = new ArrayList<String>();
    Scanner scanner;
    File visitorData = new File("../../Database/Visitors.txt");
    public Visitor(String Visitor_ID){
        this.visitorID  = Visitor_ID;
        try {
            scanner = new Scanner(visitorData);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                temp.add(data);
            }
            scanner.close();
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

    private String autoGenerateVisitorID(){
        String data = "00000";
        try {
            scanner = new Scanner(visitorData);
            while (scanner.hasNextLine()){
                data = scanner.nextLine().split("|", 4)[0];
                data = data.substring(7);
                Integer recordNum = Integer.parseInt(data) + 1;
                data = recordNum.toString();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        scanner.close();
        return "Visitor" + data;
    }

    public void newVisitor(String name, String date_Start, String date_End){}
}
