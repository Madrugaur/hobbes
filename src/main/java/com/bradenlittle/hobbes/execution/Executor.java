package com.bradenlittle.hobbes.execution;

import com.bradenlittle.hobbes.async.thread.AsyncMessageDateLogger;
import com.bradenlittle.hobbes.execution.command.*;
import com.bradenlittle.hobbes.util.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.List;

/**
 * The class responsible to executing commands
 * @author Madrugaur (https://github.com/Madrugaur)
 */
public class Executor {
    /**
     * This function uses a passed MessageReceivedEvent to execute one of the bots commands. The content of the message
     * is the command. This function won't attempt to execute a command if: the sender is a bot, there are no mentions,
     * the first mention isn't this bot, or the content is empty.
     * The "raw_command" refers to the second word in the command, after the mention to the bot. It corresponds to one
     * of the CommandGroup implementations.
     * @param event
     * @return
     */
    public static boolean execute(MessageReceivedEvent event){
        User sender = event.getAuthor();
        Message message = event.getMessage();
        List<User> mentions = message.getMentionedUsers();
        if (sender.isBot()) return false;
        AsyncMessageDateLogger.log(message);
        if (mentions.size() == 0) return false;
        if (!mentions.get(0).equals(InformationBucket.getMe())) return false;
        String content = message.getContentDisplay().trim();
        if (!content.contains(" ")) {
            DiscordUtil.queueMessage("What's up?", event.getTextChannel());
        } else {
            String raw_command = content.substring(content.indexOf(" ") + 1);
            int space_index = raw_command.indexOf(" ");
            String command = "";
            String[] args;

            if (space_index == -1) {
                command = raw_command;
                args = null;
            } else {
                command = raw_command.substring(0, space_index).toLowerCase();
                args = raw_command.substring(space_index + 1).split(" ");
            }
            CommandGroup group = getGroup(command);
            if (group != null){
                group.process(event, args);
            } else {
                DiscordUtil.queueErrorMessage(command + " is an unknown command!", event.getTextChannel());
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a new instance of the class associated with the command
     * @param command command
     * @return new instance of the command's object representation
     */
    private static CommandGroup getGroup(String command){
        switch (command) {
            case "admin": return new AdminGroup();
            case "say": return new SayGroup();
            case "comic": return new ComicGroup();
            case "draw": return new DrawGroup();
            case "server": return new ServerGroup();
            case "help": return new HelpGroup();
            case "role": return new RoleGroup();
        }
        return null;
    }
}
