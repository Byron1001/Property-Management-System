package Entity;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Patrolling {
    private String patrolID;
    private String employeeID;
    private String day;
    private LocalTime start_Time;
    private LocalTime end_Time;
    private File patrolling_Schedule_Info_txt = new File("src/Database/Patrolling_Schedule_Information.txt");
    public enum DayName{
        Mon(1, "Monday"), Tue(2, "Tuesday"), Wed(3, "Wednesday"), Thu(4, "Thursday"), Fri(5, "Friday"), Sat(6, "Saturday"), Sun(7, "Sunday");
        private int value;
        private String nameOfDay;
        DayName(int value, String nameOfDay) {
            this.value = value;
            this.nameOfDay = nameOfDay;
        }
    }
    public Patrolling(){}

    public Patrolling(String patrolID, String employeeID, String day, LocalTime start_Time, LocalTime end_Time) {
        this.patrolID = patrolID;
        this.employeeID = employeeID;
        this.day = day;
        this.start_Time = start_Time;
        this.end_Time = end_Time;
    }

    public String getPatrolID() {
        return patrolID;
    }

    public void setPatrolID(String patrolID) {
        this.patrolID = patrolID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public LocalTime getStart_Time() {
        return start_Time;
    }

    public void setStart_Time(LocalTime start_Time) {
        this.start_Time = start_Time;
    }

    public LocalTime getEnd_Time() {
        return end_Time;
    }

    public void setEnd_Time(LocalTime end_Time) {
        this.end_Time = end_Time;
    }

    public ArrayList<Patrolling> getArrayList() throws IOException, ClassNotFoundException {
        ArrayList<Patrolling> patrollingArrayList = new ArrayList<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        Scanner scanner = new Scanner(patrolling_Schedule_Info_txt);
        scanner.nextLine();
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 5);
            Patrolling patrolling = new Patrolling(data[0], data[1], data[2], LocalTime.parse(data[3], timeFormatter), LocalTime.parse(data[4], timeFormatter));
            patrollingArrayList.add(patrolling);
        }
        scanner.close();
        return patrollingArrayList;
    }

    public boolean check_Patrolling_Existence(String patrolID) throws IOException, ClassNotFoundException {
        boolean result = false;
        ArrayList<Patrolling> patrollingArrayList = this.getArrayList();
        for (Patrolling patrolling : patrollingArrayList){
            if (patrolling.getPatrolID().equals(patrolID))
                result = true;
        }
        return result;
    }

    public String getDataString(Patrolling patrolling){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        String[] data = {patrolling.getPatrolID(), patrolling.getEmployeeID(), patrolling.getDay(), patrolling.getStart_Time().format(timeFormatter), patrolling.getEnd_Time().format(timeFormatter)};
        String dataLine = "";
        for (String dd : data){
            dataLine += dd + ":";
        }
        dataLine = dataLine.substring(0, dataLine.length() - 1);
        dataLine += '\n';
        return dataLine;
    }

    public void save_All_Patrolling(ArrayList<Patrolling> patrollingArrayList) throws IOException {
        FileWriter fileWriter = new FileWriter(patrolling_Schedule_Info_txt, false);
        fileWriter.write("PatrolID:Security EmployeeID:Day:Time Start:Time End");
        for (Patrolling patrolling : patrollingArrayList){
            fileWriter.write(patrolling.getDataString(patrolling));
        }
        fileWriter.close();
    }

    public ArrayList<Patrolling> get_SG_All_Patrolling(String employeeID) throws IOException, ClassNotFoundException {
        Patrolling patrolling = new Patrolling();
        ArrayList<Patrolling> patrollingArrayList = patrolling.getArrayList();
        for (Patrolling patrolling1 : patrollingArrayList){
            if (!patrolling1.getEmployeeID().equals(employeeID))
                patrollingArrayList.remove(patrolling1);
        }
        return patrollingArrayList;
    }

    public boolean check_TimeSlot_Availability(Patrolling new_Patrolling) throws IOException, ClassNotFoundException {
        boolean result = false;
        ArrayList<Patrolling> patrollingArrayList = new_Patrolling.getArrayList();
        for (Patrolling patrolling1 : patrollingArrayList){
            if (new_Patrolling.getStart_Time().isAfter(patrolling1.getEnd_Time()) || new_Patrolling.getEnd_Time().isBefore(patrolling1.getStart_Time()))
                result = true;
        }
        return result;
    }

    public String get_Auto_PatrollingID() throws FileNotFoundException {
        FileReader fileReader = new FileReader(patrolling_Schedule_Info_txt);
        Scanner scanner = new Scanner(fileReader);
        scanner.nextLine();
        Integer num = 0;
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 5);
            String number = data[0].substring(6);
            num = Integer.parseInt(number);
            num += 1;
        }
        String str = "Patrol" + num.toString();
        System.out.println(str);
        return str;
    }

}