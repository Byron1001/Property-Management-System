package Entity.Building_Manager;

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

public class Building_Manager_Operation extends JFrame {
    public PanelBorder panelBorderLeft, panelBorderRight, panelBorderIn;
    public Menu menu = new Menu();
    public Header header = new Header();
    public Form_Home formHome = new Form_Home();
    public Table tableData = new Table();
    public Color backgroundColor = Color.WHITE;
    public String buildingManagerID;
    public JScrollPane scrollPane;
    public GridBagConstraints constraints;
    public JPanel panel;
    public Building_Manager_Function.Button addButton, deleteButton, viewButton, modifyButton, payButton;

    public Building_Manager_Operation(String buildingManagerID) throws FileNotFoundException {
        this.buildingManagerID = buildingManagerID;
        menu.initMoving(Building_Manager_Operation.this);

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

        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), buildingManagerID, TitledBorder.LEFT, TitledBorder.TOP);
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
        buttonPanel.setLayout(new GridLayout(3, 2, 40, 15));

        addButton = new Building_Manager_Function.Button("Add Operation Budgeting");
        deleteButton = new Building_Manager_Function.Button("Delete Operation Budgeting");
        viewButton = new Building_Manager_Function.Button("View Operation Info");
        modifyButton = new Building_Manager_Function.Button("Update Operation Info");
        payButton = new Building_Manager_Function.Button("Pay for operation");

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(payButton);
        constraints.gridy++;
        panelBorderIn.add(buttonPanel, constraints);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);

        ArrayList<Building_Manager_Function.Building_Manager.Operation> operationArrayList = new Building_Manager_Function.Building_Manager.Operation().getArrayList();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new addFrame(buildingManagerID).setVisible(true);
                    dispose();
                } catch (IOException | ClassNotFoundException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                if (row != -1){
                    try {
                        Building_Manager_Function.Building_Manager.Operation operationSelected = operationArrayList.get(row);
                        new modifyFrame(operationSelected).setVisible(true);
                        dispose();
                    } catch (IOException | ClassNotFoundException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the Operation", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Building_Manager_Function.Building_Manager.Operation operationSelected = operationArrayList.get(row);
                    try {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure to delete this Operation?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            operationSelected.delete_Operation(operationSelected.getOperationID());
                            JOptionPane.showMessageDialog(null, "Operation deleted", "Operation delete success", JOptionPane.INFORMATION_MESSAGE);
                            new Building_Manager_Operation(buildingManagerID).run(buildingManagerID);
                            dispose();
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the Operation", "Choice error", JOptionPane.ERROR_MESSAGE, header.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 80, 80));
                }
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                int column = tableData.getSelectedColumn();
                if (row != -1 || column != -1) {
                    Building_Manager_Function.Building_Manager.Operation operationSelected = operationArrayList.get(row);
                    new viewFrame(operationSelected).setVisible(true);
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

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableData.getSelectedRow();
                if (row != -1){
                    Building_Manager_Function.Building_Manager.Operation operationSelected = operationArrayList.get(row);
                    int result = JOptionPane.showConfirmDialog(null, "Are you sure to make payment for this operation", "Payment confirmation", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION){
                        try {
                            new Building_Manager_Function.Building_Manager.Operation().pay_for_Operation(operationSelected);
                            JOptionPane.showMessageDialog(null, "Payment success", "Payment success", JOptionPane.INFORMATION_MESSAGE);
                            new Building_Manager_Operation(buildingManagerID).run(buildingManagerID);
                            dispose();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Payment cancelled", "Payment cancelled", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please choose the operation for payment", "Choice error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void run(String buildingManagerID) throws FileNotFoundException {
        final String[] column = {"Operation ID","Building Manager ID","Operation Title", "Description", "Budget amount"};
        Building_Manager_Operation frame = new Building_Manager_Operation(buildingManagerID);
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("employee", "User Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("report", "Report", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("deposit", "Operation and Budget Planning", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Team Structure Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#f4791f");
        frame.menu.colorLeft = Color.decode("#659999");
        frame.menu.initMoving(frame);
        frame.tableData.statusAllowed = false;

        for (String col : column) {
            frame.tableData.model.addColumn(col);
        }
        frame.tableData.preferredColumnWidth = 160;
        int fund = Payment.get_Fund_Amount();
        ArrayList<Building_Manager_Function.Building_Manager.Operation> operationArrayList = new Building_Manager_Function.Building_Manager.Operation().getArrayList();
        ArrayList<Payment.Expenses> expensesArrayList = new Payment.Expenses().getArrayList();

        for (Building_Manager_Function.Building_Manager.Operation operation : operationArrayList) {
            frame.tableData.addRow(operation.getStringArray(operation));
        }

        String number = (float) new Building_Manager_Function.Building_Manager.Operation().get_All_Available_Operation_Budget_Amount() / fund * 100 + "%";
        frame.formHome.card1.setData(new Model_Card("payment", "Total fund available", number, "Fund of Parkhill R."));

        int total = new Building_Manager_Function.Building_Manager.Operation().get_All_Available_Operation_Budget_Amount();
        String totalNumber = "RM " + total;

        frame.formHome.card2.setData(new Model_Card("payment", "Fund in budget", totalNumber, "Fund planning to be used"));
        int total2 = 0;
        for (Payment.Expenses expenses : expensesArrayList) {
            total2 += expenses.getAmount();
        }
        String paid = "RM " + total2;
        frame.formHome.card3.setData(new Model_Card("payment", "Paid Expenses", paid, "Expenses for operation"));

        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException, ClassNotFoundException {
                if (index == 0) {
                    Building_Manager_Interface Building_ManagerInterface = new Building_Manager_Interface(buildingManagerID);
                    Building_ManagerInterface.frame.setVisible(true);
                    frame.dispose();
                } else if (index == 1) {
                    new Building_Manager_User_Management(buildingManagerID).run(buildingManagerID);
                    frame.dispose();
                } else if (index == 2) {
                    new Building_Manager_Report(buildingManagerID);
                    frame.dispose();
                } else if (index == 3) {
                } else if (index == 4) {
                    new Entity.Building_Manager.Building_Manager_Team_Management(buildingManagerID).run(buildingManagerID);
                    frame.dispose();
                } else if (index == 5) {
                    new Login_Frame();
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }

    private class addFrame extends JFrame {
        public addFrame(String buildingManagerID) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));
            Building_Manager_Function.Building_Manager.Operation operation = new Building_Manager_Function.Building_Manager.Operation();

            JLabel formTitle = new JLabel("OPERATION ADDING");
            JLabel[] jLabelLeft = {new JLabel("Operation ID"), new JLabel("Building Manager ID"),
                    new JLabel("Operation Title"), new JLabel("Description"),
                    new JLabel("Budget Amount (RM)")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }
            JTextField operationIDField = new JTextField(operation.get_Auto_OperationID());
            operationIDField.setEditable(false);
            JTextField buildingManagerIDField = new JTextField(buildingManagerID);
            buildingManagerIDField.setEditable(false);
            JTextField operationTitleField = new JTextField();
            TextArea descriptionArea = new TextArea();
            descriptionArea.setBounds(0,0,getWidth(),getHeight());
            JTextField amountField = new JTextField();
            amountField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9'){
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

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Resident.Button addButton = new Resident.Button("Add Operation Budget");
            Resident.Button cancelButton = new Resident.Button("Cancel Budgeting");

            addButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);
            panel2.add(jLabelLeft[0]);
            panel2.add(operationIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(buildingManagerIDField);
            panel2.add(jLabelLeft[2]);
            panel2.add(operationTitleField);
            panel2.add(jLabelLeft[3]);
            panel2.add(descriptionArea);
            panel2.add(jLabelLeft[4]);
            panel2.add(amountField);
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
                    if (!operationTitleField.getText().equals("") && !descriptionArea.getText().equals("") && !amountField.getText().equals("")){
                        Building_Manager_Function.Building_Manager.Operation operationNew = new Building_Manager_Function.Building_Manager.Operation(operationIDField.getText(), buildingManagerIDField.getText(), operationTitleField.getText(), descriptionArea.getText(), Integer.parseInt(amountField.getText()));
                        try {
                            boolean check = operationNew.check_Fund_Amount_Enough(operationNew.getBudget_Amount());
                            if (!check) {
                                JOptionPane.showMessageDialog(null, "Fund Amount not enough", "Fund amount not enough", JOptionPane.ERROR_MESSAGE);
                            } else {
                                operationNew.add_Operation(operationNew);
                                JOptionPane.showMessageDialog(null, "Operation added", "Opeartion added", JOptionPane.INFORMATION_MESSAGE);
                                new Building_Manager_Operation(buildingManagerID).run(buildingManagerID);
                                dispose();
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please provide complete information", "Informatiion lost", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Building_Manager_Operation(buildingManagerID).run(buildingManagerID);
                        dispose();
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private class modifyFrame extends JFrame {
        public modifyFrame(Building_Manager_Function.Building_Manager.Operation operation) throws IOException, ClassNotFoundException, ParseException {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("OPERATION MODIFYING");
            JLabel[] jLabelLeft = {new JLabel("Operation ID"), new JLabel("Building Manager ID"),
                    new JLabel("Operation Title"), new JLabel("Description"),
                    new JLabel("Budget Amount (RM)")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }
            JTextField operationIDField = new JTextField(operation.get_Auto_OperationID());
            operationIDField.setEditable(false);
            JTextField buildingManagerIDField = new JTextField(operation.getBuilding_Manager_ID());
            buildingManagerIDField.setEditable(false);
            JTextField operationTitleField = new JTextField(operation.getOperationTitle());
            TextArea descriptionArea = new TextArea(operation.getDescription());
            descriptionArea.setBounds(0,0,getWidth(),getHeight());
            JTextField amountField = new JTextField(Integer.toString(operation.getBudget_Amount()));
            amountField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9'){
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

            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            Resident.Button modifyButton = new Resident.Button("MODIFY Operation Budget");
            Resident.Button cancelButton = new Resident.Button("Cancel Budgeting");

            modifyButton.setAlignmentX(JButton.CENTER);
            cancelButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);
            panel2.add(jLabelLeft[0]);
            panel2.add(operationIDField);
            panel2.add(jLabelLeft[1]);
            panel2.add(buildingManagerIDField);
            panel2.add(jLabelLeft[2]);
            panel2.add(operationTitleField);
            panel2.add(jLabelLeft[3]);
            panel2.add(descriptionArea);
            panel2.add(jLabelLeft[4]);
            panel2.add(amountField);
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
                    if (!operationTitleField.getText().equals("") && !descriptionArea.getText().equals("") && !amountField.getText().equals("")){
                        Building_Manager_Function.Building_Manager.Operation operationNew = new Building_Manager_Function.Building_Manager.Operation(operationIDField.getText(), buildingManagerIDField.getText(), operationTitleField.getText(), descriptionArea.getText(), Integer.parseInt(amountField.getText()));
                        try {
                            boolean check = operationNew.check_Fund_Amount_Enough(operationNew.getBudget_Amount());
                            if (!check) {
                                JOptionPane.showMessageDialog(null, "Fund Amount not enough", "Fund amount not enough", JOptionPane.ERROR_MESSAGE);
                            } else {
                                operationNew.modify_Operation(operationNew, operation.getOperationID());
                                JOptionPane.showMessageDialog(null, "Operation modified", "Operation modified", JOptionPane.INFORMATION_MESSAGE);
                                new Building_Manager_Operation(buildingManagerID).run(buildingManagerID);
                                dispose();
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please provide complete information", "Informatiion lost", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Building_Manager_Operation(buildingManagerID).run(buildingManagerID);
                        dispose();
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    private static class viewFrame extends JFrame {
        public viewFrame(Building_Manager_Function.Building_Manager.Operation operation) {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            JLabel formTitle = new JLabel("OPERATION DETAILS");
            JLabel[] jLabelLeft = {new JLabel("Operation ID"), new JLabel("Building Manager ID"),
                    new JLabel("Operation Title"), new JLabel("Description"),
                    new JLabel("Budget Amount (RM)")};
            for (JLabel label : jLabelLeft) {
                label.setFont(new Font("sansserif", Font.BOLD, 16));
            }
            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));

            JLabel[] jLabelRight = {new JLabel(operation.getOperationID()), new JLabel(operation.getBuilding_Manager_ID()),
                    new JLabel(operation.getOperationTitle()), new JLabel(operation.getDescription()),
                    new JLabel(Integer.toString(operation.getBudget_Amount()))};
            Building_Manager_Function.Button closeButton = new Building_Manager_Function.Button("Close");
            closeButton.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);
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
        new Building_Manager_Operation("BM01").run("BM01");
//        new InvoiceFrame(new Invoice().getArrayList().get(0));
    }
}
