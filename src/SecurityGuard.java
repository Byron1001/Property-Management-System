import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SecurityGuard extends Employee{
    String Name;
    String SecurityGuardID;
    String Position = "SecurityGuard";

    public SecurityGuard(String Name){
        super(Name);
    }

    public static void Search_View_Visitor_Pass(String VisitorID){
        ArrayList<String> temp = new ArrayList<String>();               //Open Visitors text file and extract all of them
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
    public static int Record_Update_Visitor_Entry(){
        return 1;
    }
    public static int CheckPoint_Check_In(){
        return 1;
    }
    public static int Record_Update_Incident(){
        return 1;
    }

}
