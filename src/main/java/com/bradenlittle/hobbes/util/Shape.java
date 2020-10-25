package com.bradenlittle.hobbes.util;

/**
 * Utility methods to draw ascii art shapes
 * @author Madrugaur (https://github.com/Madrugaur)
 */
public class Shape {
    /**
     * Returns a string containing a specified shape made with a specified character
     * @param shape shape to draw
     * @param symbol char to use
     * @return ascii art
     */
    public static String getShape(String shape, String symbol){
        switch (shape){
            case "square":
                return getSquare(symbol);
            default:
                return shape + " is unknown";
        }
    }

    /**
     * Returns a square made using a specific ascii character
     * @param symbol the char to use
     * @return a string containing an ascii square
     */
    private static String getSquare(String symbol){
        String r = "";
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                r += symbol + spaces(3);
            }
            r += "\n";
        }
        return r;
    }

    /**
     * Returns 'i' spaces
     * @param i number of spaces
     * @return a string containing 'i' spaces
     */
    private static String spaces(int i){
        String r = "";
        for (; i >= 0; i--) {
            r += " ";
        }
        return r;
    }
}