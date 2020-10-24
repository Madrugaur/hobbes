package com.bradenlittle.hobbes.execution.command;

import com.bradenlittle.hobbes.util.DiscordUtil;
import com.bradenlittle.hobbes.util.StringUtil;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SayGroup extends CommandGroup {
    @Override
    public boolean process(MessageReceivedEvent event, String[] args) {
        return say(event.getTextChannel(), args);
    }

    private boolean say(TextChannel channel, String[] args) {
        if (args.length == 0) {
            DiscordUtil.queueMessage("Nothing to say!", channel);
            return false;
        } else {
            String text = StringUtil.construct(args);
            DiscordUtil.queueMessage(text, channel);
            return true;
        }
    }

}
