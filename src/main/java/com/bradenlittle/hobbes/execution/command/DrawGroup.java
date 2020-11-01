package com.bradenlittle.hobbes.execution.command;

import com.bradenlittle.hobbes.util.DiscordUtil;
import com.bradenlittle.hobbes.util.Shape;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Represents all of the behavior relating to drawing shapes in chat
 * @author Madrugaur (https://github.com/Madrugaur)
 */
public class DrawGroup implements CommandGroup {
    /**
     * Commands are parsed in this function and the correct method is called to handle the behavior.
     * @param event the event that contains the command from the user
     * @param args arguments passed in by the user
     * @return if the action was completed successfully
     */
    @Override
    public boolean process(MessageReceivedEvent event, String[] args) {
        return false;
    }

    /**
     * Draws the requested shape and posts it to chat
     * @param channel the channel to send the shape to
     * @param args specific shape and size
     * @return if the operation was successful
     */
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
