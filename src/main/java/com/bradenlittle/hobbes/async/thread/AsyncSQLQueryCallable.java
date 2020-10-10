package com.bradenlittle.hobbes.async.thread;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.Callable;

public class AsyncSQLQueryCallable implements Callable<ResultSet> {
    private SQLTask task;
    private Connection connection;
    public AsyncSQLQueryCallable(SQLTask task, Connection connection){
        this.task = task;
        this.connection = connection;
    }

    @Override
    public ResultSet call() throws Exception {
        String command = SQLTaskExecutor.packageStatements(task).get(0);
        Statement statement = connection.createStatement();
        return statement.executeQuery(command);
    }
}
