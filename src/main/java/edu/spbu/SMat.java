package main.java.edu.spbu;//sparse - разреженный

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class SMat implements Matrix {

    @Override
    public Matrix mul(Matrix bb) {
        SMat a = this;
        SMat res = null;
        if (bb instanceof DMat) {
            DMat b = (DMat) bb;
            res = a.mulSD(b);
        }
        if (bb instanceof SMat) {
            SMat b = (SMat) bb;
            res = a.mulSS(b);
        }

        return res;

    }

    @Override
    public void saveToFile(String nameOfFile) throws IOException {
        SMat c = this;
        double nol = 0;
        PrintWriter printWriter = new PrintWriter(new FileWriter(nameOfFile));
        for (int i = 0; i < c.pointers.size() - 1; i++) {
            ArrayList<Integer> k = new ArrayList();
            for (int ii = c.pointers.get(i); ii < c.pointers.get(i + 1); ii++) {
                k.add(ii);
            }
            if (k.size() != 0) {
                int elemOfMasK = 0;
                for (int j = 0; j < c.pointers.size() - 1; j++) {

                    if (elemOfMasK == k.size()) {
                        for (int l = j; l < c.pointers.size() - 1; l++) printWriter.print(nol + " ");


                        break;
                    }

                    if (c.cols.get(k.get(elemOfMasK)) == j) {

                        printWriter.print(c.values.get(k.get(elemOfMasK)) + " ");
                        elemOfMasK++;

                    } else printWriter.print(nol + " ");

                }

                printWriter.println();
            } else {
                for (int j = 0; j < c.pointers.size() - 1; j++) printWriter.print(nol + " ");
                printWriter.println();
            }
        }
        printWriter.close();
    }

    private ArrayList<Double> values;
    private ArrayList<Integer> cols;
    private ArrayList<Integer> pointers;
    public int sizeOfMatrix = 0;

    public static void main(String[] args) throws IOException {
        SMat a = new SMat();
        fillArrays(a, "3.txt");
        SMat b = new SMat();
        fillArrays(b, "4.txt");
        SMat c;
        DMat dMat = new DMat();
        dMat.list = dMat.getMat("1.txt");
        dMat.arr = dMat.toArray(dMat.list);
        SMat ds=(SMat) dMat.mul(a);
        ds.saveToFile("mulDSResult.txt");

        if (a.sizeOfMatrix == b.sizeOfMatrix) {
            c = (SMat) a.mul(b);
            c.sizeOfMatrix = a.sizeOfMatrix;
            c.saveToFile("mulSSResult.txt");
        }

    }


    private SMat mulSD(DMat b) {
        SMat c = new SMat();
        SMat a = this;

        c.values = new ArrayList<>();
        c.cols = new ArrayList<>();
        c.pointers = new ArrayList<>();
        double res;
        c.pointers.add(0);
        for (int stroka = 0; stroka < a.pointers.size() - 1; stroka++) {
            for (int stolb = 0; stolb < b.list.size(); stolb++) {
                res = 0;
                SMat v1 = new SMat();
                v1.sizeOfMatrix = a.sizeOfMatrix;
                v1.values = new ArrayList<>();
                v1.cols = new ArrayList<>();
                v1.pointers = new ArrayList<>();
                for (int i = a.pointers.get(stroka); i < a.pointers.get(stroka + 1); i++) {
                    v1.values.add(a.values.get(i));
                    v1.cols.add(a.cols.get(i));
                }
                //res = v1.scalarMul(v2);

                //printf(v1);
                for (int i = 0; i < v1.values.size(); i++) {
                    res = res + v1.values.get(i) * b.arr[v1.cols.get(i)][stolb];
                }

                if (res != 0) {
                    c.values.add(res);
                    c.cols.add(stolb);
                }
            }
            c.pointers.add(c.values.size());
        }


        return c;
    }

    private SMat mulSS(SMat b) {
        SMat a = this;
        SMat c = new SMat();
        b = b.transpose(b);
        c.values = new ArrayList<>();
        c.cols = new ArrayList<>();
        c.pointers = new ArrayList<>();
        double res;
        c.pointers.add(0);
        for (int stroka = 0; stroka < a.pointers.size() - 1; stroka++) {
            for (int stolb = 0; stolb < b.pointers.size() - 1; stolb++) {
                SMat v1 = new SMat();
                v1.sizeOfMatrix = a.sizeOfMatrix;
                v1.values = new ArrayList<>();
                v1.cols = new ArrayList<>();
                v1.pointers = new ArrayList<>();
                SMat v2 = new SMat();
                v2.values = new ArrayList<>();
                v2.cols = new ArrayList<>();
                v2.pointers = new ArrayList<>();
                for (int i = a.pointers.get(stroka); i < a.pointers.get(stroka + 1); i++) {
                    v1.values.add(a.values.get(i));
                    v1.cols.add(a.cols.get(i));
                }
                for (int i = b.pointers.get(stolb); i < b.pointers.get(stolb + 1); i++) {
                    v2.values.add(b.values.get(i));
                    v2.cols.add(b.cols.get(i));
                }
                res = v1.scalarMul(v2);
                if (res != 0) {
                    c.values.add(res);
                    c.cols.add(stolb);
                }
            }
            c.pointers.add(c.values.size());
        }
        return c;
    }

    private double scalarMul(SMat b) {
        SMat a = this;
        int[] x = new int[a.sizeOfMatrix];
        for (int i = 0; i < x.length; i++) x[i] = -1;
        double s = 0;
        for (int i = 0; i < a.values.size(); i++) {
            x[a.cols.get(i)] = i;
        }
        for (int i = 0; i < b.values.size(); i++) {
            if (x[b.cols.get(i)] != -1) {
                s = s + b.values.get(i) * a.values.get(x[b.cols.get(i)]);
            }
        }
        return s;
    }

    private static void fillArrays(SMat a, String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String s = reader.readLine();
        a.values = new ArrayList<>();
        a.cols = new ArrayList<>();
        a.pointers = new ArrayList<>();
        a.pointers.add(0);
        while (s != null) {
            a.sizeOfMatrix++;
            int collNumber = 0;
            for (String val : s.split(" ")) {

                if (Double.parseDouble(val) != 0) {
                    a.values.add(Double.parseDouble(val));
                    a.cols.add(collNumber);
                }
                collNumber++;
            }


            s = reader.readLine();
            if (s != null) a.pointers.add(a.values.size());
        }
        a.pointers.add(a.values.size());

    }

    public SMat transpose(SMat a) {
        int j;
        double v;
        ArrayList intVectors[] = new ArrayList[a.pointers.size() - 1];
        ArrayList doubleVectors[] = new ArrayList[a.pointers.size() - 1];

        for (int i = 0; i < intVectors.length; i++) intVectors[i] = new ArrayList();
        for (int i = 0; i < doubleVectors.length; i++) doubleVectors[i] = new ArrayList();

        for (int i = 0; i < a.pointers.size() - 1; i++) {
            for (int k = a.pointers.get(i); k < a.pointers.get(i + 1); k++) {
                j = a.cols.get(k);
                v = a.values.get(k);
                intVectors[j].add(i);
                doubleVectors[j].add(v);
            }
        }
        a.cols.clear();
        a.values.clear();
        int size = a.pointers.size();
        a.pointers.clear();
        a.pointers.add(0);
        for (int i = 1; i < size; i++) {
            a.pointers.add(a.pointers.get(i - 1) + intVectors[i - 1].size());
        }
        for (int i = 0; i < intVectors.length; i++) {
            for (int k = 0; k < intVectors[i].size(); k++) {
                a.cols.add((int) intVectors[i].get(k));
                a.values.add((double) doubleVectors[i].get(k));

            }
        }

        return a;
    }

    private static void printf(SMat a) {
        printf(a.values);
        printf(a.cols);
        if (a.pointers != null) printf(a.pointers);
    }

    private static void printf(ArrayList arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.print(arrayList.get(i) + "  ");
        }
        System.out.println();
    }


}
