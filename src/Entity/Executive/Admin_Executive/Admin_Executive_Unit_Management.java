package Entity.Executive.Admin_Executive;

import Entity.Facility;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Admin_Executive_Unit_Management extends JFrame {
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

    public Admin_Executive_Unit_Management(String executiveID) throws IOException, ClassNotFoundException {
        this.executiveID = executiveID;
        menu.initMoving(Admin_Executive_Unit_Management.this);

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

        addButton = new Admin_Executive_Function.Button("Add New Unit");
        modifyButton = new Admin_Executive_Function.Button("Modify Unit Info");
        deleteButton = new Admin_Executive_Function.Button("Delete Unit");
        viewButton = new Admin_Executive_Function.Button("View Unit details");

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

        ArrayList<Unit> unitArrayList = new Unit().getArrayList();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new addFrame().setVisible(true);
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
                        Unit unitSelected = unitArrayList.get(row);
                        new modifyFrame(unitSelected).setVisible(true);
                        dispose();
                    } catch (IOException | ClassNotFoundException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please make choose the unit", "Choice error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Unit unitSelected = unitArrayList.get(row);
                    try {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure to delete this unit?" ,"Delete confirmation", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION){
                            new Admin_Executive_Function.Admin_Executive().unit_Delete(unitSelected.getUnitID());
                            JOptionPane.showMessageDialog(null, "Unit deleted", "Unit delete success", JOptionPane.INFORMATION_MESSAGE);
                            new Admin_Executive_Unit_Management(executiveID).run(executiveID);
                            dispose();
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the unit", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Unit unitSelected = unitArrayList.get(row);
                    try {
                        new viewFrame(unitSelected).setVisible(true);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the unit", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
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
        final String[] column = {"Floor", "Unit ID", "Complete Year", "Furnish", "Parking Unit", "Owner Username", "Resident Username"};
        Admin_Executive_Unit_Management frame = new Admin_Executive_Unit_Management(executiveID);
        frame.formHome.removeAll();
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("house", "Unit Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Resident Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("complaint", "Complaint Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("employee", "Employee Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("facility", "Facility Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("booking", "Facility Booking", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("vendor", "Vendor", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout Booking", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#243B55");
        frame.menu.colorLeft = Color.decode("#141E30");
        frame.menu.initMoving(frame);
        frame.tableData.statusAllowed = false;
        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }
        frame.tableData.preferredColumnWidth = 125;

        ArrayList<Unit> unitArrayList = new Unit().getArrayList();

        for (Unit unit : unitArrayList) {
            frame.tableData.addRow(unit.getStringArray(unit));
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
        private final ArrayList<Unit> unitArrayList = new Unit().getArrayList();
        public addFrame() throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            MaskFormatter yearMask = new MaskFormatter("####");

            JLabel formTitle = new JLabel("UNIT ADDING");
            JLabel[] jLabelLeft = {new JLabel("Floor"), new JLabel("Unit ID"),
                    new JLabel("Completer Year"), new JLabel("Furnish situation"),
                    new JLabel("Parking Unit"), new JLabel("Owner Username"),
                    new JLabel("Resident Username")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            JTextField floorField = new JTextField();
            JTextField unitIDField = new JTextField();
            JFormattedTextField completeYearField = new JFormattedTextField(yearMask);
            JComboBox<String> furnishComboBox = new JComboBox<>();
            furnishComboBox.addItem("Fully");
            furnishComboBox.addItem("Half");
            furnishComboBox.addItem("Not");
            JTextField parkingUnitField = new JTextField();
            JTextField ownerUsernameField = new JTextField();
            JTextField residentUsernameField = new JTextField();
            floorField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9'){
                    } else {
                        e.consume();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Admin_Executive_Function.Button addButton = new Admin_Executive_Function.Button("Add New Unit");
            Admin_Executive_Function.Button cancelButton = new Admin_Executive_Function.Button("Cancel");

            addButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(floorField);
            panel2.add(jLabelLeft[1]);
            panel2.add(unitIDField);
            panel2.add(jLabelLeft[2]);
            panel2.add(completeYearField);
            panel2.add(jLabelLeft[3]);
            panel2.add(furnishComboBox);
            panel2.add(jLabelLeft[4]);
            panel2.add(parkingUnitField);
            panel2.add(jLabelLeft[5]);
            panel2.add(ownerUsernameField);
            panel2.add(jLabelLeft[5]);
            panel2.add(residentUsernameField);
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
                    Unit newUnit = new Unit(Integer.parseInt(floorField.getText()), unitIDField.getText(), Integer.parseInt(completeYearField.getText()), furnishComboBox.getSelectedItem().toString(), parkingUnitField.getText(), ownerUsernameField.getText(), residentUsernameField.getText());
                    try {
                        boolean check = newUnit.check_Unit_Availability(newUnit.getUnitID());
                        if (check) {
                            JOptionPane.showMessageDialog(null, "Unit ID already existed", "Unit ID found", JOptionPane.ERROR_MESSAGE);
                        } else {
                            check = newUnit.check_Parking_Unit_Availability(newUnit.getParking_Unit());
                            if (check){
                                JOptionPane.showMessageDialog(null, "Parking Unit already occupied", "Parking Unit Occupied", JOptionPane.ERROR_MESSAGE);
                            } else {
                                check = new Resident().check_Resident_Availability(ownerUsernameField.getText()) || new Resident().check_Resident_Availability(residentUsernameField.getText());
                                if (!check) {
                                    JOptionPane.showMessageDialog(null, "Resident or Owner Username not exists, please register first.", "Resident or owner username not exists", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    new Admin_Executive_Function.Admin_Executive().unit_Add(newUnit);
                                    JOptionPane.showMessageDialog(null, "Unit adding successful", "Unit adding successful", JOptionPane.INFORMATION_MESSAGE);
                                    new Admin_Executive_Unit_Management(executiveID).run(executiveID);
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
                        new Admin_Executive_Unit_Management(executiveID).run(executiveID);
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private class modifyFrame extends JFrame {
        private final ArrayList<Unit> unitArrayList = new Unit().getArrayList();
        public modifyFrame(Unit unit) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            MaskFormatter yearMask = new MaskFormatter("####");

            JLabel formTitle = new JLabel("UNIT DETAILS MODIFYING");
            JLabel[] jLabelLeft = {new JLabel("Floor"), new JLabel("Unit ID"),
                    new JLabel("Completer Year"), new JLabel("Furnish situation"),
                    new JLabel("Parking Unit"), new JLabel("Owner Username"),
                    new JLabel("Resident Username")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            JTextField floorField = new JTextField();
            floorField.setText(Integer.toString(unit.getFloor()));
            JTextField unitIDField = new JTextField();
            unitIDField.setText(unit.getUnitID());
            JFormattedTextField completeYearField = new JFormattedTextField(yearMask);
            completeYearField.setText(Integer.toString(unit.getComplete_Year()));
            JComboBox<String> furnishComboBox = new JComboBox<>();
            furnishComboBox.addItem("Fully");
            furnishComboBox.addItem("Half");
            furnishComboBox.addItem("Not");
            for (int i = 0;i < 3;i++){
                if (unit.getFurnish().equals(furnishComboBox.getItemAt(i).toString()))
                    furnishComboBox.setSelectedIndex(i);
            }
            JTextField parkingUnitField = new JTextField();
            parkingUnitField.setText(unit.getParking_Unit());
            JTextField ownerUsernameField = new JTextField();
            ownerUsernameField.setText(unit.getOwner_Username());
            JTextField residentUsernameField = new JTextField();
            residentUsernameField.setText(unit.getResident_Username());
            floorField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9'))
                        e.consume();
                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Admin_Executive_Function.Button addButton = new Admin_Executive_Function.Button("Add New Unit");
            Admin_Executive_Function.Button cancelButton = new Admin_Executive_Function.Button("Cancel");

            addButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(floorField);
            panel2.add(jLabelLeft[1]);
            panel2.add(unitIDField);
            panel2.add(jLabelLeft[2]);
            panel2.add(completeYearField);
            panel2.add(jLabelLeft[3]);
            panel2.add(furnishComboBox);
            panel2.add(jLabelLeft[4]);
            panel2.add(parkingUnitField);
            panel2.add(jLabelLeft[5]);
            panel2.add(ownerUsernameField);
            panel2.add(jLabelLeft[5]);
            panel2.add(residentUsernameField);
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
                    Unit newUnit = new Unit(Integer.parseInt(floorField.getText()), unitIDField.getText(), Integer.parseInt(completeYearField.getText()), furnishComboBox.getSelectedItem().toString(), parkingUnitField.getText(), ownerUsernameField.getText(), residentUsernameField.getText());
                    try {
                        boolean check = newUnit.check_Unit_Availability(newUnit.getUnitID());
                        if (check) {
                            JOptionPane.showMessageDialog(null, "Unit ID already existed", "Unit ID found", JOptionPane.ERROR_MESSAGE);
                        } else {
                            check = newUnit.check_Parking_Unit_Availability(newUnit.getParking_Unit());
                            if (check){
                                JOptionPane.showMessageDialog(null, "Parking Unit already occupied", "Parking Unit Occupied", JOptionPane.ERROR_MESSAGE);
                            } else {
                                check = new Resident().check_Resident_Availability(ownerUsernameField.getText()) || new Resident().check_Resident_Availability(residentUsernameField.getText());
                                if (!check) {
                                    JOptionPane.showMessageDialog(null, "Resident or Owner Username not exists, please register first.", "Resident or owner username not exists", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    new Admin_Executive_Function.Admin_Executive().unit_Modify(newUnit, unit.getUnitID());
                                    JOptionPane.showMessageDialog(null, "Unit adding successful", "Unit adding successful", JOptionPane.INFORMATION_MESSAGE);
                                    new Admin_Executive_Unit_Management(executiveID).run(executiveID);
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
                        new Admin_Executive_Unit_Management(executiveID).run(executiveID);
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private static class viewFrame extends JFrame {
        public viewFrame(Unit unit) throws ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            MaskFormatter yearMask = new MaskFormatter("####");

            JLabel formTitle = new JLabel("UNIT DETAILS");
            JLabel[] jLabelLeft = {new JLabel("Floor"), new JLabel("Unit ID"),
                    new JLabel("Completer Year"), new JLabel("Furnish situation"),
                    new JLabel("Parking Unit"), new JLabel("Owner Username"),
                    new JLabel("Resident Username")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }
            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(Integer.toString(unit.getFloor())), new JLabel(unit.getUnitID()),
                    new JLabel(Integer.toString(unit.getComplete_Year())), new JLabel(unit.getFurnish()),
                    new JLabel(unit.getParking_Unit()), new JLabel(unit.getOwner_Username()),
                    new JLabel(unit.getResident_Username())};
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
        new Admin_Executive_Unit_Management("AD01").run("AD01");
    }
}
