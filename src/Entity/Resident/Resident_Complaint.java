package Entity.Resident;

import Entity.Complaint;
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

public class Resident_Complaint extends JFrame {
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

    public Resident_Complaint(String resident_Username) throws IOException {
        this.resident_Username = resident_Username;
        menu.initMoving(Entity.Resident.Resident_Complaint.this);

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

        addButton = new Resident.Button("Log Complaint");
        cancelButton = new Resident.Button("Cancel Complaint");
        viewButton = new Resident.Button("View Complaint");
        updateButton = new Resident.Button("Update Complaint");

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
        ArrayList<Complaint> complaintArrayList =resident.view_Complaint(resident_Username);
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
                        Complaint complaintSelected = complaintArrayList.get(row);
                        new updateFrame(complaintSelected).setVisible(true);
                        dispose();
                    } catch (IOException | ClassNotFoundException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select the complaint", "Choice error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Complaint complaintSelected = complaintArrayList.get(row);
                    new cancelFrame(complaintSelected).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the complaint", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
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
                    new viewFrame(complaintSelected).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the complaint", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
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
        final String[] column = {"Complaint ID", "Resident Username", "Description", "Status"};
        Entity.Resident.Resident_Complaint frame = new Entity.Resident.Resident_Complaint(resident_Username);
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
        frame.tableData.preferredColumnWidth = 220;

        Resident resident = new Resident();
        ArrayList<Complaint> complaintArrayList = resident.view_Complaint(resident_Username);

        for (Complaint complaint : complaintArrayList) {
            frame.tableData.addRow(complaint.getStringArray(complaint));
            System.out.println(complaint.getStatus());
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
                    new Resident_Visitor_Pass(resident_Username).run(resident_Username);
                    frame.dispose();
                } else if (index == 7){
                } else if (index == 8){
                    new Login_Frame();
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }

    private static class addFrame extends JFrame {
        public addFrame(String resident_Username) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("Complaint logging Form");
            JLabel[] jLabelLeft = {new JLabel("Complaint ID"), new JLabel("Resident Username"),
                    new JLabel("Description")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }
            JTextField complaintIDField = new JTextField(new Complaint().get_Auto_ComplaintID());
            complaintIDField.setEditable(false);
            JTextField residentUsernameField = new JTextField(resident_Username);
            residentUsernameField.setEditable(false);
            TextArea descriptionArea = new TextArea();
            descriptionArea.setBounds(0,0,getWidth(),getHeight());

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Resident.Button logButton = new Resident.Button("Log Complaint");
            Resident.Button cancelButton = new Resident.Button("Cancel Logging");

            logButton.setAlignmentX(JButton.CENTER);
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

            panel3.add(logButton);
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

            logButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!descriptionArea.getText().equals("")){
                        Complaint complaint = new Complaint(complaintIDField.getText(), residentUsernameField.getText(), descriptionArea.getText(), "unsolved");
                        try {
                            boolean check = new Resident().check_Resident_Availability(complaint.getResident_Username());
                            if (!check) {
                                JOptionPane.showMessageDialog(null, "Resident username not found", "Resident Username not found", JOptionPane.ERROR_MESSAGE);
                            } else {
                                new Resident().log_Complaint(complaint);
                                new Entity.Resident.Resident_Complaint(resident_Username).run(resident_Username);
                                dispose();
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter complete description", "Description error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Resident_Complaint(resident_Username).run(resident_Username);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private static class updateFrame extends JFrame {
        public updateFrame(Complaint complaint) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("Complaint logging Form");
            JLabel[] jLabelLeft = {new JLabel("Complaint ID"), new JLabel("Resident Username"),
                    new JLabel("Description")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }
            JTextField complaintIDField = new JTextField(new Complaint().get_Auto_ComplaintID());
            complaintIDField.setEditable(false);
            JTextField residentUsernameField = new JTextField(complaint.getResident_Username());
            residentUsernameField.setEditable(false);
            TextArea descriptionArea = new TextArea();
            descriptionArea.setBounds(0,0,getWidth(),getHeight());

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Resident.Button updateButton = new Resident.Button("Log Complaint");
            Resident.Button cancelButton = new Resident.Button("Cancel Logging");

            updateButton.setAlignmentX(JButton.CENTER);
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
                    if (!descriptionArea.getText().equals("")){
                        Complaint complaint1 = new Complaint(complaintIDField.getText(), residentUsernameField.getText(), descriptionArea.getText(), "unsolved");
                        try {
                            boolean check = new Resident().check_Resident_Availability(complaint1.getResident_Username());
                            if (!check) {
                                JOptionPane.showMessageDialog(null, "Resident username not found", "Resident Username not found", JOptionPane.ERROR_MESSAGE);
                            } else {
                                new Resident().update_Complaint(complaint1);
                                new Resident_Complaint(complaint.getResident_Username()).run(complaint.getResident_Username());
                                dispose();
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter complete description", "Description error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Resident_Complaint(complaint.getResident_Username()).run(complaint.getResident_Username());
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private static class cancelFrame extends JFrame {
        public cancelFrame(Complaint complaint) {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(4, 1, 15, 15));

            JLabel frameTitle = new JLabel("Complaint");
            JLabel[] jLabelLeft = {new JLabel("Complaint ID"), new JLabel("Resident Username"),
                    new JLabel("Description")};
            JLabel issuedBy = new JLabel("Issued by Parkhill Residence");
            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(complaint.getComplaintID()), new JLabel(complaint.getResident_Username()),
                                    new JLabel(complaint.getDescription()), new JLabel(complaint.getStatus())};
            Resident.Button cancelVisitorPassButton = new Resident.Button("Cancel Complaint");
            Resident.Button cancelButton = new Resident.Button("Back");
            cancelVisitorPassButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            frameTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            frameTitle.setHorizontalAlignment(JLabel.CENTER);
            issuedBy.setFont(new Font("sansserif", Font.PLAIN, 10));
            issuedBy.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(frameTitle, BorderLayout.NORTH);
            for (int i = 0; i < jLabelLeft.length; i++) {
                jLabelLeft[i].setFont(new Font("sansserif", Font.BOLD, 16));
                panel2.add(jLabelLeft[i]);
                panel2.add(jLabelRight[i]);
            }
            panel1.add(panel2, BorderLayout.CENTER);

            panel3.add(issuedBy);
            panel3.add(cancelVisitorPassButton);
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

            cancelVisitorPassButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog(null, "Do you sure to cancel this complaint?", "Get Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_OPTION) {
                        try {
                            new Resident().cancel_Complaint(complaint.getComplaintID());
                            new Resident_Complaint(complaint.getResident_Username()).run(complaint.getResident_Username());
                            dispose();
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        try {
                            new Resident_Complaint(complaint.getResident_Username()).run(complaint.getResident_Username());
                            dispose();
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
                        new Resident_Complaint(complaint.getResident_Username()).run(complaint.getResident_Username());
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private static class viewFrame extends JFrame {
        public viewFrame(Complaint complaint) {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("Complaint logging Form");
            JLabel[] jLabelLeft = {new JLabel("Complaint ID"), new JLabel("Resident Username"),
                    new JLabel("Description")};
            JLabel issuedBy = new JLabel("Issued by Parkhill Residence");
            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(complaint.getComplaintID()), new JLabel(complaint.getResident_Username()),
                    new JLabel(complaint.getDescription()), new JLabel(complaint.getStatus())};
            Resident.Button closeButton = new Resident.Button("Close");
            closeButton.setAlignmentX(JButton.CENTER);
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
            panel3.add(closeButton);
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

            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Resident_Complaint(complaint.getResident_Username()).run(complaint.getResident_Username());
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException {
        new Entity.Resident.Resident_Complaint("Mike1001").run("Mike1001");
//        new addFrame("Mike1001");
    }
}
