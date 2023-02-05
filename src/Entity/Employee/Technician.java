package Entity.Employee;

import Entity.Employee.Employee;
import Entity.Visitor_Pass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Technician extends Employee {
    protected String position_Name = "Technician";
    private static File technician_Info_txt = new File("src/Database/Technician_Information.txt");
    public Technician(){}
    public Technician(String employeeID){
    }
    public Technician(String employeeID, String name, char gender, String contact_Number, int salary){
        super();
        super.set_Info(employeeID, name, gender, contact_Number, salary, position_Name);
    }

    public ArrayList<Technician> getArrayList() throws FileNotFoundException {
        ArrayList<Technician> technicianArrayList = new ArrayList<>();
        Scanner scanner = new Scanner(technician_Info_txt);
        scanner.nextLine();
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 6);
            Technician technician = new Technician(data[0], data[1], data[2].charAt(0), data[3], Integer.parseInt(data[4]));
            technicianArrayList.add(technician);
        }
        scanner.close();
        return technicianArrayList;
    }

    public boolean check_Technician_Availability(String employeeID) throws FileNotFoundException {
        boolean result = false;
        ArrayList<Technician> technicianArrayList = this.getArrayList();
        for (Technician technician : technicianArrayList){
            if (technician.getEmployeeID().equals(employeeID)){
                result = true;
            }
        }
        return result;
    }
    public String getDataString(Technician technician){
        String[] data = {technician.getEmployeeID(), technician.getName(), Character.toString(technician.getGender()), technician.getContact_Number(), Integer.toString(technician.getSalary()), technician.getPosition_Name()};
        String dataLine = "";
        for (String dd : data){
            dataLine += dd + ":";
        }
        dataLine = dataLine.substring(0, dataLine.length() - 1);
        dataLine += '\n';
        return dataLine;
    }

    public void save_All_Technician(ArrayList<Technician> technicianArrayList) throws IOException {
        FileWriter fileWriter = new FileWriter(technician_Info_txt, false);
        fileWriter.write("Employee ID:Name:Gender:contact_number:salary:position");
        for (Technician technician : technicianArrayList){
            fileWriter.write(technician.getDataString(technician));
        }
        fileWriter.close();
    }

    public Technician get_Technician_Info(String employeeID) throws FileNotFoundException {
        Technician technician = new Technician();
        technician.setEmployeeID("0");
        ArrayList<Technician> technicianArrayList = technician.getArrayList();
        for (Technician technician1 : technicianArrayList){
            if (technician.getEmployeeID().equals(employeeID)){
                return technician1;
            }
        }
        return technician;
    }

}
