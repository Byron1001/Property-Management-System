package UIPackage.Component;

import UIPackage.Event.EventMenuSelected;
import UIPackage.Model.Model_Menu;
import UIPackage.swing.ListMenu;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.security.PublicKey;


public class Menu extends JPanel{
    public JPanel panelMoving;
    public JLabel title = new JLabel("Parkhill.R");;
    public ListMenu listMenu = new ListMenu();
    public Color colorLeft = Color.decode("#1CB5E0");
    public Color colorRight = Color.decode("#000046");
    public ImageIcon titleIcon = new ImageIcon("src/UIPackage/Icon/small_icon.png");
    private EventMenuSelected event;

    public void addEventMenuSelected(EventMenuSelected event){
        this.event = event;
        listMenu.addEventMenuSelected(event);
    }

    public Menu(){
        setOpaque(false);
        panelMoving = new JPanel();
        panelMoving.setOpaque(false);
        panelMoving.setSize(new Dimension(215, 63));
        panelMoving.setLayout(new FlowLayout());
        panelMoving.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.white);
        title.setHorizontalAlignment(JLabel.LEADING);

        titleIcon = getScaledImage(titleIcon, 40, 40);
        title.setIcon(titleIcon);
        title.setIconTextGap(15);

        listMenu.setSize(215, 423);

        panelMoving.add(title);

        setSize(new Dimension(215, 621));
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(panelMoving);

        constraints.gridy = 1;
        add(new JSeparator(), constraints);

        constraints.gridy = 2;
        add(listMenu, constraints);
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
        GradientPaint gradientPaint = new GradientPaint(0, 0, colorLeft, 0, getHeight(), colorRight);
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
