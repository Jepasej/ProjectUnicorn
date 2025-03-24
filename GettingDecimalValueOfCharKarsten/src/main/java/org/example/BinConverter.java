package org.example;

import java.util.ArrayList;
import java.util.List;

public class BinConverter implements CharacterConverter {
    @Override
    public String convertCharacter(String input) {
        String output = "";

        if (input.length() == 0 || input == null) {
            return "No string provided";
        }

        List<Character> characters = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            characters.add(input.charAt(i));
        }

        for (Character character : characters) {
            int charInDecimal = (int) character;
            output += Integer.toBinaryString(charInDecimal) + " ";
        }

        //while (output.length() <= 9) {output += "0" + output;}
        return output;
    }
}
