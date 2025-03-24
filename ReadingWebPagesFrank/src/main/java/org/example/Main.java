package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String address = "http://horstmann.com/index.html";
        URL pageLocation = new URL(address);
        Scanner in = new Scanner(pageLocation.openStream());
        PrintWriter out = new PrintWriter("MinFil.txt");
        while(in.hasNextLine()){

            out.println(in.nextLine());
        }

        //de skal closees ellers flushes bufferen ikke til txt filen
        in.close();
        out.close();
    }
}