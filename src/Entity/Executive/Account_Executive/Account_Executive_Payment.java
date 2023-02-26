package Entity.Executive.Account_Executive;

import Entity.Financial.Payment;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Account_Executive_Payment extends JFrame{
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
    public Account_Executive_Function.AcExButton paymentButton;
    public Account_Executive_Function.AcExButton confirmButton;

    public Account_Executive_Payment(String executiveID) throws FileNotFoundException {
        this.executiveID = executiveID;
        menu.initMoving(Account_Executive_Payment.this);

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

        paymentButton = new Account_Executive_Function.AcExButton("View Payment");
        confirmButton = new Account_Executive_Function.AcExButton("Confirm payment");

        buttonPanel.add(paymentButton);
        buttonPanel.add(confirmButton);
        constraints.gridy++;
        panelBorderIn.add(buttonPanel, constraints);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);

        ArrayList<Payment> paymentArrayList = new Payment().getArrayList();
        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Payment payment = paymentArrayList.get(row);
                    new PaymentFrame(payment).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the payment details", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                Payment payment = paymentArrayList.get(row);
                if (!payment.getIssuerID().equals("")){
                    JOptionPane.showMessageDialog(null, "This payment has already been confirmed", "Payment confirmed", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (row != -1){
                        int result = JOptionPane.showConfirmDialog(null, "Confirm payment received?", "Payment confirmation", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            Account_Executive_Function.Account_Executive accountExecutive = new Account_Executive_Function.Account_Executive();
                            try {
                                accountExecutive.issue_Receipt(payment.getPaymentID(), executiveID);
                                new Account_Executive_Payment(executiveID).run(executiveID);
                                dispose();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please choose the payment details", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                    }
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
        final String[] column = {"Payment ID", "Invoice ID", "Pay Username", "Unit ID", "Amount", "Payment date", "Payment Types", "Issuer ID", "Issued date"};
        Account_Executive_Payment frame = new Account_Executive_Payment(executiveID);
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

        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }
        frame.tableData.model.addColumn("Status");
        frame.tableData.preferredColumnWidth = 90;

        ArrayList<Payment> paymentArrayList = new Payment().getArrayList();
        for (Payment payment : paymentArrayList) {
            frame.tableData.addRow(payment.getStringArrayAddStatus(payment));
        }
        frame.formHome.removeAll();
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
                } else if (index == 3) {
                    new Entity.Executive.Account_Executive.Account_Executive_Receipt(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 4) {
                    new Account_Executive_Statement(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 5) {
                    new Entity.Executive.Account_Executive.Account_Executive_Pending_fees(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 6){
                    new Login_Frame();
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }

    public static class PaymentFrame extends JFrame {
        public PaymentFrame(Payment payment) {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            JLabel receiptTitle = new JLabel("Payment");
            JLabel issuedBy = new JLabel("Issued by Parhill Residence");
            JLabel[] jLabelLeft = {new JLabel("Payment ID"), new JLabel("Invoice ID"),
                    new JLabel("Pay Username"), new JLabel("Unit ID"),
                    new JLabel("Amount"), new JLabel("Payment date"),
                    new JLabel("Payment Types"), new JLabel("Issuer ID"),
                    new JLabel("Issued date"), new JLabel("Description"),
                    new JLabel("Status")};
            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            String statusText = "Pending";
            if (!payment.getIssuerID().equals(""))
                statusText = "Received";
            JLabel[] jLabelRight = {new JLabel(payment.getPaymentID()), new JLabel(payment.getInvoiceID()),
                    new JLabel(payment.getPay_Username()), new JLabel(payment.getUnitID()),
                    new JLabel("RM " + payment.getAmount()), new JLabel(payment.getPayment_Date().format(formatter)),
                    new JLabel(payment.getPaymentTypes()), new JLabel(payment.getIssuerID()),
                    new JLabel(payment.getIssuedDate().format(formatter)), new JLabel(payment.getDescription()),
                    new JLabel(statusText)};
            Account_Executive_Function.AcExButton button = new Account_Executive_Function.AcExButton("Close");
            button.setAlignmentX(JButton.CENTER);
            receiptTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            receiptTitle.setHorizontalAlignment(JLabel.CENTER);
            issuedBy.setFont(new Font("sansserif", Font.PLAIN, 10));
            issuedBy.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(receiptTitle, BorderLayout.NORTH);
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

    public static void main(String[] args) throws FileNotFoundException {
        new Account_Executive_Payment("AC01").run("AC01");
//        new ReceiptFrame(new Invoice().getArrayList().get(0));
    }
}
