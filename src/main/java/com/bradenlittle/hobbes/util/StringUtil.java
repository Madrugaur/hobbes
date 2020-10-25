package com.bradenlittle.hobbes.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;

/**
 * Utility methods for working with strings and making things into strings
 * @author Madrugaur (https://github.com/Madrugaur)
 */
public class StringUtil {
    /**
     * Calls on construct(args) using the default values: start=0, end=args.length, separator=' '.
     * @param args Strings to merge together
     * @return concatenated string
     */
    public static String construct(String[] args){
        return construct(args, 0, args.length, ' ');
    }

    /**
     * Calls on construct(args) using the default values: start=0, end=args.length.
     * @param args Strings to merge together
     * @param separator character to separate parts with
     * @return concatenated string
     */
    public static String construct(String[] args, char separator){
        return construct(args, 0, args.length, separator);
    }

    /**
     * Calls on construct(args) using the default values: end=args.length, separator=' '.
     * @param args Strings to merge together
     * @param start index to start at
     * @return concatenated string
     */
    public static String construct(String[] args, int start){
        return construct(args, start, args.length, ' ');
    }

    /**
     * Calls on construct(args) using the default values: end=args.length.
     * @param split Strings to merge together
     * @param i index to start at
     * @param c char to separate with
     * @return concatenated string
     */
    public static String construct(String[] split, int i, char c) {
        return construct(split, i, split.length, c);
    }

    /**
     * Concatenates a range of indices (start to end) from string array into a single string using a specified separator.
     * @param args Strings to merge together
     * @param start index to start at
     * @param end index to end at
     * @param separator char to separate with
     * @return concatenated string
     */
    public static String construct(String[] args, int start, int end, char separator){
        if (start > end) return null;
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++){
            sb.append(args[i]);
            if (i + 1 != end) sb.append(separator);
        }
        return sb.toString();
    }

    /**
     * Converts an object iterator into a string array
     * @param iterator object iterator
     * @return string array
     */
    public static String[] iteratorToStringArray(Iterator<?> iterator){
        ArrayList<String> list = new ArrayList<>();
        iterator.forEachRemaining(item -> list.add(String.valueOf(item)));
        return list.toArray(new String[] {});
    }

    /**
     * Converts a iterator in to a string array using a given function
     * @param iterator object iterator
     * @param function conversion function
     * @param <T> parameter type of the function
     * @return string array
     */
    public static <T> String[] iteratorToStringArray(Iterator<?> iterator, Function<T, String> function){
        ArrayList<String> list = new ArrayList<>();
        iterator.forEachRemaining(item -> list.add(function.apply((T) item)));
        return list.toArray(new String[] {});
    }

    /**
     * Converts an array of keys and an array of values into a hashMap
     * @param keys array of keys
     * @param vals array of vals
     * @param <K> type of keys
     * @param <V> type of vals
     * @return a hashmap
     */
    public static <K,V> HashMap<K, V> makeTable(K[] keys, V[] vals){
        HashMap<K,V> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++){
            map.put(keys[i], vals[i]);
        }
        return map;
    }

}
