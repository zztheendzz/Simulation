package common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.*;


public class DigitalBank extends Bank {
    private static String CUSTOMER_ID = "";
    private static String CUSTOMER_NAME;


    public static String getCUS_ID() {
        return CUSTOMER_ID;
    }

    public static String getCUS_NAME() {
        return CUSTOMER_NAME;
    }

    public void addAccount(String customerID, Account account) {
        for (Customer customer :
                getCustomers()) {
            if (customer.getCustomerId().equals((customerID))) {
                customer.addAccount(account);
                return;
            }
        }
    }
    public void withDraw(String customerId, String accountNumber, double amount) {
        for (int i = 0; i < getCustomers().size(); i++) {
            if (getCustomers().get(i).getCustomerId() == customerId) {
                for (int j = 0; j < getCustomers().get(i).getAccounts().size(); j++) {
                    if (getCustomers().get(i).getAccounts().get(j).getAccountNumber() == accountNumber) {
                    }
                }
            }
        }
    }

    public void test() {

        try {
            FileOutputStream fo = new FileOutputStream("C:\\Users\\Trong\\IdeaProjects\\ams04\\store\\creat2.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fo);
            for (int i = 0; i < this.getDigitalCusList().size(); i++) {
                oos.writeObject(this.getDigitalCusList().get(i));
                oos.close();
                fo.close();
            }
            System.out.println(this.getDigitalCusList().size() + "");

            try (FileInputStream fos = new FileInputStream("C:\\Users\\Trong\\IdeaProjects\\ams04\\store\\creat2.txt");
                 ObjectInputStream ois = new ObjectInputStream(fos);) {
                DigitalCus cus = (DigitalCus) ois.readObject();

                System.out.println(cus.get_name() + "111");
                System.out.println(cus.toString() + "321");
                oos.close();
                fo.close();
            } catch (IOException i) {
                i.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
