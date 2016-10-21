package main.java.edu.spbu;
import static org.junit.Assert.assertEquals;

public class DMatTest {
    @org.junit.Test
    public void mulDD() throws Exception {
        Matrix a = new DMat("1.txt");
        Matrix b = new DMat("2.txt");
        Matrix goldenResult = new DMat("12.txt");
        Matrix c =  a.mul(b);
        assertEquals(c,goldenResult);
    }


    @org.junit.Test
    public void mulDS() throws Exception {
        Matrix a = new DMat("1.txt");
        Matrix b = new SMat("3.txt");
        Matrix goldenResult = new SMat("13.txt");
        Matrix c =  a.mul(b);
        assertEquals(c,goldenResult);
    }

    @org.junit.Test
    public void mulSS() throws Exception {
        Matrix a = new SMat("3.txt");
        Matrix b = new SMat("4.txt");
        Matrix goldenResult = new SMat("34.txt");
        Matrix c = a.mul(b);
        assertEquals(c,goldenResult);
    }

    @org.junit.Test
    public void mulSD() throws Exception {
        Matrix a = new SMat("3.txt");
        Matrix b = new DMat("4.txt");
        Matrix goldenResult = new SMat("34.txt");
        Matrix c =  a.mul(b);
        assertEquals(c,goldenResult);
    }

}








