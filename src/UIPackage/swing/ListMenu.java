package UIPackage.swing;

import UIPackage.Model.Model_Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListMenu<E extends Object> extends JList<E> {
    private final DefaultListModel model;
    private int selectedIndex = -1;
    public ListMenu(){
        model = new DefaultListModel();
        setModel(model);
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)){
                    int index = locationToIndex(e.getPoint());
                    Object o = model.getElementAt(index);
                    if (o instanceof Model_Menu){
                        Model_Menu menu = (Model_Menu) o;
                        if (menu.getType() == Model_Menu.MenuType.MENU){
                            selectedIndex = index;
                        }
                    }else {
                        selectedIndex = index;
                    }
                    repaint();
                }
            }
        });
    }

    @Override
    public ListCellRenderer<? super E> getCellRenderer() {
        return new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Model_Menu data;
                if (value instanceof Model_Menu){
                    data = (Model_Menu) value;
                }else {
                    data = new Model_Menu("", value+"", Model_Menu.MenuType.EMPTY);
                }
                MenuItem item = new MenuItem(data);
                item.setSelected(selectedIndex == index);
                return item;
            }
        };
    }

    public void addItem(Model_Menu data){
        model.addElement(data);
    }
}
