package User;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Database.*;

import javax.print.attribute.standard.Finishings;

public class User {
    File userData = new File("../Database/UserData.txt");
    private ArrayList<User> userArrayList = new ArrayList<User>();
    Scanner scanner;
    private String username, password, userPosition, positionID;
    public User(){
        init();
    }

    public User(String username, String password, String userPosition, String positionID){
        this.username = username;
        this.password = password;
        this.userPosition = userPosition;
        this.positionID = positionID;
    }

    private void init(){
        if (!userData.exists()){
            try {
                userData.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            scanner = new Scanner(userData);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split("|", 4);
            User user1 = new User(data[0], data[1], data[2], data[3]);
            userArrayList.add(user1);
        }
        scanner.close();
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getUserPosition(){
        return this.userPosition;
    }

    public String getPositionID(){
        return positionID;
    }

    public ArrayList<User> getUserArrayList(){
        return userArrayList;
    }

    private boolean checkUsername(User user){
        for (User userRow : userArrayList){
            if (user.getUsername().equals(userRow.username))
                return true;
        }
        return false;
    }

    private boolean checkUserPassword(User user){
        for (User userRow : userArrayList){
            if (user.getPassword().equals(userRow.password))
                return true;
        }
        return false;
    }

    private int checkLoginCredentials(User user){
        Pattern pattern = Pattern.compile("\\p{Punct}");
        Matcher matcher = pattern.matcher(user.getUsername());
        while (matcher.find())
            return 1;
        matcher = pattern.matcher(user.getPassword());
        while (matcher.find())
            return 2;
        return 0;
    }

    private void addNewUser(User user){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(userData, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            String writeString = user.getUsername() + "|" + user.getPassword() + "\n";
            fileWriter.write(writeString);

            printWriter.close();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int login(User user){
        if (! checkUsername(user))
            return 1;
        else if (! checkUserPassword(user))
            return 2;
        else
            return 0;
    }

    public int register(User user){
        int check = checkLoginCredentials(user);
        if (checkUsername(user))
            return 1;
        else if (check == 1)
            return 2;
        else if (check == 2)
            return 3;
        else
            addNewUser(user);
        return 0;
    }

}
