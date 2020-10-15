package com.bradenlittle.hobbes.async.thread;

import com.bradenlittle.hobbes.util.DiscordUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.util.Date;

public class AsyncMessageDateLogger {
    public static void log(Message message) {
        SQLTask task = new SQLTask();
        task.addStatement(
                "CREATE TABLE IF NOT EXISTS messages (id INTEGER PRIMARY KEY, user_id TEXT, date TEXT)",
                new Object[]{}
        );
        String command = "insert or replace into messages (id, user_id, date) values" +
                "((select id from messages where user_id = \'%s\'), \'%s\', %s);";
        String tag = message.getAuthor().getId();
        task.addStatement(
                command,
                new Object[] { tag, tag, new Date().getTime()}
        );
        SQLTaskExecutor.queue(task);
    }
    public static void log(User sender) {
        SQLTask task = new SQLTask();
        task.addStatement(
                "CREATE TABLE IF NOT EXISTS messages (id INTEGER PRIMARY KEY, user_id TEXT, date TEXT)",
                new Object[]{}
        );
        String command = "insert or replace into messages (id, user_id, date) values" +
                "((select id from messages where user_id = %s), %s, %s);";
        String tag = sender.getId();
        task.addStatement(
                command,
                new Object[] { tag, tag, new Date().getTime()}
        );
        SQLTaskExecutor.queue(task);
    }
}
