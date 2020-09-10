package com.bradenlittle.hobbes.util;

public class StringUtil {
    public static String construct(String[] args){
        return construct(args, 0, args.length, ' ');
    }
    public static String construct(String[] args, char separator){
        return construct(args, 0, args.length, separator);
    }
    public static String construct(String[] args, int start){
        return construct(args, start, args.length, ' ');
    }
    public static String construct(String[] args, int start, int end, char separator){
        if (start > end) return null;
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++){
            sb.append(args[i]);
            if (i + 1 != end) sb.append(separator);
        }
        return sb.toString();
    }

    public static String construct(String[] split, int i, char c) {
        return construct(split, i, split.length, c);
    }
}
