package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int escapeChar = 1;
        Scanner scanner = new Scanner(System.in);
        String input = "";
        String output = "";
        CharacterConverter decConverter = new DecimalConverter();
        CharacterConverter hexConverter = new HexConverter();
        CharacterConverter binConverter = new BinConverter();

        System.out.println("Please input characters to convert to ASCII values");

        while (escapeChar != 0) {

            input = scanner.nextLine();

            output = decConverter.convertCharacter(input);

            System.out.print(input + " In Decimal: " + output + " " );

            output = hexConverter.convertCharacter(input);

            System.out.print("In Hexadecimal: " + output + " " );

            output = binConverter.convertCharacter(input);

            System.out.println("In Binary: " + output);

            if(input.length() == 1 && input.charAt(0) == '0') {
                escapeChar = 0;
            }
        }

        System.out.println("Thanks for using Character Converter!");
    }
}