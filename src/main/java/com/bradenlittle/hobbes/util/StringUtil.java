package com.bradenlittle.hobbes.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;

public class StringUtil {
    public static String construct(String[] args) {
        return construct(args, 0, args.length, ' ');
    }

    public static String construct(String[] args, char separator) {
        return construct(args, 0, args.length, separator);
    }

    public static String construct(String[] args, int start) {
        return construct(args, start, args.length, ' ');
    }

    public static String construct(String[] split, int i, char c) {
        return construct(split, i, split.length, c);
    }

    public static String construct(String[] args, int start, int end, char separator) {
        if (start > end) return null;
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append(args[i]);
            if (i + 1 != end) sb.append(separator);
        }
        return sb.toString();
    }

    public static String[] iteratorToStringArray(Iterator<?> iterator) {
        ArrayList<String> list = new ArrayList<>();
        iterator.forEachRemaining(item -> list.add(String.valueOf(item)));
        return list.toArray(new String[]{});
    }

    public static <T, R> String[] iteratorToStringArray(Iterator<?> iterator, Function<T, String> function) {
        ArrayList<String> list = new ArrayList<>();
        iterator.forEachRemaining(item -> list.add(function.apply((T) item)));
        return list.toArray(new String[]{});
    }

    public static <K, V> HashMap<K, V> makeTable(K[] keys, V[] vals) {
        HashMap<K, V> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], vals[i]);
        }
        return map;
    }

}
