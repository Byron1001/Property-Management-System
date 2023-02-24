package Entity.Employee;

import Entity.Resident.Resident;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Employee_Task {
    private String taskID;
    private String employeeID;
    private String description;
    private String status;
    private static File employee_Task_txt = new File("src/Database/Employee_Task.txt");
    public Employee_Task(){}

    public Employee_Task(String taskID, String employeeID, String description, String status) {
        this.taskID = taskID;
        this.employeeID = employeeID;
        this.description = description;
        this.status = status;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Employee_Task> getArrayList() throws FileNotFoundException {
        ArrayList<Employee_Task> employee_TaskArrayList = new ArrayList<>();
        Scanner scanner = new Scanner(employee_Task_txt);
        scanner.nextLine();
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 4);
            Employee_Task employee_Task = new Employee_Task(data[0], data[1], data[2], data[3]);
            employee_TaskArrayList.add(employee_Task);
        }
        scanner.close();
        return employee_TaskArrayList;
    }

    public String getDataString(Employee_Task employee_Task){
        String[] data = {employee_Task.getTaskID(), employee_Task.getEmployeeID(), employee_Task.getDescription(), employee_Task.getStatus()};
        String dataLine = "";
        for (String dd : data){
            dataLine += dd + ":";
        }
        dataLine = dataLine.substring(0, dataLine.length() - 1);
        dataLine += '\n';
        return dataLine;
    }

    public String[] getStringArray(Employee_Task employeeTask){
        return new String[]{employeeTask.getTaskID(), employeeTask.getEmployeeID(), employeeTask.getDescription(), employeeTask.getStatus()};
    }

    public void save_All_Employee_Task(ArrayList<Employee_Task> employee_TaskArrayList) throws IOException {
        FileWriter fileWriter = new FileWriter(employee_Task_txt, false);
        fileWriter.write("TaskID:EmployeeID:description:status");
        for (Employee_Task employee_Task : employee_TaskArrayList){
            fileWriter.write(employee_Task.getDataString(employee_Task));
        }
        fileWriter.close();
    }

    public String get_Auto_TaskID() throws FileNotFoundException {
        FileReader fileReader = new FileReader(employee_Task_txt);
        Scanner scanner = new Scanner(fileReader);
        scanner.nextLine();
        Integer num = 0;
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 4);
            String number = data[0].substring(4);
            num = Integer.parseInt(number);
            num += 1;
        }
        String str = "Task" + num.toString();
        System.out.println(str);
        return str;
    }

    public void add_Task(Employee_Task task) throws IOException {
        ArrayList<Employee_Task> employeeTaskArrayList = task.getArrayList();
        employeeTaskArrayList.add(task);
        task.save_All_Employee_Task(employeeTaskArrayList);
    }

    public Employee_Task get_Employee_Task_Info(String taskID) throws FileNotFoundException {
        Employee_Task employeeTask = new Employee_Task();
        employeeTask.setTaskID("0");
        ArrayList<Employee_Task> employeeTaskArrayList = getArrayList();
        for (Employee_Task employeeTask1 : employeeTaskArrayList){
            if (employeeTask1.getTaskID().equals(taskID))
                employeeTask = employeeTask1;
        }
        return employeeTask;
    }
}
