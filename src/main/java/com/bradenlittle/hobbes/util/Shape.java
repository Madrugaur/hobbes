package com.bradenlittle.hobbes.util;

public class Shape {
    public static String getShape(String shape, String symbol) {
        switch (shape) {
            case "square":
                return getSquare(symbol);
            default:
                return shape + " is unknown";
        }
    }

    private static String getSquare(String symbol) {
        String r = "";
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                r += symbol + spaces(3);
            }
            r += "\n";
        }
        return r;
    }

    private static String spaces(int i) {
        String r = "";
        for (; i >= 0; i--) {
            r += " ";
        }
        return r;
    }
}