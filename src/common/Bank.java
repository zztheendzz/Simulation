package common;
import java.util.*;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.stream.Collectors;

import service.*;


public class Bank {


    private final String id;
    private List<Customer> customers;
    private List<DigitalCus> digitalCusList;

    public Bank() {
        customers = new ArrayList<Customer>();
        digitalCusList = new ArrayList<DigitalCus>();
        this.id = String.valueOf(UUID.randomUUID());
    }

    public String getId() {
        return id;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<DigitalCus> getDigitalCusList() {
        return digitalCusList;
    }

    //    ass4
// chức năng 1: xem danh sách khách hàng từ file dat:
    public void showCustomers() {
        List<Customer> customers = dao.CustomersDao.customerListOfFileDat();
        List<Account> accounts = dao.AccountsDao.accountsListOfFileDat();
        if(customers.size()==0){
            System.out.println("Danh sach khach hang trong");
        }
        for (Customer _customer :customers) {
            _customer.setAccounts(getListAccountOfCus(accounts, _customer));
            (_customer).displayInformation();
        }
    }
    //  chức năng 2: thêm danh sách khách hàng từ file text sàng file dat
    public void addCustomers() {
        Scanner scanner = new Scanner(System.in);
        String link ;
        System.out.println("Nhap duong dan file danh sach khach hang: ");
        link = scanner.nextLine();
        List<Customer> newCustomers = dao.CustomersDao.readtxt(link);
        List<Customer> dataCustomers = dao.CustomersDao.customerListOfFileDat();
        List<Customer> _newDataCustomers = new ArrayList<>();

        for (Customer newCustomer : newCustomers) {
            if (isCustomerExisted(dataCustomers, newCustomer)) {
                System.out.println("Khach hang " + newCustomer.getCustomerId()+" da ton tai, them khach hang khong thanh cong");
            } else {
                _newDataCustomers.add(newCustomer);
                System.out.println("Da them khach hang "+newCustomer.getCustomerId()+" vao danh sach khach hang");
            }
        }
        dataCustomers.addAll(_newDataCustomers);
//        lưu vào file dat
        dao.CustomersDao.updateCustomer(dataCustomers);
    }
    // chức năng 3: thêm tài khoản atm
    public void addSavingAccount() {
        Double _balance;
        String _accountNum;
        String _custormerId;
        Scanner accountNum = new Scanner(System.in);
        Scanner balance = new Scanner(System.in);
        Scanner custormerId = new Scanner(System.in);
        List<Account> accounts = new ArrayList<>();
//        nhap id: cccd
        while (true) {
            System.out.println("Nhap cccd:");
            _custormerId = custormerId.nextLine();
            checkIdLisCus(_custormerId );
            if (!checkIdLisCus(_custormerId)) {
                System.out.println("cccd nhap sai hoac k ton tai");
            } else {
                break;
            }
        }
// nhap moi stk
        while (true) {
            System.out.println("Nhap STK:");
            _accountNum = accountNum.nextLine();
            _checkAccNum(_accountNum);
            if (!_checkAccNum(_accountNum) || isAccountExisted(accounts, _accountNum)) {
                System.out.println("STK sai hoac bi trung ");
                continue;
            }
            break;
        }
//        nhap so du
        System.out.println("Nhap so du: ");
        _balance = balance.nextDouble();
        while (_balance < 50000) {
            System.out.println("So du phai lon hon 50.000 VND");
            System.out.println("Nhap so du: ");
            _balance = balance.nextDouble();
        }
        Account acc = new SavingACC();
        SavingACC _acc = (SavingACC)acc;
        _acc.setBalance(_balance);
        _acc.setAccountNumber(_accountNum);
        _acc.setCustomerId(_custormerId);
        dao.AccountsDao.updateAccount(_acc);
    }
    //    chuc nang 5: rut tien
    public void withdraw() {

        List<Account> accounts = dao.AccountsDao.accountsListOfFileDat();
        List<Account> _accounts = new ArrayList<>();
        Customer customer = new Customer();
        while (true) {
            boolean check = false;
            System.out.println("nhap cccd");
            String _cccd;
            Scanner cccd = new Scanner(System.in);
            _cccd = cccd.nextLine();
            for (Account account : accounts) {
                if (_cccd.equals(account.getCustomerId())) {
                    _accounts.add(account);
                }
            }
            if (_accounts != null) {
                customer.setCustomerId(_cccd);
                customer.withdraw(_accounts);
                check = true;
            }else {continue;}
            if (check) {
                break;
            }
        }
    }

    // chuc nang 4: chuyen tien
    public void tranfers() {
        List<Account> accounts =dao.AccountsDao.accountsListOfFileDat();
        List<Account> _accounts = new ArrayList<>();
        Customer customer = new Customer();
        while (true) {
            boolean check = false;
            System.out.println("nhap cccd");
            String _cccd;
            Scanner cccd = new Scanner(System.in);
            _cccd = cccd.nextLine();

//         lấy ra danh sách tài khoản của khách hàng
            for (Account account : accounts) {
                if (_cccd.equals(account.getCustomerId())) {
                    _accounts.add(account);
                }
            }

            if (_accounts != null) {
                customer.setCustomerId(_cccd);
                customer.transfers(_accounts, accounts);
                check = true;
            }
            if (check) {
                break;
            }
        }
    }

//    chuc nang 6: tra cuu lich su gia dich
    public void transaction(){
        Scanner scanner = new Scanner(System.in);
        String cccd;
        List<Customer> customers = dao.CustomersDao.customerListOfFileDat();
        List<Transaction>transactions = dao.TransactionsDao.transactionListOfFileDat();
        System.out.println("Nhap cccd");
        cccd = scanner.nextLine();
        Customer customer = getCustomerById(customers,cccd);
        List<Account> accounts = dao.AccountsDao.accountsListOfFileDat();
        customer.setAccounts(getListAccountOfCus(accounts, customer));
        customer.displayInformation();
        getListTransOfCus(transactions, customer);
    }

// lấy ra lich sủ giao dịch 1 khách hàng
public static List<Transaction> getListTransOfCus(List<Transaction> TransList, Customer customer) {
    List<Account>accountListOfCus =  getListAccountOfCus(dao.AccountsDao.accountsListOfFileDat(),customer);
//        lấy ra ds lịch sử giao dịch của acc trong accountListOfCus
    List<Transaction> transactions = new ArrayList<>();
    for (int i = 0; i < TransList.size(); i++) {
        for(int j =0;j<accountListOfCus.size();j++){
            if(TransList.get(i).getAccNum().equalsIgnoreCase(accountListOfCus.get(j).getAccountNumber())){
                TransList.get(i).__log();
            }
        }
    }
    return transactions;
}
    //kiểm tra một account đã tồn tại trong mảng không
    public boolean isAccountExisted(List<Account> accountsList, String newAccountNumber) {
        boolean check = accountsList.stream().anyMatch(account -> account.getCustomerId().equalsIgnoreCase(newAccountNumber));
        return check;
    }

    //    kiểm tra một customer có tồn tại trong mảng hay không
    public boolean isCustomerExisted(List<Customer> customerList, Customer newCustomer) {
        boolean check =
        customerList.stream().anyMatch(customer -> customer.getCustomerId().equalsIgnoreCase(newCustomer.getCustomerId()));
        return check;
    }

    //lấy ra một customer có id bằng id cho trước
    public Customer getCustomerById(List<Customer> customerList, String customerId) {
        Customer _customer = new Customer();
        for (Customer customer : customerList) {
            if (customerId.equals(customer.getCustomerId())) {
                _customer = customer;
            }
        }
        return _customer;
    }

    //    lấy ra ds acc của 1 khach hang
    public static List<Account> getListAccountOfCus(List<Account> accountList, Customer customer) {
        List<Account> accounts = accountList.stream().filter(account ->account.getCustomerId().equalsIgnoreCase(customer.getCustomerId())).collect(Collectors.toList());
        return accounts;
    }
    public boolean checkIdLisCus(String id) {
        boolean check = false;
        List<Customer> customers = dao.CustomersDao.customerListOfFileDat();
        for (Customer customer : customers) {
            if (id.equals(customer.getCustomerId())) {
                check = true;
            }
        }
        return check;
    }
    public boolean checkAccNum(String accNum) {
        boolean check = true;
        List<Account> accounts = BinaryFileServices.readFile("store\\accounts.dat");
        for (Account account : accounts) {
            if (accNum.equals(account.getAccountNumber())) {
                check = false;
            }
        }
        return check;
    }
    public boolean check(String _accNum) {
        Pattern check = Pattern.compile("^[0-9]{12}+$");
        if (!check.matcher(_accNum).find()) {
            return false;
        }
        return true;
    }
    public boolean _checkAccNum(String checkAccNum) {
        Pattern check = Pattern.compile("^[0-9]{6}+$");
        if (!check.matcher(checkAccNum).find()) {
            return false;
        }
        return true;
    }
}
