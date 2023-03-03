package Entity.Executive.Admin_Executive;

import Entity.Complaint;
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
import java.util.ArrayList;
import java.util.Objects;

public class Admin_Executive_Complaint_Management extends JFrame {

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
    public Admin_Executive_Function.Button addButton, deleteButton, viewButton, modifyButton, solvedButton;

    public Admin_Executive_Complaint_Management(String executiveID) throws IOException, ClassNotFoundException {
        this.executiveID = executiveID;
        menu.initMoving(Entity.Executive.Admin_Executive.Admin_Executive_Complaint_Management.this);

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
        buttonPanel.setLayout(new GridLayout(2, 3, 40, 15));
        buttonPanel.setBackground(Color.white);

        addButton = new Admin_Executive_Function.Button("New Complaint");
        modifyButton = new Admin_Executive_Function.Button("Modify Complaint details");
        deleteButton = new Admin_Executive_Function.Button("Delete Complaint");
        viewButton = new Admin_Executive_Function.Button("View Complaint details");
        solvedButton = new Admin_Executive_Function.Button("Complaint solved");

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(solvedButton);
        constraints.gridy++;
        panelBorderIn.add(buttonPanel, constraints);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);

        ArrayList<Complaint> complaintArrayList = new Complaint().getArrayList();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Entity.Executive.Admin_Executive.Admin_Executive_Complaint_Management.addFrame().setVisible(true);
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
                        Complaint complaintSelected = complaintArrayList.get(row);
                        new Entity.Executive.Admin_Executive.Admin_Executive_Complaint_Management.modifyFrame(complaintSelected).setVisible(true);
                        dispose();
                    } catch (IOException | ClassNotFoundException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the complaint", "Choice error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Complaint complaintSelected = complaintArrayList.get(row);
                    try {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure to delete this complaint?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            new Admin_Executive_Function.Admin_Executive().complaint_Delete(complaintSelected.getResident_Username());
                            JOptionPane.showMessageDialog(null, "Complaint deleted", "Complaint delete success", JOptionPane.INFORMATION_MESSAGE);
                            new Entity.Executive.Admin_Executive.Admin_Executive_Complaint_Management(executiveID).run(executiveID);
                            dispose();
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the complaint details", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Complaint complaintSelected = complaintArrayList.get(row);
                    try {
                        new Entity.Executive.Admin_Executive.Admin_Executive_Complaint_Management.viewFrame(complaintSelected).setVisible(true);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the complaint details", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        solvedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                if (row != -1){
                    Complaint complaintSelected = complaintArrayList.get(row);
                    int result = JOptionPane.showConfirmDialog(null, "Change complaint status to solved?", "Confirmation message", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION){
                        try {
                            new Admin_Executive_Function.Admin_Executive().complaint_Solved(complaintSelected.getComplaintID());
                            new Entity.Executive.Admin_Executive.Admin_Executive_Complaint_Management(executiveID).run(executiveID);
                            dispose();
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose a complaint to proceed", "Choice error", JOptionPane.ERROR_MESSAGE);
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
        final String[] column = {"Complaint ID", "Resident Username", "Description", "Status"};
        Entity.Executive.Admin_Executive.Admin_Executive_Complaint_Management frame = new Entity.Executive.Admin_Executive.Admin_Executive_Complaint_Management(executiveID);
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
        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }

        frame.tableData.preferredColumnWidth = 215;

        ArrayList<Complaint> complaintArrayList = new Complaint().getArrayList();

        for (Complaint complaint : complaintArrayList) {
            frame.tableData.addRow(complaint.getStringArray(complaint));
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
        public addFrame() throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("COMPLAINT ADDING");
            JLabel[] jLabelLeft = {new JLabel("Complaint ID"), new JLabel("Resident Username"),
                    new JLabel("Description")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            MaskFormatter idMask = new MaskFormatter("Com#");
            JFormattedTextField complaintIDField = new JFormattedTextField(idMask);
            complaintIDField.setText(new Complaint().get_Auto_ComplaintID());
            complaintIDField.setEditable(false);
            JTextField residentUsernameField = new JTextField();
            TextArea descriptionArea = new TextArea();
            descriptionArea.setBounds(0,0,getWidth(),getHeight());

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Admin_Executive_Function.Button addButton = new Admin_Executive_Function.Button("New Complaint");
            Admin_Executive_Function.Button cancelButton = new Admin_Executive_Function.Button("Cancel");

            addButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(complaintIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(residentUsernameField);
            panel2.add(jLabelLeft[2]);
            panel2.add(descriptionArea);
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
                    if (!residentUsernameField.getText().equals("") && !descriptionArea.getText().equals("")){
                        Complaint complaintNew = new Complaint(complaintIDField.getText(), residentUsernameField.getText(), descriptionArea.getText(), "unsolved");
                        try {
                            boolean check = new Resident().check_Resident_Availability(residentUsernameField.getText());
                            if (!check) {
                                JOptionPane.showMessageDialog(null, "Resident Username not existed", "Resident Username not found", JOptionPane.ERROR_MESSAGE);
                            } else {
                                new Admin_Executive_Function.Admin_Executive().complaint_Add(complaintNew);
                                JOptionPane.showMessageDialog(null, "Complaint adding successful", "Complaint adding successful", JOptionPane.INFORMATION_MESSAGE);
                                new Entity.Executive.Admin_Executive.Admin_Executive_Complaint_Management(executiveID).run(executiveID);
                                dispose();
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
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
                        new Admin_Executive_Complaint_Management(executiveID).run(executiveID);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private class modifyFrame extends JFrame {
        public modifyFrame(Complaint complaint) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("COMPLAINT MODIFYING");
            JLabel[] jLabelLeft = {new JLabel("Complaint ID"), new JLabel("Resident Username"),
                    new JLabel("Description"), new JLabel("Status")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            MaskFormatter idMask = new MaskFormatter("Com#");
            JFormattedTextField complaintIDField = new JFormattedTextField(idMask);
            complaintIDField.setText(complaint.getComplaintID());
            complaintIDField.setEditable(false);
            JTextField residentUsernameField = new JTextField();
            residentUsernameField.setText(complaint.getResident_Username());
            TextArea descriptionArea = new TextArea();
            descriptionArea.setBounds(0,0,getWidth(),getHeight());
            descriptionArea.setText(complaint.getDescription());
            JComboBox<String> statusComboBox = new JComboBox<>();
            statusComboBox.addItem("solved");
            statusComboBox.addItem("unsolved");

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Admin_Executive_Function.Button modifyButton = new Admin_Executive_Function.Button("Modify Complaint");
            Admin_Executive_Function.Button cancelButton = new Admin_Executive_Function.Button("Cancel");

            modifyButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(complaintIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(residentUsernameField);
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
                    if (!residentUsernameField.getText().equals("") && !descriptionArea.getText().equals("")){
                        Complaint complaintNew = new Complaint(complaintIDField.getText(), residentUsernameField.getText(), descriptionArea.getText(), Objects.requireNonNull(statusComboBox.getSelectedItem()).toString());
                        try {
                            boolean check = new Resident().check_Resident_Availability(residentUsernameField.getText());
                            if (!check) {
                                JOptionPane.showMessageDialog(null, "Resident Username not existed", "Resident Username not found", JOptionPane.ERROR_MESSAGE);
                            } else {
                                new Admin_Executive_Function.Admin_Executive().complaint_Update(complaintNew, complaint.getComplaintID());
                                JOptionPane.showMessageDialog(null, "Complaint modifying successful", "Complaint modifying successful", JOptionPane.INFORMATION_MESSAGE);
                                new Entity.Executive.Admin_Executive.Admin_Executive_Complaint_Management(executiveID).run(executiveID);
                                dispose();
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
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
                        new Admin_Executive_Complaint_Management(executiveID).run(executiveID);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private static class viewFrame extends JFrame {
        public viewFrame(Complaint complaint) throws ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("COMPLAINT DETAILS");
            JLabel[] jLabelLeft = {new JLabel("Complaint ID"), new JLabel("Resident Username"),
                    new JLabel("Description")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(complaint.getComplaintID()), new JLabel(complaint.getResident_Username()),
                    new JLabel(complaint.getDescription()), new JLabel(complaint.getStatus())};
            Admin_Executive_Function.Button closebutton = new Admin_Executive_Function.Button("Close");
            closebutton.setAlignmentX(JButton.CENTER);
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
            panel3.add(closebutton);
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

            closebutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException {
        new Entity.Executive.Admin_Executive.Admin_Executive_Complaint_Management("AD01").run("AD01");
    }
}
