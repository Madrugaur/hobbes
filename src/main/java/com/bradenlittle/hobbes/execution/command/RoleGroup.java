package com.bradenlittle.hobbes.execution.command;

import com.bradenlittle.hobbes.util.DiscordUtil;
import com.bradenlittle.hobbes.util.StringUtil;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.function.Function;

public class RoleGroup implements CommandGroup {
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

    private boolean list(MessageReceivedEvent event, String[] args) {
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

    private boolean assign(MessageReceivedEvent event, String[] args) {
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
