package Entity.Employee.SecurityGuard;

import Entity.Login.Login_Frame;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SecurityGuard_Pass_Check extends JFrame {
    public PanelBorder panelBorderLeft, panelBorderRight, panelBorderIn;
    public UIPackage.Component.Menu menu = new Menu();
    public Header header = new Header();
    public Form_Home formHome = new Form_Home();
    public Table tableData = new Table();
    public Color backgroundColor = Color.WHITE;
    public String SecurityGuard_Username = "SecurityGuard Username";
    public JScrollPane scrollPane;
    public GridBagConstraints constraints;
    public JPanel panel;
    public SecurityGuard.Button viewButton, entryButton;

    public SecurityGuard_Pass_Check(String securityGuard_Username) throws IOException {
        this.SecurityGuard_Username = securityGuard_Username;
        menu.initMoving(Entity.Employee.SecurityGuard.SecurityGuard_Pass_Check.this);

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

        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), SecurityGuard_Username, TitledBorder.LEFT, TitledBorder.TOP);
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

        viewButton = new SecurityGuard.Button("View Visitor Pass");
        entryButton = new SecurityGuard.Button("Add Visitor entry");

        buttonPanel.add(viewButton);

        constraints.gridy++;
        panelBorderIn.add(buttonPanel, constraints);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);
        ArrayList<Visitor_Pass> visitorPassArrayList = new Visitor_Pass().getArrayList();
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Visitor_Pass visitorPass = visitorPassArrayList.get(row);
                    new Entity.Employee.SecurityGuard.SecurityGuard_Pass_Check.viewFrame(visitorPass).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the visitor pass", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        entryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int col = tableData.getSelectedColumn();
                if (row != -1 || col != -1) {
                    Visitor_Pass visitorPass = visitorPassArrayList.get(row);
                    try {
                        new SecurityGuard().add_Visitor_Entry_Record(visitorPass.getVisitor_Pass_ID());
                        JOptionPane.showMessageDialog(null, "Visitor entry record added", "Visitor entry", JOptionPane.INFORMATION_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/success.png"), 80, 80));
                        new Entity.Employee.SecurityGuard.SecurityGuard_Pass_Check(securityGuard_Username).run(securityGuard_Username);
                        dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the visitor pass", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
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

    public void run(String securityGuard_Username) throws IOException, ClassNotFoundException {
        final String[] column = {"Visitor Pass ID", "Visitor Name", "Resident Username", "Unit ID", "Gender", "Contact Number", "Date Start", "Date End", "Status"};
        Entity.Employee.SecurityGuard.SecurityGuard_Pass_Check frame = new Entity.Employee.SecurityGuard.SecurityGuard_Pass_Check(securityGuard_Username);
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Visitor Pass Check", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("entry", "Visitor Entry", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("checkpoint", "CheckPoint", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("report", "Incident", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#4286f4");
        frame.menu.colorLeft = Color.decode("#373B44");

        frame.menu.initMoving(frame);
        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }
        frame.tableData.preferredColumnWidth = 102;

        ArrayList<Visitor_Pass> visitorPassArrayList = new Visitor_Pass().getArrayList();

        for (Visitor_Pass visitorPass : visitorPassArrayList) {
            frame.tableData.addRow(visitorPass.getStringArray(visitorPass));
        }

        frame.formHome.removeAll();
        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException, ClassNotFoundException {
                if (index == 0) {
                    Entity.Employee.SecurityGuard.SecurityGuard_Interface SecurityGuardInterface = new Entity.Employee.SecurityGuard.SecurityGuard_Interface(securityGuard_Username);
                    SecurityGuardInterface.frame.setVisible(true);
                    frame.dispose();
                } else if (index == 1) {
                } else if (index == 2) {
                    new SecurityGuard_Visitor_Entry_Record(securityGuard_Username).run(securityGuard_Username);
                    frame.dispose();
                } else if (index == 3) {
                    new Entity.Employee.SecurityGuard.SecurityGuard_CheckPoint(securityGuard_Username).run(securityGuard_Username);
                    frame.dispose();
                } else if (index == 4) {
                    new SecurityGuard_Incident(securityGuard_Username).run(securityGuard_Username);
                    frame.dispose();
                } else if (index == 5) {
                    new Login_Frame();
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }

    private static class viewFrame extends JFrame {
        public viewFrame(Visitor_Pass visitorPass) {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");

            JLabel formTitle = new JLabel("VISITOR PASS");
            JLabel issuedBy = new JLabel("Issued by Parhill Residence");
            JLabel[] jLabelLeft = {new JLabel("Visitor Pass ID"), new JLabel("Visitor name"),
                    new JLabel("Resident Username"), new JLabel("Unit ID"),
                    new JLabel("Gender"), new JLabel("Contact Number"),
                    new JLabel("Date Start (MM.dd.yyyyy)"), new JLabel("Date End(MM.dd.yyyy)"), new JLabel("Status")};
            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(visitorPass.getVisitor_Pass_ID()), new JLabel(visitorPass.getVisitor_Name()),
                    new JLabel(visitorPass.getResident_Username()), new JLabel(visitorPass.getUnitID()),
                    new JLabel(Character.toString(visitorPass.getGender())), new JLabel(visitorPass.getContact_Number()),
                    new JLabel(visitorPass.getDate_Start().format(formatter)), new JLabel(visitorPass.getDate_End().format(formatter)),
                    new JLabel(visitorPass.getStatus())};
            SecurityGuard.Button button = new SecurityGuard.Button("Close");
            button.setAlignmentX(JButton.CENTER);
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
        new Entity.Employee.SecurityGuard.SecurityGuard_Pass_Check("SG001").run("SG001");
//        new addFrame("SG001");
//        new cancelFrame(new Visitor_Pass().getArrayList().get(0));
    }
}
