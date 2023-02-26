package Entity.Financial;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Invoice {
    private String invoiceID;
    private String issuerID;
    private String unitID;
    private int amount;
    private LocalDate dueDate;
    private String paymentTypes;
    private String description;
    private String status;
    private File invoice_txt = new File("src/Database/Invoice.txt");
    public Invoice(){}

    public Invoice(String invoiceID, String issuerID, String unitID, int amount, LocalDate dueDate, String paymentTypes, String description, String status) {
        this.invoiceID = invoiceID;
        this.issuerID = issuerID;
        this.unitID = unitID;
        this.amount = amount;
        this.dueDate = dueDate;
        this.paymentTypes = paymentTypes;
        this.description = description;
        this.status = status;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getIssuerID() {
        return issuerID;
    }

    public void setIssuerID(String issuerID) {
        this.issuerID = issuerID;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Invoice> getArrayList() throws FileNotFoundException {
        ArrayList<Invoice> invoiceArrayList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        Scanner scanner = new Scanner(invoice_txt);
        scanner.nextLine();
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 8);
            Invoice invoice = new Invoice(data[0], data[1], data[2], Integer.parseInt(data[3]), LocalDate.parse(data[4], formatter), data[5], data[6], data[7]);
            invoiceArrayList.add(invoice);
        }
        scanner.close();
        return invoiceArrayList;
    }

    public String get_Auto_InvoiceID() throws FileNotFoundException {
        FileReader fileReader = new FileReader(invoice_txt);
        Scanner scanner = new Scanner(fileReader);
        scanner.nextLine();
        Integer num = 0;
        while (scanner.hasNextLine()){
            String[] data = scanner.nextLine().split(":", 8);
            String number = data[0].substring(3);
            num = Integer.parseInt(number);
            num += 1;
        }
        String str = "INV" + num.toString();
        return str;
    }

    public boolean check_Invoice_Availability(String invoiceID) throws FileNotFoundException {
        boolean result = false;
        ArrayList<Invoice> invoiceArrayList = this.getArrayList();
        for (Invoice invoice : invoiceArrayList){
            if (invoice.getInvoiceID().equals(invoiceID)){
                result = true;
            }
        }
        return result;
    }

    public String[] getStringArray(Invoice invoice){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        String[] data = {invoice.getInvoiceID(), invoice.getIssuerID(), invoice.getUnitID(), Integer.valueOf(invoice.getAmount()).toString(), invoice.getDueDate().format(formatter), invoice.getPaymentTypes(), invoice.getDescription(), invoice.getStatus()};
        return data;
    }

    public String getDataString(Invoice invoice){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        String[] data = {invoice.getInvoiceID(), invoice.getIssuerID(), invoice.getUnitID(), Integer.valueOf(invoice.getAmount()).toString(), invoice.getDueDate().format(formatter), invoice.getPaymentTypes(), invoice.getDescription(), invoice.getStatus()};
        String dataLine = "";
        for (String dd : data){
            dataLine += dd + ":";
        }
        dataLine = dataLine.substring(0, dataLine.length() - 1);
        dataLine += '\n';
        return dataLine;
    }

    public void save_All_Invoice(ArrayList<Invoice> invoiceArrayList) throws IOException {
        FileWriter fileWriter = new FileWriter(invoice_txt, false);
        fileWriter.write("invoiceID:issuerID:unitID:amount:dueDate:paymentTypes:description:status\n");
        for (Invoice invoice : invoiceArrayList){
            fileWriter.write(invoice.getDataString(invoice));
        }
        fileWriter.close();
    }

}
