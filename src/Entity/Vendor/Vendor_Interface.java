package Entity.Vendor;

import Entity.Login.Login;
import Entity.Login.Login_Frame;
import UIPackage.Event.EventMenuSelected;
import UIPackage.Model.Model_Menu;
import UIPackage.main.UIFrame;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Vendor_Interface {
    public UIFrame frame;
    private String vendor_Username;

    public String getVendor_Username() {
        return vendor_Username;
    }

    public void setVendor_Username(String vendor_Username) {
        this.vendor_Username = vendor_Username;
    }

    public void setPanelBorderRight(JPanel panel) {
        frame.panelBorderRight.removeAll();
        frame.panelBorderRight.add(panel);
        frame.panelBorderRight.revalidate();
        frame.repaint();
    }

    public Vendor_Interface(String vendor_Username) throws FileNotFoundException {
        this.vendor_Username = vendor_Username;
        frame = new UIFrame(vendor_Username);
        setPanelBorderRight(new Vendor_Profile_Panel(vendor_Username));

        frame.formHome.removeAll();
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("payment", "Invoice payment", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("paymentHistory", "Payment History", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("statement", "Statement", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("complaint", "Complaint", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout Booking", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#EB5757");
        frame.menu.colorLeft = Color.decode("#000000");

        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException, ClassNotFoundException {
                if (index == 0) {
                } else if (index == 1) {
                    new Entity.Vendor.Vendor_Payment_Frame(vendor_Username).run(vendor_Username);
                    frame.dispose();
                } else if (index == 2) {
                    new Entity.Vendor.Vendor_Payment_History(vendor_Username).run(vendor_Username);
                    frame.dispose();
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
    }

    public class Vendor_Profile_Panel extends JPanel {
        public Vendor.Button updateButton;
        public Vendor_Profile_Panel(String vendor_Username) throws FileNotFoundException {
            setPreferredSize(new Dimension(1000, 500));
            setLayout(new BorderLayout());

            ImageIcon icon = new ImageIcon("src/UIPackage/Icon/profile.png");
            icon = Vendor.toIcon(icon, 200, 200);

            JLabel iconLabel = new JLabel(icon);
            add(iconLabel, BorderLayout.NORTH);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout(0,20));
            JLabel profileLabel = new JLabel("Profile");
            Font titleFont = new Font("Sansserif", Font.BOLD, 24);
            profileLabel.setFont(titleFont);
            profileLabel.setHorizontalAlignment(JLabel.CENTER);
            panel.add(profileLabel, BorderLayout.NORTH);

            JPanel downPanel = new JPanel();
            downPanel.setLayout(new GridLayout(7, 2, 30, 10));
            String[] rowString = {"Vendor Username", "Name", "Gender", "Contact_Number", "Vendor unit", "Monthly Payment", "Payment pending"};
            Vendor vendor = new Vendor().get_Vendor_Info(vendor_Username);
            String[] data = {vendor.getVendor_Username(), vendor.getName(), Character.toString(vendor.getGender()), vendor.getContact_Number(), vendor.getVendor_Unit(), Integer.toString(vendor.getMonthly_payment()), Integer.toString(vendor.getUnpaid_payment())};
            for (int i = 0;i < rowString.length;i++){
                JLabel label1 = new JLabel(rowString[i]);
                label1.setHorizontalAlignment(JLabel.RIGHT);
                label1.setFont(new Font("sansserif", Font.BOLD, 14));
                downPanel.add(label1);
                JLabel label2 = new JLabel(data[i]);
                downPanel.add(label2);
            }
            updateButton = new Vendor.Button("Update Profile");

            panel.add(downPanel, BorderLayout.CENTER);
            panel.add(updateButton, BorderLayout.SOUTH);

            add(panel, BorderLayout.CENTER);
            setBorder(BorderFactory.createEmptyBorder(50,50,100,50));

            updateButton.setBackground(Color.decode("#45a247"));
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setEnabled(false);
                    UpdateInfo updateInfo = null;
                    try {
                        updateInfo = new UpdateInfo(data);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                    updateInfo.setLocationRelativeTo(null);
                    updateInfo.setVisible(true);
                }
            });
        }

        public class UpdateInfo extends JFrame{
            private Font labelFont = new Font("sansserif", Font.BOLD, 14);
            private Vendor.Button updateButton;
            private Vendor.Button cancelButton;

            public UpdateInfo(String[] data) throws ParseException {
                setUndecorated(true);
                setShape(new RoundRectangle2D.Double(0, 0, 1186 / 2, 621 / 2, 15, 15));
                setPreferredSize(new Dimension(1186 / 2, 621 / 2));

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(5, 2, 20, 10));

                JLabel username_Label = new JLabel("Vendor Username");
                username_Label.setFont(labelFont);
                JLabel name_Label = new JLabel("Name");
                name_Label.setFont(labelFont);
                JLabel gender_Label = new JLabel("Gender");
                gender_Label.setFont(labelFont);
                JLabel contact_Number_Label = new JLabel("Contact Number");
                contact_Number_Label.setFont(labelFont);

                JTextField vendor_Username_TextField = new JTextField();
                JTextField name_TextField = new JTextField();
                panel.add(username_Label);
                panel.add(vendor_Username_TextField);
                panel.add(name_Label);
                panel.add(name_TextField);
                panel.add(gender_Label);

                JPanel panel1 = new JPanel();
                panel1.setLayout(new GridLayout(2, 1));

                JRadioButton maleButton = new JRadioButton("Male");
                JRadioButton femaleButton = new JRadioButton("Female");

                panel1.add(maleButton);
                panel1.add(femaleButton);
                ButtonGroup group = new ButtonGroup();
                group.add(maleButton);
                group.add(femaleButton);
                panel.add(panel1);

                panel.add(contact_Number_Label);
                MaskFormatter formatter = new MaskFormatter("01#-#######");
                JFormattedTextField contact_FormattedTextField = new JFormattedTextField(formatter);
                panel.add(contact_FormattedTextField);

                updateButton = new Vendor.Button("Update");
                cancelButton = new Vendor.Button("Cancel");
                updateButton.color1 = Color.decode("#654ea3");
                updateButton.color2 = Color.decode("#eaafc8");
                panel.add(updateButton);
                panel.add(cancelButton);

                vendor_Username_TextField.setText(data[0]);
                name_TextField.setText(data[1]);
                if (data[2].equals("F")){
                    femaleButton.setSelected(true);
                } else {
                    maleButton.setSelected(true);
                }
                contact_FormattedTextField.setText(data[3]);

                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });

                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Login login = new Login();
                        boolean check = true;
                        if (login.check_Punctuation(vendor_Username_TextField.getText()) || login.check_Punctuation(name_TextField.getText())){
                            JOptionPane.showMessageDialog(null, "Username and Name cannot include punctuation", "Punctuation error", JOptionPane.ERROR_MESSAGE, Vendor.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 60, 60));
                            check = false;
                        } else {
                            try {
                                if (login.check_Username_Availability(vendor_Username_TextField.getText()) && !data[0].equals(vendor_Username_TextField.getText())) {
                                    JOptionPane.showMessageDialog(null, "Username already registered", "Username registration error", JOptionPane.ERROR_MESSAGE, Vendor.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 60, 60));
                                    check = false;
                                }
                            } catch (IOException | ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        if (check){
                            char gender;
                            if (maleButton.isSelected()){
                                gender = 'M';
                            } else {
                                gender = 'F';
                            }
                            String ori_Username = data[0];
                            Vendor vendorUpdated = new Vendor(vendor_Username_TextField.getText(), name_TextField.getText(), gender, contact_FormattedTextField.getText(), data[4], Integer.parseInt(data[5]), Integer.parseInt(data[6]));
                            try {
                                vendorUpdated.update_Vendor_Info(vendorUpdated, ori_Username);
                                JOptionPane.showMessageDialog(null, "Information Updated", "Information Update", JOptionPane.INFORMATION_MESSAGE, Vendor.toIcon(new ImageIcon("src/UIPackage/Icon/success.png"), 60, 60));
                                dispose();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            try {
                                setPanelBorderRight(new Vendor_Profile_Panel(vendor_Username_TextField.getText()));
                            } catch (FileNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                });

                panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
                setContentPane(panel);
                pack();
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Vendor_Interface vendorInterface = new Vendor_Interface("VE001");
        vendorInterface.frame.setVisible(true);
    }
}
