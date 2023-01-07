package com.one.component.User_Manage;
import javax.swing.*;
import java.awt.*;
public class BackGroundPanel extends JPanel{
        //declare Image
    private Image backIcon;
    public BackGroundPanel(Image backIcon){
        this.backIcon = backIcon;
    }
    public void paintComponent(Graphics g){
        //set background image
        super.paintComponent(g);
        g.drawImage(backIcon,0,0,this.getWidth(),this.getHeight(),null);

    }
}
