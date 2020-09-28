package com.bradenlittle.hobbes.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

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
    public static <K extends Iterable<Object>> String[] iteratorToArray(K iterable){
        ArrayList<String> list = new ArrayList<>();
        iterable.forEach(item -> list.add(String.valueOf(item)));
        return list.toArray(new String[] {});
    }
    public static <K,V> HashMap<K, V> makeTable(K[] keys, V[] vals){
        HashMap<K,V> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++){
            map.put(keys[i], vals[i]);
        }
        return map;
    }
    public static String construct(String[] split, int i, char c) {
        return construct(split, i, split.length, c);
    }
}
