package UIPackage.swing;

import UIPackage.Model.StatusType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Table extends JTable {
    public String[] column = {"Name", "Email", "User type", "Joined", "Status"};
    public Object[] data = {"Aikka", "Gmail.com", "Admin", 2001, "On1"};
    public Object[] data2 = {"Aikka", "Gmail.com", "Admin", 2001, "Online"};
    public DefaultTableModel model = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    public int preferredColumnWidth = 250;
    public boolean statusAllowed = true;
    public Table (){
        setModel(model);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                for (int i = 0;i <= column;i++){
                    getColumnModel().getColumn(i).setPreferredWidth(preferredColumnWidth);
                    setHorizontalAlignment(JLabel.CENTER);
                }
//                for (int i = 0;i <= row;i++){
//                    getModel().setHorizontalAlignment(JLabel.CENTER);
//                }
                if (column != getColumnModel().getColumnCount() - 1 || !statusAllowed){
                    Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    comp.setBackground(Color.WHITE);
                    setBorder(noFocusBorder);
                    if (isSelected){
                        comp.setForeground(new Color(13,113,182));
                    } else{
                        comp.setForeground(new Color(102,102,102));
                    }
                    return comp;
                } else {
                    String[] approve = {"APPROVED", "paid", "OK", "Approved", "solved", "RECEIVED", "solved", "done"};
                    String[] reject = {"REJECT", "unpaid", "NO", "Disapproved", "unsolved", "Unsolved"};
                    String[] pending = {"PENDING", "WAIT", "undone"};
                    StatusType type = StatusType.REJECT;
                    String outputWord = "";
                    for (String aa : approve){
                        if (aa.equals((String) value)){
                            type = StatusType.APPROVED;
                            outputWord = aa.toUpperCase();
                        }
                    }
                    for (String rr : reject){
                        if (rr.equals((String) value)){
                            type = StatusType.REJECT;
                            outputWord = rr.toUpperCase();
                        }
                    }
                    for (String pp : pending){
                        if (pp.equals((String) value)){
                            type = StatusType.PENDING;
                            outputWord = pp.toUpperCase();
                        }
                    }
//                    StatusType type = StatusType.valueOf((String) value);
                    CellStatus cell = new CellStatus(type, outputWord);
                    return cell;
                }
//                return new JLabel("error");
            }
        });

        setGridColor(new Color(230,230,230));
        setBorder(new EmptyBorder(10,5,10,5));
        setShowHorizontalLines(true);
        setRowHeight(40);
        setShowVerticalLines(false);
    }

    public void addRow(Object[] row){
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(row);
    }
}
