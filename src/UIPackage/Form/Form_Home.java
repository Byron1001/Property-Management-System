package UIPackage.Form;

import UIPackage.Component.Card;

import javax.swing.*;
import java.awt.*;

public class Form_Home extends JLayeredPane {
//    private JLayeredPane layeredPane = new JLayeredPane();
    public Card card1 = new Card();
    public Card card2 = new Card();
    public Card card3 = new Card();
    public Form_Home(){
//        setSize(727, 90);
        setPreferredSize(new Dimension(727, 90));
        setLayout(new GridLayout(1, 3, 10, 10));

        card1.setColor1(new Color(142, 142, 250));
        card1.setColor2(new Color(123,123,245));

        card2.setColor1(new Color(186, 123, 247));
        card2.setColor2(new Color(167,94,236));

        card3.setColor1(new Color(241,208,62));
        card3.setColor2(new Color(211,184, 61));

        add(card1);
        add(card2);
        add(card3);
        setBorder(BorderFactory.createEmptyBorder(10,5,5,5));
    }
}
