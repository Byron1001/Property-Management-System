package Entity.Employee;

import Entity.Visitor_Pass;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class Employee {
    protected String employeeID;
    protected String name;
    protected char gender;
    protected String contact_Number;
    protected int salary;
    protected String position_Name;

    public Employee() {
    }

    public void set_Info(String employeeID, String name, char gender, String contact_Number, int salary, String position){
        this.employeeID = employeeID;
        this.name = name;
        this.gender = gender;
        this.contact_Number = contact_Number;
        this.position_Name = position;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getContact_Number() {
        return contact_Number;
    }

    public void setContact_Number(String contact_Number) {
        this.contact_Number = contact_Number;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getPosition_Name() {
        return position_Name;
    }
    public void setPosition_Name(String position) {
        this.position_Name = position;
    }

    public ArrayList<Employee_Task> view_undone_Task(String employeeID) throws FileNotFoundException {
        Employee_Task employeeTask = new Employee_Task();
        ArrayList<Employee_Task> undone_TaskArrayList = employeeTask.getArrayList();
        for (Employee_Task employeeTask1 : undone_TaskArrayList){
            if (!(employeeTask1.getEmployeeID().substring(0, 2).equals(employeeID.substring(0, 2)))){
                undone_TaskArrayList.remove(employeeTask1);
            } else if (employeeTask1.getStatus().equals("done")) {
                undone_TaskArrayList.remove(employeeTask1);
            }
        }
        return undone_TaskArrayList;
    }

    public void mark_Task_Done(String taskID) throws IOException {
        Employee_Task employeeTask = new Employee_Task();
        ArrayList<Employee_Task> employeeTaskArrayList = employeeTask.getArrayList();
        for (Employee_Task employeeTask1 : employeeTaskArrayList){
            if (employeeTask1.getTaskID().equals(taskID)){
                employeeTaskArrayList.remove(employeeTask1);
            }
        }
        employeeTask.save_All_Employee_Task(employeeTaskArrayList);
    }

    private Integer check_Employee_Position(String employeeID){
        Integer result = 0;
        HashMap<String, Integer> map = new HashMap<>();
        map.put("SG", 1);
        map.put("CN", 2);
        map.put("TN", 3);
        result = map.getOrDefault(employeeID.substring(0,2), -1);
        return result;
    }

    public Employee search_Employee_Info(String employeeID) throws IOException, ClassNotFoundException {
        int result = check_Employee_Position(employeeID);
        Employee employee = new Employee();
        switch (result){
            case 1:
                employee = new SecurityGuard().get_Security_Guard_Info(employeeID);
                break;
            case 2:
                employee = new Cleaner().get_Cleaner_Info(employeeID);
                break;
            case 3:
                employee = new Technician().get_Technician_Info(employeeID);
                break;
            default:
                employee.setEmployeeID("0");
        }
        return employee;
    }
}
