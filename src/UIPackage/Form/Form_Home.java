package UIPackage.Form;

import UIPackage.Component.Card;

import javax.swing.*;
import java.awt.*;

public class Form_Home extends JPanel {
    public Form_Home(){
        setBackground(new Color(242, 242, 242));

        setLayout(new FlowLayout());
        Card card = new Card();

        add(card);
    }
}
