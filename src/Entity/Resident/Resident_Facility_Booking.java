package Entity.Resident;

import Entity.Facility;
import Entity.Login.Login_Frame;
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

public class Resident_Facility_Booking extends JFrame {
    public PanelBorder panelBorderLeft, panelBorderRight, panelBorderIn;
    public UIPackage.Component.Menu menu = new Menu();
    public Header header = new Header();
    public Form_Home formHome = new Form_Home();
    public Table tableData = new Table();
    public Color backgroundColor = Color.WHITE;
    public String resident_Username;
    public JScrollPane scrollPane;
    public GridBagConstraints constraints;
    public JPanel panel;
    public Resident.Button addButton, cancelButton, viewButton, updateButton;

    public Resident_Facility_Booking(String resident_Username) throws IOException, ClassNotFoundException {
        this.resident_Username = resident_Username;
        menu.initMoving(Entity.Resident.Resident_Facility_Booking.this);

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

        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), resident_Username, TitledBorder.LEFT, TitledBorder.TOP);
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

        addButton = new Resident.Button("Book facility");
        updateButton = new Resident.Button("Update booking");
        cancelButton = new Resident.Button("Cancel Booking");
        viewButton = new Resident.Button("View Booking details");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(viewButton);
        constraints.gridy++;
        panelBorderIn.add(buttonPanel, constraints);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);

        Resident resident = new Resident();
        ArrayList<Facility.Booking> bookingArrayList = resident.view_Resident_Booking(resident_Username);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new addFrame(resident_Username).setVisible(true);
                    dispose();
                } catch (IOException | ClassNotFoundException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                if (row != -1){
                    try {
                        Facility.Booking bookingSelected = bookingArrayList.get(row);
                        new updateFrame(bookingSelected).setVisible(true);
                        dispose();
                    } catch (IOException | ClassNotFoundException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the booking details", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Facility.Booking booking = bookingArrayList.get(row);
                    int result = JOptionPane.showConfirmDialog(null,"Do you sure to cancel your facility booking?", "Get Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_OPTION){
                        try {
                            new Resident().cancel_Facility_Booking(booking.getBookingID());
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    try {
                        new Resident_Facility_Booking(resident_Username).run();
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the booking details", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Facility.Booking booking = bookingArrayList.get(row);
                    new viewFrame(booking).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the booking details", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
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

    public void run() throws IOException, ClassNotFoundException {
        final String[] column = {"Booking ID", "Facility ID", "Resident Username", "Date", "Start time", "End time"};
        Entity.Resident.Resident_Facility_Booking frame = new Entity.Resident.Resident_Facility_Booking("Mike1001");
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("payment", "Invoice", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("deposit", "Deposit", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("paymentHistory", "Payment History", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("statement", "Statement", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("booking", "Facility Booking", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Visitor Pass", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("complaint", "complaint", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#38ef7d");
        frame.menu.colorLeft = Color.decode("#11998e");
        frame.menu.initMoving(frame);
        frame.tableData.statusAllowed = false;
        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }
        frame.tableData.preferredColumnWidth = 150;

        Resident resident = new Resident();
        resident = resident.get_Resident_Info(resident_Username);
        ArrayList<Facility.Booking> bookingArrayList = resident.view_Resident_Booking(resident_Username);

        for (Facility.Booking booking : bookingArrayList) {
            frame.tableData.addRow(booking.getStringArray(booking));
        }

        frame.formHome.removeAll();
        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException, ClassNotFoundException {
                if (index == 0){
                    Resident_Interface residentInterface = new Resident_Interface(resident_Username);
                    residentInterface.frame.setVisible(true);
                    frame.dispose();
                } else if (index == 1){
                    new Resident_Payment_Frame(resident_Username).run();
                    frame.dispose();
                } else if (index == 2){
                    new Entity.Resident.Resident_Deposit_Frame(resident_Username).run();
                    frame.dispose();
                } else if (index == 3){
                    new Resident_Payment_History(resident_Username).run();
                    frame.dispose();
                } else if (index == 4){
                    new Entity.Resident.Resident_Statement_Frame(resident_Username).run();
                    frame.dispose();
                } else if (index == 5){
                } else if (index == 6){
                    new Resident_Visitor_Pass(resident_Username).run(resident_Username);
                    frame.dispose();
                } else if (index == 7){
                    new Entity.Resident.Resident_Complaint(resident_Username).run(resident_Username);
                    frame.dispose();
                } else if (index == 8){
                    new Login_Frame();
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }

    private class addFrame extends JFrame {
        private final ArrayList<Facility> facility_Info = new Facility().getArrayList();
        public addFrame(String resident_Username) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
            MaskFormatter dateMask = new MaskFormatter("##.##.####");
            MaskFormatter timeMask = new MaskFormatter("######");

            JLabel formTitle = new JLabel("FACILITY BOOKING FORM");
            JLabel[] jLabelLeft = {new JLabel("Booking ID"), new JLabel("Facility"),
                    new JLabel("Resident Username"), new JLabel("Date (MM.dd.yyyy)"),
                    new JLabel("Start time (HHmmss)"), new JLabel("End time (HHmmss)")};
            for (JLabel label : jLabelLeft){
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }
            JTextField bookingIDField = new JTextField(new Facility.Booking().get_Auto_BookingID());
            bookingIDField.setEditable(false);
            JComboBox<String> facilityComboBox = new JComboBox<>();
            for (Facility facility : facility_Info){
                facilityComboBox.addItem(facility.getName());
            }
            JTextField residentUsernameField = new JTextField(resident_Username);
            residentUsernameField.setEditable(false);
            JFormattedTextField dateField = new JFormattedTextField(dateMask);
            JFormattedTextField startTimeField = new JFormattedTextField(timeMask);
            JFormattedTextField endTimeField = new JFormattedTextField(timeMask);

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Resident.Button bookButton = new Resident.Button("Book Facility");
            Resident.Button cancelButton = new Resident.Button("Cancel Booking");

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
                    if (!dateField.getText().equals("  .  .    ") && !startTimeField.getText().equals("") && !endTimeField.getText().equals("")){
                        Facility.Booking booking = new Facility.Booking(bookingIDField.getText(), facility_Info.get(facilityComboBox.getSelectedIndex()).getFacilityID(), residentUsernameField.getText(), LocalDate.parse(dateField.getText(), formatter), LocalTime.parse(startTimeField.getText(), timeFormatter), LocalTime.parse(endTimeField.getText(), timeFormatter));
                        try {
                            boolean check = new Resident().check_Resident_Availability(booking.getResident_Username());
                            if (!check) {
                                JOptionPane.showMessageDialog(null, "Resident username not found", "Resident Username not found", JOptionPane.ERROR_MESSAGE);
                            } else {
                                check = booking.check_TimeSlot_Availability(booking);
                                if (!check){
                                    JOptionPane.showMessageDialog(null, "Time slot not available", "Time slot not available", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    new Resident().add_Facility_Booking(booking);
                                    new Entity.Resident.Resident_Facility_Booking(resident_Username).run();
                                    dispose();
                                }
                            }

                        } catch (ClassNotFoundException | IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please complete the information", "Information lost", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Resident_Facility_Booking(resident_Username).run();
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private class updateFrame extends JFrame {
        private final ArrayList<Facility> facility_Info = new Facility().getArrayList();
        public updateFrame(Facility.Booking booking) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
            MaskFormatter dateMask = new MaskFormatter("##.##.####");
            MaskFormatter timeMask = new MaskFormatter("######");

            JLabel formTitle = new JLabel("FACILITY BOOKING FORM");
            JLabel[] jLabelLeft = {new JLabel("Booking ID"), new JLabel("Facility"),
                    new JLabel("Resident Username"), new JLabel("Date (MM.dd.yyyy)"),
                    new JLabel("Start time (HHmmss)"), new JLabel("End time (HHmmss)")};
            for (JLabel label : jLabelLeft){
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }
            JTextField bookingIDField = new JTextField(booking.getBookingID());
            bookingIDField.setEditable(false);
            JComboBox<String> facilityComboBox = new JComboBox<>();
            for (Facility facility : facility_Info){
                facilityComboBox.addItem(facility.getName());
            }
            for (Facility facility : facility_Info){
                if (facility.getFacilityID().equals(booking.getFacilityID()))
                    facilityComboBox.setSelectedIndex(facility_Info.indexOf(facility));
            }
            JTextField residentUsernameField = new JTextField(booking.getResident_Username());
            residentUsernameField.setEditable(false);
            JFormattedTextField dateField = new JFormattedTextField(dateMask);
            dateField.setText(booking.getDate().format(formatter));
            JFormattedTextField startTimeField = new JFormattedTextField(timeMask);
            startTimeField.setText(booking.getStart_Time().format(timeFormatter));
            JFormattedTextField endTimeField = new JFormattedTextField(timeMask);
            endTimeField.setText(booking.getEnd_Time().format(timeFormatter));

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Resident.Button updateButton = new Resident.Button("Update Booking details");
            Resident.Button cancelButton = new Resident.Button("Cancel Update");

            updateButton.setAlignmentX(JButton.CENTER);
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

            panel3.add(updateButton);
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

            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Facility.Booking bookingNew = new Facility.Booking(bookingIDField.getText(), facility_Info.get(facilityComboBox.getSelectedIndex()).getFacilityID(), residentUsernameField.getText(), LocalDate.parse(dateField.getText(), formatter), LocalTime.parse(startTimeField.getText(), timeFormatter), LocalTime.parse(endTimeField.getText(), timeFormatter));
                    try {
                        boolean check = new Resident().check_Resident_Availability(bookingNew.getResident_Username());
                        if (!check) {
                            JOptionPane.showMessageDialog(null, "Resident username not found", "Resident Username not found", JOptionPane.ERROR_MESSAGE);
                        } else {
                            check = bookingNew.check_TimeSlot_Availability(bookingNew) && !bookingNew.getDate().format(formatter).equals(booking.getDate().format(formatter)) && !bookingNew.getStart_Time().format(timeFormatter).equals(booking.getStart_Time().format(timeFormatter)) && ! bookingNew.getEnd_Time().format(timeFormatter).equals(booking.getEnd_Time().format(timeFormatter));
                            if (!check){
                                JOptionPane.showMessageDialog(null, "Time slot not available", "Time slot not available", JOptionPane.ERROR_MESSAGE);
                            } else {
                                new Resident().update_Facility_Booking(bookingNew, booking.getBookingID());
                                new Entity.Resident.Resident_Facility_Booking(resident_Username).run();
                                dispose();
                            }
                        }
                    } catch (ClassNotFoundException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Resident_Facility_Booking(resident_Username).run();
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private class viewFrame extends JFrame {
        public viewFrame(Facility.Booking booking) {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
            JLabel formTitle = new JLabel("BOOKING DETAILS");
            JLabel issuedBy = new JLabel("Issued by Parkhill Residence");
            JLabel[] jLabelLeft = {new JLabel("Booking ID"), new JLabel("Facility ID"),
                    new JLabel("Resident Username"), new JLabel("Date (MM.dd.yyyy)"),
                    new JLabel("Start time (HHmmss)"), new JLabel("End time (HHmmss)")};
            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(booking.getBookingID()), new JLabel(booking.getFacilityID()),
                    new JLabel(booking.getResident_Username()), new JLabel(booking.getDate().format(formatter)),
                    new JLabel(booking.getStart_Time().format(timeFormatter)), new JLabel(booking.getEnd_Time().format(timeFormatter))};
            Resident.Button button = new Resident.Button("Close");
            button.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
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
        new Entity.Resident.Resident_Facility_Booking("Mike1001").run();
    }
}
