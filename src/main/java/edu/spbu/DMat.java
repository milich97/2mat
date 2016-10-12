package main.java.edu.spbu;//D-dense-плотный

import java.io.*;
import java.util.ArrayList;


public class DMat implements Matrix {

    @Override
    public Matrix mul(Matrix bb) {
        DMat resD;
        SMat resS;
        DMat a = this;

        if (bb instanceof DMat) {
            DMat b = (DMat) bb;
            resD = a.mulDD(b);
            return resD;
        }
        if (bb instanceof SMat) {
            SMat b = (SMat) bb;
            resS = (SMat) a.mulDS(b);
            return resS;
        } else return null;


    }


    @Override
    public void saveToFile(String nameOfFile) throws IOException {
        DMat c = this;
        PrintWriter printWriter = new PrintWriter(new FileWriter(nameOfFile));
        for (int i = 0; i < c.arr.length; i++) {
            for (int j = 0; j < c.arr.length; j++) {
                printWriter.print(c.arr[i][j] + " ");
            }
            printWriter.println();
        }
        printWriter.close();
    }


    static BufferedReader r;
    static ArrayList<ArrayList> arrayList;
    static ArrayList<Double> row;
    private String fileName;
    public double[][] arr;
    public ArrayList list;

    public static void main(String[] args) throws IOException {
        DMat fill = new DMat();
        DMat a = new DMat();
        DMat b = new DMat();
        a.list = new ArrayList();
        b.list = new ArrayList();
        a.list = fill.getMat("1.txt");
        b.list = fill.getMat("3.txt");
        a.arr = a.toArray(a.list);
        b.arr = b.toArray(b.list);
        DMat c;
        if (a.arr.length == b.arr.length) {
            c = (DMat) a.mul(b);
            c.saveToFile("mulDDResult.txt");

        }
    }

    private DMat mulDD(DMat b) {
        DMat a = this;
        DMat res = new DMat();
        res.arr = new double[a.arr.length][a.arr.length];
        for (int i = 0; i < a.arr.length; i++) {
            for (int j = 0; j < a.arr.length; j++) {
                for (int k = 0; k < a.arr.length; k++) {
                    res.arr[i][j] = res.arr[i][j] + a.arr[i][k] * b.arr[k][j];
                }
            }
        }
        return res;
    }

    private Matrix mulDS(SMat b) {
        SMat c;
        DMat a = this;
        a = a.transpose(a);
        c = (SMat) b.transpose(b).mul(a);
        c = c.transpose(c);

        return c;
    }

    private DMat transpose(DMat a) {
        for (int i = 1; i < a.arr.length; i++) {
            for (int j = 0; j < i; j++) {
                double k = a.arr[i][j];
                a.arr[i][j] = a.arr[j][i];
                a.arr[j][i] = k;
            }
        }
        return a;
    }

    private void printf(DMat a) {
        for (int i = 0; i < a.arr.length; i++) {
            for (int j = 0; j < a.arr.length; j++) {
                System.out.print(a.arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void openFile(String fileName) {
        try {
            r = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        } catch (Exception e) {
            System.out.println("not found");
        }
    }

    private void readFile() throws IOException {

        String s = r.readLine();

        while (s != null) {
            for (String val : s.split(" ")) {
                row.add(Double.parseDouble(val));
            }
            arrayList.add((ArrayList) row.clone());
            row.clear();
            s = r.readLine();
        }

    }

    public ArrayList<ArrayList> getMat(String fileName) throws IOException {
        arrayList = new ArrayList<ArrayList>();
        row = new ArrayList<Double>();
        this.fileName = fileName;
        openFile(fileName);
        readFile();
        return arrayList;
    }

    public double[][] toArray(ArrayList<ArrayList> arrayList) {
        double[][] res = new double[arrayList.size()][arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.size(); j++) {
                res[i][j] = (double) arrayList.get(i).get(j);
            }
        }
        return res;
    }
}