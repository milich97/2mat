package main.java.edu.spbu;

import java.util.ArrayList;

import static main.java.edu.spbu.SMat.fillArrays;
//import static main.java.edu.spbu.SMat.sizeOfMatrix;
import static org.junit.Assert.assertEquals;

public class DMatTest {
    @org.junit.Test
    public void mulDD() throws Exception {
        DMat a = new DMat();
        a.list = new ArrayList();
        a.list = a.getMat("1.txt");
        a.arr = a.toArray(a.list);
        DMat b = new DMat();
        b.list = new ArrayList();
        b.list = b.getMat("2.txt");
        b.arr = b.toArray(b.list);
        DMat goldenResult = new DMat();
        goldenResult.list = new ArrayList();
        goldenResult.list = a.getMat("12.txt");
        goldenResult.arr = goldenResult.toArray(goldenResult.list);
        DMat c = (DMat) a.mul(b);
        assertEquals(true,c.equals(goldenResult));
    }

    @org.junit.Test
    public void mulDS() throws Exception {
        DMat a = new DMat();
        a.list = new ArrayList();
        a.list = a.getMat("1.txt");
        a.arr = a.toArray(a.list);
        SMat b = new SMat();
        fillArrays(b, "3.txt");
        SMat goldenResult = new SMat();
        fillArrays(goldenResult, "13.txt");
        SMat c = (SMat) a.mul(b);
        assertEquals(goldenResult.values, c.values);
    }
    @org.junit.Test
    public void mulSS() throws Exception {
        SMat a=new SMat();
        SMat b = new SMat();
        fillArrays(a,"3.txt");
        fillArrays(b, "4.txt");
        SMat goldenResult = new SMat();
        fillArrays(goldenResult, "34.txt");
        SMat c = (SMat) a.mul(b);
        assertEquals(goldenResult.values, c.values);
        assertEquals(goldenResult.cols, c.cols);
        assertEquals(goldenResult.pointers, c.pointers);

    }
    @org.junit.Test
    public void mulSD() throws Exception {
        SMat a=new SMat();
        fillArrays(a,"3.txt");
        DMat b = new DMat();
        b.list = new ArrayList();
        b.list = b.getMat("1.txt");
        b.arr = b.toArray(b.list);
        SMat goldenResult = new SMat();
        fillArrays(goldenResult, "34.txt");
        SMat c = (SMat) a.mul(b);
        assertEquals(goldenResult.pointers, c.pointers);
        assertEquals(goldenResult.values, c.values);
        assertEquals(goldenResult.cols, c.cols);
        assertEquals(goldenResult.sizeOfMatrix, c.sizeOfMatrix);
    }
}








