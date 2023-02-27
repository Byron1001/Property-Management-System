package Entity.Executive.Building_Executive;

import Entity.Employee.SecurityGuard.SecurityGuard;
import Entity.Login.Login_Frame;
import Entity.Patrolling;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Building_Executive_Patrolling_Management extends JFrame {
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
    public Building_Executive_Function.Button setupButton, deleteButton, viewButton, modifyButton;

    public Building_Executive_Patrolling_Management(String executiveID) throws IOException, ClassNotFoundException {
        this.executiveID = executiveID;
        menu.initMoving(Building_Executive_Patrolling_Management.this);

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
        buttonPanel.setLayout(new GridLayout(2, 2, 40, 15));
        buttonPanel.setBackground(Color.white);

        setupButton = new Building_Executive_Function.Button("Setup new patrolling");
        modifyButton = new Building_Executive_Function.Button("Modify patrolling details");
        deleteButton = new Building_Executive_Function.Button("Delete patrolling");
        viewButton = new Building_Executive_Function.Button("View patrolling details");

        buttonPanel.add(setupButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        constraints.gridy++;
        panelBorderIn.add(buttonPanel, constraints);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);

        ArrayList<Patrolling> patrollingArrayList = new Patrolling().getArrayList();
        setupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Building_Executive_Patrolling_Management.setupFrame().setVisible(true);
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
                        Patrolling patrollingSelected = patrollingArrayList.get(row);
                        new Building_Executive_Patrolling_Management.modifyFrame(patrollingSelected).setVisible(true);
                        dispose();
                    } catch (IOException | ClassNotFoundException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the patrolling schedule", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Patrolling patrollingSelected = patrollingArrayList.get(row);
                    try {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure to delete this patrolling schedule?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            new Building_Executive_Function.Building_Executive().delete_Patrolling_Schedule(patrollingSelected.getPatrolID());
                            JOptionPane.showMessageDialog(null, "patrolling schedule deleted", "patrolling schedule delete success", JOptionPane.INFORMATION_MESSAGE);
                            new Building_Executive_Patrolling_Management(executiveID).run(executiveID);
                            dispose();
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the patrolling schedule", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Patrolling patrollingSelected = patrollingArrayList.get(row);
                    try {
                        new Building_Executive_Patrolling_Management.viewFrame(patrollingSelected).setVisible(true);
                    } catch (ParseException | IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the patrolling schedule", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
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
        final String[] column = {"Patrol ID", "Security Employee ID", "Day", "Time Start", "Time End"};
        Building_Executive_Patrolling_Management frame = new Building_Executive_Patrolling_Management(executiveID);
        frame.formHome.removeAll();
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("employee", "Employee Task", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("complaint", "Complaint Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("patrolling", "Patrolling Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("checkpoint", "Checkpoint Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("report", "Job Report", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#DECBA4");
        frame.menu.colorLeft = Color.decode("#3E5151");
        frame.menu.initMoving(frame);
        frame.tableData.statusAllowed = false;
        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }
        frame.tableData.preferredColumnWidth = 170;

        ArrayList<Patrolling> patrollingArrayList = new Patrolling().getArrayList();

        for (Patrolling patrolling : patrollingArrayList) {
            frame.tableData.addRow(patrolling.getStringArray(patrolling));
        }

        frame.formHome.removeAll();
        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException, ClassNotFoundException {
                if (index == 0) {
                    Entity.Executive.Building_Executive.Building_Executive_Interface building_Executive_Interface = new Entity.Executive.Building_Executive.Building_Executive_Interface(executiveID);
                    building_Executive_Interface.frame.setVisible(true);
                    frame.dispose();
                } else if (index == 1) {
                    new Building_Executive_Employee_Task(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 2) {
                    new Building_Executive_Complaint_Management(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 3) {
                } else if (index == 4) {
                    new Building_Executive_CheckPoint(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 5) {
                    Entity.Executive.Building_Executive.Building_Executive_Interface building_Executive_Interface = new Entity.Executive.Building_Executive.Building_Executive_Interface(executiveID);
                    building_Executive_Interface.frame.setVisible(true);
                    frame.dispose();
                } else if (index == 6) {
                    new Login_Frame();
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }

    private class setupFrame extends JFrame {
        private final ArrayList<SecurityGuard> securityGuardArrayList = new SecurityGuard().getArrayList();
        public setupFrame() throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
            MaskFormatter timeMask = new MaskFormatter("######");

            JLabel formTitle = new JLabel("PATROLLING SCHEDULE");
            JLabel[] jLabelLeft = {new JLabel("Patrol ID"), new JLabel("Security Guard Employee ID"),
                    new JLabel("Day"), new JLabel("Time start (HHmmss)"), new JLabel("Time End (HHmmss)")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            JTextField patrollingIDField = new JTextField(new Patrolling().get_Auto_PatrollingID());
            patrollingIDField.setEditable(false);
            JComboBox<String> employeeIDComboBox = new JComboBox<>();
            for (SecurityGuard securityGuard : securityGuardArrayList){
                employeeIDComboBox.addItem(securityGuard.getEmployeeID());
            }
            JComboBox<String> dayComboBox = new JComboBox<>();
            for (int i = 1;i <= 7;i++) {
                dayComboBox.addItem(Patrolling.DayName.Mon.getNameOfDay(i));
            }
            JFormattedTextField timeStartField = new JFormattedTextField(timeMask);
            JFormattedTextField timeEndField = new JFormattedTextField(timeMask);

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Building_Executive_Function.Button setupButton = new Building_Executive_Function.Button("Setup new patrolling schedule");
            Building_Executive_Function.Button cancelButton = new Building_Executive_Function.Button("Cancel");

            setupButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(patrollingIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(employeeIDComboBox);
            panel2.add(jLabelLeft[2]);
            panel2.add(dayComboBox);
            panel2.add(jLabelLeft[3]);
            panel2.add(timeStartField);
            panel2.add(jLabelLeft[4]);
            panel2.add(timeEndField);
            panel1.add(panel2, BorderLayout.CENTER);

            panel3.add(setupButton);
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

            setupButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Patrolling newPatrolling = new Patrolling(patrollingIDField.getText(), employeeIDComboBox.getSelectedItem().toString(), dayComboBox.getSelectedItem().toString(), LocalTime.parse(timeStartField.getText(), timeFormatter), LocalTime.parse(timeEndField.getText(), timeFormatter));
                    try {
                        new Building_Executive_Function.Building_Executive().add_Patrolling_Schedule(newPatrolling);
                        JOptionPane.showMessageDialog(null, "patrolling schedule adding successful", "patrolling schedule adding successful", JOptionPane.INFORMATION_MESSAGE);
                        new Building_Executive_Patrolling_Management(executiveID).run(executiveID);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Building_Executive_Patrolling_Management(executiveID).run(executiveID);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private class modifyFrame extends JFrame {
        private final ArrayList<SecurityGuard> securityGuardArrayList = new SecurityGuard().getArrayList();
        public modifyFrame(Patrolling patrolling) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
            MaskFormatter timeMask = new MaskFormatter("######");

            JLabel formTitle = new JLabel("PATROLLING SCHEDULE");
            JLabel[] jLabelLeft = {new JLabel("Patrol ID"), new JLabel("Security Guard Employee ID"),
                    new JLabel("Day"), new JLabel("Time start (HHmmss)"), new JLabel("Time End (HHmmss)")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            JTextField patrollingIDField = new JTextField(patrolling.getPatrolID());
            patrollingIDField.setEditable(false);
            JComboBox<String> employeeIDComboBox = new JComboBox<>();
            for (SecurityGuard securityGuard : securityGuardArrayList){
                employeeIDComboBox.addItem(securityGuard.getEmployeeID());
            }
            for (int i = 0;i < securityGuardArrayList.size();i++){
                if (patrolling.getEmployeeID().equals(employeeIDComboBox.getItemAt(i))){
                    employeeIDComboBox.setSelectedIndex(i);
                    break;
                }
            }
            JComboBox<String> dayComboBox = new JComboBox<>();
            for (int i = 1;i <= 7;i++) {
                dayComboBox.addItem(Patrolling.DayName.Mon.getNameOfDay(i));
            }
            dayComboBox.setSelectedIndex(Patrolling.DayName.Mon.getValue(patrolling.getDay()) - 1);

            JFormattedTextField timeStartField = new JFormattedTextField(timeMask);
            timeStartField.setText(patrolling.getStart_Time().format(timeFormatter));
            JFormattedTextField timeEndField = new JFormattedTextField(timeMask);
            timeEndField.setText(patrolling.getEnd_Time().format(timeFormatter));

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Building_Executive_Function.Button modifyButton = new Building_Executive_Function.Button("Modify patrolling schedule");
            Building_Executive_Function.Button cancelButton = new Building_Executive_Function.Button("Cancel");

            modifyButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(patrollingIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(employeeIDComboBox);
            panel2.add(jLabelLeft[2]);
            panel2.add(dayComboBox);
            panel2.add(jLabelLeft[3]);
            panel2.add(timeStartField);
            panel2.add(jLabelLeft[4]);
            panel2.add(timeEndField);
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
                    Patrolling newPatrolling = new Patrolling(patrollingIDField.getText(), employeeIDComboBox.getSelectedItem().toString(), dayComboBox.getSelectedItem().toString(), LocalTime.parse(timeStartField.getText(), timeFormatter), LocalTime.parse(timeEndField.getText(), timeFormatter));
                    try {
                        new Building_Executive_Function.Building_Executive().modify_Patrolling_Schedule(newPatrolling, patrolling.getPatrolID());
                        JOptionPane.showMessageDialog(null, "patrolling schedule modifying successful", "patrolling schedule modifying successful", JOptionPane.INFORMATION_MESSAGE);
                        new Building_Executive_Patrolling_Management(executiveID).run(executiveID);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Building_Executive_Patrolling_Management(executiveID).run(executiveID);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private static class viewFrame extends JFrame {
        private final ArrayList<Patrolling> patrollingArrayList = new Patrolling().getArrayList();
        public viewFrame(Patrolling patrolling) throws ParseException, IOException, ClassNotFoundException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");

            JLabel formTitle = new JLabel("PATROLLING SCHEDULE");
            JLabel[] jLabelLeft = {new JLabel("Patrol ID"), new JLabel("Security Guard Employee ID"),
                    new JLabel("Day"), new JLabel("Time start (HHmmss)"), new JLabel("Time End (HHmmss)")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }
            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(patrolling.getPatrolID()), new JLabel(patrolling.getEmployeeID()),
                    new JLabel(patrolling.getDay()), new JLabel(patrolling.getStart_Time().format(timeFormatter)),
                    new JLabel(patrolling.getEnd_Time().format(timeFormatter))};
            Building_Executive_Function.Button button = new Building_Executive_Function.Button("Close");
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
        new Building_Executive_Patrolling_Management("AD01").run("AD01");
    }
}
