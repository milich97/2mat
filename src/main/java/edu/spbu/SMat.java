package edu.spbu;//sparse - разреженный

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class SMat implements Matrix {

    public SMat(String fileName) throws IOException {
        if (fileName != null) {
            fillArrays(this, fileName);
        }
    }

    private double[] toArray1(ArrayList<Double> arrayList) {
        double[] res = new double[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            res[i] = (double) arrayList.get(i);
        }

        return res;
    }

    private int[] toArray2(ArrayList<Integer> arrayList) {
        int[] res = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            res[i] = (int) arrayList.get(i);
        }

        return res;
    }


    @Override
    public Matrix mul(Matrix bb) throws IOException {
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
        for (int i = 0; i < c.pointersArr.length - 1; i++) {
            ArrayList<Integer> k = new ArrayList();
            for (int ii = c.pointersArr[i]; ii < c.pointersArr[i + 1]; ii++) {
                k.add(ii);
            }
            if (k.size() != 0) {
                int elemOfMasK = 0;
                for (int j = 0; j < c.pointersArr.length - 1; j++) {

                    if (elemOfMasK == k.size()) {
                        for (int l = j; l < c.pointersArr.length - 1; l++) printWriter.print(nol + " ");


                        break;
                    }

                    if (c.colsArr[k.get(elemOfMasK)] == j) {

                        printWriter.print(c.valuesArr[k.get(elemOfMasK)] + " ");
                        elemOfMasK++;

                    } else printWriter.print(nol + " ");

                }

                printWriter.println();
            } else {
                for (int j = 0; j < c.pointersArr.length - 1; j++) printWriter.print(nol + " ");
                printWriter.println();
            }
        }
        printWriter.close();
    }

    public double[] valuesArr;
    public int[] colsArr;
    public int[] pointersArr;
    public int sizeOfMatrix;


    public static void main(String[] args) throws IOException {
        DMat d1 = new DMat("3.txt");
        DMat d2 = new DMat("3.txt");
        SMat s1 = new SMat("3.txt");
        SMat s2 = new SMat("3.txt");


        if (d1.arr.length == d2.arr.length) {
            DMat dd = (DMat) d1.mul(d2);
            dd.saveToFile("mulDDResult.txt");
        }
        if (s1.sizeOfMatrix == s2.sizeOfMatrix) {
            SMat ss = (SMat) s1.mul(s2);
            ss.saveToFile("mulSSResult.txt");
        }
        if (s1.sizeOfMatrix == d1.arr.length) {
            SMat sd = (SMat) s1.mul(d1);
            sd.saveToFile("mulSDResult.txt");
        }
        if (d1.arr.length == s1.sizeOfMatrix) {
            SMat ds = (SMat) d1.mul(s1);
            ds.saveToFile("mulDSResult.txt");
        }
    }


    private SMat mulSD(DMat b) throws IOException {
        SMat c = new SMat(null);
        SMat a = this;
        c.sizeOfMatrix = a.sizeOfMatrix;
        ArrayList<Double> values = new ArrayList<>();
        ArrayList<Integer> cols = new ArrayList<>();
        ArrayList<Integer> pointers = new ArrayList<>();
        double res;
        pointers.add(0);
        for (int stroka = 0; stroka < a.pointersArr.length - 1; stroka++) {
            for (int stolb = 0; stolb < b.arr.length; stolb++) {
                res = 0;
                SMat v1 = new SMat(null);
                v1.sizeOfMatrix = a.sizeOfMatrix;
                v1.valuesArr = new double[a.pointersArr[stroka + 1] - a.pointersArr[stroka]];
                v1.colsArr = new int[a.pointersArr[stroka + 1] - a.pointersArr[stroka]];
                for (int i = a.pointersArr[stroka]; i < a.pointersArr[stroka + 1]; i++) {
                    v1.valuesArr[i - a.pointersArr[stroka]] = a.valuesArr[i];
                    v1.colsArr[i - a.pointersArr[stroka]] = a.colsArr[i];
                }


                for (int i = 0; i < v1.valuesArr.length; i++) {
                    res = res + v1.valuesArr[i] * b.arr[v1.colsArr[i]][stolb];
                }

                if (res != 0) {
                    values.add(res);
                    cols.add(stolb);
                }
            }
            pointers.add(values.size());
        }
        c.valuesArr = toArray1(values);
        c.colsArr = toArray2(cols);
        c.pointersArr = toArray2(pointers);

        return c;
    }

    private SMat mulSS(SMat b) throws IOException {
        SMat a = this;
        SMat c = new SMat(null);
        b = b.transpose(b);
        ArrayList<Double> values = new ArrayList<>();
        ArrayList<Integer> cols = new ArrayList<>();
        ArrayList<Integer> pointers = new ArrayList<>();
        c.sizeOfMatrix = a.sizeOfMatrix;
        double res;
        pointers.add(0);
        for (int stroka = 0; stroka < a.pointersArr.length - 1; stroka++) {
            for (int stolb = 0; stolb < b.pointersArr.length - 1; stolb++) {
                SMat v1 = new SMat(null);
                v1.sizeOfMatrix = a.sizeOfMatrix;
                v1.valuesArr = new double[a.pointersArr[stroka + 1] - a.pointersArr[stroka]];
                v1.colsArr = new int[a.pointersArr[stroka + 1] - a.pointersArr[stroka]];
                SMat v2 = new SMat(null);
                v2.sizeOfMatrix = b.sizeOfMatrix;
                v2.valuesArr = new double[b.pointersArr[stolb + 1] - b.pointersArr[stolb]];
                v2.colsArr = new int[b.pointersArr[stolb + 1] - b.pointersArr[stolb]];

                for (int i = a.pointersArr[stroka]; i < a.pointersArr[stroka + 1]; i++) {
                    v1.valuesArr[i - a.pointersArr[stroka]] = a.valuesArr[i];
                    v1.colsArr[i - a.pointersArr[stroka]] = a.colsArr[i];
                }

                for (int i = b.pointersArr[stolb]; i < b.pointersArr[stolb + 1]; i++) {
                    v2.valuesArr[i - b.pointersArr[stolb]] = b.valuesArr[i];
                    v2.colsArr[i - b.pointersArr[stolb]] = b.colsArr[i];
                }
                res = v1.scalarMul(v2);
                if (res != 0) {
                    values.add(res);
                    cols.add(stolb);
                }
            }
            pointers.add(values.size());
        }
        c.valuesArr = toArray1(values);
        c.colsArr = toArray2(cols);
        c.pointersArr = toArray2(pointers);
        return c;
    }

    private double scalarMul(SMat b) {
        SMat a = this;
        int[] x = new int[a.sizeOfMatrix];
        for (int i = 0; i < x.length; i++) x[i] = -1;
        double s = 0;
        for (int i = 0; i < a.valuesArr.length; i++) {
            x[a.colsArr[i]] = i;
        }
        for (int i = 0; i < b.valuesArr.length; i++) {
            if (x[b.colsArr[i]] != -1) {
                s = s + b.valuesArr[i] * a.valuesArr[x[b.colsArr[i]]];
            }
        }
        return s;
    }

    public static void fillArrays(SMat a, String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String s = reader.readLine();
        ArrayList<Double> values = new ArrayList<>();
        ArrayList<Integer> cols = new ArrayList<>();
        ArrayList<Integer> pointers = new ArrayList<>();
        pointers.add(0);
        a.sizeOfMatrix = 0;
        while (s != null) {
            a.sizeOfMatrix++;
            int collNumber = 0;
            for (String val : s.split(" ")) {

                if (Double.parseDouble(val) != 0) {
                    values.add(Double.parseDouble(val));
                    cols.add(collNumber);
                }
                collNumber++;
            }


            s = reader.readLine();
            if (s != null) pointers.add(values.size());
        }
        pointers.add(values.size());
        a.valuesArr = a.toArray1(values);
        a.colsArr = a.toArray2(cols);
        a.pointersArr = a.toArray2(pointers);
    }

    public SMat transpose(SMat a) throws IOException {
        int j;
        double v;
        SMat newA = new SMat(null);
        newA.sizeOfMatrix = a.sizeOfMatrix;
        ArrayList intVectors[] = new ArrayList[a.pointersArr.length - 1];
        ArrayList doubleVectors[] = new ArrayList[a.pointersArr.length - 1];
        for (int i = 0; i < intVectors.length; i++) intVectors[i] = new ArrayList();
        for (int i = 0; i < doubleVectors.length; i++) doubleVectors[i] = new ArrayList();
        for (int i = 0; i < a.pointersArr.length - 1; i++) {
            for (int k = a.pointersArr[i]; k < a.pointersArr[i + 1]; k++) {
                j = a.colsArr[k];
                v = a.valuesArr[k];
                intVectors[j].add(i);
                doubleVectors[j].add(v);
            }
        }
        newA.valuesArr = new double[a.valuesArr.length];
        newA.colsArr = new int[a.colsArr.length];
        newA.pointersArr = new int[a.pointersArr.length];
        newA.pointersArr[0] = 0;
        for (int i = 1; i < a.pointersArr.length; i++)
            newA.pointersArr[i] = newA.pointersArr[i - 1] + intVectors[i - 1].size();
        int newK = -1;
        for (int i = 0; i < intVectors.length; i++) {
            for (int k = 0; k < intVectors[i].size(); k++) {
                newK++;
                newA.colsArr[newK] = (int) intVectors[i].get(k);
                newA.valuesArr[newK] = (double) doubleVectors[i].get(k);

            }
        }

        return newA;
    }

    public static void printf(SMat a) {
        printf(a.valuesArr);
        printf(a.colsArr);
        if (a.pointersArr != null) printf(a.pointersArr);
    }

    private static void printf(DMat a) {
        for (int i = 0; i < a.arr.length; i++) {
            for (int j = 0; j < a.arr.length; j++) {
                System.out.print(a.arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printf(double[] d) {
        for (int i = 0; i < d.length; i++) {
            System.out.print(d[i] + "  ");
        }
        System.out.println();
    }

    public static void printf(int[] d) {
        for (int i = 0; i < d.length; i++) {
            System.out.print(d[i] + "  ");
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object bm) {
        boolean ans;
        if (bm instanceof SMat) {
            SMat b = (SMat) bm;
            SMat a = this;
            boolean ans1 = true;
            if (a.valuesArr.length == b.valuesArr.length && a.colsArr.length == b.colsArr.length && a.pointersArr.length == b.pointersArr.length) {
                for (int i = 0; i < a.valuesArr.length; i++) {
                    if (a.valuesArr[i] != b.valuesArr[i] || a.colsArr[i] != b.colsArr[i])
                        ans1 = false;


                }
                for (int i = 0; i < a.pointersArr.length; i++) {
                    if (a.pointersArr[i] != b.pointersArr[i]) ans1 = false;
                }
                if (a.sizeOfMatrix != b.sizeOfMatrix) ans1 = false;
            } else ans1 = false;
            ans = ans1;
        } else ans = false;
        return ans;
    }

}
