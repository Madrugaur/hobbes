package com.bradenlittle.hobbes.async.schedule;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;
import java.util.TimerTask;

public class StripOfflineRoleTask extends TimerTask {
    List<Member> members;
    Guild guild;
    Role online;
    public StripOfflineRoleTask(List<Member> members, Guild guild, Role online){
        this.members = members;
        this.guild = guild;
        this.online = online;
    }
    @Override
    public void run() {
        for (Member member : members){
            if (member.getOnlineStatus().equals(OnlineStatus.OFFLINE)){
                guild.removeRoleFromMember(member, online);
            }
        }
    }
}
