package com.bradenlittle.hobbes.util;

import net.dv8tion.jda.api.events.ReadyEvent;
import org.json.JSONObject;

import java.io.IOException;

public final class InformationBucket {
    private static JSONObject me_package;
    private static JSONObject auth;
    private static String welcomeMessage;
    static {
        try {
            me_package = IO.readJSON("package.json");
            auth = IO.readJSON("auth.json");
            welcomeMessage = IO.readString("new_member_message.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String fromAuth(String key){
        return auth.getString(key);
    }
    public static String fromPackage(String key){
        return me_package.getString(key);
    }
    public static String getWelcomeMessage(){
        return welcomeMessage;
    }
}
