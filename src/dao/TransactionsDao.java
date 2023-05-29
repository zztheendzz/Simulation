package dao;
import service.*;
import java.util.List;
import common.*;
public class TransactionsDao {
//    lay danh sach lich su gia dich
    public static List<Transaction> transactionListOfFileDat() {
        List<Transaction> transactions = BinaryFileServices.readFile("store\\transactions.dat");
        return transactions;
    }
    public static void updateTransaction(Transaction transaction) {
        List<Transaction> transactions = BinaryFileServices.readFile("store\\transactions.dat");
        transactions.add(transaction);
        BinaryFileServices.writeFile("store\\transactions.dat", transactions);
    }
}
