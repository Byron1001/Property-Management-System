package Entity.Executive.Account_Executive;

import Entity.Login.Login;
import Entity.Resident.Resident;
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

public class Account_Executive_Interface {
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

    public Account_Executive_Interface(String executiveID) throws FileNotFoundException {
        this.executiveID = executiveID;
        frame = new UIFrame(executiveID);

        frame.formHome.removeAll();
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("payment", "Invoice", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("deposit", "Payment", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("paymentHistory", "Receipt", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("statement", "Statement", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Outstanding fees", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#ad5389");
        frame.menu.colorLeft = Color.decode("#3c1053");

        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws FileNotFoundException {
                if (index == 0) {
                } else if (index == 1) {

                    frame.dispose();
                } else if (index == 2) {

                    frame.dispose();
                } else if (index == 3) {
                } else if (index == 4) {
                } else if (index == 5) {
                } else if (index == 6) {
                } else if (index == 7) {
                }
            }
        });
    }

    public static class Account_Executive_Profile_Panel extends JPanel {
        private final Font bodyFont = new Font("sansserif", Font.PLAIN, 14);
        public Account_Executive_Function.AcExButton updateButton;
        public Account_Executive_Profile_Panel(String executiveID) throws FileNotFoundException {
            setPreferredSize(new Dimension(1000, 500));
            setLayout(new BorderLayout());

            ImageIcon icon = new ImageIcon("src/UIPackage/Icon/profile.png");
            icon = Resident.toIcon(icon, 200, 200);

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
            String[] rowString = {"Executive ID", "Name", "Gender", "Contact_number", "Position"};
            Account_Executive_Function.Account_Executive accountExecutive = new Account_Executive_Function.Account_Executive().get_Account_Executive_Info(executiveID);
            String[] data = {accountExecutive.getExecutiveID(), accountExecutive.getName(), Character.toString(accountExecutive.getGender()), accountExecutive.getContact_Number(), accountExecutive.getPosition()};
            for (int i = 0;i < rowString.length;i++){
                JLabel label1 = new JLabel(rowString[i]);
                label1.setHorizontalAlignment(JLabel.RIGHT);
                label1.setFont(new Font("sansserif", Font.BOLD, 14));
                downPanel.add(label1);
                JLabel label2 = new JLabel(data[i]);
                downPanel.add(label2);
            }
            updateButton = new Account_Executive_Function.AcExButton("Update Profile");

            panel.add(downPanel, BorderLayout.CENTER);
            panel.add(updateButton, BorderLayout.SOUTH);

            add(panel, BorderLayout.CENTER);
            setBorder(BorderFactory.createEmptyBorder(50,50,100,50));

            updateButton.setBackground(Color.decode("#45a247"));
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setEnabled(false);
                    UpdateInfo_Frame updateInfo = null;
                    try {
                        updateInfo = new UpdateInfo_Frame(accountExecutive);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                    updateInfo.setLocationRelativeTo(null);
                    updateInfo.setVisible(true);
                }
            });
        }

        public static class UpdateInfo_Frame extends JFrame{
            private Font labelFont = new Font("sansserif", Font.BOLD, 14);
            private Account_Executive_Function.AcExButton updateButton;
            private Account_Executive_Function.AcExButton cancelButton;

            public UpdateInfo_Frame(Account_Executive_Function.Account_Executive accountExecutive) throws ParseException {
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

                MaskFormatter id_Mask = new MaskFormatter("AC##");
                JFormattedTextField executive_ID_TextField = new JFormattedTextField(id_Mask);
                executive_ID_TextField.setText(accountExecutive.getExecutiveID());
                JTextField name_TextField = new JTextField(accountExecutive.getName());
                panel.add(executive_ID_Label);
                panel.add(executive_ID_TextField);
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

                panel.add(position_Label);
                JTextField positionTextField = new JTextField(accountExecutive.getPosition());
                positionTextField.setEditable(false);
                panel.add(positionTextField);

                updateButton = new Account_Executive_Function.AcExButton("Update");
                cancelButton = new Account_Executive_Function.AcExButton("Cancel");
                updateButton.color1 = Color.decode("#654ea3");
                updateButton.color2 = Color.decode("#eaafc8");
                panel.add(updateButton);
                panel.add(cancelButton);

                if (accountExecutive.getGender() == 'F'){
                    femaleButton.setSelected(true);
                } else {
                    maleButton.setSelected(true);
                }
                contact_FormattedTextField.setText(accountExecutive.getContact_Number());

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
                        if (login.check_Punctuation(executive_ID_TextField.getText()) || login.check_Punctuation(name_TextField.getText())){
                            JOptionPane.showMessageDialog(null, "Username and Name cannot include punctuation", "Punctuation error", JOptionPane.ERROR_MESSAGE, Resident.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 60, 60));
                            check = false;
                        } else {
                            try {
                                if (login.check_Username_Availability(executive_ID_TextField.getText())) {
                                    JOptionPane.showMessageDialog(null, "Username already registered", "Username registration error", JOptionPane.ERROR_MESSAGE, Resident.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 60, 60));
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
                            Account_Executive_Function.Account_Executive acUpdated = new Account_Executive_Function.Account_Executive(executive_ID_TextField.getText(), name_TextField.getText(), gender, contact_FormattedTextField.getText());
                            try {
                                acUpdated.update_Account_Executive_Info(acUpdated, accountExecutive.getExecutiveID());
                                JOptionPane.showMessageDialog(null, "Information Updated", "Information Update", JOptionPane.INFORMATION_MESSAGE, Resident.toIcon(new ImageIcon("src/UIPackage/Icon/success.png"), 60, 60));
                                dispose();
                            } catch (IOException ex) {
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
        Account_Executive_Interface accountExecutiveInterface = new Account_Executive_Interface("AC01");
        accountExecutiveInterface.setPanelBorderRight(new Account_Executive_Profile_Panel(accountExecutiveInterface.getExecutiveID()));
        accountExecutiveInterface.frame.setVisible(true);
    }
}
