package Entity.Executive.Admin_Executive;

import Entity.Employee.Employee;
import Entity.Facility;
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

public class Admin_Executive_Facility_Management extends JFrame {
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

    public Admin_Executive_Facility_Management(String executiveID) throws IOException, ClassNotFoundException {
        this.executiveID = executiveID;
        menu.initMoving(Entity.Executive.Admin_Executive.Admin_Executive_Facility_Management.this);

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

        addButton = new Admin_Executive_Function.Button("Add New Facility");
        modifyButton = new Admin_Executive_Function.Button("Modify Facility Details");
        deleteButton = new Admin_Executive_Function.Button("Delete Facility");
        viewButton = new Admin_Executive_Function.Button("View Facility Details");

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

        ArrayList<Facility> facilityArrayList = new Facility().getArrayList();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Entity.Executive.Admin_Executive.Admin_Executive_Facility_Management.addFrame().setVisible(true);
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
                        Facility facilitySelected = facilityArrayList.get(row);
                        new Entity.Executive.Admin_Executive.Admin_Executive_Facility_Management.modifyFrame(facilitySelected).setVisible(true);
                    } catch (IOException | ClassNotFoundException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the Facility", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Facility facilitySelected = facilityArrayList.get(row);
                    try {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure to delete this Facility?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            new Admin_Executive_Function.Admin_Executive().delete_Employee(facilitySelected.getFacilityID());
                            JOptionPane.showMessageDialog(null, "Facility deleted", "Facility delete success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the Facility", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Facility facilitySelected = facilityArrayList.get(row);
                    try {
                        new Entity.Executive.Admin_Executive.Admin_Executive_Facility_Management.viewFrame(facilitySelected).setVisible(true);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the Facility", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
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
        final String[] column = {"Facility ID", "Name"};
        Entity.Executive.Admin_Executive.Admin_Executive_Facility_Management frame = new Entity.Executive.Admin_Executive.Admin_Executive_Facility_Management(executiveID);
        frame.formHome.removeAll();
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("house", "Unit Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Employee Management", Model_Menu.MenuType.MENU));
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
        frame.tableData.preferredColumnWidth = 420;

        ArrayList<Facility> facilityArrayList = new Facility().getArrayList();

        for (Facility facility : facilityArrayList) {
            frame.tableData.addRow(facility.getStringArray(facility));
        }

        frame.formHome.removeAll();
        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws FileNotFoundException {
                if (index == 0) {
                    Admin_Executive_Interface adminExecutiveInterface = new Admin_Executive_Interface(executiveID);
                    adminExecutiveInterface.setPanelBorderRight(new Admin_Executive_Interface.Admin_Executive_Profile_Panel(adminExecutiveInterface.getExecutiveID()));
                    adminExecutiveInterface.frame.setVisible(true);
                    dispose();
                } else if (index == 1) {
                } else if (index == 2) {
                    dispose();
                } else if (index == 3) {
                } else if (index == 4) {
                } else if (index == 5) {
                } else if (index == 6) {
                } else if (index == 7) {
                }
            }
        });

        frame.setVisible(true);
    }

    private static class addFrame extends JFrame {
        private final ArrayList<Facility> facilityArrayList = new Facility().getArrayList();

        public addFrame() throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("FACILITY ADDING");
            JLabel[] jLabelLeft = {new JLabel("Facility ID"), new JLabel("Name")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
                label.setHorizontalAlignment(JLabel.RIGHT);
            }

            JTextField facilityIDField = new JTextField();
            JTextField nameField = new JTextField();

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Admin_Executive_Function.Button addButton = new Admin_Executive_Function.Button("Add New Facility");
            Admin_Executive_Function.Button cancelButton = new Admin_Executive_Function.Button("Cancel");

            addButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(facilityIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(nameField);

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
                    Facility facilityNew = new Facility(facilityIDField.getText(), nameField.getText());
                    try {
                        boolean check = facilityNew.check_Facility_Availability(facilityNew.getFacilityID());
                        if (check) {
                            JOptionPane.showMessageDialog(null, "Facility ID already existed", "Facility ID found", JOptionPane.ERROR_MESSAGE);
                        } else {
                            new Admin_Executive_Function.Admin_Executive().add_Facility(facilityNew);
                            JOptionPane.showMessageDialog(null, "Facility registration successful.", "Facility adding successful", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
    }

    private static class modifyFrame extends JFrame {
        private final ArrayList<Facility> facilityArrayList = new Facility().getArrayList();

        public modifyFrame(Facility facility) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("FACILITY MODIFYING");
            JLabel[] jLabelLeft = {new JLabel("Facility ID"), new JLabel("Name")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
                label.setHorizontalAlignment(JLabel.RIGHT);
            }

            JTextField facilityIDField = new JTextField();
            facilityIDField.setText(facility.getFacilityID());
            JTextField nameField = new JTextField();
            nameField.setText(facility.getName());

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Admin_Executive_Function.Button modifyButton = new Admin_Executive_Function.Button("Modify Facility details");
            Admin_Executive_Function.Button cancelButton = new Admin_Executive_Function.Button("Cancel");

            modifyButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(facilityIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(nameField);
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
                    Facility facilityNew = new Facility(facilityIDField.getText(), nameField.getText());
                    try {
                        boolean check = facilityNew.check_Facility_Availability(facilityNew.getFacilityID());
                        if (check) {
                            JOptionPane.showMessageDialog(null, "Facility ID already existed", "Facility ID found", JOptionPane.ERROR_MESSAGE);
                        } else {
                            new Admin_Executive_Function.Admin_Executive().update_Facility(facilityNew, facility.getFacilityID());
                            JOptionPane.showMessageDialog(null, "Facility update successful.", "Facility adding successful", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
    }

    private static class viewFrame extends JFrame {
        public viewFrame(Facility facility) throws ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("FACILITY DETAILS");
            JLabel[] jLabelLeft = {new JLabel("Facility ID"), new JLabel("Name")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
                label.setHorizontalAlignment(JLabel.RIGHT);
            }

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(facility.getFacilityID()), new JLabel(facility.getName())};
            Admin_Executive_Function.Button closeButton = new Admin_Executive_Function.Button("Close");
            closeButton.setAlignmentX(JButton.CENTER);
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
        new Entity.Executive.Admin_Executive.Admin_Executive_Facility_Management("AD01").run("AD01");
    }
}
