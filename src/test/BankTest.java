package test;
import common.Account;
import common.Customer;
import common.SavingACC;
import org.junit.jupiter.api.Test;
import service.BinaryFileServices;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BankTest {




    @Test
    void showCustomers() {
        List<Customer> customers = dao.CustomersDao.customerListOfFileDat();
        List<Account> accounts = dao.AccountsDao.accountsListOfFileDat();
        assertNotNull(accounts);
        assertNotNull(customers);
    }

    @Test
    void addCustomers() {
        String link ="store\\customers.txt";
        List<Customer> newCustomers = dao.CustomersDao.readtxt(link);
        assertNotNull(newCustomers);
        dao.CustomersDao.updateCustomer(newCustomers);
        List<Customer> dataCustomers = dao.CustomersDao.customerListOfFileDat();
        assertNotNull(dataCustomers);
    }

    @Test
    void addSavingAccount() {
        Double _balance= 500000.0;
        String _accountNum="123456";
        String _custormerId="123456789123";
        Account acc = new SavingACC();
        SavingACC _acc = (SavingACC)acc;
        _acc.setBalance(_balance);
        _acc.setAccountNumber(_accountNum);
        _acc.setCustomerId(_custormerId);
        dao.AccountsDao.updateAccount(_acc);
        List<Account>accountList = dao.AccountsDao.accountsListOfFileDat();
        boolean check =
        accountList.stream().anyMatch(account -> account.getCustomerId().equalsIgnoreCase("123456789123"));
        assertTrue(check);
    }

}