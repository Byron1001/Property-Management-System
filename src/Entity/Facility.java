package Entity;

import Entity.Facility;
import Entity.Resident.Resident;

import javax.swing.text.DateFormatter;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Facility {
    private String facilityID;
    private String name;
    private File facility_Info_txt = new File("src/Database/Facility_Information.txt");
    public Facility(){}

    public Facility(String facilityID, String name) {
        this.facilityID = facilityID;
        this.name = name;
    }

    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Facility> getArrayList() throws IOException, ClassNotFoundException {
        ArrayList<Facility> facilityArrayList = new ArrayList<>();
        Scanner scanner = new Scanner(facility_Info_txt);
        scanner.nextLine();
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 2);
            Facility facility = new Facility(data[0], data[1]);
            facilityArrayList.add(facility);
        }
        scanner.close();
        return facilityArrayList;
    }

    public String[] getStringArray(Facility facility){
        return new String[]{facility.getFacilityID(), facility.getName()};
    }

    public boolean check_Facility_Availability(String facilityID) throws IOException, ClassNotFoundException {
        boolean result = false;
        ArrayList<Facility> facilityArrayList = this.getArrayList();
        for (Facility facility : facilityArrayList){
            if (facility.getFacilityID().equals(facilityID)){
                result = true;
            }
        }
        return result;
    }
    public String getDataString(Facility facility){
        String[] data = {facility.getFacilityID(), facility.getName()};
        String dataLine = "";
        for (String dd : data){
            dataLine += dd + ":";
        }
        dataLine = dataLine.substring(0, dataLine.length() - 1);
        dataLine += '\n';
        return dataLine;
    }

    public void save_All_Facility(ArrayList<Facility> facilityArrayList) throws IOException {
        FileWriter fileWriter = new FileWriter(facility_Info_txt, false);
        fileWriter.write("FacilityID:Name");
        for (Facility facility : facilityArrayList){
            fileWriter.write(facility.getDataString(facility));
        }
        fileWriter.close();
    }

    public static class Booking{
        private String bookingID;
        private String facilityID;
        private String resident_Username;
        private LocalDate date;
        private LocalTime start_Time;
        private LocalTime end_Time;
        private final File facility_Booking_Record_txt = new File("src/Database/Facility_Booking_Record.txt");
        public Booking(){}

        public Booking(String bookingID, String facilityID, String resident_Username, LocalDate date, LocalTime start_Time, LocalTime end_Time) {
            this.bookingID = bookingID;
            this.facilityID = facilityID;
            this.resident_Username = resident_Username;
            this.date = date;
            this.start_Time = start_Time;
            this.end_Time = end_Time;
        }

        public String getBookingID() {
            return bookingID;
        }

        public void setBookingID(String bookingID) {
            this.bookingID = bookingID;
        }

        public String getFacilityID() {
            return facilityID;
        }

        public void setFacilityID(String facilityID) {
            this.facilityID = facilityID;
        }

        public String getResident_Username() {
            return resident_Username;
        }

        public void setResident_Username(String resident_Username) {
            this.resident_Username = resident_Username;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
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

        public ArrayList<Facility.Booking> getArrayList() throws IOException, ClassNotFoundException {
            ArrayList<Facility.Booking> bookingArrayList = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
            Scanner scanner = new Scanner(facility_Booking_Record_txt);
            scanner.nextLine();
            while (scanner.hasNextLine()){
                String[] data = scanner.nextLine().split(":", 6);
                Facility.Booking booking = new Facility.Booking(data[0], data[1], data[2], LocalDate.parse(data[3], formatter), LocalTime.parse(data[4], timeFormatter), LocalTime.parse(data[5], timeFormatter));
                bookingArrayList.add(booking);
            }
            scanner.close();
            return bookingArrayList;
        }

        public String getDataString(Facility.Booking booking){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
            String[] data = {booking.getBookingID(), booking.getFacilityID(), booking.getResident_Username(), booking.getDate().format(formatter), booking.getStart_Time().format(timeFormatter), booking.getEnd_Time().format(timeFormatter)};
            String dataLine = "";
            for (String dd : data){
                dataLine += dd + ":";
            }
            dataLine = dataLine.substring(0, dataLine.length() - 1);
            dataLine += '\n';
            return dataLine;
        }

        public void save_All_Facility_Booking(ArrayList<Facility.Booking> bookingArrayList) throws IOException {
            FileWriter fileWriter = new FileWriter(facility_Booking_Record_txt, false);
            fileWriter.write("BookingID:FacilityID:Resident Username:Date:Start time:End time\n");
            for (Facility.Booking booking : bookingArrayList){
                fileWriter.write(booking.getDataString(booking));
            }
            fileWriter.close();
        }

        private ArrayList<Facility.Booking> getFacilityAllBooking(String facilityID) throws IOException, ClassNotFoundException {
            Facility.Booking booking = new Booking();
            ArrayList<Facility.Booking> bookingArrayList = booking.getArrayList();
            for (Facility.Booking booking1 : bookingArrayList){
                if (!booking1.getBookingID().equals(facilityID))
                    bookingArrayList.remove(booking1);
            }
            return bookingArrayList;
        }

        public boolean check_TimeSlot_Availability(Facility.Booking new_booking) throws IOException, ClassNotFoundException {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
            LocalTime closeTime = LocalTime.parse("220000", timeFormatter);
            LocalTime openTime = LocalTime.parse("090000", timeFormatter);
            ArrayList<Facility.Booking> bookingArrayList = new_booking.getArrayList();
            boolean result = true;
            if ((new_booking.getEnd_Time().isAfter(closeTime)) || (new_booking.getStart_Time().isBefore(openTime))) {
                result = false;
            } else {
                for (Booking booking1 : bookingArrayList) {
                    if (booking1.getDate().equals(new_booking.getDate())){
                        System.out.println("yo");
                    }
                    if (booking1.getFacilityID().equals(new_booking.getFacilityID()) && booking1.getDate().equals(new_booking.getDate())) {
                        if (!(booking1.getEnd_Time().isAfter(new_booking.getStart_Time())) || !(booking1.getStart_Time().isBefore(new_booking.getEnd_Time()))) {
                        } else {
                            result = false;
                        }
                    }
                }
            }
            return result;
        }

        public String get_Auto_BookingID() throws FileNotFoundException {
            FileReader fileReader = new FileReader(facility_Booking_Record_txt);
            Scanner scanner = new Scanner(fileReader);
            scanner.nextLine();
            Integer num = 0;
            while (scanner.hasNextLine()){
                String[] data = scanner.nextLine().split(":", 5);
                String number = data[0].substring(4);
                num = Integer.parseInt(number);
                num += 1;
            }
            String str = "Book" + num.toString();
            return str;
        }

        public String[] getStringArray(Booking booking){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
            String[] data = {booking.getBookingID(), booking.getFacilityID(), booking.getResident_Username(), booking.getDate().format(formatter), booking.getStart_Time().format(timeFormatter), booking.getEnd_Time().format(timeFormatter)};
            return data;
        }


    }
}
