package com.bradenlittle.hobbes.listener;

import com.bradenlittle.hobbes.util.*;
import com.iwebpp.crypto.TweetNaclFast;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

public class Executor {

    public void exc_draw_symbol(MessageReceivedEvent event, String[] args) {
        if (args.length < 1) {
            queueMessage("Too few arguments!", event);
        } else if (args.length > 2) {
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
        try {
            comic = IO.getComic();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (comic == null) queueMessage("Failed to get comic!", event);
        queueMessage(comic.siteURl.toString(), event);
    }

    public void exc_admin(User sender, String admin_name, MessageReceivedEvent event, String[] args) {
        /* Format: @hobbes admin <command> [args]*/
        if (!sender.getId().toLowerCase().contentEquals(admin_name)) {
            queueMessage(sender.getAsMention() + " cancelled!", event);
        } else {
            switch (args[0]) {
                case "kill":
                    queueMessage(String.format("hobbes-%s shutting down.", this.hashCode()), event);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                    break;
                case "call":
                    queueMessage(String.format("hobbes-%s is alive!", this.hashCode()), event);
                    break;
            }

        }
    }

    public void exc_help(MessageReceivedEvent event, String[] args) {
        String line_format = "**%s**\n\tDescription: [ %s ] \n\tUsage: [ %s ]\n";
        if (args == null) {
            String helpMenu = "";
            for (String[] line : InformationBucket.getHelpPage()) {
                helpMenu += String.format(line_format, line[0], line[1], line[2]);
            }
            queueMessage(helpMenu, event);
        } else {
            String[] result = InformationBucket.fromHelp(args[0]);
            if (result == null) queueMessage("I don't know what \"" + args[0] + "\" is...", event);
            else queueMessage(String.format(line_format, args[0], result[0], result[1]), event);
        }
    }

    public void exc_say(MessageReceivedEvent event, String[] args) {
        if (args.length == 0) {
            queueMessage("Nothing to say!", event);
        } else {
            String text = StringUtil.construct(args);
            queueMessage(text, event);
            event.getMessage().delete().queue();
        }
    }

    public void exc_insult(MessageReceivedEvent event, String[] args, Role online) {
        boolean pinged = true;
        String message = online.getAsMention() + ", ";
        if (args.length == 0) {
            queueMessage("There's no one to insult!", event);
        } else {
            if (StringUtil.construct(args).toLowerCase().contentEquals("me")) {
                message += event.getMember().getNickname() + Insulter.getInsult();
            } else {
                if (event.getMessage().getMentionedUsers().size() > 1) {

                }
                message += StringUtil.construct(args) + Insulter.getInsult();
            }
            queueMessage(message, event);
            event.getMessage().delete().queue();
        }
    }

    public void exc_welcome(GuildMemberJoinEvent event) {
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

    public void exc_server(MessageReceivedEvent event, String[] args) {
        String mention = event.getAuthor().getAsMention();
        if (args.length == 0) {
            queueMessage(mention + ", what am I supposed to be doing?", event);
        } else {
            String cmd = args[0].toLowerCase();
            if (cmd.contentEquals("start")) {
                if (MinecraftServer.isAlive()) {
                    queueMessage(mention + ", the server is already on.", event);
                } else {
                    int returnVal = MinecraftServer.start();
                    if (returnVal == MinecraftServer.SERVER_START_SUCCESS)
                        queueMessage(mention + ", the server is starting.", event);
                    else if (returnVal == MinecraftServer.SERVER_START_FAILED)
                        queueMessage(mention + ", server failed to start!", event);
                }
            } else if (cmd.contentEquals("stop")) {
                if (!MinecraftServer.isAlive()) {
                    queueMessage(mention + ", the server is already offline.", event);
                } else {
                    int returnVal = MinecraftServer.stop();
                    if (returnVal == MinecraftServer.SERVER_STOP_FAILED)
                        queueMessage(mention + ", I couldn't stop the server!", event);
                    else if (returnVal == MinecraftServer.SERVER_STOP_SUCCESS)
                        queueMessage(mention + ", I stopped the server.", event);
                }
            }
        }

    }

    public void exc_assign_role(MessageReceivedEvent event, Guild guild, String[] args) {
        if (args.length == 0) {
            queueMessage("What role do you want to be assigned?", event);
            return;
        }
        String role = StringUtil.construct(args);
        List<Role> searchResults = guild.getRolesByName(role, true);
        if (searchResults.size() == 0) {
            queueMessage(String.format("%s doesn't exist!", role), event);
            return;
        }
        Role actualRole = searchResults.get(0);
        HashMap<String, String> restrictedRoles = InformationBucket.fromServer("restricted.roles");
        if (restrictedRoles.containsKey(role)) {
            queueMessage(String.format("%s is a restricted role! Try asking an admin about that one.", role), event);
            return;
        }
        guild.addRoleToMember(event.getMember(), actualRole).queue();
        queueMessage(String.format("%s role added!", role), event);
    }

    public void queueMessage(String text, MessageReceivedEvent event) {
        event.getChannel().sendMessage(text).queue();
    }

    public void queueFile(File f, MessageReceivedEvent event) {
        event.getChannel().sendFile(f).queue();
    }

    public void queueTyping(MessageReceivedEvent event) {
        event.getChannel().sendTyping().queue();
    }
}
