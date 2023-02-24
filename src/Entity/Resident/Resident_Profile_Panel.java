package Entity.Resident;

import Entity.Login.Login;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class Resident_Profile_Panel extends JPanel {
    private final Font bodyFont = new Font("sansserif", Font.PLAIN, 14);
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
        Font titleFont = new Font("Sansserif", Font.BOLD, 24);
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

    public static class UpdateInfo extends JFrame{
        private Font labelFont = new Font("sansserif", Font.BOLD, 14);
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
                            if (login.check_Username_Availability(resident_Username_TextField.getText())) {
                                JOptionPane.showMessageDialog(null, "Username already registered", "Username registration error", JOptionPane.ERROR_MESSAGE, Resident.toIcon(new ImageIcon("src/UIPackage/Icon/error.png"), 60, 60));
                                check = false;
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if (check == true){
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
