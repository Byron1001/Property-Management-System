package UIPackage.swing;

import UIPackage.Model.Model_Menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuItem extends JPanel {
    private boolean selected;
    private boolean over;
    public MenuItem(Model_Menu data){

        JLabel menuName = new JLabel();
        menuName.setForeground(Color.WHITE);
        menuName.setLocation(30,0);
        menuName.setIconTextGap(20);

        setLayout(new GridLayout(1, 1));
        add(menuName);

        setSize(getWidth(), 35);
        setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        setBackground(getBackground());
        setOpaque(false);

        if (data.getType() == Model_Menu.MenuType.MENU){
            menuName.setIcon(data.toIcon(30, 30));
            menuName.setText(data.getName());
            menuName.setFont(new Font("sanserif", Font.PLAIN, 16));
        } else if (data.getType() == Model_Menu.MenuType.TITLE) {
            menuName.setText(data.getName());
            menuName.setFont(new Font("sansserif", Font.BOLD, 16));
        } else {
            menuName.setText(" ");
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }

    public void setOver(boolean over) {
        this.over = over;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {//will be used will repaint()
        if (selected || over){
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (selected){
                graphics2D.setColor(new Color(255, 255, 255, 80));
            } else {
                graphics2D.setColor(new Color(255, 255, 255, 20));
            }
            graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
        }
        super.paintComponent(g);
    }
}
