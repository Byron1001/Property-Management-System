package com.one.ui;

import com.one.component.Account_Executive.*;
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

public class AccountExecutiveInterface {
    JFrame jf = new JFrame("Admin Executive");
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

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Management");
        DefaultMutableTreeNode Unit_Management = new DefaultMutableTreeNode("Unit Management");
        DefaultMutableTreeNode Resident_Tenant_Management = new DefaultMutableTreeNode("Resident Tenant Management");
        DefaultMutableTreeNode Complaint_Management = new DefaultMutableTreeNode("Complaint Management ");
        DefaultMutableTreeNode Employee_Management = new DefaultMutableTreeNode("Employee Management ");
        DefaultMutableTreeNode Facility_Management = new DefaultMutableTreeNode("Facility Management ");
        DefaultMutableTreeNode Facility_Booking_Management = new DefaultMutableTreeNode("Facility Booking Management ");

        root.add(Unit_Management);
        root.add(Resident_Tenant_Management);
        root.add(Complaint_Management);
        root.add(Employee_Management);
        root.add(Facility_Management);
        root.add(Facility_Booking_Management);

        Color color = new Color(212,220,217);
        JTree tree = new JTree(root);
        MyRenderer myRenderer = new MyRenderer();
        myRenderer.setBackgroundNonSelectionColor(color);
        myRenderer.setBackgroundSelectionColor(new Color(140,140,140));
        tree.setCellRenderer(myRenderer);
        tree.setBackground(color);

        tree.setSelectionRow(2);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                Object lastPathComponent = e.getNewLeadSelectionPath().getLastPathComponent();

                if(Unit_Management.equals(lastPathComponent)){
                    sp.setRightComponent(new Account_Executive_Unit_Management_Component(jf));
                    sp.setDividerLocation(150);
                }else if(Resident_Tenant_Management.equals(lastPathComponent)){
                    sp.setRightComponent(new Account_Executive_Resident_Tenant_Management_Component(jf));
                    sp.setDividerLocation(150);
                }else if(Complaint_Management.equals(lastPathComponent)){
                    sp.setRightComponent(new Account_Executive_Complaint_Management_Component(jf));
                    sp.setDividerLocation(150);
                }else if(Employee_Management.equals(lastPathComponent)){
                    sp.setRightComponent(new Account_Executive_Complaint_Management_Component(jf));
                    sp.setDividerLocation(150);
                }else if(Facility_Management.equals(lastPathComponent)){
                    sp.setRightComponent(new Account_Executive_Facility_Management_Component(jf));
                    sp.setDividerLocation(150);
                }else if(Facility_Booking_Management.equals(lastPathComponent)){
                    sp.setRightComponent(new Account_Executive_Facility_Booking_Management_Component(jf));
                    sp.setDividerLocation(150);
                }
            }
        });



        sp.setRightComponent(new UserManageComponent(jf));
        sp.setLeftComponent(tree);
        jf.add(sp);
        jf.setVisible(true);



    }
    public static void main(String[] args){
        try {
            new AccountExecutiveInterface().init();
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
