package com.bradenlittle.hobbes.execution.command;

import com.bradenlittle.hobbes.util.DiscordUtil;
import com.bradenlittle.hobbes.util.InformationBucket;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpGroup implements CommandGroup {
    @Override
    public boolean process(MessageReceivedEvent event, String[] args) {
        return help(event.getTextChannel(), args);
    }

    private boolean help(TextChannel channel, String[] args) {
        String line_format = "**%s**\n\tDescription: [ %s ] \n\tUsage: [ %s ]\n";
        if (args == null) {
            String helpMenu = "";
            for (String[] line : InformationBucket.getHelpPage()) {
                helpMenu += String.format(line_format, line[0], line[1], line[2]);
            }
            DiscordUtil.queueMessage(helpMenu, channel);
        } else {
            String[] result = InformationBucket.fromHelp(args[0]);
            try {
                DiscordUtil.queueMessage(String.format(line_format, args[0], result[0], result[1]), channel);
            } catch (IllegalArgumentException e) {
                DiscordUtil.queueErrorMessage("I don't know what \"" + args[0] + "\" is...", channel);
            }
        }
        return true;
    }
}
