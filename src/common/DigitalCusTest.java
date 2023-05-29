package common;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DigitalCusTest {

    @org.junit.jupiter.api.Test
    void withdraw() {
        DigitalCus cus = new DigitalCus();

        Account d = new SavingACC();
        SavingACC f = (SavingACC)d;
        f.setAccountNumber("456789");
        f.setBalance(50000000);
        f.getType();
        cus.addAccount(f);
        cus.withdraw("456789",10000000.0);
        assertEquals(cus.getAccounts().get(0).getBalance(),40000000.0);

    }



    @org.junit.jupiter.api.Test
    void addLOAN() {
        DigitalCus cus = new DigitalCus();
        Account acc = new LoanACC();
        LoanACC _acc = (LoanACC) acc;
        cus.getAccounts().add(_acc);
        assertNotNull(cus.getAccounts());
    }

    @org.junit.jupiter.api.Test
    void addATM() {
        DigitalCus cus = new DigitalCus();
        Account acc = new SavingACC();
        SavingACC _acc = (SavingACC) acc;
        cus.getAccounts().add(_acc);
        assertNotNull(cus.getAccounts());
    }

    @org.junit.jupiter.api.Test
    void check() {
        DigitalCus cus = new DigitalCus();
        assertFalse(cus.check("1234567"));
    }

}