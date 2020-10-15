package com.bradenlittle.hobbes.util;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class DiscordUtil {
    public static void queueMessage(String text, TextChannel channel) {
        channel.sendMessage(text).queue();
    }
    public static void queueErrorMessage(String text, TextChannel channel){
        channel.sendMessage(text).queue();
    }
    public static void queueFile(File f, MessageReceivedEvent event) {
        event.getChannel().sendFile(f).queue();
    }
    public static void queueTyping(TextChannel channel) {
        channel.sendTyping().queue();
    }
    public static Role findRole(String role, Guild guild){
        List<Role> searchResults = guild.getRolesByName(role, true);
        if (searchResults.size() == 0) return null;
        else return searchResults.get(0);
    }
    public static boolean assignRole(Role role, Member member){
        member.getGuild().addRoleToMember(member, role).queue();
        return true;
    }
    public static boolean isRestricted(Role role){
        HashMap<String, String> restrictedRoles = InformationBucket.fromServer("restricted.roles");
        if (restrictedRoles.containsKey(role.getName())) { return false; }
        else return true;
    }
    public static User getUser(long id){
        return InformationBucket.getJDA().getUserById(id);
    }
    public static User getUser(String tag){
        List<User> list = InformationBucket.getJDA().getUsers();
        return InformationBucket.getJDA().getUserById(tag);
    }
}
