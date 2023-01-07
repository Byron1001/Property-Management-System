package com.one.util;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class ResultInfoData2Vector {
    public static Vector<Vector> information(){
        Vector<Vector> b = new Vector<Vector>();
        ArrayList<String> temp = new ArrayList<String>();               //Open Visitors text file and extract all of them
        try {
            File myObj = new File("JavaAssignment(Version 2.0)\\src\\com\\one\\database\\Visitors.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                temp.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        Vector<String> a = new Vector<>();
        for(int i=0 ; i<temp.size() ; i++){
            a = new Vector<>();
            String[] temp1 = temp.get(i).split("\\|");
            for(int j=0 ; j<temp1.length ; j++){
                a.addElement(temp1[j]);
            }
            b.addElement(a);
        }
        System.out.println(b);
        return b;
    }
    public static void main(String args[]){

    }
}
