package com.bradenlittle.hobbes.execution.command;

import com.bradenlittle.hobbes.util.DiscordUtil;
import com.bradenlittle.hobbes.util.InformationBucket;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AdminGroup extends CommandGroup {
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

    private boolean validateSender(User sender) {
        return sender.getName().toLowerCase().contentEquals(InformationBucket.fromAuth("admin"));
    }

    private boolean kill(MessageReceivedEvent event) {
        DiscordUtil.queueMessage(String.format("hobbes-%s shutting down.", this.hashCode()), event.getTextChannel());
        System.exit(0);
        return true;
    }

    private boolean call(MessageReceivedEvent event) {
        DiscordUtil.queueMessage(String.format("hobbes-%s is alive!", this.hashCode()), event.getTextChannel());
        return true;
    }
}
