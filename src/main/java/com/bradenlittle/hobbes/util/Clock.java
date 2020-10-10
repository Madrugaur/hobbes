package com.bradenlittle.hobbes.util;

public class Clock {
    public static long getDays(int i) { return getHours(i * 24); }
    public static long getHours(int i) { return getMinutes(i * 60); }
    public static long getMinutes(int i){
        return getSeconds(i) * 60;
    }
    public static long getSeconds(int i){
        return (1000 * i);
    }
}
