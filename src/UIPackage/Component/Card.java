package UIPackage.Component;

import UIPackage.Model.Model_Card;

import javax.swing.*;
import java.awt.*;
import java.awt.image.CropImageFilter;

public class Card extends JPanel {
    private JLabel iconLabel = new JLabel();
    private JLabel titleLabel = new JLabel();
    private JLabel valuesLabel = new JLabel();
    private JLabel descriptionLabel = new JLabel();
    private Color color1, color2;

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

    public Card(Color color1, Color color2, JLabel iconLabel, JLabel titleLabel, JLabel valuesLabel, JLabel descriptionLabel){
        this.color1 = color1;
        this.color2 = color2;
        this.iconLabel = iconLabel;
        this.titleLabel = titleLabel;
        this.valuesLabel = valuesLabel;
        this.descriptionLabel = descriptionLabel;
    }

    public Card(){
        setOpaque(false);
        setSize(195, 375);
        setLayout(new FlowLayout());

        iconLabel.setBounds(25, 25, 51, 37);
        iconLabel.setIcon(new ImageIcon("src/UIPackage/Icon/stock.png"));

        titleLabel.setBounds(25, 50, 51, 37);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 14));

        valuesLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 18));

        descriptionLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));

        color1 = Color.BLACK;
        color2 = Color.WHITE;
    }

    public void setData(Model_Card data){
        iconLabel.setIcon(data.getIcon());
        titleLabel.setText(data.getTitle());
        valuesLabel.setText(data.getValues());
        descriptionLabel.setText(data.getDescription());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        graphics2D.setPaint(gradientPaint);
        graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillOval(getWidth() - getHeight() / 2, 10, getHeight(), getHeight());
        super.paintComponent(g);
    }
}
