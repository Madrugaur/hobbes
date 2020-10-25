package com.bradenlittle.hobbes.execution.command;

import com.bradenlittle.hobbes.util.Comic;
import com.bradenlittle.hobbes.util.DiscordUtil;
import com.bradenlittle.hobbes.util.IO;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.net.MalformedURLException;

/**
 * Represents all of the behavior relating to comics
 * @author Madruagur (https://github.com/Madrugaur)
 */
public class ComicGroup implements CommandGroup {
    /**
     * Commands are parsed in this function and the correct method is called to handle the behavior.
     * @param event the event that contains the command from the user
     * @param args arguments passed in by the user
     * @return if the action was completed successfully
     */
    @Override
    public boolean process(MessageReceivedEvent event, String[] args) {
        return paste(event.getTextChannel());
    }

    /**
     * Pastes a comic from https://www.gocomics.com/calvinandhobbes into chat
     * @param channel the channel to message in
     * @return if the operation was successful
     */
    private boolean paste(TextChannel channel){
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
