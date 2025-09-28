package io;

import utils.IO;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

// Generally Use for testing
public class ByteArrayInputStreamDemo {
    public static void main(String[] args) throws IOException {
        String str = "Hello World!";
        ByteArrayInputStream fis = new ByteArrayInputStream(str.getBytes());

        int tmp;
        while ((tmp=fis.read())!=-1) {
            System.out.print((char)tmp);
        }
    }
}

