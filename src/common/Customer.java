package common;

import service.*;
import java.util.Date;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Customer extends User implements Serializable {
    private List<Account> accounts;
    public Customer() {
        accounts = new ArrayList<Account>();
    }
    //    public static void common.Customer(){loan = new ArrayList<common.LoanACC>();};
    public Customer(String name, String customerId, List<Account> accounts, List<LoanACC> loan, List<SavingACC> saving) {
        super(name, customerId);
        accounts = new ArrayList<Account>();
        loan = new ArrayList<LoanACC>();
        saving = new ArrayList<SavingACC>();
    }
    public List<Account> getAccounts() {
        return accounts;
    }
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    public boolean isPremium() {
        boolean isP = false;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).isPremium() == true) {
                isP = true;
                break;
            }
        }
        return isP;
    }
    public void addAccount(Account newAccount) {
//        for(int i=0;i<accounts.size()-1;i++) {
//            if(newAccount.getAccountNumber().equalsIgnoreCase(accounts.get(i).getAccountNumber())) {
//                System.out.println("common.Account Number Is Duplicate!! ");
//            }
//            else
//                accounts.add(newAccount);
//        }
        this.accounts.add(newAccount);
    }
    //    format số
    public String getBalance() {
        double total = 0;
        String _total;
        for (int i = 0; i < accounts.size(); i++) {
            total += accounts.get(i).getBalance();
        }
        DecimalFormat df = new DecimalFormat("#,###,###");
        _total = df.format(total);
        return _total;
    }
    // hien thog tin khach hang

    public void displayInformation() {
        String isPre;
        getListAcc();
        if (isPremium() == true) {
            isPre = "Premium";
        } else {
            isPre = "Nomal";
        }
        System.out.println(super.getCustomerId() + "|\t\t" + super.getName() + "|\t" + isPre + "|\t" + this.getBalance());
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + "|\t\t" +
                    this.getAccounts().get(i).getAccountNumber() +
                    "|\t\t\t\t\t\t\t" + accounts.get(i)._getBalance());
        }
    }
    //  tra ve list acc cua customer
    public List<Account> getListAcc() {
        List<Account> acc;
        acc = dao.AccountsDao.accountsListOfFileDat();
        List<Account> _acc = acc.stream().filter(__acc -> __acc.getCustomerId().equals(this.getCustomerId())).collect(Collectors.toList());
        this.setAccounts(_acc);
        return _acc;
    }
    // tra ve tai khoan ma nguoi dung nhap
    public Account getAccountByAccountNumber(List<Account> accounts, String accountNumber) {
        Account account = new Account();
        for (Account _account : accounts) {
            if (_account.getAccountNumber().equalsIgnoreCase(accountNumber)) {
                account = _account;
                break;
            }
        }
        return account;
    }
    // rut tien tu ATM
    public void withdraw(List<Account> accounts) {
        Date time = new Date();
        String _time = time.toString();
        Transaction trans = new Transaction();

        Scanner scanner = new Scanner(System.in);
        if (!accounts.isEmpty()) {
            Account account;
            Double amount;
            do {
                System.out.println("nhap so tai khoan");
                account = getAccountByAccountNumber(accounts, scanner.nextLine());
            } while (account.getAccountNumber() == null);
            do {
                System.out.println("nhap so tien rut");
                amount = Double.parseDouble(scanner.nextLine());
            } while (amount <= 0);
            if (account instanceof SavingACC) {
                boolean check;
                check= ((SavingACC) account).withdraw(amount);
                if(check){trans._log( account.getAccountNumber(), _time, amount,"WITHDRAW","success");}
                else {trans._log( account.getAccountNumber(), _time, amount,"WITHDRAW","fail");}

                dao.AccountsDao.updateAccount(account);
                dao.TransactionsDao.updateTransaction(trans);
            }
        } else {
            System.out.println("khach hang k ton tai hoac khong co tai khoan nao, thao tac k thanh cong");
        }
    }
    //    chuyen tien
    public void transfers(List<Account> accounts, List<Account> _accounts) {
        Date time = new Date();
        String _time = time.toString();
        Transaction trans = new Transaction();

//        accounts :list tai khoan cua 1 khach hang, _accounts: toan bo tai khoan
        Scanner scanner = new Scanner(System.in);
        if (!accounts.isEmpty()) {
            Account account;
            Account _account;
            Double amount;
//
            do {
                System.out.println("nhap so tai khoan cua ban");
                account = getAccountByAccountNumber(accounts, scanner.nextLine());
                if(account.getAccountNumber()==null){
                    System.out.println("Nhap sai STK");
                }
            } while (account.getAccountNumber() == null);

            do {
                Scanner _scanner = new Scanner(System.in);
                String acc ;
                System.out.println("nhap so tai khoan nhan tien");
                acc = _scanner.nextLine();
                _account = getAccountByAccountNumber(_accounts, acc);
            } while (_account.getAccountNumber() == null);


            do {
                System.out.println("nhap so tien chuyen");
                amount = Double.parseDouble(scanner.nextLine());
            } while (amount <= 0);

            if (account instanceof SavingACC) {
                boolean check;
                check= ((SavingACC) account).transfers(_account, amount);
                if(check){trans._log( account.getAccountNumber(), _time, amount,"TRANSFERS","success");}
                else {trans._log( account.getAccountNumber(), _time, amount,"TRANSFERS","fail");}
                dao.TransactionsDao.updateTransaction(trans);
                dao.AccountsDao.updateAccount(account);
            }
        } else {
            System.out.println("khach hang k ton tai hoac khong co tai khoan nao, thao tac k thanh cong");
        }
    }

}
