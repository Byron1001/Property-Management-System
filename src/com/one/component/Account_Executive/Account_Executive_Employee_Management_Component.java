package com.one.component.Account_Executive;

import com.one.component.Admin_Executive.Admin_Executive_Dialog;
import com.one.listener.ActionDoneListener;
import com.one.util.ResultInfoData2Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Account_Executive_Employee_Management_Component extends Box {
    final int WIDTH = 850;
    final int HEIGHT = 600;
    JFrame jf = null;
    private JTable table;
    private Vector<String> titles;
    private Vector<Vector> tableData;
    private DefaultTableModel tableModel;

    public Account_Executive_Employee_Management_Component(JFrame jf){
        //horizontal
        super(BoxLayout.Y_AXIS);
        this.jf = jf;

        JPanel btnPanel = new JPanel();
        Color color = new Color(203,220,217);
        btnPanel.setBackground(color);
        btnPanel.setMaximumSize(new Dimension(WIDTH, 80));
        btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton AddBtn = new JButton("Add");
        JButton ModifyBtn = new JButton("Modify");
        JButton DeleteBtn = new JButton("Delete");

        AddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //dialog (a form)
                new Admin_Executive_Dialog(jf, "add", true, new ActionDoneListener() {
                    @Override
                    public void done(Object result) {

                    }
                }).setVisible(true);
                requestData();
            }
        });

        btnPanel.add(AddBtn);
        btnPanel.add(ModifyBtn);
        btnPanel.add(DeleteBtn);

        this.add(btnPanel);
        String[] ts = {"ID","Name","Description","Date"};
        titles = new Vector<>();
        for(String title: ts){
            titles.add(title);
        }

        tableData = new Vector<>();

        tableModel =  new DefaultTableModel(tableData,titles);
        table = new JTable(tableModel){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollpane = new JScrollPane(table);
        this.add(scrollpane);

        //requestData,take all the data out from the txt into the table
        requestData();
    }
    public void requestData(){
        Vector<Vector> vectors = ResultInfoData2Vector.information();
        tableData.clear();
        for(Vector vector : vectors){
            tableData.add(vector);
        }
        tableModel.fireTableDataChanged();
    }
}
