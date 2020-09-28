package com.bradenlittle.hobbes.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public final class InformationBucket {
    private static JSONObject _package;
    private static JSONObject auth;
    private static JSONObject help;
    private static JSONObject server;
    private static String welcomeMessage;

    static {
        try {
            _package = IO.readJSON("package.json");
            auth = IO.readJSON("auth.json");
            help = IO.readJSON("help.json");
            server = IO.readJSON("server.json");
            welcomeMessage = IO.readString("new_member_message.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String fromAuth(String key) {
        if (!keyExists(auth, key)) return null;
        String val = auth.getString(key);
        return val;
    }

    public static String fromPackage(String key) {
        if (!keyExists(_package, key)) return null;
        return _package.getString(key);
    }
    public static HashMap<String, String> fromServer(String key){
        if (!keyExists(server, key)) return null;
        String[] arr = StringUtil.iteratorToArray(server.getJSONArray(key));
        return StringUtil.makeTable(arr, arr);
    }
    public static String[] fromHelp(String key) {
        if (!keyExists(help, key)) return null;
        JSONArray arr = help.getJSONArray(key);
        return StringUtil.iteratorToArray(arr);
    }

    public static String[][] getHelpPage() {
        String[] keys = help.keySet().toArray(new String[]{});
        String[][] table = new String[keys.length][];
        for (int i = 0; i < keys.length; i++){
            String[] line = fromHelp(keys[i]);
            table[i] = new String[] {keys[i], line[0], line[1]};
        }
        return table;
    }
    private static boolean keyExists(JSONObject object, String key){
        return object.has(key);
    }
    public static String getWelcomeMessage() {
        return welcomeMessage;
    }
}
