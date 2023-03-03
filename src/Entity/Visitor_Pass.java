package Entity;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;

public class Visitor_Pass {
    private String visitor_Pass_ID;
    private String visitor_Name;
    private String resident_Username;
    private String unitID;
    private char gender;
    private String contact_Number;
    private LocalDate date_Start;
    private LocalDate date_End;
    private String status;
    private final File visitor_Pass_Info_txt = new File("src/Database/Visitor_Pass_Information.txt");
    public Visitor_Pass(){}

    public Visitor_Pass(String visitor_Pass_ID, String visitor_Name, String resident_Username, String unitID, char gender, String contact_Number, LocalDate date_Start, LocalDate date_End, String status) {
        this.visitor_Pass_ID = visitor_Pass_ID;
        this.visitor_Name = visitor_Name;
        this.resident_Username = resident_Username;
        this.unitID = unitID;
        this.gender = gender;
        this.contact_Number = contact_Number;
        this.date_Start = date_Start;
        this.date_End = date_End;
        this.status = status;
    }

    public String getVisitor_Pass_ID() {
        return visitor_Pass_ID;
    }

    public void setVisitor_Pass_ID(String visitor_Pass_ID) {
        this.visitor_Pass_ID = visitor_Pass_ID;
    }

    public String getVisitor_Name() {
        return visitor_Name;
    }

    public void setVisitor_Name(String visitor_Name) {
        this.visitor_Name = visitor_Name;
    }

    public String getResident_Username() {
        return resident_Username;
    }

    public void setResident_Username(String resident_Username) {
        this.resident_Username = resident_Username;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
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

    public LocalDate getDate_Start() {
        return date_Start;
    }

    public void setDate_Start(LocalDate date_Start) {
        this.date_Start = date_Start;
    }

    public LocalDate getDate_End() {
        return date_End;
    }

    public void setDate_End(LocalDate date_End) {
        this.date_End = date_End;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String[] getStringArray(Visitor_Pass visitorPass){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        return new String[]{visitorPass.getVisitor_Pass_ID(), visitorPass.getVisitor_Name(), visitorPass.getResident_Username(), visitorPass.getUnitID(), Character.toString(visitorPass.getGender()), visitorPass.getContact_Number(), visitorPass.getDate_Start().format(formatter), visitorPass.getDate_End().format(formatter), visitorPass.getStatus()};
    }

    public ArrayList<Visitor_Pass> getArrayList() throws FileNotFoundException {
        ArrayList<Visitor_Pass> visitorPassArrayList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        Scanner scanner = new Scanner(visitor_Pass_Info_txt);
        scanner.nextLine();
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 9);
            Visitor_Pass visitor_Pass = new Visitor_Pass(data[0], data[1], data[2], data[3], data[4].charAt(0), data[5], LocalDate.parse(data[6], formatter), LocalDate.parse(data[7], formatter), data[8]);
            visitorPassArrayList.add(visitor_Pass);
        }
        scanner.close();
        return visitorPassArrayList;
    }

    public boolean check_Visitor_Pass_Availability(String visitor_Pass_ID) throws FileNotFoundException {
        boolean result = false;
        ArrayList<Visitor_Pass> visitorPassArrayList = getArrayList();
        for (Visitor_Pass visitor_Pass : visitorPassArrayList){
            if (visitor_Pass.getVisitor_Pass_ID().equals(visitor_Pass_ID)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean check_Visitor_Pass_Validity(String visitor_Pass_ID) throws FileNotFoundException {
        boolean result = false;
        LocalDate dateNow = LocalDate.now();
        ArrayList<Visitor_Pass> visitorPassArrayList = this.getArrayList();
        for (Visitor_Pass visitor_Pass : visitorPassArrayList){
            if (visitor_Pass.getDate_End().isAfter(dateNow) && visitor_Pass.getVisitor_Pass_ID().equals(visitor_Pass_ID)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public String getDataString(Visitor_Pass visitorPass){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        String[] data = {visitorPass.getVisitor_Pass_ID(), visitorPass.getVisitor_Name(), visitorPass.getResident_Username(), visitorPass.getUnitID(), Character.toString(visitorPass.getGender()), visitorPass.getContact_Number(), visitorPass.getDate_Start().format(formatter), visitorPass.getDate_End().format(formatter), visitorPass.getStatus()};
        String dataLine = "";
        for (String dd : data){
            dataLine += dd + ":";
        }
        dataLine = dataLine.substring(0, dataLine.length() - 1);
        dataLine += '\n';
        return dataLine;
    }

    public void save_All_Visitor(ArrayList<Visitor_Pass> visitorPassArrayList) throws IOException {
        FileWriter fileWriter = new FileWriter(visitor_Pass_Info_txt, false);
        fileWriter.write("Visitor PassID:Visitor Name:resident Username: unitID:Gender:Contact_Number:Date Start:Date End:Status\n");
        for (Visitor_Pass visitorPass : visitorPassArrayList){
            fileWriter.write(visitorPass.getDataString(visitorPass));
        }
        fileWriter.close();
    }
    public Visitor_Pass search_Visitor_Pass_Info(String visitor_Pass_ID) throws FileNotFoundException {
        Visitor_Pass visitorPass = new Visitor_Pass();
        visitorPass.setVisitor_Pass_ID("0");
        ArrayList<Visitor_Pass> visitorPassArrayList = visitorPass.getArrayList();
        for (Visitor_Pass visitorPass1 : visitorPassArrayList){
            if (visitorPass1.getVisitor_Pass_ID().equals(visitor_Pass_ID)){
                return visitorPass1;
            }
        }
        return visitorPass;
    }

    public Visitor_Pass search_Visitor_Pass_Info_by_Name(String visitor_Name) throws FileNotFoundException {
        Visitor_Pass visitorPass = new Visitor_Pass();
        visitorPass.setVisitor_Pass_ID("0");
        ArrayList<Visitor_Pass> visitorPassArrayList = visitorPass.getArrayList();
        for (Visitor_Pass visitorPass1 : visitorPassArrayList){
            if (visitorPass1.getVisitor_Name().equals(visitor_Name)){
                return visitorPass1;
            }
        }
        return visitorPass;
    }

    public String get_Auto_Visitor_Pass_ID() throws FileNotFoundException {
        FileReader fileReader = new FileReader(visitor_Pass_Info_txt);
        Scanner scanner = new Scanner(fileReader);
        scanner.nextLine();
        Integer num = 0;
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 9);
            String number = data[0].substring(2);
            num = Integer.parseInt(number);
            num += 1;
        }
        return "VI" + num;
    }

}
