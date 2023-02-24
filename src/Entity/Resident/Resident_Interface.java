package Entity.Resident;

import UIPackage.Event.EventMenuSelected;
import UIPackage.Model.Model_Menu;
import UIPackage.main.UIFrame;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class Resident_Interface {
    public UIFrame frame;
    private String resident_Username;

    public String getResident_Username() {
        return resident_Username;
    }

    public void setResident_Username(String resident_Username) {
        this.resident_Username = resident_Username;
    }

    public void setPanelBorderRight(JPanel panel){
        frame.panelBorderRight.removeAll();
        frame.panelBorderRight.add(panel);
        frame.panelBorderRight.revalidate();
        frame.repaint();
    }
    public Resident_Interface(String resident_Username) throws FileNotFoundException {
        this.resident_Username = resident_Username;
        frame = new UIFrame(resident_Username);

        frame.formHome.removeAll();
        frame.menu.listMenu.addItem(new Model_Menu("avatar", "Profile", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("payment", "Invoice", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("deposit", "Deposit", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("paymentHistory", "Payment History", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("statement", "Statement", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("booking", "Facility Booking", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("pass", "Visitor Pass", Model_Menu.MenuType.MENU));
        frame.menu.listMenu.addItem(new Model_Menu("complaint", "complaint", Model_Menu.MenuType.MENU));

        frame.menu.colorRight = Color.decode("#38ef7d");
        frame.menu.colorLeft = Color.decode("#11998e");

        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) throws FileNotFoundException {
                if (index == 0){
                } else if (index == 1){
                    new Resident_Payment_Frame(resident_Username).run();
                    frame.dispose();
                } else if (index == 2){
                    new Entity.Resident.Resident_Deposit_Frame("Mike1001").run();
                    frame.dispose();
                } else if (index == 3){
                } else if (index == 4){
                } else if (index == 5){
                } else if (index == 6){
                } else if (index == 7){
                }
            }
        });
    }

    public static void main(String[] args) throws FileNotFoundException {
        Resident_Interface residentInterface = new Resident_Interface("Mike1001");
        residentInterface.setPanelBorderRight(new Resident_Profile_Panel(residentInterface.getResident_Username()));
        residentInterface.frame.setVisible(true);
    }
}
