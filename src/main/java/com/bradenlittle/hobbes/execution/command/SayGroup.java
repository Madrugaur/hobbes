package com.bradenlittle.hobbes.execution.command;

import com.bradenlittle.hobbes.util.DiscordUtil;
import com.bradenlittle.hobbes.util.StringUtil;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SayGroup implements CommandGroup {
    /**
     * Commands are parsed in this function and the correct method is called to handle the behavior.
     * @param event the event that contains the command from the user
     * @param args arguments passed in by the user
     * @return if the action was completed successfully
     */
    @Override
    public boolean process(MessageReceivedEvent event, String[] args) {
        return say(event.getTextChannel(), args);
    }

    /**
     * Prints whatever is in the arguments back to the channel
     * @param channel channel to send back to
     * @param args what to print
     * @return if the operation was successful
     */
    private boolean say(TextChannel channel, String[] args){
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
