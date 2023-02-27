package Entity.Executive.Admin_Executive;

import Entity.Login.Login_Frame;
import Entity.Resident.Resident;
import Entity.Unit;
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
import java.util.ArrayList;

public class Admin_Executive_Resident_Management extends JFrame {

    public PanelBorder panelBorderLeft, panelBorderRight, panelBorderIn;
    public UIPackage.Component.Menu menu = new Menu();
    public Header header = new Header();
    public Form_Home formHome = new Form_Home();
    public Table tableData = new Table();
    public Color backgroundColor = Color.WHITE;
    public String executiveID = "Executive ID";
    public JScrollPane scrollPane;
    public GridBagConstraints constraints;
    public JPanel panel;
    public Admin_Executive_Function.Button addButton, deleteButton, viewButton, modifyButton;

    public Admin_Executive_Resident_Management(String executiveID) throws IOException, ClassNotFoundException {
        this.executiveID = executiveID;
        menu.initMoving(Entity.Executive.Admin_Executive.Admin_Executive_Resident_Management.this);

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

        addButton = new Admin_Executive_Function.Button("Add New Resident");
        modifyButton = new Admin_Executive_Function.Button("Modify Resident Info");
        deleteButton = new Admin_Executive_Function.Button("Delete Resident");
        viewButton = new Admin_Executive_Function.Button("View Resident Info");

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        constraints.gridy++;
        panelBorderIn.add(buttonPanel, constraints);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);

        ArrayList<Resident> residentArrayList = new Resident().getArrayList();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Entity.Executive.Admin_Executive.Admin_Executive_Resident_Management.addFrame().setVisible(true);
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
                        Resident residentSelected = residentArrayList.get(row);
                        new Entity.Executive.Admin_Executive.Admin_Executive_Resident_Management.modifyFrame(residentSelected).setVisible(true);
                        dispose();
                    } catch (IOException | ClassNotFoundException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the resident", "Choice error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Resident residentSelected = residentArrayList.get(row);
                    try {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure to delete this resident?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            new Admin_Executive_Function.Admin_Executive().resident_Delete(residentSelected.getResident_Username());
                            JOptionPane.showMessageDialog(null, "Resident deleted", "Resident delete success", JOptionPane.INFORMATION_MESSAGE);
                            new Entity.Executive.Admin_Executive.Admin_Executive_Resident_Management(executiveID).run(executiveID);
                            dispose();
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the resident", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Resident residentSelected = residentArrayList.get(row);
                    try {
                        new Entity.Executive.Admin_Executive.Admin_Executive_Resident_Management.viewFrame(residentSelected).setVisible(true);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the resident", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
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
        final String[] column = {"Resident Username", "Name", "Gender", "Contact Number", "Unit ID"};
        Entity.Executive.Admin_Executive.Admin_Executive_Resident_Management frame = new Entity.Executive.Admin_Executive.Admin_Executive_Resident_Management(executiveID);
        frame.formHome.removeAll();
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("house", "Unit Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Resident Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("complaint", "Complaint Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("employee", "Employee Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("facility", "Facility Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("booking", "Facility Booking", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("vendor", "Vendor", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#243B55");
        frame.menu.colorLeft = Color.decode("#141E30");
        frame.menu.initMoving(frame);
        frame.tableData.statusAllowed = false;
        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }
        frame.tableData.preferredColumnWidth = 170;

        ArrayList<Resident> residentArrayList = new Resident().getArrayList();

        for (Resident resident : residentArrayList) {
            frame.tableData.addRow(resident.getStringArray(resident));
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
                    new Entity.Executive.Admin_Executive.Admin_Executive_Facility_Booking(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 7) {
                    new Entity.Executive.Admin_Executive.Admin_Executive_Vendor(executiveID).run(executiveID);
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
        private final ArrayList<Resident> residentArrayList = new Resident().getArrayList();

        public addFrame() throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("RESIDENT ADDING");
            JLabel[] jLabelLeft = {new JLabel("Resident Username"), new JLabel("Name"),
                    new JLabel("Gender"), new JLabel("Contact Number"),
                    new JLabel("Unit ID")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            JTextField residentUsernameField = new JTextField();
            JTextField nameField = new JTextField();
            JRadioButton maleButton = new JRadioButton("Male");
            JRadioButton femaleButton = new JRadioButton("Female");

            panel1.add(maleButton);
            panel1.add(femaleButton);
            ButtonGroup group = new ButtonGroup();
            group.add(maleButton);
            group.add(femaleButton);

            MaskFormatter contactNumberMask = new MaskFormatter("01#-#######");
            JFormattedTextField contactNumberField = new JFormattedTextField(contactNumberMask);
            JTextField unitIDField = new JTextField();

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Admin_Executive_Function.Button addButton = new Admin_Executive_Function.Button("Add New Resident");
            Admin_Executive_Function.Button cancelButton = new Admin_Executive_Function.Button("Cancel");

            addButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(residentUsernameField);
            panel2.add(jLabelLeft[1]);
            panel2.add(nameField);
            panel2.add(jLabelLeft[2]);

            JPanel panelButton = new JPanel();
            panelButton.setLayout(new GridLayout(2, 1));
            panelButton.setBackground(Color.white);
            panelButton.add(maleButton);
            panelButton.add(femaleButton);
            panel2.add(panelButton);

            panel2.add(jLabelLeft[3]);
            panel2.add(contactNumberField);
            panel2.add(jLabelLeft[4]);
            panel2.add(unitIDField);
            panel1.add(panel2, BorderLayout.CENTER);

            panel3.add(addButton);
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

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    char gender = 'M';
                    if (femaleButton.isSelected())
                        gender = 'F';
                    Resident resident = new Resident(residentUsernameField.getText(), nameField.getText(), gender, contactNumberField.getText(), unitIDField.getText(), 0);
                    try {
                        boolean check = resident.check_Resident_Availability(resident.getResident_Username());
                        if (check) {
                            JOptionPane.showMessageDialog(null, "Resident Username already existed", "Resident Username found", JOptionPane.ERROR_MESSAGE);
                        } else {
                            check = resident.check_Resident_Contact_Number_Availability(resident.getContact_Number());
                            if (check) {
                                JOptionPane.showMessageDialog(null, "Contact Number already registered", "Contact Number registered", JOptionPane.ERROR_MESSAGE);
                            } else {
                                new Admin_Executive_Function.Admin_Executive().resident_Add(resident);
                                JOptionPane.showMessageDialog(null, "Resident registration successful.Please ask resident to sign up and remember to register new unit if needed.", "Resident adding successful", JOptionPane.INFORMATION_MESSAGE);
                                new Entity.Executive.Admin_Executive.Admin_Executive_Resident_Management(executiveID).run(executiveID);
                                dispose();
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
                        new Admin_Executive_Resident_Management(executiveID).run(executiveID);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private class modifyFrame extends JFrame {
        private final ArrayList<Resident> residentArrayList = new Resident().getArrayList();

        public modifyFrame(Resident resident) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("RESIDENT INFO MODIFYING");
            JLabel[] jLabelLeft = {new JLabel("Resident Username"), new JLabel("Name"),
                    new JLabel("Gender"), new JLabel("Contact Number"),
                    new JLabel("Unit ID")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            JTextField residentUsernameField = new JTextField();
            residentUsernameField.setText(resident.getResident_Username());
            JTextField nameField = new JTextField();
            nameField.setText(resident.getName());
            JRadioButton maleButton = new JRadioButton("Male");
            JRadioButton femaleButton = new JRadioButton("Female");

            panel1.add(maleButton);
            panel1.add(femaleButton);
            ButtonGroup group = new ButtonGroup();
            group.add(maleButton);
            group.add(femaleButton);
            if (resident.getGender() == 'F') femaleButton.setSelected(true);
            else maleButton.setSelected(true);

            MaskFormatter contactNumberMask = new MaskFormatter("01#-#######");
            JFormattedTextField contactNumberField = new JFormattedTextField(contactNumberMask);
            contactNumberField.setText(resident.getContact_Number());
            JTextField unitIDField = new JTextField();
            unitIDField.setText(resident.getUnitID());

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Admin_Executive_Function.Button modifyButton = new Admin_Executive_Function.Button("Modify Resident");
            Admin_Executive_Function.Button cancelButton = new Admin_Executive_Function.Button("Cancel");

            modifyButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(residentUsernameField);
            panel2.add(jLabelLeft[1]);
            panel2.add(nameField);
            panel2.add(jLabelLeft[2]);

            JPanel panelButton = new JPanel();
            panelButton.setLayout(new GridLayout(2, 1));
            panelButton.setBackground(Color.white);
            panelButton.add(maleButton);
            panelButton.add(femaleButton);
            panel2.add(panelButton);

            panel2.add(jLabelLeft[3]);
            panel2.add(contactNumberField);
            panel2.add(jLabelLeft[4]);
            panel2.add(unitIDField);
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
                    char gender = 'M';
                    if (femaleButton.isSelected())
                        gender = 'F';
                    Resident residentModify = new Resident(residentUsernameField.getText(), nameField.getText(), gender, contactNumberField.getText(), unitIDField.getText(), resident.getPayment());
                    try {
                        boolean check = residentModify.check_Resident_Availability(residentModify.getResident_Username()) && !residentModify.getResident_Username().equals(resident.getResident_Username());
                        if (check) {
                            JOptionPane.showMessageDialog(null, "Resident Username already existed", "Resident Username found", JOptionPane.ERROR_MESSAGE);
                        } else {
                            check = residentModify.check_Resident_Contact_Number_Availability(residentModify.getContact_Number())  && !residentModify.getContact_Number().equals(resident.getContact_Number());
                            if (check) {
                                JOptionPane.showMessageDialog(null, "Contact Number already registered", "Contact Number registered", JOptionPane.ERROR_MESSAGE);
                            } else {
                                new Admin_Executive_Function.Admin_Executive().resident_Modify(residentModify, resident.getResident_Username());
                                JOptionPane.showMessageDialog(null, "Resident modification successful.Please ask resident to sign up and remember to register new unit if needed.", "Resident adding successful", JOptionPane.INFORMATION_MESSAGE);
                                new Entity.Executive.Admin_Executive.Admin_Executive_Resident_Management(executiveID).run(executiveID);
                                dispose();
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
                        new Admin_Executive_Resident_Management(executiveID).run(executiveID);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private static class viewFrame extends JFrame {
        public viewFrame(Resident resident) throws ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            MaskFormatter yearMask = new MaskFormatter("####");

            JLabel formTitle = new JLabel("RESIDENT DETAILS");
            JLabel[] jLabelLeft = {new JLabel("Resident Username"), new JLabel("Name"),
                    new JLabel("Gender"), new JLabel("Contact Number"),
                    new JLabel("Unit ID")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(resident.getResident_Username()), new JLabel(resident.getName()),
                    new JLabel(Character.toString(resident.getGender())), new JLabel(resident.getContact_Number()),
                    new JLabel(resident.getUnitID())};

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
        new Entity.Executive.Admin_Executive.Admin_Executive_Resident_Management("AD01").run("AD01");
    }
}
