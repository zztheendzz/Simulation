package dao;
import service.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import common.*;


public class AccountsDao {
    private final static String FILE_PATH = "store/customers.dat";

    //    danh sach tai khoan khach hang từ file dat
    public static List<Account> accountsListOfFileDat() {
        List<Account> accounts = BinaryFileServices.readFile("store\\Accounts.dat");
        return accounts;
    }

    // update acc
    public static void updateAccount(Account editAccount) {
        List<Account> accounts = BinaryFileServices.readFile("store\\accounts.dat");
        boolean hasExit = accounts.stream().anyMatch(account -> account.getAccountNumber().equalsIgnoreCase(editAccount.getAccountNumber()));
        List<Account> updateAccount;
        if (!hasExit) {
            updateAccount = new ArrayList<>(accounts);
            updateAccount.add(editAccount);
        } else {
            updateAccount = new ArrayList<>();
            for (Account account : accounts) {
                if (account.getAccountNumber().equalsIgnoreCase(editAccount.getAccountNumber())) {
                    updateAccount.add(editAccount);
                } else {
                    updateAccount.add(account);
                }
            }
        }
        BinaryFileServices.writeFile("store\\accounts.dat", updateAccount);
    }
}
