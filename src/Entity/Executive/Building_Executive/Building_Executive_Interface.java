package Entity.Executive.Building_Executive;

import Entity.CheckPoint;
import Entity.Employee.SecurityGuard.SecurityGuard;
import Entity.Login.Login;
import Entity.Login.Login_Frame;
import Entity.Resident.Resident;
import UIPackage.Event.EventMenuSelected;
import UIPackage.Form.Form_Home;
import UIPackage.Model.Model_Menu;
import UIPackage.main.UIFrame;
import UIPackage.swing.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Building_Executive_Interface {
    public UIFrame frame;
    private String executiveID;

    public String getExecutiveID() {
        return executiveID;
    }

    public void setExecutiveID(String executiveID) {
        this.executiveID = executiveID;
    }

    public void setPanelBorderRight(int num) throws FileNotFoundException {
        frame.panelBorderRight.removeAll();
        if (num == 1){
            frame.panelBorderRight.add(new Building_Executive_Profile_Panel(executiveID));
        } else {
            frame.panelBorderRight.add(new Building_Executive_JobReport_Panel(executiveID));
        }
        frame.panelBorderRight.revalidate();
        frame.repaint();
    }

    public Building_Executive_Interface(String executiveID) throws FileNotFoundException {
        this.executiveID = executiveID;
        frame = new UIFrame(executiveID);

        frame.formHome.removeAll();
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("employee", "Employee Task", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("complaint", "Complaint Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("patrolling", "Patrolling Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("checkpoint", "Checkpoint Management", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("report", "Job Report", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("logout", "Logout", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#DECBA4");
        frame.menu.colorLeft = Color.decode("#3E5151");

        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws IOException, ClassNotFoundException {
                if (index == 0) {
                    run(1);
                } else if (index == 1) {
                    new Building_Executive_Employee_Task(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 2) {
                    new Building_Executive_Complaint_Management(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 3) {
                    new Building_Executive_Patrolling_Management(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 4) {
                    new Building_Executive_CheckPoint(executiveID).run(executiveID);
                    frame.dispose();
                } else if (index == 5) {
                    Entity.Executive.Building_Executive.Building_Executive_Interface building_Executive_Interface = new Entity.Executive.Building_Executive.Building_Executive_Interface(executiveID);
                    building_Executive_Interface.run(2);
                    building_Executive_Interface.frame.setVisible(true);
                    frame.dispose();
                } else if (index == 6) {
                    new Login_Frame();
                    frame.dispose();
                }
            }
        });
    }

    public class Building_Executive_Profile_Panel extends JPanel {
        private final Font bodyFont = new Font("sansserif", Font.PLAIN, 14);
        public Building_Executive_Function.Button updateButton;

        public Building_Executive_Profile_Panel(String executiveID) throws FileNotFoundException {
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
            String[] rowString = {"Executive ID", "Name", "Gender", "Contact_number", "Position"};
            Building_Executive_Function.Building_Executive buildingExecutive = new Building_Executive_Function.Building_Executive().get_Building_Executive_Info(executiveID);
            String[] data = {buildingExecutive.getExecutiveID(), buildingExecutive.getName(), Character.toString(buildingExecutive.getGender()), buildingExecutive.getContact_Number(), buildingExecutive.getPosition()};
            for (int i = 0; i < rowString.length; i++) {
                JLabel label1 = new JLabel(rowString[i]);
                label1.setHorizontalAlignment(JLabel.RIGHT);
                label1.setFont(new Font("sansserif", Font.BOLD, 14));
                downPanel.add(label1);
                JLabel label2 = new JLabel(data[i]);
                downPanel.add(label2);
            }
            updateButton = new Building_Executive_Function.Button("Update Profile");

            panel.add(downPanel, BorderLayout.CENTER);
            panel.add(updateButton, BorderLayout.SOUTH);

            add(panel, BorderLayout.CENTER);
            setBorder(BorderFactory.createEmptyBorder(50, 50, 100, 50));

            updateButton.setBackground(Color.decode("#45a247"));
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setEnabled(false);
                    Entity.Executive.Building_Executive.Building_Executive_Interface.Building_Executive_Profile_Panel.UpdateInfo_Frame updateInfo = null;
                    try {
                        updateInfo = new Entity.Executive.Building_Executive.Building_Executive_Interface.Building_Executive_Profile_Panel.UpdateInfo_Frame(buildingExecutive);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                    updateInfo.setLocationRelativeTo(null);
                    updateInfo.setVisible(true);
                }
            });
        }

        public class UpdateInfo_Frame extends JFrame {
            private Font labelFont = new Font("sansserif", Font.BOLD, 14);
            private Building_Executive_Function.Button updateButton;
            private Building_Executive_Function.Button cancelButton;

            public UpdateInfo_Frame(Building_Executive_Function.Building_Executive buildingExecutive) throws ParseException {
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

                MaskFormatter id_Mask = new MaskFormatter("BE##");
                JFormattedTextField executive_ID_TextField = new JFormattedTextField(id_Mask);
                executive_ID_TextField.setText(buildingExecutive.getExecutiveID());
                JTextField name_TextField = new JTextField(buildingExecutive.getName());
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
                JTextField positionTextField = new JTextField(buildingExecutive.getPosition());
                positionTextField.setEditable(false);
                panel.add(positionTextField);

                updateButton = new Building_Executive_Function.Button("Update");
                cancelButton = new Building_Executive_Function.Button("Cancel");
                updateButton.color1 = Color.decode("#654ea3");
                updateButton.color2 = Color.decode("#eaafc8");
                panel.add(updateButton);
                panel.add(cancelButton);

                if (buildingExecutive.getGender() == 'F') {
                    femaleButton.setSelected(true);
                } else {
                    maleButton.setSelected(true);
                }
                contact_FormattedTextField.setText(buildingExecutive.getContact_Number());

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
                            Building_Executive_Function.Building_Executive bEUpdated = new Building_Executive_Function.Building_Executive(executive_ID_TextField.getText(), name_TextField.getText(), gender, contact_FormattedTextField.getText());
                            try {
                                bEUpdated.update_Building_Executive_Info(bEUpdated, buildingExecutive.getExecutiveID());
                                JOptionPane.showMessageDialog(null, "Information Updated", "Information Update", JOptionPane.INFORMATION_MESSAGE, Resident.toIcon(new ImageIcon("src/UIPackage/Icon/success.png"), 60, 60));
                                Entity.Executive.Building_Executive.Building_Executive_Interface building_Executive_Interface = new Entity.Executive.Building_Executive.Building_Executive_Interface(executiveID);
                                building_Executive_Interface.frame.setVisible(true);
                                frame.dispose();
                                dispose();
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

    public class Building_Executive_JobReport_Panel extends JPanel {
        private final Font bodyFont = new Font("sansserif", Font.PLAIN, 14);
        public Building_Executive_Function.Button updateButton;

        public Building_Executive_JobReport_Panel(String executiveID) throws FileNotFoundException {
            setPreferredSize(new Dimension(1000, 500));
            setLayout(new BorderLayout());

            JPanel imgPanel = new JPanel();
            buttonPanel img3 = null;
            imgPanel.setLayout(new GridLayout(1, 3, 20, 0));
            buttonPanel img1 = new buttonPanel(500, 352, "record1.jpg");
            imgPanel.add(img1);
            buttonPanel img2 = new buttonPanel(500, 352, "record2.jpg");
            imgPanel.add(img2);
            img3 = new buttonPanel(500, 352, "record3.jpg");
            imgPanel.add(img3);

            Building_Executive_Function.Button checkPointButton = new Building_Executive_Function.Button("CheckPoint Record");
            Building_Executive_Function.Button visitorEntryButton = new Building_Executive_Function.Button("Visitor Entry Record");
            Building_Executive_Function.Button incidentButton = new Building_Executive_Function.Button("Incident Report");

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1, 3, 20, 0));
            buttonPanel.add(checkPointButton);
            buttonPanel.add(visitorEntryButton);
            buttonPanel.add(incidentButton);

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BorderLayout());
            centerPanel.setBorder(BorderFactory.createEmptyBorder(0,40,0,40));
            centerPanel.add(imgPanel, BorderLayout.CENTER);
            centerPanel.add(buttonPanel, BorderLayout.SOUTH);

            add(centerPanel, BorderLayout.CENTER);
            Form_Home formHome = new Form_Home();
            formHome.removeAll();
            add(formHome, BorderLayout.SOUTH);

            checkPointButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new CheckPoint_Frame();
                    } catch (ParseException | ClassNotFoundException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            visitorEntryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Visitor_Entry_Frame();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            incidentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Incident_Frame();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

        }

        public static class buttonPanel extends JPanel{
            public buttonPanel(int width, int height, String name){
                setLayout(new BorderLayout());
                JLabel imgLabel = new JLabel();
                imgLabel.setIcon(toIcon(width, height, name));
                add(imgLabel, BorderLayout.CENTER);
            }
            public ImageIcon toIcon(int w, int h, String name){
                ImageIcon imgIcon = new ImageIcon("src/UIPackage/Icon/" + name);
                Image srcImg = imgIcon.getImage();
                BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = resizedImg.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(srcImg, 0, 0, w, h, null);
                g2.dispose();
                return new ImageIcon(resizedImg);
            }
        }

        public static class CheckPoint_Frame extends JFrame {
            private Building_Executive_Function.Button closeButton;
            private JScrollPane scrollPane = new JScrollPane();
            private JPanel contentPane = new JPanel();

            public CheckPoint_Frame() throws ParseException, IOException, ClassNotFoundException {
                Table tableData = new Table();
                JTableHeader header1 = tableData.getTableHeader();
                header1.setReorderingAllowed(false);
                header1.setResizingAllowed(false);
                header1.setDefaultRenderer(new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        if (column != header1.getColumnModel().getColumnCount()) {
                            Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                            com.setBackground(Color.white);
                            setBorder(noFocusBorder);
                            com.setForeground(new Color(102, 102, 102));
                            com.setFont(new Font("sansserif", Font.BOLD, 16));
                            setHorizontalAlignment(JLabel.CENTER);
                            return com;
                        }
                        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    }
                });

                String[] columns = {"Security Guard Employee ID", "CheckPoint ID", "Date", "Time"};
                for (String col : columns){
                    tableData.model.addColumn(col);
                }
                ArrayList<CheckPoint.Record> recordArrayList = new CheckPoint.Record().getArrayList();
                for (CheckPoint.Record record : recordArrayList){
                    tableData.addRow(record.getStringArray(record));
                }
                tableData.statusAllowed = false;
                tableData.preferredColumnWidth = 250;

                Building_Executive_Function.Button closeButton = new Building_Executive_Function.Button("Close");
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });

                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());
                scrollPane.setViewportView(tableData);

                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                panel.setBackground(Color.white);
                panel.add(header1, BorderLayout.NORTH);
                panel.add(scrollPane, BorderLayout.CENTER);
//                panel.add(closeButton, BorderLayout.SOUTH);
                panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
                contentPane.setLayout(new GridLayout(2,1,0,0));
                contentPane.setBackground(Color.white);
                contentPane.add(panel, BorderLayout.CENTER);
                JPanel panelTwo = new JPanel();
                panelTwo.setBackground(Color.white);
                panelTwo.setLayout(new BorderLayout());
                panelTwo.add(closeButton, BorderLayout.NORTH);
                Form_Home home = new Form_Home();
                home.removeAll();
                panelTwo.add(home, BorderLayout.SOUTH);
                contentPane.add(panelTwo, BorderLayout.SOUTH);

                contentPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
                setContentPane(contentPane);
                setUndecorated(true);
                setBackground(Color.WHITE);
                setSize(new Dimension(1186, 621));
                setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
                setLocationRelativeTo(null);
                setVisible(true);
            }
        }

        public static class Visitor_Entry_Frame extends JFrame {
            private Building_Executive_Function.Button closeButton;
            private JScrollPane scrollPane = new JScrollPane();
            private JPanel contentPane = new JPanel();

            public Visitor_Entry_Frame() throws IOException {
                Table tableData = new Table();
                JTableHeader header1 = tableData.getTableHeader();
                header1.setReorderingAllowed(false);
                header1.setResizingAllowed(false);
                header1.setDefaultRenderer(new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        if (column != header1.getColumnModel().getColumnCount()) {
                            Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                            com.setBackground(Color.white);
                            setBorder(noFocusBorder);
                            com.setForeground(new Color(102, 102, 102));
                            com.setFont(new Font("sansserif", Font.BOLD, 16));
                            setHorizontalAlignment(JLabel.CENTER);
                            return com;
                        }
                        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    }
                });

                String[] columns = {"Visitors ID", "Enter Date", "Enter Time"};
                for (String col : columns){
                    tableData.model.addColumn(col);
                }
                ArrayList<String[]> visitor_Entry_Record = new SecurityGuard().get_all_Visitor_Entry_Record();
                for (String[] entry : visitor_Entry_Record){
                    tableData.addRow(entry);
                }
                tableData.statusAllowed = false;
                tableData.preferredColumnWidth = 350;

                Building_Executive_Function.Button closeButton = new Building_Executive_Function.Button("Close");
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });

                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());
                scrollPane.setViewportView(tableData);

                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                panel.setBackground(Color.white);
                panel.add(header1, BorderLayout.NORTH);
                panel.add(scrollPane, BorderLayout.CENTER);
//                panel.add(closeButton, BorderLayout.SOUTH);
                panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
                contentPane.setLayout(new GridLayout(2,1,0,0));
                contentPane.setBackground(Color.white);
                contentPane.add(panel, BorderLayout.CENTER);
                JPanel panelTwo = new JPanel();
                panelTwo.setBackground(Color.white);
                panelTwo.setLayout(new BorderLayout());
                panelTwo.add(closeButton, BorderLayout.NORTH);
                Form_Home home = new Form_Home();
                home.removeAll();
                panelTwo.add(home, BorderLayout.SOUTH);
                contentPane.add(panelTwo, BorderLayout.SOUTH);

                JPanel buttonGroupPanel = new JPanel();
                buttonGroupPanel.setLayout(new GridLayout(1,1, 30, 30));

                contentPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

                setContentPane(contentPane);
                setUndecorated(true);
                setBackground(Color.WHITE);
                setSize(new Dimension(1186, 621));
                setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
                setLocationRelativeTo(null);
                setVisible(true);
            }
        }

        public static class Incident_Frame extends JFrame {
            private Building_Executive_Function.Button closeButton = new Building_Executive_Function.Button("close");
            private JScrollPane scrollPane = new JScrollPane();
            private JPanel contentPane = new JPanel();

            public Incident_Frame() throws IOException {
                Table tableData = new Table();
                JTableHeader header1 = tableData.getTableHeader();
                header1.setReorderingAllowed(false);
                header1.setResizingAllowed(false);
                header1.setDefaultRenderer(new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        if (column != header1.getColumnModel().getColumnCount()) {
                            Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                            com.setBackground(Color.white);
                            setBorder(noFocusBorder);
                            com.setForeground(new Color(102, 102, 102));
                            com.setFont(new Font("sansserif", Font.BOLD, 16));
                            setHorizontalAlignment(JLabel.CENTER);
                            return com;
                        }
                        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    }
                });

                String[] columns = {"Incident ID", "Date", "Time", "SecurityGuard ID", "Description"};
                for (String col : columns){
                    tableData.model.addColumn(col);
                }
                ArrayList<SecurityGuard.Incident> incidentArrayList = new SecurityGuard.Incident().get_all_Incident_Report();
                for (SecurityGuard.Incident incident : incidentArrayList){
                    tableData.addRow(incident.getStringArray(incident));
                }
                tableData.statusAllowed = false;
                tableData.preferredColumnWidth = 200;

                Building_Executive_Function.Button closeButton = new Building_Executive_Function.Button("Close");

                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());
                scrollPane.setViewportView(tableData);

                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                panel.setBackground(Color.white);
                panel.add(header1, BorderLayout.NORTH);
                panel.add(scrollPane, BorderLayout.CENTER);
//                panel.add(closeButton, BorderLayout.SOUTH);
                panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
                contentPane.setLayout(new GridLayout(2,1,0,0));
                contentPane.setBackground(Color.white);
                contentPane.add(panel, BorderLayout.CENTER);
                JPanel panelTwo = new JPanel();
                panelTwo.setBackground(Color.white);
                panelTwo.setLayout(new BorderLayout());
                panelTwo.add(closeButton, BorderLayout.NORTH);
                Form_Home home = new Form_Home();
                home.removeAll();
                panelTwo.add(home, BorderLayout.SOUTH);
                contentPane.add(panelTwo, BorderLayout.SOUTH);

                contentPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                setContentPane(contentPane);
                setUndecorated(true);
                setBackground(Color.WHITE);
                setSize(new Dimension(1186, 621));
                setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
                setLocationRelativeTo(null);
                setVisible(true);
            }
        }
    }

    public void run(int num) throws FileNotFoundException {
        setPanelBorderRight(num);
    }

    public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException {
        Entity.Executive.Building_Executive.Building_Executive_Interface building_Executive_Interface = new Entity.Executive.Building_Executive.Building_Executive_Interface("BE01");
        building_Executive_Interface.run(1);
        building_Executive_Interface.frame.setVisible(true);

//        new Building_Executive_JobReport_Panel.Visitor_Entry_Frame().setVisible(true);
//        new Building_Executive_JobReport_Panel.Incident_Frame().setVisible(true);
    }
}
