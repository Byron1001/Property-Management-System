package Entity.Executive.Admin_Executive;

import Entity.*;
import Entity.Employee.*;
import Entity.Employee.SecurityGuard.SecurityGuard;
import Entity.Executive.Executive;
import Entity.Resident.Resident;
import Entity.Vendor.Vendor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin_Executive_Function {
    public static class Admin_Executive extends Executive {
        private final File admin_Executive_Info_txt = new File("src/Database/Admin_Executive_Information.txt");

        public Admin_Executive(){}
        public Admin_Executive(String admin_Executive_ID, String name, char gender, String contact_Number){
            super();
            String position = "Admin Executive";
            super.set_Info(admin_Executive_ID, name, gender, contact_Number, position);
        }

        public void update_Admin_Executive_Info(Admin_Executive adminExecutive, String executiveID) throws IOException {
            ArrayList<Admin_Executive> adminExecutiveArrayList = adminExecutive.getArrayList();
            ArrayList<Admin_Executive> adminExecutiveArrayList1 = new ArrayList<>();
            for (Admin_Executive adminExecutive1 : adminExecutiveArrayList){
                if (!(adminExecutive1.getExecutiveID().equals(executiveID)))
                    adminExecutiveArrayList1.add(adminExecutive1);
            }
            adminExecutiveArrayList1.add(adminExecutive);
            adminExecutive.save_All_Admin_Executive(adminExecutiveArrayList1);
        }

        //Unit management
        public void unit_Add(Unit unit) throws IOException {
            ArrayList<Unit> unitArrayList = unit.getArrayList();
            unitArrayList.add(unit);
            unit.save_All_Unit(unitArrayList);
        }
        public void unit_Modify(Unit unit, String unitID) throws IOException {
            if (unit.check_Unit_Availability(unitID)){
                unit_Delete(unitID);
                unit_Add(unit);
                unit.sort_All_Unit();
                JOptionPane.showMessageDialog(null, "Unit Details Modification success", "Modifying success added", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        public void unit_Delete(String unitID) throws IOException {
            Unit unit = new Unit();
            ArrayList<Unit> unitArrayList = unit.getArrayList();
            unitArrayList.removeIf(uni -> uni.getUnitID().equals(unitID));
            unit.save_All_Unit(unitArrayList);
            unit.sort_All_Unit();
        }

        // Resident Management
        public void resident_Add(Resident resident) throws IOException {
            ArrayList<Resident> residentArrayList = resident.getArrayList();
            residentArrayList.add(resident);
            resident.save_All_Resident(residentArrayList);
        }
        public void resident_Modify(Resident resident, String resident_Username) throws IOException {
            if (resident.check_Resident_Availability(resident_Username)){
                resident_Delete(resident_Username);
                resident_Add(resident);
                JOptionPane.showMessageDialog(null, "Resident Information modifying success", "Resident information modifying success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        public void resident_Delete(String resident_Username) throws IOException {
            Resident resident = new Resident();
            ArrayList<Resident> residentArrayList = resident.getArrayList();
            residentArrayList.removeIf(re -> re.getResident_Username().equals(resident_Username));
            resident.save_All_Resident(residentArrayList);
        }

        // Complaint Management
        public void complaint_Add(Complaint complaint) throws IOException {
            ArrayList<Complaint> complaintArrayList = complaint.getArrayList();
            complaintArrayList.add(complaint);
            complaint.save_All_Complaint(complaintArrayList);
        }
        public void complaint_Update(Complaint complaint, String complaintID) throws IOException {
            if (complaint.check_Complaint_Availability(complaintID)){
                complaint_Delete(complaintID);
                complaint_Add(complaint);
            }
        }
        public void complaint_Delete(String complaintID) throws IOException {
            Complaint complaint = new Complaint();
            ArrayList<Complaint> complaintArrayList = complaint.getArrayList();
            complaintArrayList.removeIf(com -> com.getComplaintID().equals(complaintID));
            complaint.save_All_Complaint(complaintArrayList);
        }
        public void complaint_Solved(String complaintID) throws IOException {
            if (new Complaint().check_Complaint_Availability(complaintID)){
                Complaint complaintNew = new Complaint().search_Complaint_Information(complaintID);
                complaintNew.setStatus("solved");
                complaint_Delete(complaintID);
                complaint_Add(complaintNew);
            }
        }

        // Employee Management System start from here
        public void add_Employee(Employee employee) throws IOException, ClassNotFoundException {
            String condition = employee.getEmployeeID().substring(0, 2);
            if (condition.equals("SG")){
                SecurityGuard securityGuard = new SecurityGuard();
                securityGuard.set_Info(employee.getEmployeeID(), employee.getName(), employee.getGender(), employee.getContact_Number(), employee.getSalary(), employee.getPosition_Name());
                ArrayList<SecurityGuard> securityGuardArrayList = securityGuard.getArrayList();
                securityGuardArrayList.add(securityGuard);
                securityGuard.save_All_SecurityGuard(securityGuardArrayList);
            }else if (condition.equals("CN")){
                Cleaner cleaner = new Cleaner();
                cleaner.set_Info(employee.getEmployeeID(), employee.getName(), employee.getGender(), employee.getContact_Number(), employee.getSalary(), employee.getPosition_Name());
                ArrayList<Cleaner> cleanerArrayList = cleaner.getArrayList();
                cleanerArrayList.add(cleaner);
                cleaner.save_All_Cleaner(cleanerArrayList);
            } else if (condition.equals("TN")){
                Technician technician = new Technician();
                technician.set_Info(employee.getEmployeeID(), employee.getName(), employee.getGender(), employee.getContact_Number(), employee.getSalary(), employee.getPosition_Name());
                ArrayList<Technician> technicianArrayList = technician.getArrayList();
                technicianArrayList.add(technician);
                technician.save_All_Technician(technicianArrayList);
            }
        }
        public ArrayList<Employee> view_All_Employee() throws IOException, ClassNotFoundException {
            ArrayList<Employee> employeeArrayList = new ArrayList<>();
            ArrayList<Technician> technicianArrayList = new Technician().getArrayList();
            ArrayList<Cleaner> cleanerArrayList = new Cleaner().getArrayList();
            ArrayList<SecurityGuard>securityGuardArrayList = new SecurityGuard().getArrayList();
            employeeArrayList.addAll(technicianArrayList);
            employeeArrayList.addAll(cleanerArrayList);
            employeeArrayList.addAll(securityGuardArrayList);
            return employeeArrayList;
        }
        public void delete_Employee(String employeeID) throws IOException, ClassNotFoundException {
            String condition = employeeID.substring(0, 2);
            if (condition.equals("SG")){
                SecurityGuard securityGuard = new SecurityGuard();
                ArrayList<SecurityGuard> securityGuardArrayList = securityGuard.getArrayList();
                securityGuardArrayList.removeIf(securityGuard1 -> securityGuard1.getEmployeeID().equals(employeeID));
                securityGuard.save_All_SecurityGuard(securityGuardArrayList);
            }else if (condition.equals("CN")){
                Cleaner cleaner = new Cleaner();
                ArrayList<Cleaner> cleanerArrayList = cleaner.getArrayList();
                cleanerArrayList.removeIf(cleaner1 -> cleaner1.getEmployeeID().equals(employeeID));
                cleaner.save_All_Cleaner(cleanerArrayList);
            } else if (condition.equals("TN")){
                Technician technician = new Technician();
                ArrayList<Technician> technicianArrayList = technician.getArrayList();
                technicianArrayList.removeIf(technician1 -> technician1.getEmployeeID().equals(employeeID));
                technician.save_All_Technician(technicianArrayList);
            }
        }

        public void update_Employee(Employee employee, String employeeID) throws IOException, ClassNotFoundException {
            delete_Employee(employeeID);
            add_Employee(employee);
        }

        // Facility Management System start from here
        public void add_Facility(Facility facility) throws IOException, ClassNotFoundException {
            ArrayList<Facility> facilityArrayList = facility.getArrayList();
            if (!facility.check_Facility_Availability(facility.getFacilityID()))
                facilityArrayList.add(facility);
            facility.save_All_Facility(facilityArrayList);
        }
        public void delete_Facility(String facilityID) throws IOException, ClassNotFoundException {
            Facility facility = new Facility();
            ArrayList<Facility> facilityArrayList = facility.getArrayList();
            facilityArrayList.removeIf(facility1 -> facility1.getFacilityID().equals(facilityID));
            facility.save_All_Facility(facilityArrayList);
        }

        public void update_Facility(Facility facility, String facilityID) throws IOException, ClassNotFoundException {
            delete_Facility(facilityID);
            add_Facility(facility);
        }

        // Facility Booking Management System start from here
        public void add_Booking(Facility.Booking booking) throws IOException, ClassNotFoundException {
            boolean check = booking.check_TimeSlot_Availability(booking);
            if (check) {
                ArrayList<Facility.Booking> bookingArrayList = booking.getArrayList();
                bookingArrayList.add(booking);
                booking.save_All_Facility_Booking(bookingArrayList);
            }
        }
        public void delete_Booking(String bookingID) throws IOException, ClassNotFoundException {
            Facility.Booking booking = new Facility.Booking();
            ArrayList<Facility.Booking> bookingArrayList = booking.getArrayList();
            bookingArrayList.removeIf(booking1 -> booking1.getBookingID().equals(bookingID));
            booking.save_All_Facility_Booking(bookingArrayList);
        }
        public void facility_Booking_Update(Facility.Booking booking, String bookingID) throws IOException, ClassNotFoundException {
            delete_Booking(bookingID);
            add_Booking(booking);
        }

        public ArrayList<Admin_Executive> getArrayList() throws FileNotFoundException {
            ArrayList<Admin_Executive> adminExecutiveArrayList = new ArrayList<>();
            FileReader reader = new FileReader(admin_Executive_Info_txt);
            Scanner scanner = new Scanner(reader);
            scanner.nextLine();
            while (scanner.hasNextLine()){
                String[] data = scanner.nextLine().split(":", 5);
                adminExecutiveArrayList.add(new Admin_Executive(data[0], data[1], data[2].charAt(0), data[3]));
            }
            return adminExecutiveArrayList;
        }
        public void save_All_Admin_Executive(ArrayList<Admin_Executive> adminExecutiveArrayList) throws IOException {
            FileWriter fileWriter = new FileWriter(admin_Executive_Info_txt, false);
            fileWriter.write("executiveID:Name:Gender:contact_number:position\n");
            for (Admin_Executive adminExecutive : adminExecutiveArrayList){
                String[] data = {adminExecutive.getExecutiveID(), adminExecutive.getName(), Character.toString(adminExecutive.getGender()), adminExecutive.getContact_Number(), adminExecutive.getPosition()};
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

        public String getDataString(Admin_Executive adminExecutive){
            String[] data = {adminExecutive.getExecutiveID(), adminExecutive.getName(), Character.toString(adminExecutive.getGender()), adminExecutive.getContact_Number(), adminExecutive.getPosition()};
            String dataLine = "";
            for (String dd : data){
                dataLine += dd + ":";
            }
            dataLine = dataLine.substring(0, dataLine.length() - 1);
            dataLine += '\n';
            return dataLine;
        }
        public Admin_Executive get_Admin_Executive_Info(String executiveID) throws FileNotFoundException {
            ArrayList<Admin_Executive> adminExecutiveArrayList = getArrayList();
            Admin_Executive result = new Admin_Executive();
            result.setExecutiveID("0");
            for (Admin_Executive adminExecutive : adminExecutiveArrayList) {
                if (adminExecutive.getExecutiveID().equals(executiveID)) {
                    result = adminExecutive;
                }
            }
            return result;
        }
        public boolean check_Admin_Executive_Availability(String executiveID) throws FileNotFoundException {
            boolean result = false;
            Admin_Executive adminExecutive = new Admin_Executive();
            ArrayList<Admin_Executive> adminExecutiveArrayList = adminExecutive.getArrayList();
            for (Admin_Executive adminExecutive1 : adminExecutiveArrayList) {
                if (adminExecutive1.getExecutiveID().equals(executiveID)) {
                    result = true;
                    break;
                }
            }
            return result;
        }

        //add
        public void vendor_Add(Vendor vendor) throws IOException {
            ArrayList<Vendor> vendorArrayList = vendor.getArrayList();
            vendorArrayList.add(vendor);
            vendor.save_All_Vendor(vendorArrayList);
        }
        public void vendor_Modify(Vendor vendor, String vendor_Username) throws IOException {
            String dataLine = vendor.getDataString(vendor);
            if (vendor.check_Vendor_Availability(vendor_Username)){
                vendor_Delete(vendor_Username);
                vendor_Add(vendor);
                JOptionPane.showMessageDialog(null, "Vendor Information modifying success", "Vendor information modifying success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        public void vendor_Delete(String vendor_Username) throws IOException {
            Vendor vendor = new Vendor();
            ArrayList<Vendor> vendorArrayList = vendor.getArrayList();
            vendorArrayList.removeIf(vendor1 -> vendor1.getVendor_Username().equals(vendor_Username));
            vendor.save_All_Vendor(vendorArrayList);
        }

        //Visitor Pass
        public ArrayList<Visitor_Pass> get_All_Disapproved_Visitor_Pass() throws FileNotFoundException {
            Visitor_Pass visitorPass = new Visitor_Pass();
            ArrayList<Visitor_Pass> visitorPassArrayList = visitorPass.getArrayList();
            visitorPassArrayList.removeIf(visitorPass1 -> !(visitorPass1.getStatus().equals("Disapproved")));
            return visitorPassArrayList;
        }

        public void approve_Visitor_Pass(String visitor_PassID) throws IOException {
            ArrayList<Visitor_Pass> visitorPassArrayList = new Visitor_Pass().getArrayList();
            for (Visitor_Pass visitorPass : visitorPassArrayList){
                if (visitorPass.getVisitor_Pass_ID().equals(visitor_PassID))
                    visitorPass.setStatus("Approved");
            }
            new Visitor_Pass().save_All_Visitor(visitorPassArrayList);
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
    public static ImageIcon toIcon(ImageIcon imgIcon, int w, int h){
        Image srcImg = imgIcon.getImage();
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return new ImageIcon(resizedImg);
    }
}
