package Entity.Executive.Building_Executive;

import Entity.Complaint;
import Entity.Executive.Admin_Executive.Admin_Executive_Function;
import Entity.Executive.Admin_Executive.Admin_Executive_Interface;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Building_Executive_Complaint_Management extends JFrame {
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
    public Building_Executive_Function.Button viewButton, solvedButton;

    public Building_Executive_Complaint_Management(String executiveID) throws IOException, ClassNotFoundException {
        this.executiveID = executiveID;
        menu.initMoving(Building_Executive_Complaint_Management.this);

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
        buttonPanel.setLayout(new GridLayout(3, 2, 40, 15));
        buttonPanel.setBackground(Color.white);

        viewButton = new Building_Executive_Function.Button("View Complaint details");
        solvedButton = new Building_Executive_Function.Button("Complaint solved");

        buttonPanel.add(viewButton);
        buttonPanel.add(solvedButton);
        constraints.gridy++;
        panelBorderIn.add(buttonPanel, constraints);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);

        ArrayList<Complaint> complaintArrayList = new Complaint().getArrayList();

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Complaint complaintSelected = complaintArrayList.get(row);
                    try {
                        new Building_Executive_Complaint_Management.viewFrame(complaintSelected).setVisible(true);
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
                if (row != -1) {
                    Complaint complaintSelected = complaintArrayList.get(row);
                    int result = JOptionPane.showConfirmDialog(null, "Change complaint status to solved?", "Confirmation message", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        try {
                            new Building_Executive_Function.Building_Executive().mark_Complaint_Solved(complaintSelected.getComplaintID());
                        } catch (IOException ex) {
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
        Building_Executive_Complaint_Management frame = new Building_Executive_Complaint_Management(executiveID);
        frame.formHome.removeAll();
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("employee", "Employee Task", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("complaint", "Complaint Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("patrolling", "Patrolling Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("checkpoint", "Checkpoint Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("report", "Job Report", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout Booking", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#DECBA4");
        frame.menu.colorLeft = Color.decode("#3E5151");
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
            public void selected(int index) throws FileNotFoundException {
                if (index == 0) {
                    Entity.Executive.Building_Executive.Building_Executive_Interface building_Executive_Interface = new Entity.Executive.Building_Executive.Building_Executive_Interface("BE01");
                    building_Executive_Interface.setPanelBorderRight(new Building_Executive_Interface.Building_Executive_Profile_Panel(building_Executive_Interface.getExecutiveID()));
                    building_Executive_Interface.frame.setVisible(true);
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

    private static class viewFrame extends JFrame {
        public viewFrame(Complaint complaint) throws ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            MaskFormatter yearMask = new MaskFormatter("####");

            JLabel formTitle = new JLabel("COMPLAINT DETAILS");
            JLabel[] jLabelLeft = {new JLabel("Complaint ID"), new JLabel("Resident Username"),
                    new JLabel("Description")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(complaint.getComplaintID()), new JLabel(complaint.getResident_Username()),
                    new JLabel(complaint.getDescription()), new JLabel(complaint.getStatus())};
            Building_Executive_Function.Button closebutton = new Building_Executive_Function.Button("Close");
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
        new Building_Executive_Complaint_Management("BE01").run("BE01");
    }
}