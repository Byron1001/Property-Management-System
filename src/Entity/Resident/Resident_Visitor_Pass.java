package Entity.Resident;

import Entity.Login.Login_Frame;
import Entity.Visitor_Pass;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Resident_Visitor_Pass extends JFrame {
    public PanelBorder panelBorderLeft, panelBorderRight, panelBorderIn;
    public UIPackage.Component.Menu menu = new Menu();
    public Header header = new Header();
    public Form_Home formHome = new Form_Home();
    public Table tableData = new Table();
    public Color backgroundColor = Color.WHITE;
    public String resident_Username = "resident Username";
    public JScrollPane scrollPane;
    public GridBagConstraints constraints;
    public JPanel panel;
    public Resident.Button addButton, cancelButton, viewButton, updateButton;

    public Resident_Visitor_Pass(String resident_Username) throws IOException, ClassNotFoundException {
        this.resident_Username = resident_Username;
        menu.initMoving(Entity.Resident.Resident_Visitor_Pass.this);

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

        addButton = new Resident.Button("Apply Visitor Pass");
        cancelButton = new Resident.Button("Cancel Visitor Pass");
        viewButton = new Resident.Button("View Visitor Pass");
        updateButton = new Resident.Button("Update Visitor Pass");

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
        ArrayList<Visitor_Pass> visitorPassArrayList = resident.view_All_Visitor_Pass_Apply(resident_Username);
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
                try {
                    int row = tableData.getSelectedRow();
                    if (row != -1){
                        Visitor_Pass visitorPassSelected = visitorPassArrayList.get(row);
                        new updateFrame(visitorPassSelected).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Please choose the visitor pass", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                    }
                } catch (IOException | ClassNotFoundException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Visitor_Pass visitorPassSelected = visitorPassArrayList.get(row);
                    int result = JOptionPane.showConfirmDialog(null, "Do you sure to cancel this visitor pass?", "Get Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_OPTION) {
                        try {
                            new Resident().cancel_Visitor_Pass(visitorPassSelected.getVisitor_Pass_ID());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    try {
                        new Resident_Visitor_Pass(resident_Username).run(resident_Username);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the visitor pass", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Visitor_Pass visitorPass = visitorPassArrayList.get(row);
                    new viewFrame(visitorPass).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the visitor pass", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
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

    public void run(String resident_Username) throws IOException, ClassNotFoundException {
        final String[] column = {"Visitor Pass ID", "Visitor Name", "Resident Username", "Unit ID", "Gender", "Contact Number", "Date Start", "Date End", "Status"};
        Resident_Visitor_Pass frame = new Resident_Visitor_Pass(resident_Username);
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
        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }
        frame.tableData.preferredColumnWidth = 102;

        Resident resident = new Resident();
        ArrayList<Visitor_Pass> visitorPassArrayList = resident.view_All_Visitor_Pass_Apply(resident_Username);

        for (Visitor_Pass visitorPass : visitorPassArrayList) {
            frame.tableData.addRow(visitorPass.getStringArray(visitorPass));
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
                    new Entity.Resident.Resident_Facility_Booking(resident_Username).run();
                    frame.dispose();
                } else if (index == 6){
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
        public addFrame(String resident_Username) throws IOException, ClassNotFoundException, ParseException {
            Resident resident = new Resident();
            resident = resident.get_Resident_Info(resident_Username);
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            MaskFormatter dateMask = new MaskFormatter("##.##.####");
            MaskFormatter contactNumberMask = new MaskFormatter("###-#######");

            JLabel formTitle = new JLabel("Visitor Pass Apply Form");
            JLabel[] jLabelLeft = {new JLabel("Visitor Pass ID"), new JLabel("Visitor name"),
                    new JLabel("Resident Username"), new JLabel("Unit ID"),
                    new JLabel("Gender"), new JLabel("Contact Number"),
                    new JLabel("Date start (MM.dd.yyyy)"), new JLabel("Date end (MM.dd.yyyy)")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }
            JTextField visitorPassIDField = new JTextField(new Visitor_Pass().get_Auto_Visitor_Pass_ID());
            visitorPassIDField.setEditable(false);
            JTextField visitorNameField = new JTextField();
            JTextField residentUsernameField = new JTextField(resident_Username);
            residentUsernameField.setEditable(false);
            JTextField unitIDField = new JTextField(resident.getUnitID());
            unitIDField.setEditable(false);
            JComboBox genderComboBox = new JComboBox();
            genderComboBox.addItem("Male");
            genderComboBox.addItem("Female");
            JFormattedTextField contactNumberField = new JFormattedTextField(contactNumberMask);
            JFormattedTextField dateStartField = new JFormattedTextField(dateMask);
            JFormattedTextField dateEndField = new JFormattedTextField(dateMask);

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Resident.Button applyButton = new Resident.Button("Apply Visitor Pass");
            Resident.Button cancelButton = new Resident.Button("Cancel Application");

            applyButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);
            panel2.add(jLabelLeft[0]);
            panel2.add(visitorPassIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(visitorNameField);
            panel2.add(jLabelLeft[2]);
            panel2.add(residentUsernameField);
            panel2.add(jLabelLeft[3]);
            panel2.add(unitIDField);
            panel2.add(jLabelLeft[4]);
            panel2.add(genderComboBox);
            panel2.add(jLabelLeft[5]);
            panel2.add(contactNumberField);
            panel2.add(jLabelLeft[6]);
            panel2.add(dateStartField);
            panel2.add(jLabelLeft[7]);
            panel2.add(dateEndField);
            panel1.add(panel2, BorderLayout.CENTER);

            panel3.add(applyButton);
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

            applyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (visitorNameField.getText().equals("") || contactNumberField.getText().equals("   -       ") || dateEndField.getText().equals("  .  .    ") || dateStartField.getText().equals("  .  .    ")){
                        JOptionPane.showMessageDialog(null, "Please enter all the information", "Information lost", JOptionPane.ERROR_MESSAGE);
                    } else {
                        Visitor_Pass visitorPass = new Visitor_Pass(visitorPassIDField.getText(), visitorNameField.getText(), residentUsernameField.getText(), unitIDField.getText(), genderComboBox.getSelectedItem().toString().charAt(0), contactNumberField.getText(), LocalDate.parse(dateStartField.getText(), formatter), LocalDate.parse(dateEndField.getText(), formatter), "Disapproved");
                        try {
                            boolean check = new Resident().check_Resident_Availability(visitorPass.getResident_Username());
                            if (!check) {
                                JOptionPane.showMessageDialog(null, "Resident username not found", "Resident Username not found", JOptionPane.ERROR_MESSAGE);
                            } else {
                                check = visitorPass.check_Visitor_Pass_Availability(visitorPass);
                                if (check) {
                                    JOptionPane.showMessageDialog(null, "Visitor Pass already exists", "Visitor Pass exists", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    new Resident().apply_Visitor_Pass(visitorPass);
                                    new Resident_Visitor_Pass(resident_Username).run(resident_Username);
                                    dispose();
                                }
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Resident_Visitor_Pass(resident_Username).run(resident_Username);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private class updateFrame extends JFrame {
        public updateFrame(Visitor_Pass visitorPass) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            MaskFormatter dateMask = new MaskFormatter("##.##.####");
            MaskFormatter contactNumberMask = new MaskFormatter("###-#######");

            JLabel formTitle = new JLabel("Visitor Pass Apply Form");
            JLabel[] jLabelLeft = {new JLabel("Visitor Pass ID"), new JLabel("Visitor name"),
                    new JLabel("Resident Username"), new JLabel("Unit ID"),
                    new JLabel("Gender"), new JLabel("Contact Number"),
                    new JLabel("Date start (MM.dd.yyyy)"), new JLabel("Date end (MM.dd.yyyy)")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }
            JTextField visitorPassIDField = new JTextField(visitorPass.getVisitor_Pass_ID());
            visitorPassIDField.setEditable(false);
            JTextField visitorNameField = new JTextField(visitorPass.getVisitor_Name());
            JTextField residentUsernameField = new JTextField(visitorPass.getResident_Username());
            residentUsernameField.setEditable(false);
            JTextField unitIDField = new JTextField(visitorPass.getUnitID());
            unitIDField.setEditable(false);
            JComboBox<String> genderComboBox = new JComboBox<>();
            genderComboBox.addItem("Male");
            genderComboBox.addItem("Female");
            if (visitorPass.getGender() == 'M'){
                genderComboBox.setSelectedIndex(0);
            } else {
                genderComboBox.setSelectedIndex(1);
            }
            JFormattedTextField contactNumberField = new JFormattedTextField(contactNumberMask);
            contactNumberField.setText(visitorPass.getContact_Number());
            JFormattedTextField dateStartField = new JFormattedTextField(dateMask);
            dateStartField.setText(visitorPass.getDate_Start().format(formatter));
            JFormattedTextField dateEndField = new JFormattedTextField(dateMask);
            dateEndField.setText(visitorPass.getDate_End().format(formatter));

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Resident.Button applyButton = new Resident.Button("Update Visitor Pass");
            Resident.Button cancelButton = new Resident.Button("Cancel Update");

            applyButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);
            panel2.add(jLabelLeft[0]);
            panel2.add(visitorPassIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(visitorNameField);
            panel2.add(jLabelLeft[2]);
            panel2.add(residentUsernameField);
            panel2.add(jLabelLeft[3]);
            panel2.add(unitIDField);
            panel2.add(jLabelLeft[4]);
            panel2.add(genderComboBox);
            panel2.add(jLabelLeft[5]);
            panel2.add(contactNumberField);
            panel2.add(jLabelLeft[6]);
            panel2.add(dateStartField);
            panel2.add(jLabelLeft[7]);
            panel2.add(dateEndField);
            panel1.add(panel2, BorderLayout.CENTER);

            panel3.add(applyButton);
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

            applyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Visitor_Pass visitorPass = new Visitor_Pass(visitorPassIDField.getText(), visitorNameField.getText(), residentUsernameField.getText(), unitIDField.getText(), genderComboBox.getSelectedItem().toString().charAt(0), contactNumberField.getText(), LocalDate.parse(dateStartField.getText(), formatter), LocalDate.parse(dateEndField.getText(), formatter), "Disapproved");
                    try {
                        boolean check = new Resident().check_Resident_Availability(visitorPass.getResident_Username());
                        if (!check) {
                            JOptionPane.showMessageDialog(null, "Resident username not found", "Resident Username not found", JOptionPane.ERROR_MESSAGE);
                        } else {
                            new Resident().update_Visitor_Pass(visitorPass);
                            new Resident_Visitor_Pass(resident_Username).run(resident_Username);
                            dispose();
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
                        new Resident_Visitor_Pass(resident_Username).run(resident_Username);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private class viewFrame extends JFrame {
        public viewFrame(Visitor_Pass visitorPass) {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");

            JLabel formTitle = new JLabel("VISITOR PASS");
            JLabel issuedBy = new JLabel("Issued by Parhill Residence");
            JLabel[] jLabelLeft = {new JLabel("Visitor Pass ID"), new JLabel("Visitor name"),
                    new JLabel("Resident Username"), new JLabel("Unit ID"),
                    new JLabel("Gender"), new JLabel("Contact Number"),
                    new JLabel("Date Start (MM.dd.yyyyy)"), new JLabel("Date End(MM.dd.yyyy)"), new JLabel("Status")};
            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(visitorPass.getVisitor_Pass_ID()), new JLabel(visitorPass.getVisitor_Name()),
                    new JLabel(visitorPass.getResident_Username()), new JLabel(visitorPass.getUnitID()),
                    new JLabel(Character.toString(visitorPass.getGender())), new JLabel(visitorPass.getContact_Number()),
                    new JLabel(visitorPass.getDate_Start().format(formatter)), new JLabel(visitorPass.getDate_End().format(formatter)),
                    new JLabel(visitorPass.getStatus())};
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
        new Resident_Visitor_Pass("Mike1001").run("Mike1001");
//        new addFrame("Mike1001");
//        new cancelFrame(new Visitor_Pass().getArrayList().get(0));
    }
}
