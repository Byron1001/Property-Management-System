package Entity.Financial;

import Entity.Login.Login;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Statement {
    private String statementID;
    private String issuer_Position;
    private LocalDate date;
    private String description;
    private String receiverID;
    private final File statement_txt = new File("src/Database/Statement.txt");

    public Statement(){}

    public Statement(String statementID, String issuer_Position, LocalDate date, String description, String receiverID) {
        this.statementID = statementID;
        this.issuer_Position = issuer_Position;
        this.date = date;
        this.description = description;
        this.receiverID = receiverID;
    }

    public String getStatementID() {
        return statementID;
    }

    public void setStatementID(String statementID) {
        this.statementID = statementID;
    }

    public String getIssuer_Position() {
        return issuer_Position;
    }

    public void setIssuer_Position(String issuer_Position) {
        this.issuer_Position = issuer_Position;
    }

    public LocalDate getDate(){
        return date;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiverID(){
        return receiverID;
    }

    public void setReceiverID(String receiverID){
        this.receiverID = receiverID;
    }

    public ArrayList<Statement> getArrayList() throws FileNotFoundException {
        ArrayList<Statement> statementArrayList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        Scanner scanner = new Scanner(statement_txt);
        scanner.nextLine();
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 5);
            Statement statement = new Statement(data[0], data[1], LocalDate.parse(data[2], formatter), data[3], data[4]);
            statementArrayList.add(statement);
        }
        scanner.close();
        return statementArrayList;
    }

    public String[] getStringArray(Statement statement){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        return new String[]{statement.getStatementID(), statement.getIssuer_Position(), statement.getDate().format(formatter), statement.getDescription(), statement.getReceiverID()};
    }

    public String get_Auto_StatementID() throws FileNotFoundException {
        FileReader fileReader = new FileReader(statement_txt);
        Scanner scanner = new Scanner(fileReader);
        scanner.nextLine();
        Integer num = 0;
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 5);
            String number = data[0].substring(5);
            num = Integer.parseInt(number);
            num += 1;
        }
        return "State" + num;
    }

    public String getDataString(Statement statement){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        String[] data = {statement.getStatementID(), statement.getIssuer_Position(), statement.getDate().format(formatter), statement.getDescription(), statement.getReceiverID()};
        String dataLine = "";
        for (String dd : data){
            dataLine += dd + ":";
        }
        dataLine = dataLine.substring(0, dataLine.length() - 1);
        dataLine += '\n';
        return dataLine;
    }

    public void save_All_Statement(ArrayList<Statement> statementArrayList) throws IOException {
        FileWriter fileWriter = new FileWriter(statement_txt, false);
        fileWriter.write("statement ID:issuer Position:date:description:receiverID\n");
        for (Statement statement : statementArrayList){
            fileWriter.write(statement.getDataString(statement));
        }
        fileWriter.close();
    }

    public ArrayList<Statement> get_Statement_for_Receiver(String receiverID) throws FileNotFoundException {
        ArrayList<Statement> statementArrayList = new Statement().getArrayList();
        ArrayList<Statement> statementArrayList1 = new ArrayList<>();
        for (Statement statement : statementArrayList){
            if (statement.getReceiverID().equals(receiverID))
                statementArrayList1.add(statement);
        }
        return statementArrayList1;
    }

    public ArrayList<String> get_Receiver_List() throws FileNotFoundException {
        ArrayList<String> receiverList = new ArrayList<>();
        ArrayList<Login> loginArrayList = new Login().getArrayList();
        for (Login login : loginArrayList){
            receiverList.add(login.getUsername());
        }
        return receiverList;
    }
}
