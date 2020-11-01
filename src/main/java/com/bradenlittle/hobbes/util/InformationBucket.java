package com.bradenlittle.hobbes.util;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public final class InformationBucket {
    private static JSONObject _package;
    private static JSONObject auth;
    private static JSONObject help;
    private static JSONObject server;
    private static JSONObject messages;
    private static String welcomeMessage;
    private static JDA jdaInstance;
    private static User me;
    private static String admin_name = "";
    private static Role everyone;
    private static Role online;
    private static Guild guild;
    private static HashMap<String, TextChannel> textChannelHashMap;
    static {
        try {
            _package = IO.readJSON("package.json");
            auth = IO.readJSON("auth.json");
            help = IO.readJSON("help.json");
            server = IO.readJSON("server.json");
            messages = IO.readJSON("messages.json");
            welcomeMessage = IO.readString("new_member_message.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void runtimeInit(ReadyEvent event) {
        textChannelHashMap = new HashMap<>();
        jdaInstance = event.getJDA();
        initSelfReference();
        initRoles();
        initChannelTable();
    }

    private static void initSelfReference() {
        me = jdaInstance.getUsersByName(InformationBucket.fromPackage("name"), false).get(0);
        guild = tryGetGuild();
        admin_name = InformationBucket.fromAuth("admin");
    }

    private static Role getRole(String name) {
        List<Role> results = jdaInstance.getRolesByName(name, false);
        return results.isEmpty() ? null : results.get(0);
    }

    private static Guild tryGetGuild() {
        Guild g = jdaInstance.getGuildById(InformationBucket.fromAuth("server.id"));
        g = (g != null ? g : jdaInstance.getGuildsByName(InformationBucket.fromAuth("server.name"), false).get(0));
        return g;
    }

    private static void initRoles() {
        everyone = getRole("@everyone");
        online = getRole("Online");
    }

    public static void initChannelTable(){
        List<TextChannel> list = guild.getTextChannels();
        for (TextChannel tc : list){
            textChannelHashMap.put(tc.getName(), tc);
        }
    }
    public static String fromAuth(String key) {
        if (!keyExists(auth, key))
            throw new IllegalArgumentException(String.format("auth key \"%s\" does not exist%n", key));
        return auth.getString(key);
    }

    public static String fromPackage(String key) {
        if (!keyExists(_package, key))
            throw new IllegalArgumentException(String.format("package key \"%s\" does not exist%n", key));
        return _package.getString(key);
    }

    public static HashMap<String, String> fromServer(String key) {
        if (!keyExists(server, key))
            throw new IllegalArgumentException(String.format("server key \"%s\" does not exist%n", key));
        String[] arr = StringUtil.iteratorToStringArray(server.getJSONArray(key).iterator());
        return StringUtil.makeTable(arr, arr);
    }

    public static String[] fromHelp(String key) {
        if (!keyExists(help, key))
            throw new IllegalArgumentException(String.format("help key \"%s\" does not exist%n", key));
        JSONArray arr = help.getJSONArray(key);
        return StringUtil.iteratorToStringArray(arr.iterator());
    }
    public static String fromMessages(String key){
        if (!keyExists(messages, key))
            throw new IllegalArgumentException(String.format("messages key \"%s\" does not exist%n", key));
        return messages.getString(key);
    }
    public static String[][] getHelpPage() {
        String[] keys = help.keySet().toArray(new String[]{});
        String[][] table = new String[keys.length][];
        for (int i = 0; i < keys.length; i++) {
            String[] line = fromHelp(keys[i]);
            table[i] = new String[]{keys[i], line[0], line[1]};
        }
        return table;
    }
    public static TextChannel fromTextChannelTable(String key){
        if (!textChannelHashMap.containsKey(key))
            throw new IllegalArgumentException(String.format("text channel table key \"%s\" does not exist%n", key));
        return textChannelHashMap.get(key);
    }
    public static User getMe() {
        return me;
    }

    public static Guild getGuild() {
        return guild;
    }

    public static String getAdmin_name() {
        return admin_name;
    }

    private static boolean keyExists(JSONObject object, String key) {
        return object.has(key);
    }

    public static Role getOnline() {
        return online;
    }

    public static Role getEveryone() {
        return everyone;
    }

    public static JDA getJDA() {
        return jdaInstance;
    }

    public static String getWelcomeMessage() {
        return welcomeMessage;
    }
}
