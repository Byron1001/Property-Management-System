package Entity.Resident;

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

public class Resident_Interface {
    public UIFrame frame;
    private String resident_Username;

    public String getResident_Username() {
        return resident_Username;
    }

    public void setResident_Username(String resident_Username) {
        this.resident_Username = resident_Username;
    }

    public void setPanelBorderRight(JPanel panel){
        frame.panelBorderRight.removeAll();
        frame.panelBorderRight.add(panel);
        frame.panelBorderRight.revalidate();
        frame.repaint();
    }
    public Resident_Interface(String resident_Username) throws FileNotFoundException {
        this.resident_Username = resident_Username;
        frame = new UIFrame(resident_Username);
        setPanelBorderRight(new Resident_Profile_Panel(resident_Username));

        frame.formHome.removeAll();
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
                } else if (index == 1){
                    new Resident_Payment_Frame(resident_Username).run();
                    frame.dispose();
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
    }

    public class Resident_Profile_Panel extends JPanel {
        public Resident.Button updateButton;
        public Resident_Profile_Panel(String resident_Username) throws FileNotFoundException {
            setPreferredSize(new Dimension(1000, 500));
            setLayout(new BorderLayout());

            ImageIcon icon = new ImageIcon("src/UIPackage/Icon/profile.png");
            icon = Resident.toIcon(icon, 200, 200);

            JLabel iconLabel = new JLabel(icon);
            add(iconLabel, BorderLayout.NORTH);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout(0,20));
            JLabel profileLabel = new JLabel("Profile");
            Font titleFont = new Font("sansserif", Font.BOLD, 24);
            profileLabel.setFont(titleFont);
            profileLabel.setHorizontalAlignment(JLabel.CENTER);
            panel.add(profileLabel, BorderLayout.NORTH);

            JPanel downPanel = new JPanel();
            downPanel.setLayout(new GridLayout(7, 2, 30, 10));
            String[] rowString = {"Resident Username", "Name", "Gender", "Contact_Number", "unit ID", "Payment pending"};
            Resident resident = new Resident().get_Resident_Info(resident_Username);
            String[] data = {resident.getResident_Username(), resident.getName(), Character.toString(resident.getGender()), resident.getContact_Number(), resident.getUnitID(), Integer.toString(resident.getPayment())};
            for (int i = 0;i < rowString.length;i++){
                JLabel label1 = new JLabel(rowString[i]);
                label1.setHorizontalAlignment(JLabel.RIGHT);
                label1.setFont(new Font("sansserif", Font.BOLD, 14));
                downPanel.add(label1);
                JLabel label2 = new JLabel(data[i]);
                downPanel.add(label2);
            }
            updateButton = new Resident.Button("Update Profile");

            panel.add(downPanel, BorderLayout.CENTER);
            panel.add(updateButton, BorderLayout.SOUTH);

            add(panel, BorderLayout.CENTER);
            setBorder(BorderFactory.createEmptyBorder(50,50,100,50));

            updateButton.setBackground(Color.decode("#45a247"));
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setEnabled(false);
                    UpdateInfo updateInfo;
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
            private final Font labelFont = new Font("sansserif", Font.BOLD, 14);
            private Resident.Button updateButton;
            private Resident.Button cancelButton;

            public UpdateInfo(String[] data) throws ParseException {
                setUndecorated(true);
                setShape(new RoundRectangle2D.Double(0, 0, 1186 / 2, 621 / 2, 15, 15));
                setPreferredSize(new Dimension(1186 / 2, 621 / 2));

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(5, 2, 20, 10));

                JLabel username_Label = new JLabel("Resident Username");
                username_Label.setFont(labelFont);
                JLabel name_Label = new JLabel("Name");
                name_Label.setFont(labelFont);
                JLabel gender_Label = new JLabel("Gender");
                gender_Label.setFont(labelFont);
                JLabel contact_Number_Label = new JLabel("Contact Number");
                contact_Number_Label.setFont(labelFont);

                JTextField resident_Username_TextField = new JTextField();
                JTextField name_TextField = new JTextField();
                panel.add(username_Label);
                panel.add(resident_Username_TextField);
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

                updateButton = new Resident.Button("Update");
                cancelButton = new Resident.Button("Cancel");
                updateButton.color1 = Color.decode("#654ea3");
                updateButton.color2 = Color.decode("#eaafc8");
                panel.add(updateButton);
                panel.add(cancelButton);

                resident_Username_TextField.setText(data[0]);
                name_TextField.setText(data[1]);
                name_TextField.setEditable(false);
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
                        if (login.check_Punctuation(resident_Username_TextField.getText()) || login.check_Punctuation(name_TextField.getText())){
                            JOptionPane.showMessageDialog(null, "Username and Name cannot include punctuation", "Punctuation error", JOptionPane.ERROR_MESSAGE, Resident.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 60, 60));
                            check = false;
                        } else {
                            try {
                                if (!login.check_Username_Availability(resident_Username_TextField.getText())) {
                                    JOptionPane.showMessageDialog(null, "Username not registered", "Username registration error", JOptionPane.ERROR_MESSAGE, Resident.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 60, 60));
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
                            Resident residentUpdated = new Resident(resident_Username_TextField.getText(), name_TextField.getText(), gender, contact_FormattedTextField.getText(), data[4], Integer.parseInt(data[5]));
                            try {
                                residentUpdated.update_Resident_Info(residentUpdated, ori_Username);
                                JOptionPane.showMessageDialog(null, "Information Updated", "Information Update", JOptionPane.INFORMATION_MESSAGE, Resident.toIcon(new ImageIcon("src/UIPackage/Icon/success.png"), 60, 60));
                                Resident_Interface residentInterface = new Resident_Interface(resident_Username);
                                residentInterface.frame.setVisible(true);
                                dispose();
                                frame.dispose();
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
        Resident_Interface residentInterface = new Resident_Interface("Mike1001");
        residentInterface.frame.setVisible(true);
    }
}
