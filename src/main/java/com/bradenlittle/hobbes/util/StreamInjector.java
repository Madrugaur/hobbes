package com.bradenlittle.hobbes.util;

import java.io.*;

public class StreamInjector {
    OutputStream os;
    public StreamInjector(OutputStream os){
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
