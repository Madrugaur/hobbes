package com.bradenlittle.hobbes.main;

import com.bradenlittle.hobbes.async.thread.AsyncMessageDateLogger;
import com.bradenlittle.hobbes.execution.Listener;
import com.bradenlittle.hobbes.util.InformationBucket;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, LoginException {
        JDA jda = JDABuilder.createDefault(InformationBucket.fromAuth("token"))
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(Arrays.asList(GatewayIntent.valueOf("GUILD_PRESENCES"), GatewayIntent.valueOf("GUILD_MEMBERS")))
                    .addEventListeners(new Listener())
                    .build();
    }
}

