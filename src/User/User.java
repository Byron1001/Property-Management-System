package User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Database.*;

import javax.print.attribute.standard.Finishings;

public class User {
    File userData = new File("../Database/UserData.txt");
    private ArrayList<User> userArrayList = new ArrayList<User>();
    Scanner scanner;
    private String data;
    private String username, password;
    public User(){
        initial();
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    private void initial(){
        if (!userData.exists()){
            try {
                userData.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                scanner = new Scanner(userData);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (scanner.hasNextLine()){
                data = scanner.nextLine();
                String[] row = data.split("|", 2);
                User user1 = new User(row[0], row[1]);
                userArrayList.add(user1);
            }
        }
    }

    private boolean checkUsername(String username){
        for (User userRow : userArrayList){
            if (username.equals(userRow.username))
                return true;
        }
        return false;
    }

    private boolean checkUserPassword(String password){
        for (User userRow : userArrayList){
            if (password.equals(userRow.password))
                return true;
        }
        return false;
    }

    public int checkLogin(String username, String password){
        if (! checkUsername(username))
            return 1;
        else if (! checkUserPassword(password))
            return 2;
        else
            return 0;
    }
}
