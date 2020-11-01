package com.bradenlittle.hobbes.execution.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Provides structure and organization to how commands are executed
 * @author Madrugaur (https://github.com/Madrugaur)
 */
public interface CommandGroup {
    /**
     * Commands are parsed in this function and the correct method is called to handle the behavior.
     * @param event the event that contains the command from the user
     * @param args arguments passed in by the user
     * @return if the action was completed successfully
     */
    boolean process(MessageReceivedEvent event, String[] args);
}
