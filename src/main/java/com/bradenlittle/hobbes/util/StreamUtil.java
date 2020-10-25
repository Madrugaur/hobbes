package com.bradenlittle.hobbes.util;

import java.io.*;

/**
 * Utility classes for dealing with stream injection and reading
 * @author Madrugaur (https://github.com/Madrugaur)
 */
public class StreamUtil {
    /**
     * Represents a Stream Jack, i.e. an object that listens to the output of a stream
     * @author Madrugaur (https://github.com/Madrugaur)
     */
    public static class Jack extends Thread{
        InputStream is;

        /**
         * Constructor, inits variables
         * @param is the stream to listen to
         */
        public Jack(InputStream is){
            this.is = is;
        }

        /**
         * Continually listens to the output of a stream
         */
        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null)
                    System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Represents a Stream Injector, i.e. an object that injects data into a stream
     * @author Madrugaur (https://github.com/Madrugaur)
     */
    public static class Injector {
        OutputStream os;

        /**
         * Constructor, inits variables
         * @param os
         */
        public Injector(OutputStream os){
            this.os = os;
        }

        /**
         * Pushes data into a stream
         * @param input data
         * @return if the operation was successful
         */
        public boolean send(String input){
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            try {
                bw.write(input);
                bw.newLine();
                bw.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        }
    }
}
