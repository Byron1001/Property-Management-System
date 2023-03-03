package Entity.Executive.Admin_Executive;

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

public class Admin_Executive_Interface {
    public UIFrame frame;
    private String executiveID;

    public String getExecutiveID() {
        return executiveID;
    }

    public void setExecutiveID(String executiveID) {
        this.executiveID = executiveID;
    }

    public void setPanelBorderRight(JPanel panel) {
        frame.panelBorderRight.removeAll();
        frame.panelBorderRight.add(panel);
        frame.panelBorderRight.revalidate();
        frame.repaint();
    }

    public Admin_Executive_Interface(String executiveID) throws FileNotFoundException {
        this.executiveID = executiveID;
        frame = new UIFrame(executiveID);
        setPanelBorderRight(new Admin_Executive_Profile_Panel(executiveID));

        frame.formHome.removeAll();
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("house", "Unit Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Resident Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("complaint", "Complaint Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("employee", "Employee Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("facility", "Facility Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("booking", "Facility Booking", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("vendor", "Vendor", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("entry", "Visitor Pass", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#243B55");
        frame.menu.colorLeft = Color.decode("#141E30");

        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException, ClassNotFoundException {
                if (index == 0) {
                } else if (index == 1) {
                    new Admin_Executive_Unit_Management(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 2) {
                    new Entity.Executive.Admin_Executive.Admin_Executive_Resident_Management(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 3) {
                    new Entity.Executive.Admin_Executive.Admin_Executive_Complaint_Management(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 4) {
                    new Entity.Executive.Admin_Executive.Admin_Executive_Employee_Management(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 5) {
                    new Entity.Executive.Admin_Executive.Admin_Executive_Facility_Management(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 6) {
                    new Entity.Executive.Admin_Executive.Admin_Executive_Facility_Booking(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 7) {
                    new Entity.Executive.Admin_Executive.Admin_Executive_Vendor(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 8) {
                    new Entity.Executive.Admin_Executive.Admin_Executive_Visitor_Pass(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 9){
                    new Login_Frame();
                    frame.dispose();
                }
            }
        });
    }

    public class Admin_Executive_Profile_Panel extends JPanel {
        public Admin_Executive_Function.Button updateButton;

        public Admin_Executive_Profile_Panel(String executiveID) throws FileNotFoundException {
            setPreferredSize(new Dimension(1000, 500));
            setLayout(new BorderLayout());

            ImageIcon icon = new ImageIcon("src/UIPackage/Icon/profile.png");
            icon = Admin_Executive_Function.toIcon(icon, 200, 200);

            JLabel iconLabel = new JLabel(icon);
            add(iconLabel, BorderLayout.NORTH);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout(0, 20));
            JLabel profileLabel = new JLabel("Profile");
            Font titleFont = new Font("sansserif", Font.BOLD, 24);
            profileLabel.setFont(titleFont);
            profileLabel.setHorizontalAlignment(JLabel.CENTER);
            panel.add(profileLabel, BorderLayout.NORTH);

            JPanel downPanel = new JPanel();
            downPanel.setLayout(new GridLayout(7, 2, 30, 10));
            String[] rowString = {"Executive ID", "Name", "Gender", "Contact_number", "Position"};
            Admin_Executive_Function.Admin_Executive adminExecutive = new Admin_Executive_Function.Admin_Executive().get_Admin_Executive_Info(executiveID);
            String[] data = {adminExecutive.getExecutiveID(), adminExecutive.getName(), Character.toString(adminExecutive.getGender()), adminExecutive.getContact_Number(), adminExecutive.getPosition()};
            for (int i = 0; i < rowString.length; i++) {
                JLabel label1 = new JLabel(rowString[i]);
                label1.setHorizontalAlignment(JLabel.RIGHT);
                label1.setFont(new Font("sansserif", Font.BOLD, 14));
                downPanel.add(label1);
                JLabel label2 = new JLabel(data[i]);
                downPanel.add(label2);
            }
            updateButton = new Admin_Executive_Function.Button("Update Profile");

            panel.add(downPanel, BorderLayout.CENTER);
            panel.add(updateButton, BorderLayout.SOUTH);

            add(panel, BorderLayout.CENTER);
            setBorder(BorderFactory.createEmptyBorder(50, 50, 100, 50));

            updateButton.setBackground(Color.decode("#45a247"));
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setEnabled(false);
                    Admin_Executive_Interface.Admin_Executive_Profile_Panel.UpdateInfo_Frame updateInfo = null;
                    try {
                        updateInfo = new Admin_Executive_Interface.Admin_Executive_Profile_Panel.UpdateInfo_Frame(adminExecutive);
                        updateInfo.setLocationRelativeTo(null);
                        updateInfo.setVisible(true);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }

        public class UpdateInfo_Frame extends JFrame {
            private final Font labelFont = new Font("sansserif", Font.BOLD, 14);
            private Admin_Executive_Function.Button updateButton;
            private Admin_Executive_Function.Button cancelButton;

            public UpdateInfo_Frame(Admin_Executive_Function.Admin_Executive adminExecutive) throws ParseException {
                setUndecorated(true);
                setShape(new RoundRectangle2D.Double(0, 0, 1186 / 2, 621 / 2, 15, 15));
                setPreferredSize(new Dimension(1186 / 2, 621 / 2));

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(6, 2, 20, 10));

                JLabel executive_ID_Label = new JLabel("Executive ID");
                executive_ID_Label.setFont(labelFont);
                JLabel name_Label = new JLabel("Name");
                name_Label.setFont(labelFont);
                JLabel gender_Label = new JLabel("Gender");
                gender_Label.setFont(labelFont);
                JLabel contact_Number_Label = new JLabel("Contact Number");
                contact_Number_Label.setFont(labelFont);
                JLabel position_Label = new JLabel("Position");
                position_Label.setFont(labelFont);

                MaskFormatter id_Mask = new MaskFormatter("AD##");
                JFormattedTextField executive_ID_TextField = new JFormattedTextField(id_Mask);
                executive_ID_TextField.setText(adminExecutive.getExecutiveID());
                executive_ID_TextField.setEditable(false);
                JTextField name_TextField = new JTextField(adminExecutive.getName());
                panel.add(executive_ID_Label);
                panel.add(executive_ID_TextField);
                panel.add(name_Label);
                panel.add(name_TextField);
                panel.add(gender_Label);

                JPanel panel1 = new JPanel();
                panel1.setLayout(new GridLayout(2, 1));
                panel1.setBackground(Color.white);

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

                panel.add(position_Label);
                JTextField positionTextField = new JTextField(adminExecutive.getPosition());
                positionTextField.setEditable(false);
                panel.add(positionTextField);

                updateButton = new Admin_Executive_Function.Button("Update");
                cancelButton = new Admin_Executive_Function.Button("Cancel");
                updateButton.color1 = Color.decode("#654ea3");
                updateButton.color2 = Color.decode("#eaafc8");
                panel.add(updateButton);
                panel.add(cancelButton);

                if (adminExecutive.getGender() == 'F') {
                    femaleButton.setSelected(true);
                } else {
                    maleButton.setSelected(true);
                }
                contact_FormattedTextField.setText(adminExecutive.getContact_Number());

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
                        if (login.check_Punctuation(executive_ID_TextField.getText()) || login.check_Punctuation(name_TextField.getText())) {
                            JOptionPane.showMessageDialog(null, "Username and Name cannot include punctuation", "Punctuation error", JOptionPane.ERROR_MESSAGE, Admin_Executive_Function.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 60, 60));
                            check = false;
                        } else {
                            try {
                                if (!login.check_Username_Availability(executive_ID_TextField.getText())) {
                                    JOptionPane.showMessageDialog(null, "Username not registered", "Username registration error", JOptionPane.ERROR_MESSAGE, Admin_Executive_Function.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 60, 60));
                                    check = false;
                                }
                            } catch (IOException | ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        if (check) {
                            char gender;
                            if (maleButton.isSelected()) {
                                gender = 'M';
                            } else {
                                gender = 'F';
                            }
                            Admin_Executive_Function.Admin_Executive aDUpdated = new Admin_Executive_Function.Admin_Executive(executive_ID_TextField.getText(), name_TextField.getText(), gender, contact_FormattedTextField.getText());
                            try {
                                aDUpdated.update_Admin_Executive_Info(aDUpdated, adminExecutive.getExecutiveID());
                                JOptionPane.showMessageDialog(null, "Information Updated", "Information Update", JOptionPane.INFORMATION_MESSAGE, Admin_Executive_Function.toIcon(new ImageIcon("src/UIPackage/Icon/success.png"), 60, 60));
                                dispose();
                                Admin_Executive_Interface adminExecutiveInterface = new Admin_Executive_Interface(executiveID);
                                adminExecutiveInterface.frame.setVisible(true);
                                frame.dispose();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                });

                panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
                setContentPane(panel);
                pack();
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Admin_Executive_Interface adminExecutiveInterface = new Admin_Executive_Interface("AD01");
        adminExecutiveInterface.frame.setVisible(true);
    }
}
