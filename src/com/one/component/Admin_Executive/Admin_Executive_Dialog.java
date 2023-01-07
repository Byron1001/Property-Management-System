package com.one.component.Admin_Executive;
import com.one.listener.ActionDoneListener;
import com.one.util.ScreenUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class Admin_Executive_Dialog extends JDialog{
    final int WIDTH = 500;
    final int HEIGHT  = 300;
    private ActionDoneListener listener;
    public Admin_Executive_Dialog(JFrame jf, String title, boolean isModel, ActionDoneListener listener){
        super(jf,title,isModel );
        this.listener = listener;
        this.setBounds((ScreenUtils.getScreenWidth()-WIDTH)/2,(ScreenUtils.getScreenHeight()-HEIGHT)/2,WIDTH,HEIGHT);

        Box vBox = Box.createVerticalBox();

        //name
        Box nameBox = Box.createHorizontalBox();
        JLabel nameLabel = new JLabel("  Name          ");
        JTextField nameField = new JTextField(15);
        nameBox.add(nameLabel);
        nameBox.add(Box.createHorizontalStrut(20));
        nameBox.add(nameField);


        //visitor ID
        Box IDBox = Box.createHorizontalBox();
        JLabel IDLabel = new JLabel("  Visitor ID    ");
        JTextField IDField = new JTextField(15);
        IDBox.add(IDLabel);
        IDBox.add(Box.createHorizontalStrut(20));
        IDBox.add(IDField);


        //begin date
        Box bBox = Box.createHorizontalBox();
        JLabel bLabel = new JLabel("  Begin Date ");
        JTextField bField = new JTextField(15);
        bBox.add(bLabel);
        bBox.add(Box.createHorizontalStrut(20));
        bBox.add(bField);


        //end date
        Box eBox = Box.createHorizontalBox();
        JLabel eLabel = new JLabel("  End Date     ");
        JTextField eField = new JTextField(15);
        eBox.add(eLabel);
        eBox.add(Box.createHorizontalStrut(20));
        eBox.add(eField);


        //add button
        Box btnBox = Box.createHorizontalBox();
        JButton addBtn = new JButton("Add");
        btnBox.add(addBtn);

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = IDField.getText().trim();
                String Name = nameField.getText().trim();
                String bdate = bField.getText().trim();
                String edate = eField.getText().trim();
                // 放进file 里面(haven't completed)
                String temp = ID + "|" + Name + "|" + bdate + "|" + edate + "|\n" ;
                try {
                    FileWriter myWriter = new FileWriter("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Visitors.txt",true);
                    //String temp = ";
                    myWriter.write( temp);
                    myWriter.close();
                    JOptionPane.showMessageDialog(jf,"Successful");
                    dispose();
                    System.out.println("Successfully wrote to the file.");
                    listener.done(null);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(jf,"failed, try again later");
                    System.out.println("An error occurred.");
                    ex.printStackTrace();
                }
                /* if success
                JOptionPane.showMessageDialog(jf,"successful")
                dispose()
                else
                JOptionPane.showMessageDialog(jf,"failed, try again later")


                 */
            }
        });

        //Assemble all
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(IDBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(nameBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(bBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(eBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(btnBox);
        vBox.add(Box.createVerticalStrut(20));

        Box hBox = Box.createHorizontalBox();
        hBox.add(Box.createHorizontalStrut(20));
        hBox.add(vBox);
        hBox.add(Box.createHorizontalStrut(20));

        this.add(hBox);
    }

}
