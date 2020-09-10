package com.bradenlittle.hobbes.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

public class Insulter {
    private static String[] insults;
    static {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void init() throws IOException {
        JSONObject obj = IO.readJSON("insults.json");
        JSONArray array = obj.getJSONArray("insults");
        int len = array.length();
        insults = new String[len];
        for (int i =0; i < len; i++) {
            insults[i] = array.getString(i);
        }
    }
    public static String getInsult(){
        return insults[new Random().nextInt(insults.length)];
    }
    public static String getInsults(){
        return StringUtil.construct(insults, '\n');
    }
}
