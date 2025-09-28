package io;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import utils.IO;

public class FileInputStreamDemo {

    public static void main(String[] args) throws Exception {
        String path = System.getProperty("user.dir");
        path += "/assets/questions.txt";
        FileInputStream fis = new FileInputStream(path);
        StringBuilder buffer = new StringBuilder();

        // via read
        readCharViaReadMethod(buffer,fis);
        buffer.setLength(0);



    }
    // Read each character one by one.
    static void readCharViaReadMethod(StringBuilder buffer, FileInputStream fis) throws IOException {
        int data;
        while ((data = fis.read()) != -1)
            buffer.append((char) data);
    }

    static void readCharViaReadAllBytesMethod(StringBuilder buffer, InputStream fis) throws IOException {
        String data =  new String(fis.readAllBytes());
        buffer.append(data);
    }
}

