package Entity.Building_Manager;

import Entity.Employee.Cleaner;
import Entity.Employee.Employee;
import Entity.Employee.SecurityGuard.SecurityGuard;
import Entity.Employee.Technician;
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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

public class Building_Manager_Team_Management extends JFrame {
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
    public String[] teamNameArray = {"SecurityGuard Team", "Cleaner Team", "Technician Team", "Employee Team", "Account Executive Team", "Admin Executive Team", "Building Executive Team"};

    public Building_Manager_Function.Button addButton, deleteButton, viewButton, modifyButton;

    public Building_Manager_Team_Management(String buildingManagerID) throws IOException, ClassNotFoundException {
        this.buildingManagerID = buildingManagerID;
        menu.initMoving(Entity.Building_Manager.Building_Manager_Team_Management.this);

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
        buttonPanel.setLayout(new GridLayout(2, 2, 15, 15));
        buttonPanel.setBackground(Color.white);

        addButton = new Building_Manager_Function.Button("Add New Team Leader");
        modifyButton = new Building_Manager_Function.Button("Modify Team Leader Info");
        deleteButton = new Building_Manager_Function.Button("Delete Team Leader");
        viewButton = new Building_Manager_Function.Button("View Team Leader Info");

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

        ArrayList<SecurityGuard> securityGuardArrayList = new SecurityGuard().getArrayList();
        ArrayList<Cleaner> cleanerArrayList = new Cleaner().getArrayList();
        ArrayList<Technician> technicianArrayList = new Technician().getArrayList();

        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        employeeArrayList.addAll(securityGuardArrayList);
        employeeArrayList.addAll(cleanerArrayList);
        employeeArrayList.addAll(technicianArrayList);

        ArrayList<Account_Executive_Function.Account_Executive> account_executiveArrayList = new Account_Executive_Function.Account_Executive().getArrayList();
        ArrayList<Admin_Executive_Function.Admin_Executive> adminExecutiveArrayList = new Admin_Executive_Function.Admin_Executive().getArrayList();
        ArrayList<Building_Executive_Function.Building_Executive> buildingExecutiveArrayList = new Building_Executive_Function.Building_Executive().getArrayList();
        ArrayList<Building_Manager_Function.Building_Manager.Team_Leader> teamLeaderArrayList = new Building_Manager_Function.Building_Manager.Team_Leader().getArrayList();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog.setDefaultLookAndFeelDecorated(false);
                Object[] selectionValues = teamNameArray;
                String initialSelection = selectionValues[0].toString();
                Object selection = JOptionPane.showInputDialog(null, "Which team leader do you want to add?",
                        "Team selection", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
                if (selection != null) {
                    int index = 0;
                    for (int i = 0;i < selectionValues.length;i++){
                        if (selectionValues[i].equals(selection))
                            index = i;
                    }
                    ArrayList<String[]> userArrayList = new ArrayList<>();
                    switch (index){
                        case 0:
                            for (SecurityGuard securityGuard : securityGuardArrayList){
                                userArrayList.add(new String[]{securityGuard.getEmployeeID(), securityGuard.getName()});
                            }
                            break;
                        case 1:
                            for (Cleaner cleaner : cleanerArrayList){
                                userArrayList.add(new String[]{cleaner.getEmployeeID(), cleaner.getName()});
                            }
                            break;
                        case 2:
                            for (Technician technician : technicianArrayList){
                                userArrayList.add(new String[]{technician.getEmployeeID(), technician.getName()});
                            }
                            break;
                        case 3:
                            for (Employee employee : employeeArrayList){
                                userArrayList.add(new String[]{employee.getEmployeeID(), employee.getPosition_Name()});
                            }
                            break;
                        case 4:
                            for (Account_Executive_Function.Account_Executive accountExecutive : account_executiveArrayList){
                                userArrayList.add(new String[]{accountExecutive.getExecutiveID(), accountExecutive.getName()});
                            }
                            break;
                        case 5:
                            for (Admin_Executive_Function.Admin_Executive adminExecutive : adminExecutiveArrayList){
                                userArrayList.add(new String[]{adminExecutive.getExecutiveID(), adminExecutive.getName()});
                            }
                            break;
                        default:
                            for (Building_Executive_Function.Building_Executive buildingExecutive : buildingExecutiveArrayList){
                                userArrayList.add(new String[]{buildingExecutive.getExecutiveID(), buildingExecutive.getName()});
                            }
                            break;
                    }
                    try {
                        new addFrame(userArrayList, selection.toString()).setVisible(true);
                    } catch (IOException | ClassNotFoundException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                    dispose();
                }
            }
        });
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                if (row != -1){
                    try {
                        Building_Manager_Function.Building_Manager.Team_Leader teamLeaderSelected = teamLeaderArrayList.get(row);
                        new Entity.Building_Manager.Building_Manager_Team_Management.modifyFrame(teamLeaderSelected).setVisible(true);
                        dispose();
                    } catch (IOException | ClassNotFoundException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select the team leader", "Choice error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Building_Manager_Function.Building_Manager.Team_Leader teamLeader = teamLeaderArrayList.get(row);
                    try {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure to delete this Team leader?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            teamLeader.delete_Team_Leader(teamLeader.getLeader_Username());
                            JOptionPane.showMessageDialog(null, "Leader deleted", "Leader delete success", JOptionPane.INFORMATION_MESSAGE);
                            new Building_Manager_Team_Management(buildingManagerID).run(buildingManagerID);
                            dispose();
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select the team leader", "Choice error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Building_Manager_Function.Building_Manager.Team_Leader teamLeaderSelected = teamLeaderArrayList.get(row);
                    try {
                        new Entity.Building_Manager.Building_Manager_Team_Management.viewFrame(teamLeaderSelected).setVisible(true);
                    } catch (ParseException | IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select the team leader", "Choice error", JOptionPane.INFORMATION_MESSAGE);
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
        final String[] column = {"Team Leader Username", "Team Name", "Position Name"};
        Entity.Building_Manager.Building_Manager_Team_Management frame = new Entity.Building_Manager.Building_Manager_Team_Management(buildingManagerID);
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
        frame.tableData.preferredColumnWidth = 260;

        ArrayList<Building_Manager_Function.Building_Manager.Team_Leader> teamLeaderArrayList = new Building_Manager_Function.Building_Manager.Team_Leader().getArrayList();

        for (Building_Manager_Function.Building_Manager.Team_Leader teamLeader : teamLeaderArrayList) {
            frame.tableData.addRow(teamLeader.getStringArray(teamLeader));
        }

        frame.formHome.removeAll();
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
                    new Building_Manager_Report(buildingManagerID);
                    frame.dispose();
                } else if (index == 3) {
                    new Building_Manager_Operation(buildingManagerID).run(buildingManagerID);
                    frame.dispose();
                } else if (index == 4) {
                } else if (index == 5) {
                    new Login_Frame();
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }

    private class addFrame extends JFrame {
        public addFrame(ArrayList<String[]> nameIDArrayList, String teamName) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("Team Leader ADDING");
            JLabel[] jLabelLeft = {new JLabel("Leader Username"), new JLabel("Team"), new JLabel("Position")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }
            String[] positionNameArray = {"Team Leader", "Team Vice Leader"};
            JComboBox<String> leaderUsernameComboBox = new JComboBox<>();
            for (String[] data : nameIDArrayList){
                leaderUsernameComboBox.addItem(data[0]+ " " + data[1]);
            }
            JTextField teamNameField = new JTextField(teamName);
            teamNameField.setEditable(false);
            JComboBox<String> positionComboBox = new JComboBox<>();
            for (String name : positionNameArray){
                positionComboBox.addItem(name);
            }

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Building_Manager_Function.Button addButton = new Building_Manager_Function.Button("Add New Team Leader");
            Building_Manager_Function.Button cancelButton = new Building_Manager_Function.Button("Cancel");

            addButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(leaderUsernameComboBox);
            panel2.add(jLabelLeft[1]);
            panel2.add(teamNameField);
            panel2.add(jLabelLeft[2]);
            panel2.add(positionComboBox);
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
                    String teamLeaderUsername = Objects.requireNonNull(leaderUsernameComboBox.getSelectedItem()).toString().split(" ", 2)[0];
                    String teamName = teamNameField.getText();
                    String position = Objects.requireNonNull(positionComboBox.getSelectedItem()).toString();
                    Building_Manager_Function.Building_Manager.Team_Leader teamLeaderNew = new Building_Manager_Function.Building_Manager.Team_Leader(teamLeaderUsername, teamName, position);
                    boolean check = true;
                    try {
                        if (teamLeaderNew.check_Team_Leader_Availability(teamLeaderNew.getTeam(), position)){
                            int result = JOptionPane.showConfirmDialog(null, "The team has leader already.Are you sure to add more leader?", "Leader found", JOptionPane.YES_NO_OPTION);
                            if (result == JOptionPane.NO_OPTION)
                                check = false;
                        }
                        if (check){
                            teamLeaderNew.add_Team_Leader(teamLeaderNew);
                            JOptionPane.showMessageDialog(null, "Team Leader added", "Team Leader adding success", JOptionPane.INFORMATION_MESSAGE);
                            new Building_Manager_Team_Management(buildingManagerID).run(buildingManagerID);
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
                        new Building_Manager_Team_Management(buildingManagerID).run(buildingManagerID);
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    dispose();
                }
            });
        }
    }

    private class modifyFrame extends JFrame {
        public modifyFrame(Building_Manager_Function.Building_Manager.Team_Leader teamLeader) throws IOException, ClassNotFoundException, ParseException {
            ArrayList<SecurityGuard> securityGuardArrayList = new SecurityGuard().getArrayList();
            ArrayList<Cleaner> cleanerArrayList = new Cleaner().getArrayList();
            ArrayList<Technician> technicianArrayList = new Technician().getArrayList();

            ArrayList<Employee> employeeArrayList = new ArrayList<>();
            employeeArrayList.addAll(securityGuardArrayList);
            employeeArrayList.addAll(cleanerArrayList);
            employeeArrayList.addAll(technicianArrayList);

            ArrayList<Account_Executive_Function.Account_Executive> account_executiveArrayList = new Account_Executive_Function.Account_Executive().getArrayList();
            ArrayList<Admin_Executive_Function.Admin_Executive> adminExecutiveArrayList = new Admin_Executive_Function.Admin_Executive().getArrayList();
            ArrayList<Building_Executive_Function.Building_Executive> buildingExecutiveArrayList = new Building_Executive_Function.Building_Executive().getArrayList();

            ArrayList<String[]> userArrayList = new ArrayList<>();
            int index = 0;
            for (int i = 0;i < teamNameArray.length;i++){
                if (teamNameArray[i].equals(teamLeader.getTeam()))
                    index = i;
            }

            switch (index) {
                case 0:
                    for (SecurityGuard securityGuard : securityGuardArrayList) {
                        userArrayList.add(new String[]{securityGuard.getEmployeeID(), securityGuard.getName()});
                    }
                    break;
                case 1:
                    for (Cleaner cleaner : cleanerArrayList) {
                        userArrayList.add(new String[]{cleaner.getEmployeeID(), cleaner.getName()});
                    }
                    break;
                case 2:
                    for (Technician technician : technicianArrayList) {
                        userArrayList.add(new String[]{technician.getEmployeeID(), technician.getName()});
                    }
                    break;
                case 3:
                    for (Employee employee : employeeArrayList) {
                        userArrayList.add(new String[]{employee.getEmployeeID(), employee.getPosition_Name()});
                    }
                    break;
                case 4:
                    for (Account_Executive_Function.Account_Executive accountExecutive : account_executiveArrayList) {
                        userArrayList.add(new String[]{accountExecutive.getExecutiveID(), accountExecutive.getName()});
                    }
                    break;
                case 5:
                    for (Admin_Executive_Function.Admin_Executive adminExecutive : adminExecutiveArrayList) {
                        userArrayList.add(new String[]{adminExecutive.getExecutiveID(), adminExecutive.getName()});
                    }
                    break;
                default:
                    for (Building_Executive_Function.Building_Executive buildingExecutive : buildingExecutiveArrayList) {
                        userArrayList.add(new String[]{buildingExecutive.getExecutiveID(), buildingExecutive.getName()});
                    }
                    break;
            }

            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("Team Leader Modifying");
            JLabel[] jLabelLeft = {new JLabel("Leader Username"), new JLabel("Team"), new JLabel("Position")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }
            String[] positionNameArray = {"Team Leader", "Team Vice Leader"};
            JComboBox<String> leaderUsernameComboBox = new JComboBox<>();
            for (String[] data : userArrayList){
                leaderUsernameComboBox.addItem(data[0]+ " " + data[1]);
            }
            leaderUsernameComboBox.setEnabled(false);
            for (int i = 0;i < userArrayList.size();i++){
                if (teamLeader.getLeader_Username().equals(userArrayList.get(i)[0]))
                    leaderUsernameComboBox.setSelectedIndex(i);
            }
            JTextField teamNameField = new JTextField(teamNameArray[index]);
            teamNameField.setEditable(false);
            JComboBox<String> positionComboBox = new JComboBox<>();
            for (String name : positionNameArray){
                positionComboBox.addItem(name);
            }
            for (int i = 0;i < positionNameArray.length;i++){
                if (positionNameArray[i].equals(teamLeader.getPosition()))
                    positionComboBox.setSelectedIndex(i);
            }

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Building_Manager_Function.Button modifyButton = new Building_Manager_Function.Button("Modify team leader info");
            Building_Manager_Function.Button cancelButton = new Building_Manager_Function.Button("Cancel");

            modifyButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(leaderUsernameComboBox);
            panel2.add(jLabelLeft[1]);
            panel2.add(teamNameField);
            panel2.add(jLabelLeft[2]);
            panel2.add(positionComboBox);
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
                    String teamLeaderUsername = Objects.requireNonNull(leaderUsernameComboBox.getSelectedItem()).toString().split(" ", 2)[0];
                    String teamName = teamNameField.getText();
                    String position = Objects.requireNonNull(positionComboBox.getSelectedItem()).toString();
                    Building_Manager_Function.Building_Manager.Team_Leader teamLeaderNew = new Building_Manager_Function.Building_Manager.Team_Leader(teamLeaderUsername, teamName, position);
                    boolean check = true;
                    try {
                        if (teamLeaderNew.check_Team_Leader_Availability(teamLeaderNew.getTeam(), position)){
                            int result = JOptionPane.showConfirmDialog(null, "The team has leader already.Are you sure to add more leader?", "Leader found", JOptionPane.YES_NO_OPTION);
                            if (result == JOptionPane.NO_OPTION)
                                check = false;
                        }
                        if (check){
                            teamLeaderNew.modify_Team_Leader(teamLeaderNew);
                            JOptionPane.showMessageDialog(null, "Team Leader modified", "Team Leader modification success", JOptionPane.INFORMATION_MESSAGE);
                            new Building_Manager_Team_Management(buildingManagerID).run(buildingManagerID);
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
                        new Building_Manager_Team_Management(buildingManagerID).run(buildingManagerID);
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    dispose();
                }
            });
        }
    }

    private class viewFrame extends JFrame {
        public viewFrame(Building_Manager_Function.Building_Manager.Team_Leader teamLeader) throws ParseException, IOException, ClassNotFoundException {
            ArrayList<SecurityGuard> securityGuardArrayList = new SecurityGuard().getArrayList();
            ArrayList<Cleaner> cleanerArrayList = new Cleaner().getArrayList();
            ArrayList<Technician> technicianArrayList = new Technician().getArrayList();
            ArrayList<Account_Executive_Function.Account_Executive> account_executiveArrayList = new Account_Executive_Function.Account_Executive().getArrayList();
            ArrayList<Admin_Executive_Function.Admin_Executive> adminExecutiveArrayList = new Admin_Executive_Function.Admin_Executive().getArrayList();
            ArrayList<Building_Executive_Function.Building_Executive> buildingExecutiveArrayList = new Building_Executive_Function.Building_Executive().getArrayList();
            ArrayList<Employee> employeeArrayList = new ArrayList<>();
            employeeArrayList.addAll(securityGuardArrayList);
            employeeArrayList.addAll(cleanerArrayList);
            employeeArrayList.addAll(technicianArrayList);
            ArrayList<Executive> executiveArrayList = new ArrayList<>();
            executiveArrayList.addAll(account_executiveArrayList);
            executiveArrayList.addAll(adminExecutiveArrayList);
            executiveArrayList.addAll(buildingExecutiveArrayList);
            String leaderName = "";
            for (Employee employee : employeeArrayList){
                if (employee.getEmployeeID().equals(teamLeader.getLeader_Username()))
                    leaderName = employee.getName();
            }
            for (Executive executive : executiveArrayList){
                if (executive.getExecutiveID().equals(teamLeader.getLeader_Username()))
                    leaderName = executive.getName();
            }

            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("TEAM LEADER DETAILS");
            JLabel[] jLabelLeft = {new JLabel("Team leader Username"), new JLabel("Name"),
                    new JLabel("Team Name"), new JLabel("Position Name")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(teamLeader.getLeader_Username()), new JLabel(leaderName),
                    new JLabel(teamLeader.getTeam()), new JLabel(teamLeader.getPosition())};

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
        new Entity.Building_Manager.Building_Manager_Team_Management("BM01").run("BM01");
    }
}