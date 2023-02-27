package Entity.Resident;

import Entity.Financial.Invoice;
import Entity.Financial.Payment;
import Entity.Login.Login_Frame;
import UIPackage.Component.Header;
import UIPackage.Component.Menu;
import UIPackage.Event.EventMenuSelected;
import UIPackage.Form.Form_Home;
import UIPackage.Model.Model_Card;
import UIPackage.Model.Model_Menu;
import UIPackage.swing.PanelBorder;
import UIPackage.swing.Table;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Resident_Deposit_Frame extends JFrame{
        public PanelBorder panelBorderLeft, panelBorderRight, panelBorderIn;
        public Menu menu = new Menu();
        public Form_Home formHome = new Form_Home();
        public Header header = new Header();
        public Color backgroundColor = Color.WHITE;
        public String resident_Username = "resident Username";
        public GridBagConstraints constraints;
        public Resident.Button checkPaymentButton;

        public Resident_Deposit_Frame(String resident_Username) throws FileNotFoundException {
            this.resident_Username = resident_Username;
            menu.initMoving(Resident_Deposit_Frame.this);

            panelBorderLeft = new PanelBorder();
            panelBorderRight = new PanelBorder();
//            setLayout(new BorderLayout());
            panelBorderRight.setLayout(new BorderLayout());
            panelBorderLeft.setLayout(new GridLayout(1, 1));

            panelBorderLeft.add(menu);
            panelBorderRight.setBackground(backgroundColor);

            panelBorderIn = new PanelBorder();
            panelBorderIn.setBackground(backgroundColor);

            constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.fill = GridBagConstraints.HORIZONTAL;

            formHome.removeAll();
            header.removeAll();
            panelBorderRight.add(header, BorderLayout.NORTH);
            panelBorderRight.add(panelBorderIn, BorderLayout.CENTER);
            panelBorderRight.add(formHome, BorderLayout.SOUTH);

            add(panelBorderLeft, BorderLayout.LINE_START);
            add(panelBorderRight, BorderLayout.CENTER);

            setUndecorated(true);
            setSize(new Dimension(1186, 621));
            setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
            setLocationRelativeTo(null);

            Resident resident = new Resident();
            resident = resident.get_Resident_Info(resident_Username);

            Payment.Deposit deposit = new Payment.Deposit();
            ArrayList<Payment.Deposit> depositArrayList = deposit.getArrayList();
            for (Payment.Deposit deposit1 : depositArrayList){
                if (deposit1.getUnitID().equals(resident.getUnitID())){
                    deposit = deposit1;
                }
            }
            final String[] row = {"Username", "Amount", "Unit ID"};
            final String[] depositInfo = {deposit.getUsername(), Integer.toString(deposit.getAmount()), deposit.getUnitID()};
            panelBorderIn.setLayout(new BorderLayout());

            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(Color.white);
            centerPanel.setLayout(new BorderLayout());

            JPanel inPanel = new JPanel();
            inPanel.setBackground(Color.white);
            inPanel.setLayout(new GridLayout(3,2, 30, 0));
            for (int i = 0;i < row.length;i++){
                JLabel rowLabel = new JLabel(row[i]);
                rowLabel.setHorizontalAlignment(JLabel.RIGHT);
                rowLabel.setFont(new Font("sansserif", Font.BOLD, 14));
                inPanel.add(rowLabel);
                JLabel depositLabel = new JLabel(depositInfo[i]);
                depositLabel.setHorizontalAlignment(JLabel.LEFT);
                inPanel.add(depositLabel);
            }

            centerPanel.add(new JLabel(Resident.toIcon(new ImageIcon("src/UIPackage/Icon/depositIn.png"), 80, 80)), BorderLayout.CENTER);
            centerPanel.add(inPanel, BorderLayout.SOUTH);
            centerPanel.setBorder(BorderFactory.createEmptyBorder(0,0,90,0));
            checkPaymentButton = new Resident.Button("Check Deposit Payment");
            panelBorderIn.add(checkPaymentButton, BorderLayout.SOUTH);
            panelBorderIn.add(centerPanel, BorderLayout.CENTER);
            panelBorderIn.setBorder(BorderFactory.createEmptyBorder(40,90,40,90));
        }

        public void run() throws FileNotFoundException {
            Entity.Resident.Resident_Deposit_Frame frame = new Entity.Resident.Resident_Deposit_Frame("Mike1001");
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

            frame.menu.addEventMenuSelected(new EventMenuSelected() {
                @Override
                public void selected(int index) throws IOException, ClassNotFoundException {
                    if (index == 0){
                        Resident_Interface residentInterface = new Resident_Interface(resident_Username);
                        residentInterface.frame.setVisible(true);
                        frame.dispose();
                    } else if (index == 1){
                        new Resident_Payment_Frame(resident_Username).run();
                        frame.dispose();
                    } else if (index == 2){
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

            frame.checkPaymentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Resident_Payment_Frame(resident_Username).run();
                        frame.dispose();
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            frame.setVisible(true);
        }

        public static void main(String[] args) throws FileNotFoundException {
            new Entity.Resident.Resident_Deposit_Frame("Mike1001").run();
//        new ReceiptFrame(new Invoice().getArrayList().get(0));
        }
    }

