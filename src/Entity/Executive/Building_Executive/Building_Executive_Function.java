package Entity.Executive.Building_Executive;

import Entity.CheckPoint;
import Entity.Complaint;
import Entity.Employee.Employee_Task;
import Entity.Employee.SecurityGuard.SecurityGuard;
import Entity.Executive.Executive;
import Entity.Patrolling;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Building_Executive_Function{
    public static class Building_Executive extends Executive{
        private final String position = "Building Executive";
        private final File building_Executive_Info_txt = new File("src/Database/Building_Executive_Information.txt");
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
        public void update_Building_Executive_Info(Building_Executive buildingExecutive, String executiveID) throws IOException {
            ArrayList<Building_Executive> buildingExecutiveArrayList = buildingExecutive.getArrayList();
            for (Building_Executive buildingExecutive1 : buildingExecutiveArrayList){
                if (buildingExecutive1.getExecutiveID().equals(executiveID))
                    buildingExecutiveArrayList.remove(buildingExecutive);
            }
            buildingExecutiveArrayList.add(buildingExecutive);
            buildingExecutive.save_All_Building_Executive(buildingExecutiveArrayList);
        }

        public boolean check_Building_Executive_Availability(String executiveID) throws FileNotFoundException {
            boolean result = false;
            Building_Executive building_Executive = new Building_Executive();
            ArrayList<Building_Executive> building_ExecutiveArrayList = building_Executive.getArrayList();
            for (Building_Executive building_Executive1 : building_ExecutiveArrayList) {
                if (building_Executive1.getExecutiveID().equals(executiveID)) {
                    result = true;
                    break;
                }
            }
            return result;
        }

        //Employee Task
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
            employeeTaskArrayList.removeIf(employeeTask1 -> employeeTask1.getTaskID().equals(taskID));
            employeeTask.save_All_Employee_Task(employeeTaskArrayList);
        }

        public void update_Employee_Task(Employee_Task employeeTask, String taskID) throws IOException {
            delete_Employee_Task(taskID);
            add_Employee_Task(employeeTask);
        }

        //Complaint
        public ArrayList<Complaint> view_All_Complaint() throws FileNotFoundException {
            return new Complaint().getArrayList();
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

        //Patrolling

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
            patrollingArrayList.removeIf(patrolling1 -> patrolling1.getPatrolID().equals(patrolID));
            patrolling.save_All_Patrolling(patrollingArrayList);
        }

        public void modify_Patrolling_Schedule(Patrolling patrolling, String patrolID) throws IOException, ClassNotFoundException {
            delete_Patrolling_Schedule(patrolID);
            add_Patrolling_Schedule(patrolling);
        }

        // Checkpoint
        public void add_New_CheckPoint(CheckPoint checkPoint) throws IOException, ClassNotFoundException {
            ArrayList<CheckPoint> checkPointArrayList = checkPoint.getArrayList();
            checkPointArrayList.add(checkPoint);
            checkPoint.save_All_CheckPoint(checkPointArrayList);
        }

        public void delete_CheckPoint(String checkPointID) throws IOException, ClassNotFoundException {
            CheckPoint checkPoint = new CheckPoint();
            ArrayList<CheckPoint> checkPointArrayList = checkPoint.getArrayList();
            checkPointArrayList.removeIf(checkPoint1 -> checkPoint1.getCheckPointID().equals(checkPointID));
            checkPoint.save_All_CheckPoint(checkPointArrayList);
        }

        public void modify_CheckPoint(CheckPoint checkPoint, String checkPointID) throws IOException, ClassNotFoundException {
            delete_CheckPoint(checkPointID);
            add_New_CheckPoint(checkPoint);
        }

        public ArrayList<CheckPoint> view_All_CheckPoint() throws IOException, ClassNotFoundException {
            CheckPoint checkPoint = new CheckPoint();
            return checkPoint.getArrayList();
        }

    }
    public static class Button extends JButton {
        public Color color1 = Color.decode("#283c86");
        public Color color2 = Color.decode("#45a247");
        public Button(String title){
            super(title);
            setContentAreaFilled(false);
            setBackground(Color.decode("#283c86"));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gradientPaint = new GradientPaint(0, 0, color1,getWidth(), 0, color2);
            g2.setPaint(gradientPaint);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            super.paintComponent(g);
        }
    }
}
