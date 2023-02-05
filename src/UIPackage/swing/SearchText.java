package UIPackage.swing;

import javax.swing.*;
import java.awt.*;

public class SearchText extends JTextField {
    public SearchText(){
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setSelectionColor(new Color(220, 204, 182));
        setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
    }

    private final String hint = "Search here...";
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (getText().length() == 0){
            int height = getHeight();
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Insets ins = getInsets();//to put the hint at the left most near edge
            FontMetrics fm = g.getFontMetrics();
            int c0 = getBackground().getRGB();
            int c1 = getForeground().getRGB();
            int m = 0xfefefefe;
            int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);
            g.setColor(new Color(c2, true));
            g.drawString(hint, ins.left, height / 2 + fm.getAscent() / 2);//getAscent get the font height from baseline to height
        }
    }

    public static void main(String[] args) {
        JPanel panel = new JPanel();
        SearchText searchText = new SearchText();
        searchText.setPreferredSize(new Dimension(1000, 40));
        panel.add(searchText);
        panel.setPreferredSize(new Dimension(1000, 40));
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
