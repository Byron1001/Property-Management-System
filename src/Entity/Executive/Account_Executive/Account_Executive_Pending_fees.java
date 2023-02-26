package Entity.Executive.Account_Executive;

import Entity.Financial.Payment;
import Entity.Login.Login_Frame;
import Entity.Resident.Resident;
import Entity.Vendor.Vendor;
import UIPackage.Component.Header;
import UIPackage.Component.Menu;
import UIPackage.Event.EventMenuSelected;
import UIPackage.Form.Form_Home;
import UIPackage.Model.Model_Card;
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

public class Account_Executive_Pending_fees extends JFrame {
    public PanelBorder panelBorderLeft, panelBorderRight, panelBorderIn;
    public Menu menu = new Menu();
    public Header header = new Header();
    public Form_Home formHome = new Form_Home();
    public Table tableData = new Table();
    public Color backgroundColor = Color.WHITE;
    public String executiveID = "Executive ID";
    public JScrollPane scrollPane;
    public GridBagConstraints constraints;
    public JPanel panel;
    public Account_Executive_Function.AcExButton residentPendingButton;
    public Account_Executive_Function.AcExButton depositButton;

    public Account_Executive_Pending_fees(String executiveID) throws FileNotFoundException {
        this.executiveID = executiveID;
        menu.initMoving(Entity.Executive.Account_Executive.Account_Executive_Pending_fees.this);

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
        buttonPanel.setLayout(new GridLayout(1, 2, 40, 15));
        buttonPanel.setBackground(Color.white);

        residentPendingButton = new Account_Executive_Function.AcExButton("View Pending fees");
        depositButton = new Account_Executive_Function.AcExButton("View Deposit");

        buttonPanel.add(residentPendingButton);
        buttonPanel.add(depositButton);
        constraints.gridy++;
        panelBorderIn.add(buttonPanel, constraints);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);

        ArrayList<Resident> residentArrayList = new Resident().getArrayList();
        ArrayList<Vendor> vendorArrayList = new Vendor().getArrayList();
        ArrayList<Payment.Deposit> depositArrayList = new Payment.Deposit().getArrayList();
        residentPendingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    String id = tableData.getModel().getValueAt(row, 0).toString();
                    for (Resident resident : residentArrayList){
                        if (id.equals(resident.getResident_Username()))
                            new PendingFrame(resident.getStringArray(resident));
                    }
                    for (Vendor vendor : vendorArrayList){
                        if (id.equals(vendor.getVendor_Username()))
                            new PendingFrame(vendor.getPendingStringArray(vendor));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the invoice", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    String id = tableData.getModel().getValueAt(row, 0).toString();
                    boolean check = false;
                    for (Payment.Deposit deposit : depositArrayList){
                        if (id.equals(deposit.getUsername())){
                            try {
                                check = true;
                                new DepositFrame(id, deposit.getAmount()).setVisible(true);
                            } catch (FileNotFoundException | ParseException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                    if (!check)
                        JOptionPane.showMessageDialog(null, "The user you have selected has no deposit with management.", "No receive user deposit", JOptionPane.INFORMATION_MESSAGE);
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

    public void run(String executiveID) throws FileNotFoundException {
        final String[] column = {"Username", "Name", "Gender", "Contact Number", "Unit ID", "Payment pending"};
        Entity.Executive.Account_Executive.Account_Executive_Pending_fees frame = new Entity.Executive.Account_Executive.Account_Executive_Pending_fees(executiveID);
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("payment", "Invoice", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("deposit", "Payment", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("paymentHistory", "Receipt", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("statement", "Statement", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Outstanding fees", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout Booking", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#ad5389");
        frame.menu.colorLeft = Color.decode("#3c1053");
        frame.menu.initMoving(frame);
        frame.tableData.statusAllowed = false;

        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }

        frame.tableData.preferredColumnWidth = 150;

        int pendingVendor = 0;
        int pendingResident = 0;
        ArrayList<Resident> residentArrayList = new Resident().getArrayList();
        ArrayList<Vendor> vendorArrayList = new Vendor().getArrayList();
        for (Resident resident : residentArrayList) {
            frame.tableData.addRow(resident.getStringArray(resident));
            pendingResident += resident.getPayment();
        }
        for (Vendor vendor : vendorArrayList){
            frame.tableData.addRow(vendor.getPendingStringArray(vendor));
            pendingVendor += vendor.getUnpaid_payment();
        }
        String number = "RM " + (pendingResident + pendingVendor);
        frame.formHome.card1.setData(new Model_Card("payment", "Total Pending", number, "To be paid"));
        frame.formHome.card2.setData(new Model_Card("payment", "Vendor Pending", "RM " + pendingVendor, "To be paid"));
        frame.formHome.card3.setData(new Model_Card("payment", "Resident Pending", "RM " + pendingResident, "To be paid"));

        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException {
                if (index == 0) {
                    Account_Executive_Interface accountExecutiveInterface = new Account_Executive_Interface(executiveID);
                    accountExecutiveInterface.frame.setVisible(true);
                    frame.dispose();
                } else if (index == 1) {
                    new Account_Executive_Invoice(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 2) {
                    new Account_Executive_Payment(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 3) {
                    new Entity.Executive.Account_Executive.Account_Executive_Receipt(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 4) {
                    new Account_Executive_Statement(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 5) {
                } else if (index == 6){
                    new Login_Frame();
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }

    public static class PendingFrame extends JFrame {
        public PendingFrame(String[] data) {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel2.setLayout(new GridLayout(8, 2, 15, 15));
            panel3.setLayout(new GridLayout(3, 1, 15, 15));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            JLabel invoiceTitle = new JLabel("PENDING DETAILS");
            JLabel issuedBy = new JLabel("Issued by Parhill Residence");

            JLabel[] jLabelLeft = {new JLabel("Username"), new JLabel("Name"),
                    new JLabel("Gender"), new JLabel("Contact Number"),
                    new JLabel("Unit ID"), new JLabel("Payment Pending")};
            JLabel[] jLabelRight = {new JLabel(data[0]), new JLabel(data[1]),
                    new JLabel(data[2]), new JLabel(data[3]),
                    new JLabel(data[4]), new JLabel("RM " + data[5])};
            Account_Executive_Function.AcExButton button = new Account_Executive_Function.AcExButton("Close");
            button.setAlignmentX(JButton.CENTER);
            invoiceTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            invoiceTitle.setHorizontalAlignment(JLabel.CENTER);
            issuedBy.setFont(new Font("sansserif", Font.PLAIN, 10));
            issuedBy.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(invoiceTitle, BorderLayout.NORTH);
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

    public static class DepositFrame extends JFrame {
        public DepositFrame(String username, int amount) throws FileNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel2.setLayout(new GridLayout(8, 2, 15, 15));
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel depositTitle = new JLabel("DEPOSIT");
            JLabel[] jLabelLeft = {new JLabel("Username"), new JLabel("Name"),
                    new JLabel("Gender"), new JLabel("Contact Number"),
                    new JLabel("Unit ID"), new JLabel("Deposit Amount")};

            ArrayList<Resident> residentArrayList = new Resident().getArrayList();
            ArrayList<Vendor> vendorArrayList = new Vendor().getArrayList();
            String[] data = new String[]{};
            for (Resident resident : residentArrayList){
                if (username.equals(resident.getResident_Username()))
                    data = resident.getStringArray(resident);
            }
            for (Vendor vendor : vendorArrayList){
                if (username.equals(vendor.getVendor_Username()))
                    data = vendor.getPendingStringArray(vendor);
            }
            JLabel[] jLabelRight = {new JLabel(data[0]), new JLabel(data[1]),
                    new JLabel(data[2]), new JLabel(data[3]),
                    new JLabel(data[4]), new JLabel("RM " + amount)};
            Account_Executive_Function.AcExButton closeButton = new Account_Executive_Function.AcExButton("Close");
            closeButton.setAlignmentX(JButton.CENTER);
            depositTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            depositTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(depositTitle, BorderLayout.NORTH);
            for (int i = 0; i < jLabelLeft.length; i++) {
                jLabelLeft[i].setFont(new Font("sansserif", Font.BOLD, 16));
                panel2.add(jLabelLeft[i]);
                panel2.add(jLabelRight[i]);
            }
            panel1.add(panel2, BorderLayout.CENTER);

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

    public static void main(String[] args) throws FileNotFoundException {
        new Entity.Executive.Account_Executive.Account_Executive_Pending_fees("AC01").run("AC01");
    }
}
