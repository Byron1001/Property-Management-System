package UIPackage.main;

import UIPackage.Component.Header;
import UIPackage.Component.Menu;
import UIPackage.Form.Form_Home;
import UIPackage.swing.ListMenu;
import UIPackage.swing.PanelBorder;

import javax.swing.*;
import javax.swing.plaf.basic.BasicViewportUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Main extends JFrame {
    private PanelBorder panelBorder;
    private Menu menu;

    public Main(){
        panelBorder = new PanelBorder();
        panelBorder.setBackground(new Color(255, 255, 255, 0));

        menu = new Menu();
        menu.initMoving(Main.this);
        menu.setLocation(0, 0);

        Header header = new Header();
        header.setLocation(215, 0);

        Form_Home formHome = new Form_Home();
        formHome.setLocation(215, 45);

        panelBorder.add(menu);
        panelBorder.add(header);
        panelBorder.add(formHome);
        setContentPane(panelBorder);

        this.setBackground(Color.lightGray);
        this.setUndecorated(true);
        this.setSize(new Dimension(1186, 621));
//        pack();
        this.setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args){
        new Main().setVisible(true);
    }

}
