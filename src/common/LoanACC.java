package common;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class LoanACC extends Account implements Withdraw, ReportService {

    private final double LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE = 0.01;
    private final double LOAN_ACCOUNT_MAX_BALANCE = 100000000;
    private final double LOAN_ACCOUNT_WITHDRAW_FEE = 0.05;
    private final String type = "LOAN";


    public String getType() {
        return type;
    }
    public String _getAmount(double amount) {
        DecimalFormat df = new DecimalFormat("#,###,###");
        String _amount = df.format(amount);
        return _amount;
    }

    public boolean isAccepted(double amount) {
        return amount < LOAN_ACCOUNT_MAX_BALANCE && (getBalance() - amount >= 50000);
    }

    public boolean withdraw(double amount) {
        double newBalance = 0.0;
        if (isAccepted(amount)) {
            if (isPremium()) {
                newBalance = getBalance() - amount - (amount * LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE);
                setBalance(newBalance);
                log(amount, (amount * LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE));
                System.out.println(" Giao dich  thanh cong");
                return true;
            }
            if (!isPremium()) {
                newBalance = getBalance() - amount - (amount * LOAN_ACCOUNT_WITHDRAW_FEE);
                setBalance(newBalance);
                log(amount, (amount * LOAN_ACCOUNT_WITHDRAW_FEE));
                System.out.println(" Giao dich  thanh cong");

                return true;
            }
        }
        System.out.println("So tien rut khong kha dung");
        return false;
    }

    public void log(double amount, double fee) {
        LocalDate time = LocalDate.now();
        System.out.println("+---------------------------+\n" +
                " BIEN LAI GIAO DICH LOAN " +
                "\n NGAY G/D:\t\t\t" + time +
                "\n ATM ID:\t\t\tDIGITAL-BANK-ATM" + time.getYear() +
                "\n SO TK: \t\t\t" + this.getAccNum() +
                "\n SO TIEN GIAO DICH:\t" + _getAmount(amount) +
                "\n SO DU: \t\t\t" + super._getBalance() +
                "\n PHI + VAT:\t\t\t" + fee + "d"
        );
    }

    public String getAccNum() {
        return super.getAccountNumber();
    }
    //
    public double getBalance() {
        return super.getBalance();
    }

    public void setBala(double bala) {
        super.setBalance(bala);
    }

}
