package Entity.Login;

import UIPackage.Component.Header;
import UIPackage.Component.Menu;
import UIPackage.Form.Form_Home;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                    boolean check = login.check_Login_Availability(username);
                    if (check){
                        check = login.login(newLogin);
                        if (check){
                            check = login.checkUsername_Registration_Availability(newLogin.getUsername());
                            if (check){
                                JOptionPane.showMessageDialog(null,"Registration success.Please login using your credentials", "Registration success", JOptionPane.INFORMATION_MESSAGE);
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(null,"Username already been registered.Please login using your credentials", "Registration finish", JOptionPane.INFORMATION_MESSAGE);
                                dispose();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Punctuation is not allowed", "Punctuation error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Username not found", "Username error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
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
            Image backgroundImage = null;
            try {
                backgroundImage = ImageIO.read(new File("src/UIPackage/Icon/"+imagePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            g.drawImage(backgroundImage, 0, 0, 1186, 621, this);
        }

    }

    public static class ProgressFrame extends JFrame{
        public Register.ImagePanel contentPanel = new Register.ImagePanel("record2.jpg");
        public ProgressFrame() throws IOException {
            contentPanel.setLayout(new BorderLayout());

            Form_Home formHome1 = new Form_Home();
            formHome1.removeAll();
            Form_Home formHome2 = new Form_Home();
            formHome2.removeAll();

            Register.ProgressFrame.ProgressBar progressBar = new Register.ProgressFrame.ProgressBar();
            contentPanel.add(progressBar, BorderLayout.SOUTH);
            contentPanel.add(formHome1, BorderLayout.NORTH);
            contentPanel.add(formHome2, BorderLayout.CENTER);

            setContentPane(contentPanel);
            new Menu().initMoving(this);
            setUndecorated(true);
            setSize(new Dimension(1186, 621));
            setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
            setLocationRelativeTo(null);
            setVisible(true);

            progressBar.setValue(100);
            int i = 0;
            try {
                while (i <= 100) {
                    progressBar.setValue(i + 10);
                    Thread.sleep(140);
                    i += 10;
                }
                dispose();
                new Register();
            }
            catch (Exception ignored) {
            }
        }

        public static class ProgressBar extends JProgressBar{
            public Color color1 = Color.decode("#283c86");
            public Color color2 = Color.decode("#45a247");
            public ProgressBar(){
                setStringPainted(true);
            }
            @Override
            protected void paintComponent(Graphics g) {
//                Graphics2D g2 = (Graphics2D) g;
//                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                GradientPaint gradientPaint = new GradientPaint(0, 0, color1,getWidth(), 0, color2);
//                g2.setPaint(gradientPaint);
//                g2.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Register.ProgressFrame();
    }
}
