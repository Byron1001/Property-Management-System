package Entity.Employee.SecurityGuard;

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
import java.io.IOException;
import java.text.ParseException;

public class SecurityGuard_Interface extends JFrame {
    public UIFrame frame;
    private String employeeID;

    public String getEmployeeID() {
        return employeeID;
    }

    public void setPanelBorderRight(JPanel panel) {
        frame.panelBorderRight.removeAll();
        frame.panelBorderRight.add(panel);
        frame.panelBorderRight.revalidate();
        frame.repaint();
    }

    public SecurityGuard_Interface(String employeeID) throws IOException, ClassNotFoundException {
        this.employeeID = employeeID;
        frame = new UIFrame(employeeID);
        setPanelBorderRight(new SecurityGuard_Interface.SecurityGuard_Profile_Panel(employeeID));

        frame.formHome.removeAll();
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Visitor Pass Check", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("entry", "Visitor Entry", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("checkpoint", "CheckPoint", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("report", "Incident", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#4286f4");
        frame.menu.colorLeft = Color.decode("#373B44");

        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException, ClassNotFoundException {
                if (index == 0) {
                } else if (index == 1) {
                    new Entity.Employee.SecurityGuard.SecurityGuard_Pass_Check(employeeID).run(employeeID);
                    frame.dispose();
                } else if (index == 2) {
                    new SecurityGuard_Visitor_Entry_Record(employeeID).run(employeeID);
                    frame.dispose();
                } else if (index == 3) {
                    new Entity.Employee.SecurityGuard.SecurityGuard_CheckPoint(employeeID).run(employeeID);
                    frame.dispose();
                } else if (index == 4) {
                    new SecurityGuard_Incident(employeeID).run(employeeID);
                    frame.dispose();
                } else if (index == 5) {
                    new Login_Frame();
                    frame.dispose();
                }
            }
        });
    }

    public class SecurityGuard_Profile_Panel extends JPanel {
        public SecurityGuard.Button updateButton;
        public SecurityGuard_Profile_Panel(String securityGuard_EmployeeID) throws IOException, ClassNotFoundException {
            setPreferredSize(new Dimension(1000, 500));
            setLayout(new BorderLayout());

            ImageIcon icon = new ImageIcon("src/UIPackage/Icon/profile.png");
            icon = SecurityGuard.toIcon(icon, 200, 200);

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
            String[] rowString = {"SecurityGuard Employee ID", "Name", "Gender", "Contact_Number", "Salary", "Position"};
            SecurityGuard securityGuard = new SecurityGuard().get_Security_Guard_Info(securityGuard_EmployeeID);
            String[] data = {securityGuard.getEmployeeID(), securityGuard.getName(), Character.toString(securityGuard.getGender()), securityGuard.getContact_Number(), Integer.toString(securityGuard.getSalary()), securityGuard.getPosition_Name()};
            for (int i = 0;i < rowString.length;i++){
                JLabel label1 = new JLabel(rowString[i]);
                label1.setHorizontalAlignment(JLabel.RIGHT);
                label1.setFont(new Font("sansserif", Font.BOLD, 14));
                downPanel.add(label1);
                JLabel label2 = new JLabel(data[i]);
                downPanel.add(label2);
            }
            updateButton = new SecurityGuard.Button("Update Profile");

            panel.add(downPanel, BorderLayout.CENTER);
            panel.add(updateButton, BorderLayout.SOUTH);

            add(panel, BorderLayout.CENTER);
            setBorder(BorderFactory.createEmptyBorder(50,50,100,50));

            updateButton.setBackground(Color.decode("#45a247"));
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setEnabled(false);
                    SecurityGuard_Interface.SecurityGuard_Profile_Panel.UpdateInfo updateInfo;
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
            private SecurityGuard.Button updateButton;
            private SecurityGuard.Button cancelButton;

            public UpdateInfo(String[] data) throws ParseException {
                setUndecorated(true);
                setShape(new RoundRectangle2D.Double(0, 0, 1186 / 2, 621 / 2, 15, 15));
                setPreferredSize(new Dimension(1186 / 2, 621 / 2));

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(5, 2, 20, 10));

                JLabel username_Label = new JLabel("SecurityGuard Username");
                username_Label.setFont(labelFont);
                JLabel name_Label = new JLabel("Name");
                name_Label.setFont(labelFont);
                JLabel gender_Label = new JLabel("Gender");
                gender_Label.setFont(labelFont);
                JLabel contact_Number_Label = new JLabel("Contact Number");
                contact_Number_Label.setFont(labelFont);

                JTextField securityGuard_EmployeeID_TextField = new JTextField();
                JTextField name_TextField = new JTextField();
                panel.add(username_Label);
                panel.add(securityGuard_EmployeeID_TextField);
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

                updateButton = new SecurityGuard.Button("Update");
                cancelButton = new SecurityGuard.Button("Cancel");
                updateButton.color1 = Color.decode("#654ea3");
                updateButton.color2 = Color.decode("#eaafc8");
                panel.add(updateButton);
                panel.add(cancelButton);

                securityGuard_EmployeeID_TextField.setText(data[0]);
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
                        if (login.check_Punctuation(securityGuard_EmployeeID_TextField.getText()) || login.check_Punctuation(name_TextField.getText())){
                            JOptionPane.showMessageDialog(null, "Username and Name cannot include punctuation", "Punctuation error", JOptionPane.ERROR_MESSAGE, SecurityGuard.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 60, 60));
                            check = false;
                        } else {
                            try {
                                if (login.check_Username_Availability(securityGuard_EmployeeID_TextField.getText())) {
                                    JOptionPane.showMessageDialog(null, "Username already registered", "Username registration error", JOptionPane.ERROR_MESSAGE, SecurityGuard.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 60, 60));
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
                            SecurityGuard securityGuardUpdated;
                            try {
                                securityGuardUpdated = new SecurityGuard(securityGuard_EmployeeID_TextField.getText(), name_TextField.getText(), gender, contact_FormattedTextField.getText(), Integer.parseInt(data[4]));
                                try {
                                    securityGuardUpdated.update_SecurityGuard_Info(securityGuardUpdated, ori_Username);
                                    JOptionPane.showMessageDialog(null, "Information Updated", "Information Update", JOptionPane.INFORMATION_MESSAGE, SecurityGuard.toIcon(new ImageIcon("src/UIPackage/Icon/success.png"), 60, 60));
                                    dispose();
                                    Entity.Employee.SecurityGuard.SecurityGuard_Interface securityGuardInterface = new Entity.Employee.SecurityGuard.SecurityGuard_Interface(employeeID);
                                    securityGuardInterface.frame.setVisible(true);
                                    frame.dispose();
                                } catch (IOException | ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }
                            } catch (IOException | ClassNotFoundException ex) {
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

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Entity.Employee.SecurityGuard.SecurityGuard_Interface securityGuardInterface = new Entity.Employee.SecurityGuard.SecurityGuard_Interface("SG001");
        securityGuardInterface.frame.setVisible(true);
    }
}
