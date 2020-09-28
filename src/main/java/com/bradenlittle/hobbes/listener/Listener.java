package com.bradenlittle.hobbes.listener;

import com.bradenlittle.hobbes.schedule.Scheduler;
import com.bradenlittle.hobbes.schedule.StripOfflineRoleTask;
import com.bradenlittle.hobbes.util.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
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
    | - save memes do database and post upon request
    | - let users add themselves to a Do Not Insult list
    | - let users opt out of being pinged by hobbes
    | - implement "Operation Go To Sleep" by deleting all chats between certain times
    | - boot people if they haven't send a message in a while
    |   + cache the time stamp of the last message sent by every user
    *-------*
    *-------*
    | COMPLETE:
    | - post random CH comic in chat
    | - insult people
    | - mark people with online role
    |   + find player online event
    | - role stuff
    |   + ping and remind people to change their name and role claim when they join
    |   + role claim, whitelist
    | - start & stop minecraft server
    | - make help doc
    | - role assign
    *-------*
 */
public class Listener extends ListenerAdapter {
    private JDA jdaInstance;
    private User me;
    private String admin_name = "";
    private Role everyone;
    private Role online;
    private Guild guild;
    private Executor executor = new Executor();
    private List<Message> messages = new ArrayList<Message>();
    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("Hobbes Boot Start");
        jdaInstance = event.getJDA();
        System.out.println(jdaInstance.getGuilds());
        /*** Self Reference **/
        initSelfReference();
        /** Useful Roles **/
        initRoles();
        /** Tasks **/
        scheduleTasks();
        System.out.println("Hobbes Online");
    }

    private void initSelfReference() {
        me = jdaInstance.getUsersByName(InformationBucket.fromPackage("name"), false).get(0);
        guild = tryGetGuild();
        admin_name = InformationBucket.fromAuth("admin");
    }

    private Guild tryGetGuild() {
        Guild g = jdaInstance.getGuildById(InformationBucket.fromAuth("server.id"));
        g = (g != null ? g : jdaInstance.getGuildsByName(InformationBucket.fromAuth("server.name"), false).get(0));
        return g;
    }

    private void initRoles() {
        everyone = getRole("@everyone");
        online = getRole("Online");
    }

    private Role getRole(String name) {
        List<Role> results = jdaInstance.getRolesByName(name, false);
        return results.isEmpty() ? null : results.get(0);
    }

    private void scheduleTasks() {
        if (online != null) {
            Scheduler.scheduleRegular(new StripOfflineRoleTask(guild.getMembers(), guild, online), Clock.getMinutes(5));
        } else System.out.println("Failed to schedule new StripOfflineRoleTask! Online is null");
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        executor.exc_welcome(event);
    }

    @Override
    public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event) {
        if (event.getNewOnlineStatus().name().contentEquals("ONLINE")) {
            event.getGuild().addRoleToMember(event.getMember(), online).complete();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User sender = event.getAuthor();
        Message message = event.getMessage();
        List<User> mentions = message.getMentionedUsers();
        if (mentions.size() == 0) return;
        if (!mentions.get(0).equals(me)) return;
        if (sender.isBot()) return;
        String content = message.getContentDisplay().trim();
        if (!content.contains(" ")) {
            executor.queueMessage("What's up?", event);
        } else {
            String raw_command = content.substring(content.indexOf(" ") + 1);
            int space_index = raw_command.indexOf(" ");
            String command = "";
            String[] args;

            if (space_index == -1) {
                command = raw_command;
                args = null;
            } else {
                command = raw_command.substring(0, space_index).toLowerCase();
                args = raw_command.substring(space_index + 1).split(" ");
            }
            switch (command) {
                case "admin":
                    executor.exc_admin(sender, admin_name, event, args);
                    break;
                case "say":
                    executor.exc_say(event, args);
                    break;
                case "insult":
                    executor.exc_insult(event, args, online);
                    break;
                case "comic":
                    executor.exc_comic(event, args);
                    break;
                case "draw":
                    executor.exc_draw_symbol(event, args);
                    break;
                case "server":
                    executor.exc_server(event, args);
                    break;
                case "help":
                    executor.exc_help(event, args);
                    break;
                case "assign":
                    executor.exc_assign_role(event, guild, args);
                    break;
                default:
                    executor.queueMessage("I'm confused!", event);
                    break;
            }

        }
    }

}
