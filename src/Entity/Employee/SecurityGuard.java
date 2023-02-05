package Entity.Employee;
import Entity.CheckPoint;
import Entity.Visitor_Pass;

import javax.tools.FileObject;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class SecurityGuard extends Employee implements Serializable{
    protected String position = "Security Guard";
    private File security_Guard_Info_txt = new File("src/Database/SecurityGuard_Information.txt");
    private File visitor_Entry_Record_txt = new File("src/Database/Visitors_Entry_Record.txt");
    public  SecurityGuard(){}
    public SecurityGuard(String employeeID){}
    public SecurityGuard(String employeeID, String name, char gender, String contact_Number, int salary) throws IOException, ClassNotFoundException, NotSerializableException {
        super();
        super.set_Info(employeeID, name, gender, contact_Number, salary, position);
    }

    public ArrayList<SecurityGuard> getArrayList() throws IOException, ClassNotFoundException {
        ArrayList<SecurityGuard> securityGuardArrayList = new ArrayList<>();
        Scanner scanner = new Scanner(security_Guard_Info_txt);
        scanner.nextLine();
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 6);
            SecurityGuard securityGuard = new SecurityGuard(data[0], data[1], data[2].charAt(0), data[3], Integer.parseInt(data[4]));
            securityGuardArrayList.add(securityGuard);
        }
        scanner.close();
        return securityGuardArrayList;
    }

    public boolean check_SecurityGuard_Availability(String employeeID) throws IOException, ClassNotFoundException {
        boolean result = false;
        ArrayList<SecurityGuard> securityGuardArrayList = this.getArrayList();
        for (SecurityGuard securityGuard : securityGuardArrayList){
            if (securityGuard.getEmployeeID().equals(employeeID)){
                result = true;
            }
        }
        return result;
    }
    public String getDataString(SecurityGuard securityGuard){
        String[] data = {securityGuard.getEmployeeID(), securityGuard.getName(), Character.toString(securityGuard.getGender()), securityGuard.getContact_Number(), Integer.toString(securityGuard.getSalary()), securityGuard.getPosition_Name()};
        String dataLine = "";
        for (String dd : data){
            dataLine += dd + ":";
        }
        dataLine = dataLine.substring(0, dataLine.length() - 1);
        dataLine += '\n';
        return dataLine;
    }

    public void save_All_SecurityGuard(ArrayList<SecurityGuard> securityGuardArrayList) throws IOException {
        FileWriter fileWriter = new FileWriter(security_Guard_Info_txt, false);
        fileWriter.write("Employee ID:Name:Gender:contact_number:salary:position");
        for (SecurityGuard securityGuard : securityGuardArrayList){
            fileWriter.write(securityGuard.getDataString(securityGuard));
        }
        fileWriter.close();
    }

    public SecurityGuard get_Security_Guard_Info(String employeeID) throws IOException, ClassNotFoundException {
        SecurityGuard securityGuard = new SecurityGuard();
        securityGuard.setEmployeeID("0");
        ArrayList<SecurityGuard> securityGuardArrayList = securityGuard.getArrayList();
        for (SecurityGuard securityGuard1 : securityGuardArrayList){
            if (securityGuard1.getEmployeeID().equals(employeeID)){
                return securityGuard1;
            }
        }
        return securityGuard;
    }

    public void add_Visitor_Entry_Record(String visitor_Pass_ID) throws IOException {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM.dd.yyyy:HHmmss");
        String data = visitor_Pass_ID + ":" + myDateObj.format(myFormatObj) + '\n';
        FileWriter fileWriter = new FileWriter(visitor_Entry_Record_txt, true);
        fileWriter.write(data);
        fileWriter.close();
        System.out.println("add Successful");
    }
    public void delete_Visitor_Entry_Record(String visitor_Pass_ID) throws IOException {
        ArrayList<String[]> record = get_all_Visitor_Entry_Record();
        for (String[] data : record){
            if (data[0].equals(visitor_Pass_ID)){
                record.remove(data);
            }
        }
        save_All_Visitor_Entry_Record(record);
    }

    public void save_All_Visitor_Entry_Record(ArrayList<String[]> record) throws IOException {
        FileWriter fileWriter = new FileWriter(visitor_Entry_Record_txt, false);
        fileWriter.write("VisitorsID:Enter_Date:Enter_Time\n");
        for (String[] data : record){
            fileWriter.write(getEntryRecordDataString(data));
        }
        fileWriter.close();
    }

    public String getEntryRecordDataString(String[] data){
        String dataLine = "";
        for (String dd : data){
            dataLine += dd + ":";
        }
        dataLine = dataLine.substring(0, dataLine.length() - 1);
        dataLine += '\n';
        return dataLine;
    }

    public ArrayList<String[]> get_all_Visitor_Entry_Record() throws FileNotFoundException {
        ArrayList<String[]> record = new ArrayList<>();
        Scanner scanner = new Scanner(visitor_Entry_Record_txt);
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 3);
            record.add(data);
        }
        return record;
    }

    public ArrayList<String[]> get_Visitor_Entry_Record(String visitorID) throws FileNotFoundException {
        ArrayList<String[]> record = new ArrayList<>();
        Scanner scanner = new Scanner(visitor_Entry_Record_txt);
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 3);
            if (data[0].equals(visitorID))
                record.add(data);
        }
        return record;
    }

    public ArrayList<String[]> get_Visitor_Entry_Record(LocalDate localDate) throws FileNotFoundException {
        ArrayList<String[]> record = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        String date = localDate.format(formatter);
        Scanner scanner = new Scanner(visitor_Entry_Record_txt);
        while (scanner.hasNextLine()){
            String[] recordData = scanner.nextLine().split(":", 3);
            if (recordData[1].equals(date))
                record.add(recordData);
        }
        return record;
    }

    public Visitor_Pass view_Visitor_Pass(String visitor_Pass_ID) throws FileNotFoundException {
        Visitor_Pass visitor = new Visitor_Pass();
        return visitor.search_Visitor_Pass_Info(visitor_Pass_ID);
    }

    public void checkIn_CheckPoint(String securityGuardID, String checkPointID) throws IOException, ClassNotFoundException {
        SecurityGuard securityGuard = new SecurityGuard();
        CheckPoint checkPoint = new CheckPoint();
        if (securityGuard.check_SecurityGuard_Availability(securityGuardID) && checkPoint.check_CheckPoint_Existence(checkPointID)){
            CheckPoint.Record record = new CheckPoint.Record();
            ArrayList<CheckPoint.Record> recordArrayList = record.getArrayList();
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            recordArrayList.add(new CheckPoint.Record(securityGuardID, checkPointID, date, time));
            record.save_All_Record(recordArrayList);
        }
    }

    public static class Incident{
        private String incident_ID;
        private LocalDateTime dateTime;
        private String security_Guard_ID;
        private String description;
        private File incident_Report = new File("src/Database/Incident_Report.txt");
        public Incident(){}

        public Incident(String incident_ID, LocalDateTime dateTime, String security_Guard_ID, String description) {
            this.incident_ID = incident_ID;
            this.dateTime = dateTime;
            this.security_Guard_ID = security_Guard_ID;
            this.description = description;
        }

        public String getIncident_ID() {
            return incident_ID;
        }

        public void setIncident_ID(String incident_ID) {
            this.incident_ID = incident_ID;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        public String getSecurity_Guard_ID() {
            return security_Guard_ID;
        }

        public void setSecurity_Guard_ID(String security_Guard_ID) {
            this.security_Guard_ID = security_Guard_ID;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void add_Incident_Record(Incident incident) throws IOException {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy:HHmmss");
            String data = incident.getDataString(incident);
            FileWriter fileWriter = new FileWriter(incident_Report, true);
            fileWriter.write(data);
            fileWriter.close();
            System.out.println("add Successful");
        }
        public void delete_Incident_Record(String incident_ID) throws IOException {
            ArrayList<Incident> incidentArrayList = get_all_Incident_Report();
            for (Incident incident : incidentArrayList){
                if (incident.getIncident_ID().equals(incident_ID)){
                    incidentArrayList.remove(incident);
                }
            }
            save_All_Incident_Report(incidentArrayList);
        }

        public void update_Incident_Report(Incident incident, String incident_ID) throws IOException {
            delete_Incident_Record(incident_ID);
            add_Incident_Record(incident);
        }

        public void save_All_Incident_Report(ArrayList<Incident> incidentArrayList) throws IOException {
            FileWriter fileWriter = new FileWriter(incident_Report, false);
            fileWriter.write("Incident ID:Date:Time:SecurityGuard ID:Description\n");
            for (Incident incident : incidentArrayList){
                fileWriter.write(getDataString(incident));
            }
            fileWriter.close();
        }

        public String getDataString(Incident incident){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy:HHmmss");
            String[] data = {incident.getIncident_ID(), incident.getDateTime().format(formatter), incident.getSecurity_Guard_ID(), incident.getDescription()};
            String dataLine = "";
            for (String dd : data){
                dataLine += dd + ":";
            }
            dataLine = dataLine.substring(0, dataLine.length() - 1);
            dataLine += '\n';
            return dataLine;
        }

        public ArrayList<Incident> get_all_Incident_Report() throws FileNotFoundException {
            ArrayList<Incident> incidentArrayList = new ArrayList<>();
            Scanner scanner = new Scanner(incident_Report);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy:HHmmss");
            while (scanner.hasNextLine()){
                String[] data = scanner.nextLine().split(":", 5);
                String dateTime = data[1] + ":" + data[2];
                incidentArrayList.add(new Incident(data[0], LocalDateTime.parse(dateTime, formatter), data[3], data[4]));
            }
            return incidentArrayList;
        }
    }
}
