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

/**
 * Entry point, creates the JDA instance
 * @author Madrugaur (https://github.com/Madrugaur)
 */
public class Main {
    /**
     * Creates a new JDA instance.
     * The ChunkingFilter and MemberCachePolicy are set so that the member list is updated with ALL of the members of
     * guild. By default it is just the server owner and the bot.
     * Intents:
     * GUILD_PRESENCES: allows the bot to register when someone's presence has changed
     * GUILD_MEMBERS: allows access to the members list
     * @param args command line args
     * @throws LoginException thrown if the client token doesn't match the one Discord has in its servers.
     */
    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault(InformationBucket.fromAuth("token"))
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(Arrays.asList(GatewayIntent.valueOf("GUILD_PRESENCES"), GatewayIntent.valueOf("GUILD_MEMBERS")))
                    .addEventListeners(new Listener())
                    .build();
    }
}

