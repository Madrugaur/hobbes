package com.bradenlittle.hobbes.execution.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface CommandGroup {
    boolean process(MessageReceivedEvent event, String[] args);
}
