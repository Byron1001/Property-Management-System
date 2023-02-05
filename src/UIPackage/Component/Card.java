package UIPackage.Component;

import UIPackage.Model.Model_Card;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Card extends JPanel {
    public JLabel iconLabel = new JLabel();
    public JLabel titleLabel = new JLabel("tile");
    public JLabel valuesLabel = new JLabel("values");
    public JLabel descriptionLabel = new JLabel("desc");
    public Color color1, color2;

    public Color getColor1() {
        return color1;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    public Card() {
        iconLabel.setBounds(0, 0, 51, 40);
        iconLabel.setIcon(toIcon(new ImageIcon("src/UIPackage/Icon/stock.png"), 40, 40));

        titleLabel.setBounds(0, 0, 51, 37);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 14));

        valuesLabel.setBounds(25, 25, 51, 37);
        valuesLabel.setForeground(Color.WHITE);
        valuesLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 18));

        descriptionLabel.setBounds(25, 126, 51, 37);
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));

        setLayout(new GridLayout(1,4));
        add(iconLabel);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        panel.setOpaque(false);
        panel.add(titleLabel);

        panel.add(descriptionLabel);
        add(panel);
        add(valuesLabel);

        setOpaque(false);
        setSize(new Dimension(100, 70));
        setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        color1 = Color.BLACK;
        color2 = Color.WHITE;
    }

    public void setData(Model_Card data){
        iconLabel.setIcon(toIcon(data.getIcon(), 40, 40));
        titleLabel.setText(data.getTitle());
        valuesLabel.setText(data.getValues());
        descriptionLabel.setText(data.getDescription());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, getWidth() / 2, getHeight(), color2);
        graphics2D.setPaint(gradientPaint);
        graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        graphics2D.setColor(new Color(255, 255, 255, 50));
        graphics2D.fillOval(getWidth() - (getHeight() / 2), 10, getHeight(), getHeight());
        graphics2D.fillOval(getWidth() - (getHeight() / 2) - 20, getHeight() / 2 + 20, getHeight(), getHeight());
        super.paintComponent(g);
    }

    public Icon toIcon(Icon icon, int w, int h){
        ImageIcon imgIcon = (ImageIcon) icon;
        Image srcImg = imgIcon.getImage();
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return new ImageIcon(resizedImg);
    }
}
