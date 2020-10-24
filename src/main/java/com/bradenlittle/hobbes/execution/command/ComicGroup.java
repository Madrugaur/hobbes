package com.bradenlittle.hobbes.execution.command;

import com.bradenlittle.hobbes.util.Comic;
import com.bradenlittle.hobbes.util.DiscordUtil;
import com.bradenlittle.hobbes.util.IO;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.net.MalformedURLException;

public class ComicGroup extends CommandGroup {
    @Override
    public boolean process(MessageReceivedEvent event, String[] args) {
        return paste(event.getTextChannel());
    }

    private boolean paste(TextChannel channel) {
        DiscordUtil.queueTyping(channel);
        Comic comic = null;
        try {
            comic = IO.getComic();
            DiscordUtil.queueMessage(comic.siteURl.toString(), channel);
            return true;
        } catch (MalformedURLException e) {
            DiscordUtil.queueErrorMessage("Failed to get comic!", channel);
            return false;
        }
    }
}
