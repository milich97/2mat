//package main.java.edu.spbu;
//
//import java.io.*;
//import java.util.ArrayList;
//
//public class Mat {
//    static BufferedReader r;
//    static ArrayList<ArrayList> arrayList;
//    static ArrayList<Double> row;
//    public ArrayList<ArrayList> getMat;
//    private String fileName;
//    private double[][] arr;
//    private ArrayList list;
//
//    public static void main(String[] args) throws IOException {
//        Mat fill = new Mat();
//        Mat a = new Mat();
//        Mat b = new Mat();
//        a.list = new ArrayList();
//        b.list = new ArrayList();
//        a.list = fill.getMat("1.txt");
//        b.list = fill.getMat("2.txt");
//        a.arr = a.toArray(a.list);
//        b.arr = b.toArray(b.list);
//        Mat c = new Mat();
//        c.arr = new double[a.arr.length][a.arr.length];
//        c.arr = a.mul(b);
//        c.saveToFile();
//
//
//    }
//
//    private void saveToFile() throws IOException {
//        Mat c = this;
//        PrintWriter printWriter = new PrintWriter(new FileWriter("Result.txt"));
//        for (int i = 0; i < c.arr.length; i++) {
//            for (int j = 0; j < c.arr.length; j++) {
//                printWriter.print(c.arr[i][j] + " ");
//            }
//            printWriter.println();
//        }
//        printWriter.close();
//    }
//
//
//    public double[][] add(Mat b) {
//
//        Mat a = this;
//        Mat res = new Mat();
//        res.arr = new double[a.arr.length][a.arr.length];
//        for (int i = 0; i < a.arr.length; i++) {
//            for (int j = 0; j < a.arr.length; j++) {
//                res.arr[i][j] = a.arr[i][j] + b.arr[i][j];
//            }
//        }
//
//        return res.arr;
//    }
//
//
//    public double[][] mul(Mat b) {
//
//        Mat a = this;
//        Mat res = new Mat();
//        res.arr = new double[a.arr.length][a.arr.length];
//        for (int i = 0; i < a.arr.length; i++) {
//            for (int j = 0; j < a.arr.length; j++) {
//                for (int k = 0; k < a.arr.length; k++) {
//                    res.arr[i][j] = res.arr[i][j] + a.arr[i][k] * b.arr[k][j];
//                }
//            }
//        }
//
//        return res.arr;
//    }
//
//    private void openFile(String fileName) {
//        try {
//            r = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
//        } catch (Exception e) {
//            System.out.println("not found");
//        }
//    }
//
//    private static void readFile() throws IOException {
//
//        String s = r.readLine();
//
//        while (s != null) {
//            for (String val : s.split(" ")) {
//                row.add(Double.parseDouble(val));
//            }
//            arrayList.add((ArrayList) row.clone());
//            row.clear();
//            s = r.readLine();
//        }
//
//    }
//
//    public ArrayList<ArrayList> getMat(String fileName) throws IOException {
//        arrayList = new ArrayList<ArrayList>();
//        row = new ArrayList<Double>();
//        this.fileName = fileName;
//        openFile(fileName);
//        readFile();
//        return arrayList;
//    }
//
//    private double[][] toArray(ArrayList<ArrayList> arrayList) {
//        double[][] res = new double[arrayList.size()][arrayList.size()];
//        for (int i = 0; i < arrayList.size(); i++) {
//            for (int j = 0; j < arrayList.size(); j++) {
//                res[i][j] = (double) arrayList.get(i).get(j);
//            }
//        }
//        return res;
//    }
//
//}