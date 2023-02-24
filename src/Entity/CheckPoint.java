package Entity;

import Entity.Financial.Invoice;

import java.io.*;
import java.nio.file.StandardWatchEventKinds;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class CheckPoint {
    private String checkPointID;
    private String name;
    private String location;
    private File checkPoint_Info_txt = new File("src/Database/CheckPoint_Information.txt");
    private File checkPoint_Record_txt = new File("src/Database/CheckPoint_Record.txt");

    public CheckPoint(){}

    public CheckPoint(String checkPointID, String name, String location) {
        this.checkPointID = checkPointID;
        this.name = name;
        this.location = location;
    }

    public String getCheckPointID() {
        return checkPointID;
    }

    public void setCheckPointID(String checkPointID) {
        this.checkPointID = checkPointID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String[] getStringArray(CheckPoint checkPoint){
        String[] data = {checkPoint.getCheckPointID(), checkPoint.getName(), checkPoint.getLocation()};
        return data;
    }

    public ArrayList<CheckPoint> getArrayList() throws IOException, ClassNotFoundException {
        ArrayList<CheckPoint> checkPointArrayList = new ArrayList<>();
        Scanner scanner = new Scanner(checkPoint_Info_txt);
        scanner.nextLine();
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 3);
            CheckPoint checkPoint = new CheckPoint(data[0], data[1], data[2]);
            checkPointArrayList.add(checkPoint);
        }
        scanner.close();
        return checkPointArrayList;
    }

    public boolean check_CheckPoint_Existence(String checkPointID) throws IOException, ClassNotFoundException {
        boolean result = false;
        ArrayList<CheckPoint> checkPointArrayList = this.getArrayList();
        for (CheckPoint checkPoint : checkPointArrayList){
            if (checkPoint.getCheckPointID().equals(checkPointID))
                result = true;
        }
        return result;
    }

    public String getDataString(CheckPoint checkPoint){
        String[] data = {checkPoint.getCheckPointID(), checkPoint.getName(), checkPoint.getLocation()};
        String dataLine = "";
        for (String dd : data){
            dataLine += dd + ":";
        }
        dataLine = dataLine.substring(0, dataLine.length() - 1);
        dataLine += '\n';
        return dataLine;
    }

    public void save_All_CheckPoint(ArrayList<CheckPoint> checkPointArrayList) throws IOException {
        FileWriter fileWriter = new FileWriter(checkPoint_Info_txt, false);
        fileWriter.write("CheckPointID:Name:Location");
        for (CheckPoint checkPoint : checkPointArrayList){
            fileWriter.write(checkPoint.getDataString(checkPoint));
        }
        fileWriter.close();
    }

    public static class Record{
        private String employeeID;
        private String checkPointID;
        private LocalDate date;
        private LocalTime time;
        private File checkPoint_Record_txt = new File("src/Database/CheckPoint_Record.txt");
        public Record(){}

        public Record(String employeeID, String checkPointID, LocalDate date, LocalTime time) {
            this.employeeID = employeeID;
            this.checkPointID = checkPointID;
            this.date = date;
            this.time = time;
        }

        public String getEmployeeID() {
            return employeeID;
        }

        public void setEmployeeID(String employeeID) {
            this.employeeID = employeeID;
        }

        public String getCheckPointID() {
            return checkPointID;
        }

        public void setCheckPointID(String checkPointID) {
            this.checkPointID = checkPointID;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public LocalTime getTime() {
            return time;
        }

        public void setTime(LocalTime time) {
            this.time = time;
        }

        public ArrayList<CheckPoint.Record> getArrayList() throws IOException, ClassNotFoundException {
            ArrayList<CheckPoint.Record> recordArrayList = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
            Scanner scanner = new Scanner(checkPoint_Record_txt);
            scanner.nextLine();
            while (scanner.hasNextLine()){
                String[] data = scanner.nextLine().split(":", 4);
                CheckPoint.Record record = new CheckPoint.Record(data[0], data[1], LocalDate.parse(data[2], formatter), LocalTime.parse(data[3], timeFormatter));
                recordArrayList.add(record);
            }
            scanner.close();
            return recordArrayList;
        }

        public String getDataString(CheckPoint.Record record){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
            String[] data = {record.getEmployeeID(), record.getCheckPointID(), record.getDate().format(formatter), record.getTime().format(timeFormatter)};
            String dataLine = "";
            for (String dd : data){
                dataLine += dd + ":";
            }
            dataLine = dataLine.substring(0, dataLine.length() - 1);
            dataLine += '\n';
            return dataLine;
        }

        public void save_All_Record(ArrayList<CheckPoint.Record> recordArrayList) throws IOException {
            FileWriter fileWriter = new FileWriter(checkPoint_Record_txt, false);
            fileWriter.write("SecurityGuard Employee ID:CheckPointID:date:time");
            for (CheckPoint.Record record : recordArrayList){
                fileWriter.write(record.getDataString(record));
            }
            fileWriter.close();
        }

        public ArrayList<CheckPoint.Record> search_Record(String employee_CheckPoint_ID) throws IOException, ClassNotFoundException {
            CheckPoint.Record record = new Record();
            ArrayList<CheckPoint.Record> recordArrayList = record.getArrayList();
            ArrayList<CheckPoint.Record> returnInfoArrayList = new ArrayList<>();
            for (CheckPoint.Record record1 : recordArrayList){
                if (record1.getEmployeeID().equals(employee_CheckPoint_ID)){
                    returnInfoArrayList.add(record1);
                }else if (record1.getCheckPointID().equals(employee_CheckPoint_ID)){
                    returnInfoArrayList.add(record1);
                }
            }
            return returnInfoArrayList;
        }
        public ArrayList<CheckPoint.Record> search_Record(LocalDate date) throws IOException, ClassNotFoundException {
            CheckPoint.Record record = new Record();
            ArrayList<CheckPoint.Record> recordArrayList = record.getArrayList();
            for (CheckPoint.Record record1 : recordArrayList){
                if (!(record1.getDate().equals(date)))
                    recordArrayList.remove(record1);
            }
            return recordArrayList;
        }
        public ArrayList<CheckPoint.Record> search_Record(LocalTime time) throws IOException, ClassNotFoundException {
            CheckPoint.Record record = new Record();
            ArrayList<CheckPoint.Record> recordArrayList = record.getArrayList();
            for (CheckPoint.Record record1 : recordArrayList){
                if (!(record1.getTime().equals(time)))
                    recordArrayList.remove(record1);
            }
            return recordArrayList;
        }

        public String[] getStringArray(Record record){
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
            return new String[]{record.getEmployeeID(), record.getCheckPointID(), record.getDate().format(dateFormatter), record.getTime().format(timeFormatter)};
        }
    }
}
