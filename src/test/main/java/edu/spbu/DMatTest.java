package main.java.edu.spbu;

import java.util.ArrayList;

import static main.java.edu.spbu.SMat.fillArrays;
//import static main.java.edu.spbu.SMat.sizeOfMatrix;
import static main.java.edu.spbu.SMat.printf;
import static org.junit.Assert.assertEquals;

public class DMatTest {
    @org.junit.Test
    public void mulDD() throws Exception {
        DMat a = new DMat("1.txt");
        DMat b = new DMat("2.txt");
        DMat goldenResult = new DMat("12.txt");
        DMat c = (DMat) a.mul(b);
        assertEquals(true, c.equals(goldenResult));
    }

    @org.junit.Test
    public void mulDS() throws Exception {
        DMat a = new DMat("1.txt");
        SMat b = new SMat("3.txt");
        SMat goldenResult = new SMat("13.txt");
        SMat c = (SMat) a.mul(b);
        assertEquals(true, c.equals(goldenResult));
    }

    @org.junit.Test
    public void mulSS() throws Exception {
        SMat a = new SMat("3.txt");
        SMat b = new SMat("4.txt");
        SMat goldenResult = new SMat("34.txt");
        SMat c = (SMat) a.mul(b);
        assertEquals(true, c.equals(goldenResult));
    }

    @org.junit.Test
    public void mulSD() throws Exception {
        SMat a = new SMat("3.txt");
        DMat b = new DMat("4.txt");
        SMat goldenResult = new SMat("34.txt");
        SMat c = (SMat) a.mul(b);
        assertEquals(true, c.equals(goldenResult));
    }
}








