package Entity.Building_Manager;

import Entity.Login.Login;
import Entity.Login.Login_Frame;
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

public class Building_Manager_Interface extends JFrame {
    public UIFrame frame;
    private String buildingManagerID;

    public String getBuildingManagerID() {
        return buildingManagerID;
    }

    public void setBuildingManagerID(String buildingManagerID) {
        this.buildingManagerID = buildingManagerID;
    }

    public void setPanelBorderRight(JPanel panel) {
        frame.panelBorderRight.removeAll();
        frame.panelBorderRight.add(panel);
        frame.panelBorderRight.revalidate();
        frame.repaint();
    }

    public Building_Manager_Interface(String buildingManagerID) throws FileNotFoundException {
        this.buildingManagerID = buildingManagerID;
        frame = new UIFrame(buildingManagerID);
        setPanelBorderRight(new Building_Manager_Interface.Building_Executive_Profile_Panel(buildingManagerID));

        frame.formHome.removeAll();
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("employee", "User Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("report", "Report", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("deposit", "Operation and Budget Planning", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Team Sructure Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#f4791f");
        frame.menu.colorLeft = Color.decode("#659999");

        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException, ClassNotFoundException {
                if (index == 0) {
                } else if (index == 1) {
                    new Building_Manager_User_Management(buildingManagerID).run(buildingManagerID);
                    frame.dispose();
                } else if (index == 2) {
                    new Building_Manager_Report(buildingManagerID);
                    frame.dispose();
                } else if (index == 3) {
                    new Building_Manager_Operation(buildingManagerID).run(buildingManagerID);
                    frame.dispose();
                } else if (index == 4) {
                    new Entity.Building_Manager.Building_Manager_Team_Management(buildingManagerID).run(buildingManagerID);
                    frame.dispose();
                } else if (index == 5) {
                    new Login_Frame();
                    frame.dispose();
                }
            }
        });
    }

    public class Building_Executive_Profile_Panel extends JPanel {
        private final Font bodyFont = new Font("sansserif", Font.PLAIN, 14);
        public Building_Manager_Function.Button updateButton;

        public Building_Executive_Profile_Panel(String buildingManagerID) throws FileNotFoundException {
            setPreferredSize(new Dimension(1000, 500));
            setLayout(new BorderLayout());

            ImageIcon icon = new ImageIcon("src/UIPackage/Icon/profile.png");
            icon = Resident.toIcon(icon, 200, 200);

            JLabel iconLabel = new JLabel(icon);
            add(iconLabel, BorderLayout.NORTH);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout(0, 20));
            JLabel profileLabel = new JLabel("Profile");
            Font titleFont = new Font("Sansserif", Font.BOLD, 24);
            profileLabel.setFont(titleFont);
            profileLabel.setHorizontalAlignment(JLabel.CENTER);
            panel.add(profileLabel, BorderLayout.NORTH);

            JPanel downPanel = new JPanel();
            downPanel.setLayout(new GridLayout(7, 2, 30, 10));
            String[] rowString = {"Building Manager ID", "Name", "Gender", "Contact_number", "Position"};
            Building_Manager_Function.Building_Manager building_Manager = new Building_Manager_Function.Building_Manager().get_Building_Manager_Info(buildingManagerID);
            String[] data = {building_Manager.getBuildingManagerID(), building_Manager.getName(), Character.toString(building_Manager.getGender()), building_Manager.getContact_Number(), building_Manager.getPosition()};
            for (int i = 0; i < rowString.length; i++) {
                JLabel label1 = new JLabel(rowString[i]);
                label1.setHorizontalAlignment(JLabel.RIGHT);
                label1.setFont(new Font("sansserif", Font.BOLD, 14));
                downPanel.add(label1);
                JLabel label2 = new JLabel(data[i]);
                downPanel.add(label2);
            }
            updateButton = new Building_Manager_Function.Button("Update Profile");

            panel.add(downPanel, BorderLayout.CENTER);
            panel.add(updateButton, BorderLayout.SOUTH);

            add(panel, BorderLayout.CENTER);
            setBorder(BorderFactory.createEmptyBorder(50, 50, 100, 50));
            Building_Manager_Function.Building_Manager buildingManager = new Building_Manager_Function.Building_Manager().get_Building_Manager_Info(buildingManagerID);

            updateButton.setBackground(Color.decode("#45a247"));
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setEnabled(false);
                    Building_Manager_Interface.Building_Executive_Profile_Panel.UpdateInfo_Frame updateInfo = null;
                    try {
                        updateInfo = new Building_Manager_Interface.Building_Executive_Profile_Panel.UpdateInfo_Frame(buildingManager);
                        updateInfo.setLocationRelativeTo(null);
                        updateInfo.setVisible(true);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }

        public class UpdateInfo_Frame extends JFrame {
            private Font labelFont = new Font("sansserif", Font.BOLD, 14);
            private Building_Manager_Function.Button updateButton;
            private Building_Manager_Function.Button cancelButton;

            public UpdateInfo_Frame(Building_Manager_Function.Building_Manager building_Manager) throws ParseException {
                setUndecorated(true);
                setShape(new RoundRectangle2D.Double(0, 0, 1186 / 2, 621 / 2, 15, 15));
                setPreferredSize(new Dimension(1186 / 2, 621 / 2));

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(6, 2, 20, 10));

                JLabel executive_ID_Label = new JLabel("Building Manager ID");
                executive_ID_Label.setFont(labelFont);
                JLabel name_Label = new JLabel("Name");
                name_Label.setFont(labelFont);
                JLabel gender_Label = new JLabel("Gender");
                gender_Label.setFont(labelFont);
                JLabel contact_Number_Label = new JLabel("Contact Number");
                contact_Number_Label.setFont(labelFont);
                JLabel position_Label = new JLabel("Position");
                position_Label.setFont(labelFont);

                MaskFormatter id_Mask = new MaskFormatter("BM##");
                JFormattedTextField executive_ID_TextField = new JFormattedTextField(id_Mask);
                executive_ID_TextField.setText(building_Manager.getBuildingManagerID());
                JTextField name_TextField = new JTextField(building_Manager.getName());
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
                JTextField positionTextField = new JTextField(building_Manager.getPosition());
                positionTextField.setEditable(false);
                panel.add(positionTextField);

                updateButton = new Building_Manager_Function.Button("Update");
                cancelButton = new Building_Manager_Function.Button("Cancel");
                updateButton.color1 = Color.decode("#654ea3");
                updateButton.color2 = Color.decode("#eaafc8");
                panel.add(updateButton);
                panel.add(cancelButton);

                if (building_Manager.getGender() == 'F') {
                    femaleButton.setSelected(true);
                } else {
                    maleButton.setSelected(true);
                }
                contact_FormattedTextField.setText(building_Manager.getContact_Number());

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
                        if (check) {
                            char gender;
                            if (maleButton.isSelected()) {
                                gender = 'M';
                            } else {
                                gender = 'F';
                            }
                            Building_Manager_Function.Building_Manager beUpdated = new Building_Manager_Function.Building_Manager(executive_ID_TextField.getText(), name_TextField.getText(), gender, contact_FormattedTextField.getText());
                            try {
                                beUpdated.update_Building_Manager_Info(beUpdated, building_Manager.getBuildingManagerID());
                                dispose();
                                JOptionPane.showMessageDialog(null, "Information Updated", "Information Update", JOptionPane.INFORMATION_MESSAGE, Resident.toIcon(new ImageIcon("src/UIPackage/Icon/success.png"), 60, 60));
                                Building_Manager_Interface Building_ManagerInterface = new Building_Manager_Interface("BM01");
                                Building_ManagerInterface.frame.setVisible(true);
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
        Building_Manager_Interface building_ManagerInterface = new Building_Manager_Interface("BM01");
        building_ManagerInterface.frame.setVisible(true);
    }
}