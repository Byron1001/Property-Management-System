package Entity.Resident;

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
import UIPackage.Model.StatusType;
import UIPackage.swing.PanelBorder;
import UIPackage.swing.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Resident_Payment_Frame extends JFrame {
    public PanelBorder panelBorderLeft, panelBorderRight, panelBorderIn;
    public Menu menu = new Menu();
    public Header header = new Header();
    public Form_Home formHome = new Form_Home();
    public Table tableData = new Table();
    public Color backgroundColor = Color.WHITE;
    public String resident_Username = "resident Username";
    public JScrollPane scrollPane;
    public GridBagConstraints constraints;
    public JPanel panel;
    public Resident.Button receiptButton;
    public Resident.Button payButton;

    public Resident_Payment_Frame(String resident_Username) throws FileNotFoundException {
        this.resident_Username = resident_Username;
        menu.initMoving(Resident_Payment_Frame.this);

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
        buttonPanel.setBackground(Color.white);
        buttonPanel.setLayout(new GridLayout(1, 2, 40, 15));

        receiptButton = new Resident.Button("View Invoice");
        payButton = new Resident.Button("Make Payment");

        buttonPanel.add(receiptButton);
        buttonPanel.add(payButton);
        constraints.gridy++;
        panelBorderIn.add(buttonPanel, constraints);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);

        Resident resident = new Resident();
        resident = resident.get_Resident_Info(resident_Username);
        ArrayList<Invoice> invoiceArrayList = resident.get_Unit_All_Invoice(resident.getUnitID());
        receiptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1){
                    Invoice invoice = invoiceArrayList.get(row);
                    new InvoiceFrame(invoice).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the invoice", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });

        Resident finalResident = resident;
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                if (row != -1) {
                    Invoice invoiceSelected = invoiceArrayList.get(row);
                    try {
                        finalResident.make_Payment(invoiceSelected, resident_Username);
                        JOptionPane.showMessageDialog(null, "Invoice Paid", "Payment success", JOptionPane.INFORMATION_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/success.png"), 80, 80));
                        new Entity.Resident.Resident_Payment_Frame(resident_Username).run();
                        dispose();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the invoice", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
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

    public void run() throws FileNotFoundException {
        final String[] column = {"Invoice ID", "Issuer ID", "Unit ID", "Amount", "Due Date", "Pay Types", "Description", "Status"};
        Resident_Payment_Frame frame = new Resident_Payment_Frame("Mike1001");
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

        for (String col : column){
            frame.tableData.model.addColumn(col);
        }
        frame.tableData.preferredColumnWidth = 113;
        Resident resident = new Resident();
        resident = resident.get_Resident_Info(resident_Username);
        ArrayList<Invoice> invoiceArrayList = resident.get_Unit_All_Invoice(resident.getUnitID());
        ArrayList<Invoice> unpaidArrayList = resident.get_Unit_All_Unpaid_Invoice(resident.getUnitID());
        String number = (float) unpaidArrayList.size() / invoiceArrayList.size()*100 + "%";
        for (Invoice invoice : invoiceArrayList){
            frame.tableData.addRow(invoice.getStringArray(invoice));
        }
        frame.formHome.card1.setData(new Model_Card("payment", "Invoice Pay", number, "Remember to pay"));
        ArrayList<Payment> pendingPayment = resident.get_All_pending_Payment(resident.getUnitID());
        int total = 0;
        for (Payment pay : pendingPayment){
            total += pay.getAmount();
        }
        String totalNumber = "RM " + total;

        frame.formHome.card2.setData(new Model_Card("payment", "Pending", totalNumber, "To be confirmed"));
        ArrayList<Payment> receiptList = resident.get_All_Receipt(resident.getUnitID());
        int total2 = 0;
        for (Payment pay : receiptList){
            total2 += pay.getAmount();
        }
        String paid = "RM " + total2;
        frame.formHome.card3.setData(new Model_Card("payment", "Paid", paid, "Thks for payment"));

        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException, ClassNotFoundException {
                if (index == 0){
                    Resident_Interface residentInterface = new Resident_Interface(resident_Username);
                    residentInterface.frame.setVisible(true);
                    frame.dispose();
                } else if (index == 1){
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
                    new Entity.Resident.Resident_Complaint(resident_Username).run(resident_Username);
                    frame.dispose();
                } else if (index == 8){
                    new Login_Frame();
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }

    public static class InvoiceFrame extends JFrame{
        public InvoiceFrame(Invoice invoice){
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel2.setLayout(new GridLayout(8, 2, 15, 15));
            panel3.setLayout(new GridLayout(3, 1, 15, 15));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            JLabel invoiceTitle = new JLabel("INVOICE");
            JLabel issuedBy = new JLabel("Issued by Parhill Residence");
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
            for (int i = 0;i < jLabelLeft.length;i++){
                jLabelLeft[i].setFont(new Font("sansserif", Font.BOLD, 16));
                panel2.add(jLabelLeft[i]);
                panel2.add(jLabelRight[i]);
            }
            panel1.add(panel2, BorderLayout.CENTER);

            panel3.add(issuedBy);
            panel3.add(button);
            panel1.add(panel3, BorderLayout.SOUTH);
            setUndecorated(true);
            panel1.setPreferredSize(new Dimension(1186/2, 621));
            panel1.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
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
        new Resident_Payment_Frame("Mike1001").run();
//        new InvoiceFrame(new Invoice().getArrayList().get(0));
    }
}
