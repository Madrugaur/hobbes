package com.bradenlittle.hobbes.execution.command;

import com.bradenlittle.hobbes.util.DiscordUtil;
import com.bradenlittle.hobbes.util.Shape;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DrawGroup implements CommandGroup {
    @Override
    public boolean process(MessageReceivedEvent event, String[] args) {
        return false;
    }
    private boolean draw(TextChannel channel, String[] args){
        if (args.length < 1) {
            DiscordUtil.queueMessage("Too few arguments!", channel);
            return false;
        } else if (args.length > 2) {
            DiscordUtil.queueMessage("Too many arguments", channel);
            return false;
        } else {
            String shape = args[0];
            String symbol = args[1];
            DiscordUtil.queueMessage(Shape.getShape(shape, symbol), channel);
            return true;
        }
    }

}
