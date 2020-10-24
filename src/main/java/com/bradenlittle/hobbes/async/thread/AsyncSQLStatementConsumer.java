package com.bradenlittle.hobbes.async.thread;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class AsyncSQLStatementConsumer implements Runnable {
    private final Connection connection;
    private final BlockingQueue<SQLTask> tasks;

    public AsyncSQLStatementConsumer(BlockingQueue<SQLTask> tasks, Connection connection) {
        this.tasks = tasks;
        this.connection = connection;
    }

    @Override
    public void run() {
        while (true) {
            try {
                List<String> formattedCommands = SQLTaskExecutor.packageStatements(tasks.take());
                Statement statement = null;
                try {
                    statement = connection.createStatement();
                    for (String command : formattedCommands) {
                        statement.execute(command);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


