package com.one.ui;

import com.one.component.SecurityGuard.SecurityGuard_Component;
import com.one.component.User_Manage.UserManageComponent;
import com.one.util.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SecurityGuardInterface {
    JFrame jf = new JFrame("Resident/Tenant");
    final int WIDTH = 1000;
    final int HEIGHT = 600;

    public void init() throws Exception{
        jf.setBounds((ScreenUtils.getScreenWidth()-WIDTH)/2,(ScreenUtils.getScreenHeight()-HEIGHT)/2,WIDTH,HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\logo.png")));

        JMenuBar jmb = new JMenuBar();
        JMenu jMenu = new JMenu("Setting");
        JMenuItem m1 = new JMenuItem("Logout Account");
        JMenuItem m2 = new JMenuItem("Exit Program");

        m1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    new MainInterface().init();
                    jf.dispose();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        m2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        jMenu.add(m1);
        jMenu.add(m2);
        jmb.add(jMenu);

        jf.setJMenuBar(jmb);

        JSplitPane sp = new JSplitPane();
        sp.setContinuousLayout(true);
        sp.setDividerLocation(150);
        sp.setDividerSize(7);
        /*
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Management");
        DefaultMutableTreeNode Visitor_Pass = new DefaultMutableTreeNode("Profile");
        DefaultMutableTreeNode Payment_Deposit = new DefaultMutableTreeNode("Payment Deposit");
        DefaultMutableTreeNode Payment_Management = new DefaultMutableTreeNode("Payment Management");
        DefaultMutableTreeNode Facility_Management = new DefaultMutableTreeNode("Facility Management");
        DefaultMutableTreeNode Complaint_Management = new DefaultMutableTreeNode("Complaint Management");
        */

        Box vBox = Box.createVerticalBox();
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JButton CheckPoint = new JButton("CheckPoint");
        JButton Incident = new JButton("Update Incident");
        CheckPoint.setPreferredSize(new Dimension(130, 100));
        Incident.setPreferredSize(new Dimension(130, 100));
        jp1.add(CheckPoint);
        jp2.add(Incident);
        //vBox.add(Box.createVerticalStrut(20));
        vBox.add(jp1);
        vBox.add(jp2);
        vBox.add(Box.createVerticalStrut(300));

        CheckPoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        Incident.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });



        sp.setRightComponent(new SecurityGuard_Component(jf));
        sp.setLeftComponent(vBox);
        jf.add(sp);
        jf.setVisible(true);



    }
    public static void main(String[] args){
        try {
            new SecurityGuardInterface().init();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private class MyRenderer extends DefaultTreeCellRenderer{
        private ImageIcon rootIcon = null;
        private ImageIcon unit_management = null;
        private ImageIcon resident_tenant_management = null;
        private ImageIcon complaint_management = null;
        private ImageIcon employee_management = null;
        private ImageIcon facility_management = null;
        private ImageIcon facility_booking_management = null;

        public MyRenderer(){
            rootIcon = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\userManage.png");
            unit_management = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\systemManage.png");
            resident_tenant_management = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\systemManage.png");
            complaint_management = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\systemManage.png");
            employee_management = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\systemManage.png");
            facility_management = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\systemManage.png");
            facility_booking_management = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\systemManage.png");
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            ImageIcon image = null;
            switch (row){
                case 0:
                    image = rootIcon;
                    break;
                case 1:
                    image = unit_management;
                    break;
                case 2:
                    image = resident_tenant_management;
                    break;
                case 3:
                    image = complaint_management;
                    break;
                case 4:
                    image = employee_management;
                    break;
                case 5:
                    image = facility_management;
                    break;
                case 6:
                    image = facility_booking_management;
                    break;
            }
            this.setIcon(image);
            return this;
        }
    }

}
