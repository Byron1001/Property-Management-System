package UIPackage.main;

import UIPackage.Component.Menu;
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

        panelBorder.add(menu);
        setContentPane(panelBorder);

        this.setUndecorated(true);
        this.setSize(new Dimension(1186, 621));
        this.setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args){
        new Main().setVisible(true);
    }

}
