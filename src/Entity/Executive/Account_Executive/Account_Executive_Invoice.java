package Entity.Executive.Account_Executive;

import Entity.Financial.Invoice;
import Entity.Financial.Payment;
import Entity.Login.Login_Frame;
import Entity.Resident.Resident;
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
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class Account_Executive_Invoice extends JFrame {
    public PanelBorder panelBorderLeft, panelBorderRight, panelBorderIn;
    public Menu menu = new Menu();
    public Header header = new Header();
    public Form_Home formHome = new Form_Home();
    public Table tableData = new Table();
    public Color backgroundColor = Color.WHITE;
    public String executiveID;
    public JScrollPane scrollPane;
    public GridBagConstraints constraints;
    public JPanel panel;
    public Account_Executive_Function.AcExButton invoiceButton;
    public Account_Executive_Function.AcExButton issueButton;

    public Account_Executive_Invoice(String executiveID) throws FileNotFoundException {
        this.executiveID = executiveID;
        menu.initMoving(Account_Executive_Invoice.this);

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

        invoiceButton = new Account_Executive_Function.AcExButton("View Invoice");
        issueButton = new Account_Executive_Function.AcExButton("Issue Invoice");

        buttonPanel.add(invoiceButton);
        buttonPanel.add(issueButton);
        constraints.gridy++;
        panelBorderIn.add(buttonPanel, constraints);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);

        Account_Executive_Function.Account_Executive accountExecutive = new Account_Executive_Function.Account_Executive();
        accountExecutive = accountExecutive.get_Account_Executive_Info(executiveID);
        ArrayList<Invoice> invoiceArrayList = new Invoice().getArrayList();
        invoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Invoice invoice = invoiceArrayList.get(row);
                    new InvoiceFrame(invoice).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the invoice", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        Account_Executive_Function.Account_Executive finalAccountExecutive = accountExecutive;
        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new IssueFrame(finalAccountExecutive).setVisible(true);
                    dispose();
                } catch (FileNotFoundException | ParseException ex) {
                    throw new RuntimeException(ex);
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
        final String[] column = {"Invoice ID", "Issuer ID", "Unit ID", "Amount", "Due Date", "Pay Types", "Description", "Status"};
        Account_Executive_Invoice frame = new Account_Executive_Invoice(executiveID);
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("payment", "Invoice", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("deposit", "Payment", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("paymentHistory", "Receipt", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("statement", "Statement", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Outstanding fees", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#ad5389");
        frame.menu.colorLeft = Color.decode("#3c1053");
        frame.menu.initMoving(frame);

        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }
        frame.tableData.preferredColumnWidth = 113;
        ArrayList<Invoice> invoiceArrayList = new Invoice().getArrayList();
        for (Invoice invoice : invoiceArrayList) {
            frame.tableData.addRow(invoice.getStringArray(invoice));
        }

        ArrayList<Invoice> unpaidArrayList = new Account_Executive_Function.Account_Executive().get_All_Unpaid_Invoice();
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        String number = decimalFormat.format((float) unpaidArrayList.size() / invoiceArrayList.size() * 100) + "%";
        frame.formHome.card1.setData(new Model_Card("payment", "Unpaid Invoice", number, "To be paid"));
        ArrayList<Payment> pendingPayment = new Account_Executive_Function.Account_Executive().get_All_pending_Payment();
        int total = 0;
        for (Payment pay : pendingPayment) {
            total += pay.getAmount();
        }
        String totalNumber = "RM " + total;

        frame.formHome.card2.setData(new Model_Card("payment", "Pending", totalNumber, "To be confirmed"));

        ArrayList<Payment> receiptList = new Payment().getArrayList();
        int total2 = 0;
        for (Payment pay : receiptList) {
            total2 += pay.getAmount();
        }
        String paid = "RM " + total2;
        frame.formHome.card3.setData(new Model_Card("payment", "Paid", paid, "Payment get"));

        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException {
                if (index == 0) {
                    Account_Executive_Interface accountExecutiveInterface = new Account_Executive_Interface(executiveID);
                    accountExecutiveInterface.frame.setVisible(true);
                    frame.dispose();
                } else if (index == 1) {
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

    public class InvoiceFrame extends JFrame {
        public InvoiceFrame(Invoice invoice) {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel2.setLayout(new GridLayout(8, 2, 15, 15));
            panel3.setLayout(new GridLayout(3, 1, 15, 15));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            JLabel invoiceTitle = new JLabel("INVOICE");
            JLabel issuedBy = new JLabel("Issued by Parkhill Residence");
            JLabel[] jLabelLeft = {new JLabel("Invoice ID"), new JLabel("Issuer ID"),
                    new JLabel("Unit ID"), new JLabel("Amount"),
                    new JLabel("Due Date"), new JLabel("Payment Types"),
                    new JLabel("Description"), new JLabel("Status")};
            JLabel[] jLabelRight = {new JLabel(invoice.getInvoiceID()), new JLabel(invoice.getIssuerID()),
                    new JLabel(invoice.getUnitID()), new JLabel("RM " + invoice.getAmount()),
                    new JLabel(invoice.getDueDate().format(formatter)), new JLabel(invoice.getPaymentTypes()),
                    new JLabel(invoice.getDescription()), new JLabel(invoice.getStatus())};
            Resident.Button button = new Resident.Button("Close");
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

    public class IssueFrame extends JFrame {
        public IssueFrame(Account_Executive_Function.Account_Executive accountExecutive) throws FileNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel2.setLayout(new GridLayout(8, 2, 15, 15));
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            JLabel invoiceTitle = new JLabel("INVOICE");
            JLabel[] jLabelLeft = {new JLabel("Invoice ID"), new JLabel("Issuer ID"),
                    new JLabel("Unit ID"), new JLabel("Amount (RM)"),
                    new JLabel("Due Date (MM.dd.yyyy)"), new JLabel("Payment Types"),
                    new JLabel("Description")};
            Account_Executive_Function.AcExButton issueButton = new Account_Executive_Function.AcExButton("Issue Invoice");
            Account_Executive_Function.AcExButton cancelButton = new Account_Executive_Function.AcExButton("Cancel");
            cancelButton.setAlignmentX(JButton.CENTER);
            issueButton.setAlignmentX(JButton.CENTER);
            invoiceTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            invoiceTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(invoiceTitle, BorderLayout.NORTH);

            ArrayList<String> unitList = accountExecutive.get_All_Unit_List();
            JTextField invoiceIDField = new JTextField(new Invoice().get_Auto_InvoiceID());
            invoiceIDField.setEditable(false);
            JTextField issuerIDField = new JTextField(accountExecutive.getExecutiveID());
            issuerIDField.setEditable(false);
            JComboBox<String> unitComboBox = new JComboBox<>();
            for (String unit : unitList){
                unitComboBox.addItem(unit);
            }
            MaskFormatter dateMask = new MaskFormatter("##.##.####");
            JFormattedTextField amountField = new JFormattedTextField();
            amountField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9' || e.getKeyChar() == '.'){
                    } else {
                        e.consume();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
            JFormattedTextField dueDateField = new JFormattedTextField(dateMask);
            JTextField paymentTypesField = new JTextField();
            TextArea descriptionArea = new TextArea();
            descriptionArea.setBounds(0,0,getWidth(),getHeight());

            panel2.add(jLabelLeft[0]);
            panel2.add(invoiceIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(issuerIDField);
            panel2.add(jLabelLeft[2]);
            panel2.add(unitComboBox);
            panel2.add(jLabelLeft[3]);
            panel2.add(amountField);
            panel2.add(jLabelLeft[4]);
            panel2.add(dueDateField);
            panel2.add(jLabelLeft[5]);
            panel2.add(paymentTypesField);
            panel2.add(jLabelLeft[6]);
            panel2.add(descriptionArea);

            panel1.add(panel2, BorderLayout.CENTER);

            panel3.add(issueButton);
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

            issueButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!amountField.getText().equals("    .00") && !dueDateField.getText().equals("    .  ") && !paymentTypesField.getText().equals("") && !descriptionArea.getText().equals("")){
                        try {
                            Invoice invoice = new Invoice(invoiceIDField.getText(), issuerIDField.getText(), Objects.requireNonNull(unitComboBox.getSelectedItem()).toString(), Integer.parseInt(amountField.getText()), LocalDate.parse(dueDateField.getText(), formatter), paymentTypesField.getText(), descriptionArea.getText(), "unpaid");
                            accountExecutive.issue_Unit_Invoice(invoice);
                            JOptionPane.showMessageDialog(null, "Invoice issued", "Invoice issued", JOptionPane.INFORMATION_MESSAGE);
                            new Account_Executive_Invoice(executiveID).run(executiveID);
                            dispose();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Input Error", "Input Error", JOptionPane.ERROR_MESSAGE);
                            throw new RuntimeException(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please complete the information", "Information lost", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Account_Executive_Invoice(executiveID).run(executiveID);
                        dispose();
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        new Account_Executive_Invoice("AC01").run("AC01");
//        new InvoiceFrame(new Invoice().getArrayList().get(0));
    }
}
