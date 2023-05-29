package common;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {
    private String accountNumber;
    private double balance;
    private String type;
    private List<Transaction> Transactions;
    private  String customerId;

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public Account(String accountNumber, double balance, String type, String customerId) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.type = type;
        this.customerId = customerId;
    }

    public String getType() {
        return this.type;
    }

    public Account(String accountNumber, Double balance, String type) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.type=type;
    }
    public Account() {
        Transactions = new ArrayList<Transaction>();
    }
    public List<Transaction> getReports() {
        return Transactions;
    }
    //get+set: stk
    public String getAccountNumber() {
        return this.accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    //    get+set: Balance
    public double getBalance() {
        return this.balance;
    }

    public String _getBalance() {
        String _balance = String.valueOf(this.balance);
        DecimalFormat df = new DecimalFormat();
        _balance = df.format(this.balance);
        return _balance;
    }
    ;
    public void setBalance(double balance) {
        this.balance = balance;
    }
    // isPre???
    public boolean isPremium() {
        if (getBalance() >= 10000000) {
            return true;
        } else
            return false;
    }
    // xuat thong tin tk
    public String toString() {
        return getAccountNumber() + "|\t\t\t" + getBalance();
    }

}