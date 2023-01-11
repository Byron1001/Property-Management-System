package UIPackage.Component;

import UIPackage.swing.SearchText;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Header extends JPanel {
    public Header(){
        setSize(971, 45);
        setLocation(215, 0);
        setOpaque(false);
        setBackground(Color.white);
        ImageIcon icon = new ImageIcon("src/UIPackage/Icon/search.png");
        icon = toIcon(icon, 30, 30);
        JLabel searchIcon = new JLabel(icon);
        searchIcon.setSize(30, 30);
        searchIcon.setLocation(10, 5);

        SearchText searchText = new SearchText();
        searchText.setSize(860, getHeight());
        searchText.setLocation(60, 0);

        ImageIcon stack = new ImageIcon("src/UIPackage/Icon/stack.png");
        stack = toIcon(stack, 30, 30);
        JLabel stackIcon = new JLabel(stack);
        stackIcon.setSize(30, 30);
        stackIcon.setLocation(931, 5);

        setLayout(null);
        add(searchIcon);
        add(searchText);
        add(stackIcon);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(getBackground());
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    public ImageIcon toIcon(ImageIcon imgIcon, int w, int h){
        Image srcImg = imgIcon.getImage();
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return new ImageIcon(resizedImg);
    }
}
