package Entity.Employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cleaner extends Employee{
    protected String position = "Cleaner";
    private File cleaner_Info_txt = new File("src/Database/Cleaner_Information.txt");
    public Cleaner(){}
    public Cleaner(String employeeID, String name, char gender, String contact_Number, int salary){
        super();
        super.set_Info(employeeID, name, gender, contact_Number, salary, position);
    }

    public ArrayList<Cleaner> getArrayList() throws FileNotFoundException {
        ArrayList<Cleaner> cleanerArrayList = new ArrayList<>();
        Scanner scanner = new Scanner(cleaner_Info_txt);
        scanner.nextLine();
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 6);
            Cleaner Cleaner = new Cleaner(data[0], data[1], data[2].charAt(0), data[3], Integer.parseInt(data[4]));
            cleanerArrayList.add(Cleaner);
        }
        scanner.close();
        return cleanerArrayList;
    }

    public boolean check_Cleaner_Availability(String employeeID) throws FileNotFoundException {
        boolean result = false;
        ArrayList<Cleaner> cleanerArrayList = this.getArrayList();
        for (Cleaner Cleaner : cleanerArrayList){
            if (Cleaner.getEmployeeID().equals(employeeID)){
                result = true;
            }
        }
        return result;
    }
    public static String getDataString(Cleaner Cleaner){
        String[] data = {Cleaner.getEmployeeID(), Cleaner.getName(), Character.toString(Cleaner.getGender()), Cleaner.getContact_Number(), Integer.toString(Cleaner.getSalary()), Cleaner.getPosition_Name()};
        String dataLine = "";
        for (String dd : data){
            dataLine += dd + ":";
        }
        dataLine = dataLine.substring(0, dataLine.length() - 1);
        dataLine += '\n';
        return dataLine;
    }

    public void save_All_Cleaner(ArrayList<Cleaner> cleanerArrayList) throws IOException {
        FileWriter fileWriter = new FileWriter(cleaner_Info_txt, false);
        fileWriter.write("Employee ID:Name:Gender:contact_number:salary:position");
        for (Cleaner cleaner : cleanerArrayList){
            fileWriter.write(Cleaner.getDataString(cleaner));
        }
        fileWriter.close();
    }

    public Cleaner get_Cleaner_Info(String employeeID) throws FileNotFoundException {
        Cleaner cleaner = new Cleaner();
        cleaner.setEmployeeID("0");
        ArrayList<Cleaner> cleanerArrayList = cleaner.getArrayList();
        for (Cleaner cleaner1 : cleanerArrayList){
            if (cleaner1.getEmployeeID().equals(employeeID)){
                return cleaner1;
            }
        }
        return cleaner;
    }
}
