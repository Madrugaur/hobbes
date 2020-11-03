package com.bradenlittle.hobbes.async.thread;

import com.bradenlittle.hobbes.util.IO;
import com.bradenlittle.hobbes.util.InformationBucket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * This class handles the execution of all of the SQLTasks submitted by the bot
 * @author Madrugaur (https://github.com/Madrugaur)
 */
public class SQLTaskExecutor {
    private static final BlockingQueue<SQLTask> tasks;
    private static final Connection connection;
    private static final ExecutorService executorService;

    static {
        connection = IO.getSQLConnection(InformationBucket.fromPackage("sql.url").replace("{resource_dir}", IO.getResourceDir()));
        tasks = new LinkedBlockingQueue<>();
        Thread thread = new Thread(new AsyncSQLStatementConsumer(tasks, connection));
        executorService = Executors.newFixedThreadPool(10);
        thread.start();
    }

    /**
     * Submits a SQLTask to the ExecutorService
     * @param task the task to be executed
     * @return the future representation of the results
     */
    public static Future<ResultSet> query(SQLTask task) {
        return executorService.submit(new AsyncSQLQueryCallable(task, connection));
    }

    /**
     * Queues a task in the BlockingQueue
     * @param task task to queue
     */
    public static void queue(SQLTask task) {
        try {
            tasks.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of SQL commands with their arguments embedded
     * @param task task to format
     * @return a list of formatted SQL commands
     */
    public static List<String> packageStatements(SQLTask task) {
        List<String> statements = new LinkedList<String>();
        Iterator<String> commands = task.getStatements().iterator();
        Iterator<Object[]> arguments = task.getArguments().iterator();
        while (commands.hasNext() && arguments.hasNext()) {
            String command = commands.next();
            Object[] argument = arguments.next();
            String formattedCommand = String.format(command, argument);
            statements.add(formattedCommand);
        }
        return statements;
    }
}
