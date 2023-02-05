package UIPackage.swing;

import UIPackage.Model.StatusType;

import javax.swing.*;
import java.awt.*;

public class CellStatus extends JPanel {
    public CellStatus(StatusType type, String outputWord){
        setPreferredSize(new Dimension(90, 40));
        setBackground(Color.white);
        setLayout(new GridLayout(1, 1));
        TableStatus status = new TableStatus();
        status.setType(type, outputWord);
        add(status);
    }
}
