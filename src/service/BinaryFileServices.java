package service;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BinaryFileServices implements Serializable {
    private static final long serialVersionUID = 1L;

    // kiem tra file co object hay khong
    public static boolean hasObject(File f) {
        // thu doc xem co object nao chua
        FileInputStream fi;
        boolean check = true;
        try {
            fi = new FileInputStream(f);
            ObjectInputStream inStream = new ObjectInputStream(fi);
            if (inStream.readObject() == null) {
                check = false;
            }
            inStream.close();

        } catch (FileNotFoundException e) {
            check = false;
        } catch (IOException e) {
            check = false;
        } catch (ClassNotFoundException e) {
            check = false;
            e.printStackTrace();
        }
        return check;
    }

    public static void _writeCustomer(String fileName) {
        try {
            File f = new File(fileName);
            FileOutputStream fo;
            ObjectOutputStream oStream = null;

            // neu file chua ton tai thi tao file va ghi binh thuong
            if (!f.exists()) {
                fo = new FileOutputStream(f);
                oStream = new ObjectOutputStream(fo);
            } else { // neu file ton tai

                // neu chua co thi ghi binh thuong
                if (!hasObject(f)) {
                    fo = new FileOutputStream(f);
                    oStream = new ObjectOutputStream(fo);
                } else { // neu co roi thi ghi them vao
                    fo = new FileOutputStream(f, true);
                    oStream = new ObjectOutputStream(fo) {
                        protected void writeStreamHeader() throws IOException {
                            reset();
                        }
                    };
                }
            }
            oStream.writeObject(oStream);
            oStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> void writeObject(String fileName, Object object) {
        try (ObjectOutputStream file = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            file.writeObject(object);
        } catch (IOException io) {
            System.out.println("IO Exception" + io.getMessage());
        }
    }

    public static <T> void writeFile(String fileName, List<T> objects) {

        try (ObjectOutputStream file = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            for (T object : objects) {
                file.writeObject(object);
            }
        } catch (IOException io) {
            System.out.println("IO Exception" + io.getMessage());
        }
    }
    public static <T> List<T> readFile(String fileName) {

        List<T> objects = new ArrayList<>();
        try (ObjectInputStream file = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
            boolean eof = false;
            while (!eof) {
                try {
                    T object = (T) file.readObject();
                    objects.add(object);
                } catch (EOFException e) {
                    eof = true;
                }
            }
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException io) {
            System.out.println("IO Exception" + io.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException" + e.getMessage());
        }

        return objects;
    }

}
