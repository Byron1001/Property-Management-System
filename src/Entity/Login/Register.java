package Entity.Login;

import UIPackage.Component.Header;
import UIPackage.Form.Form_Home;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;

public class Register extends JFrame {
    public Header header = new Header();
    public Color backgroundColor = Color.WHITE;
    public int iconSize = 32;
    public int fieldLength = 200;
    public Register.ImagePanel contentPanel = new Register.ImagePanel("kayamas.jpg");
    private Login login = new Login();
    public Register() throws IOException {
        contentPanel.setLayout(new GridLayout(1,2));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0.000000001;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        Font titleFont = new Font("sansserif", Font.BOLD, 28);
        JLabel titleLabel = new JLabel("User Register");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        JTextField usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createEmptyBorder());
        usernameField.setPreferredSize(new Dimension(fieldLength, iconSize));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        passwordField.setPreferredSize(new Dimension(fieldLength, iconSize));
        JLabel icon1 = new JLabel();
        icon1.setIcon(Login.toIcon(iconSize,iconSize, "avatar.png"));
        JLabel icon2 = new JLabel();
        icon2.setIcon(Login.toIcon(iconSize,iconSize, "password.png"));
        Login.Button registerButton = new Login.Button("Register");
        Login.Button cancelButton = new Login.Button("Cancel");

        usernameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    passwordField.requestFocus();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                registerButton.doClick();
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        Form_Home formHome1 = new Form_Home();
        formHome1.removeAll();
        Form_Home formHome2 = new Form_Home();
        formHome2.removeAll();

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(0,0,0,0));
        centerPanel.setOpaque(true);
        centerPanel.add(titleLabel, constraints);
        constraints.gridy++;
        centerPanel.add(icon1, constraints);
        constraints.gridx++;
        centerPanel.add(usernameField, constraints);
        constraints.gridy++;
        constraints.gridx--;
        centerPanel.add(icon2, constraints);
        constraints.gridx++;
        centerPanel.add(passwordField, constraints);
        constraints.gridy++;
        constraints.gridx--;
        constraints.gridwidth = 2;
        centerPanel.add(new JSeparator(), constraints);
        constraints.gridy++;
        centerPanel.add(registerButton, constraints);
        constraints.gridy++;
        centerPanel.add(cancelButton, constraints);

        JPanel panelLeft = new JPanel();
        panelLeft.setBackground(new Color(255,255,255,0));
        panelLeft.setPreferredSize(new Dimension(1186/2, 621));
        JPanel panelRight = new JPanel();
        panelRight.setPreferredSize(new Dimension(1186/2, 621));
        panelRight.setBackground(new Color(0,0,0, 100));
        panelRight.setLayout(new BorderLayout());
        panelRight.add(centerPanel, BorderLayout.CENTER);
        panelRight.add(formHome1, BorderLayout.NORTH);
        panelRight.add(formHome2, BorderLayout.SOUTH);

        contentPanel.add(panelLeft);
        contentPanel.add(panelRight);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = "";
                for (char x : passwordField.getPassword()){
                    password += Character.toString(x);
                }
                Login newLogin = new Login(username, password);
                try {
                    boolean check = login.check_Punctuation(username) && login.check_Punctuation(password);
                    if (!check){
                        check = login.checkUsername_Registration_Availability(newLogin.getUsername());
                        if (!check){
                            if (login.check_Username_Availability(username)){
                                login.register(newLogin);
                                JOptionPane.showMessageDialog(null,"Registration success.Please login using your credentials", "Registration success", JOptionPane.INFORMATION_MESSAGE);
                                new Login_Frame();
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Username is not registered by admin.Please ask admin to help.", "Username not found", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,"Username already been registered.Please login using your credentials", "Registration finish", JOptionPane.INFORMATION_MESSAGE);
                            new Login_Frame();
                            dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Punctuation is not allowed", "Punctuation error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Login_Frame();
                    dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        setContentPane(contentPanel);
        new UIPackage.Component.Menu().initMoving(this);
        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static class ImagePanel extends JPanel {
        private String imagePath;
        public ImagePanel(String imagePath) throws IOException {
            this.imagePath = imagePath;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1186, 621);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image backgroundImage;
            try {
                backgroundImage = ImageIO.read(new File("src/UIPackage/Icon/"+imagePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            g.drawImage(backgroundImage, 0, 0, 1186, 621, this);
        }

    }

    public static class Forgot_Password extends JFrame{
        public Header header = new Header();
        public Color backgroundColor = Color.WHITE;
        public int iconSize = 32;
        public int fieldLength = 200;
        public Register.ImagePanel contentPanel = new Register.ImagePanel("kayamas.jpg");
        private Login login = new Login();
        public Forgot_Password(String username) throws IOException {
            contentPanel.setLayout(new GridLayout(1,2));

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.weighty = 0.000000001;
            constraints.fill = GridBagConstraints.HORIZONTAL;

            Font titleFont = new Font("sansserif", Font.BOLD, 28);
            JLabel titleLabel = new JLabel("User Password Change");
            titleLabel.setFont(titleFont);
            titleLabel.setForeground(Color.WHITE);
            JTextField usernameField = new JTextField();
            usernameField.setText(username);
            usernameField.setEditable(false);
            usernameField.setBorder(BorderFactory.createEmptyBorder());
            usernameField.setPreferredSize(new Dimension(fieldLength, iconSize));
            JPasswordField passwordField = new JPasswordField();
            passwordField.setBorder(BorderFactory.createEmptyBorder());
            passwordField.setPreferredSize(new Dimension(fieldLength, iconSize));
            JLabel icon1 = new JLabel();
            icon1.setIcon(Login.toIcon(iconSize,iconSize, "avatar.png"));
            JLabel icon2 = new JLabel();
            icon2.setIcon(Login.toIcon(iconSize,iconSize, "password.png"));
            Login.Button changeButton = new Login.Button("Change Password");
            Login.Button cancelButton = new Login.Button("Cancel");

            Form_Home formHome1 = new Form_Home();
            formHome1.removeAll();
            Form_Home formHome2 = new Form_Home();
            formHome2.removeAll();

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new GridBagLayout());
            centerPanel.setBackground(new Color(0,0,0,0));
            centerPanel.setOpaque(true);
            centerPanel.add(titleLabel, constraints);
            constraints.gridy++;
            centerPanel.add(icon1, constraints);
            constraints.gridx++;
            centerPanel.add(usernameField, constraints);
            constraints.gridy++;
            constraints.gridx--;
            centerPanel.add(icon2, constraints);
            constraints.gridx++;
            centerPanel.add(passwordField, constraints);
            constraints.gridy++;
            constraints.gridx--;
            constraints.gridwidth = 2;
            centerPanel.add(new JSeparator(), constraints);
            constraints.gridy++;
            centerPanel.add(changeButton, constraints);
            constraints.gridy++;
            centerPanel.add(cancelButton, constraints);

            JPanel panelLeft = new JPanel();
            panelLeft.setBackground(new Color(255,255,255,0));
            panelLeft.setPreferredSize(new Dimension(1186/2, 621));
            JPanel panelRight = new JPanel();
            panelRight.setPreferredSize(new Dimension(1186/2, 621));
            panelRight.setBackground(new Color(0,0,0, 100));
            panelRight.setLayout(new BorderLayout());
            panelRight.add(centerPanel, BorderLayout.CENTER);
            panelRight.add(formHome1, BorderLayout.NORTH);
            panelRight.add(formHome2, BorderLayout.SOUTH);

            contentPanel.add(panelLeft);
            contentPanel.add(panelRight);

            changeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText();
                    String password = "";
                    for (char x : passwordField.getPassword()){
                        password += Character.toString(x);
                    }
                    Login newLogin = new Login(username, password);
                    try {
                        boolean check = login.check_Punctuation(username) && login.check_Punctuation(password) && login.check_Username_Availability(username);
                        if (!check){
                            check = login.checkUsername_Registration_Availability(newLogin.getUsername());
                            if (check){
                                login.modify_Login(newLogin);
                                JOptionPane.showMessageDialog(null,"Password change success.Please login using your new credentials", "Modify success", JOptionPane.INFORMATION_MESSAGE);
                                new Login_Frame();
                            } else {
                                JOptionPane.showMessageDialog(null,"Username has not been registered.Please make registration.", "Modify finish", JOptionPane.INFORMATION_MESSAGE);
                                new Register();
                            }
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Punctuation is not allowed", "Punctuation error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }


                }
            });

            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Login_Frame();
                        dispose();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            setContentPane(contentPanel);
            new UIPackage.Component.Menu().initMoving(this);
            setUndecorated(true);
            setSize(new Dimension(1186, 621));
            setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        new Register();
        new Forgot_Password("Mike1001");
    }
}
