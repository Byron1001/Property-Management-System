package UIPackage.swing;

import UIPackage.Model.StatusType;

import javax.swing.*;
import java.awt.*;

public class TableStatus extends JLabel {
    private StatusType type;
    public StatusType getType() {
        return type;
    }
    public void setType(StatusType type, String outputWord) {
        this.type = type;
        setText(outputWord);
        setHorizontalAlignment(JLabel.CENTER);
        repaint();
    }
    public TableStatus(){
        setPreferredSize(new Dimension(40, 90));
        setForeground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (type != null){
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gradientPaint;
            if (type == StatusType.PENDING){
                gradientPaint = new GradientPaint(0, 0, new Color(186, 123, 247), 0, getHeight(), new Color(167, 94, 236));
            } else if (type == StatusType.APPROVED) {
                gradientPaint = new GradientPaint(0, 0, new Color(142, 142, 250), 0, getHeight(), new Color(123, 123, 245));
            } else {
                gradientPaint = new GradientPaint(0, 0, new Color(241, 208, 62), 0, getHeight(), new Color(211, 184, 61));
            }
            g2.setPaint(gradientPaint);
            g2.fillRoundRect(getWidth()/4 - 10, 0, getWidth() / 2 + 20, getHeight(), 15, 15);
//            g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
        }
        super.paintComponent(g);
    }
}
