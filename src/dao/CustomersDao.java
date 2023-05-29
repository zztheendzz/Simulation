package dao;
import service.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import common.*;

import common.*;
public class CustomersDao {
    private final static String FILE_PATH= "customers.dat";

// danh sach khach hang tu file dat
    public static List<Customer> customerListOfFileDat() {
        List<Customer> customers = BinaryFileServices.readFile("store\\customers.dat");
        return customers;
    }
//    cap nhat danh sach khach hang
    public static void updateCustomer(List<Customer> customerList ){
        BinaryFileServices.writeFile("store\\customers.dat",customerList);
    }
//    doc file text
    public static  List<Customer> readtxt(String txt) {
        List<Customer> customerList = new ArrayList<>();
        Scanner scanner = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(txt);
            scanner = new Scanner(fileInputStream);
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                Customer customer = new Customer();
                String name = scanner.next();
                scanner.skip(scanner.delimiter());
                String id = scanner.nextLine();

                customer.setName(name);
                customer.setCustomerId(id);
                customerList.add(customer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return customerList;
    }
}