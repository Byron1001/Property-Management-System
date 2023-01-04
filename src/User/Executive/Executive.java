package User.Executive;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Executive {
    public String executiveID, executiveName, executivePosition;
    private int executiveSalary;
    private ArrayList<Executive> executiveArrayList = new ArrayList<>();
    File executiveData = new File("../../Database/Executive Data.txt");
    Scanner scanner;
    public Executive(){
        init();
    }

    public Executive(String executiveID, String executiveName, String executivePosition, int executiveSalary){
        this.executiveID = executiveID;
        this.executiveName = executiveName;
        this.executivePosition = executivePosition;
        this.executiveSalary = executiveSalary;
    }

    public int getExecutiveSalary(){
        return executiveSalary;
    }

    public ArrayList<Executive> getExecutiveArrayList(){
        return executiveArrayList;
    }

    private void init(){
        if (executiveData.exists()){
            try {
                executiveData.createNewFile();
                scanner = new Scanner(executiveData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split("|", 4);
            Executive executive1 = new Executive(data[0], data[1], data[2], Integer.parseInt(data[3]));
            executiveArrayList.add(executive1);
        }
    }

    

}
