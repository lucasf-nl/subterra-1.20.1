package net.apixelite.subterra.util.romannumeralconverter;

import java.util.TreeMap;

public class RomanNumeralConverter {

    private final static TreeMap<Integer, String> intToRomanMap = new TreeMap<>();
    private final static TreeMap<Character, Integer> romanToIntMap = new TreeMap<>();

    static {
        // Mapping for integer to Roman numeral conversion
        intToRomanMap.put(1000, "M");
        intToRomanMap.put(900, "CM");
        intToRomanMap.put(500, "D");
        intToRomanMap.put(400, "CD");
        intToRomanMap.put(100, "C");
        intToRomanMap.put(90, "XC");
        intToRomanMap.put(50, "L");
        intToRomanMap.put(40, "XL");
        intToRomanMap.put(10, "X");
        intToRomanMap.put(9, "IX");
        intToRomanMap.put(5, "V");
        intToRomanMap.put(4, "IV");
        intToRomanMap.put(1, "I");

        // Mapping for Roman numeral to integer conversion
        romanToIntMap.put('I', 1);
        romanToIntMap.put('V', 5);
        romanToIntMap.put('X', 10);
        romanToIntMap.put('L', 50);
        romanToIntMap.put('C', 100);
        romanToIntMap.put('D', 500);
        romanToIntMap.put('M', 1000);
    }

    // Method to convert integer to Roman numeral
    public static String intToRoman(int num) {
        StringBuilder roman = new StringBuilder();

        for (Integer key : intToRomanMap.descendingKeySet()) {
            while (num >= key) {
                roman.append(intToRomanMap.get(key));
                num -= key;
            }
        }

        return roman.toString();
    }

    // Method to convert Roman numeral to integer
    public static int romanToInt(String roman) {
        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            char currentChar = roman.charAt(i);
            int currentValue = romanToIntMap.get(currentChar);

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            prevValue = currentValue;
        }

        return result;
    }

    // Main method to test the class
    public static void main(String[] args) {
        // Test converting integer to Roman numeral
        int number = 1987;
        String romanNumeral = intToRoman(number);
        System.out.println("Integer: " + number + " -> Roman: " + romanNumeral);

        // Test converting Roman numeral to integer
        String roman = "MCMLXXXVII";
        int intNumber = romanToInt(roman);
        System.out.println("Roman: " + roman + " -> Integer: " + intNumber);
    }
}
