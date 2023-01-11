package UIPackage.Model;

import javax.swing.*;
import java.awt.*;

public class Model_Card {
    Icon icon;
    String title, values, description;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getValues() {
        return values;
    }

    public Model_Card(Icon icon, String title, String values, String description){
        this.icon = icon;
        this.title = title;
        this.values = values;
        this.description = description;
    }
    public Model_Card(){
    }
}
