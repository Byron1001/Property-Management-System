package Entity;

import Entity.Building_Manager.Building_Manager_Function;
import Entity.Employee.Cleaner;
import Entity.Employee.SecurityGuard;
import Entity.Employee.Technician;
import Entity.Executive.Account_Executive.Account_Executive_Function;
import Entity.Executive.Admin_Executive.Admin_Executive_Function;
import Entity.Executive.Building_Executive.Building_Executive_Function;
import Entity.Resident.Resident;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login {
    private String username;
    private String password;
    private File login_Credentials_txt = new File("src/Database/Login_Credentials.txt");
    public Login(){}

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean check_Punctuation(String line){
        Pattern pattern = Pattern.compile("\\p{Punct}");
        Matcher matcher = pattern.matcher(line);
        while(matcher.find()){
            return true;
        }
        return false;
    }

    public ArrayList<Login> getArrayList() throws FileNotFoundException {
        ArrayList<Login> loginCredentialArrayList = new ArrayList<>();
        FileReader reader = new FileReader(login_Credentials_txt);
        Scanner scanner = new Scanner(reader);
        scanner.nextLine();
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 2);
            loginCredentialArrayList.add(new Login(data[0], data[1]));
        }
        return loginCredentialArrayList;
    }
    public void save_All_Login(ArrayList<Login> loginCredentialArrayList) throws IOException {
        FileWriter fileWriter = new FileWriter(login_Credentials_txt, false);
        fileWriter.write("Username:password\n");
        for (Login login : loginCredentialArrayList){
            String dataLine = getDataString(login);
            fileWriter.write(dataLine);
        }
        fileWriter.close();
    }

    public String getDataString(Login login){
        String[] data = {login.getUsername(), login.getPassword()};
        String dataLine = "";
        for (String dd : data){
            dataLine += dd + ":";
        }
        dataLine = dataLine.substring(0, dataLine.length() - 1);
        dataLine += '\n';
        return dataLine;
    }
    public Login search_Login_Information(String username) throws FileNotFoundException {
        ArrayList<Login> loginCredentialsArrayList = getArrayList();
        Login result = new Login();
        result.setUsername("0");
        for (Login login : loginCredentialsArrayList) {
            if (login.getUsername().equals(username)) {
                result = login;
            }
        }
        return result;
    }

    private String get_Login_Credentials(String username) throws FileNotFoundException {
        ArrayList<Login> loginCredentialsArrayList = getArrayList();
        Login result = new Login();
        result.setUsername("0");
        for (Login login : loginCredentialsArrayList) {
            if (login.getUsername().equals(username)) {
                result = login;
            }
        }
        return result.getPassword();
    }

    public boolean check_Login_Availability(String username) throws FileNotFoundException {
        boolean result = false;
        ArrayList<Login> loginArrayList = getArrayList();
        for (Login login1 : loginArrayList) {
            if (login1.getUsername().equals(username) && !login1.getPassword().equals(""))
                result = true;
        }
        return result;
    }

    public void add_Login(Login login) throws IOException {//add when team no login
        boolean check = check_Punctuation(login.getPassword());
        if (!check_Login_Availability(login.getUsername()) && !check){
            ArrayList<Login> loginArrayList = getArrayList();
            loginArrayList.add(login);
            save_All_Login(loginArrayList);
        }
    }

    public void delete_Login(String login_Username) throws IOException {//delete all login of team or the specific login
        ArrayList<Login> loginArrayList = getArrayList();
        for (Login login : loginArrayList){
            if (login.getUsername().equals(login_Username)){
                loginArrayList.remove(login);
            }
        }
        save_All_Login(loginArrayList);
    }

    public void modify_Login(Login login) throws IOException {
        delete_Login(login.getUsername());
        add_Login(login);
    }

    private Integer check_User_Types(String username) throws FileNotFoundException {
        Integer result = 0;
        HashMap<String, Integer> map = new HashMap<>();
        map.put("BM", 1);
        map.put("AC", 2);
        map.put("AD", 3);
        map.put("BE", 4);
        map.put("VE", 5);
        map.put("SG", 6);
        map.put("VI", 7);
        map.put("CN", 8);
        map.put("TN", 9);
        result = map.getOrDefault(username.substring(0,2), 10);
        if (result == 10){
            if (!new Resident().check_Resident_Availability(username))
                result = -1;
            else
                return result;
        }
        return result;
    }

    public boolean check_Username_Availability(String username) throws IOException, ClassNotFoundException {
        int position = check_User_Types(username);
        if (position == -1)
            return false;
        boolean check_Availability;
        switch (position){
            case 1:
                check_Availability = new Building_Manager_Function.Building_Manager().check_Building_Manager_Availability(username);
                break;
            case 2:
                check_Availability = new Account_Executive_Function.Account_Executive().check_Account_Executive_Availability(username);
                break;
            case 3:
                check_Availability = new Admin_Executive_Function.Admin_Executive().check_Admin_Executive_Availability(username);
                break;
            case 4:
                check_Availability = new Building_Executive_Function.Building_Executive().check_Building_Executive_Availability(username);
                break;
            case 5:
                check_Availability = new Vendor().check_Vendor_Availability(username);
                break;
            case 6:
                check_Availability = new SecurityGuard().check_SecurityGuard_Availability(username);
                break;
            case 7:
                check_Availability = new Visitor_Pass().check_Visitor_Pass_Validity(username);
                break;
            case 8:
                check_Availability = new Cleaner().check_Cleaner_Availability(username);
                break;
            case 9:
                check_Availability = new Technician().check_Technician_Availability(username);
                break;
            case 10:
                check_Availability = new Resident().check_Resident_Availability(username);
                break;
            default:
                check_Availability = false;
        }
        return check_Availability;
    }

    public boolean login(Login login) throws FileNotFoundException {
        if (check_Login_Availability(login.getUsername())){
            if (get_Login_Credentials(login.getUsername()).equals(login.getPassword()))
                return true;
        }
        return false;
    }

    public boolean register(Login login) throws IOException, ClassNotFoundException {
        if (check_Punctuation(login.getPassword()) || check_Login_Availability(login.getUsername()))
            return false;
        boolean check_Availability = check_Username_Availability(login.getUsername());
        if (check_Availability) {
            add_Login(login);
            return true;
        }
        else
            return false;
    }

}
