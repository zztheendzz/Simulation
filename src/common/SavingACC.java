package common;
import java.io.Serializable;
import java.time.LocalDate;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import service.*;

public class SavingACC extends Account implements Withdraw,ReportService , Serializable {
    private double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5000000;
    private String type="SAVING";

    public String getType(){return type;}
    public  String _getAmount(double amount){
        DecimalFormat df=new DecimalFormat("#,###,###");
        String _amount = df.format(amount);
        return _amount;
    }
    public void log(double amount, double fee){
        LocalDate time = LocalDate.now();

        System.out.println("+---------------------------+\n"+
                 " BIEN LAI GIAO DICH SAVINGS "+
                 "\n NGAY G/D: \t\t\t"+time+
                 "\n ATM ID:\t\t\tDIGITAL-BANK-ATM "+ time.getYear()+
                 "\n SO TK: \t\t\t"+this.getAccNum()+
                 "\n SO TIEN GIAO DICH:\t"+_getAmount(amount)+
                 "\n SO DU: \t\t\t"+super._getBalance()+
                 "\n PHI + VAT:\t\t\t0d"
        );
    }
    public  void logTrans(double amount, double fee, String receiveNumberId){
        LocalDate time = LocalDate.now();

        System.out.println("+---------------------------+\n"+
                " BIEN LAI GIAO DICH TRANSFERS "+
                "\n NGAY G/D: \t\t\t"+time+
                "\n ATM ID:\t\t\tDIGITAL-BANK-ATM "+ time.getYear()+
                "\n SO TK: \t\t\t"+this.getAccNum()+
                "\n so TK nhan: \t\t"+receiveNumberId+
                "\n SO TIEN GIAO DICH:\t"+_getAmount(amount)+
                "\n SO DU: \t\t\t"+super._getBalance()+
                "\n PHI + VAT:\t\t\t0d"
        );
    };
    public boolean withdraw(double amount) {
        double newBalance = 0.0;
        isAccepted(amount);
        if (isAccepted(amount)) {
            newBalance = getBalance() - amount;
            setBalance(newBalance);
            log(amount, 0);
            System.out.println("Giao dich thanh cong");
            return true;
        }
        System.out.println("So tien rut khong kha dung");
        return false;
    }
    public boolean transfers(Account receiveAccount, double amount){

        double newBalance = 0.0;
        isAccepted(amount);
        if(isAccepted(amount)){
            newBalance = getBalance() - amount;
            setBalance(newBalance);
            receiveAccount.setBalance(receiveAccount.getBalance()+amount);
            logTrans(amount,0,receiveAccount.getAccountNumber());
            System.out.println("Giao dich thanh cong");
            update(receiveAccount);
            return true;
        }else {
        System.out.println("Giao dich khong thanh cong");
        return false;
        }
    }
    public boolean isAccepted(double amount) {

        if((amount >= 50000.0) && (amount % 10000.0 == 0) && (getBalance() - amount >= 50000.0)){
            if(isPremium()){
                return true;
            }
            if(!isPremium() && (amount < SAVINGS_ACCOUNT_MAX_WITHDRAW)){
                return true;
            }
        }
        return false;
    }
    public static void update(Account editAccount){
        List<Account> accounts =dao.AccountsDao.accountsListOfFileDat();
        boolean hasExit = accounts.stream().anyMatch(account -> account.getAccountNumber().equalsIgnoreCase(editAccount.getAccountNumber()));
        List<Account>updateAccount;
        if(!hasExit){
            updateAccount = new ArrayList<>(accounts);
            updateAccount.add(editAccount);
        }else {
            updateAccount = new ArrayList<>();
            for(Account account: accounts){
                if(account.getAccountNumber().equals(editAccount.getAccountNumber())){
                    updateAccount.add(editAccount);
                }else {
                    updateAccount.add(account);
                }
            }
        }
        BinaryFileServices.writeFile("store\\accounts.dat",updateAccount);
    }

    public String getAccNum(){return super.getAccountNumber();};
    public void setAccNum(String accNum){super.setAccountNumber(accNum);}
    //
    public void setBala(double bala){super.setBalance(bala);}
    public double getBalance(){
        return super.getBalance();
    }
}
