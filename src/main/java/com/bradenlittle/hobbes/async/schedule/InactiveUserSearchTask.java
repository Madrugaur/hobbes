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

/**
 * This class asynchronously submits a query to the SQL Database for the Discord user ids that have a timestamp older than 7 days. It
 * then attempts to remove the associated members from the guild.
 * @author Madrugaur (https://github.com/Madrugaur)
 */
public class InactiveUserSearchTask extends TimerTask {
    /**
     *  This is the main function of this class. It is called by a java.util.Timer at a specific time interval.
     *  It gets the results from the SQL database and then attempts to remove the returned members.
     */
    @Override
    public void run() {
        SQLTask search = new SQLTask();
        search.addStatement("SELECT user_id FROM messages WHERE date < %s", new Date().getTime() - Clock.getDays(7));
        try {
            ResultSet results = SQLTaskExecutor.query(search).get();
            while (results.next()) {
                kick(results.getString(1));
            }
        } catch (InterruptedException | SQLException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the actual method that attempts to kick a user.
     * It firsts sends a private message that they are being removed due to inactivity. If they would like to rejoin the
     * server, they can reach out to an admin or the server owner.
     * In the future, I would like to automate the rejoin process.
     * @param id id of the member
     */
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
