package com.bradenlittle.hobbes.async.schedule;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is a wrapper to the java.util.Timer class.
 * It provides two methods: one to schedule a task to fire once after a delay, and another to fire a task repeatedly
 * with a given interval.
 * @author Madruguar (https://github.com/Madrugaur)
 */
public class Scheduler {
    private static final Timer TIMER = new Timer();

    /**
     * Schedules a task to be fired repeatedly at a set interval.
     * @param task the task to be scheduled
     * @param interval the interval at which the task will be fired
     */
    public static void scheduleRegular(TimerTask task, long interval){
        TIMER.scheduleAtFixedRate(task, 0, interval);
    }

    /**
     * Schedules a task to fire once after a delay
     * @param task the task to be scheduled
     * @param delay time to wait before firing
     */
    public static void schedule(TimerTask task, long delay){
        TIMER.schedule(task, delay);
    }

}
