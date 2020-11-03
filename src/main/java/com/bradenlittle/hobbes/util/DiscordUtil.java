package com.bradenlittle.hobbes.util;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Utility methods for interacting with Discord
 * @author Madruagaur (https://github.com/Madrugaur)
 */
public class DiscordUtil {
    /**
     * Queues a message to be sent to a text channel
     * @param text message to be sent
     * @param channel channel to send to
     */
    public static void queueMessage(String text, TextChannel channel) {
        channel.sendMessage(text).queue();
    }
    /**
     * Queues an error message to be sent to a text channel. Error messages are wrapped in markdown syntax
     * @param text message to be sent
     * @param channel channel to send to
     */
    public static void queueErrorMessage(String text, TextChannel channel){
        channel.sendMessage(wrapText("```diff\n-%s\n```", text)).queue();
    }

    /**
     * Formats a string
     * @param wrapper String format to follow
     * @param text text to wrap
     * @return wrapped text
     */
    public static String wrapText(String wrapper, String text){
        return String.format(wrapper, text);
    }

    /**
     * Queues sending a file to a specific text channel
     * @param f file to send
     * @param event event containing the text channel to send to
     */
    public static void queueFile(File f, MessageReceivedEvent event) {
        event.getChannel().sendFile(f).queue();
    }

    /**
     * Queues the typing effect on that channel
     * @param channel channel to sent to
     */
    public static void queueTyping(TextChannel channel) {
        channel.sendTyping().queue();
    }

    /**
     * Attempts to locate a role in the guild by name
     * @param role name of role to find
     * @param guild guild to look in
     * @return the search result
     */
    public static Role findRole(String role, Guild guild){
        List<Role> searchResults = guild.getRolesByName(role, true);
        if (searchResults.size() == 0) return null;
        else return searchResults.get(0);
    }

    /**
     * Attempts to assign a Role to a Member
     * @param role role to assign
     * @param member member
     * @return if the operation was successful
     */
    public static boolean assignRole(Role role, Member member){
        member.getGuild().addRoleToMember(member, role).queue();
        return true;
    }

    /**
     * Checks if the role is one of the restricted roles
     * @param role role to check
     * @return if the role is restricted or not
     */
    public static boolean isRestricted(Role role){
        HashMap<String, String> restrictedRoles = InformationBucket.fromServer("restricted.roles");
        return restrictedRoles.containsKey(role.getName());
    }

    /**
     * Gets a user by id
     * @param id id of user
     * @return the user
     */
    public static User getUser(long id){
        return InformationBucket.getJDA().getUserById(id);
    }

    /**
     * Gets a user by tag
     * @param tag the tag of the user
     * @return the user
     */
    public static User getUser(String tag){
        List<User> list = InformationBucket.getJDA().getUsers();
        return InformationBucket.getJDA().getUserById(tag);
    }
}
