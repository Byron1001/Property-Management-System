package Entity.Executive.Building_Executive;

import Entity.Employee.Employee;
import Entity.Employee.Employee_Task;
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

public class Building_Executive_Employee_Task extends JFrame {
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
    public Building_Executive_Function.Button assignButton, deleteButton, viewButton, modifyButton;

    public Building_Executive_Employee_Task(String executiveID) throws IOException, ClassNotFoundException {
        this.executiveID = executiveID;
        menu.initMoving(Building_Executive_Employee_Task.this);

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

        assignButton = new Building_Executive_Function.Button("Assign New Employee Task");
        modifyButton = new Building_Executive_Function.Button("Modify Employee Task Details");
        deleteButton = new Building_Executive_Function.Button("Delete Employee Task");
        viewButton = new Building_Executive_Function.Button("View Employee Task Details");

        buttonPanel.add(assignButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        constraints.gridy++;
        panelBorderIn.add(buttonPanel, constraints);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);

        ArrayList<Employee_Task> employeeTaskArrayList = new Employee_Task().getArrayList();
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Building_Executive_Employee_Task.addFrame().setVisible(true);
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
                        Employee_Task employee_TaskSelected = employeeTaskArrayList.get(row);
                        new Building_Executive_Employee_Task.modifyFrame(employee_TaskSelected).setVisible(true);
                        dispose();
                    } catch (IOException | ClassNotFoundException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the Employee Task", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Employee_Task employee_TaskSelected = employeeTaskArrayList.get(row);
                    try {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure to delete this Employee Task?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            new Building_Executive_Function.Building_Executive().delete_Employee_Task(employee_TaskSelected.getTaskID());
                            JOptionPane.showMessageDialog(null, "Employee Task deleted", "Employee Task delete success", JOptionPane.INFORMATION_MESSAGE);
                            new Building_Executive_Employee_Task(executiveID).run(executiveID);
                            dispose();
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the Employee Task", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Employee_Task employee_TaskSelected = employeeTaskArrayList.get(row);
                    try {
                        new Building_Executive_Employee_Task.viewFrame(employee_TaskSelected).setVisible(true);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the Employee Task", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
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
        final String[] column = {"Task ID", "Employee ID", "Description", "Status"};
        Building_Executive_Employee_Task frame = new Building_Executive_Employee_Task(executiveID);
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
        frame.tableData.preferredColumnWidth = 210;

        ArrayList<Employee_Task> employeeTaskArrayList = new Employee_Task().getArrayList();

        for (Employee_Task employee_Task : employeeTaskArrayList) {
            frame.tableData.addRow(employee_Task.getStringArray(employee_Task));
        }

        frame.formHome.removeAll();
        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException, ClassNotFoundException {
                if (index == 0) {
                    Entity.Executive.Building_Executive.Building_Executive_Interface building_Executive_Interface = new Entity.Executive.Building_Executive.Building_Executive_Interface(executiveID);
                    building_Executive_Interface.run(1);
                    building_Executive_Interface.frame.setVisible(true);
                    frame.dispose();
                } else if (index == 1) {
                } else if (index == 2) {
                    new Building_Executive_Complaint_Management(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 3) {
                    new Building_Executive_Patrolling_Management(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 4) {
                    new Building_Executive_CheckPoint(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 5) {
                    Entity.Executive.Building_Executive.Building_Executive_Interface building_Executive_Interface = new Entity.Executive.Building_Executive.Building_Executive_Interface(executiveID);
                    building_Executive_Interface.run(1);
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

    private class addFrame extends JFrame {
        public addFrame() throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("Employee Task ADDING");
            JLabel[] jLabelLeft = {new JLabel("Employee Task ID"), new JLabel("Employee ID"), new JLabel("Description")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            JTextField taskIDField = new JTextField(new Employee_Task().get_Auto_TaskID());
            taskIDField.setEditable(false);
            JTextField employeeIDField = new JTextField();
            TextArea descriptionArea = new TextArea();

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Building_Executive_Function.Button assignButton = new Building_Executive_Function.Button("Assign Employee_Task");
            Building_Executive_Function.Button cancelButton = new Building_Executive_Function.Button("Cancel");

            assignButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(taskIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(employeeIDField);
            panel2.add(jLabelLeft[2]);
            panel2.add(descriptionArea);
            panel1.add(panel2, BorderLayout.CENTER);

            panel3.add(assignButton);
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

            assignButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!employeeIDField.getText().equals("") && !descriptionArea.getText().equals("")){
                        Employee_Task employee_Task = new Employee_Task(taskIDField.getText(), employeeIDField.getText(), descriptionArea.getText(), "undone");
                        try {
                            boolean check = new Employee().check_Employee_Availability(employeeIDField.getText());
                            if (!check) {
                                JOptionPane.showMessageDialog(null, "Employee not existed", "Employee not found", JOptionPane.ERROR_MESSAGE);
                            } else {
                                new Building_Executive_Function.Building_Executive().add_Employee_Task(employee_Task);
                                JOptionPane.showMessageDialog(null, "Employee_Task registration successful.Please ask Employee_Task to sign up and remember to register new unit if needed.", "Employee_Task adding successful", JOptionPane.INFORMATION_MESSAGE);
                                new Building_Executive_Employee_Task(executiveID).run(executiveID);
                                dispose();
                            }
                        } catch (IOException | ClassNotFoundException ex) {
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
                        new Building_Executive_Employee_Task(executiveID).run(executiveID);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private class modifyFrame extends JFrame {
        public modifyFrame(Employee_Task employee_Task) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("Employee Task Details MODIFYING");
            JLabel[] jLabelLeft = {new JLabel("Employee Task ID"), new JLabel("Employee ID"),
                    new JLabel("Description"), new JLabel("Status")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            JTextField taskIDField = new JTextField(employee_Task.getTaskID());
            taskIDField.setEditable(false);
            JTextField employeeIDField = new JTextField();
            employeeIDField.setText(employee_Task.getEmployeeID());
            TextArea descriptionArea = new TextArea();
            descriptionArea.setText(employee_Task.getDescription());
            JComboBox<String> statusComboBox = new JComboBox<>();
            statusComboBox.addItem("done");
            statusComboBox.addItem("undone");
            for (int i = 0;i < 2;i++){
                if (employee_Task.getStatus().equals(statusComboBox.getItemAt(i)))
                    statusComboBox.setSelectedIndex(i);
            }

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Building_Executive_Function.Button modifyButton = new Building_Executive_Function.Button("Modify Employee Task Details");
            Building_Executive_Function.Button cancelButton = new Building_Executive_Function.Button("Cancel");

            modifyButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(taskIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(employeeIDField);
            panel2.add(jLabelLeft[2]);
            panel2.add(descriptionArea);
            panel2.add(jLabelLeft[3]);
            panel2.add(statusComboBox);
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
                    if (!employeeIDField.getText().equals("") && !descriptionArea.getText().equals("")){
                        Employee_Task employee_TaskModify = new Employee_Task(taskIDField.getText(), employeeIDField.getText(), descriptionArea.getText(), Objects.requireNonNull(statusComboBox.getSelectedItem()).toString());
                        try {
                            boolean check = new Employee().check_Employee_Availability(employeeIDField.getText());
                            if (!check) {
                                JOptionPane.showMessageDialog(null, "Employee ID not existed", "Employee ID not found", JOptionPane.ERROR_MESSAGE);
                            } else {
                                new Building_Executive_Function.Building_Executive().update_Employee_Task(employee_TaskModify, employee_Task.getTaskID());
                                JOptionPane.showMessageDialog(null, "Employee Task modification successful.Please ask employee to finish the task.", "Employee_Task modification successful", JOptionPane.INFORMATION_MESSAGE);
                                new Building_Executive_Employee_Task(executiveID).run(executiveID);
                                dispose();
                            }
                        } catch (IOException | ClassNotFoundException ex) {
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
                        new Building_Executive_Employee_Task(executiveID).run(executiveID);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private static class viewFrame extends JFrame {
        public viewFrame(Employee_Task employee_Task) throws ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("Employee Task DETAILS");
            JLabel[] jLabelLeft = {new JLabel("Employee Task ID"), new JLabel("Employee ID"), new JLabel("Description")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(employee_Task.getTaskID()), new JLabel(employee_Task.getEmployeeID()),
                    new JLabel(employee_Task.getDescription()), new JLabel(employee_Task.getStatus())};
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
        new Building_Executive_Employee_Task("BE01").run("BE01");
    }
}
