package com.bradenlittle.hobbes.main;

import com.bradenlittle.hobbes.listener.Listener;
import com.bradenlittle.hobbes.util.IO;
import com.bradenlittle.hobbes.util.InformationBucket;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.json.*;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, LoginException {
        JDABuilder builder = JDABuilder.createDefault(InformationBucket.fromAuth("token"));
        builder.enableIntents(Arrays.asList(GatewayIntent.valueOf("GUILD_PRESENCES"), GatewayIntent.valueOf("GUILD_MEMBERS")));
        builder.addEventListeners(new Listener());
        builder.build();
    }
}

