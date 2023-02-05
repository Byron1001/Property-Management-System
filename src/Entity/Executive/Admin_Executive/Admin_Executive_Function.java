package Entity.Executive.Admin_Executive;

import Entity.*;
import Entity.Employee.*;
import Entity.Executive.Executive;
import Entity.Resident.Resident;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin_Executive_Function {
    public static class Admin_Executive extends Executive {
        private String position = "Admin Executive";
        private File unit_Information_txt = new File("src/Database/Unit_Information.txt");
        private File admin_Executive_Info_txt = new File("src/Database/Admin_Executive_Information.txt");

        public Admin_Executive(){}
        public Admin_Executive(String admin_Executive_ID, String name, char gender, String contact_Number){
            super();
            super.set_Info(admin_Executive_ID, name, gender, contact_Number, position);
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
                System.out.println("Successfully modify unit.");
            }
        }
        public void unit_Delete(String unitID) throws IOException {
            Unit unit = new Unit();
            ArrayList<Unit> unitArrayList = unit.getArrayList();
            for (Unit uni : unitArrayList){
                if (uni.getUnitID().equals(unitID))
                    unitArrayList.remove(uni);
            }
            unit.save_All_Unit(unitArrayList);
            unit.sort_All_Unit();
        }

        public ArrayList<Unit> unit_View_All() throws FileNotFoundException {
            Unit unit = new Unit();
            ArrayList<Unit> unitArrayList = unit.getArrayList();
            return unitArrayList;
        }

        // Resident Management
        public void resident_Add(Resident resident) throws IOException {
            ArrayList<Resident> residentArrayList = resident.getArrayList();
            residentArrayList.add(resident);
            resident.save_All_Resident(residentArrayList);
        }
        public void resident_Modify(Resident resident, String resident_Username) throws IOException {
            String dataLine = resident.getDataString(resident);
            if (resident.check_Resident_Availability(resident_Username)){
                resident_Delete(resident_Username);
                resident_Add(resident);
                System.out.println("Successfully modify resident information.");
            }
        }
        public void resident_Delete(String resident_Username) throws IOException {
            Resident resident = new Resident();
            ArrayList<Resident> residentArrayList = resident.getArrayList();
            for (Resident re : residentArrayList){
                if (re.getResident_Username().equals(resident_Username))
                    residentArrayList.remove(re);
            }
            resident.save_All_Resident(residentArrayList);
        }
        public ArrayList<Resident> resident_View_All(String resident_Username) throws FileNotFoundException {
            Resident resident = new Resident();
            ArrayList<Resident> residentArrayList = resident.getArrayList();
            return residentArrayList;
        }

        public Resident search_Resident_Info(String resident_Username) throws FileNotFoundException {
            return new Resident().get_Resident_Info(resident_Username);
        }

        // Complaint Management
        public void complaint_Add(Complaint complaint) throws IOException {
            ArrayList<Complaint> complaintArrayList = complaint.getArrayList();
            complaintArrayList.add(complaint);
            complaint.save_All_Complaint(complaintArrayList);
        }
        public ArrayList<Complaint> complaint_View_All() throws FileNotFoundException {
            Complaint complaint = new Complaint();
            ArrayList<Complaint> complaintArrayList =  complaint.getArrayList();
            return complaintArrayList;
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
            for (Complaint com : complaintArrayList){
                if (com.getComplaintID().equals(complaintID))
                    complaintArrayList.remove(com);
            }
            complaint.save_All_Complaint(complaintArrayList);
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
                for (SecurityGuard securityGuard1 : securityGuardArrayList){
                    if (securityGuard1.getEmployeeID().equals(employeeID))
                        securityGuardArrayList.remove(securityGuard1);
                }
                securityGuard.save_All_SecurityGuard(securityGuardArrayList);
            }else if (condition.equals("CN")){
                Cleaner cleaner = new Cleaner();
                ArrayList<Cleaner> cleanerArrayList = cleaner.getArrayList();
                for (Cleaner cleaner1 : cleanerArrayList){
                    if (cleaner1.getEmployeeID().equals(employeeID))
                        cleanerArrayList.remove(cleaner1);
                }
                cleaner.save_All_Cleaner(cleanerArrayList);
            } else if (condition.equals("TN")){
                Technician technician = new Technician();
                ArrayList<Technician> technicianArrayList = technician.getArrayList();
                for (Technician technician1 : technicianArrayList){
                    if (technician1.getEmployeeID().equals(employeeID))
                        technicianArrayList.remove(technician1);
                }
                technician.save_All_Technician(technicianArrayList);
            }
        }

        public void update_Employee(Employee employee, String employeeID) throws IOException, ClassNotFoundException {
            delete_Employee(employeeID);
            add_Employee(employee);
        }

        // Facility Management System start from here
        public static void add_Facility(Facility facility) throws IOException, ClassNotFoundException {
            ArrayList<Facility> facilityArrayList = facility.getArrayList();
            if (!facility.check_Facility_Availability(facility.getFacilityID()))
                facilityArrayList.add(facility);
            facility.save_All_Facility(facilityArrayList);
        }
        public static void delete_Facility(String facilityID) throws IOException, ClassNotFoundException {
            Facility facility = new Facility();
            ArrayList<Facility> facilityArrayList = facility.getArrayList();
            for (Facility facility1 : facilityArrayList){
                if (facility1.getFacilityID().equals(facilityID))
                    facilityArrayList.remove(facility1);
            }
            facility.save_All_Facility(facilityArrayList);
        }

        public static void update_Facility(Facility facility, String facilityID) throws IOException, ClassNotFoundException {
            delete_Facility(facilityID);
            add_Facility(facility);
        }
        public static ArrayList<Facility> view_All_Facility() throws IOException, ClassNotFoundException {
            Facility facility = new Facility();
            ArrayList<Facility> facilityArrayList = facility.getArrayList();
            return facilityArrayList;
        }

        // Facility Booking Management System start from here
        public boolean add_Booking(Facility.Booking booking) throws IOException, ClassNotFoundException {
            boolean check = booking.check_TimeSlot_Availability(booking);
            if (check) {
                ArrayList<Facility.Booking> bookingArrayList = booking.getArrayList();
                bookingArrayList.add(booking);
                booking.save_All_Facility_Booking(bookingArrayList);
            }
            return check;
        }
        public void delete_Booking(String bookingID) throws IOException, ClassNotFoundException {
            Facility.Booking booking = new Facility.Booking();
            ArrayList<Facility.Booking> bookingArrayList = booking.getArrayList();
            for (Facility.Booking booking1 : bookingArrayList){
                if (booking1.getBookingID().equals(bookingID))
                    bookingArrayList.remove(booking1);
            }
            booking.save_All_Facility_Booking(bookingArrayList);
        }
        public void facility_Booking_Management_Update(Facility.Booking booking, String bookingID) throws IOException, ClassNotFoundException {
            delete_Booking(bookingID);
            add_Booking(booking);
        }

        public static ArrayList<Facility.Booking> view_All_Booking() throws IOException, ClassNotFoundException {
            Facility.Booking booking = new Facility.Booking();
            ArrayList<Facility.Booking> bookingArrayList = booking.getArrayList();
            return bookingArrayList;
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
                if (adminExecutive1.getExecutiveID().equals(executiveID))
                    result = true;
            }
            return result;
        }

        public ArrayList<Visitor_Pass> get_All_Disapproved_Visitor_Pass() throws FileNotFoundException {
            Visitor_Pass visitorPass = new Visitor_Pass();
            ArrayList<Visitor_Pass> visitorPassArrayList = visitorPass.getArrayList();
            for (Visitor_Pass visitorPass1 : visitorPassArrayList){
                if (!(visitorPass1.getStatus().equals("Disapproved")))
                    visitorPassArrayList.remove(visitorPass1);
            }
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
}
