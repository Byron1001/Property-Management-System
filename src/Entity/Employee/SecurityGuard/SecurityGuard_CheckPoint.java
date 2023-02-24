package Entity.Employee.SecurityGuard;

import Entity.CheckPoint;
import Entity.Executive.Building_Executive.Building_Executive_Function;
import Entity.Visitor_Pass;
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

public class SecurityGuard_CheckPoint extends JFrame {
    public PanelBorder panelBorderLeft, panelBorderRight, panelBorderIn;
    public UIPackage.Component.Menu menu = new Menu();
    public Header header = new Header();
    public Form_Home formHome = new Form_Home();
    public Table tableData = new Table();
    public Color backgroundColor = Color.WHITE;
    public String securityGuard_EmployeeID = "SecurityGuard Username";
    public JScrollPane scrollPane;
    public JPanel panel;
    public SecurityGuard.Button viewButton, checkInButton;

    public SecurityGuard_CheckPoint(String securityGuard_EmployeeID) throws IOException, ClassNotFoundException {
        this.securityGuard_EmployeeID = securityGuard_EmployeeID;
        menu.initMoving(Entity.Employee.SecurityGuard.SecurityGuard_CheckPoint.this);

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
        header1.setResizingAllowed(true);
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
        buttonPanel.setLayout(new GridLayout(1, 2, 40, 15));
        buttonPanel.setBackground(Color.white);

        viewButton = new SecurityGuard.Button("View CheckPoint");
        checkInButton = new SecurityGuard.Button("Check-in CheckPoint");

        buttonPanel.add(checkInButton);
        buttonPanel.add(viewButton);
        panelBorderIn.add(buttonPanel, BorderLayout.SOUTH);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);

        ArrayList<CheckPoint> checkPointArrayList = new CheckPoint().getArrayList();

        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                if (row != -1) {
                    CheckPoint checkPointSelected = checkPointArrayList.get(row);
                    try {
                        new SecurityGuard().checkIn_CheckPoint(securityGuard_EmployeeID, checkPointSelected.getCheckPointID());
                        JOptionPane.showMessageDialog(null, "Check-in successful", "Check-in successful", JOptionPane.INFORMATION_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/success.png"), 80, 80));
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the CheckPoint", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    CheckPoint checkPointSelected = checkPointArrayList.get(row);
                    try {
                        new viewFrame(checkPointSelected).setVisible(true);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the CheckPoint", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
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
        final String[] column = {"CheckPoint ID", "Name", "Location"};
        Entity.Employee.SecurityGuard.SecurityGuard_CheckPoint frame = new Entity.Employee.SecurityGuard.SecurityGuard_CheckPoint(securityGuard_EmployeeID);
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Visitor Pass Check", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("entry", "Visitor Entry", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("checkpoint", "CheckPoint", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("report", "Incident", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#4286f4");
        frame.menu.colorLeft = Color.decode("#373B44");
        frame.formHome.removeAll();
        frame.tableData.statusAllowed = false;

        frame.menu.initMoving(frame);
        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }
        frame.tableData.preferredColumnWidth = 310;

        ArrayList<CheckPoint> checkPointArrayList = new CheckPoint().getArrayList();

        for (CheckPoint checkPoint : checkPointArrayList) {
            frame.tableData.addRow(checkPoint.getStringArray(checkPoint));
        }
        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException, ClassNotFoundException {
                if (index == 0) {
                    dispose();
                    SecurityGuard_Interface SecurityGuardInterface = new SecurityGuard_Interface(securityGuard_EmployeeID);
                    SecurityGuardInterface.setPanelBorderRight(new SecurityGuard_Interface.SecurityGuard_Profile_Panel(SecurityGuardInterface.getemployeeID()));
                    SecurityGuardInterface.frame.setVisible(true);
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
        public viewFrame(CheckPoint checkPoint) throws ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("CHECKPOINT DETAILS");
            JLabel[] jLabelLeft = {new JLabel("CheckPoint ID"), new JLabel("Name"), new JLabel("Location")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(checkPoint.getCheckPointID()), new JLabel(checkPoint.getName()), new JLabel(checkPoint.getLocation())};
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
        new Entity.Employee.SecurityGuard.SecurityGuard_CheckPoint("SG001").run("SG001");
//        new addFrame("SG001");
//        new cancelFrame(new String[]().getArrayList().get(0));
    }
}
