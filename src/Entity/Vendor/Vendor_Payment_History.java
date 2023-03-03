package Entity.Vendor;

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

public class Vendor_Payment_History extends JFrame {
    public PanelBorder panelBorderLeft, panelBorderRight, panelBorderIn;
    public UIPackage.Component.Menu menu = new Menu();
    public Header header = new Header();
    public Form_Home formHome = new Form_Home();
    public Table tableData = new Table();
    public Color backgroundColor = Color.WHITE;
    public String vendor_Username;
    public JScrollPane scrollPane;
    public GridBagConstraints constraints;
    public JPanel panel;
    public Vendor.Button receiptButton;

    public Vendor_Payment_History(String vendor_Username) throws FileNotFoundException {
        this.vendor_Username = vendor_Username;
        menu.initMoving(Entity.Vendor.Vendor_Payment_History.this);

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
        buttonPanel.setLayout(new GridLayout(1, 2, 40, 15));

        receiptButton = new Vendor.Button("View Receipt");

        buttonPanel.add(receiptButton);
        constraints.gridy++;
        panelBorderIn.add(buttonPanel, constraints);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);

        Vendor vendor = new Vendor();
        vendor = vendor.get_Vendor_Info(vendor_Username);
        ArrayList<Payment> receiptArrayList = vendor.get_All_Receipt(vendor.getVendor_Unit());
        receiptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Payment payment = receiptArrayList.get(row);
                    new Entity.Vendor.Vendor_Payment_History.ReceiptFrame(payment).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the payment history", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
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

    public void run(String vendor_Username) throws FileNotFoundException {
        final String[] column = {"Payment ID", "Invoice ID", "Pay Username", "Unit ID", "Amount", "Payment date", "Payment Types", "Issuer ID", "Issued date"};
        Entity.Vendor.Vendor_Payment_History frame = new Entity.Vendor.Vendor_Payment_History(vendor_Username );
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("payment", "Invoice payment", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("paymentHistory", "Payment History", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("statement", "Statement", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("complaint", "Complaint", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#EB5757");
        frame.menu.colorLeft = Color.decode("#000000");
        frame.menu.initMoving(frame);

        frame.tableData.statusAllowed = false;

        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }
        frame.tableData.preferredColumnWidth = 100;

        Vendor vendor = new Vendor();
        vendor = vendor.get_Vendor_Info(vendor_Username);
        ArrayList<Payment> receiptArrayList = vendor.get_All_Receipt(vendor.getVendor_Unit());

        for (Payment payment : receiptArrayList) {
            frame.tableData.addRow(payment.getStringArray(payment));
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
                } else if (index == 3) {
                    new Entity.Vendor.Vendor_Statement_Frame(vendor_Username).run(vendor_Username);
                    frame.dispose();
                } else if (index == 4) {
                    new Entity.Vendor.Vendor_Complaint(vendor_Username).run(vendor_Username);
                    frame.dispose();
                } else if (index == 5) {
                    new Login_Frame();
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }

    public static class ReceiptFrame extends JFrame {
        public ReceiptFrame(Payment payment) {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            JLabel receiptTitle = new JLabel("RECEIPT");
            JLabel issuedBy = new JLabel("Issued by Parkhill Residence");
            JLabel[] jLabelLeft = {new JLabel("Payment ID"), new JLabel("Invoice ID"),
                    new JLabel("Pay Username"), new JLabel("Unit ID"),
                    new JLabel("Amount"), new JLabel("Payment date"),
                    new JLabel("Payment Types"), new JLabel("Issuer ID"),
                    new JLabel("Issued date"), new JLabel("Description")};
            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            String date = "";
            String id = "";
            if (payment.getIssuedDate() != null){
                date = payment.getIssuedDate().format(formatter);
            }
            if (payment.getIssuerID() != null){
                id = payment.getIssuerID();
            }
            JLabel[] jLabelRight = {new JLabel(payment.getPaymentID()), new JLabel(payment.getInvoiceID()),
                    new JLabel(payment.getPay_Username()), new JLabel(payment.getUnitID()),
                    new JLabel("RM " + payment.getAmount()), new JLabel(payment.getPayment_Date().format(formatter)),
                    new JLabel(payment.getPaymentTypes()), new JLabel(payment.getIssuerID()),
                    new JLabel(date), new JLabel(id)};
            Vendor.Button button = new Vendor.Button("Close");
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
        new Entity.Vendor.Vendor_Payment_History("VE001").run("VE001");
//        new ReceiptFrame(new Invoice().getArrayList().get(0));
    }
}
