package com.one.ui;

import com.one.component.Building_Executive.*;
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

public class BuildingExecutiveInterface {
    JFrame jf = new JFrame("Building Executive");
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
        DefaultMutableTreeNode Employee_Management = new DefaultMutableTreeNode("Unit Management");
        DefaultMutableTreeNode Complaint_Management = new DefaultMutableTreeNode("Resident Tenant Management");
        DefaultMutableTreeNode Security_Checkpoint = new DefaultMutableTreeNode("Complaint Management ");
        DefaultMutableTreeNode Patrolling = new DefaultMutableTreeNode("Patrolling ");
        DefaultMutableTreeNode Complaints = new DefaultMutableTreeNode("Complaints ");
        DefaultMutableTreeNode Job_Reports = new DefaultMutableTreeNode("Job Reports ");

        root.add(Employee_Management);
        root.add(Complaint_Management);
        root.add(Security_Checkpoint);
        Security_Checkpoint.add(Patrolling);
        Security_Checkpoint.add(Complaints);
        Security_Checkpoint.add(Job_Reports);

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
                System.out.println(lastPathComponent);
                if(Employee_Management.equals(lastPathComponent)){
                    sp.setRightComponent(new Building_Executive_Employee_Management_Component(jf));
                    sp.setDividerLocation(150);
                }else if(Complaint_Management.equals(lastPathComponent)){
                    sp.setRightComponent(new Building_Executive_Complaint_Management_Component(jf));
                    //sp.setRightComponent(new UserManageComponent(jf));
                    sp.setDividerLocation(150);
                }else if(Patrolling.equals(lastPathComponent)){
                    sp.setRightComponent(new Building_Executive_Patrolling_Component(jf));
                    sp.setDividerLocation(150);
                }
                else if(Complaints.equals(lastPathComponent)){
                    sp.setRightComponent(new Building_Executive_Complaint_Component(jf));
                    sp.setDividerLocation(150);
                }
                else if(Job_Reports.equals(lastPathComponent)){
                    sp.setRightComponent(new Building_Executive_Job_Reports_Component(jf));
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
            new BuildingExecutiveInterface().init();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private class MyRenderer extends DefaultTreeCellRenderer{
        private ImageIcon rootIcon = null;
        private ImageIcon Employee_Management = null;
        private ImageIcon Complaint_Management = null;
        private ImageIcon Security_Checkpoint = null;
        private ImageIcon Patrolling = null;
        private ImageIcon Complaints = null;
        private ImageIcon Job_Reports = null;

        public MyRenderer(){
            rootIcon = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\userManage.png");
            Employee_Management = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\systemManage.png");
            Complaint_Management = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\systemManage.png");
            Security_Checkpoint = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\systemManage.png");
            Patrolling = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\systemManage.png");
            Complaints = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\systemManage.png");
            Job_Reports = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\systemManage.png");
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
                    image = Employee_Management;
                    break;
                case 2:
                    image = Complaint_Management;
                    break;
                case 3:
                    image = Security_Checkpoint;
                    break;
                case 4:
                    image = Patrolling;
                    break;
                case 5:
                    image = Complaints;
                    break;
                case 6:
                    image = Job_Reports;
                    break;
            }
            this.setIcon(image);
            return this;
        }
    }

}
