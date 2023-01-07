package com.one.ui;

import com.one.component.User_Manage.BackGroundPanel;
import com.one.util.ScreenUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class RegisterInterface {
    JFrame jf = new JFrame("Register");

    final int WIDTH = 500;
    final int HEIGHT = 400;
    public void init() throws Exception{


        jf.setBounds((ScreenUtils.getScreenWidth()-WIDTH)/2,(ScreenUtils.getScreenHeight()-HEIGHT)/2,WIDTH,HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("images\\logo.png")));

        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File("images\\galaxy.jpg")));
        bgPanel.setBounds(0,0,WIDTH,HEIGHT);

        Box vBox = Box.createVerticalBox();
        //user name
        Box uBox = Box.createHorizontalBox();
        JLabel uLabel = new JLabel("User Name");
        JTextField uField = new JTextField(20);
        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(20));
        uBox.add(uField);

        //password
        Box pBox = Box.createHorizontalBox();
        JLabel pLabel = new JLabel("User Name");
        JPasswordField pField = new JPasswordField(20);
        pBox.add(pLabel);
        pBox.add(Box.createHorizontalStrut(20));
        pBox.add(pField);

        //confirm password
        Box cpBox = Box.createHorizontalBox();
        JLabel cpLabel = new JLabel("confirm password");
        JPasswordField cpField = new JPasswordField(20);
        cpBox.add(cpLabel);
        cpBox.add(Box.createHorizontalStrut(20));
        cpBox.add(cpField);

        //contact number
        Box cBox = Box.createHorizontalBox();
        JLabel cLabel = new JLabel("contact number");
        JTextField cField = new JTextField(20);
        cBox.add(cLabel);
        cBox.add(Box.createHorizontalStrut(20));
        cBox.add(cField);

        //gender
        Box gBox = Box.createHorizontalBox();
        JLabel gLabel = new JLabel("Gender");
        JRadioButton maleBtn = new JRadioButton("Male",true);
        JRadioButton femaleBtn = new JRadioButton("Female",false);
        ButtonGroup bg = new ButtonGroup();
        bg.add(maleBtn);
        bg.add(femaleBtn);
        gBox.add(gLabel);
        gBox.add(Box.createHorizontalStrut(100));
        gBox.add(maleBtn);
        gBox.add(femaleBtn);
        gBox.add(Box.createHorizontalStrut(90   ));

        //button
        Box ButBox = Box.createHorizontalBox();
        JButton register = new JButton("Register");
        JButton back = new JButton("Back");
        ButBox.add(register);
        ButBox.add(Box.createHorizontalStrut(140));
        ButBox.add(back);

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    new MainInterface().init();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                jf.dispose();
             }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    new MainInterface().init();
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
        vBox.add(cpBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(cBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(gBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(ButBox);

        bgPanel.add(vBox);
        jf.add(bgPanel);




        jf.setVisible(true);


    }

    public static void main(String[] args){
        try {
            new RegisterInterface().init();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
