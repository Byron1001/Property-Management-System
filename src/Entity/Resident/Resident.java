package Entity.Resident;

import Entity.Complaint;
import Entity.Facility;
import Entity.Financial.Invoice;
import Entity.Financial.Payment;
import Entity.Financial.Statement;
import Entity.Unit;
import Entity.Visitor_Pass;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
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
        ArrayList<Resident> residentArrayList1 = new ArrayList<>();
        for (Resident resident1 : residentArrayList){
            if (!(resident1.getResident_Username().equals(resident_Username)))
                residentArrayList1.add(resident1);
        }
        residentArrayList1.add(resident);
        resident.save_All_Resident(residentArrayList1);
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

    public String[] getStringArray(Resident resident){
        return new String[]{resident.getResident_Username(), resident.getName(), Character.toString(resident.getGender()), resident.getContact_Number(), resident.getUnitID(), Integer.valueOf(resident.getPayment()).toString()};
    }

    public boolean check_Resident_Availability(String resident_Username) throws FileNotFoundException {
        boolean result = false;
        Resident Resident = new Resident();
        ArrayList<Resident> residentArrayList = Resident.getArrayList();
        for (Resident uni : residentArrayList) {
            if (uni.getResident_Username().equals(resident_Username)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean check_Resident_Contact_Number_Availability(String contact_Number) throws FileNotFoundException {
        boolean result = false;
        Resident Resident = new Resident();
        ArrayList<Resident> residentArrayList = Resident.getArrayList();
        for (Resident uni : residentArrayList) {
            if (uni.getContact_Number().equals(contact_Number)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public ArrayList<Invoice> get_Unit_All_Unpaid_Invoice(String unitID) throws FileNotFoundException {
        ArrayList<Invoice> invoiceArrayList = new Invoice().getArrayList();
        ArrayList<Invoice> invoiceArrayList1 = new ArrayList<>();
        for (Invoice invoice : invoiceArrayList){
            if (invoice.getUnitID().equals(unitID) && invoice.getStatus().equals("unpaid"))
                invoiceArrayList1.add(invoice);
        }
        return invoiceArrayList1;
    }

    public ArrayList<Invoice> get_Unit_All_Invoice(String unitID) throws FileNotFoundException {
        ArrayList<Invoice> invoiceArrayList = new Invoice().getArrayList();
        ArrayList<Invoice> invoiceArrayList1 = new ArrayList<>();
        for (Invoice invoice : invoiceArrayList){
            if (invoice.getUnitID().equals(unitID))
                invoiceArrayList1.add(invoice);
        }
        return invoiceArrayList1;
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
                if (invoice1.getUnitID().equals(unitID) && invoice1.getStatus().equals("unpaid")){
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
        return payment.get_All_Receipt(unitID);
    }

    public ArrayList<Payment> get_All_pending_Payment(String unitID) throws FileNotFoundException {
        Payment payment = new Payment();
        ArrayList<Payment> paymentArrayList = payment.getArrayList();
        ArrayList<Payment> paymentArrayList1 = new ArrayList<>();
        for (Payment payment1 : paymentArrayList)
        {
            if (payment1.getIssuerID().equals("") && !payment1.getUnitID().equals(unitID)) {
                paymentArrayList1.add(payment1);
            }
        }
        return paymentArrayList1;
    }

    public ArrayList<Statement> get_Statement_for_Resident(String resident_Username) throws FileNotFoundException {
        Statement statement = new Statement();
        return statement.get_Statement_for_Receiver(resident_Username);
    }

    public void add_Facility_Booking(Facility.Booking booking) throws IOException, ClassNotFoundException {
        ArrayList<Facility.Booking> bookingArrayList = booking.getArrayList();
        if (new Facility().check_Facility_Availability(booking.getFacilityID()) && booking.check_TimeSlot_Availability(booking)){
            bookingArrayList.add(booking);
            System.out.println("pass");
            booking.save_All_Facility_Booking(bookingArrayList);
        }
    }

    public ArrayList<Facility.Booking> view_Resident_Booking(String resident_Username) throws IOException, ClassNotFoundException {
        ArrayList<Facility.Booking> bookingArrayList = new Facility.Booking().getArrayList();
        ArrayList<Facility.Booking> bookingArrayList1 = new ArrayList<>();
        for (Facility.Booking booking : bookingArrayList) {
            if (booking.getResident_Username().equals(resident_Username))
                bookingArrayList1.add(booking);
        }
        return bookingArrayList1;
    }

    public void cancel_Facility_Booking(String bookingID) throws IOException, ClassNotFoundException {
        ArrayList<Facility.Booking> bookingArrayList = new Facility.Booking().getArrayList();
        ArrayList<Facility.Booking> bookingArrayList1 = new ArrayList<>();
        for (Facility.Booking booking : bookingArrayList){
            if (!(booking.getBookingID().equals(bookingID)))
                bookingArrayList1.add(booking);
        }
        new Facility.Booking().save_All_Facility_Booking(bookingArrayList1);
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
        ArrayList<Visitor_Pass> visitorPassesReturn = new ArrayList<>();
        for (Visitor_Pass visitorPass1 : visitorPassArrayList){
            if (visitorPass1.getResident_Username().equals(resident_Username))
                visitorPassesReturn.add(visitorPass1);
        }
        return visitorPassesReturn;
    }

    public void cancel_Visitor_Pass(String visitor_Pass_ID) throws IOException {
        Visitor_Pass visitorPass = new Visitor_Pass();
        ArrayList<Visitor_Pass> visitorPassArrayList = visitorPass.getArrayList();
        ArrayList<Visitor_Pass> visitorPassArrayList1 = new ArrayList<>();
        for (Visitor_Pass visitorPass1 : visitorPassArrayList){
            if (!(visitorPass1.getVisitor_Pass_ID().equals(visitor_Pass_ID)))
                visitorPassArrayList1.add(visitorPass1);
        }
        visitorPass.save_All_Visitor(visitorPassArrayList1);
    }

    public void update_Visitor_Pass(Visitor_Pass visitorPass) throws IOException {
        cancel_Visitor_Pass(visitorPass.getVisitor_Pass_ID());
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
        ArrayList<Complaint> complaintArrayList1 = new ArrayList<>();
        for (Complaint complaint1 : complaintArrayList){
            if (complaint1.getResident_Username().equals(resident_Username)){
                complaintArrayList1.add(complaint1);
            }
        }
        return complaintArrayList1;
    }
    public void update_Complaint(Complaint complaint) throws IOException {
        if (complaint.check_Complaint_Availability(complaint.getComplaintID())){
            cancel_Complaint(complaint.getComplaintID());
            log_Complaint(complaint);
        }
    }
    public void cancel_Complaint(String complaintID) throws IOException {
        Complaint complaint = new Complaint();
        ArrayList<Complaint> complaintArrayList = complaint.getArrayList();
        complaintArrayList.removeIf(com -> com.getComplaintID().equals(complaintID));
        complaint.save_All_Complaint(complaintArrayList);
    }

    public static ImageIcon toIcon(ImageIcon imgIcon, int w, int h){
        Image srcImg = imgIcon.getImage();
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return new ImageIcon(resizedImg);
    }

    public static class Button extends JButton{
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
