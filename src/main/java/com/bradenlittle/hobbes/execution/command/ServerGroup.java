package com.bradenlittle.hobbes.execution.command;

import com.bradenlittle.hobbes.util.DiscordUtil;
import com.bradenlittle.hobbes.util.MinecraftServer;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.w3c.dom.Text;

public class ServerGroup extends CommandGroup {
    @Override
    public boolean process(MessageReceivedEvent event, String[] args) {
        switch (args[0]){
            case "start": return start(event);
            case "stop": return stop(event);
        }
        return false;
    }
    private boolean start(MessageReceivedEvent event){
        String mention = event.getAuthor().getAsMention();
        TextChannel channel = event.getTextChannel();
        if (MinecraftServer.isAlive()) {
            DiscordUtil.queueMessage(mention + ", the server is already on.", channel);
            return false;
        } else {
            int returnVal = MinecraftServer.start();
            if (returnVal == MinecraftServer.SERVER_START_SUCCESS){
                DiscordUtil.queueMessage(mention + ", the server is starting.", channel);
                return true;
            }
            else if (returnVal == MinecraftServer.SERVER_START_FAILED){
                DiscordUtil.queueErrorMessage(mention + ", server failed to start!", channel);
                return false;
            }
        }
        return false;
    }
    private boolean stop(MessageReceivedEvent event){
        String mention = event.getAuthor().getAsMention();
        TextChannel channel = event.getTextChannel();
        if (!MinecraftServer.isAlive()) {
            DiscordUtil.queueMessage(mention + ", the server is already offline.", channel);
            return true;
        } else {
            int returnVal = MinecraftServer.stop();
            if (returnVal == MinecraftServer.SERVER_STOP_FAILED){
                DiscordUtil.queueErrorMessage(mention + ", I couldn't stop the server!", channel);
                return false;
            }
            else if (returnVal == MinecraftServer.SERVER_STOP_SUCCESS){
                DiscordUtil.queueMessage(mention + ", I stopped the server.", channel);
                return true;
            }
        }
        return false;
    }
}
