package com.bradenlittle.hobbes.util;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * This class controls all of the external information that the bot needs. It serves as a single point of access for
 * organization and readability purposes.
 * @author Madruagaur (https://github.com/Madrugaur)
 */
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

    /**
     * Called when the bot is ready, this function finishes collecting  and registering all of the required information
     * @param event ready event
     */
    public static void runtimeInit(ReadyEvent event) {
        textChannelHashMap = new HashMap<>();
        jdaInstance = event.getJDA();
        initSelfReference();
        initRoles();
        initChannelTable();
    }

    /**
     * inits reflective references so that the bot can know its own context
     */
    private static void initSelfReference() {
        me = jdaInstance.getUsersByName(InformationBucket.fromPackage("name"), false).get(0);
        guild = tryGetGuild();
        admin_name = InformationBucket.fromAuth("admin");
    }

    /**
     * Returns a role from a given name
     * @param name name of role
     * @return Role object
     */
    private static Role getRole(String name) {
        List<Role> results = jdaInstance.getRolesByName(name, false);
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * Attempts to get the Guild from its name or id
     * @return guild object
     */
    private static Guild tryGetGuild() {
        Guild g = jdaInstance.getGuildById(InformationBucket.fromAuth("server.id"));
        g = (g != null ? g : jdaInstance.getGuildsByName(InformationBucket.fromAuth("server.name"), false).get(0));
        return g;
    }

    /**
     * grabs a couple really commonly used roles and puts them in local variables
     */
    private static void initRoles() {
        everyone = getRole("@everyone");
        online = getRole("Online");
    }

    /**
     * Builds a hashmap of text channels for constant time access
     */
    public static void initChannelTable(){
        List<TextChannel> list = guild.getTextChannels();
        for (TextChannel tc : list) {
            textChannelHashMap.put(tc.getName(), tc);
        }
    }

    /**
     * returns a string from the Auth JSON for a given key
     * @param key key
     * @return value associated with the key
     */
    public static String fromAuth(String key) {
        if (!keyExists(auth, key))
            throw new IllegalArgumentException(String.format("auth key \"%s\" does not exist%n", key));
        return auth.getString(key);
    }

    /**
     * returns a string from the package JSON for a given key
     * @param key key
     * @return value associated with the key
     */
    public static String fromPackage(String key) {
        if (!keyExists(_package, key))
            throw new IllegalArgumentException(String.format("package key \"%s\" does not exist%n", key));
        return _package.getString(key);
    }
    /**
     * returns a hashmap from the server JSON for a given key
     * @param key key
     * @return value associated with the key
     */
    public static HashMap<String, String> fromServer(String key) {
        if (!keyExists(server, key))
            throw new IllegalArgumentException(String.format("server key \"%s\" does not exist%n", key));
        String[] arr = StringUtil.iteratorToStringArray(server.getJSONArray(key).iterator());
        return StringUtil.makeTable(arr, arr);
    }
    /**
     * returns a string array from the help JSON for a given key
     * @param key key
     * @return value associated with the key
     */
    public static String[] fromHelp(String key) {
        if (!keyExists(help, key))
            throw new IllegalArgumentException(String.format("help key \"%s\" does not exist%n", key));
        JSONArray arr = help.getJSONArray(key);
        return StringUtil.iteratorToStringArray(arr.iterator());
    }
    /**
     * returns a string from the messages JSON for a given key
     * @param key key
     * @return value associated with the key
     */
    public static String fromMessages(String key){
        if (!keyExists(messages, key)) return null;
        return messages.getString(key);
    }

    /**
     * Returns the entire help JSON as a two-dimensional string array
     * @return help JSON as 2d string array
     */
    public static String[][] getHelpPage() {
        String[] keys = help.keySet().toArray(new String[]{});
        String[][] table = new String[keys.length][];
        for (int i = 0; i < keys.length; i++) {
            String[] line = fromHelp(keys[i]);
            table[i] = new String[]{keys[i], line[0], line[1]};
        }
        return table;
    }

    /**
     * Returns the text channel associated with the key
     * @param key key
     * @return value associated with the key
     */
    public static TextChannel fromTextChannelTable(String key){
        if (!textChannelHashMap.containsKey(key)) return null;
        return textChannelHashMap.get(key);
    }

    /**
     * Returns the User object representing this bot
     * @return user object
     */
    public static User getMe() {
        return me;
    }

    /**
     * Returns the Guild object where this bot is located
     * @return
     */
    public static Guild getGuild() {
        return guild;
    }

    /**
     * Returns the value of admin_name
     * @return admin_name
     */
    public static String getAdmin_name() {
        return admin_name;
    }

    /**
     * Validates that a key exists in side of a JSONObject
     * @param object json object to check
     * @param key key to validate
     * @return if the key is valid
     */
    private static boolean keyExists(JSONObject object, String key) {
        return object.has(key);
    }

    /**
     * Returns the online role object
     * @return role object
     */
    public static Role getOnline() {
        return online;
    }

    /**
     * Returns the everyone role object
     * @return role object
     */
    public static Role getEveryone() {
        return everyone;
    }

    /**
     * Returns the instance of the JDA
     * @return jda
     */
    public static JDA getJDA() {
        return jdaInstance;
    }

    /**
     * Returns the welcome message
     * @return welcome message
     */
    public static String getWelcomeMessage() {
        return welcomeMessage;
    }
}
