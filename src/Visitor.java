import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class Visitor {
    public static String Name;
    public static String VisitorID;
    public static String Date_Start;
    public static String Date_End;
    public Visitor(String Visitor_ID){
        this.VisitorID  = Visitor_ID;
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
            if(this.VisitorID.compareTo(split[0])==0){
                this.VisitorID = split[0];
                this.Name = split[1];
                this.Date_Start = split[2];
                this.Date_End = split[3];
            }
        }
    }
    public static void View_Visitor_Information(){
        System.out.println(Name);
        System.out.println(VisitorID);
        System.out.println(Date_Start);
    }
}
