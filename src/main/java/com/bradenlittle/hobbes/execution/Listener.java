package com.bradenlittle.hobbes.execution;

import com.bradenlittle.hobbes.async.schedule.InactiveUserSearchTask;
import com.bradenlittle.hobbes.async.schedule.Scheduler;
import com.bradenlittle.hobbes.async.schedule.StripOfflineRoleTask;
import com.bradenlittle.hobbes.async.thread.AsyncMessageDateLogger;
import com.bradenlittle.hobbes.util.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

/*  *-------*
    | IDEAS:
    | - print shapes that players ask for and use the symbol they provide - working
    | - let users opt out of being pinged by hobbes
    | - boot people if they haven't send a message in a while
    |   + cache the time stamp of the last message sent by every user (done)
    |   + schedule task to check how long its been (done-ish)
    |     - if longer than 7 days, message about staying in the server (def not done)
    | - rework Role and Channel system to be look up tables for increased efficiency
    | - voting
    | - event registration (maybe new bots)
    *-------*
    *-------*
    | COMPLETE:
    | - post random CH comic in chat
    | - mark people with online role
    |   + find player online event
    | - role stuff
    |   + ping and remind people to change their name and role claim when they join
    |   + role claim, whitelist
    | - start & stop minecraft server
    | - make help doc
    | - role assign
    | - refactor executor to work with objects
    |   + each "command" -> becomes command group
    |   + switch around group name then switch around subcommand in side class
    |   + allows for unique behavior and better organization
    *-------*
 */
public class Listener extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        InformationBucket.runtimeInit(event);
        scheduleTasks();
    }
    private void scheduleTasks() {
        if (InformationBucket.getOnline() != null) {
            Scheduler.scheduleRegular(new StripOfflineRoleTask(InformationBucket.getGuild().getMembers(), InformationBucket.getGuild(), InformationBucket.getOnline()), Clock.getMinutes(5));
        } else System.out.println("Failed to schedule new StripOfflineRoleTask! Online is null");
        Scheduler.scheduleRegular(new InactiveUserSearchTask(), Clock.getDays(7));
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        DiscordUtil.queueMessage(InformationBucket.fromMessages("welcome"), InformationBucket.fromTextChannelTable("general"));
        AsyncMessageDateLogger.log(event.getUser());
    }

    @Override
    public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event) {
        if (event.getNewOnlineStatus().name().contentEquals("ONLINE")) {
            event.getGuild().addRoleToMember(event.getMember(), InformationBucket.getOnline()).complete();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
            Executor.execute(event);
    }
}
