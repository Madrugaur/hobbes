package com.bradenlittle.hobbes.async.schedule;

import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {
    private static Timer timer = new Timer();
    public static void scheduleRegular(TimerTask task, long interval){
        timer.scheduleAtFixedRate(task, 0, interval);
    }
    public static void schedule(TimerTask task, long delay){
        timer.schedule(task, delay);
    }

}
