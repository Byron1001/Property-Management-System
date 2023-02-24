package Entity.Financial;

import Entity.Building_Manager.Building_Manager_Function;
import Entity.Resident.Resident;
import Entity.Unit;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Payment {
    private String paymentID;
    private String invoiceID;
    private String pay_Username;
    private String unitID;
    private int amount;
    private LocalDate payment_Date;
    private String paymentTypes;
    private String issuerID;
    private LocalDate issuedDate;
    private String description;
    private File payment_txt = new File("src/Database/Payment.txt");
    public Payment(){}

    public Payment(String paymentID, String invoiceID, String pay_Username, String unitID, int amount, LocalDate payment_Date, String paymentTypes, String issuerID, LocalDate issuedDate, String description) {
        this.paymentID = paymentID;
        this.invoiceID = invoiceID;
        this.pay_Username = pay_Username;
        this.unitID = unitID;
        this.amount = amount;
        this.payment_Date = payment_Date;
        this.paymentTypes = paymentTypes;
        this.issuerID = issuerID;
        this.issuedDate = issuedDate;
        this.description = description;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getPay_Username() {
        return pay_Username;
    }

    public void setPay_Username(String pay_Username) {
        this.pay_Username = pay_Username;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDate getPayment_Date(){return payment_Date;}

    public void setPayment_Date(LocalDate payment_Date){this.payment_Date = payment_Date;}

    public String getPaymentTypes() {
        return paymentTypes;
    }

    public void setPaymentTypes(String paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public String getIssuerID() {
        return issuerID;
    }

    public void setIssuerID(String issuerID) {
        this.issuerID = issuerID;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Payment> getArrayList() throws FileNotFoundException {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        Scanner scanner = new Scanner(payment_txt);
        scanner.nextLine();
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 10);
            Payment payment = new Payment(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), LocalDate.parse(data[5], formatter), data[6], data[7], LocalDate.parse(data[8], formatter), data[9]);
            paymentArrayList.add(payment);
        }
        scanner.close();
        return paymentArrayList;
    }

    public String[] getStringArray(Payment payment){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        String[] data = {payment.getPaymentID(), payment.getInvoiceID(), payment.getPay_Username(), payment.getUnitID(), Integer.valueOf(payment.getAmount()).toString(), payment.getPayment_Date().format(formatter), payment.getPaymentTypes(), payment.getIssuerID(), payment.getIssuedDate().format(formatter), payment.getDescription()};
        return data;
    }

    public String[] getStringArrayAddStatus(Payment payment){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        String status = "PENDING";
        if (!payment.getIssuerID().equals("")){
            status = "RECEIVED";
        }
        String[] data = {payment.getPaymentID(), payment.getInvoiceID(), payment.getPay_Username(), payment.getUnitID(), Integer.valueOf(payment.getAmount()).toString(), payment.getPayment_Date().format(formatter), payment.getPaymentTypes(), payment.getIssuerID(), payment.getIssuedDate().format(formatter), status};
        return data;
    }

    public String get_Auto_PaymentID() throws FileNotFoundException {
        FileReader fileReader = new FileReader(payment_txt);
        Scanner scanner = new Scanner(fileReader);
        scanner.nextLine();
        Integer num = 0;
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 9);
            String number = data[0].substring(3);
            num = Integer.parseInt(number);
            num += 1;
        }
        String str = "Pay" + num.toString();
        System.out.println(str);
        return str;
    }

    public String getDataString(Payment payment){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        String[] data = {payment.getPaymentID(), payment.getInvoiceID(), payment.getPay_Username(), payment.getUnitID(), Integer.valueOf(payment.getAmount()).toString(), payment.getPayment_Date().format(formatter), payment.getPaymentTypes(), payment.getIssuerID(), payment.getIssuedDate().format(formatter), payment.getDescription()};
        String dataLine = "";
        for (String dd : data){
            dataLine += dd + ":";
        }
        dataLine = dataLine.substring(0, dataLine.length() - 1);
        dataLine += '\n';
        return dataLine;
    }

    public void save_All_Payment(ArrayList<Payment> paymentArrayList) throws IOException {
        FileWriter fileWriter = new FileWriter(payment_txt, false);
        fileWriter.write("paymentID:invoiceID:pay Username:unitID:amount:payment date:payment Types:issuerID:issued date:description\n");
        for (Payment payment : paymentArrayList){
            fileWriter.write(payment.getDataString(payment));
        }
        fileWriter.close();
    }

    public void make_Payment(Invoice invoice, String pay_Username) throws IOException {
        ArrayList<Invoice> invoiceArrayList = invoice.getArrayList();
        ArrayList<Payment> paymentArrayList = new Payment().getArrayList();
        boolean check = invoice.check_Invoice_Availability(invoice.getInvoiceID());
        if (!check)
            return;
        for (Invoice invoice1 : invoiceArrayList){
            if (invoice1.getInvoiceID().equals(invoice.getInvoiceID()))
                invoice1.setStatus("paid");
        }
        invoice.save_All_Invoice(invoiceArrayList);

        Payment payment = new Payment(new Payment().get_Auto_PaymentID(), invoice.getInvoiceID(), pay_Username, invoice.getUnitID(), invoice.getAmount(), LocalDate.now(), invoice.getPaymentTypes(), null, null, invoice.getDescription());
        paymentArrayList.add(payment);
        save_All_Payment(paymentArrayList);
        add_to_Fund(invoice.getAmount());

        if (payment.getPaymentTypes().equals("Deposit")){
            Unit unit = new Unit().search_Unit_Information(invoice.getUnitID());
            Deposit deposit = new Deposit(unit.getResident_Username(), invoice.getAmount(), unit.getUnitID());
            deposit.make_Deposit(deposit);
        }
    }

    public static int get_Fund_Amount() throws FileNotFoundException {
        File fund_Amount_txt = new File("src/Database/Fund.txt");
        Scanner scanner = new Scanner(fund_Amount_txt);
        String number = scanner.nextLine().split(":", 2)[1];
        return Integer.parseInt(number);
    }

    private static void add_to_Fund(int new_Amount) throws IOException {
        int number = get_Fund_Amount();
        number += new_Amount;
        File fund_Amount_txt = new File("src/Database/Fund.txt");
        FileWriter fileWriter = new FileWriter(fund_Amount_txt, false);
        String line = "total fund:" + Integer.valueOf(number).toString();
        fileWriter.write(line);
        fileWriter.close();
    }

    public ArrayList<Payment> get_All_unapproved_Payment() throws FileNotFoundException {
        Payment payment = new Payment();
        ArrayList<Payment> paymentArrayList = payment.getArrayList();
        for (Payment payment1 : paymentArrayList)
        {
            if (!(payment1.getIssuerID().equals(null)))
                paymentArrayList.remove(payment1);
        }
        return paymentArrayList;
    }

    public ArrayList<Payment> get_All_Receipt(String unitID) throws FileNotFoundException {
        Payment payment = new Payment();
        ArrayList<Payment> paymentArrayList = payment.getArrayList();
        ArrayList<Payment> paymentArrayList1 = new ArrayList<>();
        for (Payment payment1 : paymentArrayList){
            if (payment1.getUnitID().equals(unitID))
                paymentArrayList1.add(payment1);
        }
        return paymentArrayList1;
    }

    public ArrayList<Payment> get_All_Receipt() throws FileNotFoundException {
        Payment payment = new Payment();
        ArrayList<Payment> paymentArrayList = payment.getArrayList();
        ArrayList<Payment> paymentReturn = new ArrayList<>();
        for (Payment payment1 : paymentArrayList){
            if (!payment1.getIssuerID().equals(""))
                paymentReturn.add(payment1);
        }
        return paymentReturn;
    }

    public static class Deposit{
        private String username;
        private int amount;
        private String unitID;
        private File deposit_txt = new File("src/Database/Deposit.txt");
        public Deposit(){}

        public Deposit(String username, int amount, String unitID) {
            this.username = username;
            this.amount = amount;
            this.unitID = unitID;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getUnitID() {
            return unitID;
        }

        public void setUnitID(String unitID) {
            this.unitID = unitID;
        }

        public ArrayList<Deposit> getArrayList() throws FileNotFoundException {
            ArrayList<Deposit> depositArrayList = new ArrayList<>();
            Scanner scanner = new Scanner(deposit_txt);
            scanner.nextLine();
            while (scanner.hasNextLine()){
                String[] data = scanner.nextLine().split(":", 3);
                Deposit deposit = new Deposit(data[0], Integer.parseInt(data[1]), data[2]);
                depositArrayList.add(deposit);
            }
            scanner.close();
            return depositArrayList;
        }

        public String getDataString(Deposit deposit){
            String[] data = {deposit.getUsername(), Integer.valueOf(deposit.getAmount()).toString(), deposit.getUnitID()};
            String dataLine = "";
            for (String dd : data){
                dataLine += dd + ":";
            }
            dataLine = dataLine.substring(0, dataLine.length() - 1);
            dataLine += '\n';
            return dataLine;
        }

        public void save_All_Deposit(ArrayList<Deposit> depositArrayList) throws IOException {
            FileWriter fileWriter = new FileWriter(deposit_txt, false);
            fileWriter.write("Username:Amount:UnitID\n");
            for (Deposit deposit : depositArrayList){
                fileWriter.write(deposit.getDataString(deposit));
            }
            fileWriter.close();
        }

        public void make_Deposit(Deposit deposit) throws IOException {
            ArrayList<Deposit> depositArrayList = deposit.getArrayList();
            boolean check = false;
            for (Deposit deposit1 : depositArrayList){
                if (deposit1.getUnitID().equals(deposit.getUnitID())){
                    deposit1.setAmount(deposit1.getAmount() + deposit.getAmount());
                    check = true;
                }
            }
            if (!check)
                depositArrayList.add(deposit);
            deposit.save_All_Deposit(depositArrayList);
        }

        public Deposit check_Deposit(String unitID) throws FileNotFoundException {
            Deposit deposit = new Deposit();
            deposit.setUnitID("0");
            ArrayList<Deposit> depositArrayList = deposit.getArrayList();
            for (Deposit deposit1 : depositArrayList){
                if (deposit1.getUnitID().equals(unitID))
                    return deposit;
            }
            return deposit;
        }
    }

    public static class Expenses{
        private String expensesID;
        private String operationID;
        private String building_ManagerID;
        private int amount;
        private LocalDate payment_Date;
        private String paymentTypes;
        private String description;
        private File expenses_txt = new File("src/Database/Expenses.txt");

        public Expenses(){}

        public Expenses(String expensesID, String operationID, String building_ManagerID, int amount, LocalDate payment_Date, String paymentTypes, String description) {
            this.expensesID = expensesID;
            this.operationID = operationID;
            this.building_ManagerID = building_ManagerID;
            this.amount = amount;
            this.payment_Date = payment_Date;
            this.paymentTypes = paymentTypes;
            this.description = description;
        }

        public String getExpensesID() {
            return expensesID;
        }

        public void setExpensesID(String expensesID) {
            this.expensesID = expensesID;
        }

        public String getOperationID() {
            return operationID;
        }

        public void setOperationID(String operationID) {
            this.operationID = operationID;
        }

        public String getBuilding_ManagerID() {
            return building_ManagerID;
        }

        public void setBuilding_ManagerID(String building_ManagerID) {
            this.building_ManagerID = building_ManagerID;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public LocalDate getPayment_Date() {
            return payment_Date;
        }

        public void setPayment_Date(LocalDate payment_Date) {
            this.payment_Date = payment_Date;
        }

        public String getPaymentTypes() {
            return paymentTypes;
        }

        public void setPaymentTypes(String paymentTypes) {
            this.paymentTypes = paymentTypes;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public ArrayList<Expenses> getArrayList() throws FileNotFoundException {
            ArrayList<Expenses> expensesArrayList= new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            FileReader reader = new FileReader(expenses_txt);
            Scanner scanner = new Scanner(reader);
            scanner.nextLine();
            while (scanner.hasNextLine()){
                String[] data = scanner.nextLine().split(":", 7);
                expensesArrayList.add(new Expenses(data[0], data[1], data[2], Integer.parseInt(data[3]), LocalDate.parse(data[4], formatter), data[5], data[6]));
            }
            return expensesArrayList;
        }
        public void save_All_Expenses(ArrayList<Expenses> expensesArrayList) throws IOException {
            FileWriter fileWriter = new FileWriter(expenses_txt, false);
            fileWriter.write("expenses ID:operation ID:building manager ID:amount:payment date:payment types:description\n");
            for (Expenses expenses : expensesArrayList){
                String dataLine = getDataString(expenses);
                fileWriter.write(dataLine);
            }
            fileWriter.close();
        }

        public String getDataString(Expenses expenses){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            String[] data = {expenses.getExpensesID(), expenses.getOperationID(), expenses.getBuilding_ManagerID(), Integer.valueOf(expenses.getAmount()).toString(), expenses.getPayment_Date().format(formatter), expenses.getPaymentTypes(), expenses.getDescription()};
            String dataLine = "";
            for (String dd : data){
                dataLine += dd + ":";
            }
            dataLine = dataLine.substring(0, dataLine.length() - 1);
            dataLine += '\n';
            return dataLine;
        }

        public String get_Auto_ExpensesID() throws FileNotFoundException {
            FileReader fileReader = new FileReader(expenses_txt);
            Scanner scanner = new Scanner(fileReader);
            scanner.nextLine();
            Integer num = 0;
            while (scanner.hasNextLine()){
                String[] data = scanner.nextLine().split(":", 6);
                String number = data[0].substring(3);
                num = Integer.parseInt(number);
                num += 1;
            }
            String str = "Exp" + num.toString();
            System.out.println(str);
            return str;
        }

        public static void add_Expenses(Expenses expenses) throws IOException {
            ArrayList<Expenses> expensesArrayList = expenses.getArrayList();
            expensesArrayList.add(expenses);
            expenses.save_All_Expenses(expensesArrayList);
            add_to_Fund(expenses.getAmount() * -1);
            Building_Manager_Function.Building_Manager.Operation operation = new Building_Manager_Function.Building_Manager.Operation();
            operation.delete_Operation(expenses.getOperationID());
        }

        public Expenses get_Expenses_Info(String expensesID) throws FileNotFoundException {
            Expenses expenses = new Expenses();
            expenses.setExpensesID("0");
            ArrayList<Expenses> expensesArrayList = getArrayList();
            for (Expenses expenses1 : expensesArrayList){
                if (expenses1.getExpensesID().equals(expensesID))
                    expenses = expenses1;
            }
            return expenses;
        }

    }
}
