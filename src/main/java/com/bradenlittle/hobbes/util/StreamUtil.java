package com.bradenlittle.hobbes.util;

import java.io.*;

public class StreamUtil {
    public static class Jack extends Thread{
        InputStream is;
        public Jack(InputStream is){
            this.is = is;
        }
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
    public static class Injector {
        OutputStream os;
        public Injector(OutputStream os){
            this.os = os;
        }
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
