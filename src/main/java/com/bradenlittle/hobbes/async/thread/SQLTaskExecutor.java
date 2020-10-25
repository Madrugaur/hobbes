package com.bradenlittle.hobbes.async.thread;

import com.bradenlittle.hobbes.util.IO;
import com.bradenlittle.hobbes.util.InformationBucket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class SQLTaskExecutor {
    private static final BlockingQueue<SQLTask> tasks;
    private static final Connection connection;
    private static final Thread thread;
    private static final ExecutorService executorService;

    static {
        connection = IO.getSQLConnection(InformationBucket.fromPackage("sql.url").replace("{resource_dir}", IO.getResourceDir()));
        tasks = new LinkedBlockingQueue<>();
        thread = new Thread(new AsyncSQLStatementConsumer(tasks, connection));
        executorService = Executors.newFixedThreadPool(10);
        thread.start();
    }

    public static Future<ResultSet> query(SQLTask task) {
        return executorService.submit(new AsyncSQLQueryCallable(task, connection));
    }

    public static void queue(SQLTask task) {
        try {
            tasks.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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
