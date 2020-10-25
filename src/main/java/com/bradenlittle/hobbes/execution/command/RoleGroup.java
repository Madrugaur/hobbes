package com.bradenlittle.hobbes.execution.command;

import com.bradenlittle.hobbes.util.DiscordUtil;
import com.bradenlittle.hobbes.util.StringUtil;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.function.Function;

/**
 * Represents all of the commands that can be done with the role command
 * @author Madrugaur (https://github.com/Madrugaur)
 */
public class RoleGroup implements CommandGroup {
    /**
     * Commands are parsed in this function and the correct method is called to handle the behavior.
     * @param event the event that contains the command from the user
     * @param args arguments passed in by the user
     * @return if the action was completed successfully
     */
    @Override
    public boolean process(MessageReceivedEvent event, String[] args) {
        switch (args[0]) {
            case "assign":
                return assign(event, args);
            case "list":
                return list(event, args);
        }
        return false;
    }

    /**
     * List all of the roles available on this server
     * @param event event with command
     * @param args arguments
     * @return if the operation was successful
     */
    private boolean list(MessageReceivedEvent event, String[] args){
        try {
            List<Role> roles = event.getGuild().getRoles();
            Function<Role, String> wrapRoleName = (i) -> (DiscordUtil.wrapText("> %s", i.getName().replace("@", "")));
            String roleString = StringUtil.construct(StringUtil.iteratorToStringArray(roles.iterator(), wrapRoleName), '\n');
            DiscordUtil.queueMessage(roleString, event.getTextChannel());
        } catch (Exception e) {
            DiscordUtil.queueErrorMessage("Failed to get Role list!", event.getTextChannel());
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Attempts to assign a role to the sender of the message
     * @param event message event
     * @param args role to assign
     * @return if the operation was successful
     */
    private boolean assign(MessageReceivedEvent event, String[] args){
        TextChannel channel = event.getTextChannel();
        if (args.length != 0) {
            String target = StringUtil.construct(args, 1);
            Role role = DiscordUtil.findRole(target, event.getGuild());
            if (role != null) {
                if (!DiscordUtil.isRestricted(role)) {
                    event.getGuild().addRoleToMember(event.getMember(), role).queue();
                    DiscordUtil.queueMessage(String.format("%s role added!", role), channel);
                    return true;
                } else {
                    DiscordUtil.queueErrorMessage(String.format("%s can't be assigned!", role.getName()), channel);
                }
            } else {
                DiscordUtil.queueErrorMessage(String.format("%s is not a role!", target), channel);
            }
        } else {
            DiscordUtil.queueMessage("What role do you want to be assigned?", channel);
        }
        return false;
    }

}
