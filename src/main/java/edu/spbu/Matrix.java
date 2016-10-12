package main.java.edu.spbu;

import java.io.IOException;

public interface Matrix {


    Matrix mul(Matrix b);


    void saveToFile(String nameOfFile) throws IOException;
}
