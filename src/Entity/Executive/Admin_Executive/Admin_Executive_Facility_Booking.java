package Entity.Executive.Admin_Executive;

import Entity.Facility;
import Entity.Login.Login_Frame;
import Entity.Resident.Resident;
import UIPackage.Component.Header;
import UIPackage.Component.Menu;
import UIPackage.Event.EventMenuSelected;
import UIPackage.Form.Form_Home;
import UIPackage.Model.Model_Menu;
import UIPackage.swing.PanelBorder;
import UIPackage.swing.Table;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Admin_Executive_Facility_Booking extends JFrame {
    public PanelBorder panelBorderLeft, panelBorderRight, panelBorderIn;
    public UIPackage.Component.Menu menu = new Menu();
    public Header header = new Header();
    public Form_Home formHome = new Form_Home();
    public Table tableData = new Table();
    public Color backgroundColor = Color.WHITE;
    public String executiveID;
    public JScrollPane scrollPane;
    public GridBagConstraints constraints;
    public JPanel panel;
    public Admin_Executive_Function.Button bookButton, deleteButton, viewButton, modifyButton;

    public Admin_Executive_Facility_Booking(String executiveID) throws IOException, ClassNotFoundException {
        this.executiveID = executiveID;
        menu.initMoving(Entity.Executive.Admin_Executive.Admin_Executive_Facility_Booking.this);

        formHome.setBackground(backgroundColor);

        panelBorderLeft = new PanelBorder();
        panelBorderRight = new PanelBorder();
        setLayout(new BorderLayout());
        panelBorderRight.setLayout(new BorderLayout());
        panelBorderLeft.setLayout(new GridLayout(1, 1));

        panelBorderLeft.add(menu);
        panelBorderRight.setBackground(backgroundColor);
        panelBorderRight.add(header, BorderLayout.PAGE_START);

        panelBorderIn = new PanelBorder();
        panelBorderIn.setBackground(backgroundColor);
        panelBorderIn.setLayout(new GridBagLayout());

        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), executiveID, TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(new Font("sanserif", Font.BOLD, 18));
        border.setTitleColor(Color.lightGray);
        panelBorderIn.setBorder(border);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JTableHeader header1 = tableData.getTableHeader();
        header1.setReorderingAllowed(false);
        header1.setResizingAllowed(false);
        header1.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (column != header1.getColumnModel().getColumnCount()) {
                    Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    com.setBackground(backgroundColor);
                    setBorder(noFocusBorder);
                    com.setForeground(new Color(102, 102, 102));
                    com.setFont(new Font("sansserif", Font.BOLD, 16));
                    setHorizontalAlignment(JLabel.CENTER);
                    return com;
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(backgroundColor);
        panel.add(tableData);

        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setViewportView(panel);

        panelBorderIn.add(header1, constraints);
        constraints.gridy++;
        panelBorderIn.add(scrollPane, constraints);

        panelBorderRight.add(panelBorderIn, BorderLayout.CENTER);
        panelBorderRight.add(formHome, BorderLayout.PAGE_END);

        add(panelBorderLeft, BorderLayout.LINE_START);
        add(panelBorderRight, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4, 40, 15));
        buttonPanel.setBackground(Color.white);

        bookButton = new Admin_Executive_Function.Button("Make new booking");
        modifyButton = new Admin_Executive_Function.Button("Modify Booking Info");
        deleteButton = new Admin_Executive_Function.Button("Delete Booking");
        viewButton = new Admin_Executive_Function.Button("View Booking details");

        buttonPanel.add(bookButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        constraints.gridy++;
        panelBorderIn.add(buttonPanel, constraints);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);

        ArrayList<Facility.Booking> bookingArrayList = new Facility.Booking().getArrayList();
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Admin_Executive_Facility_Booking.bookFrame().setVisible(true);
                    dispose();
                } catch (IOException | ClassNotFoundException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                if (row != -1){
                    try {
                        Facility.Booking bookingSelected = bookingArrayList.get(row);
                        new Entity.Executive.Admin_Executive.Admin_Executive_Facility_Booking.modifyFrame(bookingSelected).setVisible(true);
                        dispose();
                    } catch (IOException | ClassNotFoundException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the booking details", "Choice error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Facility.Booking bookingSelected = bookingArrayList.get(row);
                    try {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure to delete this booking?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            new Admin_Executive_Function.Admin_Executive().unit_Delete(bookingSelected.getBookingID());
                            JOptionPane.showMessageDialog(null, "Booking deleted", "Booking delete success", JOptionPane.INFORMATION_MESSAGE);
                            new Entity.Executive.Admin_Executive.Admin_Executive_Facility_Booking(executiveID).run(executiveID);
                            dispose();
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the booking record", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Facility.Booking bookingSelected = bookingArrayList.get(row);
                    try {
                        new Entity.Executive.Admin_Executive.Admin_Executive_Facility_Booking.viewFrame(bookingSelected).setVisible(true);
                    } catch (ParseException | IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the booking record", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        header.searchText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                String query = header.searchText.getText().toLowerCase();
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) tableData.getModel());
                tableData.setRowSorter(sorter);
                sorter.setRowFilter(RowFilter.regexFilter(query));
            }
        });
    }

    public void run(String executiveID) throws IOException, ClassNotFoundException {
        final String[] column = {"Booking ID", "Facility ID", "Resident Username", "Date", "Start time", "End time"};
        Entity.Executive.Admin_Executive.Admin_Executive_Facility_Booking frame = new Entity.Executive.Admin_Executive.Admin_Executive_Facility_Booking(executiveID);
        frame.formHome.removeAll();
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("house", "Unit Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Resident Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("complaint", "Complaint Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("employee", "Employee Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("facility", "Facility Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("booking", "Facility Booking", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("vendor", "Vendor", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("entry", "Visitor Pass", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#243B55");
        frame.menu.colorLeft = Color.decode("#141E30");
        frame.menu.initMoving(frame);
        frame.tableData.statusAllowed = false;
        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }
        frame.tableData.preferredColumnWidth = 140;

        ArrayList<Facility.Booking> bookingArrayList = new Facility.Booking().getArrayList();

        for (Facility.Booking booking : bookingArrayList) {
            frame.tableData.addRow(booking.getStringArray(booking));
        }

        frame.formHome.removeAll();
        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException, ClassNotFoundException {
                if (index == 0) {
                    Admin_Executive_Interface adminExecutiveInterface = new Admin_Executive_Interface(executiveID);
                    adminExecutiveInterface.frame.setVisible(true);
                    frame.dispose();
                } else if (index == 1) {
                    new Admin_Executive_Unit_Management(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 2) {
                    new Entity.Executive.Admin_Executive.Admin_Executive_Resident_Management(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 3) {
                    new Entity.Executive.Admin_Executive.Admin_Executive_Complaint_Management(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 4) {
                    new Entity.Executive.Admin_Executive.Admin_Executive_Employee_Management(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 5) {
                    new Entity.Executive.Admin_Executive.Admin_Executive_Facility_Management(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 6) {
                } else if (index == 7) {
                    new Entity.Executive.Admin_Executive.Admin_Executive_Vendor(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 8) {
                    new Entity.Executive.Admin_Executive.Admin_Executive_Visitor_Pass(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 9){
                    new Login_Frame();
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }

    private class bookFrame extends JFrame {
        private final ArrayList<Facility> facilityArrayList = new Facility().getArrayList();

        public bookFrame() throws IOException, ClassNotFoundException, ParseException {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            MaskFormatter dateMask = new MaskFormatter("##.##.####");
            MaskFormatter timeMask = new MaskFormatter("######");

            JLabel formTitle = new JLabel("FACILITY BOOKING FORM");
            JLabel[] jLabelLeft = {new JLabel("Booking ID"), new JLabel("Facility"),
                    new JLabel("Resident Username"), new JLabel("Date (MM.dd.yyyy)"),
                    new JLabel("Start time (HHmmss)"), new JLabel("End time (HHmmss)")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            JTextField bookingIDField = new JTextField(new Facility.Booking().get_Auto_BookingID());
            bookingIDField.setEditable(false);
            JComboBox<String> facilityComboBox = new JComboBox<>();
            for (Facility facility : facilityArrayList){
                facilityComboBox.addItem(facility.getName());
            }
            JTextField residentUsernameField = new JTextField();
            JFormattedTextField dateField = new JFormattedTextField(dateMask);
            JFormattedTextField startTimeField = new JFormattedTextField(timeMask);
            JFormattedTextField endTimeField = new JFormattedTextField(timeMask);

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Admin_Executive_Function.Button bookButton = new Admin_Executive_Function.Button("Make new booking");
            Admin_Executive_Function.Button cancelButton = new Admin_Executive_Function.Button("Cancel");

            bookButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(bookingIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(facilityComboBox);
            panel2.add(jLabelLeft[2]);
            panel2.add(residentUsernameField);
            panel2.add(jLabelLeft[3]);
            panel2.add(dateField);
            panel2.add(jLabelLeft[4]);
            panel2.add(startTimeField);
            panel2.add(jLabelLeft[5]);
            panel2.add(endTimeField);
            panel1.add(panel2, BorderLayout.CENTER);

            panel3.add(bookButton);
            panel3.add(cancelButton);
            panel1.add(panel3, BorderLayout.SOUTH);
            setUndecorated(true);
            panel1.setPreferredSize(new Dimension(1186 / 2, 621));
            panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            setPreferredSize(new Dimension(1186, 621));
            pack();

            setLocationRelativeTo(null);
            setContentPane(panel1);
            setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
            setVisible(true);

            bookButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Facility.Booking newBooking = new Facility.Booking(bookingIDField.getText(), facilityArrayList.get(facilityComboBox.getSelectedIndex()).getFacilityID(), residentUsernameField.getText(), LocalDate.parse(dateField.getText(), dateFormatter), LocalTime.parse(startTimeField.getText(), timeFormatter), LocalTime.parse(endTimeField.getText(), timeFormatter));
                    try {
                        boolean check = new Facility().check_Facility_Availability(newBooking.getFacilityID());
                        if (check) {
                            JOptionPane.showMessageDialog(null, "Facility not existed", "Facility not found", JOptionPane.ERROR_MESSAGE);
                        } else {
                            check = new Resident().check_Resident_Availability(newBooking.getResident_Username());
                            if (check) {
                                JOptionPane.showMessageDialog(null, "Resident username not existed", "Resident Username error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                check = new Facility.Booking().check_TimeSlot_Availability(newBooking);
                                if (!check) {
                                    JOptionPane.showMessageDialog(null, "This timeslot is not available. Please choose other timeslot", "Timeslot not available", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    new Admin_Executive_Function.Admin_Executive().add_Booking(newBooking);
                                    JOptionPane.showMessageDialog(null, "Booking successful", "Booking successful", JOptionPane.INFORMATION_MESSAGE);
                                    new Entity.Executive.Admin_Executive.Admin_Executive_Facility_Booking(executiveID).run(executiveID);
                                    dispose();
                                }
                            }
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Admin_Executive_Facility_Booking(executiveID).run(executiveID);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private class modifyFrame extends JFrame {
        private final ArrayList<Facility> facilityArrayList = new Facility().getArrayList();

        public modifyFrame(Facility.Booking booking) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
            MaskFormatter dateMask = new MaskFormatter("##.##.####");
            MaskFormatter timeMask = new MaskFormatter("######");

            JLabel formTitle = new JLabel("FACILITY BOOKING FORM");
            JLabel[] jLabelLeft = {new JLabel("Booking ID"), new JLabel("Facility"),
                    new JLabel("Resident Username"), new JLabel("Date (MM.dd.yyyy)"),
                    new JLabel("Start time (HHmmss)"), new JLabel("End time (HHmmss)")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            JTextField bookingIDField = new JTextField(booking.getBookingID());
            bookingIDField.setEditable(false);
            JComboBox<String> facilityComboBox = new JComboBox<>();
            for (Facility facility : facilityArrayList){
                facilityComboBox.addItem(facility.getName());
            }
            int selectedIndex = -1;
            for (Facility facility : facilityArrayList){
                if (facility.getFacilityID().equals(booking.getFacilityID()))
                    selectedIndex = facilityArrayList.indexOf(facility);
            }
            facilityComboBox.setSelectedIndex(selectedIndex);
            JTextField residentUsernameField = new JTextField();
            residentUsernameField.setText(booking.getResident_Username());
            JFormattedTextField dateField = new JFormattedTextField(dateMask);
            dateField.setText(booking.getDate().format(dateFormatter));
            JFormattedTextField startTimeField = new JFormattedTextField(timeMask);
            startTimeField.setText(booking.getStart_Time().format(timeFormatter));
            JFormattedTextField endTimeField = new JFormattedTextField(timeMask);
            endTimeField.setText(booking.getEnd_Time().format(timeFormatter));

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Admin_Executive_Function.Button modifyButton = new Admin_Executive_Function.Button("Modify booking details");
            Admin_Executive_Function.Button cancelButton = new Admin_Executive_Function.Button("Cancel");

            modifyButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(bookingIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(facilityComboBox);
            panel2.add(jLabelLeft[2]);
            panel2.add(residentUsernameField);
            panel2.add(jLabelLeft[3]);
            panel2.add(dateField);
            panel2.add(jLabelLeft[4]);
            panel2.add(startTimeField);
            panel2.add(jLabelLeft[5]);
            panel2.add(endTimeField);
            panel1.add(panel2, BorderLayout.CENTER);

            panel3.add(modifyButton);
            panel3.add(cancelButton);
            panel1.add(panel3, BorderLayout.SOUTH);
            setUndecorated(true);
            panel1.setPreferredSize(new Dimension(1186 / 2, 621));
            panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            setPreferredSize(new Dimension(1186, 621));
            pack();

            setLocationRelativeTo(null);
            setContentPane(panel1);
            setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
            setVisible(true);

            modifyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Facility.Booking newBooking = new Facility.Booking(bookingIDField.getText(), facilityArrayList.get(facilityComboBox.getSelectedIndex()).getFacilityID(), residentUsernameField.getText(), LocalDate.parse(dateField.getText(), dateFormatter), LocalTime.parse(startTimeField.getText(), timeFormatter), LocalTime.parse(endTimeField.getText(), timeFormatter));
                    try {
                        boolean check = new Facility().check_Facility_Availability(newBooking.getFacilityID());
                        if (check) {
                            JOptionPane.showMessageDialog(null, "Facility not existed", "Facility not found", JOptionPane.ERROR_MESSAGE);
                        } else {
                            check = new Resident().check_Resident_Availability(newBooking.getResident_Username());
                            if (!check) {
                                JOptionPane.showMessageDialog(null, "Resident username not existed", "Resident Username error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                check = new Facility.Booking().check_TimeSlot_Availability(newBooking) && !newBooking.getDate().format(dateFormatter).equals(booking.getDate().format(dateFormatter)) && !newBooking.getStart_Time().format(timeFormatter).equals(booking.getStart_Time().format(timeFormatter)) && !newBooking.getEnd_Time().format(timeFormatter).equals(booking.getEnd_Time().format(timeFormatter));
                                if (!check) {
                                    JOptionPane.showMessageDialog(null, "This timeslot is not available. Please choose other timeslot", "Timeslot not available", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    new Admin_Executive_Function.Admin_Executive().facility_Booking_Update(newBooking, booking.getBookingID());
                                    JOptionPane.showMessageDialog(null, "Booking successful", "Booking successful", JOptionPane.INFORMATION_MESSAGE);
                                    new Entity.Executive.Admin_Executive.Admin_Executive_Facility_Booking(executiveID).run(executiveID);
                                    dispose();
                                }
                            }
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Admin_Executive_Facility_Booking(executiveID).run(executiveID);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private static class viewFrame extends JFrame {
        private final ArrayList<Facility> facilityArrayList = new Facility().getArrayList();
        public viewFrame(Facility.Booking booking) throws ParseException, IOException, ClassNotFoundException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");

            JLabel formTitle = new JLabel("FACILITY BOOKING FORM");
            JLabel[] jLabelLeft = {new JLabel("Booking ID"), new JLabel("Facility"),
                    new JLabel("Resident Username"), new JLabel("Date (MM.dd.yyyy)"),
                    new JLabel("Start time (HHmmss)"), new JLabel("End time (HHmmss)")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }
            String facilityName = "";
            for (Facility facility : facilityArrayList){
                if (facility.getFacilityID().equals(booking.getFacilityID()))
                    facilityName = facility.getName();
            }
            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(booking.getBookingID()), new JLabel(facilityName),
                    new JLabel(booking.getResident_Username()), new JLabel(booking.getDate().format(dateFormatter)),
                    new JLabel(booking.getStart_Time().format(timeFormatter)), new JLabel(booking.getEnd_Time().format(timeFormatter))};
            Admin_Executive_Function.Button button = new Admin_Executive_Function.Button("Close");
            button.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            JLabel issuedBy = new JLabel("Issued by Parkhill Residence");
            issuedBy.setFont(new Font("sansserif", Font.PLAIN, 10));
            issuedBy.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);
            for (int i = 0; i < jLabelLeft.length; i++) {
                jLabelLeft[i].setFont(new Font("sansserif", Font.BOLD, 16));
                panel2.add(jLabelLeft[i]);
                panel2.add(jLabelRight[i]);
            }
            panel1.add(panel2, BorderLayout.CENTER);

            panel3.add(issuedBy);
            panel3.add(button);
            panel1.add(panel3, BorderLayout.SOUTH);
            setUndecorated(true);
            panel1.setPreferredSize(new Dimension(1186 / 2, 621));
            panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            setPreferredSize(new Dimension(1186, 621));
            pack();

            setLocationRelativeTo(null);
            setContentPane(panel1);
            setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
            setVisible(true);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException {
        new Entity.Executive.Admin_Executive.Admin_Executive_Facility_Booking("AD01").run("AD01");
    }
}
