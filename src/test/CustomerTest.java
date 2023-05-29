package test;

import common.Account;
import common.SavingACC;
import dao.CustomersDao;
import common.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void withdraw() {
        Customer customer = new Customer();
        Account _acc = new SavingACC();
        _acc.setBalance(5000000.0);
        customer.addAccount(_acc);
        ((SavingACC)customer.getAccounts().get(0)).withdraw(3000000.0);
        assertEquals((customer.getAccounts().get(0)).getBalance(), 2000000.0);

    }

}