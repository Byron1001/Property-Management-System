package Entity;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Unit {
    private int floor;
    private String unitID;
    private int complete_Year;
    private String furnish;
    private String parking_Unit;
    private String owner_Username;
    private String resident_Username;
    private static final File unit_Information_txt = new File("src/Database/Unit_Information.txt");
    public Unit() {}

    public Unit(int floor, String unitID, int complete_Year, String furnish, String parking_Unit, String owner_Username, String resident_Username) {
        this.floor = floor;
        this.unitID = unitID;
        this.complete_Year = complete_Year;
        this.furnish = furnish;
        this.parking_Unit = parking_Unit;
        this.owner_Username = owner_Username;
        this.resident_Username = resident_Username;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public int getComplete_Year() {
        return complete_Year;
    }

    public void setComplete_Year(int complete_Year) {
        this.complete_Year = complete_Year;
    }

    public String getFurnish() {
        return furnish;
    }

    public void setFurnish(String furnish) {
        this.furnish = furnish;
    }

    public String getParking_Unit() {
        return parking_Unit;
    }

    public void setParking_Unit(String parking_Unit) {
        this.parking_Unit = parking_Unit;
    }

    public String getOwner_Username() {
        return owner_Username;
    }

    public void setOwner_Username(String owner_Username) {
        this.owner_Username = owner_Username;
    }

    public String getResident_Username() {
        return resident_Username;
    }

    public void setResident_Username(String resident_Username) {
        this.resident_Username = resident_Username;
    }

    public ArrayList<Unit> getArrayList() throws FileNotFoundException {
        ArrayList<Unit> unitArrayList = new ArrayList<>();
        FileReader reader = new FileReader(unit_Information_txt);
        Scanner scanner = new Scanner(reader);
        scanner.nextLine();
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 8);
            unitArrayList.add(new Unit(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), data[3], data[4], data[5], data[6]));
        }
        return unitArrayList;
    }

    public String[] getStringArray(Unit unit){
        return new String[]{Integer.toString(unit.getFloor()), unit.getUnitID(), Integer.toString(unit.getComplete_Year()), unit.getFurnish(), unit.getParking_Unit(), unit.getOwner_Username(), unit.getResident_Username()};
    }

    public void sort_All_Unit() throws IOException {
        ArrayList<Unit> unitArrayList = getArrayList();
        for (int i = 0; i < unitArrayList.size() - 1;i++){
            for (int j = 0; j < unitArrayList.size() - i - 1;j++){
                if (unitArrayList.get(j).getUnitID().compareTo(unitArrayList.get(j + 1).getUnitID()) > 0){
                    unitArrayList = switch_Unit(unitArrayList, j);
                }
            }
        }
        save_All_Unit(unitArrayList);
    }

    private static ArrayList<Unit> switch_Unit(ArrayList<Unit> unitArrayList, int index1){
        Unit temp = unitArrayList.get(index1);
        unitArrayList.set(index1, unitArrayList.get(index1 + 1));
        unitArrayList.set(index1 + 1, temp);
        return unitArrayList;
    }

    public void save_All_Unit(ArrayList<Unit> unitArrayList) throws IOException {
        FileWriter fileWriter = new FileWriter(unit_Information_txt, false);
        fileWriter.write("floor:unitID:complete_Year:furnish:parking_Unit:owner_Username:resident_Username\n");
        for (Unit unit : unitArrayList){
            String[] data = {Integer.toString(unit.getFloor()), unit.getUnitID(), Integer.toString(unit.getComplete_Year()), unit.getFurnish(), unit.getParking_Unit(), unit.getOwner_Username(), unit.getResident_Username()};
            String dataLine = "";
            for (String dd : data){
                dataLine += dd + ":";
            }
            dataLine = dataLine.substring(0, dataLine.length() - 1);
            dataLine += '\n';
            fileWriter.write(dataLine);
        }
        fileWriter.close();
    }

    public String getDataString(Unit unit){
        String[] data = {Integer.toString(unit.getFloor()), unit.getUnitID(), Integer.toString(unit.getComplete_Year()), unit.getFurnish(), unit.getParking_Unit(), unit.getOwner_Username(), unit.getResident_Username()};
        String dataLine = "";
        for (String dd : data){
            dataLine += dd + ":";
        }
        dataLine = dataLine.substring(0, dataLine.length() - 1);
        dataLine += '\n';
        return dataLine;
    }

    public Unit search_Unit_Information(String unitID) throws FileNotFoundException {
        ArrayList<Unit> unitArrayList = getArrayList();
        Unit result = new Unit();
        result.setUnitID("0");
        for (Unit u : unitArrayList){
            if (u.getUnitID().equals(unitID))
                result = u;
        }
        return result;
    }

    public boolean check_Unit_Availability(String unitID) throws FileNotFoundException {
        boolean result = false;
        Unit unit = new Unit();
        ArrayList<Unit> unitArrayList = unit.getArrayList();
        for (Unit uni : unitArrayList) {
            if (uni.getUnitID().equals(unitID)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean check_Parking_Unit_Availability(String parking_Unit) throws FileNotFoundException {
        boolean result = false;
        Unit unit = new Unit();
        ArrayList<Unit> unitArrayList = unit.getArrayList();
        for (Unit uni : unitArrayList) {
            if (uni.getParking_Unit().equals(parking_Unit)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
