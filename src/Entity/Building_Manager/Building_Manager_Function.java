package Entity.Building_Manager;

import Entity.Executive.Account_Executive.Account_Executive_Function;
import Entity.Executive.Admin_Executive.Admin_Executive_Function;
import Entity.Executive.Building_Executive.Building_Executive_Function;
import Entity.Executive.Executive;
import Entity.Financial.Payment;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Building_Manager_Function{
    public static class Building_Manager{
        private String buildingManagerID;
        private String name;
        private char gender;
        private String contact_Number;
        private String position = "Building Manager";
        private final File building_Manager_Info_txt = new File("src/Database/Building_Manager_Information.txt");

        public Building_Manager(){}

        public Building_Manager(String buildingManagerID, String name, char gender, String contact_Number) {
            this.buildingManagerID = buildingManagerID;
            this.name = name;
            this.gender = gender;
            this.contact_Number = contact_Number;
        }

        public String getBuildingManagerID() {
            return buildingManagerID;
        }

        public void setBuildingManagerID(String buildingManagerID) {
            this.buildingManagerID = buildingManagerID;
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

        public ArrayList<Building_Manager> getArrayList() throws FileNotFoundException {
            ArrayList<Building_Manager> building_ManagerArrayList = new ArrayList<>();
            FileReader reader = new FileReader(building_Manager_Info_txt);
            Scanner scanner = new Scanner(reader);
            scanner.nextLine();
            while (scanner.hasNextLine()){
                String[] data = scanner.nextLine().split(":", 4);
                building_ManagerArrayList.add(new Building_Manager(data[0], data[1], data[2].charAt(0), data[3]));
            }
            return building_ManagerArrayList;
        }
        public void save_All_Building_Manager(ArrayList<Building_Manager_Function.Building_Manager> building_ManagerArrayList) throws IOException {
            FileWriter fileWriter = new FileWriter(building_Manager_Info_txt, false);
            fileWriter.write("Building Manager ID:Name:Gender:contact_number:position\n");
            for (Building_Manager building_Manager : building_ManagerArrayList){
                String dataLine = getDataString(building_Manager);
                fileWriter.write(dataLine);
            }
            fileWriter.close();
        }

        public String getDataString(Building_Manager_Function.Building_Manager building_Manager){
            String[] data = {building_Manager.getBuildingManagerID(), building_Manager.getName(), Character.toString(building_Manager.getGender()), building_Manager.getContact_Number(), building_Manager.getPosition()};
            String dataLine = "";
            for (String dd : data){
                dataLine += dd + ":";
            }
            dataLine = dataLine.substring(0, dataLine.length() - 1);
            dataLine += '\n';
            return dataLine;
        }
        public Building_Manager search_Executive_Information(String executiveID) throws FileNotFoundException {
            ArrayList<Building_Manager_Function.Building_Manager> building_ManagerArrayList = getArrayList();
            Building_Manager_Function.Building_Manager result = new Building_Manager_Function.Building_Manager();
            result.setBuildingManagerID("0");
            for (Building_Manager_Function.Building_Manager accountExecutive : building_ManagerArrayList) {
                if (accountExecutive.getBuildingManagerID().equals(executiveID)) {
                    result = accountExecutive;
                }
            }
            return result;
        }
        public boolean check_Building_Manager_Availability(String buildingManagerID) throws FileNotFoundException {
            boolean result = false;
            Building_Manager buildingManager = new Building_Manager();
            ArrayList<Building_Manager_Function.Building_Manager> building_ManagerArrayList = buildingManager.getArrayList();
            for (Building_Manager buildingManager1 : building_ManagerArrayList) {
                if (buildingManager1.getBuildingManagerID().equals(buildingManagerID)) {
                    result = true;
                    break;
                }
            }
            return result;
        }
        public void update_Building_Manager_Info(Building_Manager buildingManager, String buildingManagerID) throws IOException {
            ArrayList<Building_Manager> buildingManagerArrayList = buildingManager.getArrayList();
            buildingManagerArrayList.removeIf(buildingManager1 -> buildingManager1.getBuildingManagerID().equals(buildingManagerID));
            buildingManagerArrayList.add(buildingManager);
            buildingManager.save_All_Building_Manager(buildingManagerArrayList);
        }

        public void add_Account_Executive(Account_Executive_Function.Account_Executive accountExecutive) throws IOException {
            ArrayList<Account_Executive_Function.Account_Executive> account_executiveArrayList = accountExecutive.getArrayList();
            if (!(accountExecutive.check_Account_Executive_Availability(accountExecutive.getExecutiveID())))
                account_executiveArrayList.add(accountExecutive);
            accountExecutive.save_All_Account_Executive(account_executiveArrayList);
        }

        public void delete_Account_Executive(String executiveID) throws IOException {
            Account_Executive_Function.Account_Executive accountExecutive = new Account_Executive_Function.Account_Executive();
            ArrayList<Account_Executive_Function.Account_Executive> account_executiveArrayList = accountExecutive.getArrayList();
            if (accountExecutive.check_Account_Executive_Availability(executiveID)){
                account_executiveArrayList.removeIf(accountExecutive1 -> accountExecutive1.getExecutiveID().equals(executiveID));
            }
            accountExecutive.save_All_Account_Executive(account_executiveArrayList);
        }

        public void modify_Account_Executive(Account_Executive_Function.Account_Executive accountExecutive, String executiveID) throws IOException {
            delete_Account_Executive(executiveID);
            add_Account_Executive(accountExecutive);
        }

        public void add_Building_Executive(Building_Executive_Function.Building_Executive buildingExecutive) throws IOException {
            ArrayList<Building_Executive_Function.Building_Executive> buildingExecutiveArrayList = buildingExecutive.getArrayList();
            if (!(buildingExecutive.check_Building_Executive_Availability(buildingExecutive.getExecutiveID())))
                buildingExecutiveArrayList.add(buildingExecutive);
            buildingExecutive.save_All_Building_Executive(buildingExecutiveArrayList);
        }

        public void delete_Building_Executive(String executiveID) throws IOException {
            Building_Executive_Function.Building_Executive buildingExecutive = new Building_Executive_Function.Building_Executive();
            ArrayList<Building_Executive_Function.Building_Executive> buildingExecutiveArrayList = buildingExecutive.getArrayList();
            if (buildingExecutive.check_Building_Executive_Availability(executiveID)){
                buildingExecutiveArrayList.removeIf(buildingExecutive1 -> buildingExecutive1.getExecutiveID().equals(executiveID));
            }
            buildingExecutive.save_All_Building_Executive(buildingExecutiveArrayList);
        }

        public void modify_Building_Executive(Building_Executive_Function.Building_Executive buildingExecutive, String executiveID) throws IOException {
            delete_Account_Executive(executiveID);
            add_Building_Executive(buildingExecutive);
        }

        public void add_Admin_Executive(Admin_Executive_Function.Admin_Executive adminExecutive) throws IOException {
            ArrayList<Admin_Executive_Function.Admin_Executive> adminExecutiveArrayList = adminExecutive.getArrayList();
            if (!(adminExecutive.check_Admin_Executive_Availability(adminExecutive.getExecutiveID())))
                adminExecutiveArrayList.add(adminExecutive);
            adminExecutive.save_All_Admin_Executive(adminExecutiveArrayList);
        }

        public void delete_Admin_Executive(String executiveID) throws IOException {
            Admin_Executive_Function.Admin_Executive adminExecutive = new Admin_Executive_Function.Admin_Executive();
            ArrayList<Admin_Executive_Function.Admin_Executive> adminExecutiveArrayList = adminExecutive.getArrayList();
            if (adminExecutive.check_Admin_Executive_Availability(executiveID)){
                adminExecutiveArrayList.removeIf(adminExecutive1 -> adminExecutive1.getExecutiveID().equals(executiveID));
            }
            adminExecutive.save_All_Admin_Executive(adminExecutiveArrayList);
        }

        public void modify_Admin_Executive(Admin_Executive_Function.Admin_Executive adminExecutive, String executiveID) throws IOException {
            delete_Admin_Executive(executiveID);
            add_Admin_Executive(adminExecutive);
        }

        public Executive get_Executive_Info(String executiveID) throws FileNotFoundException {
            Executive executive = new Executive();
            return executive.search_Executive_Info(executiveID);
        }

        public Building_Manager get_Building_Manager_Info(String buildingManagerID) throws FileNotFoundException {
            Building_Manager buildingManager = new Building_Manager();
            buildingManager.setBuildingManagerID("0");
            if (check_Building_Manager_Availability(buildingManagerID)){
                ArrayList<Building_Manager> buildingManagerArrayList = buildingManager.getArrayList();
                for (Building_Manager buildingManager1 : buildingManagerArrayList){
                    if (buildingManager1.getBuildingManagerID().equals(buildingManagerID))
                        return buildingManager1;
                }
            }
            return buildingManager;
        }

        private static int get_Fund_Amount() throws FileNotFoundException {
            File fund_Amount_txt = new File("src/Database/Fund.txt");
            Scanner scanner = new Scanner(fund_Amount_txt);
            String number = scanner.nextLine().split(":", 2)[1];
            return Integer.parseInt(number);
        }

        public static class Operation{
            private String operationID;
            private String building_Manager_ID;
            private String operationTitle;
            private String description;
            private int budget_Amount;
            private final File operation_Info_txt = new File("src/Database/Operation_Information.txt");

            public Operation(){}

            public Operation(String operationID, String building_Manager_ID, String operationTitle, String description, int budget_Amount) {
                this.operationID = operationID;
                this.building_Manager_ID = building_Manager_ID;
                this.operationTitle = operationTitle;
                this.description = description;
                this.budget_Amount = budget_Amount;
            }

            public String getOperationID() {
                return operationID;
            }

            public void setOperationID(String operationID) {
                this.operationID = operationID;
            }

            public String getBuilding_Manager_ID() {
                return building_Manager_ID;
            }

            public void setBuilding_Manager_ID(String building_Manager_ID) {
                this.building_Manager_ID = building_Manager_ID;
            }

            public String getOperationTitle() {
                return operationTitle;
            }

            public void setOperationTitle(String operationTitle) {
                this.operationTitle = operationTitle;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getBudget_Amount() {
                return budget_Amount;
            }

            public void setBudget_Amount(int budget_Amount) {
                this.budget_Amount = budget_Amount;
            }

            public ArrayList<Operation> getArrayList() throws FileNotFoundException {
                ArrayList<Operation> operationArrayList = new ArrayList<>();
                FileReader reader = new FileReader(operation_Info_txt);
                Scanner scanner = new Scanner(reader);
                scanner.nextLine();
                while (scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(":", 5);
                    System.out.println(data[3]);
                    operationArrayList.add(new Operation(data[0], data[1], data[2], data[3], Integer.parseInt(data[4])));
                }
                return operationArrayList;
            }
            public String[] getStringArray(Operation operation){
                return new String[]{operation.getOperationID(), operation.getBuilding_Manager_ID(), operation.getOperationTitle(), operation.getDescription(), Integer.toString(operation.getBudget_Amount())};
            }
            public void save_All_Operation(ArrayList<Operation> operationArrayList) throws IOException {
                FileWriter fileWriter = new FileWriter(operation_Info_txt, false);
                fileWriter.write("operation ID:building manager ID:operation title:description:budget amount\n");
                for (Operation operation : operationArrayList){
                    String dataLine = getDataString(operation);
                    fileWriter.write(dataLine);
                }
                fileWriter.close();
            }

            public boolean check_Operation_Availability(String operationID) throws FileNotFoundException {
                boolean result = false;
                ArrayList<Operation> operationArrayList = getArrayList();
                for (Operation operation : operationArrayList){
                    if (operation.getOperationID().equals(operationID)) {
                        result = true;
                        break;
                    }
                }
                return result;
            }

            public String getDataString(Operation operation){
                String[] data = {operation.getOperationID(), operation.getBuilding_Manager_ID(), operation.getOperationTitle(), operation.getDescription(), Integer.valueOf(operation.getBudget_Amount()).toString()};
                String dataLine = "";
                for (String dd : data){
                    dataLine += dd + ":";
                }
                dataLine = dataLine.substring(0, dataLine.length() - 1);
                dataLine += '\n';
                return dataLine;
            }

            public void pay_for_Operation(Operation operation) throws IOException {
                Payment.Expenses expenses = new Payment.Expenses();
                Payment.Expenses.add_Expenses(new Payment.Expenses(expenses.get_Auto_ExpensesID(), operation.getOperationID(), operation.getBuilding_Manager_ID(), operation.getBudget_Amount(), LocalDate.now(), operation.getOperationTitle(), operation.getDescription()));
                operation.setBudget_Amount(0);
                modify_Operation(operation, operation.getOperationID());
            }

            public int get_All_Available_Operation_Budget_Amount() throws FileNotFoundException {
                ArrayList<Operation> operationArrayList = getArrayList();
                int total = 0;
                for (Operation operation : operationArrayList){
                    total += operation.getBudget_Amount();
                }
                return total;
            }

            public boolean check_Fund_Amount_Enough(int budget_Amount) throws FileNotFoundException {
                int fund = get_Fund_Amount();
                int budget_total = get_All_Available_Operation_Budget_Amount();
                int number = fund - budget_total - budget_Amount;
                return number > 0;
            }

            public String get_Auto_OperationID() throws FileNotFoundException {
                FileReader fileReader = new FileReader(operation_Info_txt);
                Scanner scanner = new Scanner(fileReader);
                scanner.nextLine();
                Integer num = 0;
                while (scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(":", 5);
                    String number = data[0].substring(2);
                    num = Integer.parseInt(number);
                    num += 1;
                }
                return "OP" + num;
            }

            public void add_Operation(Operation operation) throws IOException {
                if (check_Fund_Amount_Enough(operation.getBudget_Amount())){
                    ArrayList<Operation> operationArrayList = getArrayList();
                    operationArrayList.add(operation);
                    save_All_Operation(operationArrayList);
                }
            }

            public void delete_Operation(String operationID) throws IOException {
                Operation operation = new Operation();
                ArrayList<Operation> operationArrayList = operation.getArrayList();
                if (check_Operation_Availability(operation.getOperationID())){
                    operationArrayList.removeIf(operation1 -> operation1.getOperationID().equals(operationID));
                }
                save_All_Operation(operationArrayList);
            }

            public void modify_Operation(Operation operation, String operationID) throws IOException {
                delete_Operation(operationID);
                add_Operation(operation);
            }

        }

        public static class Team_Leader{
            private String leader_Username;
            private String team;
            private String position;
            private final File team_Leader_Info_txt = new File("src/Database/Team_Leader_Information.txt");
            public Team_Leader(){}

            public Team_Leader(String leader_Username, String team, String position) {
                this.leader_Username = leader_Username;
                this.team = team;
                this.position = position;
            }

            public String getLeader_Username() {
                return leader_Username;
            }

            public void setLeader_Username(String leader_Username) {
                this.leader_Username = leader_Username;
            }

            public String getTeam() {
                return team;
            }

            public void setTeam(String team) {
                this.team = team;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public ArrayList<Team_Leader> getArrayList() throws FileNotFoundException {
                ArrayList<Team_Leader> teamLeaderArrayList = new ArrayList<>();
                FileReader reader = new FileReader(team_Leader_Info_txt);
                Scanner scanner = new Scanner(reader);
                scanner.nextLine();
                while (scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(":", 3);
                    teamLeaderArrayList.add(new Team_Leader(data[0], data[1], data[2]));
                }
                return teamLeaderArrayList;
            }
            public void save_All_Team_Leader(ArrayList<Team_Leader> teamLeaderArrayList) throws IOException {
                FileWriter fileWriter = new FileWriter(team_Leader_Info_txt, false);
                fileWriter.write("leader username:team:position\n");
                for (Team_Leader leader : teamLeaderArrayList){
                    String dataLine = getDataString(leader);
                    fileWriter.write(dataLine);
                }
                fileWriter.close();
            }

            public String getDataString(Team_Leader leader){
                String[] data = {leader.getLeader_Username(), leader.getTeam(), leader.getPosition()};
                String dataLine = "";
                for (String dd : data){
                    dataLine += dd + ":";
                }
                dataLine = dataLine.substring(0, dataLine.length() - 1);
                dataLine += '\n';
                return dataLine;
            }
            public Team_Leader search_Team_Leader_Information(String leader_Username) throws FileNotFoundException {
                ArrayList<Team_Leader> leaderArrayList = getArrayList();
                Team_Leader result = new Team_Leader();
                result.setLeader_Username("0");
                for (Team_Leader leader : leaderArrayList) {
                    if (leader.getLeader_Username().equals(leader_Username) || leader.getTeam().equals(leader_Username)) {
                        result = leader;
                    }
                }
                return result;
            }
            public boolean check_Team_Leader_Availability(String team, String position) throws FileNotFoundException {
                boolean result = false;
                ArrayList<Team_Leader> leaderArrayList = getArrayList();
                for (Team_Leader leader1 : leaderArrayList) {
                    if (leader1.getTeam().equals(team) && leader1.getPosition().equals(position)) {
                        result = true;
                        break;
                    }
                }
                return result;
            }

            public void add_Team_Leader(Team_Leader leader) throws IOException {//add when team no leader
                ArrayList<Team_Leader> leaderArrayList = getArrayList();
                leaderArrayList.add(leader);
                save_All_Team_Leader(leaderArrayList);
            }

            public void delete_Team_Leader(String leader_Username) throws IOException {//delete specific team leader
                ArrayList<Team_Leader> leaderArrayList = getArrayList();
                ArrayList<Team_Leader> leaderArrayList1 = new ArrayList<>();
                for (Team_Leader leader : leaderArrayList){
                    if (!leader.getLeader_Username().equals(leader_Username)){
                        leaderArrayList1.add(leader);
                    }
                }
                save_All_Team_Leader(leaderArrayList1);
            }

            public void modify_Team_Leader(Team_Leader leader) throws IOException {
                delete_Team_Leader(leader.getLeader_Username());
                add_Team_Leader(leader);
            }

            public String[] getStringArray(Team_Leader teamLeader){
                return new String[]{teamLeader.getLeader_Username(), teamLeader.getTeam(), teamLeader.getPosition()};
            }
        }
    }
    public static class Button extends JButton {
        public Color color1 = Color.decode("#283c86");
        public Color color2 = Color.decode("#45a247");
        public Button(String title){
            super(title);
            setContentAreaFilled(false);
            setBackground(Color.decode("#283c86"));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gradientPaint = new GradientPaint(0, 0, color1,getWidth(), 0, color2);
            g2.setPaint(gradientPaint);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            super.paintComponent(g);
        }
    }

}
