package com.bradenlittle.hobbes.listener;

import com.bradenlittle.hobbes.util.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.net.MalformedURLException;

public class Executor {
    public void exc_remember(MessageReceivedEvent event, String[] args){
        if (args.length > 2){
            queueMessage("Too many arguments!", event);
        } else if (args.length < 2) {
            queueMessage("Too few arguments", event);
        } else {
            boolean good_entry = Remember.remember(event.getMember(), args);
            if (good_entry) queueMessage("I saved the date!", event);
            else queueMessage("I couldn't the date. :'(", event);
        }
    }
    public void exc_draw_symbol(MessageReceivedEvent event, String[] args){
        if (args.length < 1){
            queueMessage("Too few arguments!", event);
        }else if (args.length > 2) {
            queueMessage("Too many arguments", event);
        } else {
            String shape = args[0];
            String symbol = args[1];
            queueMessage(Shape.getShape(shape, symbol), event);
        }
    }
    public void exc_comic(MessageReceivedEvent event, String[] args) {
        queueTyping(event);
        Comic comic = null;
        try { comic = IO.getComic(); } catch (MalformedURLException e) { e.printStackTrace(); }
        if (comic == null) queueMessage("Failed to get comic!", event);
        queueMessage(comic.siteURl.toString(), event);
    }
    public void exc_admin(User sender, String admin_name, MessageReceivedEvent event, String[] args) {
        if (!sender.getName().toLowerCase().contentEquals(admin_name)){
            queueMessage(sender.getAsMention() + " cancelled!", event);
        } else {
            switch (args[0]){
                case "kill":
                    queueMessage(String.format("hobbes-%s shutting down.", this.hashCode()), event);
                    try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
                    System.exit(0);
                    break;
                case "call":
                    queueMessage(String.format("hobbes-%s is alive!", this.hashCode()), event);
                    break;
            }

        }
    }
    public void exc_help(MessageReceivedEvent event, String[] args){

    }
    public void exc_say(MessageReceivedEvent event, String[] args){
        if (args.length == 0){
            queueMessage("Nothing to mime!", event);
        } else{
            String text = StringUtil.construct(args);
            queueMessage(text, event);
        }
    }
    public void exc_insult(MessageReceivedEvent event, String[] args, Role everyone){
        boolean pinged = true;
        String message = everyone.getAsMention() + ", ";
        if (args.length == 0) {
            queueMessage("There's no one to insult!", event);
        } else {
            if (StringUtil.construct(args).toLowerCase().contentEquals("me")){
                message += event.getMember().getNickname() + Insulter.getInsult();
            } else {
                message += StringUtil.construct(args) + Insulter.getInsult();
            }
            queueMessage(message, event);
        }
    }
    public void exc_welcome(GuildMemberJoinEvent event){
        User sender = event.getUser();
        //if (sender.isBot()) return;
        JDA jda = event.getJDA();
        TextChannel role_claim_channel = jda.getTextChannelsByName("role-claim", false).get(0);
        jda.getTextChannelsByName("general", false).get(0)
                .sendMessage(InformationBucket.getWelcomeMessage()
                            .replace("{MENTION}", sender.getAsMention())
                            .replace("{ROLE_CLAIM}", role_claim_channel.getAsMention()))
                .queue();


    }
    public void queueMessage(String text, MessageReceivedEvent event){
        event.getChannel().sendMessage(text).queue();
    }
    public void queueFile(File f, MessageReceivedEvent event){
        event.getChannel().sendFile(f).queue();
    }
    public void queueTyping(MessageReceivedEvent event){
        event.getChannel().sendTyping().queue();
    }
}
