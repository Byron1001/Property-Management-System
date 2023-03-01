package Entity.Executive;

import Entity.Executive.Account_Executive.Account_Executive_Function;
import Entity.Executive.Admin_Executive.Admin_Executive_Function;
import Entity.Executive.Building_Executive.Building_Executive_Function;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class Executive {
    private String executiveID;
    private String name;
    private char gender;
    private String contact_Number;
    private String position;

    public Executive(){}

    public void set_Info(String executiveID, String name, char gender, String contact_Number, String position){
        this.executiveID = executiveID;
        this.name = name;
        this.gender = gender;
        this.contact_Number = contact_Number;
        this.position = position;
    }

    public String getExecutiveID() {
        return executiveID;
    }

    public void setExecutiveID(String executiveID) {
        this.executiveID = executiveID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getContact_Number() {
        return contact_Number;
    }

    public void setContact_Number(String contact_Number) {
        this.contact_Number = contact_Number;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer check_Executive_Position(String executiveID){
        Integer result;
        HashMap<String, Integer> map = new HashMap<>();
        map.put("AC", 1);
        map.put("AD", 2);
        map.put("BE", 3);
        result = map.getOrDefault(executiveID.substring(0,2), -1);
        return result;
    }

    public String[] getStringArray(Executive executive){
        return new String[]{executive.getExecutiveID(), executive.getName(), Character.toString(executive.getGender()), executive.getContact_Number(), executive.getPosition()};
    }

    public Executive search_Executive_Info(String executiveID) throws FileNotFoundException {
        int result = check_Executive_Position(executiveID);
        Executive executive = new Executive();
        switch (result) {
            case 1:
                executive = new Account_Executive_Function.Account_Executive().get_Account_Executive_Info(executiveID);
                break;
            case 2:
                executive = new Admin_Executive_Function.Admin_Executive().get_Admin_Executive_Info(executiveID);
                break;
            case 3:
                executive = new Building_Executive_Function.Building_Executive().get_Building_Executive_Info(executiveID);
                break;
            default:
                executive.setExecutiveID("0");
        }
        return executive;
    }
}
