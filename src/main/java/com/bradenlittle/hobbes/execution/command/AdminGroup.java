package com.bradenlittle.hobbes.execution.command;

import com.bradenlittle.hobbes.util.DiscordUtil;
import com.bradenlittle.hobbes.util.InformationBucket;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.Objects;

/**
 * This class represents all of the commands relating to administrating the bot.
 * @author Madrugaur (https://github.com/Madrugaur)
 */
public class AdminGroup implements CommandGroup {
    /**
     * Commands are parsed in this function and the correct method is called to handle the behavior.
     * @param event the event that contains the command from the user
     * @param args arguments passed in by the user
     * @return if the action was completed successfully
     */
    @Override
    public boolean process(MessageReceivedEvent event, String[] args) {
        if (validateSender(event.getAuthor())) {
            switch (args[0]) {
                case "kill":
                    return kill(event);
                case "call":
                    return call(event);
            }
        }
        return false;
    }
    /**
     * Validates that the sender is actually the registered admin.
     * @param sender the user who send the command
     * @return if the user is the admin
     */
    private boolean validateSender(User sender){
        return sender.getName().toLowerCase().contentEquals(Objects.requireNonNull(InformationBucket.fromAuth("admin")));
    }

    /**
     * Force kills the bot
     * @param event event containing the command
     * @return Returns that the bot was successfully shutdown, redundant
     */
    private boolean kill(MessageReceivedEvent event){
        DiscordUtil.queueMessage(String.format("hobbes-%s shutting down.", this.hashCode()), event.getTextChannel());
        System.exit(0);
        return true;
    }

    /**
     * Checks to see if the bot is alive
     * @param event event containing the command
     * @return returns true if the command was successful
     */
    private boolean call(MessageReceivedEvent event){
        DiscordUtil.queueMessage(String.format("hobbes-%s is alive!", this.hashCode()), event.getTextChannel());
        return true;
    }
}
