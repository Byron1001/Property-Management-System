package Entity.Executive.Account_Executive;

import Entity.Financial.Invoice;
import Entity.Financial.Statement;
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

public class Account_Executive_Statement extends JFrame{

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
    public Account_Executive_Function.AcExButton statementButton;
    public Account_Executive_Function.AcExButton issueStatementButton;

    public Account_Executive_Statement(String executiveID) throws FileNotFoundException {
        this.executiveID = executiveID;
        menu.initMoving(Account_Executive_Statement.this);

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

        statementButton = new Account_Executive_Function.AcExButton("View Statement");
        issueStatementButton = new Account_Executive_Function.AcExButton("Issue Statement");

        buttonPanel.add(statementButton);
        buttonPanel.add(issueStatementButton);
        constraints.gridy++;
        panelBorderIn.add(buttonPanel, constraints);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);

        ArrayList<Statement> statementArrayList = new Statement().getArrayList();
        statementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Statement statement = statementArrayList.get(row);
                    new StatementFrame(statement).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the statement", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });

        issueStatementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new IssueFrame(new Account_Executive_Function.Account_Executive().get_Account_Executive_Info(executiveID));
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
        final String[] column = {"Statement ID", "Issuer Position", "Date", "Description", "Receiver ID"};
        Account_Executive_Statement frame = new Account_Executive_Statement(executiveID);
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

        frame.tableData.statusAllowed = false;

        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }
        frame.tableData.preferredColumnWidth = 180;

        ArrayList<Statement> statementArrayList = new Statement().getArrayList();

        for (Statement statement : statementArrayList) {
            frame.tableData.addRow(statement.getStringArray(statement));
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
                    new Account_Executive_Payment(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 3) {
                    new Entity.Executive.Account_Executive.Account_Executive_Receipt(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 4) {
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

    public class StatementFrame extends JFrame {
        public StatementFrame(Statement statement) {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            JLabel formTitle = new JLabel("Statement");
            JLabel issuedBy = new JLabel("Issued by Parhill Residence");
            JLabel[] jLabelLeft = {new JLabel("Issuer Position"),
                    new JLabel("Date"), new JLabel("Receiver ID"),
                    new JLabel("Description")};
            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(statement.getIssuer_Position()),
                    new JLabel(statement.getDate().format(formatter)), new JLabel(statement.getReceiverID()),
                    new JLabel(statement.getDescription())};
            Account_Executive_Function.AcExButton button = new Account_Executive_Function.AcExButton("Close");
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

    public class IssueFrame extends JFrame {
        public IssueFrame(Account_Executive_Function.Account_Executive accountExecutive) throws FileNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel2.setLayout(new GridLayout(8, 2, 15, 15));
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            JLabel statementTitle = new JLabel("STATEMENT");
            JLabel[] jLabelLeft = {new JLabel("Statement ID"), new JLabel("Issuer Position"),
                    new JLabel("Date"), new JLabel("Description"),
                    new JLabel("Receiver ID")};
            ArrayList<String> receiverList = new Statement().get_Receiver_List();
            Account_Executive_Function.AcExButton issueButton = new Account_Executive_Function.AcExButton("Issue Statement");
            Account_Executive_Function.AcExButton cancelButton = new Account_Executive_Function.AcExButton("Cancel");
            cancelButton.setAlignmentX(JButton.CENTER);
            issueButton.setAlignmentX(JButton.CENTER);
            statementTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            statementTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(statementTitle, BorderLayout.NORTH);

            JTextField statementIDField = new JTextField(new Statement().get_Auto_StatementID());
            statementIDField.setEditable(false);
            JTextField issuerPositionField = new JTextField(accountExecutive.getPosition());
            issuerPositionField.setEditable(false);
            JComboBox<String> receiverComboBox = new JComboBox<>();
            for (String receiver : receiverList){
                receiverComboBox.addItem(receiver);
            }
            MaskFormatter dateMask = new MaskFormatter("##.##.####");
            JFormattedTextField dateField = new JFormattedTextField(dateMask);
            TextArea descriptionArea = new TextArea();
            descriptionArea.setBounds(0,0,getWidth(),getHeight());

            panel2.add(jLabelLeft[0]);
            panel2.add(statementIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(issuerPositionField);
            panel2.add(jLabelLeft[2]);
            panel2.add(dateField);
            panel2.add(jLabelLeft[3]);
            panel2.add(descriptionArea);
            panel2.add(jLabelLeft[4]);
            panel2.add(receiverComboBox);

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
                    if (!dateField.getText().equals("  .  .    ") && !descriptionArea.getText().equals("")){
                        Statement statement = new Statement(statementIDField.getText(), issuerPositionField.getText(), LocalDate.parse(dateField.getText(), formatter), descriptionArea.getText(), receiverComboBox.getSelectedItem().toString());
                        try {
                            accountExecutive.issue_Statement(statement);
                            JOptionPane.showMessageDialog(null, "Statement issued", "Statement issued", JOptionPane.INFORMATION_MESSAGE);
                            new Account_Executive_Statement(executiveID).run(executiveID);
                            dispose();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please fill in complete information", "Information lost", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Account_Executive_Statement(executiveID).run(executiveID);
                        dispose();
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        new Account_Executive_Statement("AC01").run("AC01");
//        new ReceiptFrame(new Invoice().getArrayList().get(0));
    }
}
