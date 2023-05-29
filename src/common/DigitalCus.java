package common;

import java.util.Date;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;
import java.io.*;

public class DigitalCus extends Customer implements Serializable  {
    private static final long serialVersionUID = -266706354210367639L;
    private String name=super.getName();

    public String get_name() {
        return super.getName();
    }
    public void set_name(String _name) {
        this.name = _name;
    }

    @Override
    public String toString() {
        return "common.DigitalCus{" +
                "_name='" +super.getName() + '\'' +super.getCustomerId()+
                '}';
    }

    public  DigitalCus(String id, String name){
        super.setName(name);
        super.setCustomerId(id);
        super.getAccounts();
    }
    public  DigitalCus(){
    }
    public void setName(String name) {
        super.setName(name);
    }
    public String getName() {
        return super.getName();
    }

    //    //    set+get: customerId
//    public String getCustomerId() {
//        return this.customerId;
//    }
//    public  void setCustomerId(String customerId) {
//        this.customerId=customerId;
//    }
//    public void displayName(){
//        System.out.println(this.name);
//    }

    public void withdraw(String number, Double amount) {
        String code = String.valueOf(UUID.randomUUID());
        Date time = new Date();
        String _time = time.toString();
        Transaction trans = new Transaction();
        for (int i = 0; i < getAccounts().size(); i++) {
            if (this.getAccounts().get(i).getAccountNumber().equals(number)) {
                if (getAccounts().get(i).getType().equals("SAVING")) {
                    trans._log(code, number, _time, amount);
                    super.getAccounts().get(i).getReports().add(trans);
                    ((SavingACC) this.getAccounts().get(i)).withdraw(amount);
                    break;
                }
                if (this.getAccounts().get(i).getType().equals("LOAN")) {
                    trans._log(code, number, _time, amount);
                    super.getAccounts().get(i).getReports().add(trans);
                    ((LoanACC) this.getAccounts().get(i)).withdraw(amount);
                    break;
                }
            }
        }
    }
    public void displayInformation() {
        String isPre;
        String balance = this.getBalance();
        if (isPremium() == true) {
            isPre = "Premium";
        } else {
            isPre = "Nomal";
        }
        System.out.println(this.getCustomerId() + "|\t\t" + this.getName() + "|\t" + isPre + "|\t" + balance);
        for (int i = 0; i < this.getAccounts().size(); i++) {
            System.out.println((i + 1) + "|\t\t" +
                    this.getAccounts().get(i).getAccountNumber() + "\t\t|" +
                    this.getAccounts().get(i).getType() + "|\t\t" +
                    this.getAccounts().get(i)._getBalance());
        }
    }
    public void displayInformation_() {
        String isPre;
        String balance = this.getBalance();
        if (isPremium() == true) {
            isPre = "Premium";
        } else {
            isPre = "Nomal";
        }
        System.out.println(this.getCustomerId() + "|\t\t" + this.getName() + "|\t" + isPre + "|\t" + balance);
        for (int i = 0; i < this.getAccounts().size(); i++) {
            System.out.println((i + 1) + "|\t\t" +
                    this.getAccounts().get(i).getAccountNumber() + "\t\t|" +
                    this.getAccounts().get(i).getType() + "|\t\t" +
                    this.getAccounts().get(i)._getBalance());
        }
    }

    // rut tien
    public void draw() {
        Scanner draw = new Scanner(System.in);
        String accNum;

        while (true) {
            System.out.println("nhap STK");
            accNum = draw.nextLine();
            boolean _check = false;
            Pattern check = Pattern.compile("^[0-9]{6}+$");
            if (!check.matcher(accNum).find()) {
                System.out.println("Nhap sai STK");
                continue;
            }
            for (int i = 0; i < this.getAccounts().size(); i++) {

                if (accNum.equals(this.getAccounts().get(i).getAccountNumber())) {
                    _check = true;
                    break;
                }
            }
            if (_check == false) {
                System.out.println("STK khong ton tai");
                continue;
            }
            break;
        }
        System.out.println("nhap so tien can rut");
        Double _draw = draw.nextDouble();
        this.withdraw(accNum, _draw);
    }
    //        them tai khoan loan
    public void addLOAN() {
        String _account;
        Account acc = new LoanACC();
        LoanACC _acc = (LoanACC) acc;
        Scanner account = new Scanner(System.in);
        do {
            System.out.println("Nhap STK:");
            _account = account.nextLine();
            check(_account);
            if (!check(_account)) {
                System.out.println("STK sai hoac da ton tai:");
            } else {
                break;
            }
        }
        while (true);
        _acc.setAccountNumber(_account);
        _acc.setBalance(100000000);
        this.getAccounts().add(_acc);
    }
    //        them tai khoan saving
    public void addATM() {
        Double _balance;
        String _account;
        Account acc = new SavingACC();
        SavingACC _acc = (SavingACC) acc;
        Scanner account = new Scanner(System.in);
        Scanner balance = new Scanner(System.in);
        do {
            System.out.println("Nhap STK:");
            _account = account.nextLine();
            check(_account);
            if (!check(_account)) {
                System.out.println("STK sai hoac da ton tai:");
            } else {
                break;
            }
        }
        while (true);
        System.out.println("Nhap so du: ");
        _balance = balance.nextDouble();
        while (_balance < 50000) {
            System.out.println("So du phai lon hon 50.000 VND");
            System.out.println("Nhap so du: ");
            _balance = balance.nextDouble();
        }
        _acc.setBalance(_balance);
        _acc.setAccountNumber(_account);
        this.getAccounts().add(_acc);
    }
    public boolean check(String _accNum) {
        Pattern check = Pattern.compile("^[0-9]{6}+$");
        if (!check.matcher(_accNum).find()) {
            return false;
        }
        for (int i = 0; i < this.getAccounts().size(); i++) {
            if (_accNum.equals(this.getAccounts().get(i).getAccountNumber())) {
                return false;
            }
        }
        return true;
    }
    public void trans() {
        this.displayInformation();
        for (Account i : this.getAccounts()) {
            for (Transaction j : i.getReports()) {
                j.log();
            }
        }
    }
}
