package com.one.ui;

import com.one.component.User_Manage.BackGroundPanel;
import com.one.util.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class BuildingExecutiveLoginInterface {
    JFrame jf = new JFrame("Building Executive Login Page");

    final int WIDTH = 500;
    final int HEIGHT = 300;
    public void init() throws Exception {

        jf.setBounds((ScreenUtils.getScreenWidth()-WIDTH)/2,(ScreenUtils.getScreenHeight()-HEIGHT)/2,WIDTH,HEIGHT);
        jf.setResizable(false);
        try {
            jf.setIconImage(ImageIO.read(new File("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\logo.png")));
        }catch(IOException e){
            e.printStackTrace();
        }


        BackGroundPanel bg = new BackGroundPanel(ImageIO.read(new File("C:\\Users\\edmun\\IdeaProjects\\JavaAssignment(Version 2.0)\\images\\galaxy.jpg")));
        Box vBox = Box.createVerticalBox();

        //user name
        Box uBox = Box.createHorizontalBox();
        JLabel uLabel = new JLabel("User Name");
        JTextField uField = new JTextField(15);
        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(20));
        uBox.add(uField);

        //password
        Box pBox = Box.createHorizontalBox();
        JLabel pLabel = new JLabel("Password  ");
        JTextField pField = new JTextField(15);
        pBox.add(pLabel);
        pBox.add(Box.createHorizontalStrut(20));
        pBox.add(pField);

        //button
        Box bBox = Box.createHorizontalBox();
        JButton login = new JButton("Login");
        JButton register = new JButton("Register");
        bBox.add(login);
        bBox.add(Box.createHorizontalStrut(100));
        bBox.add(register);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = uField.getText().trim();
                String password = pField.getText().trim();
                if(name.compareTo("Edmund")==0&&password.compareTo("123456")==0){
                    //login success here
                    JOptionPane.showMessageDialog(jf,"Login Successfully");

                    try {
                        new BuildingExecutiveInterface().init();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    jf.dispose();
                }else{
                    //login fail here
                    JOptionPane.showMessageDialog(jf,"Invalid Password or User Name");
                }
            }
        });

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    new RegisterInterface().init();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                jf.dispose();
            }
        });

        vBox.add(Box.createVerticalStrut(50));
        vBox.add(uBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(pBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(bBox);

        bg.add(vBox);
        jf.add(bg);
        jf.setVisible(true);

    }

    public static void main(String[] args){
        try {
            new BuildingExecutiveLoginInterface().init();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
