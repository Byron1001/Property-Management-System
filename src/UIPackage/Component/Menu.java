package UIPackage.Component;

import UIPackage.Model.Model_Menu;
import UIPackage.swing.ListMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;


public class Menu extends JPanel{
    private JPanel panelMoving;
    private JLabel title;
    private ListMenu listMenu = new ListMenu();
    public Menu(){
        setOpaque(false);
        panelMoving = new JPanel();
        panelMoving.setOpaque(false);
        panelMoving.setSize(new Dimension(215, 63));
        panelMoving.setLayout(new FlowLayout());
        panelMoving.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        title = new JLabel("Parkhill.R");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.white);
        title.setHorizontalAlignment(JLabel.LEADING);
        ImageIcon titleIcon = new ImageIcon("src/UIPackage/Icon/small_icon.png");
        titleIcon = getScaledImage(titleIcon, 40, 40);
        title.setIcon(titleIcon);
        title.setIconTextGap(15);

        listMenu.setSize(215, 423);

        panelMoving.add(title);

        setSize(new Dimension(215, 621));
        setLayout(new FlowLayout());
        add(panelMoving);
        add(new JSeparator());
        add(listMenu, CENTER_ALIGNMENT);

        init();
    }

    private void init(){
        listMenu.addItem(new Model_Menu("1", "Dashboard", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("2", "UI Elements", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("3", "Components", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("4", "Form stuff", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("5", "Data Table", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("", "", Model_Menu.MenuType.EMPTY));

        listMenu.addItem(new Model_Menu("", "My Data", Model_Menu.MenuType.TITLE));
        listMenu.addItem(new Model_Menu("", "", Model_Menu.MenuType.EMPTY));
        listMenu.addItem(new Model_Menu("6", "Icons", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("7", "Sample Page", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("8", "Extra", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("9", "More", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("10", "Logout", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("", "", Model_Menu.MenuType.EMPTY));
    }

    public void initMoving(JFrame frame){
        panelMoving.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                frame.setLocation(e.getXOnScreen(), e.getYOnScreen());
            }
        });
    }

    @Override
    protected void paintChildren(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradientPaint = new GradientPaint(0, 0, Color.decode("#1CB5E0"), 0, getHeight(), Color.decode("#000046"));
        graphics2D.setPaint(gradientPaint);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        super.paintChildren(g);
    }

    private ImageIcon getScaledImage(ImageIcon sImg, int w, int h){
        Image srcImg = sImg.getImage();
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        ImageIcon imageIcon = new ImageIcon(resizedImg);
        return imageIcon;
//        Image image = imageIcon.getImage(); // transform it
//        Image newimg = image.getScaledInstance(w, h,  Image.SCALE_SMOOTH); // scale it the smooth way
//        imageIcon = new ImageIcon(newimg);  // transform it back
//        return imageIcon;
    }

}
