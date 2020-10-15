package com.bradenlittle.hobbes.execution.command;

import com.bradenlittle.hobbes.util.DiscordUtil;
import com.bradenlittle.hobbes.util.InformationBucket;
import com.bradenlittle.hobbes.util.StringUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.font.TextHitInfo;
import java.util.HashMap;
import java.util.List;

public class RoleGroup extends CommandGroup{
    @Override
    public boolean process(MessageReceivedEvent event, String[] args) {
        switch (args[0]){
            case "assign": return assign(event, args);
        }
        return false;
    }
    private boolean assign(MessageReceivedEvent event, String[] args){
        TextChannel channel = event.getTextChannel();
        if (args.length != 0){
            String target = StringUtil.construct(args);
            Role role = DiscordUtil.findRole(target, event.getGuild());
            if (role != null) {
                if (!DiscordUtil.isRestricted(role)){
                    DiscordUtil.queueMessage(String.format("%s role added!", role), channel);
                    return true;
                } else {
                    DiscordUtil.queueErrorMessage(String.format("%s can't be assigned!", role.getName()), channel);
                }
            } else{
                DiscordUtil.queueErrorMessage(String.format("%s is not a role!", target),channel);
            }
        } else {
            DiscordUtil.queueMessage("What role do you want to be assigned?", channel);
        }
        return false;
    }

}
