package UIPackage.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Model_Menu {
    String icon, name;
    MenuType type;

    public static enum MenuType{
        TITLE, MENU, EMPTY
    }

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Model_Menu(String icon, String name, MenuType type){
        this.icon = icon;
        this.name = name;
        this.type = type;
    }
    public Model_Menu(){

    }

    public Icon toIcon(int w, int h){
        ImageIcon imgIcon = new ImageIcon("src/UIPackage/Icon/" + icon + ".png");
        Image srcImg = imgIcon.getImage();
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return new ImageIcon(resizedImg);
    }
}
