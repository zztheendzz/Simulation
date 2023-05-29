import java.util.*;

import common.*;

public class Main {
    public static void main(String[] arg) {

        System.out.println("chuc nang 2: \"store\\\\customers.txt\"");
        String _cn="" ;
        Bank bank = new Bank();
        do {
            final String AUTHOR = "FX19552";
            final String VERSION = "@v4.0.0";
            System.out.println("--------------------------------+\n"
                    + "|NGAN HANG SO | " + AUTHOR + VERSION + " |\n"
                    + "--------------------------------+ \n"
                    + "|1. Xem danh sach khach hang  \n"
                    + "|2. Nhap danh sach khach hang  \n"
                    + "|3. Them tai khoan ATM  \n"
                    + "|4. Chuyen tien  \n"
                    + "|5. Rut tien  \n"
                    + "|6. Tra cuu lich su giao dich\n"
                    + "|0. Thoat  \n"
                    + "--------------------------------+ \n"
                    + "Chuc nang:");

                Scanner cn = new Scanner(System.in);
                _cn = cn.nextLine();

            switch (_cn) {
                case "1":
                    bank.showCustomers();
                    break;
                case "2":
                    bank.addCustomers();
                    break;
                case "3":
                    bank.addSavingAccount();
                    break;
                case "4":
                    bank.tranfers();
                    break;
                case "5":
                    bank.withdraw();
                    break;
                case "6":
                    bank.transaction();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Chon tu 0-6");
                    break;
            }
        }
        while (_cn != "0");
    }
}









