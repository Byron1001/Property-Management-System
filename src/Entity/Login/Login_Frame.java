package Entity.Login;

import Entity.Employee.SecurityGuard.SecurityGuard;
import Entity.Employee.SecurityGuard.SecurityGuard_Incident;
import Entity.Employee.SecurityGuard.SecurityGuard_Visitor_Entry_Record;
import Entity.Resident.Resident;
import Entity.Resident.Resident_Visitor_Pass;
import Entity.Visitor_Pass;
import UIPackage.Component.Header;
import UIPackage.Component.Menu;
import UIPackage.Event.EventMenuSelected;
import UIPackage.Form.Form_Home;
import UIPackage.Model.Model_Menu;
import UIPackage.main.UIFrame;
import UIPackage.swing.PanelBorder;
import UIPackage.swing.Table;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;

public class Login_Frame extends JFrame {
    public Header header = new Header();
    public Color backgroundColor = Color.WHITE;
    public int iconSize = 32;
    public int fieldLength = 200;
    public ImagePanel contentPanel = new ImagePanel("kayamas.jpg");
    private Login login = new Login();
    public Login_Frame() throws IOException {
        contentPanel.setLayout(new GridLayout(1,2));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0.000000001;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        Font titleFont = new Font("sansserif", Font.BOLD, 28);
        JLabel titleLabel = new JLabel("User Login");
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
        Login.Button loginButton = new Login.Button("Login");
        Login.Button registerButton = new Login.Button("Register");
        Login.Button viewVisitorPassButton = new Login.Button("View Visitor Pass");

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
        centerPanel.add(loginButton, constraints);
        constraints.gridy++;
        centerPanel.add(registerButton, constraints);
        constraints.gridy++;
        centerPanel.add(viewVisitorPassButton, constraints);

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

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = "";
                for (char x : passwordField.getPassword()){
                    password += Character.toString(x);
                }
                try {
                    boolean check = login.check_Login_Availability(username);
                    if (check){
                        login = login.search_Login_Information(username);
                        if (login.getPassword().equals(password)){
                            login.open_Interface(username);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Password error", "Password error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Username error", "Username error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Register().setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        viewVisitorPassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = JOptionPane.showInputDialog("Please enter your visitor Pass ID:");
                    id = id.toUpperCase();
                    if (new Visitor_Pass().check_Visitor_Pass_Availability(id)){
                        Visitor_Pass visitorPass = new Visitor_Pass().search_Visitor_Pass_Info(id);
                        new viewFrame(visitorPass).setAlwaysOnTop(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Visitor Pass ID not found", "ID error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        setContentPane(contentPanel);
        new Menu().initMoving(this);
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
        public ImagePanel contentPanel = new ImagePanel("record2.jpg");
        public ProgressFrame() throws IOException {
            contentPanel.setLayout(new BorderLayout());

            Form_Home formHome1 = new Form_Home();
            formHome1.removeAll();
            Form_Home formHome2 = new Form_Home();
            formHome2.removeAll();

            ProgressBar progressBar = new ProgressBar();
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
                new Login_Frame();
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

    private class viewFrame extends JFrame {
        public viewFrame(Visitor_Pass visitorPass) {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel3.setLayout(new GridLayout(3, 1, 15, 15));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");

            JLabel formTitle = new JLabel("VISITOR PASS");
            JLabel issuedBy = new JLabel("Issued by Parhill Residence");
            JLabel[] jLabelLeft = {new JLabel("Visitor Pass ID"), new JLabel("Visitor name"),
                    new JLabel("Resident Username"), new JLabel("Unit ID"),
                    new JLabel("Gender"), new JLabel("Contact Number"),
                    new JLabel("Date Start (MM.dd.yyyyy)"), new JLabel("Date End(MM.dd.yyyy)"), new JLabel("Status")};
            panel2.setLayout(new GridLayout(jLabelLeft.length, 2, 15, 15));
            JLabel[] jLabelRight = {new JLabel(visitorPass.getVisitor_Pass_ID()), new JLabel(visitorPass.getVisitor_Name()),
                    new JLabel(visitorPass.getResident_Username()), new JLabel(visitorPass.getUnitID()),
                    new JLabel(Character.toString(visitorPass.getGender())), new JLabel(visitorPass.getContact_Number()),
                    new JLabel(visitorPass.getDate_Start().format(formatter)), new JLabel(visitorPass.getDate_End().format(formatter)),
                    new JLabel(visitorPass.getStatus())};
            Resident.Button button = new Resident.Button("Close");
            button.setAlignmentX(JButton.CENTER);
            formTitle.setFont(new Font("sansserif", Font.BOLD, 24));
            formTitle.setHorizontalAlignment(JLabel.CENTER);
            issuedBy.setFont(new Font("sansserif", Font.PLAIN, 10));
            issuedBy.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(formTitle, BorderLayout.NORTH);
            for (int i = 0; i < jLabelLeft.length; i++) {
                jLabelLeft[i].setFont(new Font("sansserif", Font.BOLD, 16));
                panel2.add(jLabelLeft[i]);
                panel2.add(jLabelRight[i]);
            }
            panel1.add(panel2, BorderLayout.CENTER);

            panel3.add(issuedBy);
            panel3.add(button);
            panel1.add(panel3, BorderLayout.SOUTH);
            setUndecorated(true);
            panel1.setPreferredSize(new Dimension(1186 / 2, 621));
            panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            setPreferredSize(new Dimension(1186, 621));
            pack();

            setLocationRelativeTo(null);
            setContentPane(panel1);
            setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
            setVisible(true);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new ProgressFrame();
    }
}
