package Entity;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Complaint {
    private String complaintID;
    private String resident_Username;
    private String description;
    private String status;
    private static final File complaint_txt = new File("src/Database/Complaint.txt");

    public Complaint() {}

    public Complaint(String complaintID, String resident_Username, String description, String status) {
        this.complaintID = complaintID;
        this.resident_Username = resident_Username;
        this.description = description;
        this.status = status;
    }

    public String getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(String complaintID) {
        this.complaintID = complaintID;
    }

    public String getResident_Username() {
        return resident_Username;
    }

    public void setResident_Username(String resident_Username) {
        this.resident_Username = resident_Username;
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

    public String[] getStringArray(Complaint complaint){
        return new String[]{complaint.getComplaintID(), complaint.getResident_Username(), complaint.getDescription(), complaint.getStatus()};
    }

    public ArrayList<Complaint> getArrayList() throws FileNotFoundException {
        ArrayList<Complaint> complaintArrayList = new ArrayList<>();
        FileReader reader = new FileReader(complaint_txt);
        Scanner scanner = new Scanner(reader);
        scanner.nextLine();
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 4);
            complaintArrayList.add(new Complaint(data[0], data[1], data[2], data[3]));
        }
        return complaintArrayList;
    }
    public void save_All_Complaint(ArrayList<Complaint> complaintArrayList) throws IOException {
        FileWriter fileWriter = new FileWriter(complaint_txt, false);
        fileWriter.write("ComplaintID:resident_Username:description:status\n");
        for (Complaint complaint : complaintArrayList){
            String[] data = {complaint.getComplaintID(), complaint.getResident_Username(), complaint.description, complaint.getStatus()};
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

    public String getDataString(Complaint complaint){
        String[] data = {complaint.getComplaintID(), complaint.getResident_Username(), complaint.description, complaint.getStatus()};
        String dataLine = "";
        for (String dd : data){
            dataLine += dd + ":";
        }
        dataLine = dataLine.substring(0, dataLine.length() - 1);
        dataLine += '\n';
        return dataLine;
    }
    public Complaint search_Complaint_Information(String complaintID) throws FileNotFoundException {
        ArrayList<Complaint> complaintArrayList = getArrayList();
        Complaint result = new Complaint();
        result.setComplaintID("0");
        for (Complaint com : complaintArrayList) {
            if (com.getComplaintID().equals(complaintID)) {
                result = com;
            }
        }
        return result;
    }
    public String get_Auto_ComplaintID() throws FileNotFoundException {
        FileReader fileReader = new FileReader(complaint_txt);
        Scanner scanner = new Scanner(fileReader);
        scanner.nextLine();
        Integer num = 0;
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 4);
            String number = data[0].substring(3);
            num = Integer.parseInt(number);
            num += 1;
        }
        return "Com" + num;
    }

    public boolean check_Complaint_Availability(String complaintID) throws FileNotFoundException {
        boolean result = false;
        Complaint complaint = new Complaint();
        ArrayList<Complaint> complaintArrayList = complaint.getArrayList();
        for (Complaint com : complaintArrayList) {
            if (com.getComplaintID().equals(complaintID)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
