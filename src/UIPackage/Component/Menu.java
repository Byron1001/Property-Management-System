package UIPackage.Component;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel{
    private JPanel panelMoving;
    public Menu(){
        setOpaque(false);
        panelMoving = new JPanel();
        panelMoving.setOpaque(false);
        panelMoving.setSize(new Dimension(215, 63));


        setSize(new Dimension(215, 621));
        setLayout(new FlowLayout());
        add(panelMoving);
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

}
