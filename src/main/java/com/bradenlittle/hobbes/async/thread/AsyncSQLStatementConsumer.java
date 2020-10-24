package com.bradenlittle.hobbes.async.thread;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * This class is used to asynchronously execute SQL statements. It is different from AsyncSQLQueryCallable because it is
 * always waiting for new SQLTasks to execute. A BlockingQueue is used to ensure that there is no collision when using
 * the database.
 * @author Madrugaur (https://github.com/Madrugaur)
 */
public class AsyncSQLStatementConsumer implements Runnable {
    private Connection connection;
    private BlockingQueue<SQLTask> tasks;

    /**
     * Constructor, inits local variables
     * @param tasks a BlockingQueue used to hold SQLTask until they are ready to be executed
     * @param connection connection to the SQL database
     */
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


