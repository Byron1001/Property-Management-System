package Entity.Employee.SecurityGuard;

import Entity.Vendor.Vendor;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class SecurityGuard_Incident extends JFrame {
    public PanelBorder panelBorderLeft, panelBorderRight, panelBorderIn;
    public UIPackage.Component.Menu menu = new Menu();
    public Header header = new Header();
    public Form_Home formHome = new Form_Home();
    public Table tableData = new Table();
    public Color backgroundColor = Color.WHITE;
    public String securityGuard_EmployeeID = "Employee ID";
    public JScrollPane scrollPane;
    public JPanel panel;
    SecurityGuard.Button addButton, viewButton, updateButton;

    public SecurityGuard_Incident(String securityGuard_EmployeeID) throws IOException, ClassNotFoundException {
        this.securityGuard_EmployeeID = securityGuard_EmployeeID;
        menu.initMoving(this);

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
        panelBorderIn.setLayout(new BorderLayout());

        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), securityGuard_EmployeeID, TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(new Font("sanserif", Font.BOLD, 18));
        border.setTitleColor(Color.lightGray);
        panelBorderIn.setBorder(border);

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

        panelBorderIn.add(header1, BorderLayout.NORTH);
        panelBorderIn.add(scrollPane, BorderLayout.CENTER);

        panelBorderRight.add(panelBorderIn, BorderLayout.CENTER);
        panelBorderRight.add(formHome, BorderLayout.PAGE_END);

        add(panelBorderLeft, BorderLayout.LINE_START);
        add(panelBorderRight, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 40, 15));
        buttonPanel.setBackground(Color.white);

        addButton = new SecurityGuard.Button("Record Incident");
        updateButton = new SecurityGuard.Button("Update Incident");
        viewButton = new SecurityGuard.Button("Check Incident");

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(viewButton);
        panelBorderIn.add(buttonPanel, BorderLayout.SOUTH);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);

        ArrayList<SecurityGuard.Incident> incidentArrayList = new SecurityGuard.Incident().get_all_Incident_Report();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new addFrame(securityGuard_EmployeeID).setVisible(true);
                } catch (IOException | ClassNotFoundException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                if (row != -1) {
                    try {
                        SecurityGuard.Incident incidentSelected = incidentArrayList.get(row);
                        new modifyFrame(incidentSelected).setVisible(true);
                    } catch (IOException | ClassNotFoundException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the Incident", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    SecurityGuard.Incident incidentSelected = incidentArrayList.get(row);
                    try {
                        new viewFrame(incidentSelected).setVisible(true);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the Incident", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
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

    public void run(String securityGuard_EmployeeID) throws IOException, ClassNotFoundException {
        final String[] column = {"Incident ID", "Date", "Time", "SecurityGuard ID", "Description"};
        SecurityGuard_Incident frame = new SecurityGuard_Incident(securityGuard_EmployeeID);
        frame.formHome.removeAll();
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Visitor Pass Check", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("entry", "Visitor Entry", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("checkpoint", "CheckPoint", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("report", "Incident", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#4286f4");
        frame.menu.colorLeft = Color.decode("#373B44");
        frame.menu.initMoving(frame);
        frame.tableData.statusAllowed = false;
        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }
        frame.tableData.preferredColumnWidth = 185;

        ArrayList<SecurityGuard.Incident> incidentArrayList = new SecurityGuard.Incident().get_all_Incident_Report();

        for (SecurityGuard.Incident incident : incidentArrayList) {
            frame.tableData.addRow(incident.getStringArray(incident));
        }

        frame.formHome.removeAll();
        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException, ClassNotFoundException {
                if (index == 0) {
                    Entity.Employee.SecurityGuard.SecurityGuard_Interface SecurityGuardInterface = new Entity.Employee.SecurityGuard.SecurityGuard_Interface("SG001");
                    SecurityGuardInterface.setPanelBorderRight(new SecurityGuard_Interface.SecurityGuard_Profile_Panel(SecurityGuardInterface.getemployeeID()));
                    SecurityGuardInterface.frame.setVisible(true);
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
        private final ArrayList<SecurityGuard.Incident> incidentArrayList = new SecurityGuard.Incident().get_all_Incident_Report();

        public addFrame(String securityGuardEmployeeID) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("INCIDENT REPORT");
            JLabel[] jLabelLeft = {new JLabel("Incident ID"), new JLabel("Date (MM.dd.yyyy)"),
                    new JLabel("Time (HHmmss)"), new JLabel("Security Guard ID"), new JLabel("Description")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM.dd.yyyy:HHmmss");
            MaskFormatter dateMask = new MaskFormatter("##.##.####");
            MaskFormatter timeMask = new MaskFormatter("######");
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            JTextField incidentIDField = new JTextField(new SecurityGuard.Incident().get_Auto_IncidentID());
            incidentIDField.setEditable(false);
            JFormattedTextField dateField = new JFormattedTextField(dateMask);
            dateField.setText(date.format(dateFormatter));
            JFormattedTextField timeField = new JFormattedTextField(timeMask);
            timeField.setText(time.format(timeFormatter));
            JTextField securityGuardEmployeeIDField = new JTextField(securityGuardEmployeeID);
            securityGuardEmployeeIDField.setEditable(false);
            TextArea descriptionArea = new TextArea();

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            SecurityGuard.Button addButton = new SecurityGuard.Button("Report New Incident");
            SecurityGuard.Button cancelButton = new SecurityGuard.Button("Cancel");

            addButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(incidentIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(dateField);
            panel2.add(jLabelLeft[2]);
            panel2.add(timeField);
            panel2.add(jLabelLeft[3]);
            panel2.add(securityGuardEmployeeIDField);
            panel2.add(jLabelLeft[4]);
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
                    String dateTime = dateField.getText() + ":" + timeField.getText();
                    SecurityGuard.Incident newIncident = new SecurityGuard.Incident(incidentIDField.getText(), LocalDateTime.parse(dateTime, dateTimeFormatter), securityGuardEmployeeIDField.getText(), descriptionArea.getText());
                    try {
                        new SecurityGuard.Incident().add_Incident_Record(newIncident);
                        JOptionPane.showMessageDialog(null, "Incident adding successful", "Incident adding successful", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
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
        private final ArrayList<SecurityGuard.Incident> incidentArrayList = new SecurityGuard.Incident().get_all_Incident_Report();

        public modifyFrame(SecurityGuard.Incident incident) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("INCIDENT REPORT");
            JLabel[] jLabelLeft = {new JLabel("Incident ID"), new JLabel("Date (MM.dd.yyyy)"),
                    new JLabel("Time (HHmmss)"), new JLabel("Security Guard ID"), new JLabel("Description")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM.dd.yyyy:HHmmss");
            MaskFormatter dateMask = new MaskFormatter("##.##.####");
            MaskFormatter timeMask = new MaskFormatter("######");
            String[] dateTime = incident.getDateTime().format(dateTimeFormatter).split(":", 2);

            JTextField incidentIDField = new JTextField(incident.getIncident_ID());
            incidentIDField.setEditable(false);
            JFormattedTextField dateField = new JFormattedTextField(dateMask);
            dateField.setText(dateTime[0]);
            JFormattedTextField timeField = new JFormattedTextField(timeMask);
            timeField.setText(dateTime[1]);
            JTextField securityGuardEmployeeIDField = new JTextField(incident.getSecurity_Guard_ID());
            securityGuardEmployeeIDField.setEditable(false);
            TextArea descriptionArea = new TextArea();
            descriptionArea.setText(incident.getDescription());

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            SecurityGuard.Button modifyButton = new SecurityGuard.Button("Modify Incident");
            SecurityGuard.Button cancelButton = new SecurityGuard.Button("Cancel");

            modifyButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);

            panel2.add(jLabelLeft[0]);
            panel2.add(incidentIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(dateField);
            panel2.add(jLabelLeft[2]);
            panel2.add(timeField);
            panel2.add(jLabelLeft[3]);
            panel2.add(securityGuardEmployeeIDField);
            panel2.add(jLabelLeft[4]);
            panel2.add(descriptionArea);
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
                    String dateTime = dateField.getText() + ":" + timeField.getText();
                    SecurityGuard.Incident newIncident = new SecurityGuard.Incident(incidentIDField.getText(), LocalDateTime.parse(dateTime, dateTimeFormatter), securityGuardEmployeeIDField.getText(), descriptionArea.getText());
                    try {
                        new SecurityGuard.Incident().update_Incident_Report(newIncident, incident.getIncident_ID());
                        JOptionPane.showMessageDialog(null, "Incident adding successful", "Incident adding successful", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
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
        public viewFrame(SecurityGuard.Incident incident) throws ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("INCIDENT REPORT");
            JLabel[] jLabelLeft = {new JLabel("Incident ID"), new JLabel("Date (MM.dd.yyyy)"),
                    new JLabel("Time (HHmmss)"), new JLabel("Security Guard ID"), new JLabel("Description")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM.dd.yyyy:HHmmss");
            String[] dateTime = incident.getDateTime().format(dateTimeFormatter).split(":", 2);
            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(incident.getIncident_ID()), new JLabel(dateTime[0]),
                                    new JLabel(dateTime[1]), new JLabel(incident.getSecurity_Guard_ID()),
                                    new JLabel(incident.getDescription())};
            SecurityGuard.Button closeButton = new SecurityGuard.Button("Close");
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
        new SecurityGuard_Incident("SG001").run("SG001");
    }
}
