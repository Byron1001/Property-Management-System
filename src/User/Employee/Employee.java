package User.Employee;

import User.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Employee extends User {
    public String employeeID;
    private int employeeSalary;
    public String employeeName;
    public String employeePosition;
    private ArrayList<Employee> employeeArrayList = new ArrayList<>();
    Scanner scanner;
    File employeeData = new File("../../Database/EmployeeData.txt");
    public Employee(){
        init();
    }

    public Employee(String employeeID, String employeeName, String employeePosition, int employeeSalary){
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.employeeSalary = employeeSalary;
        this.employeePosition = employeePosition;
    }

    public int getEmployeeSalary(){
        return employeeSalary;
    }

    public ArrayList<Employee> getEmployeeArrayList(){
        return employeeArrayList;
    }

    private void init(){
        if (! employeeData.exists()){
            try {
                employeeData.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            scanner = new Scanner(employeeData);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split("|", 4);
            Employee employee1 = new Employee(data[0], data[2], data[3], Integer.parseInt(data[1]));
            employeeArrayList.add(employee1);
        }
        scanner.close();
    }
}
