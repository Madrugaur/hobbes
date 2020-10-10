package com.bradenlittle.hobbes.async.thread;

import net.dv8tion.jda.api.entities.Message;

import java.time.LocalDateTime;
import java.util.Date;

public class AsyncMessageDateLogger {
    public static void log(Message message) {
        SQLTask task = new SQLTask();
        task.addStatement(
                "CREATE TABLE IF NOT EXISTS messages (id INTEGER PRIMARY KEY, user_id TEXT, date TEXT)",
                new Object[]{}
        );
        String command = "insert or replace into messages (id, user_id, date) values" +
                "((select id from messages where user_id = %s), %s, %s);";
        task.addStatement(
                command,
                new Object[] { message.getMember().getId(), message.getMember().getId(), new Date().getTime()}
        );
        SQLTaskExecutor.queue(task);
    }
}
