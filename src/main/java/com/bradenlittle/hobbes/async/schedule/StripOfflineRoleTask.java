package com.bradenlittle.hobbes.async.schedule;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;
import java.util.TimerTask;

/**
 * This class is used to periodically remove the "Online" role from users.
 * Because this bot is not notified when users go offline, it is necessary to manually check and remove the role.
 * @author Madrugaur (https://github.com/Madrugaur)
 */
public class StripOfflineRoleTask extends TimerTask {
    private final List<Member> members;
    private final Guild guild;
    private final Role online;

    /**
     * Constructor, inits variables.
     * @param members a list of all the members in the server
     * @param guild an instance of the server
     * @param online instance of the online role
     */
    public StripOfflineRoleTask(List<Member> members, Guild guild, Role online){
        this.members = members;
        this.guild = guild;
        this.online = online;
    }

    /**
     * Loops through the list of members and checks if they are offline
     * If yes: it removes the online role from them
     * If no: it doesn't do anything
     */
    @Override
    public void run() {
        for (Member member : members){
            if (member.getOnlineStatus().equals(OnlineStatus.OFFLINE)){
                guild.removeRoleFromMember(member, online).queue();
            }
        }
    }
}
