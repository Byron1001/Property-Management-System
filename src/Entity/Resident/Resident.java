package Entity.Resident;

import Entity.Complaint;
import Entity.Facility;
import Entity.Financial.Invoice;
import Entity.Financial.Payment;
import Entity.Financial.Statement;
import Entity.Unit;
import Entity.Visitor_Pass;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Resident {
    private String resident_Username;
    private String name;
    private char gender;
    private String contact_Number;
    private String unitID;
    private int payment;
    private static final File Resident_Information_txt = new File("src/Database/Resident_Information.txt");
    public Resident() {}

    public Resident(String resident_Username, String name, char gender, String contact_Number, String unitID, int payment) {
        this.resident_Username = resident_Username;
        this.name = name;
        this.gender = gender;
        this.contact_Number = contact_Number;
        this.unitID = unitID;
        this.payment = payment;
    }

    public String getResident_Username() {
        return resident_Username;
    }

    public void setResident_Username(String resident_Username) {
        this.resident_Username = resident_Username;
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

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public Resident get_Resident_Info(String resident_Username) throws FileNotFoundException {
        Resident resident = new Resident();
        resident.setResident_Username("0");
        ArrayList<Resident> residentArrayList = resident.getArrayList();
        for (Resident resident1 : residentArrayList){
            if (resident1.getResident_Username().equals(resident_Username))
                return resident1;
        }
        return resident;
    }

    public void update_Resident_Info(Resident resident, String resident_Username) throws IOException {
        ArrayList<Resident> residentArrayList = resident.getArrayList();
        for (Resident resident1 : residentArrayList){
            if (resident1.getResident_Username().equals(resident_Username))
                residentArrayList.remove(resident1);
        }
        residentArrayList.add(resident);
        resident.save_All_Resident(residentArrayList);
    }

    public ArrayList<Resident> getArrayList() throws FileNotFoundException {
        ArrayList<Resident> residentArrayList = new ArrayList<>();
        FileReader reader = new FileReader(Resident_Information_txt);
        Scanner scanner = new Scanner(reader);
        scanner.nextLine();
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 6);
            residentArrayList.add(new Resident(data[0], data[1], data[2].charAt(0), data[3], data[4], Integer.parseInt(data[5])));
        }
        return residentArrayList;
    }
    public void save_All_Resident(ArrayList<Resident> residentArrayList) throws IOException {
        FileWriter fileWriter = new FileWriter(Resident_Information_txt, false);
        fileWriter.write("Resident_Username:Name:Gender:Contact_Number:unitID:Payment\n");
        for (Resident resident : residentArrayList){
            String dataLine = resident.getDataString(resident);
            fileWriter.write(dataLine);
        }
        fileWriter.close();
    }

    public String getDataString(Resident resident){
        String[] data = {resident.getResident_Username(), resident.getName(), Character.toString(resident.getGender()), resident.getContact_Number(), resident.getUnitID(), Integer.valueOf(resident.getPayment()).toString()};
        String dataLine = "";
        for (String dd : data){
            dataLine += dd + ":";
        }
        dataLine = dataLine.substring(0, dataLine.length() - 1);
        dataLine += '\n';
        return dataLine;
    }

    public boolean check_Resident_Availability(String resident_Username) throws FileNotFoundException {
        boolean result = false;
        Resident Resident = new Resident();
        ArrayList<Resident> residentArrayList = Resident.getArrayList();
        for (Resident uni : residentArrayList) {
            if (uni.getResident_Username().equals(resident_Username))
                result = true;
        }
        return result;
    }

    public ArrayList<Invoice> get_Unit_All_Unpaid_Invoice(String unitID) throws FileNotFoundException {
        ArrayList<Invoice> invoiceArrayList = new Invoice().getArrayList();
        for (Invoice invoice : invoiceArrayList){
            if (!(invoice.getUnitID().equals(unitID)) && invoice.getStatus().equals("unpaid"))
                invoiceArrayList.remove(invoice);
        }
        return invoiceArrayList;
    }

    public ArrayList<Invoice> get_Unit_All_Invoice(String unitID) throws FileNotFoundException {
        ArrayList<Invoice> invoiceArrayList = new Invoice().getArrayList();
        for (Invoice invoice : invoiceArrayList){
            if (!(invoice.getUnitID().equals(unitID)))
                invoiceArrayList.remove(invoice);
        }
        return invoiceArrayList;
    }

    public int get_Unpaid_Amount(String resident_Username, String unitID) throws FileNotFoundException {
        Resident resident = new Resident();
        resident = resident.get_Resident_Info(resident_Username);
        Unit unit = new Unit();
        int total = -1;
        if(resident.check_Resident_Availability(resident_Username) && unit.check_Unit_Availability(unitID) && resident.getUnitID().equals(unitID)){
            Invoice invoice = new Invoice();
            ArrayList<Invoice> invoiceArrayList = invoice.getArrayList();
            for (Invoice invoice1 : invoiceArrayList){
                if (invoice1.getUnitID().equals(unitID) && invoice.getStatus().equals("unpaid")){
                    total += invoice1.getAmount();
                }
            }
            total += 1;
        }
        return total;
    }

    public void make_Payment(Invoice invoice, String pay_Username) throws IOException {
        Payment payment1 = new Payment();
        payment1.make_Payment(invoice, pay_Username);

        Resident resident = new Resident();
        ArrayList<Resident> residentArrayList = resident.getArrayList();
        for (Resident resident1 : residentArrayList){
            resident1.setPayment(resident1.get_Unpaid_Amount(resident1.getResident_Username(), resident1.getUnitID()));
        }
        resident.save_All_Resident(residentArrayList);
    }

    public ArrayList<Payment> get_All_Receipt(String unitID) throws FileNotFoundException {
        Payment payment = new Payment();
        ArrayList<Payment> paymentArrayList = payment.get_All_Receipt(unitID);
        return paymentArrayList;
    }

    public ArrayList<Payment> get_All_pending_Payment(String unitID) throws FileNotFoundException {
        Payment payment = new Payment();
        ArrayList<Payment> paymentArrayList = payment.getArrayList();
        for (Payment payment1 : paymentArrayList)
        {
            if (!payment1.getIssuerID().equals("") && !payment1.getUnitID().equals(unitID))
                paymentArrayList.remove(payment1);
        }
        return paymentArrayList;
    }

    public ArrayList<Statement> get_Statement_for_Resident(String resident_Username) throws FileNotFoundException {
        Statement statement = new Statement();
        return statement.get_Statement_for_Receiver(resident_Username);
    }

    public void add_Facility_Booking(Facility.Booking booking) throws IOException, ClassNotFoundException {
        ArrayList<Facility.Booking> bookingArrayList = booking.getArrayList();
        if (booking.check_Facility_Existence(booking.getFacilityID()) && booking.check_TimeSlot_Availability(booking)){
            bookingArrayList.add(booking);
            booking.save_All_Facility_Booking(bookingArrayList);
        }
    }

    public ArrayList<Facility.Booking> view_Resident_Booking(String resident_Username) throws IOException, ClassNotFoundException {
        ArrayList<Facility.Booking> bookingArrayList = new Facility.Booking().getArrayList();
        for (Facility.Booking booking : bookingArrayList) {
            if (!(booking.getResident_Username().equals(resident_Username)))
                bookingArrayList.remove(booking);
        }
        return bookingArrayList;
    }

    public void cancel_Facility_Booking(String bookingID) throws IOException, ClassNotFoundException {
        ArrayList<Facility.Booking> bookingArrayList = new Facility.Booking().getArrayList();
        for (Facility.Booking booking : bookingArrayList){
            if (booking.getBookingID().equals(bookingID))
                bookingArrayList.remove(booking);
        }
        new Facility.Booking().save_All_Facility_Booking(bookingArrayList);
    }

    public void update_Facility_Booking(Facility.Booking booking, String bookingID) throws IOException, ClassNotFoundException {
        cancel_Facility_Booking(bookingID);
        add_Facility_Booking(booking);
    }

    public void apply_Visitor_Pass(Visitor_Pass visitorPass) throws IOException {
        visitorPass.setStatus("Disapproved");
        ArrayList<Visitor_Pass> visitorPassArrayList = visitorPass.getArrayList();
        visitorPassArrayList.add(visitorPass);
        visitorPass.save_All_Visitor(visitorPassArrayList);
    }

    public ArrayList<Visitor_Pass> view_All_Visitor_Pass_Apply(String resident_Username) throws FileNotFoundException {
        Visitor_Pass visitorPass = new Visitor_Pass();
        ArrayList<Visitor_Pass> visitorPassArrayList = visitorPass.getArrayList();
        for (Visitor_Pass visitorPass1 : visitorPassArrayList){
            if (!visitorPass1.getResident_Username().equals(resident_Username))
                visitorPassArrayList.remove(visitorPass1);
        }
        return visitorPassArrayList;
    }

    public void cancel_Visitor_Pass(String visitor_Pass_ID) throws IOException {
        Visitor_Pass visitorPass = new Visitor_Pass();
        ArrayList<Visitor_Pass> visitorPassArrayList = visitorPass.getArrayList();
        for (Visitor_Pass visitorPass1 : visitorPassArrayList){
            if (visitorPass1.getVisitor_Pass_ID().equals(visitor_Pass_ID))
                visitorPassArrayList.remove(visitorPass1);
        }
        visitorPass.save_All_Visitor(visitorPassArrayList);
    }

    public void update_Visitor_Pass(Visitor_Pass visitorPass, String visitor_Pass_ID) throws IOException {
        cancel_Visitor_Pass(visitor_Pass_ID);
        apply_Visitor_Pass(visitorPass);
    }

    public void log_Complaint(Complaint complaint) throws IOException {
        ArrayList<Complaint> complaintArrayList = complaint.getArrayList();
        complaintArrayList.add(complaint);
        complaint.save_All_Complaint(complaintArrayList);
    }
    public ArrayList<Complaint> view_Complaint(String resident_Username) throws FileNotFoundException {
        Complaint complaint = new Complaint();
        ArrayList<Complaint> complaintArrayList =  complaint.getArrayList();
        for (Complaint complaint1 : complaintArrayList){
            if (!(complaint1.getResident_Username().equals(resident_Username))){
                complaintArrayList.remove(complaint1);
            }
        }
        return complaintArrayList;
    }
    public void update_Complaint(Complaint complaint, String complaintID) throws IOException {
        if (complaint.check_Complaint_Availability(complaintID)){
            cancel_Complaint(complaintID);
            log_Complaint(complaint);
        }
    }
    public void cancel_Complaint(String complaintID) throws IOException {
        Complaint complaint = new Complaint();
        ArrayList<Complaint> complaintArrayList = complaint.getArrayList();
        for (Complaint com : complaintArrayList){
            if (com.getComplaintID().equals(complaintID))
                complaintArrayList.remove(com);
        }
        complaint.save_All_Complaint(complaintArrayList);
    }



}