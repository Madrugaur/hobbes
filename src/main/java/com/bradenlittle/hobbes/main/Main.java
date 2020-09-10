package com.bradenlittle.hobbes.main;

import com.bradenlittle.hobbes.listener.Listener;
import com.bradenlittle.hobbes.util.IO;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.json.*;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, LoginException {
        JSONObject jobj = IO.readJSON("auth.json");
        String token = jobj.getString("token");
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.enableIntents(Arrays.asList(GatewayIntent.valueOf("GUILD_PRESENCES")));
        builder.addEventListeners(new Listener());
        builder.build();
    }
}

