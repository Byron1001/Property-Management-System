package UIPackage.main;

import Entity.Financial.Invoice;
import Entity.Resident.Resident;
import Entity.Resident.Resident_Payment_Frame;
import UIPackage.Component.Header;
import UIPackage.Component.Menu;
import UIPackage.Event.EventMenuSelected;
import UIPackage.Form.Form_Home;
import UIPackage.Model.Model_Card;
import UIPackage.Model.Model_Menu;
import UIPackage.Model.StatusType;
import UIPackage.swing.PanelBorder;
import UIPackage.swing.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class UIFrame extends JFrame {
    public PanelBorder panelBorderLeft, panelBorderRight, panelBorderIn;
    public Menu menu = new Menu();
    public Header header = new Header();
    public Form_Home formHome = new Form_Home();
    public Table tableData = new Table();
    public Color backgroundColor = Color.WHITE;
    public String tableTitle = "Table title";
    public JScrollPane scrollPane;
    public JPanel panel;

    public UIFrame(String tableTitle){
        this.tableTitle = tableTitle;
        menu.initMoving(UIFrame.this);

        formHome.setBackground(backgroundColor);

        panelBorderLeft = new PanelBorder();
        panelBorderRight = new PanelBorder();
        setLayout(new BorderLayout());
        panelBorderRight.setLayout(new BorderLayout());
        panelBorderLeft.setLayout(new GridLayout(1,1));

        panelBorderLeft.add(menu);
        panelBorderRight.setBackground(backgroundColor);
        panelBorderRight.add(header, BorderLayout.PAGE_START);

        panelBorderIn = new PanelBorder();
        panelBorderIn.setPreferredSize(new Dimension(875,319));
        panelBorderIn.setBackground(backgroundColor);
        panelBorderIn.setLayout(new GridBagLayout());

        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), tableTitle, TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(new Font("sanserif", Font.BOLD, 18));
        border.setTitleColor(Color.lightGray);
        panelBorderIn.setBorder(border);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JTableHeader header1 = tableData.getTableHeader();
        header1.setReorderingAllowed(false);
        header1.setResizingAllowed(false);
        header1.setDefaultRenderer(new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                TableHeader header = new TableHeader(value +"");
//                    header.setPreferredSize(new Dimension(197, 40));
                if (column != header1.getColumnModel().getColumnCount()){
                    Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    com.setBackground(backgroundColor);
                    setBorder(noFocusBorder);
                    com.setForeground(new Color(102, 102, 102));
                    com.setFont(new Font("sansserif", Font.BOLD, 16));
                    setHorizontalAlignment(JLabel.CENTER);
                    return com;
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(backgroundColor);
        panel.add(tableData);

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(tableData.getWidth(), 400));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setViewportView(panel);

        panelBorderIn.add(header1, constraints);
        constraints.gridy++;
//        panelBorderIn.add(tableData, constraints);
        panelBorderIn.add(scrollPane, constraints);

        panelBorderRight.add(panelBorderIn, BorderLayout.CENTER);
        panelBorderRight.add(formHome, BorderLayout.PAGE_END);

        add(panelBorderLeft, BorderLayout.LINE_START);
        add(panelBorderRight, BorderLayout.CENTER);

        setUndecorated(true);
        setSize(new Dimension(1186, 621));
        setShape(new RoundRectangle2D.Double(0, 0, 1186, 621, 15, 15));
        setLocationRelativeTo(null);
        setVisible(true);

        header.searchText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                String query = header.searchText.getText().toLowerCase();
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) tableData.getModel());
                tableData.setRowSorter(sorter);
                sorter.setRowFilter(RowFilter.regexFilter(query));
            }
        });
    }

    public static void main(String[] args) throws FileNotFoundException {
        UIFrame frame = new UIFrame("Hello");
        frame.menu.listMenu.addItem(new Model_Menu("Dashboard", "Dashboard", Model_Menu.MenuType.MENU));
        for (int i = 0;i < 5;i++){
            frame.menu.listMenu.addItem(new Model_Menu("Dashboard", "Dashboard", Model_Menu.MenuType.MENU));
        }
        frame.menu.initMoving(frame);
//        frame.formHome.card1.setData(new Model_Card(new ImageIcon(new Image())));
//        frame.formHome.card1.color1 = Color.RED;
//        frame.formHome.card1.color2 = Color.BLACK;
        frame.formHome.card1.setData(new Model_Card("2", "this is", "100%", "%in%"));
//        frame.menu.colorLeft = Color.RED;
        frame.tableData.model.addColumn("Name");
        frame.tableData.model.addColumn("Name");
        frame.tableData.model.addColumn("Name");
        frame.tableData.model.addColumn("Status");
        frame.tableData.preferredColumnWidth = 1000 / frame.tableData.model.getColumnCount();
        frame.tableData.addRow(new String[] {"One", "Two", "Three", "OK"});
        frame.tableData.addRow(new String[] {"One", "Two", "Three", "APPROVED"});
        for (int i = 0; i < 50;i++){
            frame.tableData.addRow(new String[] {"One", "Two", "Three", "PENDING"});
        }
//        frame.tableData.statusAllowed = false;
//        System.out.println(frame.menu.listMenu.selectedIndex);
        frame.menu.listMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = frame.menu.listMenu.locationToIndex(e.getPoint());
                System.out.println(index);
                super.mouseClicked(e);
            }
        });
        frame.menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) {
                System.out.println(index);
            }
        });
//        frame.panelBorderIn.removeAll();
//        frame.panelBorderIn.add(new JLabel("helo"));
//        frame.panelBorderIn.revalidate();
//        frame.repaint();
        frame.setVisible(true);
    }
}
