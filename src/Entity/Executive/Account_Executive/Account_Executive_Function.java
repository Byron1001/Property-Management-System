package Entity.Executive.Account_Executive;

import Entity.Executive.Executive;
import Entity.Financial.Invoice;
import Entity.Financial.Payment;
import Entity.Financial.Statement;
import Entity.Unit;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Account_Executive_Function {
    public static class Account_Executive extends Executive {
        private String position = "Account Executive";
        private File account_Executive_Info_txt = new File("src/Database/Account_Executive_Information.txt");
        public Account_Executive(){}
        public Account_Executive(String account_Executive_ID, String name, char gender, String contact_Number){
            super();
            super.set_Info(account_Executive_ID, name, gender, contact_Number, position);
        }

        public ArrayList<Account_Executive_Function.Account_Executive> getArrayList() throws FileNotFoundException {
            ArrayList<Account_Executive_Function.Account_Executive> accountExecutiveArrayList = new ArrayList<>();
            FileReader reader = new FileReader(account_Executive_Info_txt);
            Scanner scanner = new Scanner(reader);
            scanner.nextLine();
            while (scanner.hasNextLine()){
                String[] data = scanner.nextLine().split(":", 5);
                accountExecutiveArrayList.add(new Account_Executive_Function.Account_Executive(data[0], data[1], data[2].charAt(0), data[3]));
            }
            return accountExecutiveArrayList;
        }
        public void save_All_Account_Executive(ArrayList<Account_Executive_Function.Account_Executive> accountExecutiveArrayList) throws IOException {
            FileWriter fileWriter = new FileWriter(account_Executive_Info_txt, false);
            fileWriter.write("executiveID:Name:Gender:contact_number:position\n");
            for (Account_Executive_Function.Account_Executive accountExecutive : accountExecutiveArrayList){
                String[] data = {accountExecutive.getExecutiveID(), accountExecutive.getName(), Character.toString(accountExecutive.getGender()), accountExecutive.getContact_Number(), accountExecutive.getPosition()};
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

        public String getDataString(Account_Executive_Function.Account_Executive accountExecutive){
            String[] data = {accountExecutive.getExecutiveID(), accountExecutive.getName(), Character.toString(accountExecutive.getGender()), accountExecutive.getContact_Number(), accountExecutive.getPosition()};
            String dataLine = "";
            for (String dd : data){
                dataLine += dd + ":";
            }
            dataLine = dataLine.substring(0, dataLine.length() - 1);
            dataLine += '\n';
            return dataLine;
        }
        public Account_Executive_Function.Account_Executive get_Account_Executive_Info(String executiveID) throws FileNotFoundException {
            ArrayList<Account_Executive_Function.Account_Executive> accountExecutiveArrayList = getArrayList();
            Account_Executive result = new Account_Executive();
            result.setExecutiveID("0");
            for (Account_Executive accountExecutive : accountExecutiveArrayList) {
                if (accountExecutive.getExecutiveID().equals(executiveID)) {
                    result = accountExecutive;
                }
            }
            return result;
        }
        public boolean check_Account_Executive_Availability(String executiveID) throws FileNotFoundException {
            boolean result = false;
            Account_Executive accountExecutive = new Account_Executive();
            ArrayList<Account_Executive> accountExecutiveArrayList = accountExecutive.getArrayList();
            for (Account_Executive accountExecutive1 : accountExecutiveArrayList) {
                if (accountExecutive1.getExecutiveID().equals(executiveID))
                    result = true;
            }
            return result;
        }

        public void issue_All_Unit_Invoice(String issuerID, int amount, LocalDate dueDate, String paymentTypes, String description) throws IOException {
            ArrayList<Unit> unitArrayList = new Unit().getArrayList();
            ArrayList<Invoice> invoiceArrayList = new Invoice().getArrayList();
            String status = "unpaid";
            for (Unit unit : unitArrayList){
                Invoice invoice = new Invoice(null, issuerID, unit.getUnitID(), amount, dueDate, paymentTypes, description, status);
                invoice.setInvoiceID(invoice.get_Auto_InvoiceID());
                invoiceArrayList.add(invoice);
            }
            new Invoice().save_All_Invoice(invoiceArrayList);
        }

        public void issue_Unit_Invoice(Invoice invoice) throws IOException {
            ArrayList<Invoice> invoiceArrayList = invoice.getArrayList();
            invoiceArrayList.add(invoice);
            invoice.save_All_Invoice(invoiceArrayList);
        }

        public void issue_Receipt(String paymentID, String issuerID) throws IOException {
            Payment payment = new Payment();
            ArrayList<Payment> paymentArrayList = payment.getArrayList();
            for (Payment payment1 : paymentArrayList){
                if (payment1.getPaymentID().equals(paymentID) && check_Account_Executive_Availability(issuerID)){
                    payment1.setIssuerID(issuerID);
                    payment1.setIssuedDate(LocalDate.now());
                }
            }
            new Payment().save_All_Payment(paymentArrayList);
        }

        public void issue_Statement(Statement statement) throws IOException {
            ArrayList<Statement> statementArrayList = new Statement().getArrayList();
            statementArrayList.add(statement);
            new Statement().save_All_Statement(statementArrayList);
        }

        public ArrayList<Invoice> get_Unit_All_Invoice(String unitID) throws FileNotFoundException {
            ArrayList<Invoice> invoiceArrayList = new Invoice().getArrayList();
            for (Invoice invoice : invoiceArrayList){
                if (!(invoice.getUnitID().equals(unitID)))
                    invoiceArrayList.remove(invoice);
            }
            return invoiceArrayList;
        }

        public ArrayList<Invoice> get_Unit_All_Unpaid_Invoice(String unitID) throws FileNotFoundException {
            ArrayList<Invoice> invoiceArrayList = new Invoice().getArrayList();
            for (Invoice invoice : invoiceArrayList){
                if (!(invoice.getUnitID().equals(unitID)) && invoice.getStatus().equals("unpaid"))
                    invoiceArrayList.remove(invoice);
            }
            return invoiceArrayList;
        }

        public int get_Unit_Unpaid_Amount(String unitID) throws FileNotFoundException {
            int total = 0;
            ArrayList<Invoice> invoiceArrayList = new Invoice().getArrayList();
            for (Invoice invoice : invoiceArrayList){
                if (invoice.getUnitID().equals(unitID) && invoice.getStatus().equals("unpaid"))
                    total += invoice.getAmount();
            }
            return total;
        }
    }
}
