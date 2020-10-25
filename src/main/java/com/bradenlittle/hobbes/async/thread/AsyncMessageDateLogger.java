package com.bradenlittle.hobbes.async.thread;

import com.bradenlittle.hobbes.util.DiscordUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.util.Date;

/**
 * This class records in an SQL database, the timestamp of the last sent message for each user. If a user hasn't sent
 * any messages, their timestamp is equal to the date/time that they were seeded into the database. This class is used
 * everytime a message is received by this bot that is not from another bot.
 * @author Madrugaur (https://github.com/Madrugaur)
 */
public class AsyncMessageDateLogger {
    /**
     * Pulls the user from the message and passes to log(user)
     * @param message the message to timestamp
     */
    public static void log(Message message) {
        log(message.getAuthor());
    }

    /**
     * Creates a new SQLTask that creates a new table if needed and updates the timestamp associated with that user.
     * @param sender the user
     */
    public static void log(User sender) {
        SQLTask task = new SQLTask();
        task.addStatement(
                "CREATE TABLE IF NOT EXISTS messages (id INTEGER PRIMARY KEY, user_id TEXT, date TEXT)"
        );
        String command = "insert or replace into messages (id, user_id, date) values" +
                "((select id from messages where user_id = %s), %s, %s);";
        String tag = sender.getId();
        task.addStatement(
                command,
                tag, tag, new Date().getTime());
        SQLTaskExecutor.queue(task);
    }
}
