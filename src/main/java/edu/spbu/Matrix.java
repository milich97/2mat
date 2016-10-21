package edu.spbu;

import java.io.IOException;

public interface Matrix {


    Matrix mul(Matrix b) throws IOException;


    void saveToFile(String nameOfFile) throws IOException;
}
