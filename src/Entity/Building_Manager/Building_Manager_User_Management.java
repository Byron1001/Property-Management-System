package Entity.Building_Manager;

import Entity.Executive.Account_Executive.Account_Executive_Function;
import Entity.Executive.Admin_Executive.Admin_Executive_Function;
import Entity.Executive.Building_Executive.Building_Executive_Function;
import Entity.Executive.Executive;
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
import java.util.ArrayList;

public class Building_Manager_User_Management extends JFrame {
    public PanelBorder panelBorderLeft, panelBorderRight, panelBorderIn;
    public UIPackage.Component.Menu menu = new Menu();
    public Header header = new Header();
    public Form_Home formHome = new Form_Home();
    public Table tableData = new Table();
    public Color backgroundColor = Color.WHITE;
    public String buildingManagerID;
    public JScrollPane scrollPane;
    public GridBagConstraints constraints;
    public JPanel panel;
    private ArrayList<Executive> userArrayList = new ArrayList<>();
    public Building_Manager_Function.Button addButton, deleteButton, viewButton, modifyButton;

    public Building_Manager_User_Management(String buildingManagerID) throws IOException, ClassNotFoundException {
        this.buildingManagerID = buildingManagerID;
        menu.initMoving(Building_Manager_User_Management.this);

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

        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), buildingManagerID, TitledBorder.LEFT, TitledBorder.TOP);
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
        buttonPanel.setLayout(new GridLayout(2, 2, 40, 15));
        buttonPanel.setBackground(Color.white);

        addButton = new Building_Manager_Function.Button("Add New User");
        modifyButton = new Building_Manager_Function.Button("Modify User Info");
        deleteButton = new Building_Manager_Function.Button("Delete User");
        viewButton = new Building_Manager_Function.Button("View User Info");

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

        ArrayList<Account_Executive_Function.Account_Executive> account_executiveArrayList = new Account_Executive_Function.Account_Executive().getArrayList();
        ArrayList<Building_Executive_Function.Building_Executive> buildingExecutiveArrayList = new Building_Executive_Function.Building_Executive().getArrayList();
        ArrayList<Admin_Executive_Function.Admin_Executive> adminExecutiveArrayList = new Admin_Executive_Function.Admin_Executive().getArrayList();
        userArrayList.addAll(account_executiveArrayList);
        userArrayList.addAll(buildingExecutiveArrayList);
        userArrayList.addAll(adminExecutiveArrayList);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog.setDefaultLookAndFeelDecorated(false);
                Object[] selectionValues = {"Account Executive", "Admin Executive", "Building Executive"};
                String initialSelection = selectionValues[0].toString();
                Object selection = JOptionPane.showInputDialog(null, "Which executive do you want to add?",
                        "Position of Executive", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
                if (selection != null) {
                    try {
                        new addFrame(selection.toString()).setVisible(true);
                        dispose();
                    } catch (IOException | ClassNotFoundException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                if (row != -1){
                    try {
                        Executive userSelected = userArrayList.get(row);
                        new Building_Manager_User_Management.modifyFrame(userSelected).setVisible(true);
                        dispose();
                    } catch (IOException | ClassNotFoundException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the user", "choice error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Executive userSelected = userArrayList.get(row);
                    try {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure to delete this User?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
                        int types = new Executive().check_Executive_Position(userSelected.getExecutiveID());
                        if (result == JOptionPane.YES_OPTION) {
                            if (types == 1) {
                                new Building_Manager_Function.Building_Manager().delete_Account_Executive(userSelected.getExecutiveID());
                            } else if (types == 2) {
                                new Building_Manager_Function.Building_Manager().delete_Admin_Executive(userSelected.getExecutiveID());
                            } else if (types == 3) {
                                new Building_Manager_Function.Building_Manager().delete_Building_Executive(userSelected.getExecutiveID());
                            }
                            JOptionPane.showMessageDialog(null, "User deleted", "User delete success", JOptionPane.INFORMATION_MESSAGE);
                            new Building_Manager_User_Management(buildingManagerID).run(buildingManagerID);
                            dispose();
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the User", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Executive userSelected = userArrayList.get(row);
                    try {
                        new Building_Manager_User_Management.viewFrame(userSelected).setVisible(true);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the User", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
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

    public void run(String buildingManagerID) throws IOException, ClassNotFoundException {
        final String[] column = {"User Executive ID", "Name", "Gender", "Contact Number", "Position Name"};
        Building_Manager_User_Management frame = new Building_Manager_User_Management(buildingManagerID);
        frame.formHome.removeAll();
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("employee", "User Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("report", "Report", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("deposit", "Operation and Budget Planning", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Team Structure Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#f4791f");
        frame.menu.colorLeft = Color.decode("#659999");
        frame.menu.initMoving(frame);
        frame.tableData.statusAllowed = false;
        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }
        frame.tableData.preferredColumnWidth = 160;

        for (Executive executive : userArrayList) {
            frame.tableData.addRow(executive.getStringArray(executive));
        }

        frame.formHome.removeAll();
        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException, ClassNotFoundException {
                if (index == 0) {
                    Building_Manager_Interface building_ManagerInterface = new Building_Manager_Interface(buildingManagerID);
                    building_ManagerInterface.frame.setVisible(true);
                    frame.dispose();
                } else if (index == 1) {
                } else if (index == 2) {
                    new Building_Manager_Report(buildingManagerID);
                    frame.dispose();
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

        frame.setVisible(true);
    }

    private class addFrame extends JFrame {
        public addFrame(String position_Name) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("User ADDING");
            JLabel[] jLabelLeft = {new JLabel("User ExecutiveID"), new JLabel("Name"),
                    new JLabel("Gender"), new JLabel("Contact Number"),
                    new JLabel("Position Name")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            String[] positionNameArray = {"Account Executive", "Admin Executive", "Building Executive"};
            int position = 0;
            MaskFormatter idMask = new MaskFormatter();
            if (positionNameArray[0].equals(position_Name)) {
                idMask = new MaskFormatter("AC##");
                position = 1;
            } else if (positionNameArray[1].equals(position_Name)) {
                idMask = new MaskFormatter("AD##");
                position = 2;
            } else if (positionNameArray[2].equals(position_Name)) {
                idMask = new MaskFormatter("BE##");
                position = 3;
            }
            JFormattedTextField executiveIDField = new JFormattedTextField(idMask);
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
            JTextField positionNameField = new JTextField(position_Name);
            positionNameField.setEditable(false);

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Building_Manager_Function.Button addButton = new Building_Manager_Function.Button("Add New Executive");
            Building_Manager_Function.Button cancelButton = new Building_Manager_Function.Button("Cancel");

            addButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(executiveIDField);
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
            panel2.add(positionNameField);
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

            int finalPosition = position;
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!contactNumberField.getText().equals("01 -       ") && !nameField.getText().equals("")){
                        char gender = 'M';
                        if (femaleButton.isSelected())
                            gender = 'F';
                        if (finalPosition == 1) {
                            Account_Executive_Function.Account_Executive accountExecutive = new Account_Executive_Function.Account_Executive(executiveIDField.getText(), nameField.getText(), gender, contactNumberField.getText());
                            try {
                                boolean check = new Account_Executive_Function.Account_Executive().check_Account_Executive_Availability(accountExecutive.getExecutiveID());
                                if (check) {
                                    JOptionPane.showMessageDialog(null, "User Username already existed", "User Username found", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    new Building_Manager_Function.Building_Manager().add_Account_Executive(accountExecutive);
                                    JOptionPane.showMessageDialog(null, "User registration successful.Please ask User to sign up and remember to register new unit if needed.", "User adding successful", JOptionPane.INFORMATION_MESSAGE);
                                    new Building_Manager_User_Management(buildingManagerID).run(buildingManagerID);
                                    dispose();
                                }
                            } catch (IOException | ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else if (finalPosition == 2) {
                            Admin_Executive_Function.Admin_Executive adminExecutive = new Admin_Executive_Function.Admin_Executive(executiveIDField.getText(), nameField.getText(), gender, contactNumberField.getText());
                            try {
                                boolean check = new Account_Executive_Function.Account_Executive().check_Account_Executive_Availability(adminExecutive.getExecutiveID());
                                if (check) {
                                    JOptionPane.showMessageDialog(null, "User Username already existed", "User Username found", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    new Building_Manager_Function.Building_Manager().add_Admin_Executive(adminExecutive);
                                    JOptionPane.showMessageDialog(null, "User registration successful.Please ask User to sign up and remember to register new unit if needed.", "User adding successful", JOptionPane.INFORMATION_MESSAGE);
                                    new Building_Manager_User_Management(buildingManagerID).run(buildingManagerID);
                                    dispose();
                                }
                            } catch (IOException | ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else if (finalPosition == 3) {
                            Building_Executive_Function.Building_Executive buildingExecutive = new Building_Executive_Function.Building_Executive(executiveIDField.getText(), nameField.getText(), gender, contactNumberField.getText());
                            try {
                                boolean check = new Building_Executive_Function.Building_Executive().check_Building_Executive_Availability(buildingExecutive.getExecutiveID());
                                if (check) {
                                    JOptionPane.showMessageDialog(null, "User Username already existed", "User Username found", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    new Building_Manager_Function.Building_Manager().add_Building_Executive(buildingExecutive);
                                    JOptionPane.showMessageDialog(null, "User registration successful.Please ask User to sign up and remember to register new unit if needed.", "User adding successful", JOptionPane.INFORMATION_MESSAGE);
                                    new Building_Manager_User_Management(buildingManagerID).run(buildingManagerID);
                                }
                            } catch (IOException | ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please provide complete information", "Information lost", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Building_Manager_User_Management(buildingManagerID).run(buildingManagerID);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private class modifyFrame extends JFrame {
        MaskFormatter idMask = new MaskFormatter();
        public modifyFrame(Executive executive) throws IOException, ClassNotFoundException, ParseException {
            String[] positionNameArray = {"Account Executive", "Admin Executive", "Building Executive"};

            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("User MODIFYING");
            JLabel[] jLabelLeft = {new JLabel("User ExecutiveID"), new JLabel("Name"),
                    new JLabel("Gender"), new JLabel("Contact Number"),
                    new JLabel("Position Name")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            int position = 0;
            if (positionNameArray[0].equals(executive.getPosition())) {
                idMask = new MaskFormatter("AC##");
                position = 1;
            } else if (positionNameArray[1].equals(executive.getPosition())) {
                idMask = new MaskFormatter("AD##");
                position = 2;
            } else if (positionNameArray[2].equals(executive.getPosition())) {
                idMask = new MaskFormatter("BE##");
                position = 3;
            }
            JFormattedTextField executiveIDField = new JFormattedTextField(idMask);
            executiveIDField.setText(executive.getExecutiveID());
            executiveIDField.setEditable(false);
            JTextField nameField = new JTextField();
            nameField.setText(executive.getName());
            JRadioButton maleButton = new JRadioButton("Male");
            JRadioButton femaleButton = new JRadioButton("Female");

            panel1.add(maleButton);
            panel1.add(femaleButton);
            ButtonGroup group = new ButtonGroup();
            group.add(maleButton);
            group.add(femaleButton);

            if (executive.getGender() == 'M') {
                maleButton.setSelected(true);
            } else if (executive.getGender() == 'F') {
                femaleButton.setSelected(true);
            }

            MaskFormatter contactNumberMask = new MaskFormatter("01#-#######");
            JFormattedTextField contactNumberField = new JFormattedTextField(contactNumberMask);
            contactNumberField.setText(executive.getContact_Number());
            JTextField positionNameField = new JTextField(executive.getPosition());
            positionNameField.setEditable(false);

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Building_Manager_Function.Button modifyButton = new Building_Manager_Function.Button("Add New Executive");
            Building_Manager_Function.Button cancelButton = new Building_Manager_Function.Button("Cancel");

            addButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(executiveIDField);
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
            panel2.add(positionNameField);
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

            int finalPosition = position;
            modifyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!contactNumberField.getText().equals("01 -       ") && !nameField.getText().equals("")){
                        char gender = 'M';
                        if (femaleButton.isSelected())
                            gender = 'F';
                        if (finalPosition == 1) {
                            Account_Executive_Function.Account_Executive accountExecutive = new Account_Executive_Function.Account_Executive(executiveIDField.getText(), nameField.getText(), gender, contactNumberField.getText());
                            try {
                                boolean check = new Account_Executive_Function.Account_Executive().check_Account_Executive_Availability(accountExecutive.getExecutiveID());
                                if (!check) {
                                    JOptionPane.showMessageDialog(null, "User Username not existed", "User Username not found", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    new Building_Manager_Function.Building_Manager().modify_Account_Executive(accountExecutive, executive.getExecutiveID());
                                    JOptionPane.showMessageDialog(null, "User modification successful.Please ask User to check.", "User adding successful", JOptionPane.INFORMATION_MESSAGE);
                                    new Building_Manager_User_Management(buildingManagerID).run(buildingManagerID);
                                    dispose();
                                }
                            } catch (IOException | ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else if (finalPosition == 2) {
                            Admin_Executive_Function.Admin_Executive adminExecutive = new Admin_Executive_Function.Admin_Executive(executiveIDField.getText(), nameField.getText(), gender, contactNumberField.getText());
                            try {
                                boolean check = new Account_Executive_Function.Account_Executive().check_Account_Executive_Availability(adminExecutive.getExecutiveID());
                                if (!check) {
                                    JOptionPane.showMessageDialog(null, "User Username not existed", "User Username not found", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    new Building_Manager_Function.Building_Manager().modify_Admin_Executive(adminExecutive, executive.getExecutiveID());
                                    JOptionPane.showMessageDialog(null, "User modification successful.Please ask User to check.", "User adding successful", JOptionPane.INFORMATION_MESSAGE);
                                    new Building_Manager_User_Management(buildingManagerID).run(buildingManagerID);
                                    dispose();
                                }
                            } catch (IOException | ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else if (finalPosition == 3) {
                            Building_Executive_Function.Building_Executive buildingExecutive = new Building_Executive_Function.Building_Executive(executiveIDField.getText(), nameField.getText(), gender, contactNumberField.getText());
                            try {
                                boolean check = new Building_Executive_Function.Building_Executive().check_Building_Executive_Availability(buildingExecutive.getExecutiveID());
                                if (!check) {
                                    JOptionPane.showMessageDialog(null, "User Username not existed", "User Username not found", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    new Building_Manager_Function.Building_Manager().modify_Building_Executive(buildingExecutive, executive.getExecutiveID());
                                    JOptionPane.showMessageDialog(null, "User modification successful.Please ask User to check.", "User modification successful", JOptionPane.INFORMATION_MESSAGE);
                                    new Building_Manager_User_Management(buildingManagerID).run(buildingManagerID);
                                    dispose();
                                }
                            } catch (IOException | ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please provide complete information", "Information lost", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Building_Manager_User_Management(buildingManagerID).run(buildingManagerID);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private class viewFrame extends JFrame {
        public viewFrame(Executive executive) throws ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("EXECUTIVE DETAILS");
            JLabel[] jLabelLeft = {new JLabel("User ExecutiveID"), new JLabel("Name"),
                    new JLabel("Gender"), new JLabel("Contact Number"),
                    new JLabel("Position Name")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(executive.getExecutiveID()), new JLabel(executive.getName()),
                    new JLabel(Character.toString(executive.getGender())), new JLabel(executive.getContact_Number()),
                    new JLabel(executive.getPosition())};

            Building_Manager_Function.Button button = new Building_Manager_Function.Button("Close");
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
        new Building_Manager_User_Management("BM01").run("BM01");
    }
}
