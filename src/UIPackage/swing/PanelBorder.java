package UIPackage.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class PanelBorder extends JPanel {
    private JPanel panel1;

    public PanelBorder(){
        setOpaque(false);
        setLayout(new GridBagLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(getBackground());
        graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        super.paintComponent(g);
    }
}
