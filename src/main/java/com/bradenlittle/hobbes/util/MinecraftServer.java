package com.bradenlittle.hobbes.util;

import java.io.File;
import java.io.IOException;

public class MinecraftServer {
    public static final int SERVER_START_FAILED = 1;
    public static final int SERVER_START_SUCCESS = 0;
    public static final int SERVER_STOP_FAILED = 1;
    public static final int SERVER_STOP_SUCCESS = 0;
    private static Process proc;
    private static StreamUtil.Jack output;
    private static StreamUtil.Jack error;
    private static StreamUtil.Injector input;

    static {

    }

    public static boolean isAlive() {
        return proc != null && proc.isAlive();
    }

    public static int start() {
        String start_bat_path = "T:\\RIT Squad Minecraft\\start.bat";
        File dir = new File("T:\\RIT Squad Minecraft");
        ProcessBuilder processBuilder = new ProcessBuilder(start_bat_path);
        processBuilder.directory(dir);

        try {
            proc = processBuilder.start();
            // check to see if there is a running minecraft server and tie it to hobbes
            output = new StreamUtil.Jack(proc.getInputStream());
            error = new StreamUtil.Jack(proc.getErrorStream());
            input = new StreamUtil.Injector(proc.getOutputStream());
            output.start();
            error.start();

        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    public static int stop() {
        return Boolean.compare(input.send("stop"), true);
    }

    public static int forceStop() {
        proc.destroy();
        return 0;
    }

}
