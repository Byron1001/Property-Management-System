package Entity.Vendor;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Vendor_Complaint extends JFrame {
    public PanelBorder panelBorderLeft, panelBorderRight, panelBorderIn;
    public UIPackage.Component.Menu menu = new Menu();
    public Header header = new Header();
    public Form_Home formHome = new Form_Home();
    public Table tableData = new Table();
    public Color backgroundColor = Color.WHITE;
    public String vendor_Username = "Vendor Username";
    public JScrollPane scrollPane;
    public GridBagConstraints constraints;
    public JPanel panel;
    public Vendor.Button addButton, cancelButton, viewButton, updateButton;

    public Vendor_Complaint(String vendor_Username) throws IOException, ClassNotFoundException {
        this.vendor_Username = vendor_Username;
        menu.initMoving(Entity.Vendor.Vendor_Complaint.this);

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

        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), vendor_Username, TitledBorder.LEFT, TitledBorder.TOP);
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

        addButton = new Vendor.Button("Log Complaint");
        cancelButton = new Vendor.Button("Cancel Complaint");
        viewButton = new Vendor.Button("View Complaint");
        updateButton = new Vendor.Button("Update Complaint");

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

        Vendor Vendor = new Vendor();
        ArrayList<Complaint> complaintArrayList = Vendor.view_Complaint(vendor_Username);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Entity.Vendor.Vendor_Complaint.addFrame(vendor_Username).setVisible(true);
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
                        Complaint complaintSelected = complaintArrayList.get(row);
                        new Entity.Vendor.Vendor_Complaint.updateFrame(complaintSelected).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Please choose complaint", "Choice error", JOptionPane.INFORMATION_MESSAGE);
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
                    Complaint complaintSelected = complaintArrayList.get(row);
                    int result = JOptionPane.showConfirmDialog(null, "Do you sure to cancel this complaint?", "Get Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_OPTION) {
                        try {
                            new Vendor().cancel_Complaint(complaintSelected.getComplaintID());
                            JOptionPane.showMessageDialog(null, "Complaint Cancelled", "Complaint cancellation", JOptionPane.INFORMATION_MESSAGE);
                            new Entity.Vendor.Vendor_Complaint(vendor_Username).run(vendor_Username);
                            dispose();
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
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
                    new Entity.Vendor.Vendor_Complaint.viewFrame(complaintSelected).setVisible(true);
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

    public void run(String vendor_Username) throws IOException, ClassNotFoundException {
        final String[] column = {"Complaint ID", "Vendor Username", "Description", "Status"};
        Entity.Vendor.Vendor_Complaint frame = new Entity.Vendor.Vendor_Complaint(vendor_Username);
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("payment", "Invoice payment", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("paymentHistory", "Payment History", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("statement", "Statement", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("complaint", "Complaint", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#EB5757");
        frame.menu.colorLeft = Color.decode("#000000");

        frame.menu.initMoving(frame);
        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }
        frame.tableData.preferredColumnWidth = 230;

        Vendor Vendor = new Vendor();
        ArrayList<Complaint> complaintArrayList = Vendor.view_Complaint(vendor_Username);

        for (Complaint complaint : complaintArrayList) {
            frame.tableData.addRow(complaint.getStringArray(complaint));
        }

        frame.formHome.removeAll();
        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException, ClassNotFoundException {
                if (index == 0) {
                    Vendor_Interface vendorInterface = new Vendor_Interface(vendor_Username);
                    vendorInterface.frame.setVisible(true);
                    frame.dispose();
                } else if (index == 1) {
                    new Entity.Vendor.Vendor_Payment_Frame(vendor_Username).run(vendor_Username);
                    frame.dispose();
                } else if (index == 2) {
                    new Entity.Vendor.Vendor_Payment_History(vendor_Username).run(vendor_Username);
                    frame.dispose();
                } else if (index == 3) {
                    new Entity.Vendor.Vendor_Statement_Frame(vendor_Username).run(vendor_Username);
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
        public addFrame(String vendor_Username) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("Complaint logging Form");
            JLabel[] jLabelLeft = {new JLabel("Complaint ID"), new JLabel("Vendor Username"),
                    new JLabel("Description")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }
            JTextField complaintIDField = new JTextField(new Complaint().get_Auto_ComplaintID());
            complaintIDField.setEditable(false);
            JTextField VendorUsernameField = new JTextField(vendor_Username);
            VendorUsernameField.setEditable(false);
            TextArea descriptionArea = new TextArea();
            descriptionArea.setBounds(0, 0, getWidth(), getHeight());

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Vendor.Button logButton = new Vendor.Button("Log Complaint");
            Vendor.Button cancelButton = new Vendor.Button("Cancel Logging");

            logButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);
            panel2.add(jLabelLeft[0]);
            panel2.add(complaintIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(VendorUsernameField);
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
                    Complaint complaint = new Complaint(complaintIDField.getText(), VendorUsernameField.getText(), descriptionArea.getText(), "unsolved");
                    try {
                        boolean check = new Vendor().check_Vendor_Availability(complaint.getResident_Username());
                        if (!check) {
                            JOptionPane.showMessageDialog(null, "Vendor username not found", "Vendor Username not found", JOptionPane.ERROR_MESSAGE);
                        } else if (descriptionArea.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Description cannot be blank", "Description Blank", JOptionPane.ERROR_MESSAGE);
                        } else {
                            new Vendor().log_Complaint(complaint);
                            new Entity.Vendor.Vendor_Complaint(vendor_Username).run(vendor_Username);
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
                        new Vendor_Complaint(vendor_Username).run(vendor_Username);
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
            JLabel[] jLabelLeft = {new JLabel("Complaint ID"), new JLabel("Vendor Username"),
                    new JLabel("Description")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }
            JTextField complaintIDField = new JTextField(complaint.getComplaintID());
            complaintIDField.setEditable(false);
            JTextField vendorUsernameField = new JTextField(complaint.getResident_Username());
            vendorUsernameField.setEditable(false);
            TextArea descriptionArea = new TextArea(complaint.getDescription());
            descriptionArea.setBounds(0, 0, getWidth(), getHeight());

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Vendor.Button updateButton = new Vendor.Button("Update Complaint");
            Vendor.Button cancelButton = new Vendor.Button("Cancel Update");

            updateButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);
            panel2.add(jLabelLeft[0]);
            panel2.add(complaintIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(vendorUsernameField);
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
                    Complaint complaint1 = new Complaint(complaintIDField.getText(), vendorUsernameField.getText(), descriptionArea.getText(), "unsolved");
                    try {
                        boolean check = new Vendor().check_Vendor_Availability(complaint1.getResident_Username());
                        if (!check) {
                            JOptionPane.showMessageDialog(null, "Vendor username not found", "Vendor Username not found", JOptionPane.ERROR_MESSAGE);
                        } else {
                            new Vendor().update_Complaint(complaint1, complaint.getComplaintID());
                            new Entity.Vendor.Vendor_Complaint(vendorUsernameField.getText()).run(vendorUsernameField.getText());
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
                        new Vendor_Complaint(vendorUsernameField.getText()).run(vendorUsernameField.getText());
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

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");

            JLabel formTitle = new JLabel("Complaint logging Form");
            JLabel[] jLabelLeft = {new JLabel("Complaint ID"), new JLabel("Vendor Username"),
                    new JLabel("Description")};
            JLabel issuedBy = new JLabel("Issued by Parhill Residence");
            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(complaint.getComplaintID()), new JLabel(complaint.getResident_Username()),
                    new JLabel(complaint.getDescription()), new JLabel(complaint.getStatus())};
            Vendor.Button closeButton = new Vendor.Button("Close");
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
                    dispose();
                }
            });
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException {
        new Entity.Vendor.Vendor_Complaint("VE001").run("VE001");
//        new addFrame("Mike1001");
//        new cancelFrame(new Visitor_Pass().getArrayList().get(0));
    }
}
