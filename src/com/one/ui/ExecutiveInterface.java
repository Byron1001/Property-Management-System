package com.one.ui;

import com.one.component.User_Manage.UserManageComponent;
import com.one.util.ScreenUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class ExecutiveInterface {
    JFrame jf = new JFrame("Executive");
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
        DefaultMutableTreeNode usermanage = new DefaultMutableTreeNode("Management1");
        DefaultMutableTreeNode bookmanage = new DefaultMutableTreeNode("Management2");
        DefaultMutableTreeNode borrowmanage = new DefaultMutableTreeNode("Management3");
        DefaultMutableTreeNode statisticsmanage = new DefaultMutableTreeNode("Management4");

        root.add(usermanage);
        root.add(bookmanage);
        root.add(borrowmanage);
        root.add(statisticsmanage);

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

                if(usermanage.equals(lastPathComponent)){
                    sp.setRightComponent(new JLabel("this is usermanage..."));
                    sp.setDividerLocation(150);
                }else if(bookmanage.equals(lastPathComponent)){
                    sp.setRightComponent(new UserManageComponent(jf));
                    sp.setDividerLocation(150);
                }else if(borrowmanage.equals(lastPathComponent)){
                    sp.setRightComponent(new JLabel("this is borrowmanage..."));
                    sp.setDividerLocation(150);
                }else if(statisticsmanage.equals(lastPathComponent)){
                    sp.setRightComponent(new JLabel("this is statisticsmanage..."));
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
            new ExecutiveInterface().init();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private class MyRenderer extends DefaultTreeCellRenderer{
        private ImageIcon rootIcon = null;
        private ImageIcon userManageIcon = null;
        private ImageIcon bookManageIcon = null;
        private ImageIcon borrowManageIcon = null;
        private ImageIcon statisticsManageIcon = null;

        public MyRenderer(){
                rootIcon = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\userManage.png");
                userManageIcon = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\systemManage.png");
                bookManageIcon = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\systemManage.png");
                borrowManageIcon = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\systemManage.png");
                statisticsManageIcon = new ImageIcon("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\systemManage.png");
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
                    image = userManageIcon;
                    break;
                case 2:
                    image = bookManageIcon;
                    break;
                case 3:
                    image = borrowManageIcon;
                    break;
                case 4:
                    image = statisticsManageIcon;
                    break;
            }
            this.setIcon(image);
            return this;
        }
    }

}
