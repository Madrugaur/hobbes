package com.bradenlittle.hobbes.execution.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class CommandGroup {
    public abstract boolean process(MessageReceivedEvent event, String[] args);

}
