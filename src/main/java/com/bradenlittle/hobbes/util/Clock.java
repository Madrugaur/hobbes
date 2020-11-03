package com.bradenlittle.hobbes.util;

/**
 * Utility class for working with time
 * @author Madruagur (https://github.com/Madrugaur)
 */
public class Clock {
    /**
     * Calculates and returns 'i' days in milliseconds
     * @param i number of days
     * @return days in milliseconds
     */
    public static long getDays(int i) { return getHours(i * 24); }

    /**
     * Calculates and returns 'i' hours in milliseconds
     * @param i number of hours
     * @return hours in milliseconds
     */
    public static long getHours(int i) { return getMinutes(i * 60); }

    /**
     * Calculates and returns 'i' minutes in milliseconds
     * @param i number of minutes
     * @return minutes in milliseconds
     */
    public static long getMinutes(int i){
        return getSeconds(i) * 60;
    }

    /**
     * Calculates and returns 'i' seconds in milliseconds
     * @param i number of seconds
     * @return seconds in milliseconds
     */
    public static long getSeconds(int i){
        return (1000 * i);
    }
}
