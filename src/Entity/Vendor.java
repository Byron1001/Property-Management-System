package Entity;

import Entity.Financial.Invoice;
import Entity.Financial.Payment;
import Entity.Financial.Statement;
import org.jfree.chart.JFreeChart;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Vendor {
    private String vendor_Username;
    private String name;
    private char gender;
    private String contact_Number;
    private String vendor_Unit;
    private int monthly_payment;
    private int unpaid_payment;
    private static final File vendor_Information_txt = new File("src/Database/Vendor_Information.txt");
    public Vendor() {}

    public Vendor(String vendor_Username, String name, char gender, String contact_Number, String vendor_Unit, int monthly_payment, int unpaid_payment) {
        this.vendor_Username = vendor_Username;
        this.name = name;
        this.gender = gender;
        this.contact_Number = contact_Number;
        this.vendor_Unit = vendor_Unit;
        this.monthly_payment = monthly_payment;
        this.unpaid_payment = unpaid_payment;
    }

    public String getVendor_Username() {
        return vendor_Username;
    }

    public void setVendor_Username(String vendor_Username) {
        this.vendor_Username = vendor_Username;
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

    public String getVendor_Unit() {
        return vendor_Unit;
    }

    public void setVendor_Unit(String vendor_Unit) {
        this.vendor_Unit = vendor_Unit;
    }

    public int getMonthly_payment() {
        return monthly_payment;
    }

    public void setMonthly_payment(int monthly_payment) {
        this.monthly_payment = monthly_payment;
    }

    public int getUnpaid_payment() {
        return unpaid_payment;
    }

    public void setUnpaid_payment(int unpaid_payment) {
        this.unpaid_payment = unpaid_payment;
    }

    public Vendor get_Vendor_Info(String vendor_Username) throws FileNotFoundException {
        Vendor vendor = new Vendor();
        vendor.setVendor_Username("0");
        ArrayList<Vendor> vendorArrayList = vendor.getArrayList();
        for (Vendor vendor1 : vendorArrayList){
            if (vendor1.getVendor_Username().equals(vendor_Username))
                return vendor1;
        }
        return vendor;
    }

    public void update_Vendor_Info(Vendor vendor, String vendor_Username) throws IOException {
        ArrayList<Vendor> vendorArrayList = vendor.getArrayList();
        for (Vendor vendor1 : vendorArrayList){
            if (vendor1.getVendor_Username().equals(vendor_Username))
                vendorArrayList.remove(vendor1);
        }
        vendorArrayList.add(vendor);
        vendor.save_All_Vendor(vendorArrayList);
    }

    public ArrayList<Entity.Vendor> getArrayList() throws FileNotFoundException {
        ArrayList<Entity.Vendor> vendorArrayList = new ArrayList<>();
        FileReader reader = new FileReader(vendor_Information_txt);
        Scanner scanner = new Scanner(reader);
        scanner.nextLine();
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 7);
            vendorArrayList.add(new Vendor(data[0], data[1], data[2].charAt(0), data[3], data[4], Integer.parseInt(data[5]), Integer.parseInt(data[6])));
        }
        return vendorArrayList;
    }
    public void save_All_Vendor(ArrayList<Entity.Vendor> vendorArrayList) throws IOException {
        FileWriter fileWriter = new FileWriter(vendor_Information_txt, false);
        fileWriter.write("Vendor Username:Name:Gender:contact_number:vendor unit:monthly payment:unpaid payment\n");
        for (Entity.Vendor vendor : vendorArrayList){
            String dataLine = vendor.getDataString(vendor);
            fileWriter.write(dataLine);
        }
        fileWriter.close();
    }

    public String getDataString(Vendor vendor){
        String[] data = {vendor.getVendor_Username(), vendor.getName(), Character.toString(vendor.getGender()), vendor.getContact_Number(), vendor.getVendor_Unit(), Integer.valueOf(vendor.getMonthly_payment()).toString(), Integer.valueOf(vendor.getUnpaid_payment()).toString()};
        String dataLine = "";
        for (String dd : data){
            dataLine += dd + ":";
        }
        dataLine = dataLine.substring(0, dataLine.length() - 1);
        dataLine += '\n';
        return dataLine;
    }
    public Vendor search_Vendor_Information(String vendor_Username) throws FileNotFoundException {
        ArrayList<Entity.Vendor> vendorArrayList = getArrayList();
        Vendor result = new Vendor();
        result.setVendor_Username("0");
        for (Vendor u : vendorArrayList) {
            if (u.getVendor_Username().equals(vendor_Username)) {
                result = u;
            }
        }
        return result;
    }
    public boolean check_Vendor_Availability(String vendor_Username) throws FileNotFoundException {
        boolean result = false;
        Vendor vendor = new Vendor();
        ArrayList<Vendor> vendorArrayList = vendor.getArrayList();
        for (Vendor uni : vendorArrayList) {
            if (uni.getVendor_Username().equals(vendor_Username))
                result = true;
        }
        return result;
    }

    public boolean check_Vendor_Unit_Availability(String vendor_Unit) throws FileNotFoundException {
        boolean result = false;
        Vendor vendor = new Vendor();
        ArrayList<Vendor> vendorArrayList = vendor.getArrayList();
        for (Vendor uni : vendorArrayList) {
            if (uni.getVendor_Unit().equals(vendor_Unit))
                result = true;
        }
        return result;
    }

    public int get_Unpaid_Amount(String vendor_Username, String vendor_Unit) throws FileNotFoundException {
        Vendor vendor = new Vendor();
        vendor = vendor.get_Vendor_Info(vendor_Username);
        int total = -1;
        if(vendor.check_Vendor_Availability(vendor_Username) && vendor.check_Vendor_Unit_Availability(vendor_Unit) && vendor.getVendor_Unit().equals(vendor_Unit)){
            Invoice invoice = new Invoice();
            ArrayList<Invoice> invoiceArrayList = invoice.getArrayList();
            for (Invoice invoice1 : invoiceArrayList){
                if (invoice1.getUnitID().equals(vendor_Unit) && invoice.getStatus().equals("unpaid")){
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

        Vendor vendor = new Vendor();
        ArrayList<Vendor> vendorArrayList = vendor.getArrayList();
        for (Vendor vendor1 : vendorArrayList){
            vendor1.setUnpaid_payment(vendor1.get_Unpaid_Amount(vendor1.getVendor_Username(), vendor1.getVendor_Unit()));
        }
        vendor.save_All_Vendor(vendorArrayList);
    }

    public void change_Monthly_Payment_Rental(String vendor_Unit, int rental_Amount) throws IOException {
        ArrayList<Vendor> vendorArrayList = new Vendor().getArrayList();
        for (Vendor vendor : vendorArrayList){
            if (vendor.getVendor_Unit().equals(vendor_Unit)){
                vendor.setMonthly_payment(rental_Amount);
            }
        }
        save_All_Vendor(vendorArrayList);
    }

    public ArrayList<Payment> get_All_Receipt(String vendor_Unit) throws FileNotFoundException {
        Payment payment = new Payment();
        ArrayList<Payment> paymentArrayList = payment.get_All_Receipt(vendor_Unit);
        return paymentArrayList;
    }

    public ArrayList<Payment> get_All_pending_Payment(String vendor_Unit) throws FileNotFoundException {
        Payment payment = new Payment();
        ArrayList<Payment> paymentArrayList = payment.getArrayList();
        for (Payment payment1 : paymentArrayList)
        {
            if (!payment1.getIssuerID().equals("") && !payment1.getUnitID().equals(vendor_Unit))
                paymentArrayList.remove(payment1);
        }
        return paymentArrayList;
    }

    public ArrayList<Statement> get_Statement_for_Vendor(String vendor_Username) throws FileNotFoundException {
        Statement statement = new Statement();
        return statement.get_Statement_for_Receiver(vendor_Username);
    }

    public ArrayList<Invoice> get_Unit_All_Unpaid_Invoice(String vendor_Unit) throws FileNotFoundException {
        ArrayList<Invoice> invoiceArrayList = new Invoice().getArrayList();
        for (Invoice invoice : invoiceArrayList){
            if (!(invoice.getUnitID().equals(vendor_Unit)) && invoice.getStatus().equals("unpaid"))
                invoiceArrayList.remove(invoice);
        }
        return invoiceArrayList;
    }

    public ArrayList<Invoice> get_Unit_All_Invoice(String vendor_Unit) throws FileNotFoundException {
        ArrayList<Invoice> invoiceArrayList = new Invoice().getArrayList();
        for (Invoice invoice : invoiceArrayList){
            if (!(invoice.getUnitID().equals(vendor_Unit)))
                invoiceArrayList.remove(invoice);
        }
        return invoiceArrayList;
    }

    public ArrayList<String> vendor_Unit_rent(String vendor_Username) throws FileNotFoundException {
        ArrayList<Vendor> vendorArrayList = new Vendor().getArrayList();
        ArrayList<String> data = new ArrayList<>();
        for (Vendor vendor1 : vendorArrayList){
            if (vendor1.getVendor_Username().equals(vendor_Username))
                data.add(vendor1.getVendor_Unit());
        }
        return data;
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