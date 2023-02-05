package Entity.Executive.Building_Executive;

import Entity.CheckPoint;
import Entity.Complaint;
import Entity.Employee.Employee_Task;
import Entity.Employee.SecurityGuard;
import Entity.Executive.Executive;
import Entity.Patrolling;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Building_Executive_Function{
    public static class Building_Executive extends Executive{
        private String position = "Building Executive";
        private File building_Executive_Info_txt = new File("src/Database/Building_Executive_Information.txt");
        public Building_Executive(){}
        public Building_Executive(String executiveID, String name, char gender, String contact_number){
            super();
            super.set_Info(executiveID, name, gender, contact_number, position);
        }

        public ArrayList<Building_Executive> getArrayList() throws FileNotFoundException {
            ArrayList<Building_Executive> building_ExecutiveArrayList = new ArrayList<>();
            FileReader reader = new FileReader(building_Executive_Info_txt);
            Scanner scanner = new Scanner(reader);
            scanner.nextLine();
            while (scanner.hasNextLine()){
                String[] data = scanner.nextLine().split(":", 5);
                building_ExecutiveArrayList.add(new Building_Executive(data[0], data[1], data[2].charAt(0), data[3]));
            }
            return building_ExecutiveArrayList;
        }
        public void save_All_Building_Executive(ArrayList<Building_Executive> building_ExecutiveArrayList) throws IOException {
            FileWriter fileWriter = new FileWriter(building_Executive_Info_txt, false);
            fileWriter.write("executiveID:Name:Gender:contact_number:position\n");
            for (Building_Executive building_Executive : building_ExecutiveArrayList){
                String[] data = {building_Executive.getExecutiveID(), building_Executive.getName(), Character.toString(building_Executive.getGender()), building_Executive.getContact_Number(), building_Executive.getPosition()};
                String dataLine = "";
                for (String dd : data){
                    dataLine += dd + ":";
                }
                dataLine = dataLine.substring(0, dataLine.length() - 1);
                dataLine += '\n';
                fileWriter.write(dataLine);
            }
            fileWriter.close();
        }
        public String getDataString(Building_Executive building_Executive){
            String[] data = {building_Executive.getExecutiveID(), building_Executive.getName(), Character.toString(building_Executive.getGender()), building_Executive.getContact_Number(), building_Executive.getPosition()};
            String dataLine = "";
            for (String dd : data){
                dataLine += dd + ":";
            }
            dataLine = dataLine.substring(0, dataLine.length() - 1);
            dataLine += '\n';
            return dataLine;
        }
        public Building_Executive get_Building_Executive_Info(String executiveID) throws FileNotFoundException {
            ArrayList<Building_Executive> building_ExecutiveArrayList = getArrayList();
            Building_Executive result = new Building_Executive();
            result.setExecutiveID("0");
            for (Building_Executive building_Executive : building_ExecutiveArrayList) {
                if (building_Executive.getExecutiveID().equals(executiveID)) {
                    result = building_Executive;
                }
            }
            return result;
        }
        public boolean check_Building_Executive_Availability(String executiveID) throws FileNotFoundException {
            boolean result = false;
            Building_Executive building_Executive = new Building_Executive();
            ArrayList<Building_Executive> building_ExecutiveArrayList = building_Executive.getArrayList();
            for (Building_Executive building_Executive1 : building_ExecutiveArrayList) {
                if (building_Executive1.getExecutiveID().equals(executiveID))
                    result = true;
            }
            return result;
        }

        public void add_Employee_Task(Employee_Task employeeTask) throws IOException {
            String status = "undone";
            employeeTask.setStatus(status);
            ArrayList<Employee_Task> employeeTaskArrayList = employeeTask.getArrayList();
            employeeTaskArrayList.add(employeeTask);
            employeeTask.save_All_Employee_Task(employeeTaskArrayList);
        }

        public void delete_Employee_Task(String taskID) throws IOException {
            Employee_Task employeeTask = new Employee_Task();
            ArrayList<Employee_Task> employeeTaskArrayList = employeeTask.getArrayList();
            for (Employee_Task employeeTask1 : employeeTaskArrayList){
                if (employeeTask1.getTaskID().equals(taskID))
                    employeeTaskArrayList.remove(employeeTask1);
            }
            employeeTask.save_All_Employee_Task(employeeTaskArrayList);
        }

        public void update_Employee_Task(Employee_Task employeeTask, String taskID) throws IOException {
            delete_Employee_Task(taskID);
            add_Employee_Task(employeeTask);
        }

        public ArrayList<Employee_Task> view_All_Employee_Task() throws FileNotFoundException {
            ArrayList<Employee_Task> employeeTaskArrayList = new Employee_Task().getArrayList();
            return employeeTaskArrayList;
        }

        public ArrayList<Complaint> view_All_Complaint() throws FileNotFoundException {
            ArrayList<Complaint> complaintArrayList = new Complaint().getArrayList();
            return complaintArrayList;
        }

        public void mark_Complaint_Solved(String complaintID) throws IOException {
            Complaint complaint = new Complaint();
            ArrayList<Complaint> complaintArrayList = complaint.getArrayList();
            for (Complaint complaint1 : complaintArrayList){
                if (complaint1.getComplaintID().equals(complaintID))
                    complaint1.setStatus("solved");
            }
            complaint.save_All_Complaint(complaintArrayList);
        }

        public void add_Patrolling_Schedule(Patrolling patrolling) throws IOException, ClassNotFoundException {
            SecurityGuard securityGuard = new SecurityGuard();
            boolean check = securityGuard.check_SecurityGuard_Availability(patrolling.getEmployeeID());
            if (check){
                ArrayList<Patrolling> patrollingArrayList = patrolling.getArrayList();
                patrollingArrayList.add(patrolling);
                patrolling.save_All_Patrolling(patrollingArrayList);
            }
        }

        public void delete_Patrolling_Schedule(String patrolID) throws IOException, ClassNotFoundException {
            Patrolling patrolling = new Patrolling();
            ArrayList<Patrolling> patrollingArrayList = patrolling.getArrayList();
            for (Patrolling patrolling1 : patrollingArrayList){
                if (patrolling1.getPatrolID().equals(patrolID))
                    patrollingArrayList.remove(patrolling1);
            }
            patrolling.save_All_Patrolling(patrollingArrayList);
        }

        public void modify_Patrolling_Schedule(Patrolling patrolling, String patrolID) throws IOException, ClassNotFoundException {
            delete_Patrolling_Schedule(patrolID);
            add_Patrolling_Schedule(patrolling);
        }

        public ArrayList<Patrolling> view_All_Patrol_Schedule() throws IOException, ClassNotFoundException {
            Patrolling patrolling = new Patrolling();
            ArrayList<Patrolling> patrollingArrayList = patrolling.getArrayList();
            return patrollingArrayList;
        }

        public void add_New_CheckPoint(CheckPoint checkPoint) throws IOException, ClassNotFoundException {
            ArrayList<CheckPoint> checkPointArrayList = checkPoint.getArrayList();
            checkPointArrayList.add(checkPoint);
            checkPoint.save_All_CheckPoint(checkPointArrayList);
        }

        public void delete_CheckPoint(String checkPointID) throws IOException, ClassNotFoundException {
            CheckPoint checkPoint = new CheckPoint();
            ArrayList<CheckPoint> checkPointArrayList = checkPoint.getArrayList();
            for (CheckPoint checkPoint1 : checkPointArrayList){
                if (checkPoint1.getCheckPointID().equals(checkPointID))
                    checkPointArrayList.remove(checkPoint1);
            }
            checkPoint.save_All_CheckPoint(checkPointArrayList);
        }

        public void modify_CheckPoint(CheckPoint checkPoint, String checkPointID) throws IOException, ClassNotFoundException {
            delete_CheckPoint(checkPointID);
            add_New_CheckPoint(checkPoint);
        }

        public ArrayList<CheckPoint> view_All_CheckPoint() throws IOException, ClassNotFoundException {
            CheckPoint checkPoint = new CheckPoint();
            ArrayList<CheckPoint> checkPointArrayList = checkPoint.getArrayList();
            return checkPointArrayList;
        }

    }
}
