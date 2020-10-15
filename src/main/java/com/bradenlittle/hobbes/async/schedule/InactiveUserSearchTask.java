package com.bradenlittle.hobbes.async.schedule;

import com.bradenlittle.hobbes.async.thread.SQLTask;
import com.bradenlittle.hobbes.async.thread.SQLTaskExecutor;
import com.bradenlittle.hobbes.util.Clock;
import com.bradenlittle.hobbes.util.DiscordUtil;
import com.bradenlittle.hobbes.util.InformationBucket;
import net.dv8tion.jda.api.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class InactiveUserSearchTask extends TimerTask {

    @Override
    public void run() {
        SQLTask search = new SQLTask();
        search.addStatement("SELECT user_id FROM messages WHERE date < %s", new Object[] { new Date().getTime() - Clock.getDays(7)});
        try {
            ResultSet results = SQLTaskExecutor.query(search).get();
            while (results.next()){
                kick(results.getString(1));
            }
        } catch (InterruptedException | SQLException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    private void kick(String id){
        User user = DiscordUtil.getUser(id);
        if (user.equals(InformationBucket.getMe())) return;
        System.out.println(user.getName());
        user.openPrivateChannel().queue((privateChannel -> {
                privateChannel.sendMessage(InformationBucket.fromMessages("inactive")).queue();
        }));
        InformationBucket.getGuild().getMember(user).kick("Inactivity").queue();
    }


}
