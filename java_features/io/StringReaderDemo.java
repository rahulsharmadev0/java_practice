package io;

import utils.IO;

import java.awt.*;
import java.io.*;
import java.util.Scanner;

// InputStreamReader -> InputStream
// InputStringReader -> String
// ByteArrayInputStream -> it take byte array as a input kind of reader who will read byte data
// FileInputStream -> file path (read byte data) for Reading
// FileOutputStream -> file path (read byte data) for Writing
// BufferReader -> Reader (StringReader, InputStreamReader)
// DataInputStream -> take InputStream (FileInputStream, ..etc)
// DataOutputStream -> for writing


public class StringReaderDemo {
    public static void main(String[] args) throws IOException {

        String path = System.getProperty("user.dir") + "/temp.dat";
        IO.printf("Writing Start");
        dataWriter(path);
        IO.printf("Writing End");
        IO.printf("Reading Start");
        dataReader(path);
        IO.printf("Reading End");


    }

    private  static  void dataReader(String path)throws  IOException{
        DataInputStream dis = new DataInputStream(new FileInputStream(path));
       var _Int = dis.readInt();
       var _Char = dis.readChar();
       var _Char2 = dis.readChar();
//       var _Char3 = dis.readChar(); if you get char which not exit (not written) then throws error
       var _Double = dis.readDouble();
       var _str = dis.readUTF();
        dis.close();

        System.out.println(_Int);
        System.out.println(_Char);
        System.out.println(_Char2);
        System.out.println(_Double);
        System.out.println(_str);
    }

    private static void dataWriter(String path) throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(path));
        dos.writeInt(232);
        dos.writeChar('a');
        dos.writeChar('b');
        dos.writeDouble(32.2324);
        dos.writeUTF("Hello World");
        dos.close();
    }


    private static void runBufferReaderWithStringReader() throws IOException {
        try (var reader = new BufferedReader(new StringReader("Hello World"))) {
            String line;
            while ((line = reader.readLine()) != null)
                System.out.print(line);
        }
    }

    private static void runBufferedReaderWithInputStreamReader() throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null)
                System.out.print(line);
        }
    }


    private static void runInputStreamDemo() throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);

        int data;
        while ((data = reader.read()) != -1) {
            System.out.print((char) data);
            if (data == '\n') {
                break;
            }
        }
        reader.close();
    }
}


