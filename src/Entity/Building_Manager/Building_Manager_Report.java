package Entity.Building_Manager;

import Entity.CheckPoint;
import Entity.Complaint;
import Entity.Employee.Employee_Task;
import Entity.Employee.SecurityGuard.SecurityGuard;
import Entity.Executive.Account_Executive.Account_Executive_Function;
import Entity.Executive.Admin_Executive.Admin_Executive_Function;
import Entity.Executive.Building_Executive.Building_Executive_Function;
import Entity.Facility;
import Entity.Login.Login_Frame;
import UIPackage.Event.EventMenuSelected;
import UIPackage.Model.Model_Menu;
import UIPackage.main.UIFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Building_Manager_Report extends JFrame {
    public UIFrame frame;
    private String buildingManagerID;

    public String getBuildingManagerID() {
        return buildingManagerID;
    }

    public void setBuildingManagerID(String buildingManagerID) {
        this.buildingManagerID = buildingManagerID;
    }

    public void setPanelBorderRight(JScrollPane panel) {
        frame.panelBorderRight.removeAll();
        frame.panelBorderRight.add(panel);
        frame.panelBorderRight.revalidate();
        frame.repaint();
    }

    public Building_Manager_Report(String buildingManagerID) throws IOException, ClassNotFoundException {
        this.buildingManagerID = buildingManagerID;
        frame = new UIFrame(buildingManagerID);
        setPanelBorderRight(new reportPanel());

        frame.formHome.removeAll();
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("employee", "User Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("report", "Report", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("deposit", "Operation and Budget Planning", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Team Structure Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#f4791f");
        frame.menu.colorLeft = Color.decode("#659999");
        frame.setVisible(true);

        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException, ClassNotFoundException {
                if (index == 0) {
                    Building_Manager_Interface Building_ManagerInterface = new Building_Manager_Interface(buildingManagerID);
                    Building_ManagerInterface.frame.setVisible(true);
                    frame.dispose();
                } else if (index == 1) {
                    new Building_Manager_User_Management(buildingManagerID).run(buildingManagerID);
                    frame.dispose();
                } else if (index == 2) {
                } else if (index == 3) {
                    new Building_Manager_Operation(buildingManagerID).run(buildingManagerID);
                    frame.dispose();
                } else if (index == 4) {
                    new Entity.Building_Manager.Building_Manager_Team_Management(buildingManagerID).run(buildingManagerID);
                    frame.dispose();
                } else if (index == 5) {
                    new Login_Frame();
                    frame.dispose();
                }
            }
        });
    }

    public class reportPanel extends JScrollPane{
        public reportPanel() throws IOException, ClassNotFoundException {
            JFreeChart checkPointRecordChart = ChartFactory.createBarChart("Security Guard CheckPoint Record", "Security Guard ID", "Frequency", getCheckPointDataset(), PlotOrientation.VERTICAL, true, true, false);
            JFreeChart executivePieChart = ChartFactory.createPieChart("Executive User Number", getExecutiveDataset(), true, true, false);
            JFreeChart complaintPieChart = ChartFactory.createPieChart("User Complaint Frequency", getUserComplaintDataset(), true, true, false);
            JFreeChart taskPieChart = ChartFactory.createPieChart("Employee Task Amount", getEmployeeTaskDataset(), true, true, false);
            JFreeChart bookingChart = ChartFactory.createBarChart("Resident Booking Frequency", "Resident Username", "Frequency", getBookingDataset(), PlotOrientation.VERTICAL, true, true, false);
            JFreeChart visitorPieChart = ChartFactory.createPieChart("Visitor Pass Visiting Frequency", getVisitorDataset(), true, true, false);

            ChartPanel checkPointPanel = new ChartPanel(checkPointRecordChart);
            ChartPanel executivePanel = new ChartPanel(executivePieChart);
            ChartPanel bookingPanel = new ChartPanel(bookingChart);
            ChartPanel complaintPanel = new ChartPanel(complaintPieChart);
            ChartPanel visitorPanel = new ChartPanel(visitorPieChart);
            ChartPanel taskPanel = new ChartPanel(taskPieChart);

            JPanel contentPane = new JPanel();
            contentPane.setLayout(new GridLayout(6, 1, 10, 10));
            contentPane.add(checkPointPanel);
            contentPane.add(executivePanel);
            contentPane.add(bookingPanel);
            contentPane.add(complaintPanel);
            contentPane.add(visitorPanel);
            contentPane.add(taskPanel);

            setViewportView(contentPane);

            OutputStream out = Files.newOutputStream(Paths.get("src/ReportPNG/checkPoint.png"));
            writeAsPNG(checkPointRecordChart, out, 1280, 720);
            out = Files.newOutputStream(Paths.get("src/ReportPNG/executive.png"));
            writeAsPNG(executivePieChart, out, 1280, 720);
            out = Files.newOutputStream(Paths.get("src/ReportPNG/complaint.png"));
            writeAsPNG(complaintPieChart, out, 1280, 720);
            out = Files.newOutputStream(Paths.get("src/ReportPNG/task.png"));
            writeAsPNG(taskPieChart, out, 1280, 720);
            out = Files.newOutputStream(Paths.get("src/ReportPNG/booking.png"));
            writeAsPNG(bookingChart, out, 1280, 720);
            out = Files.newOutputStream(Paths.get("src/ReportPNG/visitor pass.png"));
            writeAsPNG(visitorPieChart, out, 1280, 720);
        }
        private CategoryDataset getCheckPointDataset() throws IOException, ClassNotFoundException {
            final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            ArrayList<Object[]> checkPointRecordData = getCheckPointRecordData();
            for (Object[] data : checkPointRecordData){
                dataset.addValue(Integer.valueOf(data[0].toString()), data[1].toString(), data[2].toString());
            }
            return dataset;
        }
        private PieDataset getExecutiveDataset() throws FileNotFoundException {
            DefaultPieDataset dataset = new DefaultPieDataset();
            int accountExecutiveNumber = new Account_Executive_Function.Account_Executive().getArrayList().size();
            int adminExecutiveNumber = new Admin_Executive_Function.Admin_Executive().getArrayList().size();
            int buildingExecutiveNumber = new Building_Executive_Function.Building_Executive().getArrayList().size();
            dataset.setValue("Account Executive", accountExecutiveNumber);
            dataset.setValue("Admin Executive", adminExecutiveNumber);
            dataset.setValue("Building Executive", buildingExecutiveNumber);

            return dataset;
        }

        private PieDataset getUserComplaintDataset() throws FileNotFoundException {
            ArrayList<Complaint> complaintArrayList = new Complaint().getArrayList();
            DefaultPieDataset dataset = new DefaultPieDataset();

            for (Complaint complaint : complaintArrayList){
                int total = 0;
                String username = "";
                for (Complaint complaint1 : complaintArrayList){
                    if (complaint.getResident_Username().equals(complaint1.getResident_Username())){
                        total += 1;
                        username = complaint1.getResident_Username();
                    }
                }
                dataset.setValue(username, total);
            }

            return dataset;
        }

        private PieDataset getEmployeeTaskDataset() throws FileNotFoundException {
            ArrayList<Employee_Task> employeeTaskArrayList = new Employee_Task().getArrayList();
            DefaultPieDataset dataset = new DefaultPieDataset();
            for (Employee_Task task : employeeTaskArrayList){
                int total = 0;
                String employeeID = task.getEmployeeID();
                for (Employee_Task task1 : employeeTaskArrayList){
                    if (task.getEmployeeID().equals(task1.getEmployeeID()))
                        total += 1;
                }
                dataset.setValue(employeeID, total);
            }
            return dataset;
        }

        private PieDataset getVisitorDataset() throws FileNotFoundException {
            ArrayList<String[]> recordArrayList = new SecurityGuard().get_all_Visitor_Entry_Record();
            DefaultPieDataset dataset = new DefaultPieDataset();
            for (String[] record : recordArrayList){
                int total = 0;
                String visitorPassID = record[0];
                for (String[] record1 : recordArrayList){
                    if (record[0].equals(record1[0]))
                        total += 1;
                }
                dataset.setValue(visitorPassID, total);
            }
            return dataset;
        }

        private CategoryDataset getBookingDataset() throws IOException, ClassNotFoundException {
            final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            ArrayList<Object[]> bookingRecordData = getBookingRecordData();
            for (Object[] data : bookingRecordData){
                dataset.addValue(Integer.valueOf(data[0].toString()), data[1].toString(), data[2].toString());
            }
            return dataset;
        }

        private ArrayList<Object[]> getCheckPointRecordData() throws IOException, ClassNotFoundException {
            ArrayList<CheckPoint> checkPointArrayList = new CheckPoint().getArrayList();
            ArrayList<CheckPoint.Record> checkPointRecord = new CheckPoint.Record().getArrayList();
            ArrayList<Object[]> dataArrayList = new ArrayList<>();
            for (CheckPoint checkPoint : checkPointArrayList){
                for (CheckPoint.Record record : checkPointRecord){
                    int total = 0;
                    String employeeID = "";
                    for (CheckPoint.Record record1 : checkPointRecord){
                        if (record.getCheckPointID().equals(checkPoint.getCheckPointID()) && record1.getEmployeeID().equals(record.getEmployeeID())){
                            total += 1;
                            employeeID = record1.getEmployeeID();
                        }
                    }
                    dataArrayList.add(new Object[]{total, checkPoint.getCheckPointID(), employeeID});
                }
            }
            return dataArrayList;
        }

        private ArrayList<Object[]> getBookingRecordData() throws IOException, ClassNotFoundException {
            ArrayList<Facility> facilityArrayList = new Facility().getArrayList();
            ArrayList<Facility.Booking> bookingArrayList = new Facility.Booking().getArrayList();
            ArrayList<Object[]> dataArrayList = new ArrayList<>();
            for (Facility facility : facilityArrayList){
                for (Facility.Booking booking : bookingArrayList){
                    int total = 0;
                    String residentUsername = booking.getResident_Username();
                    for (Facility.Booking booking1 : bookingArrayList){
                        if (booking.getFacilityID().equals(booking1.getFacilityID()) && booking1.getResident_Username().equals(booking.getResident_Username()))
                            total += 1;
                    }
                    dataArrayList.add(new Object[]{total, facility.getFacilityID(), residentUsername});
                }
            }
            return dataArrayList;
        }
        public void writeAsPNG(JFreeChart chart, OutputStream out, int width, int height ) {
            try {
                BufferedImage chartImage = chart.createBufferedImage( width, height, null);
                ImageIO.write( chartImage, "png", out );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Building_Manager_Report("BM01");
    }
}
