package com.bradenlittle.hobbes.async.thread;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;

/**
 * This class is used to asynchronously query the SQL database for information.
 * @author Madrugaur (https://github.com/Madrugaur)
 */
public class AsyncSQLQueryCallable implements Callable<ResultSet> {
    private final SQLTask task;
    private final Connection connection;

    /**
     * Constructor, inits the local variables
     * @param task the SQLTask containing the search commands and parameters
     * @param connection connection to the SQL database
     */
    public AsyncSQLQueryCallable(SQLTask task, Connection connection){
        this.task = task;
        this.connection = connection;
    }

    /**
     * Executes the query through the SQL connection and returns the results
     * @return results of the query
     * @throws SQLException throws if something goes wrong on the SQL side
     */
    @Override
    public ResultSet call() throws SQLException {
        String command = SQLTaskExecutor.packageStatements(task).get(0);
        Statement statement = connection.createStatement();
        return statement.executeQuery(command);
    }
}
